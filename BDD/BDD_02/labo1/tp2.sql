set search_path = bdge, bdlivre;

-- EX 1
-- Créez une vue nommée Livre_eponyme avec le idLivre, le titre, l'année de parution du livre,
-- l'année d'écriture de l'oeuvre originale ne reprenant que les livres ayant le même titre que cette oeuvre.

-- CREATE VIEW livre_eponyme AS
SELECT lp.idlivre, lp.titre, EXTRACT(YEAR FROM lp.date_parution), o.annee
FROM bdlivre.livre_paru AS lp
JOIN bdlivre.oeuvre o on o.idoeuvre = lp.numoeuvre
WHERE lp.titre = o.titre;

-- EX 2
-- Créez une vue Auteurs_Editeur dont l'affichage listerait les noms, prénoms des différents écrivains
-- de la BD avec en regard les titres des oeuvres qu'ils ont écrites, les éditeurs chez qui elles ont été
-- publiées, et leur date de parution .
-- Nous ne voulons pas voir apparaître de renseignements liés à des éditeurs inconnus (editeur à NULL).

CREATE VIEW auteurs_editeurs AS
SELECT te.prenom, te.nom, o.titre, lp.editeur, lp.date_parution
FROM bdlivre.traducteur_ecrivain AS te
JOIN bdlivre.ecrit_par AS ep ON ep.numecriv = te.idtrad_ecriv
JOIN bdlivre.oeuvre AS o ON o.idoeuvre = ep.numoeuvre
JOIN bdlivre.livre_paru lp on o.idoeuvre = lp.numoeuvre
WHERE lp.editeur IS NOT NULL
;


