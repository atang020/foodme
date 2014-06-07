-- MySQL dump 10.13  Distrib 5.6.17, for Linux (x86_64)
--
-- Host: localhost    Database: foodme
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES
(1,1,'Baked Wedges','Alpha','67569ce25836ec440d3f2b8a89233ad6.jpg',5.50,0),
(2,1,'Pizza Fries','Beta','d3b56153bc498a7ce4e382818ec8a556.jpg',6.75,0),
(3,1,'Zucchini Fries','Gamma','0997c9e648700a98cc14a2412034a162.jpg',6.75,0),
(4,2,'Sugar Wings','Delta','3a80b9a987ba721efbcd7f69e9755952.jpg',5.50,0),
(5,2,'Sesame Wings','Epsilon','7535e84a09735c26a375e31aad68e86b.JPG',5.75,0),
(6,2,'Mystery Wings','Zeta','302966ca4e07c3e9c6b1e0fa2348e93a.jpg',5.75,0),
(7,3,'Original','Eta','ccfd339f0dbf1ff6110e16041bf0bede.jpg',4.50,0),
(8,4,'House Beer','Theta','42ff531d01ea79339774c10d6cc6b28a.jpg',5.00,0),
(9,5,'Mexican Coke','Iota','8509b0a70eebdd1f2c67383360d4ee73.jpg',5.00,0),
(10,6,'Cute Coffee','Kappa','d5efe5dbd7551559b58721c22ba9e6c7.jpg',5.00,0),
(11,7,'Brie Burger','Lambda','eac8e68f97bdb1e204fcbee44c4c6532.jpg',6.25,0),
(12,8,'Crayfish Taco','Mu','ed4128041dd831c5229dd1e5200c2853.jpg',4.00,0),
(13,9,'Burrito Bowl','Nu','f2b5594aaa1857c3f0913d2a2222eb6a.jpg',6.75,0),
(14,10,'PB Cake','Xi','400bf4167ebfadb30075240d70eafedf.jpg',7.99,0),
(15,12,'Dutch Apple Pie','Omnicron','954d0ab50048778d798da75e17d06a48.jpg',4.99,0)
(16,11,'Brownie Sundae','Pi','68b8d2932b5d2410815fd31881d1d37f.jpg',8.99,0),
/*!40000 ALTER TABLE `menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES
(1,11,'John Doe',5,'This was very tasty.','2014-06-07 18:19:13');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subcategory`
--

LOCK TABLES `subcategory` WRITE;
/*!40000 ALTER TABLE `subcategory` DISABLE KEYS */;
INSERT INTO `subcategory` VALUES
(1,'Fries',0),
(2,'Wings',0),
(3,'Nachos',0),
(4,'Beer',10),
(5,'Soda',10),
(6,'Coffee',10),
(7,'Burgers',20),
(8,'Tacos',20),
(9,'Burritos',20),
(10,'Cake',30),
(11,'Ice Cream',30),
(12,'Pies',30);
/*!40000 ALTER TABLE `subcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,3,'2014-06-08 08:00:00',0,1);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ticket_item`
--

LOCK TABLES `ticket_item` WRITE;
/*!40000 ALTER TABLE `ticket_item` DISABLE KEYS */;
INSERT INTO `ticket_item` VALUES
(1,1,4,1,'',0),
(2,1,11,2,'Make it extra spicy.',0),
(3,1,14,1,'I am very hungry.',0);
/*!40000 ALTER TABLE `ticket_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(1,'j','jadelane@ucsd.edu',NULL),
(2,'alex','alt020@ucsd.edu',NULL),
(3,'austin','acs008@ucsd.edu',NULL),
(4,'dennis','dscao@ucsd.edu',NULL),
(5,'henry','hetruong@ucsd.edu',NULL),
(6,'aaron','aspears@ucsd.edu',NULL),
(7,'payam','pshahidi@ucsd.edu',NULL),
(8,'peter','ylc015@ucsd.edu',NULL),
(9,'phillip','phodgson@ucsd.edu',NULL),
(10,'ryan','rmmcclur@ucsd.edu',NULL),
(11,'gary','ggillespie@ucsd.edu',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-07 18:20:20
