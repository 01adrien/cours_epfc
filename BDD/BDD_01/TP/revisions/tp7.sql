/*
EX 1
Obtenir les projets d’Athens qui ont utilisé au total plus de 1000 pièces
*/

SELECT j.jname, spj.id_j, SUM(spj.qty) FROM spj
JOIN j ON j.id_j = spj.id_j
WHERE j.city = 'Athens'
GROUP BY j.jname, spj.id_j
HAVING SUM(spj.qty) > 1000;

/*
EX2
On souhaite obtenir l’identifiant des pièces et l’identifiant 
des projets lorsque la moyenne des quantités livrées 
(de cette pièce à ce projet) est supérieure à 320
*/

SELECT spj.id_p, spj.id_j FROM spj
GROUP BY spj.id_p, spj.id_j
HAVING AVG(spj.qty) > 320;

/*
EX 3
On souhaite obtenir l’identifiant des fournisseurs qui ont 
livré la même pièce à au moins trois projets différents.
*/

SELECT spj.id_s, s.sname FROM spj
JOIN s ON s.id_s = spj.id_s
GROUP BY spj.id_s, s.sname, spj.id_p
HAVING COUNT(DISTINCT spj.id_j) > 2;

/*
EX 4
On souhaite obtenir les pièces qui ont été livrées 
par au moins deux fournisseurs différents.
*/

SELECT spj.id_p, p.pname FROM spj
JOIN p ON p.id_p = spj.id_p
GROUP BY spj.id_p, p.pname
HAVING COUNT(DISTINCT spj.id_s) > 1; 


/*
EX 5
On souhaite obtenir les pièces qui ont été livrées
à un même projet par au moins deux fournisseurs différents.
*/

SELECT DISTINCT spj.id_p FROM spj
GROUP BY spj.id_p, spj.id_j
HAVING COUNT(DISTINCT spj.id_s) > 1;


/*
EX 6 
On veut, pour chaque pièce, la somme totale livrée à 
condition qu’il y ait eu plus de 3 livraisons pour cette pièce.
*/


SELECT spj.id_p, SUM(spj.qty) FROM spj
GROUP BY spj.id_p
HAVING COUNT(*) > 3;





