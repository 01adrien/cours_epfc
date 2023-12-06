SET GLOBAL SQL_MODE = CONCAT(@@SQL_MODE, ',ONLY_FULL_GROUP_BY');    

-- 1

SELECT spj.QTY, COUNT(*) AS count FROM spj GROUP BY spj.QTY;

-- 2

SELECT 
s.SNAME AS name,
s.ID_S AS id,
COUNT(*) AS count FROM spj 
JOIN s ON s.ID_S = spj.ID_S
GROUP BY spj.ID_S; 

-- 3

SELECT j.JNAME AS project_name,
SUM(spj.QTY) AS pieces_qty FROM spj
JOIN j ON j.ID_J = spj.ID_J
GROUP BY j.ID_J, j.JNAME  
ORDER BY pieces_qty DESC;

-- 4

SELECT (spj.QTY * p.WEIGHT) AS weight
FROM spj
JOIN p ON p.ID_P = spj.ID_P;

-- 5

SELECT j.JNAME AS project,
SUM(spj.QTY) AS count,
SUM(spj.QTY * p.WEIGHT) AS weight
FROM spj 
JOIN p ON p.ID_P = spj.ID_P
JOIN j ON j.ID_J = spj.ID_J
GROUP BY spj.ID_J, j.JNAME;

-- 6

SELECT MAX(spj.QTY * p.WEIGHT) AS weight
FROM spj
JOIN p ON p.ID_P = spj.ID_P;

-- 7 

SELECT spj.ID_J, spj.ID_P, j.JNAME, p.PNAME,
SUM(spj.QTY) AS total,
SUM(spj.QTY * p.weight) AS weight
FROM spj
JOIN p ON p.ID_P = spj.ID_P
JOIN j ON j.ID_J = spj.ID_J
GROUP BY spj.ID_J, spj.ID_P
ORDER BY spj.ID_P;