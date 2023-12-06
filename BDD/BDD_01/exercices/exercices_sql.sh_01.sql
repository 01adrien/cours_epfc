
-- EX 1

SELECT villes.ville_nom 
FROM villes_france_free AS villes
ORDER BY villes.ville_population_2012 DESC
LIMIT 10;

-- EX 2

SELECT villes.ville_nom 
FROM villes_france_free AS villes
ORDER BY villes.ville_surface ASC
LIMIT 50;

-- EX 3

SELECT villes.ville_nom 
FROM villes_france_free AS villes
WHERE villes.ville_departement LIKE '97%'

