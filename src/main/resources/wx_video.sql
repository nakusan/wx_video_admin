/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : wx_video

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 28/09/2024
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
    `id` BIGINT AUTO_INCREMENT,
    `openid` varchar(50) NOT NULL UNIQUE COMMENT 'openid',
    `session_key` varchar(100) NOT NULL COMMENT 'session_key',
    `face_image` varchar(255) NULL DEFAULT NULL COMMENT '头像',
    `nickname` varchar(100) NULL DEFAULT NULL COMMENT '昵称',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX (`openid`) USING BTREE,
    INDEX `idx_nickname` (`nickname`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users`(openid, session_key, face_image, nickname, create_time, update_time)
VALUES ('test-openid-1111', 'session_key_111', '/path000999', 'TEST达人一号', '2024-09-28 16:05:00', '2024-09-28 16:05:00');
INSERT INTO `users`(openid, session_key, face_image, nickname, create_time, update_time)
VALUES ('test-openid-1112', 'session_key_112', '/path000999', 'TEST达人二号', '2024-09-28 16:05:00', '2024-09-28 16:05:00');
INSERT INTO `users`(openid, session_key, face_image, nickname, create_time, update_time)
VALUES ('test-openid-1113', 'session_key_113', '/180425CFA4RB6T0H/face/d038c3c8200b4a.jpg', 'TEST达人三号~', '2024-09-28 16:05:00', '2024-09-28 16:05:00');
INSERT INTO `users`(openid, session_key, face_image, nickname, create_time, update_time)
VALUES ('test-openid-1114', 'session_key_114', '/180425CFA4RB6T0H/face/d038c3c8200b4a.jpg', 'TEST达人四号~', '2024-09-28 16:05:00', '2024-09-28 16:05:00');
INSERT INTO `users`(openid, session_key, face_image, nickname, create_time, update_time)
VALUES ('test-openid-1115', 'session_key_115',  NULL, 'TEST达人五号', '2024-09-28 16:05:00', '2024-09-28 16:05:00');
INSERT INTO `users`(openid, session_key, face_image, nickname, create_time, update_time)
VALUES ('test-openid-1116', 'session_key_116',  NULL, 'TEST达人六号', '2024-09-28 16:05:00', '2024-09-28 16:05:00');

-- ----------------------------
-- Table structure for users_buy_videos
-- ----------------------------
DROP TABLE IF EXISTS `users_buy_videos`;
CREATE TABLE `users_buy_videos`  (
    `id` BIGINT AUTO_INCREMENT,
    `openid` varchar(50) NOT NULL COMMENT '用户',
    `video_id` varchar(64) NOT NULL COMMENT '视频',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `user_video_rel`(`openid`, `video_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户购买的视频' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
    `id` BIGINT AUTO_INCREMENT,
    `category_id` INT NULL DEFAULT NULL COMMENT '类型ID',
    `category_name` VARCHAR(255) NOT NULL COMMENT '类型名',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `category_id` (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category`(category_id, category_name) VALUES ('1', '大数据');
INSERT INTO `category`(category_id, category_name) VALUES ('2', 'AI人工智能');
INSERT INTO `category`(category_id, category_name) VALUES ('3', '会计自动化');
INSERT INTO `category`(category_id, category_name) VALUES ('4', '架构师考试');

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`  (
    `id` BIGINT AUTO_INCREMENT,
    `video_id` VARCHAR(64) NOT NULL COMMENT '视频ID',
    `video_title` VARCHAR(255) NOT NULL COMMENT '视频名',
    `video_desc` TEXT NULL DEFAULT NULL COMMENT '视频描述',
    `video_path` VARCHAR(255) NOT NULL COMMENT '视频存放路径',
    `thumb_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '视频封面图片路径',
    `duration` VARCHAR(20) NULL DEFAULT 0 COMMENT '视频时长',
    `sales_counts` INT NULL DEFAULT 0 COMMENT '卖出的数量',
    `category_id` INT NULL DEFAULT NULL COMMENT '分类ID',
    `price` DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '价格',
    `status` TINYINT(1) NOT NULL DEFAULT 2 COMMENT '视频状态：1=发布成功，2=禁止播放',
    `is_delete` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0=否，1=是',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_video_id` (`video_id`) USING BTREE,
    INDEX `idx_video_title` (`video_title`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
