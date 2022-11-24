/*
 Navicat Premium Data Transfer

 Source Server         : blog.sticki.live
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : sql.blog.sticki.live:3306
 Source Schema         : blink

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 08/08/2022 12:30:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blink
-- ----------------------------
DROP TABLE IF EXISTS `blink`;
CREATE TABLE `blink`
(
    `id`            int(11) UNSIGNED                                      NOT NULL AUTO_INCREMENT COMMENT '动态id',
    `user_id`       int(11) UNSIGNED                                      NOT NULL COMMENT '用户id',
    `school_code`   int(11) UNSIGNED                                      NULL     DEFAULT NULL COMMENT '学校代码',
    `content`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '动态内容',
    `create_time`   datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
    `modified_time` datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for blink_general
-- ----------------------------
DROP TABLE IF EXISTS `blink_general`;
CREATE TABLE `blink_general`
(
    `blink_id`    int(11) UNSIGNED NOT NULL COMMENT '动态id',
    `view_num`    int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览量',
    `likes_num`   int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞量',
    `comment_num` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论量',
    `score`       double UNSIGNED  NOT NULL DEFAULT 0 COMMENT '评分',
    PRIMARY KEY (`blink_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for blink_view
-- ----------------------------
DROP VIEW IF EXISTS `blink_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `blink_view` AS
select `blink`.`id`                  AS `id`,
       `blink`.`user_id`             AS `user_id`,
       `blink`.`school_code`         AS `school_code`,
       `blink`.`content`             AS `content`,
       `blink`.`create_time`         AS `create_time`,
       `blink`.`modified_time`       AS `modified_time`,
       `blink_general`.`view_num`    AS `view_num`,
       `blink_general`.`likes_num`   AS `likes_num`,
       `blink_general`.`comment_num` AS `comment_num`,
       `blink_general`.`score`       AS `score`
from (`blink`
         join `blink_general`)
where (`blink`.`id` = `blink_general`.`blink_id`);

SET FOREIGN_KEY_CHECKS = 1;
