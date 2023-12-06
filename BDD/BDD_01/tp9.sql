SET GLOBAL SQL_MODE = CONCAT(@@SQL_MODE, ',ONLY_FULL_GROUP_BY');

-- EX 1

SELECT DISTINCT p.PNAME FROM p
WHERE p.ID_P IN (
    SELECT spj.ID_P FROM spj WHERE spj.QTY = 500
); 

-- EX 2

SELECT DISTINCT p.PNAME FROM p
WHERE p.ID_P IN (
    SELECT spj.ID_P FROM spj WHERE spj.ID_S = 'S2'
);

-- EX 3

SELECT s.SNAME FROM s
WHERE s.ID_S IN (
    SELECT spj.ID_S FROM spj WHERE spj.ID_P = 'P3'
);

-- EX 4

SELECT s.SNAME FROM s
WHERE s.ID_S IN (
    SELECT spj.ID_S FROM spj
    JOIN p ON p.ID_P = spj.ID_P
    WHERE p.COLOR = 'RED'
);