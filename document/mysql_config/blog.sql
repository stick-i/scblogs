/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 08/08/2022 12:30:29
*/

set names utf8mb4;
set FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
drop table if exists `blog`;
create table `blog`
(
    `id`            int(11) unsigned                                                  not null auto_increment comment '博客id',
    `author_id`     int(11) unsigned                                                  not null comment '作者id',
    `title`         varchar(256) character set `utf8mb4` collate `utf8mb4_general_ci` not null comment '标题',
    `description`   varchar(256) character set `utf8mb4` collate `utf8mb4_general_ci` not null comment '描述',
    `school_code`   int(11) unsigned                                                  null     default null comment '院校代码',
    `cover_image`   char(32) character set `utf8` collate `utf8_general_mysql500_ci`  null     default null comment '封面图',
    `release_time`  datetime                                                          null     default null comment '发表时间',
    `write_type`    tinyint(3) unsigned                                               not null default 1 comment '博客创作类型：1. 原创; 2. 转载',
    `status`        tinyint(3) unsigned                                               not null comment '发表状态（1表示已发表、2表示未发表、3为仅自己可见、4为回收站、5为审核中）',
    `create_time`   datetime                                                          not null default current_timestamp comment '创建时间',
    `modified_time` datetime                                                          not null default current_timestamp on update current_timestamp comment '修改时间',
    `is_deleted`    tinyint(3) unsigned                                               not null default 0 comment '是否已经删除，0未删除，1已删除',
    primary key (`id`) using btree,
    index `author_id` (`author_id`) using btree,
    index `school_code` (`school_code`) using btree
) engine = InnoDB
  auto_increment = 0
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for blog_content
-- ----------------------------
drop table if exists `blog_content`;
create table `blog_content`
(
    `blog_id`       int(11) unsigned                                          not null comment '博客id',
    `content`       text character set `utf8mb4` collate `utf8mb4_general_ci` not null comment '博客内容',
    `modified_time` datetime                                                  not null default current_timestamp on update current_timestamp comment '修改时间',
    `is_deleted`    tinyint(3) unsigned                                       not null default 0 comment '0为未删除，1为已删除',
    primary key (`blog_id`) using btree,
    unique index `blog_id` (`blog_id`) using btree
) engine = InnoDB
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for blog_content_html
-- ----------------------------
drop table if exists `blog_content_html`;
create table `blog_content_html`
(
    `blog_id`       int(11) unsigned                                          not null comment '博客id',
    `content`       text character set `utf8mb4` collate `utf8mb4_general_ci` not null comment '博客内容（html）',
    `modified_time` datetime                                                  not null default current_timestamp on update current_timestamp,
    `is_deleted`    tinyint(3) unsigned                                       not null default 0 comment '0为未删除，1为已删除',
    primary key (`blog_id`) using btree,
    unique index `blog_id` (`blog_id`) using btree
) engine = InnoDB
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for blog_general
-- ----------------------------
drop table if exists `blog_general`;
create table `blog_general`
(
    `blog_id`        int(10) unsigned    not null comment '博客id',
    `view_num`       int(10) unsigned    not null default 0 comment '浏览量',
    `like_num`       int(10) unsigned    not null default 0 comment '点赞量',
    `comment_num`    int(10) unsigned    not null default 0 comment '评论量',
    `collection_num` int(10) unsigned    not null default 0 comment '收藏量',
    `score`          int(10) unsigned    not null default 0 comment '评分',
    `is_deleted`     tinyint(3) unsigned not null default 0 comment '0为未删除，1为已删除',
    primary key (`blog_id`) using btree,
    unique index `blog_id` (`blog_id`) using btree
) engine = InnoDB
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for blog_set_tag
-- ----------------------------
drop table if exists `blog_set_tag`;
create table `blog_set_tag`
(
    `id`         int(10) unsigned    not null auto_increment,
    `tag_id`     int(10) unsigned    not null,
    `blog_id`    int(10) unsigned    not null,
    `is_deleted` tinyint(3) unsigned not null default 0 comment '0为未删除，1为已删除',
    primary key (`id`) using btree,
    index `index_tag_blog` (`tag_id`, `blog_id`) using btree
) engine = InnoDB
  auto_increment = 0
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for collect_blog
-- ----------------------------
drop table if exists `collect_blog`;
create table `collect_blog`
(
    `id`          int(10) unsigned    not null auto_increment,
    `blog_id`     int(10) unsigned    not null,
    `user_id`     int(10) unsigned    not null,
    `create_time` datetime            not null default current_timestamp,
    `is_deleted`  tinyint(3) unsigned not null default 0 comment '0为未删除，1为已删除',
    primary key (`id`) using btree,
    index `_blog_collection_user_1` (`user_id`) using btree,
    index `index_blog_collection_blog_1` (`blog_id`) using btree
) engine = InnoDB
  auto_increment = 0
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for column
-- ----------------------------
drop table if exists `column`;
create table `column`
(
    `id` int(11) not null comment '专栏id',
    primary key (`id`) using btree
) engine = InnoDB
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for config
-- ----------------------------
drop table if exists `config`;
create table `config`
(
    `id`     int(10) unsigned                                                     not null auto_increment,
    `param`  varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` null default null comment '参数',
    `result` varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` null default null comment '值',
    primary key (`id`) using btree
) engine = InnoDB
  auto_increment = 1
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for like_blog
-- ----------------------------
drop table if exists `like_blog`;
create table `like_blog`
(
    `id`          int(10) unsigned    not null auto_increment comment '点赞id',
    `blog_id`     int(10) unsigned    not null comment '博客id',
    `user_id`     int(10) unsigned    not null comment '用户id',
    `create_time` datetime            not null default current_timestamp on update current_timestamp comment '创建时间',
    `is_deleted`  tinyint(3) unsigned not null default 0 comment '0为未删除，1为已删除',
    primary key (`id`) using btree,
    index `index_blog_likes_user_1` (`user_id`) using btree,
    index `index_blog_likes_blog_1` (`blog_id`) using btree
) engine = InnoDB
  auto_increment = 1
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
drop table if exists `tag`;
create table `tag`
(
    `id`            int(10) unsigned                                                     not null auto_increment,
    `name`          varchar(20) character set `utf8` collate `utf8_general_mysql500_ci`  not null,
    `alias`         varchar(20) character set `utf8` collate `utf8_general_mysql500_ci`  null     default null,
    `description`   varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` null     default null,
    `create_time`   datetime                                                             not null default current_timestamp,
    `modified_time` datetime                                                             null     default null on update current_timestamp,
    `is_deleted`    tinyint(3) unsigned                                                  not null default 0 comment '0为未删除，1为已删除',
    primary key (`id`) using btree
) engine = InnoDB
  auto_increment = 1
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- View structure for blog_view
-- ----------------------------
drop view if exists `blog_view`;
create algorithm = undefined sql security definer view `blog_view` as
select `blog`.`id`                                     as `id`,
       `blog`.`author_id`                              as `author_id`,
       `blog`.`title`                                  as `title`,
       `blog`.`description`                            as `description`,
       `blog`.`school_code`                            as `school_code`,
       concat(`config`.`result`, `blog`.`cover_image`) as `cover_image`,
       `blog`.`create_time`                            as `create_time`,
       `blog`.`release_time`                           as `release_time`,
       `blog`.`modified_time`                          as `modified_time`,
       `blog`.`status`                                 as `status`,
       `blog`.`write_type`                             as `write_type`,
       `blog_general`.`view_num`                       as `view_num`,
       `blog_general`.`like_num`                       as `like_num`,
       `blog_general`.`comment_num`                    as `comment_num`,
       `blog_general`.`collection_num`                 as `collection_num`,
       `blog_general`.`score`                          as `score`
from ((`blog` left join `blog_general` on ((`blog`.`id` = `blog_general`.`blog_id`)))
         left join `config` on ((`config`.`param` = 'cover_url')))
where (`blog`.`is_deleted` = 0);

set FOREIGN_KEY_CHECKS = 1;
