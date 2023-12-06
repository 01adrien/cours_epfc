
SET GLOBAL SQL_MODE = CONCAT(@@SQL_MODE, ',ONLY_FULL_GROUP_BY');    

-- EX 1

SELECT spj.ID_S FROM spj
GROUP BY spj.ID_S
HAVING COUNT(*) > 3;

-- EX 2

SELECT spj.ID_S FROM spj
GROUP BY spj.ID_S
HAVING COUNT(*) > 3 AND COUNT(DISTINCT spj.ID_P) > 2;

-- EX 3

SELECT spj.ID_P FROM spj
JOIN s ON s.ID_S = spj.ID_S
JOIN j ON j.ID_J = spj.ID_J
WHERE j.CITY = 'London' AND s.CITY = "London"
GROUP BY spj.ID_P;

-- EX 4

SELECT spj.ID_J, SUM(spj.QTY) FROM spj
GROUP BY spj.ID_J, spj.ID_S
HAVING SUM(spj.QTY) > 500;

-- EX 5

SELECT spj.ID_P, spj.ID_J, SUM(spj.QTY) AS total FROM spj
GROUP BY spj.ID_P, spj.ID_J;

-- EX 6

SELECT spj.ID_P, COUNT(*) AS COUNT FROM spj
GROUP BY spj.ID_P
HAVING COUNT(*) > 2;

-- EX 7

SELECT DISTINCT p.CITY, s.CITY FROM spj
JOIN p ON spj.ID_P = p.ID_P
JOIN s ON spj.ID_S = s.ID_S
WHERE spj.ID_S = s.ID_S AND spj.ID_P = p.ID_P;

-- EX 8

SELECT DISTINCT s.SNAME FROM spj
JOIN s ON spj.ID_S = s.ID_S
JOIN p ON spj.ID_P = p.ID_P
WHERE p.COLOR = 'RED' AND (s.CITY = 'Paris' OR p.CITY = 'Paris');

-- EX 9

SELECT spj.ID_S, AVG(spj.QTY) FROM spj
JOIN s ON spj.ID_S = s.ID_S
WHERE s.CITY = 'London'
GROUP BY spj.ID_S
HAVING SUM(spj.QTY) > 800;

-- EX 10

