/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Schema         : comment

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 08/08/2022 12:30:38
*/

set names utf8mb4;
set FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
drop table if exists `comment`;
create table `comment`
(
    `id`             int(10) unsigned                                             not null auto_increment comment 'id',
    `user_id`        int(10) unsigned                                             not null comment '用户id',
    `blog_id`        int(10) unsigned                                             not null comment '博客id',
    `content`        text character set `utf8` collate `utf8_general_mysql500_ci` not null comment '评论内容',
    `parent_id`      int(10) unsigned                                             null     default null comment '父评论id',
    `parent_user_id` int(10) unsigned                                             null     default null comment '父评论用户id',
    `create_time`    datetime                                                     not null default current_timestamp comment '创建时间',
    `is_deleted`     tinyint(3) unsigned                                          null     default 0 comment '是否已经删除，0未删除，1已删除',
    primary key (`id`) using btree,
    index `index_comment_user_id` (`user_id`) using btree,
    index `index_comment_blog_id` (`blog_id`) using btree
) engine = InnoDB
  auto_increment = 131
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

set FOREIGN_KEY_CHECKS = 1;
