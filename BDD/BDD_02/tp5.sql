set search_path TO bdge, dblivre;

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


a   => erreur (oeuvre, genre)
b   => oeuvre exist
c   => erreur trad (nom, prenom)
d   => trad exist
e   => erreur livre (titre, date_parution) 


Exemple:
1 0 1 1 1
a b c d e 


*/

DROP PROCEDURE IF EXISTS check_oeuvre_genre;
CREATE PROCEDURE check_oeuvre_genre(
    oeuvre_titre ttitre, oeuvre_genre tgenre, INOUT flags int
)
LANGUAGE plpgsql
AS
$$
    DECLARE oeuvre_record RECORD;
    BEGIN
        SELECT idoeuvre, genre INTO oeuvre_record FROM oeuvre AS o 
        WHERE LOWER(o.titre) = LOWER(oeuvre_titre);
        IF (oeuvre_record IS NOT NULL) THEN
            IF (oeuvre_record.genre <> oeuvre_genre) THEN
                flags = flags | 16;
                RAISE NOTICE 'couple (%, %) non valide', oeuvre_titre, oeuvre_genre;
            END IF;
        ELSE 
            flags = flags | 8;
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
        WHERE LOWER(te.nom) = LOWER(trad_nom) AND LOWER(te.prenom) = LOWER(trad_prenom);
        IF (traducteur = 1) THEN
            flags = flags | 2;
        ELSEIF (traducteur > 1) THEN
            flags = flags | 4;
            RAISE NOTICE 'trop de traducteur (%, %)', trad_prenom, trad_nom;
        END IF;
    END
$$;

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS check_livre_trad;
CREATE PROCEDURE check_livre_trad(
    livre_titre ttitre, livre_date date, trad_nom tnom, trad_prenom tprenom, INOUT flags int
)
LANGUAGE plpgsql
AS
$$
    DECLARE livre int;
    BEGIN
        SELECT COUNT(*) INTO livre FROM livre_paru AS lp
        JOIN traduit_par AS tp ON tp.numlivre = lp.idlivre
        JOIN traducteur_ecrivain AS te ON te.idtrad_ecriv = tp.numtrad
        WHERE LOWER(lp.titre) = LOWER(livre_titre) 
        AND lp.date_parution = livre_date
        AND LOWER(trad_nom) = LOWER(te.nom)
        AND LOWER(trad_prenom) = LOWER(te.prenom);
        IF (livre > 0) THEN
            flags = flags | 1;
            RAISE NOTICE '% % a deja traduit (%, %)', 
            trad_nom, trad_prenom, livre_titre, livre_date;
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
        CALL check_oeuvre_genre(oeuvre_titre, oeuvre_genre, flags);
        CALL check_traducteur(trad_nom, trad_prenom, flags);
        CALL check_livre_trad(livre_titre, livre_date, trad_nom, trad_prenom, flags); 
    END
$$;

------------------------------------------------------------------

DROP PROCEDURE IF EXISTS creer_oeuvre_et_lier_a_ecriv_1;
CREATE PROCEDURE creer_oeuvre_et_lier_a_ecriv_1(
    oeuvre_titre ttitre, oeuvre_genre tgenre
)
LANGUAGE plpgsql
AS
$$
    DECLARE id_oeuvre int;
    BEGIN
        INSERT INTO oeuvre (titre, genre) VALUES (oeuvre_titre, oeuvre_genre)
        RETURNING idoeuvre INTO id_oeuvre;
        INSERT INTO ecrit_par  (numOeuvre, numEcriv) VALUES (id_oeuvre, 1);
    END
$$;


------------------------------------------------------------------

DROP PROCEDURE IF EXISTS creer_livre_et_lier_trad_oeuvre;
CREATE PROCEDURE creer_livre_et_lier_trad_oeuvre(
    livre_titre ttitre, livre_date date, trad_nom tnom, 
    trad_prenom tprenom, oeuvre_titre ttitre, oeuvre_genre tgenre
)
LANGUAGE plpgsql
AS
$$
    DECLARE id_livre int;
    DECLARE id_oeuvre int;
    DECLARE id_trad int;
    BEGIN
        SELECT o.idoeuvre INTO id_oeuvre FROM oeuvre AS o 
        WHERE LOWER(o.titre) = LOWER(oeuvre_titre) 
        AND LOWER(o.genre) = LOWER(oeuvre_genre);

        SELECT te.idtrad_ecriv INTO id_trad FROM traducteur_ecrivain AS te
        WHERE LOWER(te.nom) = LOWER(trad_nom)
        AND LOWER(te.prenom) = LOWER(trad_prenom);

        INSERT INTO livre_paru (titre, date_parution, numoeuvre) 
        VALUES (livre_titre, livre_date, id_oeuvre)
        RETURNING idlivre INTO id_livre;
        
        INSERT INTO traduit_par (numlivre, numtrad) VALUES (id_livre, id_trad);
    END
$$;

------------------------------------------------------------------

------------------------------------------------------------------


DROP PROCEDURE IF EXISTS insert_data;
CREATE PROCEDURE insert_data(
    oeuvre_titre ttitre, oeuvre_genre tgenre, livre_titre ttitre, 
    livre_date date, trad_nom tnom, trad_prenom tprenom, INOUT flags int
)
LANGUAGE plpgsql
AS
$$
    BEGIN
        IF ((flags & 16 = 0) AND (flags & 4 = 0) AND (flags & 1 = 0)) THEN
            IF (flags & 8 = 0) THEN
                CALL creer_oeuvre_et_lier_a_ecriv_1(oeuvre_titre, oeuvre_genre);
            END IF;
            IF (flags & 2 = 0) THEN 
                INSERT INTO traducteur_ecrivain (nom, prenom) 
                VALUES (trad_nom, trad_prenom);
            END IF;
            CALL creer_livre_et_lier_trad_oeuvre(
                livre_titre, livre_date, trad_nom, trad_prenom, oeuvre_titre, oeuvre_genre
            );
        END IF;
    END
$$;

------------------------------------------------------------------

DO
LANGUAGE plpgsql
$$
    DECLARE
    oeuvre_titre ttitre  := 'Hamlet';
    oeuvre_genre tgenre  := 'Tragédie';
    livre_titre ttitre   := 'hamlet';
    livre_date date      := '2016/10/3'::DATE;
    trad_nom tnom        := 'harrisson';
    trad_prenom tprenom  := 'Bob';
    flags int            := 0;

    BEGIN
        CALL checks(
            oeuvre_titre, oeuvre_genre, livre_titre,
            livre_date, trad_nom, trad_prenom, flags
        );
                                                                                                                                                                                                                                                                                                                                                            
        RAISE NOTICE 'flags = %', flags::BIT(5);

        CALL insert_data(
            oeuvre_titre, oeuvre_genre, livre_titre, 
            livre_date, trad_nom, trad_prenom, flags
        );
    END
$$;

