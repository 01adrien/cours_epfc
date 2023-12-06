SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";                                                                                                                                                                        
SET time_zone = "+00:00";                                                                                                                                                                                      
                                                                                                                                                                                                           
-- Database: cours2                                                                                                                                                                                     
                                                                                                                                                                                                             
DROP DATABASE IF EXISTS cours2;                                                                                                                                                                            
CREATE DATABASE IF NOT EXISTS cours2 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;                                                                                                               
USE cours2;                     

-- Table: personne 

DROP TABLE IF EXISTS personne;
CREATE TABLE personne (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL
);

INSERT INTO personne (nom)
VALUES ('Paul'), ('Pierre'), ('Jules');

-- Table: professeur

DROP TABLE IF EXISTS professeur;
CREATE TABLE professeur (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL
);

INSERT INTO professeur (nom)
VALUES ('Andre'), ('Jacques');

-- Table: formation

DROP TABLE IF EXISTS formation;
CREATE TABLE formation (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cours VARCHAR(255) NOT NULL,
    titulaire INT NOT NULL,
    CONSTRAINT FK_prof FOREIGN KEY (titulaire) REFERENCES professeur(id)   
);

INSERT INTO formation (cours, titulaire)
VALUES ('Analyse', 2), ('SQL', 2), ('Cobol', 1);

-- Table: suivre

DROP TABLE IF EXISTS suivre;
CREATE TABLE suivre (
    cours INTEGER NOT NULL,
    stagiaire INTEGER NOT NULL,
    CONSTRAINT FK_formation FOREIGN KEY (cours) REFERENCES formation(id),
    CONSTRAINT FK_personne FOREIGN KEY (stagiaire) REFERENCES personne(id),
    PRIMARY KEY (cours, stagiaire)
);

INSERT INTO suivre (cours, stagiaire)
VALUES (1, 2), (1, 3), (2, 2);