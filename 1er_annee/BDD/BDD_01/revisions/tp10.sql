/*
EX 1
Afficher le contenu des messages envoyés par un homme.
>>
Bonjour
Salut
Regarde cette vidéo de chat
Trololololol
*/

SELECT m.contenu FROM Message AS m WHERE m.expediteur IN (
    SELECT p.ssn FROM Personne AS p WHERE p.sexe = 'M'
);

/*
EX 2
Afficher le nom des personnes qui ont reçu le message M4
>> (Julie, Marie)
*/

SELECT p.nom FROM Personne AS p WHERE p.ssn IN (
    SELECT d.destinataire FROM Destinataires AS d WHERE d.id_message = 'M4' 
);

/*
EX 3
Afficher le contenu des messages reçus par une femme de moins de 30 ans
>>
Salut
Regarde cette vidéo de chat
Trololololol
*/

SELECT m.contenu FROM Message AS m WHERE m.id_message IN (
    SELECT d.id_message FROM Destinataires AS d WHERE d.destinataire IN (
        SELECT p.ssn FROM Personne AS p WHERE p.sexe = 'F' AND p.age < 30
    )
);

/*
EX 4
Afficher le nom des amis de la personne P1.
>>
Julie
David
Xavier
*/

SELECT p.nom FROM Personne AS p WHERE p.ssn IN (
    SELECT a.ssn2 FROM EstAmi AS a WHERE a.ssn1 = 'P1' 
);


/*
EX 5
Quels sont les amis des amis de P1 ?
>>  (P1, P2, P3, P4)
*/

SELECT DISTINCT a.ssn2 FROM EstAmi AS a WHERE a.ssn1 IN (
    SELECT a.ssn2 FROM EstAmi AS a WHERE a.ssn1 = 'P1' 
);

/*
EX 6
Quels sont les amis des amis de Xavier?
>>  (P1, P2, P3, P4)
*/

SELECT DISTINCT a.ssn2 FROM EstAmi AS a WHERE a.ssn1 IN (
    SELECT a.ssn2 FROM EstAmi AS a WHERE a.ssn1 IN (
        SELECT p.ssn FROM Personne AS p WHERE p.nom = 'Xavier'
    ) 
);

/*
EX 7
Quelles sont les personnes qui ont envoyé un message 
à une femme et qui ont reçu un message d’un homme ?
>> Xavier
*/

SELECT p.nom FROM Personne AS p WHERE p.ssn IN (
    SELECT m.expediteur FROM Message AS m WHERE m.id_message IN (
        SELECT d.id_message FROM Destinataires AS d WHERE d.destinataire IN (
            SELECT p.ssn FROM Personne AS p WHERE p.sexe = 'F'
        )
    )
) AND p.ssn IN (
    SELECT d.destinataire FROM Destinataires AS d WHERE d.id_message IN (
        SELECT m.id_message FROM Message AS m WHERE m.expediteur IN (
            SELECT p.ssn FROM Personne AS p WHERE p.sexe = 'M' 
        )
    )
);


/*
EX 8
Quelle personne n’a envoyé aucun message ?
>> David
*/

SELECT p.nom FROM Personne AS p WHERE p.ssn NOT IN (
    SELECT m.expediteur FROM Message AS m
);


/*
EX 9
Quelle personne n’a reçu aucun message ?
>> PAUL
*/

SELECT p.nom FROM Personne AS p WHERE p.ssn NOT IN (
    SELECT d.destinataire FROM Destinataires AS d
);


/*
EX 10
Qui n’a jamais envoyé de message à un homme 
>> ( David, Xavier et Remy )
*/

SELECT p.nom FROM Personne AS p WHERE p.ssn NOT IN (
    SELECT m.expediteur FROM Message AS m WHERE m.id_message IN (
        SELECT d.id_message FROM Destinataires AS d WHERE d.destinataire IN (
            SELECT p.ssn FROM Personne AS p WHERE p.sexe = 'M'
        )
    )
);


/*
EX 11
Quel est le message le plus récent ? Utilisez ANY ou ALL.
>> M6
*/

SELECT m.id_message FROM Message AS m WHERE m.date_expedition >= ALL (
    SELECT m.date_expedition FROM Message AS m
);


/*
EX 12
quelle est la personne la plus jeune ?
>> ( Remy )
*/

SELECT p.nom FROM Personne AS p WHERE p.age <= ALL (
    SELECT p.age FROM Personne AS p
);

SELECT p.nom FROM Personne AS p WHERE p.age = (
    SELECT MIN(p.age) FROM Personne AS p
);


/*
EX 13
Afficher le contenu de tous les messages sauf le plus ancien
*/

SELECT m.contenu FROM Message AS m WHERE m.date_expedition != (
    SELECT MIN(m.date_expedition) FROM Message AS m
);

/*
EX 14
Afficher la ou les personnes qui ont le plus d’amis.
>> ( Paul, Julie )
*/

SELECT p.nom FROM EstAmi AS a 
JOIN Personne AS p ON p.ssn = a.ssn1
GROUP BY a.ssn1, p.nom
HAVING COUNT(*) >= ALL (
    SELECT COUNT(*) FROM EstAmi AS a GROUP BY a.ssn1
);







