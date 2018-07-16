SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `carsale` ;
CREATE SCHEMA IF NOT EXISTS `carsale` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `carsale` ;

-- -----------------------------------------------------
-- Table `carsale`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `carsale`.`customer` ;

CREATE  TABLE IF NOT EXISTS `carsale`.`customer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `phone` VARCHAR(45) NOT NULL ,
  `address` VARCHAR(45) NOT NULL ,
  `state` VARCHAR(2) NOT NULL ,
  `cc_number` VARCHAR(19) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
COMMENT = 'maintains customer details';


-- -----------------------------------------------------
-- Table `carsale`.`customer_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `carsale`.`customer_order` ;

CREATE  TABLE IF NOT EXISTS `carsale`.`customer_order` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `amount` DECIMAL(6,2) NOT NULL ,
  `date_created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `confirmation_number` INT UNSIGNED NOT NULL ,
  `customer_id` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_customer_order_customer` (`customer_id` ASC) ,
  CONSTRAINT `fk_customer_order_customer`
    FOREIGN KEY (`customer_id` )
    REFERENCES `carsale`.`customer` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'maintains customer order details';


-- -----------------------------------------------------
-- Table `carsale`.`make`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `carsale`.`make` ;

CREATE  TABLE IF NOT EXISTS `carsale`.`make` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `makename` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
COMMENT = 'contains car makes i.e., ford, dodge, plymouth';


-- -----------------------------------------------------
-- Table `carsale`.`car`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `carsale`.`car` ;

CREATE  TABLE IF NOT EXISTS `carsale`.`car` (
  `vin` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `modelyear` INT(5) NOT NULL,
  `modelname` VARCHAR(45) NOT NULL ,
  `price` DECIMAL(5,2) NOT NULL ,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `make_id` TINYINT UNSIGNED NOT NULL ,
  PRIMARY KEY (`vin`) ,
  INDEX `fk_car_make` (`make_id` ASC) ,
  CONSTRAINT `fk_car_make`
    FOREIGN KEY (`make_id` )
    REFERENCES `carsale`.`make` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'contains car make details';


-- -----------------------------------------------------
-- Table `carsale`.`ordered_car`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `carsale`.`ordered_car` ;

CREATE  TABLE IF NOT EXISTS `carsale`.`ordered_car` (
  `customer_order_id` INT UNSIGNED NOT NULL ,
  `car_vin` INT UNSIGNED NOT NULL ,
  `quantity` SMALLINT UNSIGNED NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`customer_order_id`, `car_vin`) ,
  INDEX `fk_ordered_car_customer_order` (`customer_order_id` ASC) ,
  INDEX `fk_ordered_car_car` (`car_vin` ASC) ,
  CONSTRAINT `fk_ordered_car_customer_order`
    FOREIGN KEY (`customer_order_id` )
    REFERENCES `carsale`.`customer_order` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordered_car_car`
    FOREIGN KEY (`car_vin` )
    REFERENCES `carsale`.`car` (`vin` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;