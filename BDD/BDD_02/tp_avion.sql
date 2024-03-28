

-- EX 1
-- Écrivez une contrainte de check vérifiant qu'un avion ayant 
-- plus de 200 places possède une taille supérieure à 60 m et au moins 2 réacteurs.


nb_places > 200  => A
taille > 60      => B
nb_reacteurs > 1 => C

 
A -> (B -> C)

!( A & !(B & C))
!(A & (B || C))
!A || !(B || C)
!A || B && C


ALTER TABLE avion 
DROP CONSTRAINT IF EXISTS c_ex1;
ALTER TABLE avion 
ADD CONSTRAINT c_ex1
CHECK (
    NOT nb_places > 200 AND (taille < 60 AND nb_reacteurs < 2)
);
    -- nb_places <= 200 OR taille > 60 AND nb_reacteurs > 1

INSERT INTO avion (noma, taille, nb_places, nb_reacteurs) VALUES ('A', 201, 61, 1);
INSERT INTO avion (noma, taille, nb_places, nb_reacteurs) VALUES ('B', 201, 61, 2);
INSERT INTO avion (noma, taille, nb_places, nb_reacteurs) VALUES ('C', 201, 59, 1); -- F
INSERT INTO avion (noma, taille, nb_places, nb_reacteurs) VALUES ('D', 201, 59, 2);
INSERT INTO avion (noma, taille, nb_places, nb_reacteurs) VALUES ('E', 199, 61, 1);
INSERT INTO avion (noma, taille, nb_places, nb_reacteurs) VALUES ('F', 199, 61, 2);
INSERT INTO avion (noma, taille, nb_places, nb_reacteurs) VALUES ('G', 199, 59, 1);
INSERT INTO avion (noma, taille, nb_places, nb_reacteurs) VALUES ('H', 199, 59, 2);

DELETE FROM avion WHERE ida > 6;