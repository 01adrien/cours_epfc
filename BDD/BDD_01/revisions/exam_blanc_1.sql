-- BDD => BD01_2122_1S_A

/*
EX 1
Écrivez la requête permettant d’obtenir le titre des films et le nom
des techniciens belges ayant participé à ces films, à condition que 
le technicien ait exercé sur le film au moins un métier dont le prix de
l'heure est inférieur à 40 ou que l'année de production du film soit postérieure à 2017.
*/

SELECT DISTINCT f.titre, t.nom FROM contrat as c
JOIN film AS f ON f.idfilm = c.idfilm
JOIN technicien AS t ON t.idtechnicien = c.idtechnicien
JOIN metier AS m ON m.idmetier = c.idmetier
WHERE ( m.prixheure < 40 OR f.anneeprod < 2017 ) AND t.nat = 'Be';


/*
EX 2
Écrivez la requête permettant d’obtenir les paires différentes 
d’identifiants de films telles que, pour chaque paire (d’identifiants) de
films, au moins un même technicien exerce le même métier.
Rappel : la paire (X, Y) est égale à la paire (Y, X) 
(X et Y représentant ici n’importe quelle donnée).
*/

SELECT DISTINCT c1.idfilm, c2.idfilm 
FROM contrat AS c1, contrat AS c2
WHERE c1.idmetier = c2.idmetier
AND c1.idtechnicien = c1.idtechnicien
AND c1.idfilm < c2.idfilm;

/*
EX 3
Écrivez la requête permettant d’obtenir les films sur lesquels ne travaillent que des
techniciens belges. On affichera tous les attributs de ces films.
*/

SELECT DISTINCT * FROM film AS f
WHERE f.idfilm NOT IN (
    SELECT c.idfilm FROM contrat AS c
    JOIN technicien AS t ON t.idtechnicien = c.idtechnicien
    WHERE t.nat != 'Be'
);


