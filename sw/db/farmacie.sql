-- MySQL dump 10.16  Distrib 10.1.22-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: farmacie
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
  `giorni_ordine_periodico` tinyint(4) NOT NULL DEFAULT '1',
  `ultimo_rifornimento` datetime NOT NULL,
  PRIMARY KEY (`idfarmacia`),
  UNIQUE KEY `piva` (`piva`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farmacia`
--

LOCK TABLES `farmacia` WRITE;
/*!40000 ALTER TABLE `farmacia` DISABLE KEYS */;
INSERT INTO `farmacia` VALUES (1,'13215641321','FarmAccì','via albertini','palermo','0918753121',1,'2022-08-08 00:00:00'),(2,'12312312333','Farmax','via niccolo','catania','09265498',12,'2022-09-03 08:14:50');
/*!40000 ALTER TABLE `farmacia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazzino`
--

DROP TABLE IF EXISTS `magazzino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `magazzino` (
  `idm` int(11) NOT NULL AUTO_INCREMENT,
  `reffarmacia` int(11) DEFAULT NULL,
  `refprodotto` int(11) DEFAULT NULL,
  `scadenza` date DEFAULT NULL,
  `qta` int(11) DEFAULT NULL,
  `costo` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`idm`),
  KEY `reffarmacia` (`reffarmacia`),
  KEY `refprodotto` (`refprodotto`),
  CONSTRAINT `magazzino_ibfk_1` FOREIGN KEY (`reffarmacia`) REFERENCES `farmacia` (`idfarmacia`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `magazzino_ibfk_2` FOREIGN KEY (`refprodotto`) REFERENCES `prodotto` (`idprodotto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazzino`
--

LOCK TABLES `magazzino` WRITE;
/*!40000 ALTER TABLE `magazzino` DISABLE KEYS */;
INSERT INTO `magazzino` VALUES (4,1,1,'2022-07-13',2,12.50),(39,1,9,'2022-07-11',2,20.00),(40,1,2,'2022-09-25',1,34.00),(41,1,8,'2022-11-07',10,12.00);
/*!40000 ALTER TABLE `magazzino` ENABLE KEYS */;
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
  `nome` varchar(70) NOT NULL,
  `principioattivo` varchar(120) DEFAULT NULL,
  `dabanco` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idprodotto`),
  UNIQUE KEY `ean` (`ean`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES (1,'012389258275','Tachipirina','Paracetamolo',0),(2,'946294628402','Brufen','Ibuprofene',0),(3,'946204729374','Vivin C','Acido acetilsalicilico',0),(4,'845294759301','Bentelan','Betametasone',0),(5,'936402748572','Aspirina','Acido acetilsalicilico',0),(6,'946293847503','Momendol','Naprossene',0),(7,'946293847523','Gaviscon ','Sodio alginato',0),(8,'234629384750','Zitromax','Azitromicina',0),(9,'946293847512','Integratore di Vitamina C','Vitamina C',0),(10,'244243347503','Moment Act','Ibuprofene',0),(11,'946293843512','B12','B12',1),(12,'123456789021','Elisir di bellezza','Super bellezza',0);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `idutente` int(11) NOT NULL AUTO_INCREMENT,
  `nomeutente` varchar(30) NOT NULL,
  `password` char(64) NOT NULL,
  `salt` int(11) NOT NULL,
  `reffarmacia` int(11) NOT NULL,
  PRIMARY KEY (`idutente`),
  KEY `reffarmacia` (`reffarmacia`),
  CONSTRAINT `utente_ibfk_1` FOREIGN KEY (`reffarmacia`) REFERENCES `farmacia` (`idfarmacia`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'mimmo','7fab4e13ac54304d156074f17d21cf46a52b486726b7c46c0bef69b6ed1ea15f',290524740,1);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-04 14:21:55
