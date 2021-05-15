-- MySQL dump 10.13  Distrib 8.0.24, for macos11 (x86_64)
--
-- Host: localhost    Database: bookstore
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `bookstore`;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `accountid` int NOT NULL AUTO_INCREMENT,
  `balance` float DEFAULT NULL,
  PRIMARY KEY (`accountid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--


/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,9740),(2,1),(3,100),(4,633),(5,0),(6,50);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


--
-- Table structure for table `mybooks`
--

DROP TABLE IF EXISTS `mybooks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mybooks` (
  `bookid` int NOT NULL AUTO_INCREMENT,
  `author` varchar(30) NOT NULL,
  `title` varchar(30) NOT NULL,
  `price` float NOT NULL,
  `publishingdate` date NOT NULL,
  `salesamount` int NOT NULL,
  `storenumber` int NOT NULL,
  `remark` text NOT NULL,
  PRIMARY KEY (`bookid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mybooks`
--


/*!40000 ALTER TABLE `mybooks` DISABLE KEYS */;
INSERT INTO `mybooks` VALUES (1,'Tom','Java 编程思想',50,'2009-06-22',20,80,'Good Java Book'),(2,'Jerry','Oracle DBA 教材',60,'2009-06-22',12,88,'Good Oracle Book'),(3,'Bob','Ruby',50,'2009-06-22',12,88,'Good 0'),(4,'Mike','Javascript',51,'2009-06-22',2,98,'Good 1'),(5,'Rose','Ajax',52,'2009-06-22',0,100,'Good 2'),(6,'Backham','Struts',53,'2009-06-22',0,100,'Good 3'),(7,'Zidon','Hibernate',54,'2009-06-22',2,12,'Good 4'),(8,'Ronaerdo','Spring',55,'2009-06-22',3,12,'Good 5'),(9,'Clinsman','Cvs',56,'2009-06-22',0,16,'Good 6'),(10,'Kaka','Seo',57,'2009-06-22',0,17,'Good 7'),(11,'Lauer','Lucence',58,'2009-06-22',0,18,'Good 8'),(12,'Kasi','Guice',59,'2009-06-22',0,19,'Good 9'),(13,'Prierlo','Mysql',60,'2009-06-22',7,13,'Good 10'),(14,'Haohaidong','DB2',61,'2009-06-22',9,12,'Good 11'),(15,'Feige','Sybase',62,'2009-06-22',8,14,'Good 12'),(16,'Tuoleisi','DBDesign',63,'2009-06-22',0,23,'Good 13'),(17,'Jielade','Eclipse',64,'2009-06-22',0,24,'Good 14'),(18,'Teli','Netbeans',65,'2009-06-22',0,25,'Good 15'),(19,'Lapade','C#',66,'2009-06-22',0,26,'Good 16'),(20,'Runi','JDBC',67,'2009-06-22',0,27,'Good 17'),(21,'JoeKeer','Php',68,'2009-06-22',0,28,'Good 18'),(22,'Jordan','MysqlFront',69,'2009-06-22',5,24,'Good 19'),(23,'Yaoming','NoteBook',70,'2009-06-22',5,25,'Good 20'),(24,'Yi','C',71,'2009-06-22',5,26,'Good 21'),(25,'Sun','Css',72,'2009-06-22',0,32,'Good 22'),(26,'Xuliang','JQuery',73,'2009-06-22',0,33,'Good 23'),(27,'Meixi','Ext',74,'2009-06-22',0,34,'Good 24'),(28,'Apple','iphone',75,'2009-06-22',0,35,'Good 25'),(29,'Aigo','dc',76,'2009-06-22',0,36,'Good 26'),(30,'Sony','psp',77,'2009-06-22',0,100,'Good 27'),(31,'IRiver','mp3',78,'2009-06-22',0,100,'Good 28'),(32,'Sansing','TV',79,'2009-06-22',0,100,'Good 29');
/*!40000 ALTER TABLE `mybooks` ENABLE KEYS */;


--
-- Table structure for table `trade`
--

DROP TABLE IF EXISTS `trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade` (
  `tradeid` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `tradetime` timestamp NOT NULL,
  PRIMARY KEY (`tradeid`),
  KEY `user_id_fk` (`userid`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`userid`) REFERENCES `userinfo` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade`
--


/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
INSERT INTO `trade` VALUES (12,1,'2012-10-31 16:00:00'),(13,1,'2012-10-31 16:00:00'),(14,1,'2012-10-31 16:00:00'),(15,1,'2012-12-19 16:00:00'),(16,1,'2012-12-19 16:00:00'),(17,4,'2021-05-14 16:00:00'),(18,4,'2021-05-14 16:00:00'),(19,4,'2021-05-14 16:00:00'),(20,4,'2021-05-15 10:00:46');
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;


--
-- Table structure for table `tradeitem`
--

DROP TABLE IF EXISTS `tradeitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tradeitem` (
  `itemid` int NOT NULL AUTO_INCREMENT,
  `bookid` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `tradeid` int DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  KEY `book_id_fk` (`bookid`),
  KEY `trade_id_fk` (`tradeid`),
  CONSTRAINT `book_id_fk` FOREIGN KEY (`bookid`) REFERENCES `mybooks` (`bookid`),
  CONSTRAINT `trade_id_fk` FOREIGN KEY (`tradeid`) REFERENCES `trade` (`tradeid`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tradeitem`
--


/*!40000 ALTER TABLE `tradeitem` DISABLE KEYS */;
INSERT INTO `tradeitem` VALUES (22,1,10,12),(23,2,10,12),(24,3,10,12),(25,1,1,13),(26,13,2,13),(27,14,3,13),(28,15,4,13),(29,1,1,14),(30,13,2,14),(31,14,3,14),(32,15,4,14),(33,22,5,14),(34,23,5,14),(35,24,5,14),(36,2,1,15),(37,1,2,15),(38,3,1,15),(39,2,1,16),(40,1,3,16),(41,3,1,16),(42,4,2,17),(43,8,1,17),(44,13,1,17),(45,1,1,18),(46,1,1,19),(47,1,1,20);
/*!40000 ALTER TABLE `tradeitem` ENABLE KEYS */;


--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userinfo` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `accountid` int DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  KEY `account_id_fk` (`accountid`),
  CONSTRAINT `account_id_fk` FOREIGN KEY (`accountid`) REFERENCES `account` (`accountid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--


/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES (1,'Tom',1,'e10adc3949ba59abbe56e057f20f883e'),(2,'AA',2,'e10adc3949ba59abbe56e057f20f883e'),(3,'BB',3,'e10adc3949ba59abbe56e057f20f883e'),(4,'CC',4,'e10adc3949ba59abbe56e057f20f883e'),(5,'DD',5,'e10adc3949ba59abbe56e057f20f883e'),(6,'EE',6,'e10adc3949ba59abbe56e057f20f883e');
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-15 20:33:02
