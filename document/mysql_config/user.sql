/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 08/08/2022 12:30:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`
(
    `id`     int(10) UNSIGNED                                                 NOT NULL AUTO_INCREMENT,
    `param`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '参数',
    `result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '值',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `param` (`param`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`            int(11) UNSIGNED                                                NOT NULL AUTO_INCREMENT,
    `username`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '用户名',
    `school_code`   int(11) UNSIGNED                                                NULL     DEFAULT NULL COMMENT '院校代码',
    `nickname`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL     DEFAULT NULL COMMENT '昵称',
    `avatar_url`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL     DEFAULT NULL COMMENT '头像链接',
    `register_time` datetime                                                        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for user_basic
-- ----------------------------
DROP TABLE IF EXISTS `user_basic`;
CREATE TABLE `user_basic`
(
    `username`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NOT NULL COMMENT '用户名',
    `realname`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NULL DEFAULT NULL COMMENT '真实姓名',
    `birthday`         datetime(6)                                                      NULL DEFAULT NULL COMMENT '生日',
    `intro`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '个人简介',
    `gender`           bit(1)                                                           NULL DEFAULT NULL COMMENT '性别',
    `city_id`          int(11) UNSIGNED                                                 NULL DEFAULT NULL COMMENT '城市id',
    `province_id`      int(11) UNSIGNED                                                 NULL DEFAULT NULL COMMENT '省份id',
    `modified_time`    datetime(6)                                                      NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '信息修改时间',
    `name_modify_time` datetime(6)                                                      NULL DEFAULT NULL COMMENT '用户名修改时间',
    `start_work_time`  datetime(6)                                                      NULL DEFAULT NULL COMMENT '开始工作的时间',
    PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for user_education
-- ----------------------------
DROP TABLE IF EXISTS `user_education`;
CREATE TABLE `user_education`
(
    `user_id`             int(11) UNSIGNED                                                 NOT NULL COMMENT '用户名',
    `create_time`         datetime(6)                                                      NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
    `school`              varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NULL DEFAULT NULL COMMENT '学校名称',
    `school_id`           int(11) UNSIGNED                                                 NULL DEFAULT NULL COMMENT '学校id',
    `profession`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '专业',
    `degree`              smallint(5) UNSIGNED                                             NULL DEFAULT NULL COMMENT '学位程度',
    `start_time`          datetime(6)                                                      NULL DEFAULT NULL COMMENT '入学时间',
    `end_time`            datetime(6)                                                      NULL DEFAULT NULL COMMENT '毕业时间',
    `audit_img_url`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NULL DEFAULT NULL COMMENT '图片链接',
    `safety_audit_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '安全审核状态',
    `status`              varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '状态码',
    `modified_time`       datetime                                                         NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for user_follow
-- ----------------------------
DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow`
(
    `id`          int(11) UNSIGNED                                                NOT NULL AUTO_INCREMENT,
    `fans_id`     int(10) UNSIGNED                                                NOT NULL COMMENT '粉丝id',
    `follow_id`   int(10) UNSIGNED                                                NOT NULL COMMENT '关注id',
    `note`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL     DEFAULT NULL COMMENT '备注',
    `status`      int(10) UNSIGNED ZEROFILL                                       NULL     DEFAULT NULL COMMENT '状态码',
    `create_time` datetime                                                        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `user_id` (`fans_id`) USING BTREE,
    INDEX `follow_id` (`follow_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for user_general
-- ----------------------------
DROP TABLE IF EXISTS `user_general`;
CREATE TABLE `user_general`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(0) UNIQUE NOT NULL COMMENT '用户id',
  `like_num` int(0) NULL DEFAULT 0 COMMENT '用户获赞',
  `fans_num` int(0) NULL DEFAULT 0 COMMENT '用户粉丝数',
  `comment_num` int(0) NULL DEFAULT 0 COMMENT '用户评论数',
  `collect_num` int(0) NULL DEFAULT 0 COMMENT '收藏博客数量',
  `view_num` int(0) NULL DEFAULT 0 COMMENT '访问数',
  `blog_num` int(0) NULL DEFAULT 0 COMMENT '用户博客数量',
  `week_rank` int(0) NULL DEFAULT NULL COMMENT '周排行',
  `total_rank` int(0) NULL DEFAULT NULL COMMENT '总排行',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_safety
-- ----------------------------
DROP TABLE IF EXISTS `user_safety`;
CREATE TABLE `user_safety`
(
    `user_id`  int(10) UNSIGNED                                                 NOT NULL COMMENT '用户id',
    `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NOT NULL COMMENT '用户名',
    `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '密码',
    `mail`     varchar(30) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NOT NULL COMMENT '邮箱',
    `mobile`   varchar(11) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NULL DEFAULT NULL COMMENT '手机号',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `email` (`mail`) USING BTREE,
    UNIQUE INDEX `mobile` (`mobile`) USING BTREE,
    INDEX `fk_user_safety_user_general_1` (`username`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- View structure for user_view
-- ----------------------------
DROP VIEW IF EXISTS `user_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_view` AS
select `user`.`id`                                    AS `id`,
       `user`.`username`                              AS `username`,
       `user`.`nickname`                              AS `nickname`,
       `user`.`school_code`                           AS `school_code`,
       concat(`config`.`result`, `user`.`avatar_url`) AS `avatar_url`,
       `user`.`register_time`                         AS `register_time`
from (`user`
         join `config`)
where (`config`.`param` = 'avatar_url');

-- ----------------------------
-- View structure for fans_view
-- ----------------------------
DROP VIEW IF EXISTS `fans_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `fans_view` AS
select `user_follow`.`id`          AS `id`,
       `user_follow`.`follow_id`   AS `user_id`,
       `user_follow`.`fans_id`     AS `fans_id`,
       `user_follow`.`note`        AS `note`,
       `user_follow`.`status`      AS `status`,
       `user_follow`.`create_time` AS `create_time`,
       `user_view`.`username`      AS `username`,
       `user_view`.`nickname`      AS `nickname`,
       `user_view`.`school_code`   AS `school_code`,
       `user_view`.`avatar_url`    AS `avatar_url`,
       `user_view`.`register_time` AS `register_time`
from (`user_follow`
         left join `user_view` on ((`user_follow`.`fans_id` = `user_view`.`id`)));

-- ----------------------------
-- View structure for follow_view
-- ----------------------------
DROP VIEW IF EXISTS `follow_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `follow_view` AS
select `user_follow`.`id`          AS `id`,
       `user_follow`.`fans_id`     AS `user_id`,
       `user_follow`.`follow_id`   AS `follow_id`,
       `user_follow`.`note`        AS `note`,
       `user_follow`.`status`      AS `status`,
       `user_follow`.`create_time` AS `create_time`,
       `user_view`.`username`      AS `username`,
       `user_view`.`nickname`      AS `nickname`,
       `user_view`.`school_code`   AS `school_code`,
       `user_view`.`avatar_url`    AS `avatar_url`,
       `user_view`.`register_time` AS `register_time`
from (`user_follow`
         left join `user_view` on ((`user_view`.`id` = `user_follow`.`follow_id`)));

SET FOREIGN_KEY_CHECKS = 1;
