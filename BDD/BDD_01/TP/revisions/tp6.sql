/*
EX 1
Déterminez, par quantité, le nombre de livraisons effectuées
Exemple : il y a eu 4 livraisons de 100 unités, 7 livraisons de 200 unités, etc
*/

SELECT COUNT(*) AS count , spj.qty FROM spj
GROUP BY spj.qty;


/*
EX 2
On souhaite connaître le nombre de livraisons de chaque fournisseur.
Exemple : le fournisseur S2 a effectué 8 livraisons. 
Le fournisseur S3 a effectué 2 livraisons
*/

SELECT COUNT(*) AS count, spj.id_s FROM spj
GROUP BY spj.id_s;

/*
EX 3
On veut connaître le nombre de pièces fournies par projet. 
On souhaite le nom du projet et non son identifiant.
Exemple : on a fourni 800 pièces au projet Sorter, 1200 pièces au projet Display
*/

SELECT SUM(spj.qty) AS sum, j.jname FROM spj
JOIN j ON j.id_j = spj.id_j
GROUP BY spj.id_j, j.jname ORDER BY sum;

/*
EX 4
On veut connaître le poids de chaque livraison.
*/

SELECT j.jname, p.pname, s.sname, (p.weight * spj.qty) AS total_weight FROM spj
JOIN j ON j.id_j = spj.id_j
JOIN s ON s.id_s = spj.id_s
JOIN p ON p.id_p = spj.id_p;

/*
EX 5
On veut connaître le poids total des pièces livrées par projet.
*/

SELECT spj.id_j, SUM(spj.qty * p.weight) AS sum FROM spj
JOIN p ON p.id_p = spj.id_p
GROUP BY spj.id_j;


/*
EX 6
On veut connaître le poids de « la livraison la plus lourde ».
>> 13600
*/

SELECT MAX(spj.qty * p.weight) FROM spj
JOIN p ON p.id_p = spj.id_p;

/*
EX 7
Pour chaque pièce fournie à un projet, obtenir le numéro de pièce, 
le numéro de projet et la quantité totale fournie (de cette pièce à ce projet)
dont l’intitulé sera « total ».
*/


SELECT spj.id_j, spj.id_p, SUM(spj.qty) FROM spj
GROUP BY spj.id_j, spj.id_p;







