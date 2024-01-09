/*
EX 1
Afficher le nombre de destinataires du message M2 
>> (1).
*/

SELECT COUNT(d.destinataire) FROM Destinataires AS d
WHERE d.id_message = 'M2';  

/*
EX 2
Afficher l'âge de la personne la plus jeune 
>> (16).
*/

SELECT MIN(p.age) FROM Personne AS p;

/*
EX 3
Afficher la date du message le plus récent
>> (06/10/13).
*/

SELECT MAX(m.date_expedition) FROM Message AS m;

/*
EX 4
Afficher le nombre de personnes de moins de 30 ans
>> (4)
*/

SELECT COUNT(*) FROM Personne AS p
WHERE p.age < 30;

/*
EX 5
Afficher l'âge moyen des femmes
>> (23).
*/

SELECT AVG(p.age) FROM Personne AS p
WHERE p.sexe = 'F';

SELECT AVG(p.age) FROM Personne AS p
GROUP BY p.sexe
HAVING p.sexe = 'F';

/*
EX 6
Afficher la moyenne du nombre de destinataires par message
>> (1.333).
*/

SELECT (COUNT(*) / COUNT(DISTINCT d.id_message)) 
FROM Destinataires AS d;


/*
EX 7
Afficher le nombre de personnes par sexe
>> (F 2 et M 4)
*/

SELECT COUNT(*), p.sexe FROM Personne AS p
GROUP BY p.sexe;


/*
EX 8
Afficher le nombre d'amis de P1 et de P2
(P1 3 et P2 3).
*/

SELECT COUNT(*), a.ssn1 FROM EstAmi AS a
WHERE a.ssn1 IN ('P1', 'P2')
GROUP BY a.ssn1; 


/*
EX 9
Afficher les identifiants des personnes qui ont entre 1 et 2 amis 
>> (P3 et P4).
*/

SELECT a.ssn1, p.nom FROM EstAmi AS a
JOIN Personne AS p ON p.ssn = a.ssn1
GROUP BY a.ssn1, p.nom
HAVING COUNT(*) BETWEEN 1 AND 2;


/*
EX 10
Afficher le nombre de destinataires de chaque message
>> (M1 2, M2 1, M3 1, M4 2, M5 1, M6 1).
*/

SELECT d.id_message, COUNT(DISTINCT d.destinataire) AS count,
m.contenu, p.nom FROM Destinataires AS d 
JOIN Message AS m ON m.id_message = d.id_message
JOIN Personne AS p On p.ssn = m.expediteur 
GROUP BY d.id_message, m.contenu, p.nom;

/*
EX 11
Afficher les identifiants des messages adressés à deux personnes ou plus
>> (M1 et M4).
*/

SELECT d.id_message FROM Destinataires AS d
GROUP BY d.id_message 
HAVING COUNT(DISTINCT d.destinataire) > 1;

/*
EX 12
Afficher le nom des femmes qui ont envoyé un seul message
>> (Julie et Marie).
*/

SELECT p.nom FROM Message AS m
JOIN Personne AS p ON p.ssn = m.expediteur
GROUP BY m.expediteur, p.nom, p.sexe
HAVING COUNT(*) = 1 AND p.sexe = 'F';


/*
EX 13
Afficher le nombre de messages reçus en fonction du sexe du destinataire
>> (F 4 et M 4).
*/

SELECT p.sexe, COUNT(*) FROM Message AS m
JOIN Destinataires AS d ON d.id_message = m.id_message
JOIN Personne AS p ON p.ssn = d.destinataire
GROUP BY p.sexe;

/*
EX 14
Afficher le contenu et le nom de l'expéditeur
des messages envoyés à une (et une) seule personne.
*/

SELECT m.contenu, p.nom FROM Message AS m
JOIN Destinataires AS d ON d.id_message = m.id_message
JOIN Personne AS p ON p.ssn = m.expediteur
GROUP BY m.contenu, p.nom, d.id_message
HAVING COUNT(DISTINCT d.Destinataire) = 1;
