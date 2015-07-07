-- phpMyAdmin SQL Dump
-- version 3.2.3
-- http://www.phpmyadmin.net
--
-- 호스트: localhost
-- 처리한 시간: 10-12-15 14:20 
-- 서버 버전: 5.0.77
-- PHP 버전: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 데이터베이스: `sysprog`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `log`
--

CREATE TABLE IF NOT EXISTS `log` (
  `num` int(10) NOT NULL auto_increment,
  `user` int(10) NOT NULL,
  `temp` int(3) NOT NULL,
  `humi` int(3) NOT NULL,
  `illu` tinyint(1) NOT NULL,
  `event` text NOT NULL,
  `section` int(1) NOT NULL,
  `secure` tinyint(1) NOT NULL,
  `date` datetime NOT NULL,
  `photo` text NOT NULL,
  PRIMARY KEY  (`num`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=149 ;

-- --------------------------------------------------------

--
-- 테이블 구조 `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `user` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `section` int(1) NOT NULL,
  PRIMARY KEY  (`user`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `member`
--

CREATE TABLE IF NOT EXISTS `member` (
  `user` int(10) unsigned NOT NULL,
  `password` text NOT NULL,
  `level` int(1) NOT NULL default '0',
  PRIMARY KEY  (`user`),
  UNIQUE KEY `user` (`user`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `resource`
--

CREATE TABLE IF NOT EXISTS `resource` (
  `num` int(4) NOT NULL,
  `name` text NOT NULL,
  `level` int(1) NOT NULL,
  `temp_max` int(3) NOT NULL,
  `temp_min` int(3) NOT NULL,
  `humi_max` int(2) NOT NULL,
  `humi_min` int(2) NOT NULL,
  `illu` tinyint(1) NOT NULL,
  `section1` int(10) NOT NULL default '0',
  `section2` int(10) NOT NULL default '0',
  `section3` int(10) NOT NULL default '0',
  `section4` int(10) NOT NULL default '0',
  PRIMARY KEY  (`num`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `root_member`
--

CREATE TABLE IF NOT EXISTS `root_member` (
  `id` text NOT NULL,
  `password` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `status`
--

CREATE TABLE IF NOT EXISTS `status` (
  `sec1_temp` int(3) NOT NULL,
  `sec1_humi` int(3) NOT NULL,
  `sec1_illu` tinyint(1) NOT NULL,
  `sec1_secure` tinyint(1) NOT NULL default '0',
  `sec1_on` tinyint(1) NOT NULL,
  `sec2_temp` int(3) NOT NULL,
  `sec2_humi` int(3) NOT NULL,
  `sec2_illu` tinyint(1) NOT NULL,
  `sec2_secure` tinyint(1) NOT NULL default '0',
  `sec2_on` tinyint(1) NOT NULL,
  `sec3_temp` int(3) NOT NULL,
  `sec3_humi` int(3) NOT NULL,
  `sec3_illu` tinyint(1) NOT NULL,
  `sec3_secure` tinyint(1) NOT NULL default '0',
  `sec3_on` tinyint(1) NOT NULL,
  `sec4_temp` int(3) NOT NULL,
  `sec4_humi` int(3) NOT NULL,
  `sec4_illu` tinyint(1) NOT NULL,
  `sec4_secure` tinyint(1) NOT NULL default '0',
  `sec4_on` int(11) NOT NULL,
  `flag` varchar(1) NOT NULL default '1'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
