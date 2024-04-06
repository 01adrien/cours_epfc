set search_path TO dblivre;

-- EX 1 
/*
Modifier les procédures présentées dans la section Programmation modulaire afin que :

a. La procédure englobante recoive le nom et le prénom de l'auteur au lieu de son id. 
Pour l'exercice, vous pouvez considérer que la pair (nom, prenom) est unique dans la 
table traducteur_ecrivain.

b. Si l'écrivain n'existe pas dans la base de données, il faut le créer sinon il faut
utiliser son id existant pour le lier à la nouvelle oeuvre.

c. Faites-en sorte que le titre de l'oeuvre soit toujours stocké en majuscule.
*/

DROP PROCEDURE IF EXISTS creer_oeuvre;
CREATE PROCEDURE creer_oeuvre(
    titre ttitre, annee tannee, genre tgenre, langue tlangue, OUT newId int
    )
LANGUAGE plpgsql
AS
$$
    BEGIN
        INSERT INTO oeuvre (titre, annee, genre, langue) 
        VALUES (titre, annee, genre, langue) RETURNING idOeuvre INTO newId;
    END
$$;

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS lier_oeuvre_ecriv;
CREATE PROCEDURE lier_oeuvre_ecriv(idOeuvre int, idEcriv int)
LANGUAGE plpgsql
AS
$$
    BEGIN
        INSERT INTO ecrit_par (numOeuvre, numEcriv) VALUES (idOeuvre, idEcriv);
    END
$$;

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS ecr_existe;
CREATE PROCEDURE ecr_existe(ecr_nom tnom, ecr_prenom tprenom, OUT id int)
LANGUAGE plpgsql
AS
$$
    BEGIN
        SELECT te.idtrad_ecriv INTO id FROM traducteur_ecrivain AS te
        WHERE te.prenom = ecr_prenom AND te.nom = ecr_nom;
    END
$$;

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS creer_ecr;
CREATE PROCEDURE creer_ecr(ecr_nom tnom, ecr_prenom tprenom, OUT id int)
LANGUAGE plpgsql
AS
$$
    BEGIN
        INSERT INTO traducteur_ecrivain (nom, prenom) VALUES (ecr_nom, ecr_prenom)
        RETURNING idtrad_ecriv INTO id;
    END
$$;

--------------------------------------------------------------------

DROP PROCEDURE IF EXISTS creer_oeuvre_et_lier_a_ecriv;
CREATE PROCEDURE creer_oeuvre_et_lier_a_ecriv(
    titre ttitre, annee tannee, genre tgenre, langue tlangue, ecr_nom tnom, ecr_prenom tprenom
    )
LANGUAGE plpgsql
AS
$$
    DECLARE idOeuvre int;
    DECLARE idEcriv int;
    BEGIN
        CALL creer_oeuvre(titre, annee, genre, langue, idOeuvre);
        CALL ecr_existe(ecr_nom, ecr_prenom, idEcriv);
        IF (IS NULL idEcriv)
            CALL creer_ecr(ecr_nom, ecr_prenom, idEcriv);
        END IF;
        CALL lier_oeuvre_ecriv(idOeuvre, idEcriv);
    END
$$;

CALL creer_oeuvre_et_lier_a_ecriv('test titre oeuvre', 1850, 'Roman', 'En', 'LISKOV', 'Barbara');

------------------------------------------------------------------

DO
LANGUAGE plpgsql
$$
DECLARE id int;
BEGIN
    -- id := 0;
    CALL ecr_existe('LISKOV', 'Barbara', id);
    RAISE NOTICE 'id = %', id;
END
$$;