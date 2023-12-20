/*
EX 1
Donnez le nom des pièces qui contiennent la
lettre ‘c’ majuscule ou minuscule dans leur nom.
(Cam, Cog, Screw)
*/

SELECT DISTINCT p.PNAME FROM p 
WHERE p.PNAME LIKE '%c%' OR p.PNAME LIKE '%C%';


/*
EX 2
Sachant que le poids des pièces est exprimé en livres
et qu’une livre vaut 454 grammes, donnez le nom des 
pièces dont le poids est inférieur ou égal à 6 kilos.
(Nut, Cam)
*/

SELECT DISTINCT p.PNAME FROM p
WHERE (p.WEIGHT * 454) <= 6000;


/*
EX 3
Donnez le nom des projets dont le nom contient
une lettre ‘i’ (majuscule ou minuscule).
(Display, RAID)
*/

SELECT DISTINCT JName FROM j 
WHERE j.JNAME LIKE '%I%';


/*
EX 4
Affichez le nom des fournisseurs qui 
commencent soit par ‘A’ soit par ‘C’.
(Adams, Clark)
*/

SELECT s.SNAME FROM s
WHERE s.SNAME LIKE 'A%' OR s.SNAME LIKE 'C%';


/*
EX 5
Affichez le nom des fournisseurs qui ont
livré entre 2015 et 2019 (inclus)
(Blake, Adams)
*/

SELECT DISTINCT s.SNAME FROM s WHERE s.ID_S IN (
    SELECT spj.ID_S FROM spj 
    WHERE YEAR(spj.DATE_DERNIERE_LIVRAISON) >= 2015 
    AND YEAR(spj.DATE_DERNIERE_LIVRAISON) <= 2019
);


/*
EX 6
Donnez tous les triplets (id_s, id_p, id_j) tel que le
fournisseur, la pièce et le projet n’aient pas
tous la même ville (194 réponses). Autrement dit, 
les trois villes ne peuvent pas être identiques. 
(Remarque : il ne faut pas spécialement qu’il y ait une 
livraison associée à ce triplet)
*/

SELECT s.ID_S, p.ID_P, j.ID_J FROM s, p, j
WHERE s.CITY != p.CITY AN j.CITY != p.CITY;


/*
EX 7
Donnez tous les triplets 
(numéro de pièce1, numéro de pièce2, numéro de fournisseur) 
tel que le fournisseur fournisse les deux pièces du triplet 
On désire donc trouver deux livraisons d’un même fournisseur 
pour deux produits différents.
(17 réponses). 
*/

SELECT DISTINCT s1.ID_P, s2.ID_P, s1.ID_S
FROM spj AS s1, spj AS s2
WHERE s1.ID_S = s2.ID_S 
AND s1.ID_P < s2.ID_P;


/*
EX 8
Donnez tous les triplets (id_s,id_p,id_j) tel que le fournisseur, 
la pièce et le projet aient tous des villes différentes 
Il ne faut pas spécialement qu’il y ait eu une livraison associée 
à ce triplet.
Remarque : affichez aussi les villes correspondantes pour 
faciliter la vérification.
(79 réponses).
*/

SELECT DISTINCT s.ID_S, s.CITY, p.ID_P, p.CITY, j.ID_J, j.CITY
FROM s, p, j
WHERE s.CITY != p.CITY 
AND j.CITY != p.CITY
AND s.CITY != j.CITY; 


/*
EX 9
Quels sont les fournisseurs qui ont fourni la même pièce
à des clients différents ? 
(4 réponses).
*/

SELECT DISTINCT s1.ID_S 
FROM spj AS s1, spj AS s2
WHERE s1.ID_S = s2.ID_S 
AND s1.ID_P = s2.ID_P
AND s1.ID_J <> s2.ID_J; 