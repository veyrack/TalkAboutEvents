-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: talkaboutevents
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

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
-- Table structure for table `Abonnement`
--

DROP TABLE IF EXISTS `Abonnement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Abonnement` (
  `idUser` int(11) NOT NULL,
  `idEnter` varchar(255) NOT NULL,
  PRIMARY KEY (`idUser`,`idEnter`),
  KEY `idEnter` (`idEnter`),
  CONSTRAINT `abonnement_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `Utilisateurs` (`id`),
  CONSTRAINT `abonnement_ibfk_2` FOREIGN KEY (`idEnter`) REFERENCES `Entertainment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Abonnement`
--

LOCK TABLES `Abonnement` WRITE;
/*!40000 ALTER TABLE `Abonnement` DISABLE KEYS */;
/*!40000 ALTER TABLE `Abonnement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Entertainment`
--

DROP TABLE IF EXISTS `Entertainment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Entertainment` (
  `id` varchar(255) NOT NULL,
  `label` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Entertainment`
--

LOCK TABLES `Entertainment` WRITE;
/*!40000 ALTER TABLE `Entertainment` DISABLE KEYS */;
/*!40000 ALTER TABLE `Entertainment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Participe`
--

DROP TABLE IF EXISTS `Participe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Participe` (
  `idUser` int(11) NOT NULL,
  `idEvent` varchar(255) NOT NULL,
  PRIMARY KEY (`idUser`,`idEvent`),
  CONSTRAINT `participe_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `Utilisateurs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Participe`
--

LOCK TABLES `Participe` WRITE;
/*!40000 ALTER TABLE `Participe` DISABLE KEYS */;
/*!40000 ALTER TABLE `Participe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Utilisateurs`
--

DROP TABLE IF EXISTS `Utilisateurs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Utilisateurs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `pdp` varchar(100) DEFAULT 'empty',
  `bio` varchar(255) DEFAULT 'empty',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Utilisateurs`
--

LOCK TABLES `Utilisateurs` WRITE;
/*!40000 ALTER TABLE `Utilisateurs` DISABLE KEYS */;
INSERT INTO `Utilisateurs` VALUES (1,'test','felous77@gmail.com','GolV11uf0MukzB4DvohvHw==','izdOW6WLfkdepgP8vjJ8A+hjba5IGwy4Z/7nGU4d5Mv00aeHfGn+aYtI+ZOjl5QQL+rQPVekDpM1/OKJzAl9WLtxPqx/pG+WyH/+RBUMum4+3Y9iHaMKvI4UFrIlEpNv69bPib/UB16l7+6jPiwyCPqa6IXypwcjpIpEcfmdFog=','chien.png','empty');
/*!40000 ALTER TABLE `Utilisateurs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-23 22:23:46
