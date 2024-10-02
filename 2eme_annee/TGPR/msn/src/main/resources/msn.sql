    -- phpMyAdmin SQL Dump
    -- version 4.9.1
    -- https://www.phpmyadmin.net/
    --
    -- Hôte : 127.0.0.1
    -- Généré le :  ven. 17 juil. 2020 à 19:35
    -- Version du serveur :  10.4.8-MariaDB
    -- Version de PHP :  7.3.10

    SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
    SET AUTOCOMMIT = 0;
    START TRANSACTION;
    SET time_zone = "+00:00";


    /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
    /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
    /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
    /*!40101 SET NAMES utf8mb4 */;

    --
    -- Base de données :  `tgpr-msn`
    --

    DROP DATABASE IF EXISTS `tgpr-msn`;
    CREATE DATABASE `tgpr-msn` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
    USE `tgpr-msn`;

    -- --------------------------------------------------------

    --
    -- Structure de la table `follows`
    --

    DROP TABLE IF EXISTS `follows`;
    CREATE TABLE `follows` (
      `follower` varchar(128) NOT NULL,
      `followee` varchar(128) NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

    --
    -- Déchargement des données de la table `follows`
    --

    INSERT INTO `follows` (`follower`, `followee`) VALUES
    ('admin', 'ben'),
    ('admin', 'guest'),
    ('ben', 'admin'),
    ('ben', 'caro'),
    ('ben', 'fred'),
    ('ben', 'guest'),
    ('bob', 'ben'),
    ('caro', 'ben'),
    ('caro', 'fred');

    -- --------------------------------------------------------

    --
    -- Structure de la table `members`
    --

    DROP TABLE IF EXISTS `members`;
    CREATE TABLE `members` (
      `pseudo` varchar(128) NOT NULL,
      `password` varchar(128) NOT NULL,
      `profile` text DEFAULT NULL,
      `admin` tinyint(1) NOT NULL DEFAULT 0,
      `birthdate` date DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

    --
    -- Déchargement des données de la table `members`
    --

    INSERT INTO `members` (`pseudo`, `password`, `profile`, `admin`, `birthdate`) VALUES
    ('admin', 'c6aa01bd261e501b1fea93c41fe46dc7', 'Je suis l\'admin.', 1, NULL),
    ('ben', 'cc4902e2506fc6de54e53489314c615a', 'Benoît Penelle', 0, '1970-07-01'),
    ('bob', '6bc8d5a0ad1818c0924255f5e56e68c6', 'Bob l\'éponge', 0, NULL),
    ('caro', 'e82d99e3aaa83e1746bda2a58b99ba17', 'Caroline de Monaco', 0, NULL),
    ('fred', '90598d58045d3548866f853df199fb55', NULL, 0, NULL),
    ('marc', 'b41216a574ab900d4911cce4f4941a00', 'Marc Michel', 0, '1987-02-26'),
    ('guest', 'b6384a74aaf072666c8fd7c9ce58c428', NULL, 0, NULL);

    -- --------------------------------------------------------

    --
    -- Structure de la table `messages`
    --

    DROP TABLE IF EXISTS `messages`;
    CREATE TABLE `messages` (
      `post_id` int(11) NOT NULL,
      `author` varchar(128) NOT NULL,
      `recipient` varchar(128) NOT NULL,
      `body` text NOT NULL,
      `private` tinyint(1) NOT NULL DEFAULT 0,
      `date_time` datetime NOT NULL DEFAULT current_timestamp()
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

    --
    -- Déchargement des données de la table `messages`
    --

    -- INSERT INTO `messages` (`post_id`, `author`, `recipient`, `body`, `private`, `date_time`) VALUES
    -- (2, 'ben', 'ben', 'message 1', 0, '2021-07-09 10:11:33'),
    -- (3, 'ben', 'ben', 'message 2', 0, '2021-07-09 10:12:59'),
    -- (5, 'caro', 'ben', 'message de caro', 0, '2021-07-09 10:14:03'),
    -- (8, 'ben', 'ben', 'test', 1, '2021-07-09 10:58:10'),
    -- (9, 'ben', 'ben', 'test', 0, '2021-07-09 10:58:15'),
    -- (19, 'caro', 'caro', 'myself', 0, '2021-07-09 11:29:20'),
    -- (47, 'ben', 'caro', 'a longer message for caro in order to see how it is wrapped around in the message table.', 0, '2021-07-09 11:34:44'),
    -- (48, 'ben', 'fred', 'this is a message to fred', 0, '2021-07-09 18:15:27'),
    -- (49, 'ben', 'fred', 'this is a private message to fred', 1, '2021-07-09 18:15:36'),
    -- (58, 'ben', 'fred', 'hello', 0, '2021-07-19 00:16:01'),
    -- (59, 'ben', 'fred', 'aaa', 0, '2021-07-19 00:17:41'),
    -- (61, 'admin', 'admin', 'test', 0, '2021-10-30 11:32:37'),
    -- (86, 'ben', 'caro', 'ben to caro', 0, '2021-12-16 12:50:29');

    --
    -- Index pour les tables déchargées
    --

    --
    -- Index pour la table `follows`
    --
    ALTER TABLE `follows`
      ADD PRIMARY KEY (`follower`,`followee`),
      ADD KEY `members_followee_fk` (`followee`);

    --
    -- Index pour la table `members`
    --
    ALTER TABLE `members`
      ADD PRIMARY KEY (`pseudo`);

    --
    -- Index pour la table `messages`
    --
    ALTER TABLE `messages`
      ADD PRIMARY KEY (`post_id`),
      ADD KEY `members_author_fk` (`author`),
      ADD KEY `members_recipient_fk` (`recipient`);

    --
    -- AUTO_INCREMENT pour les tables déchargées
    --

    --
    -- AUTO_INCREMENT pour la table `messages`
    --
    ALTER TABLE `messages`
      MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

    --
    -- Contraintes pour les tables déchargées
    --

    --
    -- Contraintes pour la table `follows`
    --
    ALTER TABLE `follows`
      ADD CONSTRAINT `members_followee_fk` FOREIGN KEY (`followee`) REFERENCES `members` (`pseudo`),
      ADD CONSTRAINT `members_follower_fk` FOREIGN KEY (`follower`) REFERENCES `members` (`pseudo`);

    --
    -- Contraintes pour la table `messages`
    --
    ALTER TABLE `messages`
      ADD CONSTRAINT `members_author_fk` FOREIGN KEY (`author`) REFERENCES `members` (`pseudo`),
      ADD CONSTRAINT `members_recipient_fk` FOREIGN KEY (`recipient`) REFERENCES `members` (`pseudo`);
    COMMIT;

    /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
    /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
    /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
