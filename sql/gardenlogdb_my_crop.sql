-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: gardenlogdb
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `my_crop`
--

DROP TABLE IF EXISTS `my_crop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_crop` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) NOT NULL,
  `gardenid` int NOT NULL,
  `cropid` int NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `planted_date` date NOT NULL,
  `water_count` int NOT NULL DEFAULT '0',
  `last_watered_at` datetime DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'growing',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  KEY `my_crop_ibfk_2` (`cropid`),
  KEY `fk_my_crop_garden` (`gardenid`),
  CONSTRAINT `fk_my_crop_garden` FOREIGN KEY (`gardenid`) REFERENCES `garden` (`gardenid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `my_crop_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE RESTRICT,
  CONSTRAINT `my_crop_ibfk_2` FOREIGN KEY (`cropid`) REFERENCES `crop_info` (`cropid`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_crop`
--

LOCK TABLES `my_crop` WRITE;
/*!40000 ALTER TABLE `my_crop` DISABLE KEYS */;
INSERT INTO `my_crop` VALUES (3,'leenayeon',1,6,'강낭콩','2025-10-10',3,NULL,'growing','2025-11-30 16:20:02','2025-11-30 16:20:02'),(4,'leenayeon',2,1,'가지','2025-08-20',5,NULL,'growing','2025-11-30 16:20:02','2025-11-30 16:20:02');
/*!40000 ALTER TABLE `my_crop` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-01  1:20:41
