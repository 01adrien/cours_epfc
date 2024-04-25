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

-- TRIGGER sur INSERT et UPDATE pour la table ecrit par

CREATE OR REPLACE FUNCTION check_oeuvre_limit()
    RETURNS TRIGGER AS 
$$
    DECLARE oeuvres_count int;
    BEGIN
        SELECT COUNT(*) INTO oeuvres_count FROM oeuvre AS o
        JOIN ecrit_par AS ep ON ep.numoeuvre = o.idoeuvre
        JOIN traducteur_ecrivain AS te ON te.idtrad_ecriv = ep.numecriv
        JOIN traduit_par AS td ON td.numtrad = te.idtrad_ecriv
        WHERE new.
    END;
$$ LANGUAGE plpgsql;