-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema scheddb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema scheddb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scheddb` DEFAULT CHARACTER SET utf8 ;
USE `scheddb` ;

-- -----------------------------------------------------
-- Table `scheddb`.`INSTRUCTORS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`INSTRUCTORS` (
  `INSTR_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `FIRSTNAME` VARCHAR(45) NOT NULL,
  `LASTNAME` VARCHAR(45) NOT NULL,
  `SSN` VARCHAR(9) NULL,
  PRIMARY KEY (`INSTR_ID`),
  UNIQUE INDEX `SSN_UNIQUE` (`SSN` ASC) VISIBLE,
  INDEX `LASTNAME_IDX` (`LASTNAME` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`CLIENTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`CLIENTS` (
  `CLIENT_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `FIRSTNAME` VARCHAR(45) NOT NULL,
  `LASTNAME` VARCHAR(45) NOT NULL,
  `GENDER` CHAR(1) NULL,
  `BIRTHDAY` DATE NULL,
  PRIMARY KEY (`CLIENT_ID`),
  INDEX `LASTNAME_IDX` (`LASTNAME` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`SPECIALTIES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`SPECIALTIES` (
  `SPEC_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`SPEC_ID`),
  UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`SPECIALTIES_INSTRUCTORS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`SPECIALTIES_INSTRUCTORS` (
  `INSTR_ID` BIGINT NOT NULL,
  `SPEC_ID` BIGINT NOT NULL,
  PRIMARY KEY (`INSTR_ID`, `SPEC_ID`),
  INDEX `SPEC_ID_FK_idx` (`SPEC_ID` ASC) VISIBLE,
  CONSTRAINT `INSTR_ID_FK`
    FOREIGN KEY (`INSTR_ID`)
    REFERENCES `scheddb`.`INSTRUCTORS` (`INSTR_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `SPEC_ID_FK`
    FOREIGN KEY (`SPEC_ID`)
    REFERENCES `scheddb`.`SPECIALTIES` (`SPEC_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`DAYS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`DAYS` (
  `DAY_ID` BIGINT NOT NULL,
  `DAY` VARCHAR(45) NULL,
  PRIMARY KEY (`DAY_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`ROOMS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`ROOMS` (
  `ROOM_ID` BIGINT NOT NULL,
  PRIMARY KEY (`ROOM_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`CLASSES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`CLASSES` (
  `ROOM_ID` BIGINT NOT NULL,
  `DAY_ID` BIGINT NOT NULL,
  `INSTR_ID` BIGINT NOT NULL,
  `SPEC_ID` BIGINT NOT NULL,
  `STARTING_TIME` TIME NOT NULL,
  `ENDING_TIME` TIME NOT NULL,
  PRIMARY KEY (`ROOM_ID`, `DAY_ID`, `STARTING_TIME`, `ENDING_TIME`),
  INDEX `DAY_ID_FK_idx` (`DAY_ID` ASC) VISIBLE,
  INDEX `ROOM_DAY_TIME_IDX` (`ROOM_ID` ASC, `DAY_ID` ASC, `STARTING_TIME` ASC, `ENDING_TIME` ASC) VISIBLE,
  INDEX `INSTR_ID_FK2_idx` (`INSTR_ID` ASC) VISIBLE,
  INDEX `SPEC_ID_FK2_idx` (`SPEC_ID` ASC) VISIBLE,
  CONSTRAINT `DAY_ID_FK`
    FOREIGN KEY (`DAY_ID`)
    REFERENCES `scheddb`.`DAYS` (`DAY_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `INSTR_ID_FK2`
    FOREIGN KEY (`INSTR_ID`)
    REFERENCES `scheddb`.`INSTRUCTORS` (`INSTR_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `SPEC_ID_FK2`
    FOREIGN KEY (`SPEC_ID`)
    REFERENCES `scheddb`.`SPECIALTIES` (`SPEC_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ROOM_ID_FK`
    FOREIGN KEY (`ROOM_ID`)
    REFERENCES `scheddb`.`ROOMS` (`ROOM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`ENROLLMENTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`ENROLLMENTS` (
  `CLIENT_ID` BIGINT NOT NULL,
  `ROOM_ID` BIGINT NOT NULL,
  `DAY_ID` BIGINT NOT NULL,
  `STARTING_TIME` TIME NOT NULL,
  `ENDING_TIME` TIME NOT NULL,
  PRIMARY KEY (`ROOM_ID`, `DAY_ID`, `STARTING_TIME`, `ENDING_TIME`, `CLIENT_ID`),
  INDEX `CLIENT_ID_IDX` (`CLIENT_ID` ASC) VISIBLE,
  INDEX `ROOM_DAY_TIME_IDX` (`ROOM_ID` ASC, `DAY_ID` ASC, `STARTING_TIME` ASC, `ENDING_TIME` ASC) INVISIBLE,
  CONSTRAINT `CLIENT_ID_FK`
    FOREIGN KEY (`CLIENT_ID`)
    REFERENCES `scheddb`.`CLIENTS` (`CLIENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ROOM_DAY_TIME_FK`
    FOREIGN KEY (`ROOM_ID` , `DAY_ID` , `STARTING_TIME` , `ENDING_TIME`)
    REFERENCES `scheddb`.`CLASSES` (`ROOM_ID` , `DAY_ID` , `STARTING_TIME` , `ENDING_TIME`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`USERS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`USERS` (
  `USER_ID` BIGINT NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR(45) NULL,
  `PASSWORD` VARCHAR(256) NULL,
  PRIMARY KEY (`USER_ID`),
  INDEX `USERNAME_IDX` (`USERNAME` ASC) VISIBLE,
  UNIQUE INDEX `USERNAME_UNIQUE` (`USERNAME` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `scheddb`.`USERS_CLIENTS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `scheddb`.`USERS_CLIENTS` (
  `USER_ID` BIGINT NOT NULL,
  `CLIENT_ID` BIGINT NOT NULL,
  PRIMARY KEY (`USER_ID`, `CLIENT_ID`),
  INDEX `CLIENT_ID_FK_idx` (`CLIENT_ID` ASC) VISIBLE,
  UNIQUE INDEX `USER_ID_UNIQUE` (`USER_ID` ASC) VISIBLE,
  UNIQUE INDEX `CLIENT_ID_UNIQUE` (`CLIENT_ID` ASC) VISIBLE,
  CONSTRAINT `USER_ID_FK`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `scheddb`.`USERS` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CLIENT_ID_FK2`
    FOREIGN KEY (`CLIENT_ID`)
    REFERENCES `scheddb`.`CLIENTS` (`CLIENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE USER 'scheddbuser' IDENTIFIED BY '1234';

GRANT ALL ON `scheddb`.* TO 'scheddbuser';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


DELIMITER $$

CREATE TRIGGER `restrict_client_classes_limit` 
BEFORE INSERT ON `ENROLLMENTS`
FOR EACH ROW
BEGIN
    DECLARE client_classes_count INT;

    SELECT COUNT(*) INTO client_classes_count
    FROM ENROLLMENTS
    WHERE CLIENT_ID = NEW.CLIENT_ID;

    IF client_classes_count >= 4 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Client has reached the maximum enrollment limit.';
    END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER `restrict_class_clients_limit` 
BEFORE INSERT ON `ENROLLMENTS`
FOR EACH ROW
BEGIN
    DECLARE class_clients_count INT;

    SELECT COUNT(*) INTO class_clients_count
    FROM ENROLLMENTS
    WHERE ROOM_ID = NEW.ROOM_ID
        AND DAY_ID = NEW.DAY_ID
        AND STARTING_TIME = NEW.STARTING_TIME
        AND ENDING_TIME = NEW.ENDING_TIME;

    IF class_clients_count >= 6 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Class has reached the maximum client limit.';
    END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER `restrict_schedule_overlap_and_specialty`
BEFORE INSERT ON `CLASSES`
FOR EACH ROW
BEGIN
    DECLARE count_overlap_room INT;
    DECLARE count_overlap_instructor INT;
    DECLARE instructor_spec INT;

    SELECT COUNT(*) INTO count_overlap_room
    FROM CLASSES
    WHERE ROOM_ID = NEW.ROOM_ID
        AND DAY_ID = NEW.DAY_ID
        AND (
            (NEW.STARTING_TIME BETWEEN STARTING_TIME AND ADDTIME(ENDING_TIME, '-00:01:00'))  
            OR (NEW.ENDING_TIME BETWEEN SUBTIME(STARTING_TIME, '-00:01:00') AND ENDING_TIME)  
            OR (NEW.STARTING_TIME <= STARTING_TIME AND NEW.ENDING_TIME >= ENDING_TIME)
        );

    IF count_overlap_room > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Schedule overlap detected for the room.';
    END IF;

    SELECT COUNT(*) INTO count_overlap_instructor
    FROM CLASSES
    WHERE INSTR_ID = NEW.INSTR_ID
        AND DAY_ID = NEW.DAY_ID
        AND (
            (NEW.STARTING_TIME BETWEEN STARTING_TIME AND ADDTIME(ENDING_TIME, '-00:01:00'))  
            OR (NEW.ENDING_TIME BETWEEN SUBTIME(STARTING_TIME, '-00:01:00') AND ENDING_TIME)  
            OR (NEW.STARTING_TIME <= STARTING_TIME AND NEW.ENDING_TIME >= ENDING_TIME)
        );

    IF count_overlap_instructor > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Instructor is already assigned to another room at that time.';
    END IF;

    IF NOT EXISTS (
    SELECT 1
    FROM SPECIALTIES_INSTRUCTORS
    WHERE INSTR_ID = NEW.INSTR_ID AND SPEC_ID = NEW.SPEC_ID
	) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Instructor does not have the correct specialty for this class.';
	END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER `restrict_schedule_overlap_and_specialty_on_update` 
BEFORE UPDATE ON `CLASSES`
FOR EACH ROW
BEGIN
    DECLARE count_overlap_room INT;
    DECLARE count_overlap_instructor INT;
    DECLARE instructor_spec INT;

    SELECT COUNT(*) INTO count_overlap_room
    FROM CLASSES
    WHERE ROOM_ID = NEW.ROOM_ID
        AND DAY_ID = NEW.DAY_ID
        AND (
            (NEW.STARTING_TIME BETWEEN STARTING_TIME AND ADDTIME(ENDING_TIME, '-00:01:00'))  
            OR (NEW.ENDING_TIME BETWEEN SUBTIME(STARTING_TIME, '-00:01:00') AND ENDING_TIME)  
            OR (NEW.STARTING_TIME <= STARTING_TIME AND NEW.ENDING_TIME >= ENDING_TIME)
        );

    IF count_overlap_room > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Schedule overlap detected for the room.';
    END IF;

    SELECT COUNT(*) INTO count_overlap_instructor
    FROM CLASSES
    WHERE INSTR_ID = NEW.INSTR_ID
        AND DAY_ID = NEW.DAY_ID
        AND (
            (NEW.STARTING_TIME BETWEEN STARTING_TIME AND ADDTIME(ENDING_TIME, '-00:01:00'))  
            OR (NEW.ENDING_TIME BETWEEN SUBTIME(STARTING_TIME, '-00:01:00') AND ENDING_TIME)  
            OR (NEW.STARTING_TIME <= STARTING_TIME AND NEW.ENDING_TIME >= ENDING_TIME)
        );

    IF count_overlap_instructor > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Instructor is already assigned to another room at that time.';
    END IF;

    IF NOT EXISTS (
    SELECT 1
    FROM SPECIALTIES_INSTRUCTORS
    WHERE INSTR_ID = NEW.INSTR_ID AND SPEC_ID = NEW.SPEC_ID
	) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Instructor does not have the correct specialty for this class.';
	END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER `restrict_client_enrollment` 
BEFORE INSERT ON `ENROLLMENTS`
FOR EACH ROW
BEGIN
    DECLARE count_overlap_client INT;

    SELECT COUNT(*) INTO count_overlap_client
    FROM ENROLLMENTS
    WHERE CLIENT_ID = NEW.CLIENT_ID
        AND DAY_ID = NEW.DAY_ID
        AND (
            (NEW.STARTING_TIME BETWEEN STARTING_TIME AND ADDTIME(ENDING_TIME, '-00:01:00'))  
            OR (NEW.ENDING_TIME BETWEEN SUBTIME(STARTING_TIME, '-00:01:00') AND ENDING_TIME)  
            OR (NEW.STARTING_TIME <= STARTING_TIME AND NEW.ENDING_TIME >= ENDING_TIME)
        );

    IF count_overlap_client > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Client is already enrolled in another class at that time on the same day.';
    END IF;
END$$

DELIMITER ;


INSERT INTO `scheddb`.`CLIENTS` (`FIRSTNAME`, `LASTNAME`, `GENDER`, `BIRTHDAY`) VALUES
('Alice', 'Johnson', 'F', '1990-05-15'),
('John', 'Smith', 'M', '1985-09-20'),
('Emily', 'Davis', 'F', '1998-12-10'),
('Michael', 'Brown', 'M', '1982-03-25'),
('Sophia', 'Martinez', 'F', '1976-07-18'),
('Jacob', 'Jones', 'M', '1995-11-30'),
('Olivia', 'Miller', 'F', '1989-02-14'),
('William', 'Wilson', 'M', '1979-06-02'),
('Ava', 'Taylor', 'F', '1992-08-05'),
('Ethan', 'Anderson', 'M', '1987-04-09'),
('Mia', 'Thomas', 'F', '1974-10-12'),
('Alexander', 'Jackson', 'M', '1980-01-28'),
('Charlotte', 'White', 'F', '1993-03-22'),
('James', 'Harris', 'M', '1978-09-03'),
('Amelia', 'Martin', 'F', '1996-06-07'),
('Benjamin', 'Thompson', 'M', '1983-12-19'),
('Harper', 'Garcia', 'F', '1977-04-25'),
('Daniel', 'Martinez', 'M', '1991-07-08'),
('Evelyn', 'Davis', 'F', '1986-11-01'),
('Oliver', 'Rodriguez', 'M', '1975-01-14'),
('Isabella', 'Hernandez', 'F', '1994-08-22'),
('Sebastian', 'Nguyen', 'M', '1981-02-11'),
('Grace', 'Perez', 'F', '1973-05-28'),
('Lucas', 'Torres', 'M', '1984-10-03'),
('Aria', 'Gonzales', 'F', '1997-02-17'),
('Henry', 'Stewart', 'M', '1970-06-20'),
('Lily', 'Sanchez', 'F', '1988-09-09'),
('Alexander', 'Rivera', 'M', '1999-03-13'),
('Scarlett', 'Moore', 'F', '1972-07-24'),
('Luke', 'Lee', 'M', '1989-11-27'),
('Zoey', 'Scott', 'F', '1994-04-01'),
('Andrew', 'Green', 'M', '1983-08-08'),
('Penelope', 'Adams', 'F', '1976-12-30'),
('Logan', 'Baker', 'M', '1990-02-03'),
('Mila', 'Campbell', 'F', '1987-05-16'),
('Gabriel', 'Evans', 'M', '1974-09-21'),
('Aurora', 'Collins', 'F', '1991-01-25'),
('Joshua', 'Murphy', 'M', '1979-03-11'),
('Hannah', 'Cook', 'F', '1995-06-14');

INSERT INTO DAYS (DAY_ID, DAY) VALUES
(1, 'Monday'),
(2, 'Tuesday'),
(3, 'Wednesday'),
(4, 'Thursday'),
(5, 'Friday'),
(6, 'Saturday'),
(7, 'Sunday');

INSERT INTO INSTRUCTORS (FIRSTNAME, LASTNAME, SSN) VALUES
('John', 'Doe', 123456789),
('Jane', 'Smith', 987654321),
('Alice', 'Johnson', 111222333),
('Michael', 'Brown', 444555666),
('Emily', 'Davis', 777888999),
('Sophia', 'Garcia', 112233445),
('David', 'Lee', 998877665),
('Emma', 'Wilson', 554433221),
('Oliver', 'Martinez', 123321456),
('Grace', 'Nguyen', 789123654);

INSERT INTO ROOMS (ROOM_ID) VALUES
(1),
(2),
(3),
(4);

INSERT INTO SPECIALTIES (NAME) VALUES
('Yoga'),
('Zumba'),
('Pilates'),
('Boxing'),
('Dance'),
('Aerobics'),
('Martial Arts'),
('Spinning'),
('Barre'),
('CrossFit');

INSERT INTO SPECIALTIES_INSTRUCTORS (INSTR_ID, SPEC_ID) VALUES
(2, 1),
(3, 2),
(6, 3),
(4, 4),
(5, 5),
(8, 6),
(1, 7),
(7, 8),
(10, 9),
(9, 10);

INSERT INTO CLASSES (ROOM_ID, DAY_ID, STARTING_TIME, ENDING_TIME, INSTR_ID, SPEC_ID) VALUES
(1, 1, '10:00:00', '12:00:00', 8, 6),
(1, 1, '13:00:00', '15:00:00', 8, 6),
(1, 3, '10:00:00', '12:00:00', 8, 6),
(1, 3, '13:00:00', '15:00:00', 8, 6),
(1, 5, '10:00:00', '12:00:00', 8, 6),
(1, 5, '13:00:00', '15:00:00', 8, 6),
(1, 1, '15:00:00', '17:00:00', 1, 7),
(1, 1, '17:00:00', '19:00:00', 1, 7),
(1, 2, '17:00:00', '19:00:00', 1, 7),
(1, 2, '19:00:00', '21:00:00', 1, 7),
(1, 3, '15:00:00', '17:00:00', 1, 7),
(1, 3, '17:00:00', '19:00:00', 1, 7),
(1, 4, '17:00:00', '19:00:00', 1, 7),
(1, 4, '19:00:00', '21:00:00', 1, 7),
(1, 5, '15:00:00', '17:00:00', 1, 7),
(1, 5, '17:00:00', '19:00:00', 1, 7),
(1, 4, '10:00:00', '12:00:00', 10, 9),
(2, 1, '10:00:00', '12:00:00', 2, 1),
(2, 1, '13:00:00', '15:00:00', 2, 1),
(2, 3, '10:00:00', '12:00:00', 2, 1),
(2, 3, '13:00:00', '15:00:00', 2, 1),
(2, 1, '17:00:00', '19:00:00', 5, 5),
(2, 1, '19:00:00', '21:00:00', 5, 5),
(2, 4, '10:00:00', '12:00:00', 5, 5),
(2, 5, '17:00:00', '19:00:00', 5, 5),
(2, 5, '19:00:00', '21:00:00', 5, 5),
(2, 2, '10:00:00', '12:00:00', 3, 2),
(2, 3, '17:00:00', '19:00:00', 3, 2),
(2, 3, '19:00:00', '21:00:00', 3, 2),
(2, 4, '17:00:00', '19:00:00', 3, 2),
(2, 4, '19:00:00', '21:00:00', 3, 2),
(2, 5, '10:00:00', '12:00:00', 3, 2),
(2, 5, '13:00:00', '15:00:00', 3, 2),
(2, 2, '13:00:00', '15:00:00', 8, 6),
(2, 2, '15:00:00', '17:00:00', 8, 6),
(2, 4, '13:00:00', '15:00:00', 8, 6),
(2, 4, '15:00:00', '17:00:00', 8, 6),
(3, 1, '10:00:00', '12:00:00', 5, 5),
(3, 2, '15:00:00', '17:00:00', 5, 5),
(3, 4, '15:00:00', '17:00:00', 5, 5),
(3, 1, '13:00:00', '15:00:00', 10, 9),
(3, 2, '13:00:00', '15:00:00', 10, 9),
(3, 3, '10:00:00', '12:00:00', 10, 9),
(3, 3, '13:00:00', '15:00:00', 10, 9),
(3, 4, '13:00:00', '15:00:00', 10, 9),
(3, 1, '15:00:00', '17:00:00', 4, 4),
(3, 1, '17:00:00', '19:00:00', 4, 4),
(3, 2, '17:00:00', '19:00:00', 4, 4),
(3, 2, '19:00:00', '21:00:00', 4, 4),
(3, 3, '15:00:00', '17:00:00', 4, 4),
(3, 3, '17:00:00', '19:00:00', 4, 4),
(3, 5, '15:00:00', '17:00:00', 4, 4),
(3, 5, '17:00:00', '19:00:00', 4, 4),
(3, 1, '19:00:00', '21:00:00', 6, 3),
(3, 3, '19:00:00', '21:00:00', 6, 3),
(3, 4, '10:00:00', '12:00:00', 6, 3),
(3, 5, '19:00:00', '21:00:00', 6, 3),
(3, 4, '17:00:00', '19:00:00', 2, 1),
(3, 4, '19:00:00', '21:00:00', 2, 1),
(3, 5, '13:00:00', '15:00:00', 2, 1),
(4, 1, '10:00:00', '12:00:00', 9, 10),
(4, 2, '13:00:00', '15:00:00', 9, 10),
(4, 2, '15:00:00', '17:00:00', 9, 10),
(4, 3, '13:00:00', '15:00:00', 9, 10),
(4, 3, '15:00:00', '17:00:00', 9, 10),
(4, 5, '17:00:00', '19:00:00', 9, 10),
(4, 1, '13:00:00', '15:00:00', 7, 8),
(4, 1, '15:00:00', '17:00:00', 7, 8),
(4, 2, '10:00:00', '12:00:00', 7, 8),
(4, 3, '10:00:00', '12:00:00', 7, 8),
(4, 4, '13:00:00', '15:00:00', 7, 8),
(4, 4, '15:00:00', '17:00:00', 7, 8),
(4, 5, '10:00:00', '12:00:00', 7, 8),
(4, 5, '13:00:00', '15:00:00', 7, 8),
(4, 5, '15:00:00', '17:00:00', 7, 8),
(4, 1, '17:00:00', '19:00:00', 6, 3),
(4, 4, '17:00:00', '19:00:00', 4, 4),
(4, 4, '19:00:00', '21:00:00', 4, 4);


INSERT INTO USERS (USERNAME, PASSWORD) VALUES
('admin', '$2a$12$OYZpyNhWvO30t7kz9H5nf.nkZ0HBYF/jvx/aKNu/DYibqniGTpS/i'),
('mike82', '$2a$12$BZRHsVWNKLh0H23y1EB/VeE7ROVwagKV4SxRTYy6pKFCniQg/OFFm');

-- admin password is 'admin'
-- mike82 password is '82'
-- user account MUST be assigned to a client (by admin) before use:

INSERT INTO USERS_CLIENTS (USER_ID, CLIENT_ID) VALUES
(2,4);