DROP SCHEMA IF EXISTS `foodme`;
CREATE SCHEMA `foodme` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `foodme`;

-- -----------------------------------------------------
-- Table structure for table `user`
-- Stores users for the website.
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(256) NOT NULL,
  `email` VARCHAR(256) NOT NULL,
  `phone` VARCHAR(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table structure for table `order`
-- Used to store orders that are being made by tables.
-- -----------------------------------------------------
DROP TABLE IF EXISTS `order` ;

CREATE TABLE `order` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `table_number` INT NOT NULL,
  `order_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `checked_out` TINYINT NOT NULL,
  `call_waiter_status` TINYINT NOT NULL,
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `subcategory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subcategory` ;

CREATE TABLE `subcategory` (
  `subcategory_id` INT NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  `category` INT NOT NULL,
  PRIMARY KEY (`subcategory_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `menu_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu_item` ;

CREATE TABLE `menu_item` (
  `menu_item_id` INT NOT NULL,
  `subcategory_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NOT NULL,
  `picture_path` VARCHAR(256) NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`menu_item_id`),
  FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory`(`subcategory_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `order_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `order_item` ;

CREATE TABLE `order_item` (
  `order_item_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  `menu_item_id` INT NOT NULL,
  `quantity` TINYINT NOT NULL,
  `notes` TEXT NOT NULL,
  `kitchen_status` TINYINT NOT NULL,
  PRIMARY KEY (`order_item_id`),
  FOREIGN KEY (`order_id`) REFERENCES `order`(`order_id`),
  FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item`(`menu_item_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `review` ;

CREATE TABLE `review` (
  `review_id` INT NOT NULL,
  `menu_item_id` INT NOT NULL,
  `reviewer` VARCHAR(45) NULL,
  `rating` TINYINT NOT NULL,
  `review_text` TEXT NULL,
  `review_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item`(`menu_item_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `setting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `setting` ;

CREATE TABLE `setting` (
  `key` VARCHAR(45) NOT NULL,
  `value` VARCHAR(45) NULL,
  PRIMARY KEY (`review_id`))
ENGINE = InnoDB;
