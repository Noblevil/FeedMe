/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : feedme

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2016-01-19 19:52:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `discuss`
-- ----------------------------
DROP TABLE IF EXISTS `discuss`;
CREATE TABLE `discuss` (
  `discuss_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `discuss_title` varchar(45) NOT NULL,
  `discuss_content` text NOT NULL,
  `discuss_create_time` datetime NOT NULL,
  `discuss_hot` int(11) NOT NULL,
  PRIMARY KEY (`discuss_id`),
  KEY `user_id_fk_discuss_idx` (`user_id`),
  CONSTRAINT `user_id_fk_discuss` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of discuss
-- ----------------------------

-- ----------------------------
-- Table structure for `memory`
-- ----------------------------
DROP TABLE IF EXISTS `memory`;
CREATE TABLE `memory` (
  `memory_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `word_id` int(11) NOT NULL,
  `memory_proficiency` int(11) NOT NULL,
  `memory_difficulty` int(11) NOT NULL,
  `memory_important` int(11) NOT NULL,
  `memory_accuracy` int(11) NOT NULL,
  PRIMARY KEY (`memory_id`),
  KEY `user_id_fk_memory_idx` (`user_id`),
  KEY `word_id_fk_memeory_idx` (`word_id`),
  CONSTRAINT `user_id_fk_memory` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `word_id_fk_memeory` FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of memory
-- ----------------------------

-- ----------------------------
-- Table structure for `pet`
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet` (
  `pet_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `pet_hunger` int(11) NOT NULL,
  `pet_clean` int(11) NOT NULL,
  `pet_mood` int(11) NOT NULL,
  `pet_health` int(11) NOT NULL,
  PRIMARY KEY (`pet_id`),
  KEY `user_id_fk_pet_idx` (`user_id`),
  CONSTRAINT `user_id_fk_pet` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pet
-- ----------------------------

-- ----------------------------
-- Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `reply_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `discuss_id` int(11) NOT NULL,
  `reply_content` text NOT NULL,
  `reply_create_time` datetime NOT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `user_id_fk_reply_idx` (`user_id`),
  KEY `discuss_id_fk_reply_idx` (`discuss_id`),
  CONSTRAINT `discuss_id_fk_reply` FOREIGN KEY (`discuss_id`) REFERENCES `discuss` (`discuss_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id_fk_reply` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_account` varchar(45) NOT NULL,
  `user_password` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `user_head` varchar(150) NOT NULL,
  `user_create_time` datetime NOT NULL,
  `user_point` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------


-- ----------------------------
-- Table structure for `word`
-- ----------------------------
DROP TABLE IF EXISTS `word`;
CREATE TABLE `word` (
  `word_id` int(11) NOT NULL AUTO_INCREMENT,
  `word_spell` varchar(45) NOT NULL,
  `word_speech` varchar(45) NOT NULL,
  `word_mean` varchar(45) NOT NULL,
  `word_phonetic_symbol` varchar(45) NOT NULL,
  `word_classify_of_book` varchar(45) NOT NULL,
  `word_classify_unit` tinyint(4) NOT NULL,
  `word_picture` varchar(100) NOT NULL,
  `word_example1` varchar(150) NOT NULL,
  `word_example2` varchar(150) NOT NULL,
  `word_mean_of_example1` varchar(150) NOT NULL,
  `word_mean_of_example2` varchar(150) NOT NULL,
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of word
-- ----------------------------
