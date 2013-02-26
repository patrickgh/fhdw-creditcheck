# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.25a)
# Datenbank: puzzles
# Erstellungsdauer: 2013-02-26 20:08:32 +0100
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Export von Tabelle config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;

INSERT INTO `config` (`id`, `category`, `value`, `description`)
VALUES
	(1,'LHKP1','450','1-Personen-Haushalt'),
	(2,'LHKP2','700','2-Personen-Haushalt'),
	(3,'LHKP3','150','jede weitere Person'),
	(4,'KFZ','175','PKW bis 100PS'),
	(5,'KFZ','250','PKW über 100PS'),
	(6,'KFZ','125','Motorrad'),
	(7,'KFZ','10','2in1 Skateboard'),
	(8,'BASE_INTEREST','7','Zinssatz für neue Kredite');

/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;


# Export von Tabelle consultants
# ------------------------------------------------------------

DROP TABLE IF EXISTS `consultants`;

CREATE TABLE `consultants` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `consultants` WRITE;
/*!40000 ALTER TABLE `consultants` DISABLE KEYS */;

INSERT INTO `consultants` (`id`, `firstname`, `lastname`, `username`, `password`)
VALUES
	(1,'Barnery','Stinson','bstinson','098f6bcd4621d373cade4e832627b4f6'),
	(2,'Ted','Mosby','tmosby','098f6bcd4621d373cade4e832627b4f6'),
	(3,'Moritz','Barnick','mbarnick','098f6bcd4621d373cade4e832627b4f6'),
	(4,'Hermann','Mels','hmels','098f6bcd4621d373cade4e832627b4f6'),
	(5,'Patrick','Groß-Holtwick','pgrossholtwick','098f6bcd4621d373cade4e832627b4f6');

/*!40000 ALTER TABLE `consultants` ENABLE KEYS */;
UNLOCK TABLES;


# Export von Tabelle creditrequests
# ------------------------------------------------------------

DROP TABLE IF EXISTS `creditrequests`;

CREATE TABLE `creditrequests` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) unsigned NOT NULL,
  `consultant_id` int(11) unsigned NOT NULL,
  `creationdate` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `creditamount` float DEFAULT NULL,
  `hasfixedlength` tinyint(1) DEFAULT NULL,
  `rate` float DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `consultant_id` (`consultant_id`),
  CONSTRAINT `creditrequests_ibfk_2` FOREIGN KEY (`consultant_id`) REFERENCES `consultants` (`id`),
  CONSTRAINT `creditrequests_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Export von Tabelle customer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `street` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `zipcode` varchar(10) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `accountnumber` varchar(30) DEFAULT NULL,
  `bankcode` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Export von Tabelle transactions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `request_id` int(11) unsigned NOT NULL,
  `description` varchar(30) DEFAULT NULL,
  `description1` varchar(30) DEFAULT NULL,
  `description2` varchar(30) DEFAULT NULL,
  `value` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `request_id` (`request_id`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `creditrequests` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
