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
-- Table structure for table `farming_logs`
--

DROP TABLE IF EXISTS `farming_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farming_logs` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(20) NOT NULL,
  `my_crop_id` int DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `content` text,
  `weather` varchar(20) DEFAULT NULL,
  `log_date` date DEFAULT (curdate()),
  `log_img` varchar(255) DEFAULT NULL,
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `log_type` varchar(20) DEFAULT '관찰',
  PRIMARY KEY (`log_id`),
  KEY `userid` (`userid`),
  KEY `my_crop_id` (`my_crop_id`),
  CONSTRAINT `farming_logs_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `farming_logs_ibfk_2` FOREIGN KEY (`my_crop_id`) REFERENCES `my_crop` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farming_logs`
--

LOCK TABLES `farming_logs` WRITE;
/*!40000 ALTER TABLE `farming_logs` DISABLE KEYS */;
INSERT INTO `farming_logs` VALUES (3,'student',58,'방울토마토 물주기','방울토마토에 물주기 1회차!','맑음','2025-12-01',NULL,'2025-12-11 00:35:09','물주기'),(4,'student',61,'처음 키워보는 감자','감자를 처음으로 심은 날?','흐림','2025-12-05',NULL,'2025-12-11 00:36:03','물주기'),(5,'student',59,'새순이 올라왔어요!','오늘 아침에 확인해보니 상추 새순이 1cm 정도 자라 있었다.\r\n잎 색도 연두빛이라 건강해 보임! 앞으로 이틀 간격으로 물 관리만 신경 쓰면 될 듯.','맑음','2025-12-11',NULL,'2025-12-11 00:37:55','관찰'),(6,'student',61,'비 와서 물주기 패스','오늘 하루 종일 비가 와서 추가 물주기는 하지 않았음. \r\n토양 수분도 충분한 상태라 내일은 관찰만 하면 될 듯!','비','2025-12-07',NULL,'2025-12-11 00:38:30','물주기');
/*!40000 ALTER TABLE `farming_logs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-11  0:39:38
