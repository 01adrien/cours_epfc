SET GLOBAL SQL_MODE = CONCAT(@@SQL_MODE, ',ONLY_FULL_GROUP_BY');

/*
-- EX 1
Afficher le contenu des messages envoyés par un homme.
>> (4 réponses)
*/

SELECT m.Contenu FROM Message AS m 
WHERE m.Expediteur IN (
    SELECT p.SSN FROM Personne AS p 
    WHERE p.Sexe = 'M'
);


/*
-- EX 2
Afficher le nom des personnes qui ont reçu le message M4.
>> (Julie et Marie)
*/

SELECT p.Nom FROM Personne AS p 
WHERE p.SSN IN (
    SELECT d.Destinataire FROM Destinataires AS d 
    WHERE d.ID_Message = 'M4'
);


/*
-- EX 3
Afficher le contenu des messages reçus par une femme de moins de 30 ans.
>> (3 réponses)
*/

SELECT m.Contenu FROM Message AS m 
WHERE m.ID_Message IN (
    SELECT d.ID_Message FROM Destinataires AS d
    WHERE d.Destinataire IN (
        SELECT p.SSN FROM Personne AS p
        WHERE p.Age < 30 AND p.Sexe = 'F'
    )
);


/*
-- EX 4
Afficher le nom des amis de la personne P1.
>> (Julie, David et Xavier)
*/

SELECT p.Nom FROM Personne AS p 
WHERE p.SSN IN (
    SELECT a.SSN2 FROM EstAmi AS a
    WHERE a.SSN1 = 'P1'
);


/*
-- EX 5
Quels sont les amis des amis de P1 ?
>> (P1, P2, P3 et P4)
*/

SELECT DISTINCT a.SSN2 FROM EstAmi AS a
WHERE a.SSN1 IN (
    SELECT a.SSN2 FROM EstAmi AS a
    WHERE a.SSN1 = 'P1'
);


/*
-- EX 6
Quels sont les amis des amis de Xavier?
>> (P1, P2, P3 et P4)
*/

SELECT DISTINCT a.SSN2 FROM EstAmi AS a
WHERE a.SSN1 IN (
    SELECT a.SSN2 FROM EstAmi AS a
    WHERE a.SSN1 IN (
        SELECT p.SSN FROM Personne AS p 
        WHERE p.Nom = 'Xavier'
    )
);


/*
-- EX 7
Quelles sont les personnes qui ont envoyé un message à une femme (a)
et qui ont reçu un message d’un homme (b) ? 
>> (Xavier)
*/

-- UNIQUEMENT AVEC IN 
SELECT p.Nom FROM Personne AS p 
WHERE p.SSN IN (
    SELECT m.Expediteur FROM Message AS m 
    WHERE m.ID_Message IN (
        SELECT d.ID_Message FROM Destinataires AS d
        WHERE d.Destinataire IN (
            SELECT p.SSN FROM Personne AS p 
            WHERE p.Sexe = 'F'
        )
    )
) AND p.SSN IN (
    SELECT d.Destinataire FROM Destinataires AS d
    WHERE d.ID_Message IN (
        SELECT m.ID_Message FROM Message AS m
        WHERE m.Expediteur IN (
            SELECT p.SSN FROM Personne AS p
            WHERE p.Sexe = 'M'
        )
    )
);

-- AVEC JOIN
SELECT p.Nom FROM Personne AS p 
WHERE p.SSN IN (
    SELECT m.Expediteur FROM Message AS m
    JOIN Destinataires AS d ON d.ID_Message = m.ID_Message
    JOIN Personne AS p ON p.SSN = d.Destinataire
    WHERE p.Sexe = 'F'
) AND p.SSN IN (
    SELECT d.Destinataire FROM Destinataires AS d
    JOIN Message AS m ON m.ID_Message = d.ID_Message
    JOIN Personne AS p ON p.SSN = m.Expediteur
    WHERE p.Sexe = 'M'
);


/*
-- EX 8
Quelle personne n’a envoyé aucun message ? 
>> (David)
*/

SELECT p.Nom FROM Personne AS p 
WHERE p.SSN NOT IN (
    SELECT DISTINCT m.Expediteur FROM Message AS m
);


/*
-- EX 9
Quelle personne n’a reçu aucun message ? 
>> (Paul)
*/

SELECT p.Nom FROM Personne AS p 
WHERE p.SSN NOT IN (
    SELECT DISTINCT d.Destinataire FROM Destinataires AS d
);


/*
-- EX 10
Qui n’a jamais envoyé de message à un homme ?
>> (David, Xavier et Remy)
*/

SELECT p.Nom FROM Personne AS p
WHERE p.SSN NOT IN (
    SELECT m.Expediteur FROM Message AS m
    WHERE m.ID_Message IN (
        SELECT d.ID_Message FROM Destinataires AS d
        WHERE d.Destinataire IN (
            SELECT p.SSN FROM Personne AS p
            WHERE p.Sexe = 'M'
        )
    )
);


/*
-- EX 11
Quel est le message le plus récent ? Utilisez ANY ou ALL. 
>> (M6)
*/

SELECT m.ID_Message FROM Message AS m
WHERE m.Date_Expedition >= ALL (
    SELECT m.Date_Expedition FROM Message AS m
);


/*
-- EX 12
Quelle est la personne la plus jeune ? Ecrivez une version 
avec ANY ou ALL et une seconde avec une liaison naturelle. 
>> (Remy)
*/

SELECT p.NOM FROM Personne AS p
WHERE p.Age <= ALL (
    SELECT p.Age FROM Personne AS p
);

SELECT p.NOM FROM Personne AS p
WHERE p.Age = (
    SELECT MIN(p.Age) FROM Personne AS p
);


/*
-- EX 13
Afficher le contenu de tous les messages sauf le plus ancien.
>> (5 réponses)
*/

SELECT m.Contenu FROM Message AS m
WHERE m.ID_Message NOT IN (
    SELECT m.ID_Message FROM Message AS m
    WHERE m.Date_Expedition = (
        SELECT MIN(m.Date_Expedition) FROM Message AS m
    )
);


/*
-- EX 14
Afficher la ou les personnes qui ont le plus d’amis. 
(Paul et Julie)
*/

SELECT p.NOM FROM Personne AS p
WHERE p.SSN IN (
    SELECT a.SSN1 FROM EstAmi AS a
    GROUP BY a.SSN1
    HAVING COUNT(*) >= ALL (
        SELECT COUNT(*) FROM EstAmi AS a
        GROUP BY a.SSN1
    )
);



