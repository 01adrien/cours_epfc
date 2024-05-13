
/*
Ecrivez une procédure creer_oeuvre_collective qui permet de créer une nouvelle œuvre « collective » 
en recevant en paramètre le titre de cette œuvre, l’année, le genre et la langue. 
Cette œuvre doit avoir les caractéristiques suivantes :

    • L’œuvre étant collective, elle doit être écrite par tous les traducteurs-écrivains 
    de la base de données qui ont déjà écrit une œuvre. Si le nombre de ces auteurs est 
    inférieur à 10, on inclura aussi les super-traducteurs, c'est-à-dire ceux qui ont fait 
    au moins deux traductions (attention de ne pas inclure deux fois le même auteur).

    • Elle doit paraître une fois sous le même titre que l’œuvre, dans la même langue et avoir 
    été publiée au 1er janvier de l’année de l’œuvre.

    • En outre, elle doit paraître une fois pour chaque langue différente de la langue de l’œuvre 
    et pour laquelle la base de données contient des traducteurs ayant déjà fait des 
    traductions dans cette langue. Ces livres traduits doivent porter le titre de 
    l’œuvre suivi d’un tiret et de la langue en question et avoir été publiés au 1er janvier de 
    l’année de l’œuvre. En outre, chacun de ces livres doit avoir été traduit par tous les 
    traducteurs ayant déjà traduit au moins un livre dans la langue correspondante.
*/

---------------------------------------------------------------------------

DROP FUNCTION IF EXISTS super_trad_ls;
CREATE FUNCTION super_trad_ls()
    RETURNS TABLE(idtrad_ecriv int) AS
$$
    SELECT tp.numtrad FROM traduit_par AS tp
    GROUP BY tp.numtrad HAVING COUNT(*) > 1;
$$ LANGUAGE SQL;

---------------------------------------------------------------------------

DROP FUNCTION IF EXISTS super_ecr_ls;
CREATE FUNCTION super_ecr_ls()
    RETURNS TABLE(idtrad_ecriv int) AS
$$
    SELECT te.idtrad_ecriv FROM traducteur_ecrivain AS te
    JOIN ecrit_par AS ep ON ep.numecriv = te.idtrad_ecriv
    GROUP BY te.idtrad_ecriv;
$$ LANGUAGE SQL;

---------------------------------------------------------------------------

DROP FUNCTION IF EXISTS super_trad_ecr_distinct_ls;
CREATE FUNCTION super_trad_ecr_distinct_ls()
    RETURNS TABLE(idtrad_ecriv int) AS
$$
    SELECT t1.idtrad_ecriv FROM super_trad_ls() AS t1 
    UNION DISTINCT
    SELECT t2.idtrad_ecriv FROM super_ecr_ls() AS t2;
$$ LANGUAGE SQL;

---------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS creer_oeuvre;
CREATE PROCEDURE creer_oeuvre(
    oeuvre_titre ttitre, oeuvre_annee tannee, oeuvre_genre tgenre, oeuvre_langue tlangue, OUT id int
) 
LANGUAGE plpgsql
AS
$$
    BEGIN
        INSERT INTO oeuvre (titre, annee, genre, langue) 
        VALUES (oeuvre_titre, oeuvre_annee, oeuvre_genre, oeuvre_langue) 
        RETURNING idoeuvre INTO id;
    END
$$;

---------------------------------------------------------------------------


DROP PROCEDURE IF EXISTS creer_livre;
CREATE PROCEDURE creer_livre(
    livre_titre ttitre, livre_langue tlangue, livre_date date
) 
LANGUAGE plpgsql
AS
$$
    BEGIN
        INSERT INTO livre_paru (titre, langue, date_parution) 
        VALUES (livre_titre, livre_langue, livre_date);
    END
$$;


---------------------------------------------------------------------------

DROP FUNCTION IF EXISTS langue_ls;
CREATE FUNCTION langue_ls(lang tlangue)
    RETURNS TABLE(langue tlangue) AS
$$
    SELECT DISTINCT lp.langue FROM livre_paru AS lp
    JOIN traduit_par AS tp ON tp.numlivre = lp.idlivre
    WHERE lp.langue IS NOT NULL AND lp.langue <> lang;
$$ LANGUAGE SQL;

---------------------------------------------------------------------------

DROP FUNCTION IF EXISTS trad_par_langue_ls;
CREATE FUNCTION trad_par_langue_ls(lang tlangue)
    RETURNS TABLE(idtrad_ecriv int) AS
$$
    SELECT DISTINCT te.idtrad_ecriv FROM traducteur_ecrivain AS te
    JOIN traduit_par AS tp ON tp.numtrad = te.idtrad_ecriv
    JOIN livre_paru AS lp ON lp.idlivre = tp.numlivre
    WHERE lp.langue = lang;
$$ LANGUAGE SQL;

---------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS creer_oeuvre_collective;
CREATE PROCEDURE creer_oeuvre_collective(
    oeuvre_titre ttitre, oeuvre_annee tannee, oeuvre_genre tgenre, oeuvre_langue tlangue
)
LANGUAGE plpgsql
AS
$$
    DECLARE ecr_num int;
    DECLARE oeuvre_id int;
    BEGIN
    
        INSERT INTO oeuvre (titre, annee, genre, langue) 
        VALUES (oeuvre_titre, oeuvre_annee, oeuvre_genre, oeuvre_langue) 
        RETURNING idoeuvre INTO oeuvre_id;

        SELECT COUNT(*) INTO ecr_num FROM super_ecr_ls();

        IF (ecr_num < 10) THEN
            INSERT INTO ecrit_par (numoeuvre, numecriv) VALUES
            (oeuvre_id, super_trad_ecr_distinct_ls());
        ELSE 
            INSERT INTO ecrit_par (numoeuvre, numecriv) VALUES
            (oeuvre_id, super_ecr_ls());
        END IF;

        INSERT INTO livre_paru (titre, langue, date_parution) VALUES
        (
            oeuvre_titre, 
            oeuvre_langue, 
            CONCAT('01-01-', oeuvre_annee)::DATE
        );

        INSERT INTO livre_paru (titre, langue, date_parution) VALUES
        (
            CONCAT(oeuvre_titre, '-', langue_ls(oeuvre_langue)), 
            langue_ls(oeuvre_langue), 
            CONCAT('01-01-', oeuvre_annee)::DATE
        );

        -- Creer un trigger pour ajouter des traducteur sur l'insert d'un livre  

        INSERT INTO traduit_par (numlivre, numtrad) VALUES
        (
            SELECT 
        )
    END
$$;


---------------------------------------------------------------------------

DO 
LANGUAGE plpgsql
$$
    DECLARE
    oeuvre_titre ttitre := 'test titre oeuvre';
    oeuvre_annee tannee := 1965;
    oeuvre_genre tgenre := 'Tragédie';
    oeuvre_langue tlangue := 'Fr';

    BEGIN
        CALL creer_oeuvre_collective(
            oeuvre_titre, oeuvre_annee, oeuvre_genre, oeuvre_langue
        );
    END
$$;


DROP PROCEDURE IF EXISTS clean;
CREATE PROCEDURE clean()
LANGUAGE plpgsql
AS
$$
    BEGIN
        DELETE FROM oeuvre WHERE idoeuvre > 23;
        DELETE FROM ecrit_par WHERE numoeuvre > 23;
        DELETE FROM livre_paru WHERE idlivre > 33;
    END
$$;

