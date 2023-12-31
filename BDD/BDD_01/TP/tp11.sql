/*
EX 1
On souhaite obtenir les noms de fournisseurs 
de Londres qui ont effectué au moins une livraison dont la
quantité de pièces est supérieur à 500 unités.
*/

SELECT S.SNAME FROM S WHERE EXISTS (
    SELECT * FROM SPJ 
    WHERE S.ID_S = SPJ.ID_S AND SPJ.QTY > 500
);


/*
EX 2
On souhaite connaître les noms de projets 
pour lesquels on a fourni au total plus de 1000 pièces.
*/

SELECT J.JNAME FROM J WHERE EXISTS (
    SELECT SPJ.ID_J FROM SPJ
    WHERE SPJ.ID_J = J.ID_J 
    GROUP BY SPJ.ID_J
    HAVING SUM(SPJ.QTY) > 1000
);


/*
EX 3
On souhaite obtenir toutes les villes pour
lesquelles au moins un fournisseur, une pièce ou un projet est situé.
*/

SELECT S.CITY FROM S
UNION 
SELECT J.CITY FROM J
UNION
SELECT P.CITY FROM P;


/*
EX 4
Quels sont les identifiants de fournisseurs qui ne livrent aucune pièce bleue ?
*/

SELECT S.ID_S FROM S WHERE NOT EXISTS (
    SELECT * FROM SPJ 
    WHERE SPJ.ID_S = S.ID_S 
    AND SPJ.ID_P IN (
        SELECT P.ID_P FROM P 
        WHERE P.COLOR = 'Blue'
    )
);


/*
EX 5
On souhaite connaître le nombre de livraisons de moins de
350 unités pour l’ensemble des fournisseurs qui
n’ont jamais fourni à Paris.
*/

SELECT COUNT(*) FROM SPJ WHERE SPJ.QTY < 350
AND NOT EXISTS (
    SELECT * FROM SPJ AS SPJ1
    WHERE SPJ1.ID_S = SPJ.ID_S
    AND SPJ1.ID_J IN (
        SELECT J.ID_J FROM J 
        WHERE J.CITY = 'Paris' 
    )
);

SELECT COUNT(*) FROM SPJ WHERE SPJ.QTY < 350
AND NOT EXISTS (
    SELECT * FROM SPJ AS SPJ1
    JOIN J ON J.ID_J = SPJ1.ID_J
    WHERE SPJ1.ID_S = SPJ.ID_S
    AND J.CITY = 'Paris' 
);

/*
EX 6
On souhaite connaître les fournisseurs qui n’ont jamais fourni 
plus de 650 pièces identiques au total de toutes leurs livraisons. 
(C’est à dire ceux dont aucune pièce de leur stock n’a baissé de 650 unités) 
>> (S3 et S4).
*/

SELECT s.ID_S, s.SNAME FROM s WHERE NOT EXISTS (
    SELECT spj.ID_S FROM spj
    WHERE s.ID_S = spj.ID_S
    GROUP BY spj.ID_S, spj.ID_P
    HAVING SUM(spj.QTY) > 650
);


/*
EX 7
On souhaite connaître les fournisseurs qui ont fait au minimum 
4 livraisons représentant au moins trois pièces différentes (S5)
*/

SELECT s1.ID_S FROM spj AS s1 WHERE EXISTS (
    SELECT * FROM spj
    WHERE s1.ID_S = spj.ID_S
    GROUP BY spj.ID_S
    HAVING COUNT(DISTINCT spj.ID_P) > 2
) GROUP BY s1.ID_S
HAVING COUNT(*) > 3;

