/*
 Navicat Premium Data Transfer

 Source Server         : File
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : chiazolone

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 27/03/2023 14:42:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` bigint(20) NOT NULL COMMENT '用户id',
                             `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号（账号）',
                             `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码（展示加密的密码）',
                             `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '游戏昵称',
                             `head_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
                             `ip_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ip地址',
                             `number` int(18) NULL DEFAULT NULL COMMENT '身份证号码',
                             `token` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '令牌',
                             `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态（1：正常，2：冻结，3：注销）',
                             `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间（注册时间）',
                             `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                             `is_deleted` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记（0:可用 1:已删除）',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (-9584777216, '1101211', 'EiVp3pCJDxEqgoQnTx2qWg==', NULL, NULL, NULL, NULL, NULL, '1', '2023-03-27 13:42:29', '2023-03-27 13:42:29', '0');
INSERT INTO `sys_user` VALUES (-4354220032, '11012001', 'EiVp3pCJDxEqgoQnTx2qWg==', NULL, NULL, NULL, NULL, '', '1', '2023-03-27 13:42:24', '2023-03-27 13:48:52', '0');
INSERT INTO `sys_user` VALUES (1, '15565139513', 'yDAvvAf/r2N6aLhoVpTKyA==', '张一', NULL, NULL, 1, '00000187-21c3-d430-5da8-d3f56c05bd70-192.168.186.1', '1', '2023-03-27 09:36:00', '2023-03-27 14:31:35', '0');
INSERT INTO `sys_user` VALUES (3, '15039026266', 'pfwndlH4hV+oy9LtWuTt/Q==', '张二', NULL, NULL, 1, '', '1', '2023-03-08 11:26:18', '2023-03-08 15:49:11', '0');
INSERT INTO `sys_user` VALUES (4, '15039026267', 'yDAvvAf/r2N6aLhoVpTKyA==', '张三', NULL, NULL, 1, '642ef880-08f0-4d83-9d55-55a8a16760a7', '1', '2023-03-08 11:26:20', '2023-03-08 11:26:20', '0');
INSERT INTO `sys_user` VALUES (7, '110120130', 'yDAvvAf/r2N6aLhoVpTKyA==', '', '', '', 1, '00000186-bff8-fd71-aca7-eedfd9f02428-192.168.186.1', '1', '2023-03-27 09:29:33', '2023-03-27 09:29:33', '');
INSERT INTO `sys_user` VALUES (748831744, '98765000', 'EiVp3pCJDxEqgoQnTx2qWg==', NULL, NULL, NULL, NULL, NULL, '1', '2023-03-27 14:19:28', '2023-03-27 14:19:28', '0');
INSERT INTO `sys_user` VALUES (2524466176, '11012000', 'EiVp3pCJDxEqgoQnTx2qWg==', '', '', '', 0, '', '1', '2023-03-27 13:45:43', '2023-03-27 13:45:43', '');
INSERT INTO `sys_user` VALUES (9796230144, '1234666', 'EiVp3pCJDxEqgoQnTx2qWg==', NULL, NULL, NULL, NULL, NULL, '1', '2023-03-27 13:45:33', '2023-03-27 13:45:33', '0');
INSERT INTO `sys_user` VALUES (1631914520159858689, '11111111', 'yDAvvAf/r2N6aLhoVpTKyA==', '', '', '', 1, '00000186-bf3c-f560-d526-46180a7a421d-192.168.186.1', '1', '2023-03-27 09:29:36', '2023-03-27 09:29:36', '');

SET FOREIGN_KEY_CHECKS = 1;
