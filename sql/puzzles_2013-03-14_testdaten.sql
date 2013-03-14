# Sequel Pro dump
# Version 2492
# http://code.google.com/p/sequel-pro
#
# Host: 127.0.0.1 (MySQL 5.0.51a)
# Database: puzzles
# Generation Time: 2013-03-14 22:34:02 +0100
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `category` varchar(50) default NULL,
  `value` varchar(50) default NULL,
  `description` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` (`id`,`category`,`value`,`description`)
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


# Dump of table consultants
# ------------------------------------------------------------

DROP TABLE IF EXISTS `consultants`;

CREATE TABLE `consultants` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `firstname` varchar(30) NOT NULL default '',
  `lastname` varchar(50) NOT NULL default '',
  `username` varchar(30) NOT NULL default '',
  `password` varchar(32) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

LOCK TABLES `consultants` WRITE;
/*!40000 ALTER TABLE `consultants` DISABLE KEYS */;
INSERT INTO `consultants` (`id`,`firstname`,`lastname`,`username`,`password`)
VALUES
	(1,'Barnery','Stinson','bstinson','098f6bcd4621d373cade4e832627b4f6'),
	(2,'Ted','Mosby','tmosby','098f6bcd4621d373cade4e832627b4f6'),
	(3,'Moritz','Barnick','mbarnick','098f6bcd4621d373cade4e832627b4f6'),
	(4,'Hermann','Mels','hmels','098f6bcd4621d373cade4e832627b4f6'),
	(5,'Patrick','Groß-Holtwick','pgrossholtwick','098f6bcd4621d373cade4e832627b4f6'),
	(6,'Ich bin Neukunde',' ','neukunde','098f6bcd4621d373cade4e832627b4f6');

/*!40000 ALTER TABLE `consultants` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table creditrequests
# ------------------------------------------------------------

DROP TABLE IF EXISTS `creditrequests`;

CREATE TABLE `creditrequests` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `customer_id` int(11) unsigned NOT NULL,
  `consultant_id` int(11) unsigned NOT NULL,
  `creationdate` datetime default NULL,
  `state` int(11) default NULL,
  `creditamount` float default NULL,
  `hasfixedlength` tinyint(1) default NULL,
  `rate` float default NULL,
  `duration` float default NULL,
  `interest` float default NULL,
  PRIMARY KEY  (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `consultant_id` (`consultant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

LOCK TABLES `creditrequests` WRITE;
/*!40000 ALTER TABLE `creditrequests` DISABLE KEYS */;
INSERT INTO `creditrequests` (`id`,`customer_id`,`consultant_id`,`creationdate`,`state`,`creditamount`,`hasfixedlength`,`rate`,`duration`,`interest`)
VALUES
	(1,1,1,'2013-03-13 00:00:00',2,5000,1,0,10,0.07),
	(2,2,1,'2003-03-14 00:00:00',1,5000,1,0,5,0.07),
	(3,3,1,'2007-03-14 00:00:00',0,100000,1,0,5,0.07),
	(4,5,1,'2013-02-07 00:00:00',0,10000,1,0,5,0.07),
	(6,7,1,'2013-01-24 00:00:00',0,50000,1,0,30,0.07),
	(7,8,1,'2011-12-09 00:00:00',1,1000,1,0,5,0.07),
	(8,9,1,'2012-06-09 00:00:00',0,10000,1,0,5,0.07),
	(9,10,1,'2013-03-12 00:00:00',0,50000,1,0,30,0.07);

/*!40000 ALTER TABLE `creditrequests` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table customer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `firstname` varchar(30) default NULL,
  `lastname` varchar(50) default NULL,
  `birthdate` date default NULL,
  `street` varchar(30) default NULL,
  `city` varchar(30) default NULL,
  `zipcode` varchar(10) default NULL,
  `telephone` varchar(30) default NULL,
  `email` varchar(30) default NULL,
  `accountnumber` varchar(30) default NULL,
  `bankcode` varchar(30) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`,`firstname`,`lastname`,`birthdate`,`street`,`city`,`zipcode`,`telephone`,`email`,`accountnumber`,`bankcode`)
VALUES
	(1,'Patrick','Groß-Holtwick','1991-04-26','Ehlentruper Weg 87','Bielefeld','33477','017691403382','patrickgh@web.de','1234','12123324'),
	(2,'Hermann','Mels','2013-02-14','Burloer Straße 125','Borken','46325','0900123456','h.mels@gmx.de','123455','543211'),
	(3,'Peter','Pan','2000-01-01','Sperber Straße 12','Bonn','46663','0174/35473893','p.pan@wunderland.de','00782345340000','1237632'),
	(4,NULL,NULL,'2013-03-14',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
	(5,'Han Solo','Burger','1985-07-16','West Ave. 1','Los Angeles','BC4383','29839234723','hsb@la.de','0-2983-0892374','BHW-238243'),
	(6,'Han Solo','Burger','1985-07-16','West Ave. 1','Los Angeles','BC4383','29839234723','hsb@la.de','0-2983-0892374','BHW-238243'),
	(7,'Max','Mustermann','1984-06-12','A-Str. 1','Ulm','234','928374','mm@gmx.de','2342435','9873454'),
	(8,'Angela','Merkel','1954-02-09','A1 3','Berlin','34234','213123-01','a.merkel@gmx.de','2982734','2398234'),
	(9,'Kevin','Kuranyi','1984-06-05','Schlossallee 23','Moskau','87234','982374234','k.k@gmx.de','09234234','23498323'),
	(10,'Josef','Ratzinger','1980-06-02','St. Petersdom Straße 4','Rom','43532','023423234','nopapam@1u1.de','23234987','32498');

/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table transactions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `request_id` int(11) unsigned NOT NULL,
  `description` varchar(30) default NULL,
  `description1` varchar(30) default NULL,
  `description2` varchar(30) default NULL,
  `value` float default NULL,
  PRIMARY KEY  (`id`),
  KEY `request_id` (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` (`id`,`request_id`,`description`,`description1`,`description2`,`value`)
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
	(24,2,NULL,NULL,NULL,0),
	(25,3,'God Ltd',NULL,NULL,5000),
	(26,3,'Rasenmähen',NULL,NULL,5),
	(27,3,'Miete',NULL,NULL,356),
	(28,3,'Miete','','',-1999),
	(29,3,'Wohnnebenkosten','','',-340),
	(30,3,'Sparverträge','','',-23),
	(31,3,'Telefon/Internet/Handy','','',-23),
	(32,3,'Lebenshaltungskosten','3 Personen','',-850),
	(33,3,NULL,'','',0),
	(34,3,'KFZ-Kosten','','',-250),
	(35,3,'Unfallversicherung','GNB','100000',-120),
	(36,3,NULL,NULL,NULL,0),
	(37,4,NULL,NULL,NULL,0),
	(38,4,NULL,NULL,NULL,0),
	(39,4,NULL,NULL,NULL,0),
	(40,4,'Miete','','',0),
	(41,4,'Wohnnebenkosten','','',0),
	(42,4,'Sparverträge','','',0),
	(43,4,'Telefon/Internet/Handy','','',0),
	(44,4,'Lebenshaltungskosten','0 Personen','',0),
	(45,4,NULL,'','',0),
	(46,4,'KFZ-Kosten','','',0),
	(47,4,NULL,NULL,NULL,0),
	(48,4,NULL,NULL,NULL,0),
	(49,5,NULL,NULL,NULL,0),
	(50,5,NULL,NULL,NULL,0),
	(51,5,NULL,NULL,NULL,0),
	(52,5,'Miete','','',0),
	(53,5,'Wohnnebenkosten','','',0),
	(54,5,'Sparverträge','','',0),
	(55,5,'Telefon/Internet/Handy','','',0),
	(56,5,'Lebenshaltungskosten','0 Personen','',0),
	(57,5,NULL,'','',0),
	(58,5,'KFZ-Kosten','','',0),
	(59,5,NULL,NULL,NULL,0),
	(60,5,NULL,NULL,NULL,0),
	(61,6,'Google Inc.',NULL,NULL,3234),
	(62,6,'Bierbaum GmbH',NULL,NULL,423),
	(63,6,'Miete',NULL,NULL,932.32),
	(64,6,'Miete','','',-1992.23),
	(65,6,'Wohnnebenkosten','','',-200.43),
	(66,6,'Sparverträge','','',0),
	(67,6,'Telefon/Internet/Handy','','',-25),
	(68,6,'Lebenshaltungskosten','2 Personen','',-700),
	(69,6,NULL,'','',0),
	(70,6,'KFZ-Kosten','','',-250),
	(71,6,'Haftpflichtversicherung','VAG','100000000',-10000.2),
	(72,6,NULL,NULL,NULL,0),
	(73,6,NULL,NULL,NULL,0),
	(74,7,'Vater Staat',NULL,NULL,30000),
	(75,7,NULL,NULL,NULL,0),
	(76,7,NULL,NULL,NULL,0),
	(77,7,'Miete','','',-10),
	(78,7,'Wohnnebenkosten','','',-1),
	(79,7,'Sparverträge','','',0),
	(80,7,'Telefon/Internet/Handy','','',0),
	(81,7,'Lebenshaltungskosten','82000 Personen','',-1.23004e+07),
	(82,7,NULL,'','',0),
	(83,7,'KFZ-Kosten','','',0),
	(84,7,'Sonstige Versicherung','EZB','10000000000',-100000),
	(85,7,NULL,NULL,NULL,0),
	(86,8,'Dynamo Moskau',NULL,NULL,2e+06),
	(87,8,NULL,NULL,NULL,0),
	(88,8,'Miete','','',-213123),
	(89,8,'Wohnnebenkosten','','',-876),
	(90,8,'Sparverträge','','',0),
	(91,8,'Telefon/Internet/Handy','','',-300),
	(92,8,'Lebenshaltungskosten','3 Personen','',-850),
	(93,8,NULL,'','',0),
	(94,8,'KFZ-Kosten','','',-250),
	(95,8,'Unfallversicherung','Putin AG','98723423',-23),
	(96,8,NULL,NULL,NULL,0),
	(97,9,'Gott',NULL,NULL,1.23003e+06),
	(98,9,NULL,NULL,NULL,0),
	(99,9,'Miete','','',0),
	(100,9,'Wohnnebenkosten','','',0),
	(101,9,'Sparverträge','','',0),
	(102,9,'Telefon/Internet/Handy','','',0),
	(103,9,'Lebenshaltungskosten','1 Personen','',-450),
	(104,9,NULL,'','',0),
	(105,9,'KFZ-Kosten','','',0),
	(106,9,NULL,NULL,NULL,0),
	(107,9,NULL,NULL,NULL,0);

/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;





/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
