
set search_path TO avions;

-- EX 1
-- Écrivez une contrainte de check vérifiant qu'un avion ayant 
-- plus de 200 places possède une taille supérieure à 60 m et au moins 2 réacteurs.

/*
nb_places > 200  => A
taille > 60      => B
nb_reacteurs > 1 => C

 
A -> (B && C)
!(A && (!B && !C))
!A || (B && C)
*/

ALTER TABLE avion 
DROP CONSTRAINT IF EXISTS c_ex1;
ALTER TABLE avion 
ADD CONSTRAINT c_ex1
CHECK (
    nb_places <= 200 OR (taille > 60 AND nb_reacteurs > 1)
);

INSERT INTO avion (noma, nb_places, taille, nb_reacteurs) VALUES ('A', 201, 61, 1); -- F
INSERT INTO avion (noma, nb_places, taille, nb_reacteurs) VALUES ('B', 201, 61, 2); -- OK
INSERT INTO avion (noma, nb_places, taille,nb_reacteurs) VALUES ('C', 201, 59, 1);  -- F
INSERT INTO avion (noma, nb_places, taille, nb_reacteurs) VALUES ('D', 201, 59, 2); -- F 
INSERT INTO avion (noma, nb_places, taille,nb_reacteurs) VALUES ('E', 199, 61, 1);  -- OK
INSERT INTO avion (noma, nb_places, taille,nb_reacteurs) VALUES ('F', 199, 61, 2);  -- OK
INSERT INTO avion (noma, nb_places, taille,nb_reacteurs) VALUES ('G', 199, 59, 1);  -- OK
INSERT INTO avion (noma, nb_places, taille,nb_reacteurs) VALUES ('H', 199, 59, 2);  -- OK
SELECT * FROM avion;
DELETE FROM avion WHERE ida > 6;


-- EX 2
-- Écrivez une vue compagnie_gros_avions qui présentera les données suivantes :
-- nom, fonds dont la signification est la suivante : nom est l'attribut nomc, 
-- fonds est l'attribut fonds_propres de la table compagnie. Cette vue sélectionnera 
-- les compagnies affrétant uniquement des avions d'au moins 300 places.

CREATE OR REPLACE VIEW compagnie_gros_avions AS
SELECT c.nomc AS nom, c.fonds_propres AS fonds FROM compagnie AS c
JOIN affrete AS af ON af.numc = c.idc
JOIN avion AS av ON av.ida = af.numa
WHERE av.nb_places >= 300;


-- EX 3
-- Écrivez une fonction table recevant un paramètre fonds de type int
-- et qui retourne une relation dont chaque ligne est constituée de deux colonnes :
-- nomc et plus_cher_pilote. Chaque ligne associe le nom d’une compagnie et un prix qui est 
-- celui du pilote qui peut travailler pour elle et qui a le plus haut prix_par_vol. 
-- On se restreint aux compagnies ayant plus de fonds_propres que le paramètre fonds reçu par la fonction.

-- GROUP BY + HAVING
DROP FUNCTION IF EXISTS most_expensive_pilot;
CREATE FUNCTION most_expensive_pilot (fp int)
RETURNS TABLE (company_name VARCHAR, max_price_pilot INT)
AS
$$
    SELECT c.nomc, MAX(p.prix_par_vol) FROM compagnie AS c
    JOIN peut_travailler AS pt ON pt.numc = c.idc
    JOIN pilote AS p ON p.idp = pt.nump
    WHERE c.fonds_propres > fp 
    GROUP BY c.idc, c.nomc
$$ LANGUAGE SQL;


-- SOUS REQUETE CORRELEE
DROP FUNCTION IF EXISTS most_expensive_pilot_c;
CREATE FUNCTION most_expensive_pilot_c (fp int)
RETURNS TABLE (company_name VARCHAR, max_price_pilot INT)
AS
$$
    SELECT c.nomc, p.prix_par_vol FROM compagnie AS c
    JOIN peut_travailler AS pt1 ON pt1.numc = c.idc
    JOIN pilote AS p ON p.idp = pt1.nump
    WHERE c.fonds_propres > fp AND p.prix_par_vol >= ALL (
        SELECT p.prix_par_vol FROM pilote AS p
        JOIN peut_travailler AS pt2 ON pt2.nump = p.idp
        WHERE pt2.numc = pt1.numc
    )
$$ LANGUAGE SQL;
   
SELECT * FROM most_expensive_pilot(0);
SELECT * FROM most_expensive_pilot_c(0);


/*
C1 => P1
C2 => P1 P2 P5
C2 => P5

P1 => 150$
P2 => 350$
P5 => 100$
*/