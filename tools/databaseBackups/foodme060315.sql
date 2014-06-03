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
-- Table structure for table `menu_item`
--

DROP TABLE IF EXISTS `menu_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu_item` (
  `menu_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `subcategory_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `picture_path` varchar(512) DEFAULT '',
  `price` decimal(6,2) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`menu_item_id`),
  KEY `subcategory_id` (`subcategory_id`),
  CONSTRAINT `menu_item_ibfk_1` FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory` (`subcategory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES (1,9,'Burrito Bowl','Tired of tortillas? Get a burrito bowl to fill yourself up with. Comes with black beans, cheese, and carne asada.','f2b5594aaa1857c3f0913d2a2222eb6a.jpg',6.75,0),(2,1,'Baked Potato Wedges','Sliced and baked potato wedges.  Just the way your dad used to make them.','67569ce25836ec440d3f2b8a89233ad6.jpg',5.50,0),(3,9,'Vegetarian Burrito','Get all the taste of a burrito with no loss of flavor!','05bc1dc1a99ce1c4d3acc97fbf7b9dd8.jpg',4.50,0),(4,9,'Carnitas Burrito','Delicious burrito that will make your mouth water.','08ae9406573ead983cf2570597919ecc.jpg',7.25,0),(5,3,'Original Nachos','Authentic nachos made in Mexico.','ccfd339f0dbf1ff6110e16041bf0bede.jpg',4.50,1),(6,3,'Nach-yo Typical Nachos','Nachos made from organic corn baked in a stone oven','f1e31f07a38874a084dd2a4e31e36152.jpg',5.00,0),(7,8,'Crayfish Taco','Our best-selling taco!','ed4128041dd831c5229dd1e5200c2853.jpg',4.00,0),(8,8,'Black Bean Taco','Wonderful taco for those who dislike meat.','c30cf134b74216e204ac43003f6fb415.jpg',2.75,0),(11,7,'Brie Burger','A world-famous burger made with high quality ingredients.','eac8e68f97bdb1e204fcbee44c4c6532.jpg',6.25,0),(12,7,'Whiskey Burger','Cooked in Whiskey. Enough said.','6a56362f93ed2ac74c3c03223aab54ed.jpg',5.25,0),(13,1,'Pizza Fries','You want pizza?  You want fries? Why not get both at the same time?','d3b56153bc498a7ce4e382818ec8a556.jpg',6.75,0),(14,4,'House Beer','This is beer I made in my house, how do you think it tastes','42ff531d01ea79339774c10d6cc6b28a.jpg',5.00,0),(15,4,'Duff Beer','As seen on the Simpsons','078793f991fa3c10711301f2c6650bd6.jpg',5.00,0),(17,1,'Zucchini Fries','Better than at the fair.','0997c9e648700a98cc14a2412034a162.jpg',6.75,0),(18,10,'Reese Peanut Butter Chocolate Brownie Cheesecake','Delicious cheesecake that will melt in your mouth.','400bf4167ebfadb30075240d70eafedf.jpg',7.99,0),(19,4,'European Beer','The best beer in all of the great country of Europe','f897f3eea17194903e60baa89377705b.jpg',5.00,0),(20,2,'Brown Sugar Buffalo Wings','These wings are fried and slathered in a delicious brown sugar glaze made from 100% Hawaiian cane sugar.','3a80b9a987ba721efbcd7f69e9755952.jpg',5.50,0),(21,5,'Mexican Coke','The best soda from the other side of the border. Now made with real sugar','8509b0a70eebdd1f2c67383360d4ee73.jpg',5.00,0),(22,5,'Sprite','Sweet, refreshing, and tastes of lemon/lime what\'s not to like','b982f19f02ff83982c4004c62f55ea49.jpg',5.00,0),(23,2,'Asian Sesame Wings','Fried in a honey sauce and covered with fresh sesame seeds imported from Africa daily.','7535e84a09735c26a375e31aad68e86b.JPG',5.75,0),(24,5,'Milkis',' It\'s milk and soda and tastes fantastic','756bbe6d0673ff029adee231bc6a72e1.jpg',5.00,0),(25,6,'Cute Coffee','Watch our baristas create wonderful custom pictures in your expresso','d5efe5dbd7551559b58721c22ba9e6c7.jpg',5.00,0),(26,6,'Expensive European Coffee','Straight from the nation of Europe, the best coffee made delicately from the hands of artisans','18f3dcd8dbf65c593c5c3b344faaba62.jpg',5.00,0),(27,6,'Hipster Coffee','It\'s all hipster and vegan and stuff. Made out of organic hippie materials in American farms','82be46ab9216c642e65577beba9761f9.jpg',5.00,0),(28,2,'Mystery Wings','A new flavor is randomly chosen each day!','302966ca4e07c3e9c6b1e0fa2348e93a.jpg',5.75,0),(31,12,'Dutch Apple Pie','Homemade apple pie.','954d0ab50048778d798da75e17d06a48.jpg',4.99,0),(32,12,'Pumpkin Pie','Delicious pumpkin pie, served with whip cream.','7aff95f371c31c0b36866e6b87d92b0b.jpg',3.99,0),(33,10,'Red Velvet Cake','After you\'re done eating this slice of cake, you\'d wish you had more!','1904055a01275b0517f2ed9e1ca9bec3.jpg',2.99,0),(34,10,'Mint Chocolate Chip Ice Cream Cake','Love mint ice cream? Love cake? Here is a way you can enjoy both.','8c778d9202cea66aec59631125b81a0d.jpg',6.99,0),(35,11,'Brownie Sundae','Tons of ice cream on brownies. What more could you want?','68b8d2932b5d2410815fd31881d1d37f.jpg',8.99,0),(36,11,'Various Ice Cream Sandwiches','Available in mint, strawberry, cookies n\' cream and chocolate!','e6feb0d93b92d36e4970f021dd37b850.jpg',4.50,0),(37,11,'Moon Mist Ice Cream','A mixture of blueberry, banana, grape and bubble gum that will make you want to come back for more.','29341d4b89badabd0658894f0e900b0b.jpg',6.00,0),(38,10,'Chocolate Mud Cake with Raspberry Buttercream','A cake that tastes just as great as it looks.','1f52f34a126d33a302ed33899e8dbd2d.jpg',5.99,0);
/*!40000 ALTER TABLE `menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_item_id` int(11) NOT NULL,
  `reviewer` varchar(45) DEFAULT NULL,
  `rating` tinyint(4) NOT NULL,
  `review_text` text,
  `review_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  KEY `menu_item_id` (`menu_item_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`menu_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `key` varchar(45) NOT NULL,
  `value` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subcategory`
--

DROP TABLE IF EXISTS `subcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subcategory` (
  `subcategory_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `category` int(11) NOT NULL,
  PRIMARY KEY (`subcategory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subcategory`
--

LOCK TABLES `subcategory` WRITE;
/*!40000 ALTER TABLE `subcategory` DISABLE KEYS */;
INSERT INTO `subcategory` VALUES (1,'Fries',0),(2,'Wings',0),(3,'Nachos',0),(4,'Beer',10),(5,'Soda',10),(6,'Coffee',10),(7,'Burgers',20),(8,'Tacos',20),(9,'Burritos',20),(10,'Cake',30),(11,'Ice Cream',30),(12,'Pies',30);
/*!40000 ALTER TABLE `subcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `ticket_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_number` int(11) NOT NULL,
  `ticket_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `checked_out` tinyint(4) NOT NULL DEFAULT '0',
  `call_waiter_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (107,12,'2014-06-03 04:14:21',0,0),(108,12,'2014-06-03 04:16:08',0,0),(109,12,'2014-06-03 04:16:55',0,0),(110,12,'2014-06-03 04:17:30',0,0),(111,12,'2014-06-03 04:18:09',0,0),(112,12,'2014-06-03 04:18:42',0,0),(113,12,'2014-06-03 04:19:41',0,0),(114,12,'2014-06-03 04:22:14',0,0),(115,12,'2014-06-03 04:29:35',0,0),(116,12,'2014-06-03 04:34:18',0,0),(117,12,'2014-06-03 04:35:16',0,1),(118,12,'2014-06-03 04:46:37',0,1),(119,12,'2014-06-03 04:50:12',0,0),(120,12,'2014-06-03 04:51:35',0,0),(121,12,'2014-06-03 04:55:14',0,0),(122,12,'2014-06-03 04:59:12',0,0);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_item`
--

DROP TABLE IF EXISTS `ticket_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_item` (
  `ticket_item_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` int(11) NOT NULL,
  `menu_item_id` int(11) NOT NULL,
  `quantity` tinyint(4) NOT NULL DEFAULT '1',
  `notes` text NOT NULL,
  `kitchen_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ticket_item_id`),
  KEY `ticket_id` (`ticket_id`),
  KEY `menu_item_id` (`menu_item_id`),
  CONSTRAINT `ticket_item_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`ticket_id`),
  CONSTRAINT `ticket_item_ibfk_2` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`menu_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_item`
--

LOCK TABLES `ticket_item` WRITE;
/*!40000 ALTER TABLE `ticket_item` DISABLE KEYS */;
INSERT INTO `ticket_item` VALUES (1,117,4,1,'Imagine your at goodies',1),(2,117,26,1,'Have some coffee Phillip',1),(3,117,26,1,'Have some coffee Phillip',1),(4,117,26,1,'Have some coffee Phillip',1),(5,118,7,1,'crazy',1),(6,118,8,1,'beans uh oh',1),(7,118,8,1,'beans uh oh',1);
/*!40000 ALTER TABLE `ticket_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'j','jadelane@ucsd.edu',NULL),(2,'alex','alt020@ucsd.edu',NULL),(3,'austin','acs008@ucsd.edu',NULL),(4,'dennis','dscao@ucsd.edu',NULL),(5,'henry','hetruong@ucsd.edu',NULL),(6,'aaron','aspears@ucsd.edu',NULL),(7,'payam','pshahidi@ucsd.edu',NULL),(8,'peter','ylc015@ucsd.edu',NULL),(9,'phillip','phodgson@ucsd.edu',NULL),(10,'ryan','rmmcclur@ucsd.edu',NULL),(11,'gary','ggillespie@ucsd.edu',NULL);
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

-- Dump completed on 2014-06-03 15:15:47
