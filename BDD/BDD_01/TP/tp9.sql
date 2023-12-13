SET GLOBAL SQL_MODE = CONCAT(@@SQL_MODE, ',ONLY_FULL_GROUP_BY');


-- EX 1

SELECT DISTINCT p.PNAME FROM p WHERE p.ID_P IN (
    SELECT spj.ID_P FROM spj WHERE spj.QTY = 500
); 


-- EX 2

SELECT DISTINCT p.PNAME FROM p WHERE p.ID_P IN (
    SELECT spj.ID_P FROM spj WHERE spj.ID_S = 'S2'
);


-- EX 3

SELECT s.SNAME FROM s WHERE s.ID_S IN (
    SELECT spj.ID_S FROM spj WHERE spj.ID_P = 'P3'
);


-- EX 4 with JOIN

SELECT s.SNAME FROM s WHERE s.ID_S IN (
    SELECT spj.ID_S FROM spj 
    JOIN p ON p.ID_P = spj.ID_P
    WHERE p.COLOR = 'RED'
);


-- EX 4 without JOIN

SELECT s.SNAME FROM s WHERE s.ID_S IN (
    SELECT spj.ID_S FROM spj WHERE spj.ID_P IN (
        SELECT p.ID_P FROM p WHERE p.COLOR = 'RED'
    )
);


-- EX 5 with JOIN

SELECT s.SNAME, s.ID_S FROM s WHERE s.ID_S IN (
    SELECT spj.ID_S FROM spj
        JOIN j ON j.ID_J = spj.ID_J
            WHERE j.JNAME = 'Console'
        );


-- EX 5 without JOIN

SELECT s.SNAME, s.ID_S FROM s WHERE s.ID_S IN (
    SELECT spj.ID_S FROM spj WHERE spj.ID_J IN (
        SELECT j.ID_J FROM j WHERE j.JNAME = 'Console'
    )
);

-- EX 6

SELECT j.JNAME FROM j WHERE j.ID_J IN (
    SELECT spj.ID_J FROM spj
    GROUP BY spj.ID_J
    HAVING SUM(spj.QTY) > 1000
);

-- EX 7

SELECT p.PNAME FROM p WHERE p.COLOR = ANY (
    SELECT p.COLOR FROM p 
    GROUP BY p.COLOR
    HAVING COUNT(*) = 1
);

-- EX 8

SELECT p.PNAME FROM p WHERE p.WEIGHT >= ALL (
    SELECT p.WEIGHT FROM p
);

SELECT p.PNAME FROM p WHERE p.WEIGHT = (
    SELECT MAX(p.WEIGHT) FROM p
);

-- EX 9

SELECT s.ID_S FROM s WHERE s.ID_S NOT IN (
    SELECT DISTINCT spj.ID_S FROM spj
    JOIN p ON p.ID_P = spj.ID_P
    WHERE p.COLOR = 'Blue'
); 


SELECT s.ID_S FROM s WHERE s.ID_S NOT IN (
    SELECT spj.ID_S FROM spj WHERE spj.ID_P IN (
        SELECT p.ID_P FROM p WHERE p.COLOR = 'Blue'
    )
); 

-- EX 10

SELECT COUNT(*) FROM spj
WHERE spj.ID_S NOT IN (
    SELECT spj.ID_S FROM spj
    JOIN j ON j.ID_J = spj.ID_J
    WHERE j.CITY = 'Paris'
) AND spj.QTY < 350;
