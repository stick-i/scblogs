/*
 Navicat Premium Data Transfer

 Source Server         : blog.sticki.live
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : sql.blog.sticki.live:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 08/08/2022 12:30:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`
(
    `id`            int(11) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT '博客id',
    `author_id`     int(11) UNSIGNED                                              NOT NULL COMMENT '作者id',
    `title`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `description`   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
    `school_code`   int(11) UNSIGNED                                              NULL     DEFAULT NULL COMMENT '院校代码',
    `cover_image`   char(32) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NULL     DEFAULT NULL COMMENT '封面图',
    `create_time`   datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `release_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '发表时间',
    `modified_time` datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `status`        int(11) UNSIGNED                                              NOT NULL COMMENT '发表状态（1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中）',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `author_id` (`author_id`) USING BTREE,
    INDEX `school_code` (`school_code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 34
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for blog_content
-- ----------------------------
DROP TABLE IF EXISTS `blog_content`;
CREATE TABLE `blog_content`
(
    `blog_id`       int(11) UNSIGNED                                      NOT NULL COMMENT '博客id',
    `content`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '博客内容',
    `modified_time` datetime                                              NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`blog_id`) USING BTREE,
    UNIQUE INDEX `blog_id` (`blog_id`) USING BTREE,
    CONSTRAINT `fk_blog_content_blog_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for blog_content_html
-- ----------------------------
DROP TABLE IF EXISTS `blog_content_html`;
CREATE TABLE `blog_content_html`
(
    `blog_id`       int(11) UNSIGNED                                      NOT NULL COMMENT '博客id',
    `content`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '博客内容（html）',
    `modified_time` datetime                                              NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`blog_id`) USING BTREE,
    UNIQUE INDEX `blog_id` (`blog_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for blog_general
-- ----------------------------
DROP TABLE IF EXISTS `blog_general`;
CREATE TABLE `blog_general`
(
    `blog_id`        int(10) UNSIGNED NOT NULL COMMENT '博客id',
    `view_num`       int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览量',
    `like_num`       int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞量',
    `comment_num`    int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论量',
    `collection_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '收藏量',
    `score`          int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评分',
    PRIMARY KEY (`blog_id`) USING BTREE,
    UNIQUE INDEX `blog_id` (`blog_id`) USING BTREE,
    CONSTRAINT `fk_blog_general_blog_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for blog_set_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_set_tag`;
CREATE TABLE `blog_set_tag`
(
    `id`      int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    `tag_id`  int(10) UNSIGNED NOT NULL,
    `blog_id` int(10) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_blog_set_tag_blog_1` (`blog_id`) USING BTREE,
    INDEX `fk_blog_set_tag_tag_1` (`tag_id`) USING BTREE,
    CONSTRAINT `fk_blog_set_tag_blog_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_blog_set_tag_tag_1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for collect_blog
-- ----------------------------
DROP TABLE IF EXISTS `collect_blog`;
CREATE TABLE `collect_blog`
(
    `id`          int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    `blog_id`     int(10) UNSIGNED NOT NULL,
    `user_id`     int(10) UNSIGNED NOT NULL,
    `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_blog_collection_user_1` (`user_id`) USING BTREE,
    INDEX `fk_blog_collection_blog_1` (`blog_id`) USING BTREE,
    CONSTRAINT `collect_blog_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE NO ACTION ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for column
-- ----------------------------
DROP TABLE IF EXISTS `column`;
CREATE TABLE `column`
(
    `id` int(11) NOT NULL COMMENT '专栏id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`
(
    `id`     int(10) UNSIGNED                                                 NOT NULL AUTO_INCREMENT,
    `param`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '参数',
    `result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '值',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for like_blog
-- ----------------------------
DROP TABLE IF EXISTS `like_blog`;
CREATE TABLE `like_blog`
(
    `id`          int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '点赞id',
    `blog_id`     int(10) UNSIGNED NOT NULL COMMENT '博客id',
    `user_id`     int(10) UNSIGNED NOT NULL COMMENT '用户id',
    `create_time` datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_blog_likes_user_1` (`user_id`) USING BTREE,
    INDEX `fk_blog_likes_blog_1` (`blog_id`) USING BTREE,
    CONSTRAINT `like_blog_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE NO ACTION ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`
(
    `id`            int(10) UNSIGNED                                                 NOT NULL AUTO_INCREMENT,
    `name`          varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NOT NULL,
    `alias`         varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci  NULL     DEFAULT NULL,
    `description`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL     DEFAULT NULL,
    `create_time`   datetime                                                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time` datetime                                                         NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- View structure for blog_view
-- ----------------------------
DROP VIEW IF EXISTS `blog_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `blog_view` AS
select `blog`.`id`                                     AS `id`,
       `blog`.`author_id`                              AS `author_id`,
       `blog`.`title`                                  AS `title`,
       `blog`.`description`                            AS `description`,
       `blog`.`school_code`                            AS `school_code`,
       concat(`config`.`result`, `blog`.`cover_image`) AS `cover_image`,
       `blog`.`create_time`                            AS `create_time`,
       `blog`.`release_time`                           AS `release_time`,
       `blog`.`modified_time`                          AS `modified_time`,
       `blog`.`status`                                 AS `status`,
       `blog_general`.`view_num`                       AS `view_num`,
       `blog_general`.`like_num`                       AS `like_num`,
       `blog_general`.`comment_num`                    AS `comment_num`,
       `blog_general`.`collection_num`                 AS `collection_num`,
       `blog_general`.`score`                          AS `score`
from ((`blog` left join `blog_general` on ((`blog`.`id` = `blog_general`.`blog_id`)))
         join `config`)
where (`config`.`param` = 'cover_url');

SET FOREIGN_KEY_CHECKS = 1;
