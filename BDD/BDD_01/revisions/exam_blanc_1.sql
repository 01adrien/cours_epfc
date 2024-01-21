
-- DB ==>> BD01_2122_1S_A

/*
EX 1
Écrivez la requête permettant d’obtenir le titre des films et le nom des techniciens belges
ayant participé à ces films, à condition que le technicien ait exercé sur le film au moins
un métier dont le prix de l'heure est inférieur à 40 ou que l'année de production du film
soit postérieure à 2017.
*/

SELECT DISTINCT f.Titre, t.Nom FROM contrat as c
JOIN Film AS f ON f.IdFilm = c.IdFilm
JOIN Technicien AS t ON t.IdTechnicien = c.IdTechnicien
JOIN Metier AS m ON m.IdMetier = c.IdMetier
WHERE t.Nat = 'Be' AND (
    f.AnneeProd < 2017 OR m.PrixHeure < 40
);

/*
EX 2
Écrivez la requête permettant d’obtenir les paires différentes d’identifiants de films telles
que, pour chaque paire (d’identifiants) de films, au moins un même technicien exerce le
même métier.
Rappel : la paire (X, Y) est égale à la paire (Y, X) (X et Y représentant ici n’importe
quelle donnée)
*/

SELECT c1.idFilm, c2.IdFilm FROM contrat AS c1, contrat AS c2
WHERE c1.idFilm < c2.idFilm
AND c1.IdTechnicien = c2.IdTechnicien
AND c1.IdMetier = c2.IdMetier;


/*
EX 3
Écrivez la requête permettant d’obtenir les films sur lesquels ne travaillent que des
techniciens belges. On affichera tous les attributs de ces films.
*/

SELECT * FROM film AS f WHERE f.idFilm NOT IN (
    SELECT c.idFilm FROM contrat AS c
    JOIN technicien AS t ON t.IdTechnicien = c.IdTechnicien
    WHERE t.Nat <> 'BE'
    GROUP BY c.idFilm 
);

/*
EX 4
Écrivez la requête permettant d’obtenir pour chaque technicien, son nom, ainsi que le
plus grand et le plus petit nombre de jours de travail qu’il a effectués sur un film.
*/

SELECT t.Nom, MIN(c.nbJours) AS min, MAX(c.nbJours) AS max
FROM contrat AS c
JOIN technicien AS t on t.IdTechnicien = c.IdTechnicien
GROUP BY t.Nom, t.IdTechnicien;

/*
EX 5
Écrivez la requête permettant d’obtenir les films ‘hors budget', c’est-à-dire tels que le
coût total de l’ensemble des contrats liés au film soit supérieur au budget du film (on
suppose qu’une journée de travail comporte 8h de travail rémunéré). On affichera
l’identifiant du film, son titre, son budget, ainsi que le coût total de l’ensemble des métiers
liés au film.
*/

SELECT f.idFilm, f.Titre, f.budget, SUM(c.nbJours * m.PrixHeure * 8) AS cout
FROM contrat AS c
JOIN film AS f ON f.idFilm = c.idFilm
JOIN metier AS m ON m.IdMetier = c.IdMetier
GROUP BY f.idFilm, c.idFilm, f.Titre, f.budget
HAVING SUM(c.nbJours * m.PrixHeure * 8) > f.budget;