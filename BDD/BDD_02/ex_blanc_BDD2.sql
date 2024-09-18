set search_path TO bdge, ecole;

/*
Écrivez une contrainte de check qui empêche un étudiant de moins de 26 ans
dont le statut est sans emploi (SE) ou famille nombreuse (FN),
de payer un minerval de 800 euros ou plus. Utilisez la fonction age donnée ci-dessous
(dont la définition est dans le script, donc elle se trouve déjà définie dans la db).


a = -26 ans
b = SE || FN
c = minerval > 800

a => ( b => c )

!a && !( !b && c  )

age >= 26 || statut ! IN (SE, FN) || minerval < 800


- 26  |  SE || FN  |  > 800
- 26  |  SE || FN  |  < 800
- 26  |     NO     |  > 800
- 26  |     NO     |  < 800
+ 26  |  SE || FN  |  > 800
+ 26  |  SE || FN  |  < 800
+ 26  |     NO     |  > 800
+ 26  |     NO     |  < 800

 */
 ALTER TABLE etudiant
 ADD CONSTRAINT ex1
 CHECK (age(etudiant.ddn) >= 26 OR etudiant.statut NOT IN ('FN', 'SE') OR etudiant.minerval < 800);

SELECT  * FROM  etudiant;

INSERT INTO etudiant (nom, prenom, ddn, statut, minerval) VALUES ('jim', 'champ', '2000-01-01'::DATE, 'FN', 900); -- F
INSERT INTO etudiant (nom, prenom, ddn, statut, minerval) VALUES ('jim', 'champ', '2000-01-01'::DATE, 'SE', 400); -- V
INSERT INTO etudiant (nom, prenom, ddn, statut, minerval) VALUES ('jim', 'champ', '2000-01-01'::DATE, 'NO', 900); -- V
INSERT INTO etudiant (nom, prenom, ddn, statut, minerval) VALUES ('jim', 'champ', '2000-01-01'::DATE, 'NO', 400); -- V
INSERT INTO etudiant (nom, prenom, ddn, statut, minerval) VALUES ('jim', 'champ', '1990-01-01'::DATE, 'FN', 900); -- V
INSERT INTO etudiant (nom, prenom, ddn, statut, minerval) VALUES ('jim', 'champ', '1990-01-01'::DATE, 'SE', 400); -- V
INSERT INTO etudiant (nom, prenom, ddn, statut, minerval) VALUES ('jim', 'champ', '1990-01-01'::DATE, 'NO', 900); -- V
INSERT INTO etudiant (nom, prenom, ddn, statut, minerval) VALUES ('jim', 'champ', '1990-01-01'::DATE, 'NO', 400); -- V

SELECT  * FROM etudiant;
DELETE  FROM  etudiant WHERE etudiant.idetudiant > 4;


/*
Écrivez une fonction Moyenne Cotes qui reçoit en paramètres un titre de cours
et une date et qui affiche les données suivantes :
nom, prenom et moyenneCote
qui correspondent, respectivement, au nom et prénom d'un étudiant
et à la moyenne des cotes obtenues, par l'étudiant, aux interros
pour toutes les matières liées au cours dont le titre est passé en paramètre.
On prend en compte uniquement les interros se passant à partir de la date passée en paramètre
(dit autrement : on exclut donc les interros antérieures à la date passée en paramètre).
 */

SELECT * FROM etudiant;
SELECT * FROM interro;
SELECT * FROM matière;
SELECT * FROM cours;

INSERT INTO interro (idetudiant, idmatiere, dateinterro, exam, points)
VALUES (3, 6 , '2024-06-02'::DATE, false, 2);

CREATE OR REPLACE FUNCTION moyenne_cotes(titre_cours VARCHAR, date_debut DATE)
RETURNS TABLE(nom_etudiant VARCHAR, prenom_etudiant VARCHAR, moyenne INT) AS
$$
    SELECT e.nom, e.prenom, AVG(i.points) FROM etudiant AS e
    JOIN interro AS i ON i.idetudiant = e.idetudiant
    JOIN matière AS m ON m.idmatiere = i.idmatiere
    JOIN cours AS c ON c.idcours = m.idcours
    WHERE i.dateinterro >= date_debut AND LOWER(c.titre) = LOWER(titre_cours)
    GROUP BY e.idetudiant, e.nom, e.prenom ;
$$ LANGUAGE sql;

SELECT * FROM moyenne_cotes('anglais' , '2024-06-01'::DATE);


/*
Ecrire une procédure stockée preparer_exam qui reçoit en parametrises,
le titre d'un cours et une date et qui :
vérifie que le cours existe et est déterminant (remarque : on suppose que le
titre d'un cours est unique dans la base de données, il ne faut pas le vérifier).
si ce n'est pas le cas, elle déclenche une erreur et s'arrête sinon
elle crée une matière associée au cours avec, comme titre de matière EXAM_<titre_du_cours>
elle crée des interrogations de type examen à la date fournie en paramètre
pour chaque étudiant pour cette matière, et elle attribue la valeur NULL à la colonne point.
 */

DROP FUNCTION IF EXISTS valide_cours;
CREATE FUNCTION valide_cours(titre_cours VARCHAR)
RETURNS INTEGER AS
$$
    DECLARE id INTEGER;
    DECLARE det BOOLEAN;
    BEGIN
        SELECT c.idcours INTO id FROM cours AS c
        WHERE LOWER(c.titre) = LOWER(titre_cours);
        SELECT c.determinant INTO det FROM cours AS c
        WHERE LOWER(c.titre) = LOWER(titre_cours);
        IF (id IS NULL OR det = false) THEN
            id := 0;
        END IF;
        RETURN id ;
    END ;
$$ LANGUAGE plpgsql;

DROP PROCEDURE IF EXISTS preparer_exam;
CREATE PROCEDURE preparer_exam(titre_cours VARCHAR, date_exam DATE)
LANGUAGE plpgsql
AS
$$
    DECLARE
        id_cour INTEGER;
        id_matiere INTEGER;
    BEGIN
        SELECT valide_cours(titre_cours) INTO id_cour;
        IF (id_cour = 0) THEN
            RAISE EXCEPTION 'le cours % n''est pas valide', titre_cours;
        END IF;
        INSERT INTO matière (titre, idcours) VALUES (CONCAT('EXAM', titre_cours), id_cour)
        RETURNING idmatiere INTO id_matiere;
        INSERT INTO interro (idetudiant, idmatiere, dateinterro, exam, points)
            SELECT
                e.idetudiant, id_matiere, date_exam, true, NULL
            FROM etudiant AS e;
    END
$$;

SELECT valide_cours('geo');
SELECT * FROM cours;
DO
LANGUAGE plpgsql
$$
    BEGIN
        CALL preparer_exam('anglais', '2000-01-01');
    END
$$;

/*
La règle de gestion suivante doit toujours être respectée :
il ne peut exister qu'une seule date d'interro de type exam par matière.
De plus la matière doit être liée à un cours déterminant.
citez tous les événements (nom de table – opération SQL) qui pourraient mettre à mal cette règle.
écrivez le déclencheur qui concerne l'utilisation normale de la base de données,
c'est à dire qui ne concerne pas une opération qui voudrait « corriger » une erreur d'encodage.

ANALYSE

Tables ou il faut verifier
interro:
    - UPDATE => oui car on pourrait briser la regle ( ex: mettre la meme date qu'une interro exam/determinant ...
    - INSERT => oui car on ajoute une interro qui peut briser la regle
    - DELETE => non car on supprime une interro

matiere:
    - UPDATE => oui car si on la lie a un cour non determinant alors qu'elle a un exam
    - INSERT => non car on cree une nouvelle matiere
    - DELETE => non car on supprimen

cours :
    - UPDATE => oui car si on change pour non determinant alors qu'il ete deja lie a des exams + matieres
    - INSERT => non
    - DELETE => non

*/


-----------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS matieres_deja_exam;
CREATE OR REPLACE FUNCTION matieres_deja_exam(id_matiere INT)
RETURNS BOOLEAN AS
$$
        SELECT i.exam FROM interro AS i
        JOIN matière AS m ON m.idmatiere = i.idmatiere
        JOIN cours AS c ON c.idcours = m.idmatiere
        WHERE i.exam = TRUE AND i.idmatiere = id_matiere AND c.determinant = TRUE;
$$ LANGUAGE sql;



CREATE OR REPLACE FUNCTION interro_trig_fct()
    RETURNS TRIGGER AS
$$
    BEGIN
        IF tg_op IN ('UPDATE') THEN
            IF (new.exam = TRUE AND old.idmatiere <> new.idmatiere
                    AND matieres_deja_exam(new.idmatiere)) THEN
                RAISE EXCEPTION 'un exam existe deja pour cette matiere';
            END IF;
        END IF;
        IF tg_op IN ('INSERT') THEN
            IF (new.exam = TRUE AND matieres_deja_exam(new.idmatiere)) THEN
                RAISE EXCEPTION 'un exam existe deja pour cette matiere';
            END IF;
        END IF;
        return new;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER interro_trig
BEFORE INSERT OR UPDATE
ON interro
FOR EACH ROW
EXECUTE FUNCTION interro_trig_fct();

-----------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION matiere_trig_fct()
    RETURNS TRIGGER AS
$$
    BEGIN
        IF ( old.idmatiere <> new.idmatiere AND matieres_deja_exam(new.matiere)) THEN
            RAISE EXCEPTION 'un exam existe deja pour cette matiere';
        END IF;
        RETURN new;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER matiere_trig
BEFORE UPDATE
ON matière
FOR EACH ROW
EXECUTE FUNCTION matiere_trig_fct();

SELECT * FROM matière;
SELECT * FROM cours;
SELECT * FROM interro;
-----------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION cours_trig_fct()
    RETURNS TRIGGER AS
$$
    BEGIN

        RETURN new;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER cours_trig
BEFORE UPDATE
ON cours
FOR EACH ROW
EXECUTE FUNCTION cours_trig_fct();

-----------------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------------