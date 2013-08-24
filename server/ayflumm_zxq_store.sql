SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `default_schema` ;
USE `default_schema` ;

-- -----------------------------------------------------
-- Table `default_schema`.`authors`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`authors` (
  `AID` BIGINT(20) UNSIGNED NOT NULL ,
  `Name` VARCHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `DOB` DATE NOT NULL ,
  `DOD` DATE NOT NULL ,
  PRIMARY KEY (`AID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `default_schema`.`authorconames`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`authorconames` (
  `CoNameID` BIGINT(20) NOT NULL ,
  `AuthorID` BIGINT(20) UNSIGNED NOT NULL ,
  `CoName` CHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,
  INDEX `AuthorID` (`AuthorID` ASC) ,
  INDEX `CoNameID` (`CoNameID` ASC) ,
  CONSTRAINT `authorconames_ibfk_1`
    FOREIGN KEY (`AuthorID` )
    REFERENCES `default_schema`.`authors` (`AID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf32
COLLATE = utf32_unicode_ci;


-- -----------------------------------------------------
-- Table `default_schema`.`bookcollections`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`bookcollections` (
  `BCID` BIGINT(20) UNSIGNED NOT NULL ,
  `CreatorID` BIGINT(20) UNSIGNED NOT NULL ,
  `CollectionName` TINYTEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `BooksNo` INT(10) UNSIGNED NOT NULL DEFAULT '1' ,
  `Description` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  UNIQUE INDEX `BCID` (`BCID` ASC) ,
  INDEX `CreatorID` (`CreatorID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`bookcollector`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`bookcollector` (
  `BID` BIGINT(20) UNSIGNED NOT NULL ,
  `BCID` BIGINT(20) UNSIGNED NOT NULL ,
  `Sequence` INT(10) UNSIGNED NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  INDEX `BID` (`BID` ASC, `BCID` ASC, `Sequence` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`booklendingentry`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`booklendingentry` (
  `BorrowerID` BIGINT(20) UNSIGNED NOT NULL ,
  `BookID` BIGINT(20) UNSIGNED NOT NULL ,
  `LentDateTime` DATETIME NOT NULL ,
  `ReturnedTime` DATETIME NULL DEFAULT NULL ,
  INDEX `BorrowerID` (`BorrowerID` ASC) ,
  INDEX `BookID` (`BookID` ASC, `LentDateTime` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`bookquery`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`bookquery` (
  `SubscriberID` BIGINT(20) UNSIGNED NOT NULL ,
  `BID` BIGINT(20) UNSIGNED NOT NULL ,
  `QueryDateTime` DATETIME NOT NULL ,
  `InformedDateTime` DATETIME NULL DEFAULT NULL ,
  INDEX `QueryDateTime` (`QueryDateTime` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`photos`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`photos` (
  `OID` BIGINT(20) NOT NULL ,
  `URL` TEXT NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  PRIMARY KEY (`OID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`books`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`books` (
  `OID` BIGINT(20) UNSIGNED NOT NULL ,
  `Name` CHAR(75) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `AuthorID` BIGINT(20) UNSIGNED NULL DEFAULT NULL COMMENT 'Actually, authorCoNameID' ,
  `PublicationID` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
  `Price` INT(10) UNSIGNED NULL DEFAULT NULL ,
  `Description` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL ,
  `PhotoID` BIGINT(20) NULL DEFAULT NULL ,
  `TypeID` BIGINT(20) UNSIGNED NULL DEFAULT NULL ,
  `CreatedTime` DATETIME NOT NULL ,
  PRIMARY KEY (`OID`) ,
  UNIQUE INDEX `OID` (`OID` ASC) ,
  INDEX `BID` (`Name` ASC, `AuthorID` ASC, `PublicationID` ASC) ,
  INDEX `AuthorID` (`AuthorID` ASC) ,
  INDEX `PhotoID` (`PhotoID` ASC) ,
  CONSTRAINT `books_ibfk_1`
    FOREIGN KEY (`AuthorID` )
    REFERENCES `default_schema`.`authors` (`AID` )
    ON UPDATE CASCADE,
  CONSTRAINT `books_ibfk_2`
    FOREIGN KEY (`PhotoID` )
    REFERENCES `default_schema`.`photos` (`OID` )
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`bookscollected`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`bookscollected` (
  `BID` BIGINT(20) UNSIGNED NOT NULL ,
  `BCID` BIGINT(20) UNSIGNED NOT NULL ,
  `SequenceNo` INT(10) UNSIGNED NOT NULL ,
  `CreatedTime` DATETIME NOT NULL ,
  INDEX `BID` (`BID` ASC, `BCID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`booksonshelf`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`booksonshelf` (
  `OID` BIGINT(20) UNSIGNED NOT NULL ,
  `BID` BIGINT(20) UNSIGNED NOT NULL ,
  `DonorID` BIGINT(20) UNSIGNED NOT NULL ,
  `ArrivedDate` DATE NOT NULL ,
  `Status` INT(11) NOT NULL DEFAULT '0' ,
  `CopiesNo` INT(11) NOT NULL ,
  PRIMARY KEY (`OID`) ,
  INDEX `DonorID` (`DonorID` ASC) ,
  CONSTRAINT `booksonshelf_ibfk_1`
    FOREIGN KEY (`OID` )
    REFERENCES `default_schema`.`books` (`OID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`booksuggestions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`booksuggestions` (
  `BSID` BIGINT(20) UNSIGNED NOT NULL ,
  `BID` BIGINT(20) UNSIGNED NOT NULL ,
  `MID` BIGINT(20) UNSIGNED NOT NULL ,
  `AskedDate` DATETIME NOT NULL ,
  `FullfilledDate` DATE NOT NULL ,
  `AskedText` MEDIUMTEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  PRIMARY KEY (`BSID`) ,
  INDEX `BID` (`BID` ASC) ,
  INDEX `BID_2` (`BID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
ROW_FORMAT = COMPACT;


-- -----------------------------------------------------
-- Table `default_schema`.`bulletinposts`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`bulletinposts` (
  `PostID` BIGINT(20) UNSIGNED NOT NULL ,
  `OID` BIGINT(20) UNSIGNED NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  `AttachmentID` BIGINT(20) UNSIGNED NOT NULL ,
  `ContentID` INT(11) NOT NULL )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`comments`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`comments` (
  `CommentID` BIGINT(20) UNSIGNED NOT NULL ,
  `SubjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `ObjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  `Content` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  PRIMARY KEY (`CommentID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`docs`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`docs` (
  `OID` BIGINT(20) NOT NULL ,
  `URL` BIGINT(20) NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  PRIMARY KEY (`OID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`members`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`members` (
  `OID` BIGINT(20) UNSIGNED NOT NULL ,
  `MID` INT(10) UNSIGNED NOT NULL ,
  `PhotoID` BIGINT(20) UNSIGNED NOT NULL ,
  `DOB` DATE NOT NULL ,
  `FriendsNo` INT(10) UNSIGNED NOT NULL DEFAULT '0' ,
  `Address` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `Phone` INT(11) NOT NULL ,
  `Score` INT(11) NOT NULL ,
  `ShortAbout` MEDIUMTEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  PRIMARY KEY (`OID`, `MID`) ,
  INDEX `MID` (`MID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`donors`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`donors` (
  `DID` BIGINT(20) UNSIGNED NOT NULL ,
  `Name` VARCHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `Organization` INT(11) NULL DEFAULT NULL ,
  `MemberID` BIGINT(20) UNSIGNED NOT NULL DEFAULT NULL ,
  `FirstDealingDate` DATE NOT NULL ,
  PRIMARY KEY (`DID`, `MemberID`) ,
  INDEX `MemberID` (`MemberID` ASC) ,
  CONSTRAINT `MemberID`
    FOREIGN KEY (`MemberID` )
    REFERENCES `default_schema`.`members` (`OID` )
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`friends`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`friends` (
  `SubjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `ObjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `DealingDate` DATE NOT NULL ,
  INDEX `SubjectID` (`SubjectID` ASC) ,
  INDEX `ObjectID` (`ObjectID` ASC) ,
  INDEX `SubjectMemberID` (`SubjectID` ASC) ,
  INDEX `ObjectMemberID` (`ObjectID` ASC) ,
  CONSTRAINT `SubjectMemberID`
    FOREIGN KEY (`SubjectID` )
    REFERENCES `default_schema`.`members` (`OID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ObjectMemberID`
    FOREIGN KEY (`ObjectID` )
    REFERENCES `default_schema`.`members` (`OID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`likes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`likes` (
  `SubjectID` BIGINT(20) NOT NULL ,
  `ObjectID` BIGINT(20) NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  INDEX `SubjectID` (`SubjectID` ASC, `ObjectID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`mailbox`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`mailbox` (
  `OID` BIGINT(20) UNSIGNED NOT NULL ,
  `From` BIGINT(20) UNSIGNED NOT NULL ,
  `To` BIGINT(20) UNSIGNED NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  `ContentID` BIGINT(20) UNSIGNED NOT NULL ,
  `AttachmentID` BIGINT(20) UNSIGNED NOT NULL ,
  PRIMARY KEY (`OID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`multivoteactions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`multivoteactions` (
  `SubjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `ObjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `Value` INT(11) NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  INDEX `SubjectID` (`SubjectID` ASC, `ObjectID` ASC, `Value` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`multivoteproperty`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`multivoteproperty` (
  `MultiVoteID` BIGINT(20) UNSIGNED NOT NULL ,
  `CreatorID` BIGINT(20) UNSIGNED NOT NULL ,
  `ObjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  `MaxChoosableNo` INT(11) NOT NULL ,
  PRIMARY KEY (`MultiVoteID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`multivotevalues`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`multivotevalues` (
  `MultiVoteID` BIGINT(20) UNSIGNED NOT NULL ,
  `Value` INT(11) NOT NULL ,
  `String` TINYTEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  INDEX `MultiVoteID` (`MultiVoteID` ASC) ,
  INDEX `Value` (`Value` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`photoalbumn`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`photoalbumn` (
  `OID` BIGINT(20) UNSIGNED NOT NULL ,
  `CoverPhotoID` BIGINT(20) UNSIGNED NOT NULL ,
  `TotalPhotoNo` INT(11) NOT NULL DEFAULT '1' ,
  `DateTime` DATETIME NOT NULL ,
  PRIMARY KEY (`OID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`photoalbumnlist`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`photoalbumnlist` (
  `AlbumnID` BIGINT(20) NOT NULL ,
  `PhotoID` BIGINT(20) NOT NULL ,
  `SequenceNo` INT(11) NOT NULL )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`ranks`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`ranks` (
  `RankID` INT(20) UNSIGNED NOT NULL ,
  `RankName` CHAR(20) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `Description` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  PRIMARY KEY (`RankID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`rates`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`rates` (
  `SubjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `ObjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  `Actual` INT(11) NOT NULL ,
  `Given` INT(11) NOT NULL ,
  INDEX `SubjectID` (`SubjectID` ASC, `ObjectID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`reviews`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`reviews` (
  `SubjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `ObjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  `Content` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  INDEX `SubjectID` (`SubjectID` ASC, `ObjectID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`users` (
  `OID` BIGINT(20) UNSIGNED NOT NULL ,
  `MID` INT(10) UNSIGNED NOT NULL ,
  `Username` CHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL ,
  `Password` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL ,
  `Rank` SET('M','N','V') NOT NULL DEFAULT 'N' ,
  `AccountStatus` ENUM('AllowedToLogin') CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL DEFAULT 'AllowedToLogin' ,
  `Name` CHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `JoinedDate` DATE NULL ,
  `Department` VARCHAR(30) NULL ,
  `Batch` VARCHAR(45) NULL ,
  PRIMARY KEY (`OID`, `MID`) ,
  INDEX `MemberID` (`MID` ASC) ,
  CONSTRAINT `MemberID`
    FOREIGN KEY (`MID` )
    REFERENCES `default_schema`.`members` (`MID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`videos`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`videos` (
  `ID` BIGINT(20) NOT NULL ,
  `URL` MEDIUMINT(9) NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`votes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`votes` (
  `SubjectID` INT(20) UNSIGNED NOT NULL ,
  `ObjectID` BIGINT(20) UNSIGNED NOT NULL ,
  `Vote` TINYINT(1) NOT NULL ,
  `DateTime` DATETIME NOT NULL ,
  INDEX `SubjectID` (`SubjectID` ASC, `ObjectID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`_columns`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_columns` (
  `CID` INT(10) NOT NULL ,
  `Table` CHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `Column` CHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  PRIMARY KEY (`CID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`_dal`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_dal` (
  `DALID` TINYINT(3) UNSIGNED NOT NULL ,
  `Readable` TINYINT(1) NOT NULL DEFAULT '0' ,
  `Clonable` TINYINT(1) NOT NULL DEFAULT '0' ,
  `Updatable` TINYINT(1) NOT NULL DEFAULT '0' ,
  `Deletable` TINYINT(1) NOT NULL DEFAULT '0' ,
  `Writable` TINYINT(1) NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`DALID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `default_schema`.`_dap`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_dap` (
  `RankID` INT(11) NOT NULL ,
  `ColumnID` BIGINT(20) UNSIGNED NOT NULL ,
  `DAL` TINYINT(3) UNSIGNED NOT NULL ,
  INDEX `RankID` (`RankID` ASC, `ColumnID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `default_schema`.`_objecttypelocations`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_objecttypelocations` (
  `TID` BIGINT(5) UNSIGNED NOT NULL ,
  `Table` CHAR(25) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `Column` CHAR(25) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  INDEX `OID` (`TID` ASC, `Table` ASC, `Column` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`_objecttypes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_objecttypes` (
  `TID` SMALLINT(5) UNSIGNED NOT NULL ,
  `Name` CHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `Description` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL ,
  `FirstLimit` BIGINT(20) UNSIGNED NOT NULL ,
  `LastLimit` BIGINT(20) UNSIGNED NOT NULL ,
  PRIMARY KEY (`TID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`_posthandler`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_posthandler` (
  `ID` BIGINT(20) UNSIGNED NOT NULL ,
  `DataHandler` TEXT NULL ,
  `QueryString` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,
  `ReturnString` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,
  `Comment` TEXT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `default_schema`.`_requesthandler`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_requesthandler` (
  `id` BIGINT(20) UNSIGNED NOT NULL ,
  `QueryString` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,
  `ReturnString` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,
  `Comment` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `default_schema`.`_socialfeaturesdeterminer`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_socialfeaturesdeterminer` (
  `TypeID` INT(10) UNSIGNED NOT NULL ,
  `ColumnID` BIGINT(20) UNSIGNED NOT NULL ,
  `Likable` TINYINT(1) NOT NULL ,
  `Votable` TINYINT(1) NOT NULL ,
  `MultiVotable` TINYINT(1) NOT NULL ,
  `Commentable` TINYINT(1) NOT NULL ,
  `Reviewable` TINYINT(1) NOT NULL ,
  INDEX `TypeID` (`TypeID` ASC) ,
  INDEX `Column` (`ColumnID` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`_socialfeaturesno`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`_socialfeaturesno` (
  `OID` BIGINT(20) UNSIGNED NOT NULL ,
  `Likable` TINYINT(1) NOT NULL DEFAULT '0' ,
  `Votable` TINYINT(1) NOT NULL DEFAULT '0' ,
  `MultiVotable` TINYINT(1) NOT NULL DEFAULT '0' ,
  `Commentable` TINYINT(1) NOT NULL DEFAULT '0' ,
  `Reviewable` TINYINT(1) NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`OID`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `default_schema`.`publications`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `default_schema`.`publications` (
  `PublicationID` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`PublicationID`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
