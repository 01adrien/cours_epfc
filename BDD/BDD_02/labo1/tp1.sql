set search_path = bdge, bdlivre;


-- EX 1
-- Quels sont les traducteurs français des livres parus en français et dont le titre parle d'intelligence artificielle ?



SELECT * FROM bdlivre.traducteur_ecrivain as trad
JOIN bdlivre.traduit_par AS tdp ON tdp.numtrad = trad.idtrad_ecriv
JOIN bdlivre.livre_paru AS lp ON lp.idlivre = tdp.numlivre
WHERE trad.nat = 'Fr' AND lp.langue = 'Fr' AND lp.titre LIKE '%Intelligence artificielle%';

-- EX 2 
-- Quels écrivains ont écrit une œuvre publiée chez Gallimard avant l’an 2000 ?

SELECT * FROM bdlivre.traducteur_ecrivain as te
JOIN bdlivre.ecrit_par AS ep ON ep.numecriv = te.idtrad_ecriv
JOIN bdlivre.livre_paru AS lp ON lp.numoeuvre = ep.numoeuvre
WHERE lp.editeur = 'Gallimard' AND EXTRACT (YEAR FROM lp.date_parution) < 2000;

-- EX 3
-- Quelles œuvres n'ont été publiées qu'une fois ? Affichez toutes les colonnes.
-- Faites une version avec jointure et une version avec sous-requête. 
-- Quel est l'avantage de travailler avec une sous-requête ?


-- EX 4
-- Quels sont les livres qui ont nécessité plus d'un traducteur ? 2 12 13

SELECT lp.titre, tp.numlivre FROM bdlivre.livre_paru AS lp
JOIN bdlivre.traduit_par AS tp ON lp.idlivre = tp.numlivre
GROUP BY lp.titre, tp.numlivre
HAVING COUNT(*) > 1;

-- EX 5
-- Quels sont les écrivains qui sont aussi traducteurs ?

SELECT td.nom FROM bdlivre.traducteur_ecrivain AS td
JOIN bdlivre.ecrit_par AS ep ON ep.numecriv = td.idtrad_ecriv
JOIN bdlivre.traduit_par AS tp ON tp.numtrad = td.idtrad_ecriv
WHERE ep.numecriv = tp.numtrad;



-- EX 6
-- Calculez le nombre de traducteurs différents pour chacune des œuvres de la BDD. 
-- Attention voir apparaître le renseignement 0 pour les œuvres non traduites et celles non publiées ! 2 9 12 13 14 15

SELECT o.titre, COUNT(DISTINCT tp.numtrad) AS trad_count FROM bdlivre.oeuvre AS o
LEFT JOIN bdlivre.livre_paru AS lp ON lp.numoeuvre = o.idoeuvre
LEFT JOIN bdlivre.traduit_par AS tp ON tp.numlivre = lp.idlivre
GROUP BY o.idoeuvre
;


-- EX 7
-- Quelles sont les œuvres qui n'ont jamais été traduites par Laurent Miclet ? n 10

SELECT * FROM bdlivre.oeuvre AS o
WHERE o.idoeuvre NOT IN (
	SELECT o.idoeuvre FROM bdlivre.oeuvre AS o
	JOIN bdlivre.livre_paru AS lp ON lp.numoeuvre = o.idoeuvre
	JOIN bdlivre.traduit_par AS tp ON tp.numlivre = lp.idlivre
	JOIN bdlivre.traducteur_ecrivain AS te ON te.idtrad_ecriv = tp.numtrad
	WHERE te.prenom = 'Laurent' AND te.nom = 'Miclet'
);


-- EX 8
-- Trouvez le nom d'origine des œuvres qui ont été traduites par au moins 2 traducteurs français différents.    

SELECT o.idoeuvre, o.titre FROM bdlivre.oeuvre AS o
JOIN bdlivre.livre_paru AS lp ON lp.numoeuvre = o.idoeuvre
JOIN bdlivre.traduit_par AS tp ON tp.numlivre = lp.idlivre
JOIN bdlivre.traducteur_ecrivain AS te ON te.idtrad_ecriv = tp.numtrad
WHERE te.nat = 'Fr'
GROUP BY o.titre, o.idoeuvre
HAVING COUNT(DISTINCT te.idtrad_ecriv) > 1;

-- EX 9
-- Quelles œuvres ont été publiées plusieurs fois chez le même éditeur ?
SELECT o.idoeuvre, o.titre FROM bdlivre.oeuvre AS o
JOIN bdlivre.livre_paru AS lp on o.idoeuvre = lp.numoeuvre
GROUP BY o.idoeuvre, lp.editeur, o.titre
HAVING COUNT(*) > 1 ;

-- EX 10
-- Supprimez de la BD en une requête tous les livres dont au moins un traducteur est né après 1950.

-- EX 11
-- Supprimez de la BD en une requête tous les livres dont tous les traducteurs sont nés après 1950.
