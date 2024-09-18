-- BD le leviathan
-- le comptoir belge
-- BD en face de la pompe

/*
EX 1
Affichez l’identifiant des livraisons qui concernent un produit rouge 
(9 lignes)
*/

SELECT * FROM spj 
JOIN p ON spj.ID_P = p.ID_P
WHERE p.COLOR = 'RED';


/*
EX 2
Affichez le nom des fournisseurs qui fournissent le produit P4 
(Blake et Adams)
*/

SELECT DISTINCT s.SNAME FROM s
JOIN spj ON spj.ID_S = s.ID_S
WHERE spj.ID_P = 'P4';


/*
EX 3
Affichez le nom des clients/projets qui utilisent le produit P3 
(7 lignes : le nom de tous les clients).
*/

SELECT DISTINCT j.JNAME FROM j
JOIN spj ON spj.ID_J = j.ID_J
WHERE spj.ID_P = 'P3';


/*
EX 4
Affichez le nom des projets fournis par le fournisseur S1 
(Sorter et Console).
*/

SELECT j.JNAME FROM j
JOIN spj ON spj.ID_J = j.ID_J
WHERE spj.ID_S = 'S1';


/*
EX 5
Affichez le nom des fournisseurs qui ont fait au moins
une livraison d’entre 150 et 250 pièces
(Smith, Jones, Blake et Adams)
*/

SELECT DISTINCT s.SNAME FROM s
JOIN spj ON spj.ID_S = s.ID_S
WHERE spj.QTY BETWEEN 150 AND 250;


/*
EX 6
On souhaite obtenir l’affichage des livraisons avec 
le nom du fournisseur à la place de son identifiant, 
ainsi que le nom de la pièce à la place de son identifiant
et le nom du projet à la place de son identifiant. 
La date de dernière livraison ne doit pas figurer dans le résultat. 
Par contre l’entête affichée doit être NOM, PIECE, PROJET et QUANTITE. 
Le résultat contient 24 lignes.
Par exemple :
NOM PIECE PROJET QUANTITE
Smith Nut Sorter 200
Smith Nut Console 700
*/

SELECT s.SNAME, p.PNAME, j.JNAME, spj.QTY FROM spj
JOIN s ON s.ID_S = spj.ID_S
JOIN p ON p.ID_P = spj.ID_P
JOIN j ON j.ID_J = spj.ID_J;


/*
EX 7
Même requête où l’on ne garde que les fournisseurs de Paris 
(10 lignes).
*/

SELECT s.SNAME, p.PNAME, j.JNAME, spj.QTY FROM spj
JOIN s ON s.ID_S = spj.ID_S
JOIN p ON p.ID_P = spj.ID_P
JOIN j ON j.ID_J = spj.ID_J
WHERE s.CITY = 'PARIS';

/*
EX 8
Affichez les villes des projets où le fournisseur Adams a livré 
(Athens, Rome et London)
*/

SELECT DISTINCT j.CITY FROM spj
JOIN j ON j.ID_J = spj.ID_J
JOIN s ON s.ID_S = spj.ID_S
WHERE s.SNAME = 'Adams'; 

/*
EX 9
On souhaite le nom des pièces dont le poids
est strictement inférieur à 18 livres et qui 
sont fournies par un fournisseur de Rome ou de Londres 
(Nut).
*/

SELECT DISTINCT p.PNAME FROM spj
JOIN p ON p.ID_P = spj.ID_P
JOIN s ON s.ID_S = spj.ID_S
WHERE p.WEIGHT < 18 AND 
s.CITY IN ('Rome', 'London');

/*
EX 10
Obtenir l’identifiant des pièces de Londres qui ont étés
livrées par un fournisseur de Londres 
(P1 et P6)
*/

SELECT DISTINCT spj.ID_P FROM spj 
JOIN s ON s.ID_S = spj.ID_S
JOIN p ON p.ID_P = spj.ID_P
WHERE s.CITY = 'London' AND p.CITY = 'London';