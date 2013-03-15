-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Mer 06 Mars 2013 à 12:59
-- Version du serveur: 5.5.24-log
-- Version de PHP: 5.4.3

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
-- Structure de la table `artist`
--

CREATE TABLE IF NOT EXISTS `artist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `artist`
--

INSERT INTO `artist` (`id`, `name`, `type`) VALUES
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
-- Structure de la table `party`
--

CREATE TABLE IF NOT EXISTS `party` (
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
  `pathTicket` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `street` varchar(255) DEFAULT NULL,
  `theme` varchar(255) DEFAULT NULL,
  `timeParty` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `validated` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `party`
--

INSERT INTO `party` (`id`, `CP`, `dateBegin`, `dateEnd`, `dateParty`, `description`, `image`, `nbPlace`, `nbPlaceBought`, `nbPlaceScanned`, `pathTicket`, `place`, `price`, `street`, `theme`, `timeParty`, `title`, `town`, `validated`) VALUES
(1, '75012', '2013-03-20', '2013-04-20', '2013-04-21', 'Beyoncé annonce son grand retour sur scène avec une tournée mondiale', 'beyonce.jpg', 1000, 0, 0, NULL, 'PALAIS OMNISPORTS DE PARIS ', 25.50, '8 BD DE BERCY', 'RnB', '0002-12-31 20:30:00', 'BEYONCE THE MRS. CARTER SHOW', 'PARIS', 1),
(2, '75009', '2013-03-21', '2013-04-21', '2013-04-22', 'Le buzz de l''année vient de cette jeune New Yorkaise de 24 ans que tout Facebook se partage', 'lana-del-rey.jpg', 1000, 0, 0, NULL, 'L''OLYMPIA ', 30, '28, BD DES CAPUCINES', 'pop-rock', '0002-12-31 21:30:00', 'LANA DEL REY', 'PARIS', 1),
(3, '75012', '2013-03-22', '2013-04-22', '2013-04-23', 'Bruno Mars fait sans aucun doute partie des artistes pop les plus talentueux et irrésistibles de notre époque', 'bruno-mars.jpg', 1000, 0, 0, NULL, 'PALAIS OMNISPORTS DE PARIS', 45.50, '8 BD DE BERCY', 'pop-rock', '0002-12-31 20:00:00', 'BRUNO MARS', 'PARIS', 1),
(4, '75012', '2013-03-23', '2013-04-23', '2013-04-24', 'IRON MAIDEN va désormais tourner à travers l’Europe en 2013', 'iron-maiden.jpg', 1000, 0, 0, NULL, 'PALAIS OMNISPORTS DE PARIS', 55, '8 BD DE BERCY', 'hard-rock', '0002-12-31 21:30:00', 'IRON MAIDEN ENGLAND WORLD TOUR 2013', 'PARIS', 1),
(5, '75012', '2013-03-24', '2013-04-24', '2013-04-25', 'Après sa triomphale tournée 2012, Johnny Hallyday est de retour sur scène', 'johnny-hallyday.jpg', 1000, 0, 0, NULL, 'PALAIS OMNISPORTS DE PARIS', 152, '8 BD DE BERCY', 'variete', '0002-12-31 21:00:00', 'JOHNNY HALLYDAY', 'PARIS', 1),
(6, '75012', '2013-03-25', '2013-04-25', '2013-04-26', 'Ce grand concert de Maroon 5 devrait faire une large part à leur dernier album', 'maroon5.jpg', 1000, 0, 0, NULL, 'PALAIS OMNISPORTS DE PARIS', 50, '8 BD DE BERCY', 'pop-rock', '0002-12-31 20:30:00', 'MAROON 5', 'PARIS', 1),
(7, '75019', '2013-03-26', '2013-04-26', '2013-04-27', 'Le Doggfather de la West Coast aka SNOOP DOGG, sera en concert évènement au Zénith de Paris', 'snoop-dogg.jpg', 1000, 0, 0, NULL, 'ZENITH PARIS - LA VILLETTE', 61.90, '30, avenue Corentin Cariou', 'rap', '0002-12-31 20:00:00', 'SNOOP DOGG AKA SNOOP LION', 'PARIS', 1),
(8, '75019', '2013-03-27', '2013-04-27', '2013-04-28', 'LA FOUINE créé l''événement en proposant une nouvelle tournée', 'la-fouine.jpg', 1000, 0, 0, NULL, 'ZENITH PARIS - LA VILLETTE ', 35.50, '30, avenue Corentin Cariou', 'rap', '0002-12-31 20:00:00', 'LA FOUINE', 'PARIS', 1),
(9, '75012', '2013-03-28', '2013-04-27', '2013-04-29', 'Mylene FARMER, l''une des plus grandes artistes françaises sera en concert au Palais Omnisports de Paris Bercy', 'mylene-farmer.jpg', 1000, 0, 0, NULL, 'PALAIS OMNISPORTS DE PARIS', 140, '8 BD DE BERCY', 'variete', '0002-12-31 21:30:00', 'MYLENE FARMER "TIMELESS 2013"', 'PARIS', 1),
(10, '75012', '2013-03-29', '2013-04-29', '2013-04-30', 'Alicia KEYS annonce sa tournée europeenne SET THE WORLD ON FIRE', 'alicia-keys.jpg', 1000, 0, 0, NULL, 'PALAIS OMNISPORTS DE PARIS', 67.50, '8 BD DE BERCY', 'RnB', '0002-12-31 21:00:00', 'ALICIA KEYS SET THE WORLD ON FIRE TOUR', 'PARIS', 1),
(11, '75012', '2013-03-30', '2013-04-30', '2013-04-31', 'Justin devient l’artiste le plus visionné sur Youtube avec 2.9 milliards de vues !', 'justin-bieber.jpg', 1000, 0, 0, NULL, 'PALAIS OMNISPORTS DE PARIS', 96, '8 BD DE BERCY', 'pop-rock', '0002-12-31 21:00:00', 'JUSTIN BIEBER', 'PARIS', 1),
(12, '75019', '2013-03-31', '2013-04-31', '2013-05-01', 'Retrouvez BOOBA lors d''une tournée événement', 'booba.jpg', 1000, 0, 0, NULL, 'ZENITH PARIS - LA VILLETTE', 37.50, '30, avenue Corentin Cariou', 'rap', '0002-12-31 21:30:00', 'BOOBA "FUTUR TOUR" ', 'PARIS', 1),
(13, '75009', '2013-03-15', '2013-04-15', '2013-04-16', '20 ans de tubes pour lui et les autres', 'pascal-obispo.jpg', 1000, 0, 0, NULL, 'L''OLYMPIA ', 45, '28, BD DES CAPUCINES', 'variete', '0002-12-31 21:00:00', 'PASCAL OBISPO "MillesimeS"', 'PARIS', 0),
(14, '75019', '2013-03-20', '2013-04-20', '2013-04-21', 'M sort cet automne une épopée solaire et rêve d''un tour de la terre', 'M.jpg', 1000, 0, 0, NULL, 'ZENITH PARIS - LA VILLETTE', 32, '30, avenue Corentin Cariou', 'variete', '0002-12-31 20:30:00', '-M-', 'PARIS', 0),
(15, '75019', '2013-03-25', '2013-04-25', '2013-04-26', 'Patrick Bruel signe un retour en force', 'patrick-bruel.jpg', 1000, 0, 0, NULL, 'ZENITH PARIS - LA VILLETTE', 39.50, '30, avenue Corentin Cariou', 'variete', '0002-12-31 20:00:00', 'PATRICK BRUEL AVEC RFM et LE PARISIEN', 'PARIS', 0),
(16, '75019', '2013-03-30', '2013-04-30', '2013-05-01', 'Réservez vite vos places pour The Killers', 'the-killers.jpg', 1000, 0, 0, NULL, 'ZENITH PARIS - LA VILLETTE', 48.80, '30, avenue Corentin Cariou', 'pop-rock', '0002-12-31 21:30:00', 'THE KILLERS', 'PARIS', 0);

-- --------------------------------------------------------

--
-- Structure de la table `party_artist`
--

CREATE TABLE IF NOT EXISTS `party_artist` (
  `PARTY_FK` int(11) NOT NULL,
  `ARTIST_FK` int(11) NOT NULL,
  KEY `FK4DDE22E02373B139` (`ARTIST_FK`),
  KEY `FK4DDE22E01E6EEBCF` (`PARTY_FK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `party_artist`
--

INSERT INTO `party_artist` (`PARTY_FK`, `ARTIST_FK`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(16, 16);

-- --------------------------------------------------------

--
-- Structure de la table `ticket`
--

CREATE TABLE IF NOT EXISTS `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `PARTY_FK` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK954D572C1E6EEBCF` (`PARTY_FK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
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
-- Contraintes pour la table `party_artist`
--
ALTER TABLE `party_artist`
  ADD CONSTRAINT `FK4DDE22E01E6EEBCF` FOREIGN KEY (`PARTY_FK`) REFERENCES `party` (`id`),
  ADD CONSTRAINT `FK4DDE22E02373B139` FOREIGN KEY (`ARTIST_FK`) REFERENCES `artist` (`id`);

--
-- Contraintes pour la table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `FK954D572C1E6EEBCF` FOREIGN KEY (`PARTY_FK`) REFERENCES `party` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
