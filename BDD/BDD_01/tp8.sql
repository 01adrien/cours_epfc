SET GLOBAL SQL_MODE = CONCAT(@@SQL_MODE, ',ONLY_FULL_GROUP_BY');    

-- EX 1

SELECT COUNT(*) AS COUNT FROM Destinataires AS d WHERE d.ID_Message = 'M2'; 

-- EX 2

SELECT MIN(p.AGE) FROM Personne AS p;

-- EX 3

SELECT MAX(m.Date_Expedition) FROM Message AS m;

-- EX 4

SELECT COUNT(*) FROM Personne AS p
WHERE p.Age < 30;


-- EX 4

SELECT AVG(p.AGE) FROM Personne AS p
WHERE p.Sexe = 'F';