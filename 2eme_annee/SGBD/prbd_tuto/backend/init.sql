/**************************************************************/
/*                                                            */
/* CLEAN                                                      */
/*                                                            */
/**************************************************************/
SET search_path TO public;


/*
 Supprime tout ce qui a été créé précédemment
 */
-- permet de revenir au rôle de la connexion, au cas où on aurait pris un autre rôle
RESET ROLE;

-- supprime les schémas et tout ce qu'ils contiennent
DROP SCHEMA IF EXISTS public CASCADE;

DROP SCHEMA IF EXISTS auth CASCADE;

-- supprime les rôles
DROP ROLE IF EXISTS anon, authenticator, authenticated;


/*
 Crée les rôles
 */
CREATE ROLE authenticator noinherit LOGIN PASSWORD 'mysecretpassword';

CREATE ROLE anon nologin;

CREATE ROLE authenticated nologin;

GRANT anon TO authenticator;

GRANT authenticated TO authenticator;

GRANT anon TO authenticated;


/*
 Crée le schéma public
 */
CREATE SCHEMA public;

-- par défaut tout est privé
-- (le 'public' qui est dans le from signifie "tous les rôles présents et futurs")
REVOKE ALL ON SCHEMA public FROM public;

-- par défault les fonctions ne sont pas accessibles
ALTER DEFAULT privileges REVOKE ALL ON functions FROM public;

-- donne accès à certains rôles
GRANT usage ON SCHEMA public TO anon, authenticated;


/**************************************************************/
/*                                                            */
/* POSTGREST_RELOAD_CACHE                                     */
/*                                                            */
/**************************************************************/
/*
 Crée un trigger qui fait en sorte que la cache de PostgREST soit rechargée à chaque commande ddl
 (voir https://postgrest.org/en/stable/references/schema_cache.html#automatic-schema-cache-reloading)
 */
CREATE OR REPLACE FUNCTION pgrst_watch()
    RETURNS event_trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NOTIFY pgrst,
    'reload schema';
    RAISE NOTICE 'pgrst_watch: reload schema';
END;
$$;

-- fait en sorte que le trigger se déclenche après chaque commande ddl
DROP EVENT TRIGGER IF EXISTS pgrst_watch;

CREATE EVENT TRIGGER pgrst_watch ON ddl_command_end
    EXECUTE PROCEDURE pgrst_watch();

-- test : force un reload
NOTIFY pgrst,
'reload schema';


/**************************************************************/
/*                                                            */
/* JWT                                                        */
/*                                                            */
/**************************************************************/
/*
 Met en place la sécurité à base de jetons JWT
 */
DROP SCHEMA IF EXISTS auth CASCADE;

CREATE SCHEMA auth;

GRANT usage ON SCHEMA auth TO anon, authenticated;

SET search_path TO auth;

-- par défault les fonctions ne sont pas accessibles
ALTER DEFAULT privileges REVOKE ALL ON functions FROM public;

SET search_path TO public;

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA auth;

CREATE TYPE auth.jwt_token AS (
    token text
);


/*
 Code copié depuis l'extension pgjwt (voir https://github.com/michelp/pgjwt).
 On n'utilise pas l'extension elle-même, car elle n'est généralement pas disponible dans les VM hostées.
 */
CREATE OR REPLACE FUNCTION auth.url_encode(data bytea)
    RETURNS text
    LANGUAGE sql
    AS $$
    SELECT
        translate(encode(data, 'base64'), E'+/=\n', '-_');
$$ IMMUTABLE;

CREATE OR REPLACE FUNCTION auth.url_decode(data text)
    RETURNS bytea
    LANGUAGE sql
    AS $$
    WITH t AS(
        SELECT
            translate(data, '-_', '+/') AS trans
),
rem AS(
    SELECT
        length(t.trans) % 4 AS remainder
    FROM
        t) -- compute padding size
    SELECT
        decode(t.trans || CASE WHEN rem.remainder > 0 THEN
                repeat('=',(4 - rem.remainder))
            ELSE
                ''
            END, 'base64')
    FROM
        t,
        rem;
$$ IMMUTABLE;

CREATE OR REPLACE FUNCTION auth.algorithm_sign(signables text, secret text, algorithm text)
    RETURNS text
    LANGUAGE sql
    AS $$
    WITH alg AS(
        SELECT
            CASE WHEN algorithm = 'HS256' THEN
                'sha256'
            WHEN algorithm = 'HS384' THEN
                'sha384'
            WHEN algorithm = 'HS512' THEN
                'sha512'
            ELSE
                ''
            END AS id) -- hmac throws error
        SELECT
            auth.url_encode(auth.hmac(signables, secret, alg.id))
        FROM
            alg;
$$ IMMUTABLE;

CREATE OR REPLACE FUNCTION auth.sign (payload json, secret text, algorithm text DEFAULT 'HS256')
    RETURNS text
    LANGUAGE sql
    AS $$
    WITH header AS(
        SELECT
            auth.url_encode(convert_to('{"alg":"' || algorithm || '","typ":"JWT"}', 'utf8')) AS data
),
payload AS(
    SELECT
        auth.url_encode(convert_to(payload::text, 'utf8')) AS data
),
signables AS(
    SELECT
        header.data || '.' || payload.data AS data
    FROM
        header,
        payload
)
SELECT
    signables.data || '.' || auth.algorithm_sign(signables.data, secret, algorithm)
FROM
    signables;
$$ IMMUTABLE;

CREATE OR REPLACE FUNCTION auth.try_cast_double(inp text)
    RETURNS double precision
    AS $$
BEGIN
    BEGIN
        RETURN inp::double precision;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN NULL;
    END;
END;

$$
LANGUAGE plpgsql
IMMUTABLE;

CREATE OR REPLACE FUNCTION auth.verify(token text, secret text, algorithm text DEFAULT 'HS256')
    RETURNS TABLE(
        header json,
        payload json,
        valid boolean)
    LANGUAGE sql
    AS $$
    SELECT
        jwt.header AS header,
        jwt.payload AS payload,
        jwt.signature_ok
        AND tstzrange(to_timestamp(auth.try_cast_double(jwt.payload ->> 'nbf')), to_timestamp(auth.try_cast_double(jwt.payload ->> 'exp'))) @> CURRENT_TIMESTAMP AS valid
    FROM(
        SELECT
            convert_from(auth.url_decode(r[1]), 'utf8')::json AS header,
            convert_from(auth.url_decode(r[2]), 'utf8')::json AS payload,
            r[3] = auth.algorithm_sign(r[1] || '.' || r[2], secret, algorithm) AS signature_ok
        FROM
            regexp_split_to_array(token, '\.') r) jwt
$$ IMMUTABLE;

