set search_path = bdge, bdlivre;

-- EX 1
-- Rajoutez la valeur par défaut INCONNU pour l'attribut Titre de la table oeuvre.

ALTER TABLE bdlivre.oeuvre
ALTER COLUMN titre
SET DEFAULT 'INCONNU';

-- EX 2
-- Deux oeuvres ne peuvent avoir le même titre la même année.

ALTER TABLE bdlivre.oeuvre
ADD CONSTRAINT year_title
UNIQUE (titre, annee);

-- EX 3
-- Aucun livre (imprimé) ne peut remonter au delà de 1434 (découverte de l'imprimerie).

ALTER TABLE bdlivre.livre_paru
ADD CONSTRAINT ck_impr
CHECK ( !(date_parution < 1434) );

-- EX 4
-- Les genres des oeuvres ne peuvent être que roman, poésie, musique, tragédie, essai, biographie, informatique, mystère.

ALTER TABLE bdlivre.oeuvre
ADD CONSTRAINT ck_genre
CHECK ( genre IN ('Roman', 'Poesie', 'Musique', 'Tragédie', 'Essai', 'Informatique', 'Mystère') );

-- EX 5
-- La date de décès de tQ
-- individu doit être postérieure à sa date de naissance.

ALTER TABLE bdlivre.traducteur_ecrivain
ADD CONSTRAINT ck_date
CHECK ( ddd > ddn );

-- EX 6
-- Les tragédies doivent obligatoirement avoir été écrites après l'année 1600

ALTER TABLE bdlivre.oeuvre
ADD CONSTRAINT ck_trag
CHECK ( NOT (genre = 'Tragédie' AND annee < 1600) );
-- ALT avec implication (genre = tragegie => year > 1600)
ALTER TABLE bdlivre.oeuvre
ADD CONSTRAINT ck_trag
CHECK (genre <> 'Tragédie' OR annee > 1600);


-- EX 7
-- Les livres édités chez "Gallimard" ne peuvent pas avoir plus de 300 pages

ALTER TABLE bdlivre.livre_paru
ADD CONSTRAINT ck_gall
CHECK ( editeur <> 'Gallimard' OR nb_pages <= 300) ;

-- EX 8
-- Les livres édités chez "Pearson" doivent, s’ils sont parus à partir de l’an 2000, contenir au moins 1000 pages.
/*

A : editeur       = Pearson
B : date_parution = 2000
C : nb_pages      = 1000

  A  |  B  |  C  |  (A -> (B -> C))  | R |
  1  |  1  |  1  |     1   ->   1    | 1 |
  1  |  1  |  0  |     1   ->   0    | 0 |
  1  |  0  |  1  |     1   ->   1    | 1 |
  1  |  0  |  0  |     1   ->   1    | 1 |
  0  |  1  |  1  |     0   ->   1    | 1 |
  0  |  1  |  0  |     0   ->   0    | 1 |
  0  |  0  |  1  |     0   ->   1    | 1 |
  0  |  0  |  0  |     0   ->   1    | 1 |

!(A . B . !C)

!A + !B + C
*/

ALTER TABLE bdlivre.livre_paru
ADD CONSTRAINT ck_pear
CHECK ( NOT editeur = 'Pearson' OR NOT EXTRACT(YEAR FROM date_parution) > 1999 OR nb_pages > 999 );

INSERT INTO bdlivre.livre_paru(editeur, date_parution, nb_pages) VALUES ('Pearson', '01/01/2000'::DATE , 1500); -- OK
INSERT INTO bdlivre.livre_paru(editeur, date_parution, nb_pages) VALUES ('Pearson', '01/01/2000'::DATE, 850);   -- PAS OK
INSERT INTO bdlivre.livre_paru(editeur, date_parution, nb_pages) VALUES ('Pearson', '01/01/1995'::DATE, 1541);  -- OK
INSERT INTO bdlivre.livre_paru(editeur, date_parution, nb_pages) VALUES ('Pearson', '01/01/1950'::DATE, 451);   -- OK
INSERT INTO bdlivre.livre_paru(editeur, date_parution, nb_pages) VALUES ('Pauvert' , '01/01/2020'::DATE, 1424);  -- OK
INSERT INTO bdlivre.livre_paru(editeur, date_parution, nb_pages) VALUES ('Pauvert', '01/01/2024'::DATE, 800);   -- OK
INSERT INTO bdlivre.livre_paru(editeur, date_parution, nb_pages) VALUES ('Pauvert', '01/01/1950'::DATE, 1632);  -- OK
INSERT INTO bdlivre.livre_paru(editeur, date_parution, nb_pages) VALUES ('Pauvert', '01/01/1950'::DATE, 400);   -- OK

SELECT * FROM bdlivre.livre_paru;