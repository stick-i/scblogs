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

set names utf8mb4;
set FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blink
-- ----------------------------
drop table if exists `blink`;
create table `blink`
(
    `id`            int(11) unsigned                                          not null auto_increment comment '动态id',
    `user_id`       int(11) unsigned                                          not null comment '用户id',
    `school_code`   int(11) unsigned                                          null     default null comment '学校代码',
    `content`       text character set `utf8mb4` collate `utf8mb4_unicode_ci` not null comment '动态内容',
    `create_time`   datetime                                                  not null default current_timestamp comment '发表时间',
    `modified_time` datetime                                                  not null default current_timestamp on update current_timestamp comment '修改时间',
    `is_deleted`    tinyint(3) unsigned                                       not null default 0 comment '0为未删除，1为已删除',
    primary key (`id`) using btree,
    index `fk_blink_user_id` (`user_id`) using btree
) engine = InnoDB
  auto_increment = 0
  character set = `utf8mb4`
  collate = `utf8mb4_unicode_ci`
  row_format = dynamic;

-- ----------------------------
-- Table structure for blink_general
-- ----------------------------
drop table if exists `blink_general`;
create table `blink_general`
(
    `blink_id`    int(11) unsigned    not null comment '动态id',
    `view_num`    int(11) unsigned    not null default 0 comment '浏览量',
    `likes_num`   int(11) unsigned    not null default 0 comment '点赞量',
    `comment_num` int(11) unsigned    not null default 0 comment '评论量',
    `score`       double unsigned     not null default 0 comment '评分',
    `is_deleted`  tinyint(3) unsigned not null default 0 comment '0为未删除，1为已删除',
    primary key (`blink_id`) using btree
) engine = InnoDB
  character set = `utf8mb4`
  collate = `utf8mb4_unicode_ci`
  row_format = dynamic;

-- ----------------------------
-- View structure for blink_view
-- ----------------------------
drop view if exists `blink_view`;
create algorithm = undefined sql security definer view `blink_view` as
select `blink`.`id`                  as `id`,
       `blink`.`user_id`             as `user_id`,
       `blink`.`school_code`         as `school_code`,
       `blink`.`content`             as `content`,
       `blink`.`create_time`         as `create_time`,
       `blink`.`modified_time`       as `modified_time`,
       `blink_general`.`view_num`    as `view_num`,
       `blink_general`.`likes_num`   as `likes_num`,
       `blink_general`.`comment_num` as `comment_num`,
       `blink_general`.`score`       as `score`
from (`blink`
         left join `blink_general` on ((`blink`.`id` = `blink_general`.`blink_id`)))
where (`blink`.`is_deleted` = 0);

set FOREIGN_KEY_CHECKS = 1;
