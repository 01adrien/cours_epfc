/* 
EX 1
On souhaite obtenir les numéros de fournisseurs qui 
ont effectué au moins quatre livraisons
>> (S2 S5).
*/

SELECT spj.id_s FROM spj
GROUP By spj.id_s
HAVING COUNT(*) > 3;

/*
EX 2
On souhaite obtenir les numéros de fournisseurs 
qui ont effectué au moins quatre livraisons
concernant au moins trois pièces différentes 
>> (S5).
*/

SELECT spj.id_s FROM spj
GROUP BY spj.id_s
HAVING COUNT(*) > 3 AND COUNT(DISTINCT spj.id_p) > 2;

/*
EX 3
On souhaite obtenir les numéros de pièces pour les pièces 
fournies par un fournisseur de Londres à un projet de Londres 
>> (P6).
*/

SELECT spj.id_p FROM spj
JOIN s ON s.id_s = spj.id_s
JOIN j ON j.id_j = spj.id_j
WHERE s.city = 'London' AND j.city = 'London';

/*
EX 4
On souhaite obtenir les numéros de projets et la quantité 
totale livrée pour chacun des projets fourni par un même 
fournisseur, pour autant que la quantité totale livrée soit 
supérieure à 500 pièces 
>> (J4 700, J4 2100, J5 600, J7 800)
*/

SELECT spj.id_j, SUM(spj.qty) FROM spj
GROUP BY spj.id_j, spj.id_s
HAVING SUM(spj.qty) > 500;


/*
EX 5
On souhaite afficher, pour chaque pièce fournie à un projet, 
le numéro de la pièce, le numéro du projet et la quantité totale 
fournie dont l’entête sera TOTAL 
>> (21 résultats).
*/

SELECT spj.id_p, spj.id_j, SUM(spj.qty) AS TOTAL FROM spj
GROUP BY spj.id_j, spj.id_p;

/*
EX 6
Obtenir, par pièce fournie plus que deux fois, le numéro de pièce 
et le nombre de fois qu’elle a été fournie
>> (P1 3, P3 9, P5 4, P6 4).
*/

SELECT spj.id_p, COUNT(*) FROM spj
GROUP BY spj.id_p
HAVING COUNT(*) > 2;

/*
EX 7
Obtenir toutes les paires de villes telles que le fournisseur situé dans 
la première ville fournisse une pièce entreposée dans la seconde ville
>> (7 résultats)
*/

SELECT DISTINCT s.city, p.city FROM spj
JOIN s ON s.id_s = spj.id_s
JOIN p ON p.id_p = spj.id_p;


/*
EX 8
Obtenir le nom des fournisseurs qui fournissent au moins une pièce
rouge et tel que le fournisseur ou la pièce se situent à Paris 
>> (Blake).
*/

SELECT DISTINCT s.sname FROM spj
JOIN p ON p.id_p = spj.id_p
JOIN s ON s.id_s = spj.id_s
WHERE p.color = 'Red' 
AND (s.city = 'Paris' OR p.city = 'Paris');


/*
EX 9
Obtenir la moyenne des quantités livrées de chaque 
fournisseur de Londres dont le total des quantités 
livrées est supérieur strictement à 800 
>> (450 S1).
*/

SELECT spj.id_s, AVG(spj.qty) FROM spj
JOIN s ON s.id_s = spj.id_s
WHERE s.city = 'London'
GROUP BY spj.id_s
HAVING SUM(spj.qty) > 800;

/*
EX 10
Obtenir les couples d'identifiants de fournisseurs 
différents qui fournissent la même pièce pour autant
qu'ils fournissent la pièce en même quantité 
(S2 S3, S2 S5, S3 S5).
*/

SELECT DISTINCT spj1.id_s, spj2.id_s FROM spj AS spj1, spj AS spj2
WHERE spj1.id_s < spj2.id_s
AND spj1.id_p = spj2.id_p AND spj2.qty = spj1.qty;