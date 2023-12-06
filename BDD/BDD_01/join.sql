
-- EX 1

SELECT spj.ID_s, spj.ID_P 
FROM spj 
JOIN p ON spj.ID_P = p.ID_P 
WHERE p.COLOR = 'RED';

-- EX 2

SELECT s.SNAME AS name 
FROM s JOIN spj 
ON s.ID_S = spj.ID_S 
WHERE spj.ID_P = 'P4';


-- EX 3

SELECT DISTINCT j.JNAME AS name 
FROM j 
JOIN spj ON j.ID_J = spj.ID_J 
WHERE spj.ID_P = 'P3';

-- EX 4

SELECT DISTINCT j.JNAME AS name 
FROM j 
JOIN spj ON j.ID_J = spj.ID_J 
WHERE spj.ID_S  = 'S1';


-- EX 5

SELECT DISTINCT s.SNAME 
FROM spj 
JOIN s ON s.ID_S = spj.ID_S 
WHERE spj.QTY >= 150 AND spj.QTY <= 250

-- DE MORGAN 

SELECT DISTINCT s.SNAME 
FROM spj 
JOIN s ON s.ID_S = spj.ID_S 
WHERE NOT ( spj.QTY < 150 OR spj.QTY > 250 )


-- EX 6

SELECT
s.SNAME AS NOM,
p.PNAME AS PIECE,
j.JNAME AS PROJET,
spj.QTY AS QUANTITE
FROM spj 
JOIN s ON s.ID_S = spj.ID_S 
JOIN p ON p.ID_P = spj.ID_P
JOIN j ON j.ID_J = spj.ID_J


-- EX 7

SELECT
s.SNAME AS NOM,
p.PNAME AS PIECE,
j.JNAME AS PROJET,
spj.QTY AS QUANTITE
FROM spj 
JOIN s ON s.ID_S = spj.ID_S 
JOIN p ON p.ID_P = spj.ID_P
JOIN j ON j.ID_J = spj.ID_J
WHERE s.CITY = 'Paris'

-- EX 8

SELECT DISTINCT
j.CITY 
FROM spj
JOIN s ON s.ID_S = spj.ID_S
JOIN j ON j.ID_J = spj.ID_J
WHERE s.SNAME = 'Adams'

-- EX 9

SELECT DISTINCT p.PNAME 
FROM spj 
JOIN p ON p.ID_P = spj.ID_P 
JOIN s ON s.ID_S = spj.ID_S 
WHERE p.WEIGHT < 18 AND 
s.CITY = 'London' OR s.CITY = 'Rome'

-- EX 10

SELECT DISTINCT p.ID_P
FROM spj
JOIN p ON p.ID_P = spj.ID_P
JOIN s ON s.ID_S =spj.ID_S
WHERE s.CITY = 'London'