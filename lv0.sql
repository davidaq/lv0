-- phpMyAdmin SQL Dump
-- version 3.5.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 11, 2013 at 07:34 AM
-- Server version: 5.5.28
-- PHP Version: 5.4.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `lv0`
--

-- --------------------------------------------------------

--
-- Table structure for table `admininfo`
--

DROP TABLE IF EXISTS `admininfo`;
CREATE TABLE IF NOT EXISTS `admininfo` (
  `adId` int(11) NOT NULL AUTO_INCREMENT,
  `adPassword` varchar(100) DEFAULT NULL,
  `adName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`adId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `attention`
--

DROP TABLE IF EXISTS `attention`;
CREATE TABLE IF NOT EXISTS `attention` (
  `attentionId` int(11) NOT NULL AUTO_INCREMENT,
  `attUser` int(11) DEFAULT NULL,
  `attedUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`attentionId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT AUTO_INCREMENT=3 ;

--
-- Dumping data for table `attention`
--

INSERT INTO `attention` (`attentionId`, `attUser`, `attedUser`) VALUES
(1, 2, 1),
(2, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `comId` int(11) NOT NULL AUTO_INCREMENT,
  `uId` int(11) DEFAULT NULL,
  `comContent` text,
  `tourLogId` int(11) DEFAULT NULL,
  PRIMARY KEY (`comId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `good`
--

DROP TABLE IF EXISTS `good`;
CREATE TABLE IF NOT EXISTS `good` (
  `goodId` int(11) NOT NULL AUTO_INCREMENT,
  `tourLogId` int(11) DEFAULT NULL,
  `uId` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`goodId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `landmark`
--

DROP TABLE IF EXISTS `landmark`;
CREATE TABLE IF NOT EXISTS `landmark` (
  `landmarkId` int(11) NOT NULL AUTO_INCREMENT,
  `landAddress` varchar(255) DEFAULT NULL,
  `landAbstract` varchar(255) DEFAULT NULL,
  `landPicture` varchar(255) DEFAULT NULL,
  `uId` int(11) DEFAULT NULL,
  `landDate` datetime DEFAULT NULL,
  PRIMARY KEY (`landmarkId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
CREATE TABLE IF NOT EXISTS `media` (
  `mediaId` int(11) NOT NULL AUTO_INCREMENT,
  `uId` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `mediaCover` varchar(255) DEFAULT NULL,
  `mediaName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`mediaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `mediacontent`
--

DROP TABLE IF EXISTS `mediacontent`;
CREATE TABLE IF NOT EXISTS `mediacontent` (
  `mediaContentId` int(11) NOT NULL AUTO_INCREMENT,
  `uId` int(11) DEFAULT NULL,
  `headline` varchar(255) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mediaAbstract` varchar(255) DEFAULT NULL,
  `mediaId` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`mediaContentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `authorId` int(11) DEFAULT NULL,
  `targetId` int(11) DEFAULT NULL,
  `messContent` text,
  `messDate` datetime DEFAULT NULL,
  `messReply` int(11) DEFAULT NULL,
  `messRead` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`messageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
CREATE TABLE IF NOT EXISTS `plan` (
  `planId` int(11) NOT NULL AUTO_INCREMENT,
  `authorId` int(11) DEFAULT NULL,
  `planHeadline` varchar(255) DEFAULT NULL,
  `planFavor` tinyint(4) DEFAULT NULL,
  `planContent` text,
  PRIMARY KEY (`planId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `planitem`
--

DROP TABLE IF EXISTS `planitem`;
CREATE TABLE IF NOT EXISTS `planitem` (
  `planItemId` int(11) NOT NULL AUTO_INCREMENT,
  `planId` int(11) DEFAULT NULL,
  `resortId` int(11) DEFAULT NULL,
  `resName` varchar(255) DEFAULT NULL,
  `startdate` datetime DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  PRIMARY KEY (`planItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `resort`
--

DROP TABLE IF EXISTS `resort`;
CREATE TABLE IF NOT EXISTS `resort` (
  `resortId` int(11) NOT NULL AUTO_INCREMENT,
  `resPicture` varchar(255) DEFAULT NULL,
  `resName` varchar(255) DEFAULT NULL,
  `resAddress` varchar(255) DEFAULT NULL,
  `resLabel` varchar(255) DEFAULT NULL,
  `resState` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`resortId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `resort`
--

INSERT INTO `resort` (`resortId`, `resPicture`, `resName`, `resAddress`, `resLabel`, `resState`) VALUES
(1, 'test', 'heheh', 'jklasjda', 'hsad adaksdjajd ', '1'),
(2, 'aksldjaskd', 'jlkdsajalskd', 'kasldjklsfj', 'jfdsjfoi', 'jodiads'),
(3, 'aksldjaskd', 'jlkdsajalskd', 'kasldjklsfj', 'jfdsjfoi', 'jodiads'),
(4, 'aksldjaskd', 'jlkdsajalskd', 'kasldjklsfj', 'jfdsjfoi', 'jodiads'),
(5, 'aksldjaskd', 'jlkdsajalskd', 'kasldjklsfj', 'jfdsjfoi', 'jodiads'),
(6, 'aksldjaskd', 'jlkdsajalskd', 'kasldjklsfj', 'jfdsjfoi', 'jodiads'),
(7, 'aksldjaskd', 'jlkdsajalskd', 'kasldjklsfj', 'jfdsjfoi', 'jodiads'),
(8, 'aksldjaskd', 'jlkdsajalskd', 'kasldjklsfj', 'jfdsjfoi', 'jodiads'),
(9, 'aksldjaskd', 'jlkdsajalskd', 'kasldjklsfj', 'jfdsjfoi', 'jodiads');

-- --------------------------------------------------------

--
-- Table structure for table `resortremark`
--

DROP TABLE IF EXISTS `resortremark`;
CREATE TABLE IF NOT EXISTS `resortremark` (
  `resortRemarkId` int(11) NOT NULL AUTO_INCREMENT,
  `AuthorId` int(11) DEFAULT NULL,
  `resortId` int(11) DEFAULT NULL,
  `resRemark` int(11) DEFAULT NULL,
  PRIMARY KEY (`resortRemarkId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ressupplement`
--

DROP TABLE IF EXISTS `ressupplement`;
CREATE TABLE IF NOT EXISTS `ressupplement` (
  `resSupplementId` int(11) NOT NULL AUTO_INCREMENT,
  `resortId` int(11) DEFAULT NULL,
  `resHeadline` varchar(100) DEFAULT NULL,
  `resContent` text,
  `resDate` datetime DEFAULT NULL,
  `resAuthor` int(11) DEFAULT NULL,
  `resFrom` int(11) DEFAULT NULL,
  PRIMARY KEY (`resSupplementId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `sensitive`
--

DROP TABLE IF EXISTS `sensitive`;
CREATE TABLE IF NOT EXISTS `sensitive` (
  `sensitiveId` int(11) NOT NULL AUTO_INCREMENT,
  `sensitiveWords` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sensitiveId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tourlog`
--

DROP TABLE IF EXISTS `tourlog`;
CREATE TABLE IF NOT EXISTS `tourlog` (
  `tourLogId` int(11) NOT NULL AUTO_INCREMENT,
  `author` int(11) DEFAULT NULL,
  `content` text,
  `relaySourceId` int(11) DEFAULT NULL,
  `relayFromId` int(11) DEFAULT NULL,
  `abstract` text,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`tourLogId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `tourlog`
--

INSERT INTO `tourlog` (`tourLogId`, `author`, `content`, `relaySourceId`, `relayFromId`, `abstract`, `date`) VALUES
(1, 1, 'test', NULL, NULL, 'test', '2013-07-10 01:40:56'),
(2, 2, '<p>\r\n	hahahahah\r\n</p>\r\n<p>\r\n	??????\r\n</p>', NULL, NULL, '<p>\r\n	hahahahah\r\n</p>\r\n<p>\r\n	??????\r\n</p>', '2013-07-10 12:17:05'),
(3, 2, '<strong>asdasdasd</strong>', NULL, NULL, '<strong>asdasdasd</strong>', '2013-07-10 12:27:48'),
(4, 1, '测试', NULL, NULL, '测试', '2013-07-11 09:29:30'),
(5, 1, '<p>\r\n	多行日志！！！！\r\n</p>\r\n<p>\r\n	大一点\r\n</p>\r\n<p>\r\n	再大一点！！！！！！\r\n</p>\r\n<p>\r\n	<strong>多行日志！！！！</strong>\r\n</p>\r\n<p>\r\n	<strong>大一点</strong>\r\n</p>\r\n<p>\r\n	<strong>再大一点！！！！！！</strong>\r\n</p>\r\n<p>\r\n	多行日志！！！！\r\n</p>\r\n<p>\r\n	大一点\r\n</p>\r\n<p>\r\n	再大一点！！！！！！\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<br />\r\n</p>', NULL, NULL, '<p>\r\n	多行日志！！！！\r\n</p>\r\n<p>\r\n	大一点\r\n</p>\r\n<p>\r\n	再大一点！！！！！！\r\n</p>\r\n<p>\r\n	<strong>多行日志！！！！</strong>\r\n</p>\r\n<p>\r\n	<strong>大一点</strong>\r\n</p>\r\n<p>\r\n	<strong>再大一点！！！！！！</strong>\r\n</p>\r\n<p>\r\n	多行日志！！！！\r\n</p>\r\n<p>\r\n	大一点\r\n</p>\r\n<p>\r\n	再大一点！！！！！！\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<br />\r\n</p>', '2013-07-11 10:47:28'),
(6, 1, '<p>\r\n	多行日志！！！！\r\n</p>\r\n<p>\r\n	大一点\r\n</p>\r\n<p>\r\n	再大一点！！！！！！\r\n</p>\r\n<p>\r\n	<strong>多行日志！！！！</strong>\r\n</p>\r\n<p>\r\n	<strong>大一点</strong>\r\n</p>\r\n<p>\r\n	<strong>再大一点！！！！！！</strong>\r\n</p>\r\n<p>\r\n	多行日志！！！！\r\n</p>\r\n<p>\r\n	大一点\r\n</p>\r\n<p>\r\n	再大一点！！！！！！\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<strong>多行日志！！！！</strong>\r\n	</p>\r\n	<p>\r\n		<strong>大一点</strong>\r\n	</p>\r\n	<p>\r\n		<strong>再大一点！！！！！！</strong>\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<strong>多行日志！！！！</strong>\r\n	</p>\r\n	<p>\r\n		<strong>大一点</strong>\r\n	</p>\r\n	<p>\r\n		<strong>再大一点！！！！！！</strong>\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<strong>多行日志！！！！</strong>\r\n	</p>\r\n	<p>\r\n		<strong>大一点</strong>\r\n	</p>\r\n	<p>\r\n		<strong>再大一点！！！！！！</strong>\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n</p>', NULL, NULL, '<p>\r\n	多行日志！！！！\r\n</p>\r\n<p>\r\n	大一点\r\n</p>\r\n<p>\r\n	再大一点！！！！！！\r\n</p>\r\n<p>\r\n	<strong>多行日志！！！！</strong>\r\n</p>\r\n<p>\r\n	<strong>大一点</strong>\r\n</p>\r\n<p>\r\n	<strong>再大一点！！！！！！</strong>\r\n</p>\r\n<p>\r\n	多行日志！！！！\r\n</p>\r\n<p>\r\n	大一点\r\n</p>\r\n<p>\r\n	再大一点！！！！！！\r\n</p>\r\n<p>\r\n	<br />\r\n</p>\r\n<p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<strong>多行日志！！！！</strong>\r\n	</p>\r\n	<p>\r\n		<strong>大一点</strong>\r\n	</p>\r\n	<p>\r\n		<strong>再大一点！！！！！！</strong>\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<strong>多行日志！！！！</strong>\r\n	</p>\r\n	<p>\r\n		<strong>大一点</strong>\r\n	</p>\r\n	<p>\r\n		<strong>再大一点！！！！！！</strong>\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<strong>多行日志！！！！</strong>\r\n	</p>\r\n	<p>\r\n		<strong>大一点</strong>\r\n	</p>\r\n	<p>\r\n		<strong>再大一点！！！！！！</strong>\r\n	</p>\r\n	<p>\r\n		多行日志！！！！\r\n	</p>\r\n	<p>\r\n		大一点\r\n	</p>\r\n	<p>\r\n		再大一点！！！！！！\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n	<p>\r\n		<br />\r\n	</p>\r\n</p>', '2013-07-11 10:48:15');

-- --------------------------------------------------------

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE IF NOT EXISTS `userinfo` (
  `uId` int(11) NOT NULL AUTO_INCREMENT,
  `uName` varchar(100) DEFAULT NULL,
  `uPassword` varchar(100) DEFAULT NULL,
  `uMail` varchar(255) DEFAULT NULL,
  `uPhone` varchar(20) DEFAULT NULL,
  `uPortrait` varchar(255) DEFAULT NULL,
  `uSketch` varchar(255) DEFAULT NULL,
  `uState` varchar(100) DEFAULT NULL,
  `uLabel` varchar(255) DEFAULT NULL,
  `uScore` int(11) DEFAULT NULL,
  PRIMARY KEY (`uId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `userinfo`
--

INSERT INTO `userinfo` (`uId`, `uName`, `uPassword`, `uMail`, `uPhone`, `uPortrait`, `uSketch`, `uState`, `uLabel`, `uScore`) VALUES
(1, 'davidaq', 'b2248c2b421edb6b9b43452d4c22c26c', 'aq@num16.com', '13520175097', 'https://www.gravatar.com/avatar/597a542bd38dbe3021f203214d7fe609?s=32&d=identicon&r=PG', 'hehehehe', NULL, 'fuck and fuck hehe', NULL),
(2, 'aq', 'b2248c2b421edb6b9b43452d4c22c26c', 'math_dav2003@num16.com', NULL, NULL, NULL, NULL, NULL, NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
