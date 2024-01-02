/*
EX 1
On souhaite les noms des pièces qui sont fournies,
lors d'une livraison, en 500 unités.
>> (Screw, Cam, Cog)
*/

SELECT DISTINCT p.pname FROM p WHERE p.id_p IN (
    SELECT spj.id_p FROM spj WHERE spj.qty = 500 
);

/*
EX 2
On souhaite connaître les noms des pièces fournies
par le fournisseur S2.
>> (Screw, Cam)
*/

SELECT p.pname FROM p WHERE p.id_p IN (
    SELECT spj.id_p FROM spj WHERE spj.id_s = 'S2'
);

/*
EX 3
On souhaite connaître les noms des fournisseurs de la pièce P3. 
>> (Jones, Blake, Adams)
*/

SELECT s.sname FROM s WHERE s.id_s IN (
    SELECT spj.id_s FROM spj WHERE spj.id_p = 'P3'
);

/*
EX 4
On souhaite connaître les noms des fournisseurs 
d'au moins une pièce de couleur rouge.
>> (Smith, Blake, Clark, Adams)
*/

SELECT s.sname FROM s WHERE s.id_s IN (
    SELECT spj.id_s FROM spj WHERE spj.id_p IN (
        SELECT p.id_p FROM p WHERE p.color = 'Red'
    )
);

/*
EX 5
On souhaite connaître les numéros des fournisseurs et les 
noms des fournisseurs qui fournissent des pièces au projet Console.
>> (S1 Smith, S2 Jones, S5 Adams)
*/

SELECT s.id_s, s.sname FROM s WHERE s.id_s IN (
    SELECT spj.id_s FROM spj WHERE spj.id_j IN (
        SELECT j.id_j FROM j WHERE j.jname = 'Console'
    )
);

/*
EX 6
On souhaite connaître le nom des projets pour lesquels
on a fourni au total plus de 1000 pièces.
>> (Display, Console, RAID, Tape)
*/

SELECT j.jname FROM j WHERE j.id_j IN (
    SELECT spj.id_j FROM spj
    GROUP BY spj.id_j
    HAVING SUM(spj.qty) > 1000
);

/*
EX 7
On souhaite connaître le nom des pièces qui sont d’une couleur 
différente des couleurs des autres pièces 
(une pièce dont la couleur n’apparaît qu’une seule fois dans la base de données).
>> (Bolt)
*/

SELECT p.pname FROM p WHERE p.color = ANY (
    SELECT p.color FROM p
    GROUP BY color
    HAVING COUNT(*) = 1
);

/*
EX 8
Quelle est la pièce la plus lourde ? Ecrivez une version avec ANY ou ALL
et une seconde avec une liaison naturelle.
>> (Cog)
*/

SELECT p.pname FROM p WHERE p.weight >= ALL (
    SELECT p.weight FROM p
);


/*
EX 9
Quels sont les identifiants de fournisseurs qui ne livrent aucune pièce bleue
>> (S1, S4)
*/

SELECT s.id_s FROM s WHERE s.id_s NOT IN (
    SELECT spj.id_s FROM spj
    JOIN p ON p.id_p = spj.id_p
    WHERE p.color = 'Blue'
);

/*
EX 10
On souhaite connaître le nombre de livraisons de moins de 350 unités pour
l’ensemble des fournisseurs qui n’ont jamais fourni à Paris.
>> (8)
*/

SELECT COUNT(*) FROM spj WHERE spj.id_s NOT IN (
    SELECT spj.id_s FROM spj
    JOIN j ON j.id_j = spj.id_j
    WHERE j.city = 'Paris'
) AND spj.qty < 350;

/*
EX 11
On souhaite connaître les fournisseurs qui n’ont jamais fourni 
plus de 650 pièces identiques au total de toutes leurs livraisons. 
(C’est à dire ceux dont aucune pièce de leur stock n’a baissé de 650 unités).
>> (S3, S4)
*/

SELECT s.id_s FROM s WHERE s.id_s NOT IN (
    SELECT spj.id_s FROM spj
    GROUP BY spj.id_s, spj.id_p
    HAVING SUM(spj.qty) > 650
);

/*
EX 12
On souhaite connaître les fournisseurs qui ont fait au minimum
4 livraisons représentant au moins trois pièces différentes. 
>> (S5)
*/

SELECT s.id_s FROM s WHERE s.id_s IN ( 
    SELECT spj.id_s FROM spj 
    GROUP BY spj.id_s
    HAVING COUNT(*) > 3
    AND COUNT(DISTINCT spj.id_p) > 2 
);


/*
EX 13
On souhaite les fournisseurs qui fournissent au moins dans trois 
villes différentes et dont les pièces qu’ils fournissent proviennent 
d’au moins deux villes différentes.
>> (S2, S5)
*/

SELECT spj.id_s FROM spj
JOIN p ON p.id_p = spj.id_p
WHERE spj.id_s IN (
    SELECT spj.id_s FROM spj
    JOIN j ON j.id_j = spj.id_j
    GROUP BY spj.id_s
    HAVING COUNT(DISTINCT j.city) > 2
) GROUP BY spj.id_s
HAVING COUNT(DISTINCT p.city) > 1;

