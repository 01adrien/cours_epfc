drop database if exists ie_groupby;
create database if not exists ie_groupby;
use ie_groupby;

set global sql_mode=concat(@@sql_mode, ',only_full_group_by');

create table Musicien (
    idMusicien int PRIMARY KEY AUTO_INCREMENT,
    nom varchar(50) not null,
    prenom varchar(50) not null,
    ddn date not null,
    ddd date
);

create table Groupe (
    idGroupe int PRIMARY KEY AUTO_INCREMENT,
	nom varchar(50) not null,
	price double not null,
	ville varchar(50) not null
);

create table Client (
	idClient int PRIMARY KEY AUTO_INCREMENT,
    prenom varchar(50) not null,
    nom varchar(50) not null,
    ville varchar(50) not null
);

create table Representation (
	numGroupe int,
    numClient int,
    dateRepresentation date not null,
    nbHeure int not null default 1,
    FOREIGN KEY (numGroupe) REFERENCES Groupe(idGroupe),
    FOREIGN KEY (numClient) REFERENCES Client(idClient),
    PRIMARY KEY (numGroupe, numClient, dateRepresentation)
);

create table Musicien_Groupe (
	numGroupe int,
    numMusicien int,
    FOREIGN KEY (numGroupe) REFERENCES Groupe(idGroupe),
    FOREIGN KEY (numMusicien) REFERENCES Musicien(idMusicien),
    PRIMARY KEY (numGroupe, numMusicien)
);

insert into Musicien (prenom, nom, ddn, ddd) values 
	('Angus', 'Young', '1955-03-31', null),
    ('Hugh', 'Laurie', '1959-06-11', null),
    ('Bill', 'Evans', '1929-08-16', '1980-09-15'),
    ('John', 'Coltrane', '1926-06-23', '1967-07-17'),
    ('Benny', 'Goodman', '1909-05-30', '1986-06-13')
;

insert into Groupe (nom, price, ville) values 
	('Big band 1', 120, 'Bruxelles'),
    ('Big band 2', 110, 'Bruxelles'),
    ('Band from TV', 75, 'New York'),
    ('AD/DC', 90, 'Melbourne'),
    ('Brussel singer', 25, 'Bruxelles')
;

insert into Musicien_Groupe (numMusicien, numGroupe) values 
	(1,4),
    (2,1),
    (2,2),
    (2,3),
    (3,1),
    (3,2),
    (4,1),
    (5,1),
    (5,2)
;

insert into Client (prenom, nom, ville) values 
	('Xavier', 'Pigeolet', 'Nivelles'),
	('Laëtitia', 'Bastide', 'Montpellier'),
    ('Boris', 'Verhaegen', 'Bruxelles'),
    ('Benoît', 'Penelle', 'Bruxelles'),
    ('Marc', 'Michel', 'Bruxelles'),
    ('Bruno', 'Lacroix', 'Bruxelles'),
    ('Éléonore', 'Pigeolet', 'Nivelles')
;

insert into Representation (numClient, numGroupe, dateRepresentation, nbHeure) values 
	(1, 1, '2022-01-02', 4),
    (1, 2, '2012-04-15', 3),
    (1, 3, '2015-07-11', 10),
    (1, 5, '2017-02-03', 6),
    (1, 5, '2021-02-11', 5),
    (1, 5, '2022-07-12', 10),
    (1, 5, '2019-04-04', 2),
    (2, 5, '2011-01-01', 4),
    (2, 5, '2000-12-13', 1),
    (2, 5, '2001-12-13', 2),
    (3, 4, '2021-07-07', 7),
    (4, 1, '2014-05-20', 8),
    (4, 1, '2015-05-20', 2),
    (5, 4, '2014-02-20', 3),
    (6, 1, '2014-05-20', 5),
    (7, 1, '2019-05-25', 1),
    (7, 1, '2017-02-24', 2),
    (7, 2, '2017-02-25', 3)
;