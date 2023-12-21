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
