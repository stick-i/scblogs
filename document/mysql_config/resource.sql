/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Schema         : resource

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 08/08/2022 12:30:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`
(
    `id`          int(10) UNSIGNED                                                NOT NULL AUTO_INCREMENT COMMENT '城市id',
    `name`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '名称',
    `province_id` int(10) UNSIGNED                                                NOT NULL COMMENT '所属省份id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `province_id` (`province_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`
(
    `id`            int(10) UNSIGNED                                                NOT NULL AUTO_INCREMENT COMMENT '图片id',
    `url`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '资料路径',
    `visit`         int(10) UNSIGNED                                                NOT NULL DEFAULT 0 COMMENT '访问量',
    `create_time`   datetime                                                        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime                                                        NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `img` (`url`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`
(
    `id`   int(11) UNSIGNED                                                NOT NULL AUTO_INCREMENT,
    `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '省份名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_mysql500_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for university
-- ----------------------------
DROP TABLE IF EXISTS `university`;
CREATE TABLE `university`
(
    `id`         int(11) UNSIGNED                                                NOT NULL AUTO_INCREMENT,
    `name`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '学校名称',
    `identifier` int(11) UNSIGNED                                                NOT NULL COMMENT '学校标识码',
    `code`       int(11) UNSIGNED                                                NOT NULL COMMENT '院校代码',
    `city_id`    int(11) UNSIGNED                                                NOT NULL COMMENT '所属城市',
    `rank`       smallint(6) UNSIGNED                                            NOT NULL COMMENT '办学层次（1本科，2专科）',
    `remark`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_mysql500_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `code` (`code`) USING BTREE,
    UNIQUE INDEX `identifier` (`identifier`) USING BTREE,
    INDEX `name` (`name`) USING BTREE,
    INDEX `city_id` (`city_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for university_view
-- ----------------------------
DROP VIEW IF EXISTS `university_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `university_view` AS
select `university`.`id`         AS `id`,
       `university`.`name`       AS `name`,
       `university`.`identifier` AS `identifier`,
       `university`.`code`       AS `code`,
       `province`.`name`         AS `province`,
       `city`.`name`             AS `city`,
       `university`.`rank`       AS `rank`,
       `university`.`remark`     AS `remark`
from ((`city` join `province`)
         join `university`)
where ((`province`.`id` = `city`.`province_id`) and (`city`.`id` = `university`.`city_id`));

SET FOREIGN_KEY_CHECKS = 1;
