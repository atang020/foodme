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
DROP TABLE IF EXISTS `ticket` ;

CREATE TABLE `ticket` (
  `ticket_id` INT NOT NULL AUTO_INCREMENT,
  `table_number` INT NOT NULL,
  `ticket_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `checked_out` TINYINT NOT NULL DEFAULT 0,
  `call_waiter_status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ticket_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `subcategory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subcategory` ;

CREATE TABLE `subcategory` (
  `subcategory_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  `category` INT NOT NULL,
  PRIMARY KEY (`subcategory_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `menu_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menu_item` ;

CREATE TABLE `menu_item` (
  `menu_item_id` INT NOT NULL AUTO_INCREMENT,
  `subcategory_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NOT NULL,
  `picture_path` VARCHAR(512) NULL DEFAULT '',
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`menu_item_id`),
  FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory`(`subcategory_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `order_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ticket_item` ;

CREATE TABLE `ticket_item` (
  `ticket_item_id` INT NOT NULL AUTO_INCREMENT,
  `ticket_id` INT NOT NULL,
  `menu_item_id` INT NOT NULL,
  `quantity` TINYINT NOT NULL DEFAULT 1,
  `notes` TEXT NOT NULL,
  `kitchen_status` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ticket_item_id`),
  FOREIGN KEY (`ticket_id`) REFERENCES `ticket`(`ticket_id`),
  FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item`(`menu_item_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `review` ;

CREATE TABLE `review` (
  `review_id` INT NOT NULL AUTO_INCREMENT,
  `menu_item_id` INT NOT NULL,
  `reviewer` VARCHAR(45) NULL,
  `rating` TINYINT NOT NULL,
  `review_text` TEXT NULL,
  `review_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item`(`menu_item_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table structure for table `setting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `setting` ;

CREATE TABLE `setting` (
  `key` VARCHAR(45) NOT NULL,
  `value` VARCHAR(45) NULL)
ENGINE = InnoDB;
