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
        IF (idEcriv IS NULL) THEN
            CALL creer_ecr(ecr_nom, ecr_prenom, idEcriv);
        END IF;
        CALL lier_oeuvre_ecriv(idOeuvre, idEcriv);
    END
$$;

CALL creer_oeuvre_et_lier_a_ecriv('test oeuvre 2', 1875, 'Roman', 'En', 'HARRIS', 'Bob');

------------------------------------------------------------------

DO
LANGUAGE plpgsql
$$
DECLARE id int;
BEGIN
    -- id := 0;
    CALL creer_ecr('DUPOND', 'Jason', id);
    RAISE NOTICE 'id = %', id;
END
$$;


------------------------------------------------------------------
------------------------------------------------------------------

/*
EX 2


Soit la problématique suivante : Créer une procédure qui reçoit le nom et le genre d'une oeuvre, 
le titre d'un livre et sa date d'édition, le nom et le prénom du traducteur du livre. 
Vous devrez répondre aux exigences suivantes :

a. Respecter les principes de la programmation modulaire

b. Vérifier que l'oeuvre existe et qu'elle est associée au genre fourni en paramètre. 
Si aucune oeuvre n'a le titre fourni en paramètre, 
la procédure la créera et la liera à l'auteur qui a l'idtrad_ecriv numéro 1. 
Si le couple (titre, genre) n'est pas bon, la procédure devra renvoyer un 
message d'erreur cohérent avec l'erreur rencontrée.

c. Vérifier que le traducteur existe. Si ce n'est pas le cas, la procédure le créera. 
S'il existe plusieurs entrées dans la table traducteur_ecrivain avec 
le couple (nom, prenom) fourni en paramètre, la procédure devra renvoyer un message d'erreur 
cohérent avec l'erreur rencontrée.

d. Insérer le livre fourni en paramètre sauf s'il existe déjà un couple (titre, date_parution) 
associé au traducteur fourni en paramètre. 
Un message d'erreur cohérent doit être renvoyé dans ce dernier cas.

e. Lier le livre au traducteur fourni en paramètre ainsi qu'à l'oeuvre fournie en paramètre.

f. Votre procédure doit renvoyer toutes les erreurs rencontrées et, à la moindre erreur, 
ne faire aucune modification de la base de données.

*/

DROP PROCEDURE IF EXISTS check_oeuvre_et_genre;
CREATE PROCEDURE check_oeuvre_et_genre(
    oeuvre_titre ttitre, oeuvre_genre tgenre, INOUT oeuvre_exist boolean
)
LANGUAGE plpgsql
AS
$$
    DECLARE oeuvre_ok int;
    BEGIN
        SELECT * INTO oeuvre_ok FROM oeuvre AS o
        WHERE o.titre = oeuvre_titre AND o.genre = oeuvre_genre;
        IF (oeuvre_ok IS NULL) THEN
            oeuvre_exist = FALSE;
        ELSE 
            oeuvre_exist = TRUE;
        END IF;
    END
$$;

------------------------------------------------------------------

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS checks;
CREATE PROCEDURE checks(
    oeuvre_titre ttitre, oeuvre_genre tgenre, livre_titre ttitre, 
    livre_edit_date tannee, trad_nom tnom, trad_prenom tprenom, OUT err int, OUT oeuvre_exist boolean 
)
LANGUAGE plpgsql
AS
$$
    BEGIN
        err := 0;
        CALL check_oeuvre_et_genre(oeuvre_titre, oeuvre_genre, oeuvre_exist);
        IF (err <> 0) THEN
            RAISE NOTICE 'Couple (oeuvre, genre) non valide.';
        -- ELSE
        -- CALL 
        END IF;
        -- RETURNING 1 INTO err;
        RAISE NOTICE 'check() => err = %', err;
    END
$$;


------------------------------------------------------------------

DO
LANGUAGE plpgsql
$$
    DECLARE err int;
    DECLARE oeuvre_exist boolean;
    BEGIN
        CALL checks('Hamlet', 'Tragédie', 'tesst','1950', 'HARRIS', 'Bob', err, oeuvre_exist);
        RAISE NOTICE 'main() => err = %', oeuvre_exist;
        IF (err = 0) THEN
            RAISE NOTICE 'CHECKS OK.';
        END IF;
    END
$$;