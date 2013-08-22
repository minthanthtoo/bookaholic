-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 21, 2013 at 06:01 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ayfl_store`
--

-- --------------------------------------------------------

--
-- Table structure for table `authorconames`
--

CREATE TABLE IF NOT EXISTS `authorconames` (
  `CoNameID` bigint(20) NOT NULL,
  `AuthorID` bigint(20) unsigned NOT NULL,
  `CoName` char(30) COLLATE utf8_bin NOT NULL,
  KEY `AuthorID` (`AuthorID`),
  KEY `CoNameID` (`CoNameID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `authorconames`
--

INSERT INTO `authorconames` (`CoNameID`, `AuthorID`, `CoName`) VALUES
(1, 2, 'ဘလာ'),
(2, 2, 'သ'),
(1, 2, 'ဘလာ'),
(2, 2, 'သ'),
(1, 2, 'ဘလာ'),
(2, 2, 'သ');

-- --------------------------------------------------------

--
-- Table structure for table `authors`
--

CREATE TABLE IF NOT EXISTS `authors` (
  `AID` bigint(20) unsigned NOT NULL,
  `Name` varchar(30) COLLATE utf8_bin NOT NULL,
  `DOB` date NOT NULL,
  `DOD` date NOT NULL,
  PRIMARY KEY (`AID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `authors`
--

INSERT INTO `authors` (`AID`, `Name`, `DOB`, `DOD`) VALUES
(1, 'မင္းသန္႔ထူး', '2012-07-01', '2012-07-01'),
(2, 'ေအာင္မ်ိဳးျမတ္', '2012-07-02', '2012-07-02');

-- --------------------------------------------------------

--
-- Table structure for table `bookcollections`
--

CREATE TABLE IF NOT EXISTS `bookcollections` (
  `BCID` bigint(20) unsigned NOT NULL,
  `CreatorID` bigint(20) unsigned NOT NULL,
  `CollectionName` tinytext COLLATE utf8_bin NOT NULL,
  `BooksNo` int(10) unsigned NOT NULL DEFAULT '1',
  `Description` text COLLATE utf8_bin NOT NULL,
  UNIQUE KEY `BCID` (`BCID`),
  KEY `CreatorID` (`CreatorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `bookcollector`
--

CREATE TABLE IF NOT EXISTS `bookcollector` (
  `BID` bigint(20) unsigned NOT NULL,
  `BCID` bigint(20) unsigned NOT NULL,
  `Sequence` int(10) unsigned NOT NULL,
  `DateTime` datetime NOT NULL,
  KEY `BID` (`BID`,`BCID`,`Sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `booklendingentry`
--

CREATE TABLE IF NOT EXISTS `booklendingentry` (
  `BorrowerID` bigint(20) unsigned NOT NULL,
  `BookID` bigint(20) unsigned NOT NULL,
  `LentDateTime` datetime NOT NULL,
  `ReturnedTime` datetime DEFAULT NULL,
  KEY `BorrowerID` (`BorrowerID`),
  KEY `BookID` (`BookID`,`LentDateTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `bookquery`
--

CREATE TABLE IF NOT EXISTS `bookquery` (
  `SubscriberID` bigint(20) unsigned NOT NULL,
  `BID` bigint(20) unsigned NOT NULL,
  `QueryDateTime` datetime NOT NULL,
  `InformedDateTime` datetime DEFAULT NULL,
  KEY `QueryDateTime` (`QueryDateTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `OID` bigint(20) unsigned NOT NULL,
  `Name` char(75) COLLATE utf8_bin NOT NULL,
  `AuthorID` bigint(20) unsigned DEFAULT NULL COMMENT 'Actually, authorCoNameID',
  `PublicationID` bigint(20) unsigned DEFAULT NULL,
  `Price` int(10) unsigned DEFAULT NULL,
  `Description` text COLLATE utf8_bin,
  `PhotoID` bigint(20) DEFAULT NULL,
  `TypeID` bigint(20) unsigned DEFAULT NULL,
  `CreatedTime` datetime NOT NULL,
  UNIQUE KEY `OID` (`OID`),
  KEY `BID` (`Name`,`AuthorID`,`PublicationID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `bookscollected`
--

CREATE TABLE IF NOT EXISTS `bookscollected` (
  `BID` bigint(20) unsigned NOT NULL,
  `BCID` bigint(20) unsigned NOT NULL,
  `SequenceNo` int(10) unsigned NOT NULL,
  `CreatedTime` datetime NOT NULL,
  KEY `BID` (`BID`,`BCID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `booksonshelf`
--

CREATE TABLE IF NOT EXISTS `booksonshelf` (
  `OID` bigint(20) unsigned NOT NULL,
  `BID` bigint(20) unsigned NOT NULL,
  `DonorID` bigint(20) unsigned NOT NULL,
  `ArrivedDate` date NOT NULL,
  `Status` int(11) NOT NULL DEFAULT '0',
  `CopiesNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `booksuggestions`
--

CREATE TABLE IF NOT EXISTS `booksuggestions` (
  `BSID` bigint(20) unsigned NOT NULL,
  `BID` bigint(20) unsigned NOT NULL,
  `MID` bigint(20) unsigned NOT NULL,
  `AskedDate` datetime NOT NULL,
  `FullfilledDate` date NOT NULL,
  `AskedText` mediumtext COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`BSID`),
  KEY `BID` (`BID`),
  KEY `BID_2` (`BID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `bulletinposts`
--

CREATE TABLE IF NOT EXISTS `bulletinposts` (
  `PostID` bigint(20) unsigned NOT NULL,
  `OID` bigint(20) unsigned NOT NULL,
  `DateTime` datetime NOT NULL,
  `AttachmentID` bigint(20) unsigned NOT NULL,
  `ContentID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `columns`
--

CREATE TABLE IF NOT EXISTS `columns` (
  `CID` bigint(20) NOT NULL,
  `Table` char(30) COLLATE utf8_bin NOT NULL,
  `Column` char(30) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `CommentID` bigint(20) unsigned NOT NULL,
  `SubjectID` bigint(20) unsigned NOT NULL,
  `ObjectID` bigint(20) unsigned NOT NULL,
  `DateTime` datetime NOT NULL,
  `Content` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CommentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `dal`
--

CREATE TABLE IF NOT EXISTS `dal` (
  `DALID` tinyint(3) unsigned NOT NULL,
  `Readable` tinyint(1) NOT NULL DEFAULT '0',
  `Clonable` tinyint(1) NOT NULL DEFAULT '0',
  `Updatable` tinyint(1) NOT NULL DEFAULT '0',
  `Deletable` tinyint(1) NOT NULL DEFAULT '0',
  `Writable` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`DALID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `dal`
--

INSERT INTO `dal` (`DALID`, `Readable`, `Clonable`, `Updatable`, `Deletable`, `Writable`) VALUES
(0, 0, 0, 0, 0, 0),
(1, 1, 0, 0, 0, 0),
(2, 1, 1, 0, 0, 0),
(3, 1, 1, 1, 0, 0),
(4, 1, 1, 1, 1, 0),
(5, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `dap`
--

CREATE TABLE IF NOT EXISTS `dap` (
  `RankID` int(11) NOT NULL,
  `ColumnID` bigint(20) unsigned NOT NULL,
  `DAL` tinyint(3) unsigned NOT NULL,
  KEY `RankID` (`RankID`,`ColumnID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `docs`
--

CREATE TABLE IF NOT EXISTS `docs` (
  `OID` bigint(20) NOT NULL,
  `URL` bigint(20) NOT NULL,
  `DateTime` datetime NOT NULL,
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `donors`
--

CREATE TABLE IF NOT EXISTS `donors` (
  `DID` bigint(20) unsigned NOT NULL,
  `Name` varchar(30) COLLATE utf8_bin NOT NULL,
  `Organization` int(11) DEFAULT NULL,
  `MemberID` bigint(20) unsigned DEFAULT NULL,
  `FirstDealingDate` date NOT NULL,
  PRIMARY KEY (`DID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE IF NOT EXISTS `friends` (
  `SubjectID` bigint(20) NOT NULL,
  `ObjectID` bigint(20) NOT NULL,
  `DealingDate` date NOT NULL,
  KEY `SubjectID` (`SubjectID`),
  KEY `ObjectID` (`ObjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE IF NOT EXISTS `likes` (
  `SubjectID` bigint(20) NOT NULL,
  `ObjectID` bigint(20) NOT NULL,
  `DateTime` datetime NOT NULL,
  KEY `SubjectID` (`SubjectID`,`ObjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `mailbox`
--

CREATE TABLE IF NOT EXISTS `mailbox` (
  `OID` bigint(20) unsigned NOT NULL,
  `From` bigint(20) unsigned NOT NULL,
  `To` bigint(20) unsigned NOT NULL,
  `DateTime` datetime NOT NULL,
  `ContentID` bigint(20) unsigned NOT NULL,
  `AttachmentID` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE IF NOT EXISTS `members` (
  `OID` bigint(20) unsigned NOT NULL,
  `MID` int(20) unsigned NOT NULL,
  `Name` char(30) COLLATE utf8_bin NOT NULL,
  `Status` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `PhotoID` bigint(20) unsigned NOT NULL,
  `DOB` date NOT NULL,
  `FriendsNo` int(10) unsigned NOT NULL DEFAULT '0',
  `JoinDate` date NOT NULL,
  `Department` text COLLATE utf8_bin NOT NULL,
  `Batch` text COLLATE utf8_bin NOT NULL,
  `Address` text COLLATE utf8_bin NOT NULL,
  `Phone` int(11) NOT NULL,
  `Score` int(11) NOT NULL,
  `ShortAbout` mediumtext COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `multivoteactions`
--

CREATE TABLE IF NOT EXISTS `multivoteactions` (
  `SubjectID` bigint(20) unsigned NOT NULL,
  `VoteItemID` bigint(20) NOT NULL,
  `DateTime` datetime NOT NULL,
  KEY `SubjectID` (`SubjectID`,`VoteItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `multivoteitems`
--

CREATE TABLE IF NOT EXISTS `multivoteitems` (
  `MultiVoteID` bigint(20) unsigned NOT NULL,
  `VoteItemID` bigint(20) unsigned NOT NULL,
  `String` varchar(40) COLLATE utf8_bin NOT NULL,
  `DateTime` datetime NOT NULL,
  KEY `MultiVoteID` (`MultiVoteID`),
  KEY `VoteItemID` (`VoteItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `multivoteproperty`
--

CREATE TABLE IF NOT EXISTS `multivoteproperty` (
  `MultiVoteID` bigint(20) unsigned NOT NULL,
  `CreatorID` bigint(20) unsigned NOT NULL,
  `ObjectID` bigint(20) unsigned NOT NULL,
  `DateTime` datetime NOT NULL,
  UNIQUE KEY `MultiVoteID` (`MultiVoteID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `nonmembers`
--

CREATE TABLE IF NOT EXISTS `nonmembers` (
  `OID` bigint(20) unsigned NOT NULL,
  `Name` varchar(30) COLLATE utf8_bin NOT NULL,
  `Department` varchar(30) COLLATE utf8_bin NOT NULL,
  `Batch` text COLLATE utf8_bin NOT NULL,
  `Status` tinyint(4) NOT NULL,
  `JoinedDate` date NOT NULL,
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `objectlocations`
--

CREATE TABLE IF NOT EXISTS `objectlocations` (
  `OID` bigint(20) unsigned NOT NULL,
  `Table` char(25) COLLATE utf8_bin NOT NULL,
  `Column` char(25) COLLATE utf8_bin NOT NULL,
  KEY `OID` (`OID`,`Table`,`Column`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `objects`
--

CREATE TABLE IF NOT EXISTS `objects` (
  `OType` varchar(20) COLLATE utf8_bin NOT NULL,
  `OID` int(10) unsigned NOT NULL,
  `FirstLimit` bigint(20) unsigned NOT NULL,
  `LastLimit` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`OID`),
  KEY `OType` (`OType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `objecttypes`
--

CREATE TABLE IF NOT EXISTS `objecttypes` (
  `TID` bigint(20) unsigned NOT NULL,
  `Name` char(30) COLLATE utf8_bin NOT NULL,
  `Description` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `photoalbumn`
--

CREATE TABLE IF NOT EXISTS `photoalbumn` (
  `OID` bigint(20) unsigned NOT NULL,
  `CoverPhotoID` bigint(20) unsigned NOT NULL,
  `TotalPhotoNo` int(11) NOT NULL DEFAULT '1',
  `DateTime` datetime NOT NULL,
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `photoalbumnlist`
--

CREATE TABLE IF NOT EXISTS `photoalbumnlist` (
  `AlbumnID` bigint(20) NOT NULL,
  `PhotoID` bigint(20) NOT NULL,
  `SequenceNo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `photos`
--

CREATE TABLE IF NOT EXISTS `photos` (
  `OID` bigint(20) NOT NULL,
  `URL` bigint(20) NOT NULL,
  `DateTime` datetime NOT NULL,
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `ranks`
--

CREATE TABLE IF NOT EXISTS `ranks` (
  `RankID` int(20) unsigned NOT NULL,
  `RankName` char(20) COLLATE utf8_bin NOT NULL,
  `Description` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`RankID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `rates`
--

CREATE TABLE IF NOT EXISTS `rates` (
  `SubjectID` bigint(20) unsigned NOT NULL,
  `ObjectID` bigint(20) unsigned NOT NULL,
  `DateTime` datetime NOT NULL,
  `Actual` int(11) NOT NULL,
  `Given` int(11) NOT NULL,
  KEY `SubjectID` (`SubjectID`,`ObjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE IF NOT EXISTS `reviews` (
  `SubjectID` bigint(20) unsigned NOT NULL,
  `ObjectID` bigint(20) unsigned NOT NULL,
  `DateTime` datetime NOT NULL,
  `Content` text COLLATE utf8_bin NOT NULL,
  KEY `SubjectID` (`SubjectID`,`ObjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `socialfeaturesdeterminer`
--

CREATE TABLE IF NOT EXISTS `socialfeaturesdeterminer` (
  `TypeID` int(10) unsigned NOT NULL,
  `ColumnID` bigint(20) unsigned NOT NULL,
  `Likable` tinyint(1) NOT NULL,
  `Votable` tinyint(1) NOT NULL,
  `MultiVotable` tinyint(1) NOT NULL,
  `Commentable` tinyint(1) NOT NULL,
  `Reviewable` tinyint(1) NOT NULL,
  KEY `TypeID` (`TypeID`),
  KEY `Column` (`ColumnID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `socialfeaturesno`
--

CREATE TABLE IF NOT EXISTS `socialfeaturesno` (
  `OID` bigint(20) unsigned NOT NULL,
  `Likable` tinyint(1) NOT NULL DEFAULT '0',
  `Votable` tinyint(1) NOT NULL DEFAULT '0',
  `MultiVotable` tinyint(1) NOT NULL DEFAULT '0',
  `Commentable` tinyint(1) NOT NULL DEFAULT '0',
  `Reviewable` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `OID` bigint(20) unsigned NOT NULL,
  `MID` int(10) unsigned NOT NULL,
  `Name` char(50) COLLATE utf8_bin NOT NULL,
  `Username` char(25) COLLATE utf8_bin DEFAULT NULL,
  `Password` bigint(20) unsigned DEFAULT NULL,
  `Rank` set('M','N','V') COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `AccountStatus` tinyint(4) NOT NULL,
  PRIMARY KEY (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

CREATE TABLE IF NOT EXISTS `videos` (
  `ID` bigint(20) NOT NULL,
  `URL` mediumint(9) NOT NULL,
  `DateTime` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `authorconames`
--
ALTER TABLE `authorconames`
  ADD CONSTRAINT `authorconames_ibfk_1` FOREIGN KEY (`AuthorID`) REFERENCES `authors` (`AID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
