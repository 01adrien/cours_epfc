SET GLOBAL SQL_MODE = CONCAT(@@SQL_MODE, ',ONLY_FULL_GROUP_BY');

/*
EX 1
Selectionner le nom des employ´es, adresse, ville et r´egion o`u ils habitent.
*/

SELECT e.LastName, e.Address, e.City, e.Region FROM Employees AS e;

/*
EX 2
S´electionner le nom des employ´es et le nom des clients pour les commandes qui sont
envoy´ees par la soci´et´e ‘Speedy Express’ aux clients qui habitent `a Bruxelles
*/

SELECT e.LastName AS employee, c.CompanyName AS customer FROM Orders AS o
JOIN Employees AS e ON e.EmployeeID = o.EmployeeID
JOIN Customers AS c ON c.CustomerID = o.CustomerID
JOIN Shippers aS s ON s.ShipperID = o.ShipperID
WHERE c.City = 'Bruxelles' AND s.CompanyName = 'Speedy Express';

/*
EX 3
S´electionner le titre et le nom des employ´es qui ont vendu au moins un des produits
‘Gravad Lax’ ou ‘Mishi Kobe Niku’.
*/

SELECT DISTINCT e.LastName, e.Title FROM Employees AS e WHERE e.EmployeeID IN (
    SELECT o.EmployeeID FROM Orders AS o WHERE o.OrderID IN (
        SELECT od.OrderID FROM OrderDetails AS od WHERE od.ProductID IN (
            SELECT p.ProductID FROM Products AS p WHERE p.ProductName IN (
                'Gravad lax', 'Mishi Kobe Niku'
            )
        )
    )
);

/*
EX 4
Selectionner le nom et titre des employ´es ainsi que le nom et titre des personnes `a
qui ils se reportent (si une telle personne existe)
*/

SELECT DISTINCT e1.LastName, e1.Title, e2.LastName, e2.Title FROM Employees AS e1
JOIN Employees AS e2 ON e2.EmployeeID = e1.ReportsTo ;

/*
EX 5
S´electionner le nom des clients, le nom des produits et le nom des fournisseurs pour
les clients qui habitent `a London et les fournisseurs qui s’appellent ‘Pavlova, Ltd.’
ou ‘Karkki Oy’.
*/

SELECT DISTINCT c.CompanyName AS C, p.ProductName AS P, s.CompanyName AS S FROM Orders AS o
JOIN Customers AS c ON c.CustomerID = o.CustomerID
JOIN OrderDetails AS od ON od.OrderID = o.OrderID
JOIN Products AS p ON p.ProductID = od.ProductID
JOIN Suppliers AS s ON s.SupplierID = p.SupplierID
WHERE c.City = 'London' AND s.CompanyName IN ('Pavlova, Ltd.', 'Karkki Oy');


/*
EX 6
S´electionner le nom des produits qui ont ´et´e achet´es ou vendus par des personnes
(clients ou employ´es) qui habitent `a London. En SQL, ´ecrire deux versions de la
requˆete, une avec union et l’autre avec in.
*/

SELECT DISTINCT p.ProductName FROM Products AS p WHERE p.ProductID IN (
    SELECT od.ProductID FROM OrderDetails AS od WHERE OrderID IN (
        SELECT o.OrderID FROM Orders AS o WHERE o.EmployeeID IN (
            SELECT e.EmployeeID FROM Employees AS e WHERE e.City = 'London'
        ) OR o.CustomerID IN (
            SELECT c.CustomerID FROM Customers AS c WHERE c.City = 'London'
        )
    )
);

/*
EX 9
Sp´ecifier le nom des employ´es et la ville o`u ils habitent pour les employ´es qui ont
vendu `a des clients de la mˆeme ville. En SQL, ´ecrire trois versions de la requˆete en
utilisant une jointure, in et exists.
*/

SELECT DISTINCT e.LastName, e.City FROM Orders AS o
JOIN Customers AS c ON c.CustomerID = o.CustomerID
JOIN Employees AS e ON e.EmployeeID = o.EmployeeID
WHERE e.City = c.City;

-- AVEC IN

SELECT e.LastName, e.City FROM Employees AS e WHERE e.EmployeeID IN (
    SELECT o.EmployeeID FROM Orders AS o WHERE o.CustomerID IN (
        SELECT c.CustomerID FROM Customers AS c WHERE c.City = e.City
    )
);

-- AVEC EXISTS

SELECT e.LastName, e.City FROM Employees AS e WHERE EXISTS (
    SELECT * FROM Orders AS o  WHERE EXISTS (
        SELECT * FROM Customers AS c WHERE c.City = e.City
    )
);

/*
EX 10
S´electionner le nom des clients qui n’ont achet´e aucun produit. En SQL, ´ecrire deux
versions de la requˆete en utilisant not exists et not in.
*/

SELECT c.CompanyName FROM Customers AS c WHERE c.CustomerID NOT IN (
    SELECT o.CustomerID FROM Orders AS o
);

-- avec EXISTS

SELECT c.CompanyName FROM Customers AS c WHERE NOT EXISTS (
    SELECT * FROM Orders AS o WHERE o.CustomerID = c.CustomerID
);

/*
EX 11
Sp´ecifier le nom des clients qui ont achet´e tous les produits.
*/

SELECT c.CompanyName FROM Orders AS o
JOIN Customers AS c ON c.CustomerID = o.CustomerID
JOIN OrderDetails AS od ON od.OrderID = o.OrderID
GROUP BY o.CustomerID, c.CompanyName
HAVING COUNT(DISTINCT od.ProductID) = (
    SELECT COUNT(*) FROM Products
);

/*
EX 12
S´electionner le nom des produits vendus par tous les employ´es.
*/

SELECT p.ProductName FROM Orders AS o
JOIN OrderDetails AS od ON od.OrderID = o.OrderID 
JOIN Products AS p ON p.ProductID = od.ProductID
GROUP BY p.ProductName, p.ProductID
HAVING COUNT(DISTINCT o.EmployeeID) = (
    SELECT COUNT(*) FROM Employees
);

/*
EX 13
S´electionner le nom des clients qui ont achet´e tous les produits achet´es par la soci´et´e
dont l’identificateur est ‘LAZYK'
*/

SELECT c.CompanyName FROM Orders AS o
JOIN Customers AS c ON c.CustomerID = o.CustomerID
JOIN OrderDetails AS od ON od.OrderID = o.OrderID 
JOIN Products AS p ON p.ProductID = od.ProductID
JOIN Suppliers AS s ON s.SupplierID = p.SupplierID
WHERE s.CompanyName = 'LAZYK' 
GROUP BY c.CompanyName, c.CustomerID
HAVING COUNT(*) = (
    SELECT COUNT(*) FROM Products AS p
    JOIN Suppliers AS s ON s.SupplierID = p.SupplierID
    WHERE s.CompanyName = 'LAZYK'
)