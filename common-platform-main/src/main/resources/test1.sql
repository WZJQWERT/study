/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : test1

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 03/12/2020 09:26:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性编码标识',
  `dict_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否是字典中的值',
  `dict_type_id` bigint(0) NULL DEFAULT NULL COMMENT '字典类型的编码',
  `value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性值，如果是字典中的类型，则为dict的code',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1143324237579165697, '验证码开关', 'COMMON_PLATFORM_KAPTCHA_OPEN', 'Y', 1106120265689055233, 'DISABLE', '是否开启验证码', '2020-03-20 12:46:43', 1, '2019-06-25 09:04:42', 1);
INSERT INTO `sys_config` VALUES (1143468689664876546, '管理系统名称', 'COMMON_PLATFORM_SYSTEM_NAME', 'N', NULL, '通用快速开发平台', '管理系统名称', '2020-03-20 18:39:15', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (1143468867767607297, '默认系统密码', 'COMMON_PLATFORM_DEFAULT_PASSWORD', 'N', NULL, '111111', '默认系统密码', '2020-03-20 18:39:57', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (1145915627211370498, '发布的编号', 'COMMON_PLATFORM_SYSTEM_RELEASE_VERSION', 'N', NULL, '20200320', '用于防止浏览器缓存相关的js和css', '2020-03-20 12:42:30', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (1145915627211370499, '文件上传路径', 'COMMON_PLATFORM_FILE_UPLOAD_PATH', 'N', NULL, '/Users/commonplatform/tmp/tempFiles/', '文件上传默认目录', '2020-03-20 09:10:40', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (1145915627211370501, '获取系统地密钥过期时间', 'COMMON_PLATFORM_JWT_SECRET_EXPIRE', 'N', NULL, '86400', '获取系统地密钥过期时间（单位：秒），默认1天', '2020-03-20 23:02:39', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (1145915627211370502, '获取token的header标识', 'COMMON_PLATFORM_TOKEN_HEADER_NAME', 'N', NULL, 'Authorization', '获取token的header标识', '2020-03-20 23:02:39', 1, NULL, NULL);
INSERT INTO `sys_config` VALUES (1240857329922818050, '获取系统的密钥', 'COMMON_PLATFORM_JWT_SECRET', 'N', 1160532704105742300, 'common_platform', '获取系统的密钥', '2020-03-20 04:26:56', 1, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(0) NOT NULL COMMENT '主键id',
  `pid` bigint(0) NULL DEFAULT 0 COMMENT '父部门id',
  `pids` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '父级ids',
  `simple_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (24, 0, '[0],', '总公司', '总公司', '', NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (25, 24, '[0],[24],', '开发部', '开发部', '', NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (26, 24, '[0],[24],', '运营部', '运营部', '', NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (1244895938384588801, 24, '[0],[24],', '市场部', '市场部', '市场部', NULL, 3, '2020-03-31 07:54:55', NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `dict_id` bigint(0) NOT NULL COMMENT '字典id',
  `dict_type_id` bigint(0) NOT NULL COMMENT '所属字典类型的id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `parent_id` bigint(0) NOT NULL COMMENT '上级代码id',
  `parent_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有上级id',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'ENABLE' COMMENT '状态（字典）',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典的描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '基础字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1106120532442595330, 1106120208097067009, 'M', '男', 0, '[0]', 'ENABLE', NULL, '', '2020-03-14 17:11:00', NULL, 1, NULL);
INSERT INTO `sys_dict` VALUES (1106120574163337218, 1106120208097067009, 'F', '女', 0, '[0]', 'ENABLE', NULL, '', '2020-03-14 17:11:10', NULL, 1, NULL);
INSERT INTO `sys_dict` VALUES (1106120645697191938, 1106120265689055233, 'ENABLE', '启用', 0, '[0]', 'ENABLE', NULL, '', '2020-03-14 17:11:27', NULL, 1, NULL);
INSERT INTO `sys_dict` VALUES (1106120699468169217, 1106120265689055233, 'DISABLE', '禁用', 0, '[0]', 'ENABLE', NULL, '', '2020-03-14 17:11:40', NULL, 1, NULL);
INSERT INTO `sys_dict` VALUES (1106120784318939137, 1106120322450571266, 'ENABLE', '启用', 0, '[0]', 'ENABLE', NULL, '', '2020-03-14 17:12:00', NULL, 1, NULL);
INSERT INTO `sys_dict` VALUES (1106120825993543682, 1106120322450571266, 'FREEZE', '冻结', 0, '[0]', 'ENABLE', 1, '', '2020-03-14 17:12:10', '2020-03-11 10:56:36', 1, 1);
INSERT INTO `sys_dict` VALUES (1106120875872206849, 1106120322450571266, 'DELETED', '已删除', 0, '[0]', 'ENABLE', -1221, '', '2020-03-14 17:12:22', '2020-03-11 10:56:53', 1, 1);
INSERT INTO `sys_dict` VALUES (1106120935070613505, 1106120388036902914, 'Y', '删除', 0, '[0]', 'ENABLE', 23333, '', '2020-03-14 17:12:36', '2020-03-11 10:58:53', 1, 1);
INSERT INTO `sys_dict` VALUES (1106120968910258177, 1106120388036902914, 'N', '未删除', 0, '[0]', 'ENABLE', 1212211221, '', '2020-03-14 17:12:44', '2020-03-11 10:59:03', 1, 1);
INSERT INTO `sys_dict` VALUES (1149218674746355713, 1149217131989069826, 'BASE_SYSTEM', '系统管理', 0, '[0]', 'ENABLE', 1, '系统管理平台', '2020-03-11 15:27:38', '2020-03-11 20:27:14', 1, 1);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_type_id` bigint(0) NOT NULL COMMENT '字典类型id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型名称',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典描述',
  `system_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否是系统字典，Y-是，N-否',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'ENABLE' COMMENT '状态(字典)',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`dict_type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1106120208097067009, 'SEX', '性别', '', 'Y', 'ENABLE', 4, '2020-03-14 17:09:43', 1, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1106120265689055233, 'STATUS', '状态', '', 'Y', 'ENABLE', 3, '2020-03-14 17:09:57', 1, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1106120322450571266, 'ACCOUNT_STATUS', '账号状态', '', 'Y', 'ENABLE', 40, '2020-03-14 17:10:10', 1, '2020-03-11 20:46:38', 1);
INSERT INTO `sys_dict_type` VALUES (1106120388036902914, 'DEL_FLAG', '是否删除', '', 'Y', 'ENABLE', 2, '2020-03-14 17:10:26', 1, '2020-03-11 16:26:31', 1);
INSERT INTO `sys_dict_type` VALUES (1149217131989069826, 'SYSTEM_TYPE', '系统分类', '系统所有分类的维护', 'Y', 'ENABLE', 50, '2020-03-11 15:21:30', 1, '2020-03-11 20:46:47', 1);

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info`  (
  `file_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `file_bucket` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件仓库（oss仓库）',
  `file_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `file_suffix` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  `file_size_kb` bigint(0) NULL DEFAULT NULL COMMENT '文件大小kb',
  `final_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件唯一标识id',
  `file_path` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储路径',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `login_log_id` bigint(0) NOT NULL COMMENT '主键',
  `log_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志名称',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '管理员id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否执行成功',
  `message` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '具体消息',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`login_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (1331103765673029634, '登录日志', 1, '2020-11-24 05:14:03', '成功', NULL, '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL COMMENT '主键id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url地址',
  `sort` int(0) NULL DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(0) NULL DEFAULT NULL COMMENT '菜单层级',
  `menu_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否是菜单(字典)',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'ENABLE' COMMENT '菜单状态(字典)',
  `new_page_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打开新页面的标识(字典)',
  `open_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否打开(字典)',
  `system_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统分类(字典)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (105, 'system', '0', '[0],', '系统管理', 'layui-icon layui-icon-set', '#', 20, 1, 'Y', NULL, 'ENABLE', NULL, '1', 'BASE_SYSTEM', NULL, '2019-03-29 16:32:27', NULL, 1);
INSERT INTO `sys_menu` VALUES (106, 'mgr', 'system', '[0],[system],', '用户管理', '', '/mgr', 10, 2, 'Y', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (107, 'mgr_add', 'mgr', '[0],[system],[mgr],', '添加用户', NULL, '/mgr/add', 1, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (108, 'mgr_edit', 'mgr', '[0],[system],[mgr],', '修改用户', NULL, '/mgr/edit', 2, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (109, 'mgr_delete', 'mgr', '[0],[system],[mgr],', '删除用户', NULL, '/mgr/delete', 3, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (110, 'mgr_reset', 'mgr', '[0],[system],[mgr],', '重置密码', NULL, '/mgr/reset', 4, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (111, 'mgr_freeze', 'mgr', '[0],[system],[mgr],', '冻结用户', NULL, '/mgr/freeze', 5, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (112, 'mgr_unfreeze', 'mgr', '[0],[system],[mgr],', '解除冻结用户', NULL, '/mgr/unfreeze', 6, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (113, 'mgr_setRole', 'mgr', '[0],[system],[mgr],', '分配角色', NULL, '/mgr/setRole', 7, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (114, 'role', 'system', '[0],[system],', '角色管理', '', '/role', 20, 2, 'Y', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (115, 'role_add', 'role', '[0],[system],[role],', '添加角色', NULL, '/role/add', 1, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (116, 'role_edit', 'role', '[0],[system],[role],', '修改角色', NULL, '/role/edit', 2, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (117, 'role_remove', 'role', '[0],[system],[role],', '删除角色', NULL, '/role/remove', 3, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (118, 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', NULL, '/role/setAuthority', 4, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (119, 'menu', 'system', '[0],[system],', '菜单管理', '', '/menu', 50, 2, 'Y', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:25', NULL, 1);
INSERT INTO `sys_menu` VALUES (120, 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', NULL, '/menu/add', 1, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:25', NULL, 1);
INSERT INTO `sys_menu` VALUES (121, 'menu_edit', 'menu', '[0],[system],[menu],', '修改菜单', NULL, '/menu/edit', 2, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:25', NULL, 1);
INSERT INTO `sys_menu` VALUES (122, 'menu_remove', 'menu', '[0],[system],[menu],', '删除菜单', NULL, '/menu/remove', 3, 3, 'N', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:25', NULL, 1);
INSERT INTO `sys_menu` VALUES (128, 'log', 'system', '[0],[system],', '业务日志', '', '/log', 70, 2, 'Y', NULL, 'ENABLE', NULL, '0', 'BASE_SYSTEM', NULL, '2019-06-30 13:48:39', NULL, 1);
INSERT INTO `sys_menu` VALUES (130, 'druid', 'system', '[0],[system],', '监控管理', '', '/druid', 80, 2, 'Y', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:50:06', NULL, 1);
INSERT INTO `sys_menu` VALUES (131, 'dept', 'system', '[0],[system],', '部门管理', '', '/dept', 30, 2, 'Y', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:53', NULL, 1);
INSERT INTO `sys_menu` VALUES (132, 'dict', 'system', '[0],[system],', '字典管理', '', '/dictType', 40, 2, 'Y', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:04', NULL, 1);
INSERT INTO `sys_menu` VALUES (133, 'loginLog', 'system', '[0],[system],', '登录日志', '', '/loginLog', 60, 2, 'Y', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:29', NULL, 1);
INSERT INTO `sys_menu` VALUES (134, 'log_clean', 'log', '[0],[system],[log],', '清空日志', NULL, '/log/delLog', 3, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:39', NULL, 1);
INSERT INTO `sys_menu` VALUES (135, 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', NULL, '/dept/add', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:53', NULL, 1);
INSERT INTO `sys_menu` VALUES (136, 'dept_update', 'dept', '[0],[system],[dept],', '修改部门', NULL, '/dept/update', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:53', NULL, 1);
INSERT INTO `sys_menu` VALUES (137, 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', NULL, '/dept/delete', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:53', NULL, 1);
INSERT INTO `sys_menu` VALUES (138, 'dict_add', 'dict', '[0],[system],[dict],', '添加字典', NULL, '/dictType/addItem', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:04', NULL, 1);
INSERT INTO `sys_menu` VALUES (139, 'dict_update', 'dict', '[0],[system],[dict],', '修改字典', NULL, '/dictType/editItem', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:04', NULL, 1);
INSERT INTO `sys_menu` VALUES (140, 'dict_delete', 'dict', '[0],[system],[dict],', '删除字典', NULL, '/dictType/delete', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:04', NULL, 1);
INSERT INTO `sys_menu` VALUES (150, 'to_menu_edit', 'menu', '[0],[system],[menu],', '菜单编辑跳转', '', '/menu/menu_edit', 4, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:25', NULL, 1);
INSERT INTO `sys_menu` VALUES (151, 'menu_list', 'menu', '[0],[system],[menu],', '菜单列表', '', '/menu/list', 5, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:25', NULL, 1);
INSERT INTO `sys_menu` VALUES (152, 'to_dept_update', 'dept', '[0],[system],[dept],', '修改部门跳转', '', '/dept/dept_update', 4, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:53', NULL, 1);
INSERT INTO `sys_menu` VALUES (153, 'dept_list', 'dept', '[0],[system],[dept],', '部门列表', '', '/dept/list', 5, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:53', NULL, 1);
INSERT INTO `sys_menu` VALUES (154, 'dept_detail', 'dept', '[0],[system],[dept],', '部门详情', '', '/dept/detail', 6, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:53', NULL, 1);
INSERT INTO `sys_menu` VALUES (155, 'to_dict_edit', 'dict', '[0],[system],[dict],', '修改菜单跳转', '', '/dict/dict_edit', 4, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:04', NULL, 1);
INSERT INTO `sys_menu` VALUES (156, 'dict_list', 'dict', '[0],[system],[dict],', '字典列表', '', '/dict/list', 5, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:04', NULL, 1);
INSERT INTO `sys_menu` VALUES (157, 'dict_detail', 'dict', '[0],[system],[dict],', '字典详情', '', '/dict/detail', 6, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:04', NULL, 1);
INSERT INTO `sys_menu` VALUES (158, 'log_list', 'log', '[0],[system],[log],', '日志列表', '', '/log/list', 2, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:39', NULL, 1);
INSERT INTO `sys_menu` VALUES (159, 'log_detail', 'log', '[0],[system],[log],', '日志详情', '', '/log/detail', 3, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:39', NULL, 1);
INSERT INTO `sys_menu` VALUES (160, 'del_login_log', 'loginLog', '[0],[system],[loginLog],', '清空登录日志', '', '/loginLog/delLoginLog', 1, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:29', NULL, 1);
INSERT INTO `sys_menu` VALUES (161, 'login_log_list', 'loginLog', '[0],[system],[loginLog],', '登录日志列表', '', '/loginLog/list', 2, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:49:29', NULL, 1);
INSERT INTO `sys_menu` VALUES (162, 'to_role_edit', 'role', '[0],[system],[role],', '修改角色跳转', '', '/role/role_edit', 5, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (163, 'to_role_assign', 'role', '[0],[system],[role],', '角色分配跳转', '', '/role/role_assign', 6, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (164, 'role_list', 'role', '[0],[system],[role],', '角色列表', '', '/role/list', 7, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (165, 'to_assign_role', 'mgr', '[0],[system],[mgr],', '分配角色跳转', '', '/mgr/role_assign', 8, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (166, 'to_user_edit', 'mgr', '[0],[system],[mgr],', '编辑用户跳转', '', '/mgr/user_edit', 9, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (167, 'mgr_list', 'mgr', '[0],[system],[mgr],', '用户列表', '', '/mgr/list', 10, 3, 'N', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-06-30 13:48:07', NULL, 1);
INSERT INTO `sys_menu` VALUES (172, 'dashboard', '0', '[0],', '主控面板', 'layui-icon layui-icon-home', '#', 10, 1, 'Y', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', NULL, '2019-04-08 22:48:15', NULL, 1);
INSERT INTO `sys_menu` VALUES (1139826657964593154, 'meta_data', '0', '[0],', '系统配置', 'layui-icon-app', '#', 15, 1, 'Y', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', '2019-06-15 17:27:07', '2020-03-31 08:35:31', 1, 1);
INSERT INTO `sys_menu` VALUES (1142957882422112257, 'SYS_CONFIG', 'meta_data', '[0],[meta_data],', '参数配置', 'fa-star', '/sysConfig', 5, 2, 'Y', '', 'ENABLE', '', '', 'BASE_SYSTEM', '2019-06-24 08:49:28', '2020-03-31 08:35:31', 1, 1);
INSERT INTO `sys_menu` VALUES (1142957882422112258, 'SYS_CONFIG_ADD', 'SYS_CONFIG', '[0],[meta_data],[SYS_CONFIG],', '参数配置添加', 'fa-star', '', 999, 3, 'N', '', 'ENABLE', '', '', 'BASE_SYSTEM', '2019-06-24 08:49:28', '2020-03-31 08:35:31', 1, 1);
INSERT INTO `sys_menu` VALUES (1142957882422112259, 'SYS_CONFIG_EDIT', 'SYS_CONFIG', '[0],[meta_data],[SYS_CONFIG],', '参数配置修改', 'fa-star', '', 999, 3, 'N', '', 'ENABLE', '', '', 'BASE_SYSTEM', '2019-06-24 08:49:28', '2020-03-31 08:35:31', 1, 1);
INSERT INTO `sys_menu` VALUES (1142957882426306562, 'SYS_CONFIG_DELETE', 'SYS_CONFIG', '[0],[meta_data],[SYS_CONFIG],', '参数配置删除', 'fa-star', '', 999, 3, 'N', '', 'ENABLE', '', '', 'BASE_SYSTEM', '2019-06-24 08:49:28', '2020-03-31 08:35:31', 1, 1);
INSERT INTO `sys_menu` VALUES (1144441072852684801, 'SYS_POSITION', 'system', '[0],[system],', '职位管理', 'fa-star', '/position', 35, 2, 'Y', '', 'ENABLE', '', '', 'BASE_SYSTEM', '2019-06-28 11:03:09', '2019-06-28 11:06:42', 1, 1);
INSERT INTO `sys_menu` VALUES (1144441072852684802, 'SYS_POSITION_ADD', 'SYS_POSITION', '[0],[system],[SYS_POSITION],', '职位表添加', 'fa-star', '', 999, 3, 'N', '', 'ENABLE', '', '', 'BASE_SYSTEM', '2019-06-28 11:03:09', '2019-06-28 11:06:42', 1, 1);
INSERT INTO `sys_menu` VALUES (1144441072852684803, 'SYS_POSITION_EDIT', 'SYS_POSITION', '[0],[system],[SYS_POSITION],', '职位表修改', 'fa-star', '', 999, 3, 'N', '', 'ENABLE', '', '', 'BASE_SYSTEM', '2019-06-28 11:03:09', '2019-06-28 11:06:42', 1, 1);
INSERT INTO `sys_menu` VALUES (1144441072852684804, 'SYS_POSITION_DELETE', 'SYS_POSITION', '[0],[system],[SYS_POSITION],', '职位表删除', 'fa-star', '', 999, 3, 'N', '', 'ENABLE', '', '', 'BASE_SYSTEM', '2019-06-28 11:03:09', '2019-06-28 11:06:42', 1, 1);
INSERT INTO `sys_menu` VALUES (1149955324929765378, 'system_info', 'dashboard', '[0],[dashboard],', '系统监控', 'layui-icon-star-fill', '/system/systemInfo', 40, 2, 'Y', NULL, 'ENABLE', NULL, NULL, 'BASE_SYSTEM', '2019-07-13 16:14:49', NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `operation_log_id` bigint(0) NOT NULL COMMENT '主键',
  `log_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志类型(字典)',
  `log_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志名称',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类名称',
  `method` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '方法名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否成功(字典)',
  `message` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '备注',
  PRIMARY KEY (`operation_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position`  (
  `position_id` bigint(0) NOT NULL COMMENT '主键id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位名称',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位编码',
  `sort` int(0) NOT NULL COMMENT '顺序',
  `status` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'ENABLE' COMMENT '状态(字典)',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`position_id`) USING BTREE,
  UNIQUE INDEX `CODE_UNI`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '职位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_position
-- ----------------------------
INSERT INTO `sys_position` VALUES (1, '董事长', 'President', 1, 'ENABLE', NULL, '2019-06-27 18:14:43', 1, NULL, NULL);
INSERT INTO `sys_position` VALUES (1244896383404437505, '部门主管', 'dept', 1, 'ENABLE', '部门主管', '2020-03-31 07:56:42', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation`  (
  `relation_id` bigint(0) NOT NULL COMMENT '主键',
  `menu_id` bigint(0) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_relation
-- ----------------------------
INSERT INTO `sys_relation` VALUES (1187549829752950785, 105, 1);
INSERT INTO `sys_relation` VALUES (1187549829794893825, 106, 1);
INSERT INTO `sys_relation` VALUES (1187549829815865345, 107, 1);
INSERT INTO `sys_relation` VALUES (1187549829845225473, 108, 1);
INSERT INTO `sys_relation` VALUES (1187549829874585601, 109, 1);
INSERT INTO `sys_relation` VALUES (1187549829903945729, 110, 1);
INSERT INTO `sys_relation` VALUES (1187549829933305857, 111, 1);
INSERT INTO `sys_relation` VALUES (1187549829962665985, 112, 1);
INSERT INTO `sys_relation` VALUES (1187549829987831810, 113, 1);
INSERT INTO `sys_relation` VALUES (1187549830017191937, 165, 1);
INSERT INTO `sys_relation` VALUES (1187549830042357762, 166, 1);
INSERT INTO `sys_relation` VALUES (1187549830071717890, 167, 1);
INSERT INTO `sys_relation` VALUES (1187549830096883713, 114, 1);
INSERT INTO `sys_relation` VALUES (1187549830126243841, 115, 1);
INSERT INTO `sys_relation` VALUES (1187549830155603970, 116, 1);
INSERT INTO `sys_relation` VALUES (1187549830180769793, 117, 1);
INSERT INTO `sys_relation` VALUES (1187549830210129921, 118, 1);
INSERT INTO `sys_relation` VALUES (1187549830235295745, 162, 1);
INSERT INTO `sys_relation` VALUES (1187549830260461570, 163, 1);
INSERT INTO `sys_relation` VALUES (1187549830285627393, 164, 1);
INSERT INTO `sys_relation` VALUES (1187549830310793217, 119, 1);
INSERT INTO `sys_relation` VALUES (1187549830340153345, 120, 1);
INSERT INTO `sys_relation` VALUES (1187549830365319169, 121, 1);
INSERT INTO `sys_relation` VALUES (1187549830390484993, 122, 1);
INSERT INTO `sys_relation` VALUES (1187549830415650817, 150, 1);
INSERT INTO `sys_relation` VALUES (1187549830440816641, 151, 1);
INSERT INTO `sys_relation` VALUES (1187549830470176769, 128, 1);
INSERT INTO `sys_relation` VALUES (1187549830495342593, 134, 1);
INSERT INTO `sys_relation` VALUES (1187549830516314114, 158, 1);
INSERT INTO `sys_relation` VALUES (1187549830537285634, 159, 1);
INSERT INTO `sys_relation` VALUES (1187549830549868546, 130, 1);
INSERT INTO `sys_relation` VALUES (1187549830566645762, 131, 1);
INSERT INTO `sys_relation` VALUES (1187549830583422978, 135, 1);
INSERT INTO `sys_relation` VALUES (1187549830604394498, 136, 1);
INSERT INTO `sys_relation` VALUES (1187549830616977409, 137, 1);
INSERT INTO `sys_relation` VALUES (1187549830633754626, 152, 1);
INSERT INTO `sys_relation` VALUES (1187549830650531841, 153, 1);
INSERT INTO `sys_relation` VALUES (1187549830663114753, 154, 1);
INSERT INTO `sys_relation` VALUES (1187549830679891969, 132, 1);
INSERT INTO `sys_relation` VALUES (1187549830692474881, 138, 1);
INSERT INTO `sys_relation` VALUES (1187549830709252098, 139, 1);
INSERT INTO `sys_relation` VALUES (1187549830726029313, 140, 1);
INSERT INTO `sys_relation` VALUES (1187549830738612225, 155, 1);
INSERT INTO `sys_relation` VALUES (1187549830763778049, 156, 1);
INSERT INTO `sys_relation` VALUES (1187549830788943874, 157, 1);
INSERT INTO `sys_relation` VALUES (1187549830814109697, 133, 1);
INSERT INTO `sys_relation` VALUES (1187549830843469826, 160, 1);
INSERT INTO `sys_relation` VALUES (1187549830868635649, 161, 1);
INSERT INTO `sys_relation` VALUES (1187549830986076161, 1144441072852684801, 1);
INSERT INTO `sys_relation` VALUES (1187549831011241985, 1144441072852684802, 1);
INSERT INTO `sys_relation` VALUES (1187549831036407809, 1144441072852684803, 1);
INSERT INTO `sys_relation` VALUES (1187549831057379330, 1144441072852684804, 1);
INSERT INTO `sys_relation` VALUES (1187549831137071105, 1139826657964593154, 1);
INSERT INTO `sys_relation` VALUES (1187549831166431234, 1142957882422112257, 1);
INSERT INTO `sys_relation` VALUES (1187549831191597058, 1142957882422112258, 1);
INSERT INTO `sys_relation` VALUES (1187549831220957186, 1142957882422112259, 1);
INSERT INTO `sys_relation` VALUES (1187549831246123009, 1142957882426306562, 1);
INSERT INTO `sys_relation` VALUES (1187549831271288834, 172, 1);
INSERT INTO `sys_relation` VALUES (1187549831330009090, 1111545968697860098, 1);
INSERT INTO `sys_relation` VALUES (1187549831355174913, 1111546189892870145, 1);
INSERT INTO `sys_relation` VALUES (1187549831384535041, 1149955324929765378, 1);
INSERT INTO `sys_relation` VALUES (1244518550769344513, 105, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244518550777733121, 130, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244518550786121730, 133, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244518550790316034, 160, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244518550798704642, 161, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244518550807093250, 1144441072852684801, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244518550815481857, 1144441072852684802, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244518550823870466, 1144441072852684803, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244518550832259074, 1144441072852684804, 1244510890397286402);
INSERT INTO `sys_relation` VALUES (1244895617734242305, 105, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617746825217, 130, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617755213825, 131, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617771991041, 135, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617784573953, 136, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617797156865, 137, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617805545473, 152, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617813934081, 153, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617822322689, 154, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617830711298, 1144441072852684801, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617839099906, 1144441072852684802, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617855877121, 1144441072852684803, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617864265729, 1144441072852684804, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617872654337, 172, 1244895387861217282);
INSERT INTO `sys_relation` VALUES (1244895617881042945, 1149955324929765378, 1244895387861217282);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL COMMENT '主键id',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提示',
  `sort` int(0) NULL DEFAULT NULL COMMENT '序号',
  `version` int(0) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 0, '超级管理员', 'administrator', 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (1244510890397286402, 0, '普通操作员', 'common_user', 2, NULL, '2020-03-30 06:24:53', '2020-03-30 06:54:14', 1, 1);
INSERT INTO `sys_role` VALUES (1244895387861217282, 0, '普通管理员', 'common_admin', 2, NULL, '2020-03-31 07:52:44', NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL COMMENT '主键id',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `account` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `sex` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别(字典)',
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id(多个逗号隔开)',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '部门id(多个逗号隔开)',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态(字典)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `version` int(0) NULL DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '1124606971782160385', 'admin', '17db03c22596b7609c7c9704f16663e0', 'abcdef', 'admin', '2020-03-15 16:00:00', 'M', 'admin@qq.com', '16600000000', '1', 25, 'ENABLE', '2016-01-29 08:49:53', NULL, '2020-03-30 07:04:34', 24, 25);
INSERT INTO `sys_user` VALUES (1240933237517975553, NULL, 'user', 'df3c9533daa7c2c69af6cbf5021b7edd', 'reuty', 'user', '2020-03-19 16:00:00', 'M', 'user@qq.com', '13612345678', '1244510890397286402', 26, 'ENABLE', '2020-03-20 09:28:34', 1, '2020-03-30 06:56:30', 1, NULL);
INSERT INTO `sys_user` VALUES (1244889181797052417, NULL, 'test', '1a06957a2ea9be1b0fa9b6b840ed7838', 'whvc7', 'test', '2020-03-30 16:00:00', 'F', 'test@qq.com', '13612345678', NULL, 26, 'ENABLE', '2020-03-31 07:28:05', 1, '2020-03-31 07:49:30', 1, NULL);

-- ----------------------------
-- Table structure for sys_user_pos
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_pos`;
CREATE TABLE `sys_user_pos`  (
  `user_pos_id` bigint(0) NOT NULL COMMENT '主键id',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `pos_id` bigint(0) NOT NULL COMMENT '职位id',
  PRIMARY KEY (`user_pos_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户职位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_pos
-- ----------------------------
INSERT INTO `sys_user_pos` VALUES (1244518848225189890, 1240933237517975553, 1);
INSERT INTO `sys_user_pos` VALUES (1244894573881028610, 1244889181797052417, 1);

SET FOREIGN_KEY_CHECKS = 1;
