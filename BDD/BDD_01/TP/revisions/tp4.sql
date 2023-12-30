/*
EX 1
Afficher le nom des personnes qui ont plus de 30 ans. 
>> (2 réponses)
*/

SELECT p.nom FROM Personne AS p
WHERE p.age > 30;

/*
EX 2
Afficher le nom des femmes qui ont moins de 30 ans.
>> (2 réponses)
*/

SELECT p.nom FROM Personne AS p
WHERE p.age < 30 AND p.sexe = 'F';

/*
EX 3
Afficher l’identifiant des messages envoyés par la personne P1. 
(2 réponses)
*/

SELECT m.id_message FROM Message AS m
WHERE m.expediteur = 'P1';

/*
EX 4
Afficher le nom des hommes dans l’ordre alphabétique.
>> (4 réponses)
*/

SELECT p.nom FROM Personne AS p 
ORDER BY p.nom ASC;

/*
EX 5
Afficher le contenu des messages envoyés par un homme.
>> (4 réponses)
*/

SELECT DISTINCT m.contenu FROM Message AS m
JOIN Personne AS p ON p.SSN = m.expediteur
WHERE p.sexe = 'M';

/*
EX 6
Afficher le nom des personnes qui ont reçu le message M4.
>> (2 réponses)
*/

SELECT p.nom FROM Personne AS p
WHERE p.SSN IN (
    SELECT d.destinataire FROM Destinataires AS d 
    WHERE d.id_message = 'M4'
);

/*
EX 7
Afficher le contenu des messages reçus par une femme de moins de 30 ans. 
>>(3 réponses)
*/

SELECT DISTINCT m.contenu FROM Message AS m
JOIN Destinataires AS d ON d.id_message = m.id_message
JOIN Personne AS p ON d.destinataire = p.ssn
WHERE p.age < 30 AND p.sexe = 'F'; 

/*
EX 8
Afficher le nom des amis de la personne P1.
>> (3 réponses)
*/

SELECT p.nom FROM Personne AS p
JOIN EstAmi AS a ON a.ssn2 = p.ssn
WHERE a.ssn1 = 'P1';

/*
EX 9
Afficher le contenu des messages envoyés par une personne
dont le nom contient la lettre « e ».
>> (4 réponses)
*/

SELECT DISTINCT m.contenu FROM Message AS m
JOIN Personne AS p ON p.SSN = m.expediteur
WHERE p.nom LIKE '%e%';


/*
EX 10
Afficher la date d’expédition des messages reçus 
par une femme dont le nom commence par « M ». 
>> (2 réponses)
*/

SELECT DISTINCT m.date_expedition FROM Message AS m
JOIN Destinataires AS d ON d.id_message = m.id_message
JOIN Personne AS p ON p.SSN = d.destinataire
WHERE p.sexe = 'F' AND p.nom LIKE 'M%';

/*
EX 11
Afficher le contenu des messages envoyés par un homme
dont la seconde lettre du nom est « a ».
>>(3 réponses)
*/

SELECT DISTINCT m.contenu FROM Message AS m
JOIN Personne AS p ON p.SSN = m.expediteur
WHERE p.nom LIKE '_a%' AND p.sexe = 'M';

/*
EX 12
Qui a envoyé un message à une personne de moins de 18 ans ?
>> (1 réponse)
*/

SELECT DISTINCT p.nom FROM Personne AS p
JOIN Message AS m ON m.expediteur = p.SSN
JOIN Destinataires AS d ON d.id_message = m.id_message
JOIN Personne AS p2 ON p2.SSN = d.destinataire
WHERE p2.age < 18;

/*
EX 13
Quels sont les amis des amis de P1 ?
>> (4 réponses)
*/

SELECT DISTINCT a2.ssn2 FROM EstAmi AS a2
JOIN EstAmi AS a1 ON a1.ssn2 = a2.ssn1
WHERE a1.ssn1 = 'P1';  

/*
EX 14
Quels sont les amis des amis du ou des personnes dont le nom est Xavier?
>> (4 réponses)
*/

SELECT DISTINCT a2.ssn2 FROM EstAmi AS a2
JOIN EstAmi AS a1 ON a1.ssn2 = a2.ssn1
JOIN Personne AS p ON a1.ssn1 = p.ssn
WHERE p.nom = 'Xavier';


/*
EX 15
Afficher les couples de personnes de sexe différent.
Remarque : si vous considérez tous les couples possibles 
(sans tenir compte des amitiés), vous obtiendrez 8 réponses. 
Si vous tenez compte des amitiés, vous obtiendrez 3 réponses. 
Essayez les deux.
*/

-- (a) --

SELECT p1.nom, p2.nom FROM Personne AS p1, Personne p2
WHERE p1.sexe < p2.sexe;