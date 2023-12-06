-- EX 1

SELECT * FROM Personne WHERE `Age` > 30;

-- EX 2

SELECT * FROM Personne WHERE Age < 30 AND Sexe = 'F' ;

-- EX 3

SELECT ID_Message FROM Message WHERE Expediteur = 'P1';

-- EX 4

SELECT Nom FROM Personne WHERE Sexe = 'M' ORDER BY Nom ASC;

-- EX 5

SELECT Contenu FROM Message AS M
JOIN Personne AS P ON P.SSN = M.Expediteur
WHERE P.Sexe = 'M';

-- EX 6

SELECT Nom FROM Personne AS P  
JOIN Destinataires AS D ON P.SSN = D.Destinataire 
WHERE D.ID_Message = 'M4';

-- EX 7

SELECT DISTINCT Contenu FROM Message AS M
JOIN Destinataires AS D ON M.ID_Message = D.ID_Message
JOIN Personne AS P ON P.SSN = D.Destinataire
WHERE P.Sexe = 'F' AND P.Age < 30;

-- EX 8

SELECT P.Nom, A.SSN2 FROM Personne AS P
JOIN EstAmi AS A ON A.SSN2 = P.SSN
WHERE A.SSN1 = 'P1' AND A.SSN2 = P.SSN;

-- EX 9

SELECT M.Contenu FROM Message AS M
JOIN Personne AS P ON P.SSN = M.Expediteur
WHERE P.Nom LIKE '%e%';

-- EX 10

SELECT M.Date_Expedition FROM Message AS M
JOIN Destinataires AS D ON M.ID_Message = D.ID_Message
JOIN Personne AS P ON P.SSN = D.Destinataire
WHERE P.Nom LIKE 'M%';

-- EX 11

SELECT M.Contenu, P.Nom FROM Message AS M
JOIN Personne AS P ON P.SSN = M.Expediteur
WHERE P.Nom LIKE '_a%' AND P.Sexe = 'M';

-- EX 12

SELECT P2.Nom FROM Personne AS P 
JOIN Destinataires AS D ON D.Destinataire = P.SSN
JOIN Message AS M ON M.ID_Message = D.ID_Message
JOIN Personne AS P2 ON P2.SSN = M.Expediteur
WHERE P.AGE < 18;

-- EX 13

SELECT * FROM Personne AS pers
JOIN EstAmi AS A ON A.SSN1 = pers.SSN
WHERE pers.SSN = 'P1';

-- EX 14    
 
SELECT DISTINCT
P3.Nom
FROM Personne AS P1
JOIN EstAmi AS A1 ON P1.SSN = A1.SSN1
JOIN Personne AS P2 ON P2.SSN = A1.SSN2
JOIN EstAmi AS A2 ON P2.SSN = A2.SSN1
JOIN Personne AS P3 ON P3.SSN = A2.SSN2
WHERE P1.Nom = 'Xavier';

-- EX 15 
--a
SELECT P1.Nom, P2.Nom 
FROM Personne AS P1
JOIN Personne AS P2 ON P2.SSN < P1.SSN
WHERE P1.Sexe != P2.Sexe;

--b 
SELECT P1.Nom, P2.Nom
FROM Personne AS P1
JOIN Personne AS P2 ON P2.SSN < P1.SSN
JOIN EstAmi AS A ON A.SSN1 = P1.SSN AND A.SSN2 = P2.SSN
WHERE P1.Sexe != P2.Sexe;

-- Ex 16

SELECT P1.Nom, P2.Nom 
FROM Personne AS P1
JOIN Personne AS P2 ON P2.SSN < P1.SSN
WHERE P1.Sexe != P2.Sexe
ORDER BY (P2.Age + P1.Age);