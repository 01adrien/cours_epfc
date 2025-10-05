SET search_path TO public, auth;


/**************************************************************
 Role basic_user
 **************************************************************/
DROP ROLE IF EXISTS basic_user;

CREATE ROLE basic_user nologin;

GRANT basic_user TO authenticator;

GRANT authenticated TO basic_user;


/**************************************************************
 Table users
 **************************************************************/
DROP TABLE IF EXISTS users;

CREATE TABLE users(
    pseudo varchar(128) NOT NULL PRIMARY KEY CHECK (length(trim(pseudo)) >= 3),
    password varchar(128) NOT NULL CHECK (length(trim(PASSWORD)) >= 1),
    role varchar(32) NOT NULL DEFAULT 'basic_user' CHECK (ROLE IN ('basic_user', 'admin'))
);

INSERT INTO users(pseudo, password)
    VALUES ('ben', '?'),
('boris', '?'),
('geo', '?');


/**************************************************************
 Trigger pour encrypter automatiquement le mot de passe
 **************************************************************/
CREATE OR REPLACE FUNCTION auth.encrypt_pass()
    RETURNS TRIGGER
    AS $$
BEGIN
    IF tg_op = 'INSERT' OR NEW.password <> OLD.password THEN
        NEW.password = auth.crypt(NEW.password, auth.gen_salt('bf'));
    END IF;
    RETURN new;
END
$$
LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS encrypt_pass ON users;

CREATE TRIGGER encrypt_pass
    BEFORE INSERT OR UPDATE ON users
    FOR EACH ROW
    EXECUTE PROCEDURE auth.encrypt_pass();

-- met à jour les mots de passe et déclenche le trigger pour les crypter
UPDATE
    users
SET
    PASSWORD = pseudo;


/**************************************************************
 Fonction qui permet de faire le login et retourne un jeton JWT
 **************************************************************/
CREATE OR REPLACE FUNCTION LOGIN(pseudo text, PASSWORD text)
    RETURNS auth.jwt_token
    AS $$
DECLARE
    ROLE name;
    result auth.jwt_token;
BEGIN
    -- check email and password
    IF NOT EXISTS (
        SELECT
            *
        FROM
            users
        WHERE
            users.pseudo = login.pseudo
            AND users.password = auth.crypt(login.password, users.password)) THEN
    RAISE invalid_password
    USING message = 'invalid user or password';
END IF;
    SELECT
        users.role
    FROM
        users
    WHERE
        users.pseudo = login.pseudo INTO ROLE;
    SELECT
        auth.sign(row_to_json(r), '94VEF6BGSV4MHACYQYWYZZXILQR7412Z') AS token
    FROM (
        SELECT
            ROLE AS role,
            pseudo AS sub,
            -- valid for 24 hours
            extract(epoch FROM now())::integer + 24 * 60 * 60 AS exp) r INTO result;
    RETURN result;
END;
$$
LANGUAGE plpgsql
SECURITY DEFINER;

GRANT EXECUTE ON FUNCTION login TO anon;


/**************************************************************
 Fonctions utilitaires vàv de la sécurité
 **************************************************************/
/*
 Retourne le pseudo de l'utilisateur connecté via JWT
 */
CREATE OR REPLACE FUNCTION auth.pseudo()
    RETURNS varchar
    AS $$
BEGIN
    RETURN current_setting('request.jwt.claims', TRUE)::json ->> 'sub';
END;
$$
LANGUAGE plpgsql;


/*
 Retourne le rôle de l'utilisateur connecté via JWT
 */
CREATE OR REPLACE FUNCTION auth.role()
    RETURNS varchar
    AS $$
BEGIN
    RETURN current_setting('request.jwt.claims', TRUE)::json ->> 'role';
END;
$$
LANGUAGE plpgsql;


/*
 Vérifie si l'utilisateur est connecté
 */
CREATE OR REPLACE FUNCTION auth.check_logged()
    RETURNS void
    AS $$
BEGIN
    IF auth.pseudo() IS NULL THEN
        RAISE EXCEPTION 'You must be logged';
    END IF;
END
$$
LANGUAGE plpgsql;


/*
 Lors des tests, permet de simuler une connexion anonyme
 */
CREATE OR REPLACE FUNCTION auth.login_anonymously_for_test()
    RETURNS void
    AS $$
BEGIN
    EXECUTE 'set session role to anon';
    -- true = pour la transaction, false = pour la session
    PERFORM
        set_config('request.jwt.claims', '{"role":"anon"}', FALSE);
END
$$
LANGUAGE plpgsql;


/*
 Lors des tests, permet de simuler une connexion avec un utilisateur donné
 */
CREATE OR REPLACE FUNCTION auth.login_for_test(pseudo text)
    RETURNS void
    AS $$
DECLARE
    ROLE text;
BEGIN
    IF NOT EXISTS (
        SELECT
            1
        FROM
            users u
        WHERE
            u.pseudo = login_for_test.pseudo) THEN
    RAISE EXCEPTION 'User ''%'' does not exist', pseudo;
END IF;
    SELECT
        m.role
    FROM
        users m
    WHERE
        m.pseudo = login_for_test.pseudo INTO ROLE;
    EXECUTE 'set session role to ' || ROLE;
    -- true = pour la transaction, false = pour la session
    PERFORM
        set_config('request.jwt.claims', concat('{"role": "', ROLE, '", "sub": "', pseudo, '"}'), FALSE);
END
$$
LANGUAGE plpgsql;


/*
 Permet de revenir à son rôle normal (après un login_for_test)
 */
CREATE OR REPLACE FUNCTION auth.logout_for_test()
    RETURNS void
    AS $$
BEGIN
    PERFORM
        set_config('request.jwt.claims', '{}', FALSE);
    RESET ROLE;
END;
$$
LANGUAGE plpgsql;

GRANT EXECUTE ON FUNCTION auth.login_anonymously_for_test() TO anon;

GRANT EXECUTE ON FUNCTION auth.login_for_test(text) TO anon;

GRANT EXECUTE ON FUNCTION auth.logout_for_test() TO anon;

GRANT EXECUTE ON FUNCTION auth.pseudo() TO anon;

GRANT EXECUTE ON FUNCTION auth.role() TO anon;


/**************************************************************
 TESTS
 **************************************************************/
SELECT
    login('ben', 'ben');

-- OK
SELECT
    login('xxx', 'Password1,');

-- KO: user n'existe pas
SELECT
    login('ben', 'xxx');

-- KO: mauvais mot de passe
SELECT
    auth.login_anonymously_for_test();

SELECT
    CURRENT_USER,
    auth.pseudo(),
    auth.role();

SELECT
    auth.logout_for_test();

SELECT
    auth.login_for_test('ben');

SELECT
    CURRENT_USER,
    auth.pseudo(),
    auth.role();

SELECT
    auth.logout_for_test();

