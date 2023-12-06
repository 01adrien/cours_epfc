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

-- EX 5

SELECT AVG(p.AGE) FROM Personne AS p
WHERE p.Sexe = 'F';

-- EX 6

SELECT (COUNT(d.Destinataire) / COUNT(DISTINCT d.ID_Message)) AS avg
FROM Destinataires AS d;

-- EX 7

SELECT p.Sexe, COUNT(*) AS total FROM Personne AS p
GROUP BY p.Sexe;

-- EX 8

SELECT a.ssn1, COUNT(*) AS friends_nbr FROM estami AS a
WHERE a.ssn1 = 'P1' OR a.ssn1 = 'P2'
GROUP BY a.ssn1;

-- EX 9

SELECT a.ssn1, p.Nom FROM estami AS a
JOIN Personne AS p ON p.ssn = a.ssn1
GROUP BY a.ssn1, p.Nom
HAVING COUNT(*) < 3;

-- EX 10

SELECT d.ID_Message, m.Contenu, p.Nom AS exp, COUNT(*) AS COUNT
FROM destinataires AS d
JOIN message AS m ON d.ID_Message = m.ID_Message
JOIN personne AS p ON p.ssn = m.Expediteur
GROUP BY d.ID_Message, m.Contenu, p.Nom
ORDER BY d.ID_Message ASC;

-- EX 11

SELECT d.ID_Message FROM destinataires AS d
GROUP BY d.ID_Message
HAVING COUNT(*) > 1;

-- EX 12

SELECT m.Expediteur, p.Sexe, p.Nom FROM message AS m
JOIN personne AS p ON p.ssn = m.Expediteur
GROUP BY m.Expediteur, p.Sexe, p.Nom
HAVING p.Sexe = 'F'; 

-- EX 13

SELECT p.Sexe, COUNT(*) FROM destinataires AS d
JOIN personne AS p ON p.ssn = d.Destinataire
GROUP BY p.Sexe;

-- EX 14

    