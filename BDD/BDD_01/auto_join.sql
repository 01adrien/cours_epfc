
-- EX 1

select DISTINCT p.PNAME from p WHERE p.PNAME LIKE CONCAT ('%', 'c', '%'); 

-- EX 2

select DISTINCT p.PNAME FROM p WHERE ( p.WEIGHT * 454 ) <= 6000; 

-- EX 3

select j.JNAME from j WHERE j.JNAME LIKE CONCAT ('%', 'i', '%'); 

-- EX 4

SELECT s.SNAME FROM s WHERE s.SNAME LIKE 'A%' OR s.SNAME LIKE 'C%';

-- EX 5

SELECT DISTINCT s.SNAME FROM spj 
JOIN s ON s.ID_S = spj.ID_S 
WHERE YEAR(spj.DATE_DERNIERE_LIVRAISON) BETWEEN '2015' AND '2019'; 

-- EX 6

SELECT s.ID_S, s.CITY AS c1, p.ID_P, p.CITY AS c2, j.ID_J, j.CITY AS c3 
FROM s, p, j
WHERE s.CITY = p.CITY AND j.CITY != s.CITY 
OR s.CITY != p.CITY AND j.CITY = s.CITY
OR s.CITY != p.CITY AND j.CITY != s.CITY

-- EX 7

SELECT DISTINCT spj1.ID_P, spj2.ID_P, spj1.ID_S
FROM spj AS spj1 JOIN spj AS spj2 
ON spj1.ID_P < spj2.ID_P
AND spj1.ID_S = spj2.ID_S;

-- EX 8

SELECT s.ID_S, s.CITY AS c1, p.ID_P, p.CITY AS c2, j.ID_J, j.CITY AS c3 
FROM s, p, j
WHERE s.CITY != p.CITY AND p.CITY != j.CITY AND j.CITY != s.CITY;

-- EX 9

SELECT DISTINCT s.SNAME FROM s
JOIN spj ON spj.ID_S = s.ID_S
JOIN spj AS spj_bis ON spj.ID_P = spj_bis.ID_P 
AND spj.ID_J > spj_bis.ID_J;





