/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.65-MariaDB : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `test`;

/*Table structure for table `m_user` */

DROP TABLE IF EXISTS `m_user`;

CREATE TABLE `m_user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `password` char(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `iv` char(8) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '偏移量',
  `email` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮件',
  `mobile` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机电话',
  `gender` int(2) DEFAULT '0' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `avatar` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态',
  `lasted` datetime DEFAULT NULL COMMENT '最后的登陆时间',
  `created` datetime NOT NULL COMMENT '创建日期',
  `modified` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_login_check` (`email`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `m_user` */

insert  into `m_user`(`id`,`username`,`password`,`iv`,`email`,`mobile`,`gender`,`birthday`,`avatar`,`status`,`lasted`,`created`,`modified`) values (9,'张','7c4b957d0dc34b25ff9807f4b9aae8c577bf22b99c56bcfa726d1c64c01c973b','rXuo9CGG','zhang@hcworld.xyz','13000000001',0,'2021-04-24','',0,'2021-04-24 19:22:45','2021-04-24 19:22:45','2021-04-24 19:22:45');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
