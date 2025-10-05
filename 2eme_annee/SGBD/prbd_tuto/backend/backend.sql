SET search_path TO public;

DROP TABLE IF EXISTS word_pairs CASCADE;

CREATE TABLE word_pairs(
    id serial PRIMARY KEY,
    first_word varchar(50),
    second_word varchar(50)
);

CREATE TABLE favorites(
    pseudo varchar(128) NOT NULL REFERENCES users(pseudo),
    pair int NOT NULL REFERENCES word_pairs(id),
    PRIMARY KEY (pseudo, pair)
);

INSERT INTO word_pairs(first_word, second_word)
    VALUES ('blue', 'sky'),
('red', 'apple'),
('green', 'grass'),
('yellow', 'sun'),
('white', 'snow'),
('black', 'night'),
('purple', 'flower'),
('silver', 'spoon'),
('golden', 'ring'),
('brown', 'bear'),
('bright', 'star'),
('dark', 'cloud'),
('happy', 'day'),
('sad', 'song'),
('loud', 'noise'),
('soft', 'pillow'),
('fast', 'car'),
('slow', 'turtle'),
('strong', 'wind'),
('weak', 'signal'),
('warm', 'coffee'),
('cold', 'water'),
('sweet', 'dream'),
('bitter', 'truth'),
('fresh', 'air');

--------------------------
-- fonction qui récupère une paire de mots aléatoire
--------------------------
CREATE OR REPLACE FUNCTION get_random_pair()
    RETURNS word_pairs
    AS $$
DECLARE
    pair word_pairs;
BEGIN
    -- vérifie qu'un utilisateur est connecté
    PERFORM
        auth.check_logged();
    SELECT
        *
    FROM
        word_pairs
    ORDER BY
        random()
    LIMIT 1 INTO pair;
    RETURN pair;
END;
$$
LANGUAGE plpgsql
SECURITY DEFINER;

GRANT EXECUTE ON FUNCTION get_random_pair TO authenticated;

--------------------------
-- fonction qui bascule le statut "favori" d'une paire de mots pour l'utilisateur courant
--------------------------
CREATE OR REPLACE FUNCTION toggle_favorite(id int)
    RETURNS void
    AS $$
BEGIN
    -- vérifie qu'un utilisateur est connecté
    PERFORM
        auth.check_logged();
    -- vérifie que l'id existe
    IF NOT EXISTS(
        SELECT
            *
        FROM
            word_pairs p
        WHERE
            p.id = toggle_favorite.id) THEN
    RAISE EXCEPTION 'id % not found', id;
END IF;
    IF EXISTS(
        SELECT
            *
        FROM
            favorites f
        WHERE
            pair = toggle_favorite.id
            AND f.pseudo = auth.pseudo()) THEN
    DELETE FROM favorites f
    WHERE f.pseudo = auth.pseudo()
        AND pair = id;
ELSE
    INSERT INTO favorites(pseudo, pair)
        VALUES(auth.pseudo(), id);
END IF;
END;
$$
LANGUAGE plpgsql
SECURITY DEFINER;

GRANT EXECUTE ON FUNCTION toggle_favorite TO authenticated;

--------------------------
-- fonction qui retourne tous les favoris pour l'utilisateur courant
--------------------------
CREATE OR REPLACE FUNCTION get_favorites()
    RETURNS SETOF word_pairs
    AS $$
BEGIN
    -- vérifie qu'un utilisateur est connecté
    PERFORM
        auth.check_logged();
    RETURN query
    SELECT
        *
    FROM
        word_pairs w
    WHERE
        w.id IN(
            SELECT
                pair
            FROM
                favorites f
            WHERE
                f.pseudo = auth.pseudo())
    ORDER BY
        first_word,
        second_word;
END;
$$
LANGUAGE plpgsql
SECURITY DEFINER;

GRANT EXECUTE ON FUNCTION get_favorites TO authenticated;

-- tests
SELECT
    auth.login_for_test('ben');

SELECT
    get_random_pair();

SELECT
    get_random_pair();

SELECT
    toggle_favorite(1);

SELECT
    toggle_favorite(2);

SELECT
    toggle_favorite(99);

SELECT
    *
FROM
    get_favorites();

SELECT
    toggle_favorite(1);

SELECT
    toggle_favorite(2);

SELECT
    *
FROM
    get_favorites();

SELECT
    auth.logout_for_test();

