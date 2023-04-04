/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : love-pig

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 22/06/2021 09:05:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for love_promise
-- ----------------------------
DROP TABLE IF EXISTS `love_promise`;
CREATE TABLE `love_promise`
(
    `id`              bigint(20)                                              NOT NULL AUTO_INCREMENT,
    `love_id`         bigint(20)                                              NOT NULL COMMENT '恋爱id',
    `promise_name`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '承诺的标题',
    `promise_user_id` bigint(20)                                              NOT NULL COMMENT '承诺人',
    `finish_user_id`  bigint(20)                                              NULL DEFAULT NULL COMMENT '完成者',
    `promise_type`    tinyint(1)                                              NOT NULL COMMENT '承诺类型1承诺 2纪念日',
    `promise_state`   tinyint(1)                                              NOT NULL COMMENT '承诺状态0开始，1完成，2延期，3取消，4删除',
    `promise_time`    datetime                                                NOT NULL COMMENT '承诺开始时间',
    `finish_time`     datetime                                                NOT NULL COMMENT '承诺完成时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of love_promise
-- ----------------------------

-- ----------------------------
-- Table structure for love_resources
-- ----------------------------
DROP TABLE IF EXISTS `love_resources`;
CREATE TABLE `love_resources`
(
    `id`            bigint(20)                                              NOT NULL AUTO_INCREMENT,
    `love_id`       bigint(20)                                              NOT NULL COMMENT '恋爱id',
    `entity_type`   tinyint(4)                                              NULL DEFAULT 1 COMMENT '归属类型，story=1，lovestart=2',
    `entity_id`     bigint(20)                                              NULL DEFAULT NULL COMMENT '归属id，如故事id',
    `resource_type` tinyint(1)                                              NULL DEFAULT 0 COMMENT '资源类型0照片 1视频 2其他',
    `user_id`       bigint(11)                                              NULL DEFAULT NULL COMMENT '上传的用户',
    `state`         tinyint(1)                                              NULL DEFAULT 1 COMMENT '资源状态1.正常0.删除',
    `create_time`   datetime                                                NULL DEFAULT CURRENT_TIMESTAMP,
    `resource_url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源地址',
    `resource_size` double                                                  NULL DEFAULT NULL COMMENT '资源大小',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of love_resources
-- ----------------------------
INSERT INTO `love_resources`
VALUES (1, 521, 1, 3, 0, 1, 1, '2021-06-19 18:29:10',
        'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=343682999,1827287392&fm=26&gp=0.jpg', 333);
INSERT INTO `love_resources`
VALUES (2, 521, 1, 3, 0, 1, 1, '2021-06-19 18:29:10',
        'https://img2.woyaogexing.com/2021/06/03/42ccdc9b977942009f548f484efecb5e.jpeg', 222);
INSERT INTO `love_resources`
VALUES (3, 521, 1, 3, 0, 1, 1, '2021-06-19 18:29:10', 'https://scpic.chinaz.net/files/pic/pic9/202106/apic33360.jpg',
        222);
INSERT INTO `love_resources`
VALUES (4, 521, 1, 4, 0, 1, 1, '2021-06-19 18:29:10', 'https://scpic.chinaz.net/files/pic/pic9/202106/apic33360.jpg',
        222);

-- ----------------------------
-- Table structure for love_start
-- ----------------------------
DROP TABLE IF EXISTS `love_start`;
CREATE TABLE `love_start`
(
    `id`             bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '恋爱空间id',
    `love_name`      varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '空间名称',
    `invite_user`    bigint(20)                                              NOT NULL COMMENT '发起者',
    `accepted_user`  bigint(20)                                              NULL DEFAULT NULL COMMENT '接收者',
    `invite_time`    datetime                                                NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发起时间',
    `accepted_time`  datetime                                                NULL DEFAULT NULL COMMENT '接受时间',
    `separated_time` datetime                                                NULL DEFAULT NULL COMMENT '分手时间',
    `allow_access`   tinyint(1)                                              NULL DEFAULT 1 COMMENT '是否允许访问0拒绝1允许',
    `love_type`      tinyint(1)                                              NULL DEFAULT 0 COMMENT '恋爱状态0.发起1.同意2.拒绝5.解除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of love_start
-- ----------------------------
INSERT INTO `love_start`
VALUES (521, '爱猪猪', 1, 2, '2021-06-19 17:41:00', '2021-06-19 17:41:02', NULL, 1, 1);

-- ----------------------------
-- Table structure for love_story
-- ----------------------------
DROP TABLE IF EXISTS `love_story`;
CREATE TABLE `love_story`
(
    `id`            bigint(20)                                              NOT NULL AUTO_INCREMENT,
    `love_id`       bigint(20)                                              NOT NULL COMMENT '恋爱id',
    `user_id`       bigint(20)                                              NOT NULL COMMENT '发布人',
    `story_type`    tinyint(1)                                              NOT NULL DEFAULT 1 COMMENT '故事类型1.动态2.文章3.大图文章4.承诺5.视频6.相册',
    `title`         varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '故事标题（文章使用）',
    `banner`        varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '头图地址（文章或者承诺使用）',
    `summary`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '故事摘要',
    `content`       text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '故事内容',
    `comment_state` tinyint(1)                                              NULL     DEFAULT 1 COMMENT '是否开放评论0.不开启 1.开启',
    `comment_num`   int(11)                                                 NULL     DEFAULT 0 COMMENT '评论数量',
    `like_num`      int(11)                                                 NULL     DEFAULT 0 COMMENT '点赞数量',
    `location`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '发布的位置，经纬度',
    `weather`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '天气',
    `story_time`    datetime                                                NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '故事发生时间',
    `publish_Time`  datetime                                                NULL     DEFAULT NULL COMMENT '发布时间',
    `state`         tinyint(1)                                              NULL     DEFAULT 0 COMMENT '故事状态0.待发布1.正常2.隐藏3.删除',
    `create_time`   datetime                                                NULL     DEFAULT CURRENT_TIMESTAMP,
    `modify_time`   datetime                                                NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of love_story
-- ----------------------------
INSERT INTO `love_story`
VALUES (3, 521, 1, 1, '上海之恋', NULL,
        '从素颜到淡妆，从没牌子到有牌子，从沐浴露的味道到香水的味道，从帆布鞋到高跟鞋；从爸爸满头的黑发到渐渐出现的白发，从妈妈光滑嫩白的皮肤到眼角的皱纹，从爷爷笔直的身子到佝偻的背，从你天真的脸庞到所谓大人的模样。我知道我们都回不去了。',
        '从素颜到淡妆，从没牌子到有牌子，从沐浴露的味道到香水的味道，从帆布鞋到高跟鞋；从爸爸满头的黑发到渐渐出现的白发，从妈妈光滑嫩白的皮肤到眼角的皱纹，从爷爷笔直的身子到佝偻的背，从你天真的脸庞到所谓大人的模样。我知道我们都回不去了。',
        1, 0, 0, '上海.外滩', '多云', '2021-06-19 18:16:13', '2021-06-19 18:09:09', 1, '2021-06-19 18:09:19',
        '2021-06-19 18:09:20');
INSERT INTO `love_story`
VALUES (4, 521, 1, 1, '上海之恋1', NULL,
        '从素颜到淡妆，从没牌子到有牌子，从沐浴露的味道到香水的味道，从帆布鞋到高跟鞋；从爸爸满头的黑发到渐渐出现的白发，从妈妈光滑嫩白的皮肤到眼角的皱纹，从爷爷笔直的身子到佝偻的背，从你天真的脸庞到所谓大人的模样。我知道我们都回不去了。',
        '从素颜到淡妆，从没牌子到有牌子，从沐浴露的味道到香水的味道，从帆布鞋到高跟鞋；从爸爸满头的黑发到渐渐出现的白发，从妈妈光滑嫩白的皮肤到眼角的皱纹，从爷爷笔直的身子到佝偻的背，从你天真的脸庞到所谓大人的模样。我知道我们都回不去了。',
        1, 0, 0, '上海.外滩', '多云', '2021-06-19 18:16:23', '2021-06-19 18:09:09', 1, '2021-06-19 18:09:19',
        '2021-06-19 18:09:20');

-- ----------------------------
-- Table structure for user_base
-- ----------------------------
DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT,
    `username`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    `gender`      tinyint(1)                                              NOT NULL DEFAULT 0 COMMENT '性别0，未设置，1男，2女',
    `nickname`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '昵称',
    `avatar`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '头像',
    `signature`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '个性签名',
    `city`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '城市',
    `spouse_id`   bigint(20)                                              NULL     DEFAULT NULL COMMENT '配偶用户id0代表没有',
    `state`       tinyint(1)                                              NULL     DEFAULT 0 COMMENT '用户状态0正常',
    `create_time` datetime                                                NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time` datetime                                                NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_base
-- ----------------------------
INSERT INTO `user_base`
VALUES (1, 'joke', 1, '大宝贝', 'https://img2.woyaogexing.com/2021/06/19/cdc6ff7e8c89440aa5163a845394351e!400x400.jpeg',
        NULL, NULL, 2, 1, '2021-06-19 17:39:59', '2021-06-19 17:40:01');
INSERT INTO `user_base`
VALUES (2, 'moon', 2, '小猪姑娘', 'https://img2.woyaogexing.com/2021/06/19/ca6991f0fe604794864e050cd8018745!400x400.jpeg',
        NULL, NULL, 1, 1, '2021-06-19 17:40:01', '2021-06-19 17:40:02');

-- ----------------------------
-- Table structure for user_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_comment`;
CREATE TABLE `user_comment`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT,
    `love_id`     bigint(20)                                              NULL     DEFAULT NULL COMMENT '恋爱id',
    `user_id`     bigint(20)                                              NOT NULL COMMENT '用户id',
    `entity_type` tinyint(1)                                              NOT NULL DEFAULT 1 COMMENT '评论类型1:帖子 2:用户 ',
    `entity_id`   bigint(20)                                              NOT NULL COMMENT '具体那个故事或者用户id',
    `target_id`   bigint(20)                                              NOT NULL DEFAULT 0 COMMENT '嵌套回复时回复的目标',
    `content`     text CHARACTER SET utf8 COLLATE utf8_general_ci         NOT NULL COMMENT '内容',
    `image`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '评论附加的一张图片',
    `state`       tinyint(1)                                              NOT NULL DEFAULT 1 COMMENT '状态',
    `create_time` datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_user_id` (`user_id`) USING BTREE,
    INDEX `index_entity_id` (`entity_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_comment
-- ----------------------------

-- ----------------------------
-- Table structure for user_login
-- ----------------------------
DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login`
(
    `id`       bigint(20)                                              NOT NULL AUTO_INCREMENT,
    `user_id`  bigint(20)                                              NULL DEFAULT NULL COMMENT '扩展信息所属的userinfo',
    `username` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
    `phone`    char(13) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '手机号',
    `email`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `openid`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信用户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_login
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
