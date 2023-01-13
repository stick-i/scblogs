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

set names utf8mb4;
set FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config
-- ----------------------------
drop table if exists `config`;
create table `config`
(
    `id`     int(10) unsigned                                                     not null auto_increment,
    `param`  varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` not null comment '参数',
    `result` varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` not null comment '值',
    primary key (`id`) using btree,
    unique index `param` (`param`) using btree
) engine = InnoDB
  auto_increment = 0
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
drop table if exists `user`;
create table `user`
(
    `id`            int(11) unsigned                                                    not null auto_increment,
    `username`      varchar(20) character set `utf8` collate `utf8_general_mysql500_ci` not null comment '用户名',
    `school_code`   int(11) unsigned                                                    null     default null comment '院校代码',
    `nickname`      varchar(20) character set `utf8` collate `utf8_general_mysql500_ci` null     default null comment '昵称',
    `avatar_url`    varchar(50) character set `utf8` collate `utf8_general_mysql500_ci` null     default null comment '头像链接',
    `register_time` datetime                                                            not null default current_timestamp comment '注册时间',
    `is_deleted`    tinyint(3) unsigned                                                 not null default 0 comment '0为未删除，1为已删除',
    primary key (`id`) using btree,
    unique index `username` (`username`) using btree
) engine = InnoDB
  auto_increment = 0
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for user_basic
-- ----------------------------
drop table if exists `user_basic`;
create table `user_basic`
(
    `username`         varchar(20) character set `utf8` collate `utf8_general_mysql500_ci`  not null comment '用户名',
    `realname`         varchar(20) character set `utf8` collate `utf8_general_mysql500_ci`  null     default null comment '真实姓名',
    `birthday`         datetime                                                             null     default null comment '生日',
    `intro`            varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` null     default null comment '个人简介',
    `gender`           tinyint(1) unsigned                                                  null     default null comment '性别',
    `city_id`          int(11) unsigned                                                     null     default null comment '城市id',
    `province_id`      int(11) unsigned                                                     null     default null comment '省份id',
    `name_modify_time` datetime                                                             null     default null comment '用户名修改时间',
    `start_work_time`  datetime                                                             null     default null comment '开始工作的时间',
    `modified_time`    datetime                                                             not null default current_timestamp on update current_timestamp comment '信息修改时间',
    `is_deleted`       tinyint(3) unsigned                                                  not null default 0 comment '0为未删除，1为已删除',
    primary key (`username`) using btree
) engine = InnoDB
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for user_education
-- ----------------------------
drop table if exists `user_education`;
create table `user_education`
(
    `user_id`             int(11) unsigned                                                     not null comment '用户名',
    `create_time`         datetime                                                             null     default current_timestamp comment '创建时间',
    `school`              varchar(20) character set `utf8` collate `utf8_general_mysql500_ci`  null     default null comment '学校名称',
    `school_id`           int(11) unsigned                                                     null     default null comment '学校id',
    `profession`          varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` null     default null comment '专业',
    `degree`              tinyint(5) unsigned                                                  null     default null comment '学位程度',
    `start_time`          datetime                                                             null     default null comment '入学时间',
    `end_time`            datetime                                                             null     default null comment '毕业时间',
    `audit_img_url`       varchar(50) character set `utf8` collate `utf8_general_mysql500_ci`  null     default null comment '图片链接',
    `safety_audit_status` varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` null     default null comment '安全审核状态',
    `status`              varchar(255) character set `utf8` collate `utf8_general_mysql500_ci` null     default null comment '状态码',
    `modified_time`       datetime                                                             not null default current_timestamp on update current_timestamp comment '更新时间',
    `is_deleted`          tinyint(3) unsigned                                                  not null default 0 comment '0为未删除，1为已删除',
    primary key (`user_id`) using btree
) engine = InnoDB
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for user_follow
-- ----------------------------
drop table if exists `user_follow`;
create table `user_follow`
(
    `id`          int(11) unsigned                                                    not null auto_increment,
    `fans_id`     int(10) unsigned                                                    not null comment '粉丝id',
    `follow_id`   int(10) unsigned                                                    not null comment '关注id',
    `note`        varchar(50) character set `utf8` collate `utf8_general_mysql500_ci` null     default null comment '备注',
    `status`      int(10) unsigned                                                    not null default 1 comment '状态码',
    `create_time` datetime                                                            not null default current_timestamp comment '创建时间',
    `is_deleted`  tinyint(3) unsigned                                                 not null default 0 comment '0为未删除，1为已删除',
    primary key (`id`) using btree,
    index `user_id` (`fans_id`) using btree,
    index `follow_id` (`follow_id`) using btree
) engine = InnoDB
  auto_increment = 0
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for user_general
-- ----------------------------
drop table if exists `user_general`;
create table `user_general`
(
    `id`          int(10) unsigned    not null auto_increment,
    `user_id`     int(11) unsigned    not null comment '用户id',
    `like_num`    int(11) unsigned    not null default 0 comment '用户获赞',
    `fans_num`    int(11) unsigned    not null default 0 comment '用户粉丝数',
    `comment_num` int(11) unsigned    not null default 0 comment '用户评论数',
    `collect_num` int(11) unsigned    not null default 0 comment '收藏博客数量',
    `view_num`    int(11) unsigned    not null default 0 comment '访问数',
    `blog_num`    int(11) unsigned    not null default 0 comment '用户博客数量',
    `week_rank`   int(11) unsigned    null     default null comment '周排行',
    `total_rank`  int(11) unsigned    null     default null comment '总排汗',
    `is_deleted`  tinyint(3) unsigned not null default 0 comment '0为未删除，1为已删除',
    primary key (`id`) using btree,
    unique index `index_user_general_user_id` (`user_id`) using btree
) engine = InnoDB
  auto_increment = 0
  character set = `utf8`
  collate = `utf8_general_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for user_safety
-- ----------------------------
drop table if exists `user_safety`;
create table `user_safety`
(
    `user_id`    int(10) unsigned                                                     not null comment '用户id',
    `username`   varchar(20) character set `utf8` collate `utf8_general_mysql500_ci`  not null comment '用户名',
    `password`   varchar(100) character set `utf8` collate `utf8_general_mysql500_ci` not null comment '密码',
    `mail`       varchar(30) character set `utf8` collate `utf8_general_mysql500_ci`  not null comment '邮箱',
    `mobile`     varchar(11) character set `utf8` collate `utf8_general_mysql500_ci`  null     default null comment '手机号',
    `is_deleted` tinyint(3) unsigned                                                  not null default 0 comment '0为未删除，1为已删除',
    primary key (`user_id`) using btree,
    unique index `email` (`mail`) using btree,
    unique index `mobile` (`mobile`) using btree,
    index `index_user_safety_user_general_1` (`username`) using btree
) engine = InnoDB
  character set = `utf8`
  collate = `utf8_general_mysql500_ci`
  row_format = dynamic;

-- ----------------------------
-- View structure for user_view
-- ----------------------------
drop view if exists `user_view`;
create algorithm = undefined sql security definer view `user_view` as
select `user`.`id`                                    as `id`,
       `user`.`username`                              as `username`,
       `user`.`nickname`                              as `nickname`,
       `user`.`school_code`                           as `school_code`,
       concat(`config`.`result`, `user`.`avatar_url`) as `avatar_url`,
       `user`.`register_time`                         as `register_time`
from (`user`
         left join `config` on ((`config`.`param` = 'avatar_url')))
where (`user`.`is_deleted` = 0);

-- ----------------------------
-- View structure for fans_view
-- ----------------------------
drop view if exists `fans_view`;
create algorithm = undefined sql security definer view `fans_view` as
select `user_follow`.`id`          as `id`,
       `user_follow`.`follow_id`   as `user_id`,
       `user_follow`.`fans_id`     as `fans_id`,
       `user_follow`.`note`        as `note`,
       `user_follow`.`status`      as `status`,
       `user_follow`.`create_time` as `create_time`,
       `user_view`.`username`      as `username`,
       `user_view`.`nickname`      as `nickname`,
       `user_view`.`school_code`   as `school_code`,
       `user_view`.`avatar_url`    as `avatar_url`,
       `user_view`.`register_time` as `register_time`
from (`user_follow`
         left join `user_view` on ((`user_follow`.`fans_id` = `user_view`.`id`)))
where (`user_follow`.`is_deleted` = 0);

-- ----------------------------
-- View structure for follow_view
-- ----------------------------
drop view if exists `follow_view`;
create algorithm = undefined sql security definer view `follow_view` as
select `user_follow`.`id`          as `id`,
       `user_follow`.`fans_id`     as `user_id`,
       `user_follow`.`follow_id`   as `follow_id`,
       `user_follow`.`note`        as `note`,
       `user_follow`.`status`      as `status`,
       `user_follow`.`create_time` as `create_time`,
       `user_view`.`username`      as `username`,
       `user_view`.`nickname`      as `nickname`,
       `user_view`.`school_code`   as `school_code`,
       `user_view`.`avatar_url`    as `avatar_url`,
       `user_view`.`register_time` as `register_time`
from (`user_follow`
         left join `user_view` on ((`user_view`.`id` = `user_follow`.`follow_id`)))
where (`user_follow`.`is_deleted` = 0);

set FOREIGN_KEY_CHECKS = 1;
