/*
EX 1
On souhaite obtenir les noms de fournisseurs de Londres
qui ont effectué au moins une livraison dont la
quantité de pièces est supérieur à 500 unités.
*/

SELECT s.sname FROM s WHERE EXISTS (
    SELECT * FROM spj WHERE s.id_s = spj.id_s
    AND spj.qty > 500 AND s.city = 'London'
);

/*
EX 2
On souhaite connaître les noms de projets pour
lesquels on a fourni au total plus de 1000 pièces.
>> ( Display, Console, RAID, Tape )
*/

SELECT j.jname FROM j WHERE EXISTS (
    SELECT spj.id_j FROM spj WHERE j.id_j = spj.id_j
    GROUP BY spj.id_j HAVING SUM(spj.qty) > 1000
);


/*
EX 3
On souhaite obtenir toutes les villes pour 
lesquelles au moins un fournisseur, une pièce ou un projet est situé.
>> ( London, Paris, Athens, Rome, Oslo )
*/

SELECT p.city FROM p 
UNION 
SELECT s.city FROM s
UNION 
SELECT j.city FROM j
;


/*
EX 4
Quels sont les identifiants de fournisseurs qui ne livrent aucune pièce bleue ?
>> ( S1, S4 )
*/

SELECT s.id_s FROM s WHERE NOT EXISTS (
    SELECT * FROM spj WHERE s.id_s = spj.id_s AND spj.id_p IN (
        SELECT p.id_p FROM p WHERE p.color = 'Blue'
    )
);


/*
EX 5
On souhaite connaître le nombre de livraisons de moins
de 350 unités pour l’ensemble des fournisseurs qui n’ont jamais fourni à Paris.
>> ( 8 )
*/

SELECT COUNT(*) FROM spj AS s1 WHERE NOT EXISTS (
    SELECT * FROM spj WHERE spj.id_j = s1.id_j
    AND spj.id_j IN (
        SELECT j.id_j FROM j WHERE j.city = 'Paris'
    )
) AND s1.qty < 350;


SELECT COUNT(*) FROM spj
JOIN j ON j.id_j = spj.id_j
WHERE (j.city <> 'Paris' AND spj.qty < 350);


/*
EX 6
On souhaite connaître les fournisseurs qui n’ont jamais fourni 
plus de 650 pièces identiques au total de toutes leurs livraisons. 
(C’est à dire ceux dont aucune pièce de leur stock n’a baissé de 650 unités).
>> ( S3, S4 )
*/

SELECT s.id_s FROM s WHERE NOT EXISTS (
    SELECT * FROM spj WHERE spj.id_s = s.id_s
    GROUP BY spj.id_p
    HAVING SUM(spj.qty) > 650
);

/*
EX 7 
On souhaite connaître les fournisseurs qui ont fait au minimum 4 livraisons
représentant au moins trois pièces différentes.
>> ( S5 )
*/

SELECT s.id_s FROM s WHERE EXISTS (
    SELECT * FROM spj WHERE spj.id_s = s.id_s
    GROUP BY spj.id_s
    HAVING COUNT(*) > 3 AND COUNT(DISTINCT(spj.id_p)) > 2
);

/*
EX 8
On souhaite les fournisseurs qui fournissent au moins dans 
trois villes différentes et dont les pièces qu’ils
fournissent proviennent d’au moins deux villes différentes.
>> ( S2, S5 )
*/

SELECT DISTINCT spj.id_s FROM spj
JOIN p ON p.id_p = spj.id_p
JOIN j ON j.id_j = spj.id_j
GROUP BY spj.id_s
HAVING (
    COUNT(DISTINCT(j.city)) > 2
    AND COUNT(DISTINCT(p.city)) > 1
);




