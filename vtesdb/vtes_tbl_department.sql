-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: vtes
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `tbl_department`
--

DROP TABLE IF EXISTS `tbl_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_department` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `DEPARTMENT_NAME` varchar(100) NOT NULL,
  `CREATE_DT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DT` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `DELETE_FLAG` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_department`
--

LOCK TABLES `tbl_department` WRITE;
/*!40000 ALTER TABLE `tbl_department` DISABLE KEYS */;
INSERT INTO `tbl_department` VALUES (1,'BOD','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (2,'営業第一部','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (3,'営業第二部','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (4,'営業第三部','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (5,'大阪事業所','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (6,'開発第ゼログループ','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (7,'開発第一グループ','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (8,'開発第二グループ','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (9,'開発第三グループ','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (10,'開発第四グループ','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (11,'開発第五グループ','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (12,'開発第六グループ','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (13,'マーケティング部','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (14,'総務部','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (15,'人事部','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (16,'採用','2023-05-23 02:11:56',NULL,NULL);												
INSERT INTO `tbl_department` VALUES (17,'経理・会計ー財務部','2023-05-23 02:11:56',NULL,NULL);

/*!40000 ALTER TABLE `tbl_department` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-25 16:50:50
