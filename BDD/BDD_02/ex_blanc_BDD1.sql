/*
EX 1
Écrivez la requête permettant d’obtenir le titre des films et le nom des techniciens belges
ayant participé à ces films, à condition que le technicien ait exercé sur le film au moins
un métier dont le prix de l'heure est inférieur à 40 ou que l'année de production du film
soit postérieure à 2017.
*/

SELECT DISTINCT f.titre, t.nom FROM film AS f
JOIN contrat AS c ON c.idFilm = f.idFilm
JOIN technicien AS t ON t.idTechnicien = c.idTechnicien
JOIN metier AS m ON m.idMetier = c.idMetier
WHERE LOWER(t.nat) = 'be' AND (
    m.prixHeure < 40 OR f.anneeProd > 2017
);

/*
EX 2
Q2) Écrivez la requête permettant d’obtenir les paires différentes 
d’identifiants de films telles que, pour chaque paire (d’identifiants) de films, 
au moins un même technicien exerce le même métier.
Rappel : la paire (X, Y) est égale à la paire (Y, X) 
(X et Y représentant ici n’importe quelle donnée).
*/

SELECT DISTINCT c1.idFilm, c2.idFilm FROM contrat AS c1
JOIN contrat AS c2 ON c1.idMetier = c2.idMetier
AND c1.idTechnicien = c2.idTechnicien
WHERE c1.idFilm < c2.idFilm;

/*
EX 3
Q3) Écrivez la requête permettant d'obtenir les films sur lesquels ne travail-
lent que des techniciens belges. On affichera tous les attributs de ces films.
*/

SELECT f.titre FROM film AS f WHERE f.idFilm NOT IN (
    SELECT c.idFilm FROM contrat AS c
    JOIN technicien AS t ON t.idTechnicien = c.idTechnicien
    WHERE LOWER(t.nat) != 'be'
);



/*
Q4) Écrivez la requête permettant d'obtenir pour chaque technicien, son nom, 
ainsi que le plus grand et le plus petit nombre de jours de travail qu'il a 
effectués sur un film.
*/

SELECT t.nom, MIN(c.nbJours) , MAX(c.nbJours)
FROM technicien AS t
JOIN contrat AS c ON t.idTechnicien = c.idTechnicien
GROUP BY t.idTechnicien, t.nom;


/*
Écrivez la requête permettant d’obtenir les films ‘hors budget', c’est-à-dire tels que le
coût total de l’ensemble des contrats liés au film soit supérieur au budget du film (on
suppose qu’une journée de travail comporte 8h de travail rémunéré). On affichera
l’identifiant du film, son titre, son budget, ainsi que le coût total de l’ensemble des métiers
liés au film.
*/

SELECT f.idFilm, f.titre, f.budget, SUM(m.prixHeure * c.nbJours * 8) AS $$
FROM film AS f
JOIN contrat AS c ON c.idFilm = f.idFilm
JOIN metier AS m ON m.idMetier = c.idMetier
GROUP BY f.idFilm, f.titre, f.budget
HAVING SUM(m.prixHeure * c.nbJours * 8) > f.budget;