SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `mydb` ;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(256) NOT NULL,
  `email` VARCHAR(256) NOT NULL,
  `phone` VARCHAR(32) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`order` ;

CREATE TABLE IF NOT EXISTS `mydb`.`order` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `table_number` INT NOT NULL,
  `order_date` DATETIME NOT NULL,
  `checked_out` TINYINT NULL,
  `call_waiter_status` TINYINT NULL,
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`subcategory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`subcategory` ;

CREATE TABLE IF NOT EXISTS `mydb`.`subcategory` (
  `subcategory_id` INT NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  `category` INT NOT NULL,
  PRIMARY KEY (`subcategory_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`menu_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`menu_item` ;

CREATE TABLE IF NOT EXISTS `mydb`.`menu_item` (
  `menu_item_id` INT NOT NULL,
  `subcategory_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `picture_path` VARCHAR(256) NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`menu_item_id`),
  INDEX `fk_menu_item_subcategory1_idx` (`subcategory_id` ASC),
  CONSTRAINT `fk_menu_item_subcategory1`
    FOREIGN KEY (`subcategory_id`)
    REFERENCES `mydb`.`subcategory` (`subcategory_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`order_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`order_item` ;

CREATE TABLE IF NOT EXISTS `mydb`.`order_item` (
  `order_item_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  `menu_item_id` INT NOT NULL,
  `quantity` TINYINT NOT NULL,
  `notes` TEXT NULL,
  `kitchen_status` TINYINT NULL,
  PRIMARY KEY (`order_item_id`),
  INDEX `fk_order_item_order1_idx` (`order_id` ASC),
  INDEX `fk_order_item_menu_item1_idx` (`menu_item_id` ASC),
  CONSTRAINT `fk_order_item_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `mydb`.`order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_item_menu_item1`
    FOREIGN KEY (`menu_item_id`)
    REFERENCES `mydb`.`menu_item` (`menu_item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`review` ;

CREATE TABLE IF NOT EXISTS `mydb`.`review` (
  `review_id` INT NOT NULL,
  `menu_item_id` INT NOT NULL,
  `reviewer` VARCHAR(45) NULL,
  `rating` TINYINT NOT NULL,
  `review_text` TEXT NULL,
  `review_date` DATETIME NULL,
  PRIMARY KEY (`review_id`),
  INDEX `fk_review_menu_item1_idx` (`menu_item_id` ASC),
  CONSTRAINT `fk_review_menu_item1`
    FOREIGN KEY (`menu_item_id`)
    REFERENCES `mydb`.`menu_item` (`menu_item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`subcategory_tablet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`subcategory_tablet` ;

CREATE TABLE IF NOT EXISTS `mydb`.`subcategory_tablet` (
  `subcategory_id` INT NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  `category` INT NOT NULL,
  PRIMARY KEY (`subcategory_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`menu_item_tablet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`menu_item_tablet` ;

CREATE TABLE IF NOT EXISTS `mydb`.`menu_item_tablet` (
  `menu_item_id` INT NOT NULL,
  `subcategory_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `picture_path` VARCHAR(256) NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`menu_item_id`),
  INDEX `fk_menu_item_subcategory1_idx` (`subcategory_id` ASC),
  CONSTRAINT `fk_menu_item_subcategory1`
    FOREIGN KEY (`subcategory_id`)
    REFERENCES `mydb`.`subcategory_tablet` (`subcategory_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
