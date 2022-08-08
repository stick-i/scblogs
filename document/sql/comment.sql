/*
 Navicat Premium Data Transfer

 Source Server         : blog.sticki.live
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : sql.blog.sticki.live:3306
 Source Schema         : comment

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 08/08/2022 12:30:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`             int(10) UNSIGNED                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`        int(10) UNSIGNED                                         NOT NULL COMMENT '用户id',
    `blog_id`        int(10) UNSIGNED                                         NOT NULL COMMENT '博客id',
    `content`        text CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '评论内容',
    `parent_id`      int(10) UNSIGNED                                         NULL     DEFAULT NULL COMMENT '父评论id',
    `parent_user_id` int(10) UNSIGNED                                         NULL     DEFAULT NULL COMMENT '父评论用户id',
    `create_time`    datetime                                                 NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 117
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
