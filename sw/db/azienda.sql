-- MySQL dump 10.16  Distrib 10.1.22-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: azienda
-- ------------------------------------------------------
-- Server version	10.1.22-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `consegna`
--

DROP TABLE IF EXISTS `consegna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consegna` (
  `idc` int(11) NOT NULL AUTO_INCREMENT,
  `dataconsegna` datetime DEFAULT NULL,
  `refordine` int(11) NOT NULL,
  `refcorriere` int(11) NOT NULL,
  PRIMARY KEY (`idc`),
  KEY `refordine` (`refordine`),
  KEY `refcorriere` (`refcorriere`),
  CONSTRAINT `consegna_ibfk_1` FOREIGN KEY (`refordine`) REFERENCES `ordine` (`ido`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `consegna_ibfk_2` FOREIGN KEY (`refcorriere`) REFERENCES `corriere` (`idcorriere`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consegna`
--

LOCK TABLES `consegna` WRITE;
/*!40000 ALTER TABLE `consegna` DISABLE KEYS */;
INSERT INTO `consegna` VALUES (20,NULL,37,1),(21,NULL,38,1),(22,NULL,39,1),(23,NULL,40,1),(24,NULL,41,1),(25,NULL,42,1),(26,NULL,43,1),(27,NULL,44,1),(28,NULL,45,1),(29,NULL,46,1);
/*!40000 ALTER TABLE `consegna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corriere`
--

DROP TABLE IF EXISTS `corriere`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corriere` (
  `idcorriere` int(11) NOT NULL AUTO_INCREMENT,
  `nomeutente` varchar(30) NOT NULL,
  `password` char(64) NOT NULL,
  `salt` int(11) NOT NULL,
  PRIMARY KEY (`idcorriere`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corriere`
--

LOCK TABLES `corriere` WRITE;
/*!40000 ALTER TABLE `corriere` DISABLE KEYS */;
INSERT INTO `corriere` VALUES (1,'ciro.verdi','65cff8268da2442ad5ab838201f8a05b9a0aa4611b91b0d5d846c3c2a6960847',340527531);
/*!40000 ALTER TABLE `corriere` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farmacia`
--

DROP TABLE IF EXISTS `farmacia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `farmacia` (
  `idfarmacia` int(11) NOT NULL AUTO_INCREMENT,
  `piva` char(11) DEFAULT NULL,
  `nome` varchar(120) NOT NULL,
  `indirizzo` varchar(210) DEFAULT NULL,
  `citta` varchar(70) DEFAULT NULL,
  `telefono` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`idfarmacia`),
  UNIQUE KEY `piva` (`piva`),
  UNIQUE KEY `telefono` (`telefono`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farmacia`
--

LOCK TABLES `farmacia` WRITE;
/*!40000 ALTER TABLE `farmacia` DISABLE KEYS */;
INSERT INTO `farmacia` VALUES (1,'13215641321','FarmAccì','via albertini','palermo','0918753121');
/*!40000 ALTER TABLE `farmacia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordine` (
  `ido` int(11) NOT NULL AUTO_INCREMENT,
  `dataconsegnaprevista` date DEFAULT NULL,
  `reffarmacia` int(11) NOT NULL,
  `data` datetime DEFAULT CURRENT_TIMESTAMP,
  `stato` tinyint(4) DEFAULT '0',
  `periodico` tinyint(4) DEFAULT '0',
  `stato_consegna_visualizzato` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`ido`),
  KEY `reffarnacia_fk` (`reffarmacia`),
  CONSTRAINT `reffarnacia_fk` FOREIGN KEY (`reffarmacia`) REFERENCES `farmacia` (`idfarmacia`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
INSERT INTO `ordine` VALUES (37,'2022-08-29',1,'2022-08-18 00:00:00',4,0,0),(38,'2022-08-29',1,'2022-08-25 00:00:00',0,0,0),(39,'2022-08-30',1,'2022-08-18 00:00:00',3,0,0),(40,'2022-09-07',1,'2022-08-25 00:00:00',0,0,0),(41,'2022-08-28',1,'2022-08-18 00:00:00',3,0,0),(42,'2022-09-05',1,'2022-08-25 00:00:00',0,0,0),(43,'2022-08-24',1,'2022-08-18 00:00:00',0,0,0),(44,'2022-09-01',1,'2022-08-25 00:00:00',0,0,0),(45,'2022-08-26',1,'2022-08-18 00:00:00',0,0,0),(46,'2022-08-24',1,'2022-08-18 00:00:00',0,0,0);
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prenotato`
--

DROP TABLE IF EXISTS `prenotato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prenotato` (
  `idp` int(11) NOT NULL AUTO_INCREMENT,
  `refprodotto` int(11) NOT NULL,
  `scadenza` date DEFAULT NULL,
  `qta` int(11) NOT NULL,
  `refordine` int(11) NOT NULL,
  `consegnato` tinyint(1) NOT NULL DEFAULT '0',
  `caricato` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idp`),
  KEY `refprodotto` (`refprodotto`),
  KEY `refordine` (`refordine`),
  CONSTRAINT `prenotato_ibfk_1` FOREIGN KEY (`refprodotto`) REFERENCES `prodotto` (`idprodotto`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `prenotato_ibfk_2` FOREIGN KEY (`refordine`) REFERENCES `ordine` (`ido`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotato`
--

LOCK TABLES `prenotato` WRITE;
/*!40000 ALTER TABLE `prenotato` DISABLE KEYS */;
INSERT INTO `prenotato` VALUES (103,8,'2022-11-07',10,37,1,1),(104,8,'2022-12-01',2,38,0,0),(105,8,'2022-09-12',10,39,0,0),(106,8,'2022-10-08',2,40,0,0),(107,8,'2022-08-19',10,41,0,0),(108,8,'2022-10-13',2,42,0,0),(109,8,'2022-09-03',3,43,0,0),(110,8,'2022-11-11',2,44,0,0),(111,2,'2022-11-04',2,45,0,0),(112,8,'2022-10-22',1,45,0,0),(113,2,'2022-09-09',2,46,0,0),(114,8,'2022-10-24',1,46,0,0);
/*!40000 ALTER TABLE `prenotato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodotto` (
  `idprodotto` int(11) NOT NULL AUTO_INCREMENT,
  `ean` char(12) DEFAULT NULL,
  `nome` varchar(70) DEFAULT NULL,
  `principioattivo` varchar(120) DEFAULT NULL,
  `costo` decimal(7,2) DEFAULT NULL,
  `dabanco` tinyint(1) NOT NULL DEFAULT '0',
  `scorte` int(11) DEFAULT '50',
  PRIMARY KEY (`idprodotto`),
  UNIQUE KEY `ean` (`ean`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES (1,'012389258275','Tachipirina','Paracetamolo',12.50,1,5021),(2,'946294628402','Vivin C','Acido acetilsalicilico',34.00,1,5006),(3,'946204729374','Bentelan','Betametasone',21.40,0,5010),(4,'845294759301','Aspirina','Acido acetilsalicilico',25.00,0,5010),(5,'936402748572','Momendol','Naprossene',12.00,0,5010),(6,'946293847503','Gaviscon','Sodio alginato',7.00,1,5500),(7,'946293847523','Zitromax','Azitromicina',5.00,0,5300),(8,'234629384750','Integratore di Vitamina C','Vitamina C',12.00,0,4998),(9,'946293847512','Moment Act','Ibuprofene',20.00,0,5050),(10,'946293843512','B12','B12',25.00,1,5070);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-04 14:22:11
