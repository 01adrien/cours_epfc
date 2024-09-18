/*
EX 1
Quel est le nom et le poids des personnes de moins de 32 ans
qui ont mangé un hamburger de bœuf de plus de 1000 calories ?
>> (Lisa 50)
*/

SELECT DISTINCT p.nom, p.poids FROM Mange AS m
JOIN Personne AS p ON p.id = m.id_p
JOIN Hamburger AS h ON h.id = m.id_h
WHERE p.age < 32 AND h.genre = 'Boeuf' AND h.calories > 1000;


/*
EX 2
Quel est le nom et l’âge des hommes de plus de 60 kg qui ont mangé 
un hamburger de poulet et qui lui ont donné une note strictement inférieure à 7 ?
>> (Tony 33 et Marc 60)
*/

SELECT DISTINCT p.nom, p.age FROM Mange AS m
JOIN Personne AS p ON p.id = m.id_p
JOIN Hamburger AS h ON h.id = m.id_h
WHERE p.poids > 60 AND h.genre = 'Poulet'
AND m.note < 7 AND p.sexe = 'M';


/*
EX 3
Quel est le nom des hamburgers et la moyenne des notes pour les 
hamburgers ayant été mangés par au moins 3 personnes différentes ?
>> (Bicky 6.75 et Bicky Poulet Cheese 5.66)
*/

SELECT DISTINCT h.nom, ROUND(AVG(m.note), 2) AS avg_note FROM Mange AS m
JOIN Hamburger AS h ON h.id = m.id_h
GROUP BY h.nom
HAVING COUNT(DISTINCT m.id_p) > 2;


/*
EX 4
Pour les hamburgers dont la moyenne des notes est supérieure ou égale à 6, 
quel est leur nom et le nombre de personnes différentes les ayant mangé ?
>> (Bicky 4, Bicky Poulet 2 et Mega Burger 1)
*/

SELECT DISTINCT h.nom, COUNT(DISTINCT m.id_p) AS count FROM Mange AS m
JOIN Hamburger AS h ON h.id = m.id_h
GROUP BY h.nom
HAVING AVG(m.note) >= 6;


/*
EX 5
Quel est le nom et le sexe des personnes qui ont mangé au moins 3 fois le même hamburger ?
>> (Lisa F)
*/

SELECT DISTINCT p.nom, p.sexe FROM Mange AS m
JOIN Personne AS p ON p.id = m.id_p
GROUP BY p.nom, p.sexe, m.id_h, p.id_p
HAVING COUNT(*) >= 3;



/*
EX 6
Quelles sont les dates où le même hamburger a été mangé au moins deux fois ?
>> (29/5 et 21/5)
*/

SELECT DISTINCT m.id_h, m.date_consommation FROM Mange AS m
GROUP BY m.date_consommation, m.id_h
HAVING COUNT(m.id_h) >= 2;


/*
EX 7
Quel est le nom des hamburgers qui ont été mangés par un homme de plus 
de 100 kg et qui n’ont jamais été mangés par une femme de plus de 30 ans ?
>> (Bicky Poulet Cheese)
*/


SELECT h.nom FROM Hamburger AS h WHERE h.id IN (
    SELECT m.id_h FROM Mange AS m
    JOIN Personne AS p ON p.id = m.id_p
    WHERE p.sexe = 'M' AND p.poids > 100
) AND h.id NOT IN (
    SELECT m.id_h FROM Mange AS m
    JOIN Personne AS p ON p.id = m.id_p
    WHERE p.sexe = 'F' AND p.age > 30
);


/*
EX 8
Quel est le nom des personnes qui ont mangé un hamburger de poulet et 
qui n’ont jamais mangé un hamburger de bœuf de moins de 1000 calories ?
>> (Tony, Lisa et Marc)
*/

SELECT p.nom FROM Personne AS p WHERE p.id IN (
    SELECT m.id_p FROM Mange AS m
    JOIN Hamburger AS h ON h.id = m.id_h
    WHERE h.genre = 'Poulet' 
) AND p.id NOT IN (
    SELECT m.id_p FROM Mange AS m
    JOIN Hamburger AS h ON h.id = m.id_h
    WHERE h.genre = 'Boeuf' AND h.calories < 1000
);


/*
EX 9
Quel est le nom des femmes qui ont mangé tous les hamburgers de poulet ?
>> (Lisa)
*/

SELECT DISTINCT p.nom FROM Mange AS m
JOIN Personne AS p ON p.id = m.id_p
JOIN Hamburger AS h on h.id = m.id_h
WHERE p.sexe = 'F' AND h.genre = 'Poulet'
GROUP BY p.nom, p.id
HAVING COUNT(DISTINCT m.id_h) = (
    SELECT COUNT(*) FROM Hamburger AS m
    WHERE m.genre = 'Poulet'
);


/*
EX 10
Quel est le nom des hamburgers de boeuf qui ont été mangés 
par toutes les personnes de plus de 80 kilos ?
>> (Bicky)
*/

SELECT DISTINCT h.nom FROM Mange AS m
JOIN Hamburger AS h ON h.id = m.id_h
JOIN Personne AS p ON p.id = m.id_p
WHERE p.poids > 80 AND h.genre = 'Boeuf'
GROUP BY h.nom
HAVING COUNT(DISTINCT(m.id_p)) = (
    SELECT COUNT(*) FROM Personne AS p
    WHERE p.poids > 80
);


/*
EX 11
Affichez tous les hamburgers sauf le plus calorique.
>> (Tous sauf le Giga Burger)
*/

SELECT h.nom FROM Hamburger AS h WHERE h.calories != (
    SELECT MAX(h.calories) FROM Hamburger AS h
);


/*
EX 12
Quel est l’homme le plus lourd qui a mangé un hamburger de poulet ?
>> ( P3 )
*/
--->
-----> A VERIFIER --->
--->
SELECT p.nom, p.id FROM Personne AS p WHERE p.poids = (
    SELECT MAX(p.poids) FROM Mange AS m
    JOIN Personne AS p ON p.id = m.id_p
    JOIN Hamburger AS h ON h.id = m.id_h
    WHERE p.sexe = 'M' AND h.genre = 'Poulet'
);


/*
EX 13
Quels sont les hamburgers qui ont été mangés par des hommes et des femmes ?
>> (H1, H2 et H4)
*/

SELECT m.id_h FROM Mange AS m 
JOIN Personne AS p ON p.id = m.id_p
GROUP BY m.id_h 
HAVING COUNT(DISTINCT p.sexe) = 2;


/*
EX 14
Qui a mangé le moins de hamburgers parmi ceux qui en ont mangé ?
>> (P1 et P2)
*/

SELECT m.id_p FROM Mange AS m
GROUP BY m.id_p
HAVING COUNT(*) <= ALL (
    SELECT COUNT(*) FROM Mange AS m
    GROUP BY m.id_p
);
