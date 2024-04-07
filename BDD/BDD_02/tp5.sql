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

variable check int sur 4 bits
bit a 1 ou 0 si condition OK

Exemple:
1 0 1 1
a b c err

*/

DROP PROCEDURE IF EXISTS check_oeuvre_genre;
CREATE PROCEDURE check_oeuvre_genre(
    oeuvre_titre ttitre, oeuvre_genre tgenre, INOUT flags int
)
LANGUAGE plpgsql
AS
$$
    DECLARE oeuvre int;
    BEGIN
        SELECT COUNT(*) INTO oeuvre FROM oeuvre AS o
        WHERE o.titre = oeuvre_titre AND o.genre = oeuvre_genre;
        IF (oeuvre > 0) THEN
            flags = flags::BIT(4) | B'1000';
        ELSE 
            flags = flags::BIT(4) | B'0000';
        END IF;
    END
$$;

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS check_traducteur;
CREATE PROCEDURE check_traducteur(
    trad_nom tnom, trad_prenom tprenom, INOUT flags int
)
LANGUAGE plpgsql
AS
$$
    DECLARE traducteur int;
    BEGIN
        SELECT COUNT(*) INTO traducteur FROM traducteur_ecrivain AS te
        WHERE te.nom = trad_nom AND te.prenom = trad_prenom;
        IF (traducteur = 0) THEN
            flags = flags::BIT(4) | B'0000';
        ELSEIF (traducteur = 1) THEN
            flags = flags::BIT(4) | B'0100';
        ELSEIF (traducteur > 1) THEN
            flags = flags::BIT(4) | B'0001';
            RAISE NOTICE 'trop de traducteur ( %, % )', trad_prenom, trad_nom; 
        END IF;
    END
$$;

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS check_titre_date;
CREATE PROCEDURE check_titre_date(
    livre_titre ttitre, livre_date date, INOUT flags int
)
LANGUAGE plpgsql
AS
$$
    DECLARE livre int;
    BEGIN
        SELECT COUNT(*) INTO livre FROM livre_paru AS lp
        WHERE lp.titre = livre_titre AND lp.date_parution = livre_date;
        IF (livre > 0) THEN
            flags = flags::BIT(4) | B'0001';
            RAISE NOTICE 'couple (livre, date) deja existant ( %, % )', 
                livre_titre, livre_date;
        END IF;
    END
$$;

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS checks;
CREATE PROCEDURE checks(
    oeuvre_titre ttitre, oeuvre_genre tgenre, livre_titre ttitre, 
    livre_date date, trad_nom tnom, trad_prenom tprenom, INOUT flags int
)
LANGUAGE plpgsql
AS
$$
    BEGIN
        RAISE NOTICE 'avant checks => flags = %', flags::BIT(4);
        -- check b
        CALL check_oeuvre_genre(oeuvre_titre, oeuvre_genre, flags);
        RAISE NOTICE 'apres check b => flags = %', flags::BIT(4);
        -- check c
        CALL check_traducteur(trad_nom, trad_prenom, flags);
        RAISE NOTICE 'apres check c => flags = %', flags::BIT(4);
        -- check d
        CALL check_titre_date(livre_titre, livre_date, flags);
        RAISE NOTICE 'apres check d => flags = %', flags::BIT(4);
    END
$$;


DROP PROCEDURE IF EXISTS insert_data;
CREATE PROCEDURE insert_data(
    oeuvre_titre ttitre, oeuvre_genre tgenre, livre_titre ttitre, 
    livre_date tannee, trad_nom tnom, trad_prenom tprenom, INOUT flags int
)
LANGUAGE plpgsql
AS
$$
    BEGIN
    END;
$$;

------------------------------------------------------------------

DO
LANGUAGE plpgsql
$$
    DECLARE 
    oeuvre_titre ttitre := 'Hamlet';
    oeuvre_genre tgenre := 'Tragédie';
    livre_titre ttitre := 'test';
    livre_date date := '1950/01/01'::DATE;
    trad_nom tnom := 'DUPOND';
    trad_prenom tprenom := 'Jason';
    flags int := B'0000' ;

    BEGIN
        CALL checks(
            oeuvre_titre, oeuvre_genre, livre_titre,
            livre_date, trad_nom, trad_prenom, flags
            );
        IF ((flags & 1) = 0) THEN
            -- insert_data(
            --     oeuvre_titre, oeuvre_genre, livre_titre, 
            --     livre_date, trad_nom, trad_prenom, flags
            -- );
        ELSE 
            RAISE NOTICE 'informations invalides';
        END IF;
    END
$$;

