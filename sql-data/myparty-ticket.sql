-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Ven 15 Mars 2013 à 20:23
-- Version du serveur: 5.5.29
-- Version de PHP: 5.3.10-1ubuntu3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `myparty`
--

-- --------------------------------------------------------

--
-- Structure de la table `Artist`
--

CREATE TABLE IF NOT EXISTS `Artist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Contenu de la table `Artist`
--

INSERT INTO `Artist` (`id`, `name`, `type`) VALUES
(1, 'BEYONCE', 'RnB'),
(2, 'LANA DEL REY', 'POP-ROCK'),
(3, 'BRUNO MARS', 'POP-ROCK'),
(4, 'IRON MAIDEN', 'HARD-ROCK'),
(5, 'JOHNNY HALLYDAY', 'VARIETE'),
(6, 'MAROON 5', 'POP-ROCK'),
(7, 'SNOOP DOGG', 'RAP'),
(8, 'LA FOUINE', 'RAP'),
(9, 'MYLENE FARMER', 'VARIETE'),
(10, 'ALICIA KEYS', 'RnB'),
(11, 'JUSTIN BIEBER', 'POP-ROCK'),
(12, 'BOOBA', 'RAP'),
(13, 'PASCAL OBISPO', 'VARIETE'),
(14, 'M', 'VARIETE'),
(15, 'PATRICK BRUEL', 'RAP'),
(16, 'THE KILLERS', 'POP-ROCK');

-- --------------------------------------------------------

--
-- Structure de la table `Customer`
--

CREATE TABLE IF NOT EXISTS `Customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `Customer`
--

INSERT INTO `Customer` (`id`, `firstname`, `lastname`, `mail`) VALUES
(1, 'Géraldine', 'Lemaçon', 'gera@hotmail.fr'),
(2, 'Gérard', 'Cheminlong', 'gerard@gmail.com'),
(3, 'Laeticia', 'Lagrange', 'tizzeuse33@gmx.com'),
(5, 'Rahma', 'Niémay', 'rahma_niemayyy546446464864684553@htomail.fr');

-- --------------------------------------------------------

--
-- Structure de la table `Party`
--

CREATE TABLE IF NOT EXISTS `Party` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CP` varchar(255) DEFAULT NULL,
  `dateBegin` date DEFAULT NULL,
  `dateEnd` date DEFAULT NULL,
  `dateParty` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `nbPlace` int(11) NOT NULL,
  `nbPlaceBought` int(11) NOT NULL,
  `nbPlaceScanned` int(11) NOT NULL,
  `place` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `street` varchar(255) DEFAULT NULL,
  `theme` varchar(255) DEFAULT NULL,
  `timeParty` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `validated` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Contenu de la table `Party`
--

INSERT INTO `Party` (`id`, `CP`, `dateBegin`, `dateEnd`, `dateParty`, `description`, `image`, `nbPlace`, `nbPlaceBought`, `nbPlaceScanned`, `place`, `price`, `street`, `theme`, `timeParty`, `title`, `town`, `validated`) VALUES
(1, '75012', '2013-03-20', '2013-04-20', '2013-04-21', 'Beyoncé annonce son grand retour sur scène avec une tournée mondiale', 'beyonce.jpg', 1000, 0, 0, 'PALAIS OMNISPORTS DE PARIS ', 25.5, '8 BD DE BERCY', 'RnB', '0002-12-31 20:30:00', 'BEYONCE THE MRS. CARTER SHOW', 'PARIS', 1),
(2, '75009', '2013-03-21', '2013-04-21', '2013-04-22', 'Le buzz de l''année vient de cette jeune New Yorkaise de 24 ans que tout Facebook se partage', 'lana-del-rey.jpg', 1000, 0, 0, 'L''OLYMPIA ', 30, '28, BD DES CAPUCINES', 'pop-rock', '0002-12-31 21:30:00', 'LANA DEL REY', 'PARIS', 1),
(3, '75012', '2013-03-22', '2013-04-22', '2013-04-23', 'Bruno Mars fait sans aucun doute partie des Artistes pop les plus talentueux et irrésistibles de notre époque', 'bruno-mars.jpg', 1000, 0, 0, 'PALAIS OMNISPORTS DE PARIS', 45.5, '8 BD DE BERCY', 'pop-rock', '0002-12-31 20:00:00', 'BRUNO MARS', 'PARIS', 1),
(4, '75012', '2013-03-23', '2013-04-23', '2013-04-24', 'IRON MAIDEN va désormais tourner à travers l’Europe en 2013', 'iron-maiden.jpg', 1000, 0, 0, 'PALAIS OMNISPORTS DE PARIS', 55, '8 BD DE BERCY', 'hard-rock', '0002-12-31 21:30:00', 'IRON MAIDEN ENGLAND WORLD TOUR 2013', 'PARIS', 1),
(5, '75012', '2013-03-24', '2013-04-24', '2013-04-25', 'Après sa triomphale tournée 2012, Johnny Hallyday est de retour sur scène', 'johnny-hallyday.jpg', 1000, 0, 0, 'PALAIS OMNISPORTS DE PARIS', 152, '8 BD DE BERCY', 'variete', '0002-12-31 21:00:00', 'JOHNNY HALLYDAY', 'PARIS', 1),
(6, '75012', '2013-03-25', '2013-04-25', '2013-04-26', 'Ce grand concert de Maroon 5 devrait faire une large part à leur dernier album', 'maroon5.jpg', 1000, 0, 0, 'PALAIS OMNISPORTS DE PARIS', 50, '8 BD DE BERCY', 'pop-rock', '0002-12-31 20:30:00', 'MAROON 5', 'PARIS', 1),
(7, '75019', '2013-03-26', '2013-04-26', '2013-04-27', 'Le Doggfather de la West Coast aka SNOOP DOGG, sera en concert évènement au Zénith de Paris', 'snoop-dogg.jpg', 1000, 0, 0, 'ZENITH PARIS - LA VILLETTE', 61.9, '30, avenue Corentin Cariou', 'rap', '0002-12-31 20:00:00', 'SNOOP DOGG AKA SNOOP LION', 'PARIS', 1),
(8, '75019', '2013-03-27', '2013-04-27', '2013-04-28', 'LA FOUINE créé l''événement en proposant une nouvelle tournée', 'la-fouine.jpg', 1000, 0, 0, 'ZENITH PARIS - LA VILLETTE ', 35.5, '30, avenue Corentin Cariou', 'rap', '0002-12-31 20:00:00', 'LA FOUINE', 'PARIS', 1),
(9, '75012', '2013-03-28', '2013-04-27', '2013-04-29', 'Mylene FARMER, l''une des plus grandes Artistes françaises sera en concert au Palais Omnisports de Paris Bercy', 'mylene-farmer.jpg', 1000, 0, 0, 'PALAIS OMNISPORTS DE PARIS', 140, '8 BD DE BERCY', 'variete', '0002-12-31 21:30:00', 'MYLENE FARMER "TIMELESS 2013"', 'PARIS', 1),
(10, '75012', '2013-03-29', '2013-04-29', '2013-05-05', 'Alicia KEYS annonce sa tournée europeenne SET THE WORLD ON FIRE', 'alicia-keys.jpg', 1000, 0, 0, 'PALAIS OMNISPORTS DE PARIS', 67.5, '8 BD DE BERCY', 'RnB', '0002-12-31 21:00:00', 'ALICIA KEYS SET THE WORLD ON FIRE TOUR', 'PARIS', 1),
(11, '75012', '2013-03-15', '2013-04-20', '2013-05-05', 'Justin devient l’Artiste le plus visionné sur Youtube avec 2.9 milliards de vues !', 'justin-bieber.jpg', 1000, 0, 0, 'PALAIS OMNISPORTS DE PARIS', 96, '8 BD DE BERCY', 'pop-rock', '0002-12-31 21:00:00', 'JUSTIN BIEBER', 'PARIS', 1),
(12, '75019', '2013-03-20', '2013-04-10', '2013-05-01', 'Retrouvez BOOBA lors d''une tournée événement', 'booba.jpg', 1000, 0, 0, 'ZENITH PARIS - LA VILLETTE', 37.5, '30, avenue Corentin Cariou', 'rap', '0002-12-31 21:30:00', 'BOOBA "FUTUR TOUR" ', 'PARIS', 1),
(13, '75009', '2013-03-15', '2013-04-15', '2013-04-16', '20 ans de tubes pour lui et les autres', 'pascal-obispo.jpg', 1000, 0, 0, 'L''OLYMPIA ', 45, '28, BD DES CAPUCINES', 'variete', '0002-12-31 21:00:00', 'PASCAL OBISPO "MillesimeS"', 'PARIS', 0),
(14, '75019', '2013-03-20', '2013-04-20', '2013-04-21', 'M sort cet automne une épopée solaire et rêve d''un tour de la terre', 'M.jpg', 1000, 0, 0, 'ZENITH PARIS - LA VILLETTE', 32, '30, avenue Corentin Cariou', 'variete', '0002-12-31 20:30:00', '-M-', 'PARIS', 0),
(15, '75019', '2013-03-25', '2013-04-25', '2013-04-26', 'Patrick Bruel signe un retour en force', 'patrick-bruel.jpg', 1000, 0, 0, 'ZENITH PARIS - LA VILLETTE', 39.5, '30, avenue Corentin Cariou', 'variete', '0002-12-31 20:00:00', 'PATRICK BRUEL AVEC RFM et LE PARISIEN', 'PARIS', 0),
(16, '75019', '2013-03-30', '2013-04-30', '2013-05-01', 'Réservez vite vos places pour The Killers', 'the-killers.jpg', 1000, 0, 0, 'ZENITH PARIS - LA VILLETTE', 48.8, '30, avenue Corentin Cariou', 'pop-rock', '0002-12-31 21:30:00', 'THE KILLERS', 'PARIS', 0);

-- --------------------------------------------------------

--
-- Structure de la table `PARTY_ARTIST`
--

CREATE TABLE IF NOT EXISTS `PARTY_ARTIST` (
  `PARTY_FK` int(11) NOT NULL,
  `ARTIST_FK` int(11) NOT NULL,
  KEY `FK4DDE22E02373B139` (`ARTIST_FK`),
  KEY `FK4DDE22E01E6EEBCF` (`PARTY_FK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Ticket`
--

CREATE TABLE IF NOT EXISTS `Ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `secretcode` varchar(16) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `party_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK954D572C1E6EEC25` (`party_id`),
  KEY `FK954D572C8C82DCEF` (`customer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `Ticket`
--

INSERT INTO `Ticket` (`id`, `secretcode`, `customer_id`, `party_id`) VALUES
(1, '1234567890ABCDEF', 1, 3),
(2, '59B5678A0ABCD8F0', 1, 7),
(3, '00B567860AD068FA', 2, 7),
(4, '8FB5A786DAD0783C', 3, 10),
(5, '8FB5A788DAD0783C', 5, 15);

-- --------------------------------------------------------

--
-- Structure de la table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `PARTY_ARTIST`
--
ALTER TABLE `PARTY_ARTIST`
  ADD CONSTRAINT `FK4DDE22E01E6EEBCF` FOREIGN KEY (`PARTY_FK`) REFERENCES `Party` (`id`),
  ADD CONSTRAINT `FK4DDE22E02373B139` FOREIGN KEY (`ARTIST_FK`) REFERENCES `Artist` (`id`);

--
-- Contraintes pour la table `Ticket`
--
ALTER TABLE `Ticket`
  ADD CONSTRAINT `FK954D572C1E6EEC25` FOREIGN KEY (`party_id`) REFERENCES `Party` (`id`),
  ADD CONSTRAINT `FK954D572C8C82DCEF` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
