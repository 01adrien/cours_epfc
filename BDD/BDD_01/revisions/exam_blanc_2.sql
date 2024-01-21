SET GLOBAL SQL_MODE = CONCAT(@@SQL_MODE, ',ONLY_FULL_GROUP_BY');
-- DB => world_cup_2022
/*
EX 1
1) Écrivez la requête permettant d’obtenir les dates des matchs de poule
du Maroc (*Morocco*) et de la France (*France*).
*/  

SELECT m.date, t.team_name FROM participations AS p
JOIN matches AS m ON m.match_id = p.match_id
JOIN teams AS t ON t.team_id = p.team_id
WHERE m.type = 'p' AND t.team_name IN ('Morocco', 'France');  


/*
EX 2
2) Écrivez la requête permettant d’obtenir les matchs de poule qui se sont terminés 
par une égalité. Pour rappel, les deux équipes reçoivent 1 point dans ce cas-là. Affichez 
le nom des deux équipes, ainsi que la poule du match. Indice : le résultat contient 10 lignes.
*/

SELECT t1.team_name , t2.team_name FROM participations AS p1
JOIN participations AS p2 ON p2.match_id = p1.match_id
JOIN matches AS m ON m.match_id = p1.match_id
JOIN teams AS t1 ON t1.team_id = p1.team_id
JOIN teams AS t2 ON t2.team_id = p2.team_id
WHERE p1.match_id = p2.match_id AND p1.team_id > p2.team_id
AND p1.points = 1 AND m.type = 'p';


/*
EX 3
Écrivez la requête permettant d’obtenir les poules lors desquelles il y a eu exactement
quatre victoires. On affichera le nom de la poule. Indice : le résultat contient 2 lignes.
*/

SELECT t.pool, FROM participations AS p
JOIN teams AS t ON t.team_id = p.team_id
WHERE p.points = 3
GROUP BY t.pool
HAVING COUNT(DISTINCT p.match_id) = 4;


/*
EX 4
Écrivez la requête permettant d’afficher le nom des joueurs présents dans la base de
données qui n’ont marqué aucun but en phase de poule. 
Indice : le résultat contient 40 lignes.
*/

SELECT p.player_name FROM players AS p WHERE p.player_id NOT IN (
    SELECT g.player_id FROM goals AS g
    JOIN matches AS m ON m.match_id = g.match_id
    WHERE m.type = 'p' 
);


/*
EX 5
Écrivez la requête qui détermine quels sont les joueurs qui ont marqué le plus de buts lors
des tirs aux buts de ce tournoi. On affichera l’identifiant de ces joueurs.
*/

SELECT p.player_name, p.player_id, COUNT(*) FROM goals AS g
JOIN players AS p ON p.player_id = g.player_id
WHERE g.type = 'p'
GROUP BY p.player_name, p.player_id
HAVING SUM(g.num_goals) >= ALL (
    SELECT COUNT(g.num_goals) FROM goals AS g
    WHERE g.type = 'p'
    GROUP BY g.player_id
);