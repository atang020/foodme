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
INSERT INTO `menu_item` VALUES (1,1,'Curly Fries','Delicious curly fries that will make your mouth water.','sample.jpg',3.00),(2,1,'Seasoned Fries','They taste good','sample.jpg',4.00),(3,1,'Bad Fries','Really shitty','sample.jpg',1.25),(4,2,'Fancy Wings','Ping pong','sample.jpg',5.00),(5,2,'Hot Wings','SUPERHOT','sample.jpg',7.00),(6,2,'Great Wings','jkfjkasfdjklksfdk fsdfjnf jdfs jk sdfnfnasf dksf df asd asdf','sample.jpg',2.00),(8,3,'Cheesy Nachos','Delicious nachos that will make you very happy.','sample.jpg',1.25),(9,4,'Blue Moon','Wonderful light beer.','sample.jpg',3.00),(10,5,'Pepsi','Free refills.','sample.jpg',1.50),(11,6,'House Coffee','Hot South American coffee. Free refills.','sample.jpg',2.00),(12,7,'Cheeseburger','Our staple burger. Comes with pickles, tomatoes, and grilled onions.','sample.jpg',6.50),(13,8,'Carne Asada Taco','This tastes very good!','sample.jpg',4.00),(14,9,'Bean and Cheese Burrito','Your stomach will love you.','sample.jpg',5.00);
/*!40000 ALTER TABLE `menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
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
INSERT INTO `subcategory` VALUES (1,'Fries',0),(2,'Wings',0),(3,'Nachos',0),(4,'Beer',10),(5,'Soda',10),(6,'Coffee',10),(7,'Burgers',20),(8,'Tacos',20),(9,'Burritos',20),(10,'Cake',30),(11,'Ice Cream',30),(12,'Pies',30);
/*!40000 ALTER TABLE `subcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ticket_item`
--

LOCK TABLES `ticket_item` WRITE;
/*!40000 ALTER TABLE `ticket_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'j','jadelane@ucsd.edu',NULL),(2,'alex','alt020@ucsd.edu',NULL),(3,'austin','acs008@ucsd.edu',NULL),(4,'dennis','dscao@ucsd.edu',NULL),(5,'henry','hetruong@ucsd.edu',NULL),(6,'aaron','aspears@ucsd.edu',NULL),(7,'payam','pshahidi@ucsd.edu',NULL),(8,'peter','ylc015@ucsd.edu',NULL),(9,'phillip','phodgson@ucsd.edu',NULL),(10,'ryan','rmmcclur@ucsd.edu',NULL),(11,'gary','gary@gary.com',NULL);
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

-- Dump completed on 2014-05-29 22:20:33
