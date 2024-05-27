-- EX 1
-- Ecrire des déclencheurs de manière à stocker directement en majuscules
-- les titres des livres et des oeuvres quelle que soit la manière d'entrer 
-- le string au sein des commandes insert ou update.

-- Trigger sur insert et update
-- 2 trigger => 1 sur la table livre_paru et un sur la table oeuvre

CREATE OR REPLACE FUNCTION titre_uppercase() 
    RETURNS TRIGGER AS
$$
    BEGIN
        new.titre = UPPER(new.titre);
        RETURN new;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER titre_uppercase_livre
BEFORE INSERT OR UPDATE
ON livre_paru
FOR EACH ROW
EXECUTE FUNCTION titre_uppercase();


CREATE OR REPLACE TRIGGER titre_uppercase_oeuvre
BEFORE INSERT OR UPDATE
ON oeuvre
FOR EACH ROW
EXECUTE FUNCTION titre_uppercase();


-- EX 2
-- Ecrire un (des) déclencheur(s) qui empêche(nt) d'avoir dans la BD des personnes 
-- ayant écrit plus de 2 oeuvres et étant traducteur d'au moins un livre.

-- TRIGGER BEFORE sur INSERT et UPDATE pour la table ecrit par et traduit par


DROP FUNCTION IF EXISTS is_limit;
CREATE FUNCTION is_limit(id int)
    RETURNS boolean AS
$$
    SELECT id IN (
        SELECT DISTINCT te.idtrad_ecriv FROM traducteur_ecrivain AS te
        WHERE te.idtrad_ecriv IN (
            SELECT tp.numtrad FROM traduit_par AS tp
        ) AND te.idtrad_ecriv IN (
            SELECT ep.numecriv FROM ecrit_par AS ep
            GROUP BY ep.numecriv
            HAVING COUNT(ep.numecriv) >= 2
        )
    ); 
$$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION check_limit_ecr_fct()
    RETURNS TRIGGER AS 
$$
    BEGIN
        IF (is_limit(new.numecriv)) THEN
            RAISE EXCEPTION 'ecrivain ayant ecrit 2 livre et au moins traduit 1';
        END IF;
        return new;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_limit_trad_fct()
    RETURNS TRIGGER AS 
$$
    BEGIN
        IF (is_limit(new.numtrad)) THEN
            RAISE EXCEPTION 'traducteur ayant ecrit 2 livre et au moins traduit 1';
        END IF;
        return new;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_limit
BEFORE  INSERT OR UPDATE
ON ecrit_par
FOR EACH ROW 
EXECUTE FUNCTION check_limit_ecr_fct();

CREATE OR REPLACE TRIGGER check_limit
BEFORE  INSERT OR UPDATE
ON traduit_par
FOR EACH ROW 
EXECUTE FUNCTION check_limit_trad_fct();

INSERT INTO ecrit_par (numoeuvre, numecriv) VALUES (2 , 4);
INSERT INTO traduit_par (numlivre, numtrad) VALUES (6, 1);
UPDATE traduit_par SET numtrad = 23 WHERE numlivre = 1;


-- EX 3
-- Ecrire un (des) déclencheur(s) qui empêche(nt) une oeuvre de dater 
-- d'avant la date de naissance d'un quelconque de ses écrivains.

-- TRIGGER BEFORE EACH sur UPDATE INSERT ecrit_par 
-- TRIGGER BEFORE EACH sur UPDATE traducteur_ecrivain
-- TRIGGER BEFORE EACH sur UPDATE oeuvre

CREATE OR REPLACE FUNCTION ecr_ddn(ecr_id int)
    RETURNS tannee AS 
$$
    DECLARE ddn date;
    BEGIN
        SELECT te.ddn INTO ddn FROM traducteur_ecrivain AS te
        WHERE te.idtrad_ecriv = ecr_id;
        RETURN EXTRACT(YEAR FROM ddn);
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION oeuvre_date(oeuvre_id int)
    RETURNS tannee AS 
$$
    DECLARE d tannee;
    BEGIN
        SELECT o.annee INTO d FROM oeuvre AS o WHERE o.idoeuvre = oeuvre_id;
        RETURN d;
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION avant_naissance_ep_fct() 
    RETURNS TRIGGER AS
$$
    DECLARE
    BEGIN
        IF (oeuvre_date(new.numoeuvre) < ecr_ddn(new.numecriv)) THEN
            RAISE EXCEPTION 'date oeuvre < ddn auteur';
        END IF ;
        RETURN new;
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER avant_naissance_ep
BEFORE INSERT OR UPDATE
ON ecrit_par
FOR EACH ROW
EXECUTE FUNCTION avant_naissance_ep_fct();

CREATE OR REPLACE FUNCTION avant_naissance_te_fct() 
    RETURNS TRIGGER AS
$$
    BEGIN
        IF (EXTRACT(YEAR FROM new.ddn) > ANY ( 
                SELECT o.annee FROM oeuvre AS o
                JOIN ecrit_par AS ep ON ep.numoeuvre = o.idoeuvre
                WHERE ep.numecriv = old.idtrad_ecriv )
        ) THEN
            RAISE EXCEPTION 'new ddn auteur > any date oeuvre';
        END IF ;
        RETURN new;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER avant_naissance_te
BEFORE UPDATE
ON traducteur_ecrivain
FOR EACH ROW
EXECUTE FUNCTION avant_naissance_te_fct();


CREATE OR REPLACE FUNCTION avant_naissance_o_fct() 
    RETURNS TRIGGER AS
$$
    BEGIN
        IF (new.annee < ANY (
            SELECT EXTRACT(YEAR FROM te.ddn) FROM traducteur_ecrivain AS te
            JOIN ecrit_par AS ep ON ep.numecriv = te.idtrad_ecriv
            WHERE ep.numoeuvre = old.idoeuvre)
        ) THEN
            RAISE EXCEPTION 'date oeuvre < any ddn';
        END IF;
        RETURN new;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER avant_naissance_o
BEFORE UPDATE
ON oeuvre
FOR EACH ROW
EXECUTE FUNCTION avant_naissance_o_fct();


INSERT INTO ecrit_par (numOeuvre, numecriv) VALUES (2, 9);
UPDATE ecrit_par SET numecriv = 1 WHERE numoeuvre = 2;


UPDATE traducteur_ecrivain SET ddn = '01/01/1900'::DATE WHERE idtrad_ecriv = 3;


-- EX 4
-- Ecrire un (des) déclencheur(s) qui fait(font) en sorte qu'un livre ne peut faire l'objet 
-- d'une traduction dans la même langue que l’oeuvre liée à ce livre.

-- TRIGGER BEFORE EACH sur UPDATE INSERT livre-paru 
-- TRIGGER BEFORE EACH sur UPDATE oeuvre

CREATE OR REPLACE FUNCTION trad_lp_fct() 
    RETURNS TRIGGER AS
$$
    DECLARE 
        oeuvre_lang tlangue;
    BEGIN
        SELECT o.langue INTO oeuvre_lang
        FROM oeuvre AS o WHERE o.idoeuvre = new.numoeuvre;
        IF (oeuvre_lang = new.langue) THEN
            RAISE EXCEPTION 'Un livre ne peut etre traduit dans la langue originale';
        END IF;
        RETURN new;
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER trad_lp
BEFORE UPDATE OR INSERT
ON livre_paru
FOR EACH ROW
EXECUTE FUNCTION trad_lp_fct();

INSERT INTO livre_paru (titre, langue, numoeuvre) VALUES ('test', 'En', 3);
UPDATE livre_paru SET langue = 'Gr' WHERE idlivre = 2;


CREATE OR REPLACE FUNCTION trad_o_fct() 
    RETURNS TRIGGER AS
$$
    BEGIN
        IF (new.langue = ANY (
            SELECT lp.langue FROM livre_paru AS lp
            WHERE lp.numoeuvre = old.idoeuvre)
        ) THEN 
            RAISE EXCEPTION 'livre deja traduit dans cette langue';
        END IF;
        return null;
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER trad_o
BEFORE UPDATE 
ON oeuvre
FOR EACH ROW
EXECUTE FUNCTION trad_o_fct();

UPDATE oeuvre SET langue = 'Fr' WHERE idoeuvre = 3;