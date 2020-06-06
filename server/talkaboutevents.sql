-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: localhost    Database: talkaboutevents
-- ------------------------------------------------------
-- Server version	5.7.30-0ubuntu0.18.04.1

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
  `pdp` text,
  `bio` varchar(255) DEFAULT 'empty',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Utilisateurs`
--

LOCK TABLES `Utilisateurs` WRITE;
/*!40000 ALTER TABLE `Utilisateurs` DISABLE KEYS */;
INSERT INTO `Utilisateurs` VALUES (1,'test','felous77@gmail.com','GolV11uf0MukzB4DvohvHw==','izdOW6WLfkdepgP8vjJ8A+hjba5IGwy4Z/7nGU4d5Mv00aeHfGn+aYtI+ZOjl5QQL+rQPVekDpM1/OKJzAl9WLtxPqx/pG+WyH/+RBUMum4+3Y9iHaMKvI4UFrIlEpNv69bPib/UB16l7+6jPiwyCPqa6IXypwcjpIpEcfmdFog=','chien.png','empty'),(2,'test1','email','nkpPE/+XVzHGPD8ev3+JqQ==','LfQsNCNZz8UT+PZwZMgZ4n9PTUHE7ddW6y0ftjZ0r7j7FeQEhOxvxRGuh/0O0rjkljxGpPiz/D5iSypnOw53hOlY+2oyTcmDPpk5zav1v5KcRak2IAkMCGJelNQ8yWjPOJLF6IIoRY4HPij4yz39780BuZOZV2B9eF6klFDA7Y8=','pdp','bio'),(3,'test2','test2@gmail.com','BoByRo2rSafmLMGA2qizjw==','f/Z7uM98W3rG6wHgvJOXuDbu4vNcrN+3z9jDvj+HZVSre6W7mI5jLYBqmHc6Lid0saq9e8UjjJXEiuBp0RROSu6K3rGIVeTO9yMmH/VTx9ExW/8gu6DfFaWP+hvpiymmmUeGcVyGyC/k6vFjpUH5BcwDryPlERYtz7yuECG60H4=','pdp','bio'),(4,'test3','test3@gmail.com','az3rz5wt3p0+iQfKBHGhew==','3WxZm8ZK3Qd232JlsuSXNmBXXqVMg3hlwYghAx9SPo8avQS/UeUzOmgkKXGTfJOlPFAPbONfo5gVSPSrT6QtK0Rh3J3sqlD6mwO7sBy6gqMT1K9e6nESiyrKTwtyFqa6mclc9Df9JYQR+HAsHCHbrBzl6iNGPjH6Po4biPpzFXA=',NULL,NULL),(5,'test3','test3@gmail.com','hBRLxxLKEOiP3B7B2Z5z5Q==','gHa9QXwcBnXzRGHUhGPPoXTsZ0iJZOKBjez61J5/MW0VHtWxz5vZEUa/rjwUWJ3NUsN97YrZNSKw7RmHQZ1wMmoIe7Ev+ibYKoBCfQyuRT7NrRu0+C+4+5ovadnIXheYu/rvclPcNU39MKcmqR2vjFkNHH4uB09uGRI9SsMNx9I=',NULL,NULL),(6,'test5','test5@gmail.com','pTeI2rs6kPzNgJP784SBgA==','vpR4bZtHUXDRmP5o3/MptZV3iZT8aO+mivT7q3PvNOT6mNG2O0awx7Jhbjp/R1WI/FWzm+ZhJOMGnRgFs3r7jC3R6KfxLf1RcGl6ldv/bWoKVQWsXnWWkyUPSSBnXLsOZVhDPTdMTOZdgdNkU2tJsSDLNK9cDJDPHipwx/2y7Ag=',NULL,NULL),(7,'test6','test6@gmail.com','7EzI7Ud9iFXrFvfNRd4oUw==','aUfypnir6bipLu3+RuEizrAZCVvyk9e6Xr4UYR7KBMFr+j2maLMmt2t/qFwdXL5zwe95qiRCyyao7YIKLodGKHrPkYkucreycekxvJlegfXxMLl9c/DkHmS8WZ+DQ3XTOMngOU5/vuwlYvR8RNJXJ6xlooYWr6ER7NK7oitrx0w=',NULL,NULL),(8,'test7','test7@gmail.com','TsoKDDCyZtp/hqY/RmCsgQ==','5X8TJLuTY1ECP09ZHR5UcqwrkTFWfIa2BZMSIIjluonhOjHTv8wm8uMtehB7c5+E2TaK0PAdl2FHcVjqgkpwwTEkGFsII09QHhMPMOFZca3ope5pesAX0bvwsNTyfwj2mTO9XkQKc88Pzq07V/9ZWRwmMn0mHjM70+q8ASitccw=',NULL,NULL);
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

-- Dump completed on 2020-06-06 15:15:22
