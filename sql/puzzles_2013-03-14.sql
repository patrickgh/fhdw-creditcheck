# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.25a)
# Datenbank: puzzles
# Erstellungsdauer: 2013-03-14 01:33:31 +0100
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
  `firstname` varchar(30) NOT NULL DEFAULT '',
  `lastname` varchar(50) NOT NULL DEFAULT '',
  `username` varchar(30) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
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
	(5,'Patrick','Groß-Holtwick','pgrossholtwick','098f6bcd4621d373cade4e832627b4f6'),
	(6,'Ich bin Neukunde',' ','neukunde','098f6bcd4621d373cade4e832627b4f6');

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
  `duration` float DEFAULT NULL,
  `interest` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `consultant_id` (`consultant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `creditrequests` WRITE;
/*!40000 ALTER TABLE `creditrequests` DISABLE KEYS */;

INSERT INTO `creditrequests` (`id`, `customer_id`, `consultant_id`, `creationdate`, `state`, `creditamount`, `hasfixedlength`, `rate`, `duration`, `interest`)
VALUES
	(1,1,1,'2013-03-13 00:00:00',0,5000,1,0,10,0.07),
	(2,2,1,'2013-03-14 00:00:00',0,5000,1,0,5,0.07);

/*!40000 ALTER TABLE `creditrequests` ENABLE KEYS */;
UNLOCK TABLES;


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

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;

INSERT INTO `customer` (`id`, `firstname`, `lastname`, `birthdate`, `street`, `city`, `zipcode`, `telephone`, `email`, `accountnumber`, `bankcode`)
VALUES
	(1,'Patrick','Groß-Holtwick','1991-04-26','Ehlentruper Weg 87','Bielefeld','33477','017691403382','patrickgh@web.de','1234','12123324'),
	(2,'Hermann','Mels','2013-02-14','Burloer Straße 125','Borken','46325','0900123456','h.mels@gmx.de','123455','543211');

/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;


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
  KEY `request_id` (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;

INSERT INTO `transactions` (`id`, `request_id`, `description`, `description1`, `description2`, `value`)
VALUES
	(1,1,'Laudert',NULL,NULL,3000),
	(2,1,'rasen mähen',NULL,NULL,10),
	(3,1,'Miete','','',-300),
	(4,1,'Wohnnebenkosten','','',-100),
	(5,1,'Sparverträge','','',-10),
	(6,1,'Telefon/Internet/Handy','','',-20),
	(7,1,'Lebenshaltungskosten','1 Personen','',-450),
	(8,1,'YOLO','','',-50),
	(9,1,'KFZ-Kosten','','',-10),
	(10,1,'Rechtsschutzversicherung','ASD','100000',-5),
	(11,1,'Spa','3000','1000',-10),
	(12,2,'Laudert',NULL,NULL,359),
	(13,2,'Bafög',NULL,NULL,16),
	(14,2,'Sofortrente Glücksspirale',NULL,NULL,5000),
	(15,2,'Räsen mähen',NULL,NULL,10),
	(16,2,'Miete','','',-200),
	(17,2,'Wohnnebenkosten','','',-100),
	(18,2,'Sparverträge','','',0),
	(19,2,'Telefon/Internet/Handy','','',-30),
	(20,2,'Lebenshaltungskosten','1 Personen','',-450),
	(21,2,NULL,'','',0),
	(22,2,'KFZ-Kosten','','',-10),
	(23,2,'Rechtsschutzversicherung','ASDF Insurances','1000000',-4.99),
	(24,2,NULL,NULL,NULL,0);

/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
