-- =====================================================
-- 省钱兄送礼物商城 演示库初始化脚本
-- Database: gift-mall
-- Generated: 2026-11-06T06:47:04.888Z
-- 说明：
-- 1. 管理端菜单 / 角色 / 权限数据完整保留
-- 2. 系统配置中客服、微信/支付宝/短信/OSS/地图等隐私密钥已清空
-- 3. 用户、订单、支付、提现、聊天等业务隐私数据已清空
-- 4. 后台演示账号 admin / 123456（导入后请立即修改）
-- =====================================================

CREATE DATABASE IF NOT EXISTS `gift-mall` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gift-mall`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `android_wgt_url` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_at` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `des` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `ios_version` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `ios_wgt_url` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `method` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `wgt_url` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='app升级表';
-- Data for app cleared (privacy / demo)

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `image_url` text COLLATE utf8mb4_bin COMMENT '图片地址',
  `classify` int(11) DEFAULT NULL COMMENT '分类1banner图 2金刚区分类',
  `url` text COLLATE utf8mb4_bin COMMENT '跳转地址 ',
  `sort` int(11) DEFAULT NULL COMMENT '顺序',
  `description` text COLLATE utf8mb4_bin COMMENT '描述',
  `state` int(11) DEFAULT NULL COMMENT '状态1正常 2隐藏',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='banner';

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` (`id`, `name`, `image_url`, `classify`, `url`, `sort`, `description`, `state`, `create_time`, `update_time`) VALUES
(6, '不知道', 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/a83d1fcd5c4414623c76edcee70ac2f5.png', 1, '123', 1, '123', 1, '2025-07-30 15:14:24', '2025-09-05 09:35:20'),
(7, '美妆饰品', 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/d9f097f8f008dfcd55930e1cc780cbd7.png', 1, '1', 1, '123', 2, '2025-07-30 15:14:41', '2026-01-09 09:21:28'),
(8, '生活日用', 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/6f7f049a4a893ba39e12407465e2bcad.png', 1, '123', 1, '123', 2, '2025-07-30 15:14:55', '2026-01-09 09:21:30'),
(9, 'IP文创', 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/93555ea9e68c94385b80fd09840056bf.png', 1, '123', 1, '123', 2, '2025-07-30 15:14:55', '2026-01-09 09:21:29'),
(11, 'IP文创', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/b124320c-2b0f-4dab-a67f-29d2f5d3c91a.png', 2, '/pages/index/navlist?name=IP文创&id=1', 1, '123', 1, '2025-07-30 15:14:55', '2026-01-09 09:23:54'),
(13, '还珠河', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2025/10/15/405cf4f5-63c4-43ed-aa0d-d0ca23dc39ee.png', 3, '123', 1, '123', 1, '2025-07-31 09:23:26', '2026-01-09 09:24:32'),
(15, '生鲜食品', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2025/10/15/97a0f3da-0214-4589-a0f7-c390663bdab5.png', 1, '123', 1, '123', 1, '2025-09-01 13:37:29', '2025-10-15 17:50:12'),
(16, '轮播图', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2025/10/15/47dcc12a-4d81-47ac-a504-7903de1f4638.png', 1, '111', 1, '222', 1, '2025-09-01 13:39:45', '2025-10-15 17:35:06'),
(17, '邀请好友', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2025/10/15/c2ce9f9e-6dfa-46b5-9b74-464d5ede9a52.png', 5, '1', 1, '1', 1, '2025-10-14 11:16:22', '2025-10-15 17:48:20'),
(18, '日常用品', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/d2f51370-0b30-4f77-a5ab-8f779b929f89.png', 2, '/pages/index/navlist?name=日常用品&id=3', 2, '11', 1, '2025-10-15 17:41:13', '2026-01-09 09:22:57'),
(19, '日用美妆', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/3767607e-476c-459e-b911-74b565e0d0aa.png', 2, '/pages/index/navlist?name=日用美妆&id=4', 3, '2', 1, '2025-10-15 17:46:12', '2026-01-09 09:22:43'),
(20, '生鲜食品', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/927e5d89-62fc-4483-9a9a-60dbd5ba2664.png', 2, '/pages/index/navlist?name=生鲜食品&id=6', NULL, NULL, 1, '2026-01-09 09:23:41', '2026-01-16 11:41:27');

-- ----------------------------
-- Table structure for cancel_account
-- ----------------------------
DROP TABLE IF EXISTS `cancel_account`;
CREATE TABLE `cancel_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '内容',
  `status` int(11) NOT NULL COMMENT '状态: 1申请中 2已注销 3撤销申请',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户注销申请';
-- Data for cancel_account cleared (privacy / demo)

-- ----------------------------
-- Table structure for chats
-- ----------------------------
DROP TABLE IF EXISTS `chats`;
CREATE TABLE `chats` (
  `chat_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会话id',
  `create_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `user_count` int(11) DEFAULT '0' COMMENT '用户未读条数',
  `user_count2` int(11) DEFAULT '0' COMMENT '好友未读条数',
  `user_head` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户头像',
  `user_head2` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '好友头像',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `user_id2` bigint(20) DEFAULT NULL COMMENT '好友id',
  `user_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户昵称',
  `user_name2` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '好友昵称',
  `store_count` int(11) DEFAULT '0' COMMENT '后台未读条数',
  `store_head` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '后台头像',
  `store_id` bigint(20) DEFAULT NULL COMMENT '总后台id(总后台传0',
  `store_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商户昵称',
  PRIMARY KEY (`chat_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='聊天会话';
-- Data for chats cleared (privacy / demo)

-- ----------------------------
-- Table structure for chats_content
-- ----------------------------
DROP TABLE IF EXISTS `chats_content`;
CREATE TABLE `chats_content` (
  `chat_content_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会话内容id',
  `chat_id` bigint(20) DEFAULT NULL COMMENT '会话Id',
  `content` text COLLATE utf8mb4_bin COMMENT '聊天内容',
  `create_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `send_type` bigint(20) DEFAULT NULL COMMENT '消息来源（1用户消息 2好友消息）',
  `status` int(11) DEFAULT '1' COMMENT '是否已读(1未读 2已读)',
  `type` int(11) DEFAULT NULL COMMENT '类型（1文字 2图片 3链接）',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `user_id2` bigint(20) DEFAULT NULL COMMENT '好友id',
  `store_id` bigint(20) DEFAULT NULL COMMENT '后台id（总后台传0）',
  PRIMARY KEY (`chat_content_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='聊天会话内容';
-- Data for chats_content cleared (privacy / demo)

-- ----------------------------
-- Table structure for chat_content
-- ----------------------------
DROP TABLE IF EXISTS `chat_content`;
CREATE TABLE `chat_content` (
  `chat_content_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '聊天内容id',
  `chat_conversation_id` int(11) DEFAULT NULL COMMENT '聊天会话id',
  `content` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '内容',
  `message_type` int(11) DEFAULT NULL COMMENT '类型 1文字 2图片 3语音',
  `width` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片宽度',
  `height` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片高度',
  `user_id` int(11) DEFAULT NULL COMMENT '发送用户id',
  `status` int(11) DEFAULT NULL COMMENT '0未读 1已读',
  `create_time` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发送时间',
  `voice_length` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`chat_content_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='聊天记录';
-- Data for chat_content cleared (privacy / demo)

-- ----------------------------
-- Table structure for chat_conversation
-- ----------------------------
DROP TABLE IF EXISTS `chat_conversation`;
CREATE TABLE `chat_conversation` (
  `chat_conversation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '聊天会话id',
  `user_id` int(11) DEFAULT NULL COMMENT '发送用户id',
  `focused_user_id` int(11) DEFAULT NULL COMMENT '接受用户id',
  `status` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态',
  `create_time` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `is_send_msg` int(11) DEFAULT NULL,
  `is_wx_msg` int(11) DEFAULT NULL,
  PRIMARY KEY (`chat_conversation_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='聊天会话';
-- Data for chat_conversation cleared (privacy / demo)

-- ----------------------------
-- Table structure for common_info
-- ----------------------------
DROP TABLE IF EXISTS `common_info`;
CREATE TABLE `common_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置文件id',
  `create_at` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `max` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `min` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置文件名称',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `value` text COLLATE utf8mb4_bin COMMENT '值',
  `condition_from` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=440 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='配置';

-- ----------------------------
-- Records of common_info
-- ----------------------------
INSERT INTO `common_info` (`id`, `create_at`, `max`, `min`, `type`, `value`, `condition_from`) VALUES
(1, '2025-10-14 11:24:51', NULL, '客服二维码', 1, '', 'xitong'),
(2, '2020-02-21 21:17:17', NULL, '公众号二维码', 2, '', 'xitong'),
(6, '2020-07-09 17:59:45', NULL, '微信公众号APPID\t', 5, '', 'weixin'),
(7, '2021-03-15 21:16:30', NULL, '短信签名', 6, '送礼物商城', 'xitong'),
(16, '2020-02-25 20:43:38', NULL, '微信公众号秘钥\t', 21, '', 'weixin'),
(17, '2020-02-25 20:43:59', NULL, '公众号Token', 16, '', 'weixin'),
(18, '2020-02-25 20:44:15', NULL, '公众号EncodingAESKey\t', 17, '', 'weixin'),
(20, '2025-10-13 18:55:14', NULL, '后台管理平台域名配置\t', 20, '', 'xitong'),
(21, '2025-10-13 18:55:23', NULL, 'h5服务域名配置\t', 19, '', 'xitong'),
(22, '2020-07-27 15:17', NULL, '短信服务商（1 启腾讯云 2 启阿里云  3 短信宝）', 79, '2', 'duanxin'),
(23, '2021-03-15 21:16:30', NULL, '后台服务名称\t', 12, '送礼物商城', 'xitong'),
(27, '2020-03-09 20:46:10', NULL, '通用APP下载地址\t', 25, '', 'xitong'),
(33, '2021-03-15 21:19:27', NULL, '腾讯云短信clientId\t', 31, '', 'duanxin'),
(34, '2020-03-28 00:47', NULL, '腾讯云短信clientSecret\t', 32, '', 'duanxin'),
(44, '2021-09-02 15:46:41', NULL, '客服微信号', 44, '', 'xitong'),
(47, '2020-03-29 00:21', NULL, '微信小程序APPID', 45, '', 'weixin'),
(48, '2020-03-29 00:21', NULL, '微信小程序秘钥', 46, '', 'weixin'),
(51, '2020-03-29 00:21', NULL, '分享安卓下载地址', 49, '', 'xitong'),
(52, '2020-03-29 00:21', NULL, '分享苹果下载地址', 50, '', 'xitong'),
(58, '2020-03-29 00:21', NULL, '开启微信登录', 53, '是', 'xitong'),
(69, '2020-06-04 16:34', NULL, 'APP消息推送PushAppKey', 60, '', 'push'),
(70, '2020-06-04 16:34', NULL, 'APP消息推送PushAppId', 61, '', 'push'),
(71, '2020-06-04 16:34', NULL, 'APP消息推送PushMasterSecret', 62, '', 'push'),
(77, '2021-03-15 21:22:30', NULL, '文件上传阿里云Endpoint', 68, '', 'oss'),
(78, '2021-03-15 21:22:38', NULL, '文件上传阿里云账号accessKeyId', 69, '', 'oss'),
(79, '2021-03-15 21:22:46', NULL, '文件上传阿里云账号accessKeySecret', 70, '', 'oss'),
(80, '2021-03-15 21:22:53', NULL, '文件上传阿里云Bucket名称', 71, '', 'oss'),
(81, '2021-03-15 21:23:00', NULL, '文件上传阿里云Bucket域名', 72, '', 'oss'),
(83, '2020-07-27 15:17', NULL, '微信APPappId', 74, '', 'weixin'),
(84, '2020-07-27 15:17', NULL, '微信商户key', 75, '', 'weixin'),
(85, '2020-07-27 15:17', NULL, '微信商户号mchId', 76, '', 'weixin'),
(96, '2020-07-27 15:17', NULL, '阿里云登陆或注册模板code（开启阿里云短信必须配置）', 80, '', 'duanxin'),
(97, '2020-07-27 15:17', NULL, '阿里云找回密码模板code（开启阿里云短信必须配置）', 81, '', 'duanxin'),
(98, '2020-07-27 15:17', NULL, '阿里云绑定手机号模板code（开启阿里云短信必须配置）', 82, '', 'duanxin'),
(99, '2020-07-27 15:17', NULL, '阿里云短信accessKeyId', 83, '', 'duanxin'),
(100, '2020-07-27 15:17', NULL, '阿里云短信accessSecret', 84, '', 'duanxin'),
(101, '2021-02-24 18:46:57', NULL, '官方邀请码', 88, '666666', 'xitong'),
(102, '2021-02-24 18:46:57', NULL, '转账方式 1支付宝证书  2支付宝秘钥 3手动', 98, '2', 'zhifubao'),
(108, '2020-07-27 15:17', NULL, '公众号是否自动登录', 108, '是', 'kaiguan'),
(118, '2020-07-27 15:17', NULL, '邀请赚钱推广内容', 101, '', 'xitong'),
(120, '2020-07-27 15:17', NULL, '邀请赚钱微信分享内容', 103, '', 'xitong'),
(130, '2020-11-04 10:31:40', NULL, '提现最低额度', 112, '10', 'xitong'),
(134, '2020-11-04 10:31:40', NULL, '海报邀请语', 116, '', 'xitong'),
(154, '2020-11-04 10:31:40', NULL, '是否开启APP微信分享', 136, '否', 'weixin'),
(159, '2020-11-04 10:31:40', NULL, 'H5推广是否分享APP下载页面', 141, '', 'xitong'),
(170, '2025-10-13 16:30:02', NULL, '提现手续费', 152, '0.1', 'xitong'),
(171, '2021-09-06 16:19:51', NULL, '最高提现金额', 153, '1000', 'xitong'),
(173, '2021-08-14 19:09:54', NULL, '平台费率', 155, '0.3', 'xitongs'),
(182, '2020-07-27 15:17', NULL, '短信宝用户名', 164, '', 'duanxin'),
(183, '2020-07-27 15:17', NULL, '短信宝密码', 165, '', 'duanxin'),
(184, '2020-11-04 10:31:40', NULL, '支付宝证书路径', 200, '', 'zhifubao'),
(200, '2021-07-19 11:32:12', NULL, '帮助中心', 175, '<p>本应用尊重并保护所有使用服务用户的个人隐私权。为了给您提供更准确、更有个性化的服务，本应用会按照本隐私权政策的规定使用和披露您的个人信息。但本应用将以高度的勤勉、审慎义务对待这些信息。除本隐私权政策另有规定外，在未征得您事先许可的情况下，本应用不会将这些信息对外披露或向第三方提供。本应用会不时更新本隐私权政策。 您在同意本应用服务使用协议之时，即视为您已经同意本隐私权政策全部内容。本隐私权政策属于本应用服务使用协议不可分割的一部分。</p><p>1. 适用范围</p><p>(a) 在您注册本应用帐号时，您根据本应用要求提供的个人注册信息；</p><p>(b) 在您使用本应用网络服务，或访问本应用平台网页时，本应用自动接收并记录的您的浏览器和计算机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间、软硬件特征信息及您需求的网页记录等数据；</p><p>(c) 本应用通过合法途径从商业伙伴处取得的用户个人数据。</p><p>您了解并同意，以下信息不适用本隐私权政策：</p><p>(a) 您在使用本应用平台提供的搜索服务时输入的关键字信息；</p><p>(b) 本应用收集到的您在本应用发布的有关信息数据，包括但不限于参与活动、成交信息及评价详情；</p><p>(c) 违反法律规定或违反本应用规则行为及本应用已对您采取的措施。</p><p>2. 信息使用</p><p>(a)本应用不会向任何无关第三方提供、出售、出租、分享或交易您的个人信息，除非事先得到您的许可，或该第三方和本应用（含本应用关联公司）单独或共同为您提供服务，且在该服务结束后，其将被禁止访问包括其以前能够访问的所有这些资料。</p><p>(b) 本应用亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播您的个人信息。任何本应用平台用户如从事上述活动，一经发现，本应用有权立即终止与该用户的服务协议。</p><p>(c) 为服务用户的目的，本应用可能通过使用您的个人信息，向您提供您感兴趣的信息，包括但不限于向您发出产品和服务信息，或者与本应用合作伙伴共享信息以便他们向您发送有关其产品和服务的信息（后者需要您的事先同意）。</p><p>3. 信息披露</p><p>在如下情况下，本应用将依据您的个人意愿或法律的规定全部或部分的披露您的个人信息：</p><p>(a) 经您事先同意，向第三方披露；</p><p>(b)为提供您所要求的产品和服务，而必须和第三方分享您的个人信息；</p><p>(c) 根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露；</p><p>(d) 如您出现违反中国有关法律、法规或者本应用服务协议或相关规则的情况，需要向第三方披露；</p><p>(e) 如您是适格的知识产权投诉人并已提起投诉，应被投诉人要求，向被投诉人披露，以便双方处理可能的权利纠纷；</p><p>(f) 在本应用平台上创建的某一交易中，如交易任何一方履行或部分履行了交易义务并提出信息披露请求的，本应用有权决定向该用户提供其交易对方的联络方式等必要信息，以促成交易的完成或纠纷的解决。</p><p>(g) 其它本应用根据法律、法规或者网站政策认为合适的披露。</p><p>4. 信息存储和交换</p><p>本应用收集的有关您的信息和资料将保存在本应用及（或）其关联公司的服务器上，这些信息和资料可能传送至您所在国家、地区或本应用收集信息和资料所在地的境外并在境外被访问、存储和展示。</p><p>5. Cookie的使用</p><p>(a) 在您未拒绝接受cookies的情况下，本应用会在您的计算机上设定或取用cookies ，以便您能登录或使用依赖于cookies的本应用平台服务或功能。本应用使用cookies可为您提供更加周到的个性化服务，包括推广服务。</p><p>(b) 您有权选择接受或拒绝接受cookies。您可以通过修改浏览器设置的方式拒绝接受cookies。但如果您选择拒绝接受cookies，则您可能无法登录或使用依赖于cookies的本应用网络服务或功能。</p><p>(c) 通过本应用所设cookies所取得的有关信息，将适用本政策。</p><p>6. 信息安全</p><p>(a) 本应用帐号均有安全保护功能，请妥善保管您的用户名及密码信息。本应用将通过对用户密码进行加密等安全措施确保您的信息不丢失，不被滥用和变造。尽管有前述安全措施，但同时也请您注意在信息网络上不存在“完善的安全措施”。</p><p>(b) 在使用本应用网络服务进行网上交易时，您不可避免的要向交易对方或潜在的交易对方披露自己的个人信息，如联络方式或者邮政地址。请您妥善保护自己的个人信息，仅在必要的情形下向他人提供。如您发现自己的个人信息泄密，尤其是本应用用户名及密码发生泄露，请您立即联络本应用客服，以便本应用采取相应措施。</p><p>7.本隐私政策的更改</p><p>(a)如果决定更改隐私政策，我们会在本政策中、本公司网站中以及我们认为适当的位置发布这些更改，以便您了解我们如何收集、使用您的个人信息，哪些人可以访问这些信息，以及在什么情况下我们会透露这些信息。</p><p>(b)本公司保留随时修改本政策的权利，因此请经常查看。如对本政策作出重大更改，本公司会通过网站通知的形式告知。</p>', 'xieyi'),
(201, '2021-08-14 19:10:04', NULL, '隐私政策', 176, '<p>本应用尊重并保护所有使用服务用户的个人隐私权。为了给您提供更准确、更有个性化的服务，本应用会按照本隐私权政策的规定使用和披露您的个人信息。但本应用将以高度的勤勉、审慎义务对待这些信息。除本隐私权政策另有规定外，在未征得您事先许可的情况下，本应用不会将这些信息对外披露或向第三方提供。本应用会不时更新本隐私权政策。 您在同意本应用服务使用协议之时，即视为您已经同意本隐私权政策全部内容。本隐私权政策属于本应用服务使用协议不可分割的一部分。</p><p>1. 适用范围</p><p>(a) 在您注册本应用帐号时，您根据本应用要求提供的个人注册信息；</p><p>(b) 在您使用本应用网络服务，或访问本应用平台网页时，本应用自动接收并记录的您的浏览器和计算机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间、软硬件特征信息及您需求的网页记录等数据；</p><p>(c) 本应用通过合法途径从商业伙伴处取得的用户个人数据。</p><p>您了解并同意，以下信息不适用本隐私权政策：</p><p>(a) 您在使用本应用平台提供的搜索服务时输入的关键字信息；</p><p>(b) 本应用收集到的您在本应用发布的有关信息数据，包括但不限于参与活动、成交信息及评价详情；</p><p>(c) 违反法律规定或违反本应用规则行为及本应用已对您采取的措施。</p><p>2. 信息使用</p><p>(a)本应用不会向任何无关第三方提供、出售、出租、分享或交易您的个人信息，除非事先得到您的许可，或该第三方和本应用（含本应用关联公司）单独或共同为您提供服务，且在该服务结束后，其将被禁止访问包括其以前能够访问的所有这些资料。</p><p>(b) 本应用亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播您的个人信息。任何本应用平台用户如从事上述活动，一经发现，本应用有权立即终止与该用户的服务协议。</p><p>(c) 为服务用户的目的，本应用可能通过使用您的个人信息，向您提供您感兴趣的信息，包括但不限于向您发出产品和服务信息，或者与本应用合作伙伴共享信息以便他们向您发送有关其产品和服务的信息（后者需要您的事先同意）。</p><p>3. 信息披露</p><p>在如下情况下，本应用将依据您的个人意愿或法律的规定全部或部分的披露您的个人信息：</p><p>(a) 经您事先同意，向第三方披露；</p><p>(b)为提供您所要求的产品和服务，而必须和第三方分享您的个人信息；</p><p>(c) 根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露；</p><p>(d) 如您出现违反中国有关法律、法规或者本应用服务协议或相关规则的情况，需要向第三方披露；</p><p>(e) 如您是适格的知识产权投诉人并已提起投诉，应被投诉人要求，向被投诉人披露，以便双方处理可能的权利纠纷；</p><p>(f) 在本应用平台上创建的某一交易中，如交易任何一方履行或部分履行了交易义务并提出信息披露请求的，本应用有权决定向该用户提供其交易对方的联络方式等必要信息，以促成交易的完成或纠纷的解决。</p><p>(g) 其它本应用根据法律、法规或者网站政策认为合适的披露。</p><p>4. 信息存储和交换</p><p>本应用收集的有关您的信息和资料将保存在本应用及（或）其关联公司的服务器上，这些信息和资料可能传送至您所在国家、地区或本应用收集信息和资料所在地的境外并在境外被访问、存储和展示。</p><p>5. Cookie的使用</p><p>(a) 在您未拒绝接受cookies的情况下，本应用会在您的计算机上设定或取用cookies ，以便您能登录或使用依赖于cookies的本应用平台服务或功能。本应用使用cookies可为您提供更加周到的个性化服务，包括推广服务。</p><p>(b) 您有权选择接受或拒绝接受cookies。您可以通过修改浏览器设置的方式拒绝接受cookies。但如果您选择拒绝接受cookies，则您可能无法登录或使用依赖于cookies的本应用网络服务或功能。</p><p>(c) 通过本应用所设cookies所取得的有关信息，将适用本政策。</p><p>6. 信息安全</p><p>(a) 本应用帐号均有安全保护功能，请妥善保管您的用户名及密码信息。本应用将通过对用户密码进行加密等安全措施确保您的信息不丢失，不被滥用和变造。尽管有前述安全措施，但同时也请您注意在信息网络上不存在“完善的安全措施”。</p><p>(b) 在使用本应用网络服务进行网上交易时，您不可避免的要向交易对方或潜在的交易对方披露自己的个人信息，如联络方式或者邮政地址。请您妥善保护自己的个人信息，仅在必要的情形下向他人提供。如您发现自己的个人信息泄密，尤其是本应用用户名及密码发生泄露，请您立即联络本应用客服，以便本应用采取相应措施。</p><p>7.本隐私政策的更改</p><p>(a)如果决定更改隐私政策，我们会在本政策中、本公司网站中以及我们认为适当的位置发布这些更改，以便您了解我们如何收集、使用您的个人信息，哪些人可以访问这些信息，以及在什么情况下我们会透露这些信息。</p><p>(b)本公司保留随时修改本政策的权利，因此请经常查看。如对本政策作出重大更改，本公司会通过网站通知的形式告知。</p>', 'xieyi'),
(202, '2021-08-14 19:09:54', NULL, '注册协议', 177, '<p>尊敬的用户您好：在您使用本服务之前，请您认真阅读本用户协议，更好的了解我们所提供的服务以及您享有的权利义务。您开始使用时，即表示您已经了解并确认接受了本文件中的全部条款，包括我们对本服务条款随时做的任何修改。</p><p>一、协议的效力</p><p>本协议内容包括协议正文及所有已经发布或将来可能发布的各类规则。所有规则为本协议不可分割的组成部分，与协议正文具有同等法律效力。您承诺接受并遵守本协议的约定。如果您不同意本协议的约定，您应立即停止使用本平台服务。</p><p>二、用户行为规范</p><p>用户同意将不会利用本服务进行任何违法或不正当的活动，包括但不限于下列行为∶</p><p>发布或以其它方式传送含有下列内容之一的信息：</p><p>反对宪法所确定的基本原则的；</p><p>危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；</p><p>损害国家荣誉和利益的；</p><p>煽动民族仇恨、民族歧视、破坏民族团结的；</p><p>破坏国家宗教政策，宣扬邪教和封建迷信的；</p><p>散布谣言，扰乱社会秩序，破坏社会稳定的；</p><p>散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</p><p>侮辱或者诽谤他人，侵害他人合法权利的；</p><p>含有虚假、诈骗、有害、胁迫、侵害他人隐私、骚扰、侵害、中伤、粗俗、猥亵、或其它道德上令人反感的内容；</p><p>含有当地法律、法规、规章、条例以及任何具有法律效力之规范所限制或禁止的其它内容的；</p><p>含有不适合在本平台展示的内容；</p><p>以任何方式危害他人的合法权益；</p><p>冒充其他任何人或机构，或以虚伪不实的方式陈述或谎称与任何人或机构有关；</p><p>将依据任何法律或合约或法定关系（例如由于雇佣关系和依据保密合约所得知或揭露之内部资料、专属及机密资料）知悉但无权传送之任何内容加以发布、发送电子邮件或以其它方式传送；</p><p>将侵害他人著作权、专利权、商标权、商业秘密、或其它专属权利（以下简称“专属权利”）之内容加以发布或以其它方式传送；</p><p>将任何广告信函、促销资料、“垃圾邮件”、““滥发信件”、“连锁信件”、“直销”或其它任何形式的劝诱资料加以发布、发送或以其它方式传送；</p><p>将设计目的在于干扰、破坏或限制任何计算机软件、硬件或通讯设备功能之计算机病毒（包括但不限于木马程序（trojan horses）、蠕虫（worms）、定时炸弹、删除蝇（cancelbots）（以下简称“病毒”）或其它计算机代码、档案和程序之任何资料，加以发布、发送或以其它方式传送；</p><p>干扰或破坏本服务或与本服务相连线之服务器和网络，或违反任何关于本服务连线网络之规定、程序、政策或规范；</p><p>跟踪、人肉搜索或以其它方式骚扰他人；</p><p>故意或非故意地违反任何适用的当地、国家法律，以及任何具有法律效力的规则；</p><p>未经合法授权而截获、篡改、收集、储存或删除他人个人信息、站内邮件或其它数据资料，或将获知的此类资料用于任何非法或不正当目的。</p><p>三、知识产权</p><p>本平台所有设计图样以及其他图样、产品及服务名称。任何人不得使用、复制或用作其他用途。未经我们许可，任何单位和个人不得私自复制、传播、展示、镜像、上载、下载、使用，或者从事任何其他侵犯我们知识产权的行为。否则，我们将追究相关法律责任。</p><p>我们鼓励用户充分利用平台自由地张贴和共享自己的信息，但这些内容必须位于公共领域内，或者用户拥有这些内容的使用权。同时，用户对于其创作并在本平台上发布的合法内容依法享有著作权及其相关权利。</p><p>四、免责声明</p><p>互联网是一个开放平台，用户将照片等个人资料上传到互联网上，有可能会被其他组织或个人复制、转载、擅改或做其它非法用途，用户必须充分意识此类风险的存在。用户明确同意其使用本服务所存在的风险将完全由其自己承担；因其使用本服务而产生的一切后果也由其自己承担，我们对用户不承担任何责任。</p><p>对于用户上传的照片、资料、证件等，已采用相关措施并已尽合理努力进行审核，但不保证其内容的正确性、合法性或可靠性，相关责任由上传上述内容的会员负责。</p><p>尽管已采取相应的技术保障措施 ，但用户仍有可能收到各类的广告信或其他不以招聘/应聘为目的邮件或其它方式传送的任何内容，本平台不承担责任。</p><p>对于各种广告信息、链接、资讯等，不保证其内容的正确性、合法性或可靠性，相关责任由广告商承担；用户通过本服务与广告商进行任何形式的通讯或商业往来，或参与促销活动，包含相关商品或服务之付款及交付，以及达成的其它任何相关条款、条件、保证或声明，完全为用户与广告商之间之行为，与本平台无关。用户因前述任何交易或前述广告商而遭受的任何性质的损失或损害，本平台不承担任何责任。</p><p>本平台不保证其提供的服务一定能满足用户的要求和期望，也不保证服务不会中断，对服务的及时性、安全性、准确性也都不作保证。对于因不可抗力或无法控制的原因造成的网络服务中断或其他缺陷，不承担任何责任。我们不对用户所发布信息的删除或储存失败承担责任。我们有权判断用户的行为是否符合本网站使用协议条款之规定，如果我们认为用户违背了协议条款的规定，我们有终止向其提供服务的权利。</p><p>本平台保留变更、中断或终止部分网络服务的权利。保留根据实际情况随时调整平台提供的服务种类、形式的权利。本平台不承担因业务调整给用户造成的损失。本平台仅提供相关服务，除此之外与本服务有关的设备（如电脑、调制解调器及其他与接入互联网有关的装置）及所需的费用（如为接入互联网而支付的电话费及上网费）均应由用户自行负担。</p>', 'xieyi'),
(203, '2021-08-14 19:09:54', NULL, '关于我们', 187, '关于我们，有问题可以在线联系客服哦。', 'xieyi'),
(204, '2021-08-14 19:09:54', NULL, '登录是否获取手机号', 188, '是', 'weixin'),
(205, '2020-10-28 16:57:07', NULL, '佣金比例', 189, '0.1', 'xitong'),
(217, '2020-07-27 15:17', NULL, '腾讯地图key', 217, '', 'xitong'),
(234, '2020-11-04 10:31:40', NULL, '上传方式 1阿里云oss  2本地', 234, '1', 'oss'),
(235, '2020-11-04 10:31:40', NULL, '新订单通知', 235, '', 'xitong'),
(236, '2020-11-04 10:31:40', NULL, '退款通知', 236, '', 'xitong'),
(237, '2020-11-04 10:31:40', NULL, '是否开启会员', 237, '是', 'xitong'),
(238, '2020-11-04 10:31:40', NULL, '是否开启我的余额', 238, '是', 'xitong'),
(239, '2020-11-04 10:31:40', NULL, '小程序是否发布', 239, '是', 'xitong'),
(240, '2020-11-04 10:31:40', NULL, '消息通知', 240, '', 'xitong'),
(241, '2020-11-04 10:31:40', NULL, '超时几分钟短信通知', 241, '5', 'xitong'),
(242, '2020-11-04 10:31:40', NULL, '几条未读短信通知', 242, '1', 'xitong'),
(243, '2020-11-04 10:31:40', NULL, '是否开启微信提现', 243, '是', 'xitong'),
(244, '2021-12-22 10:11:36', NULL, '微信提现方式 1自动 2手动', 244, '2', 'xitong'),
(245, '2020-11-04 10:31:40', NULL, '是否开启支付宝提现', 245, '是', 'xitong'),
(246, '2020-10-28 16:57:07', NULL, '微信证书地址', 201, '', 'xitong'),
(415, '2023-07-26 16:27:15', NULL, '天地图key', 415, '', 'xitong'),
(416, '2025-10-14 15:13:16', NULL, '支付宝appid', 416, '', 'zhifubao'),
(417, '2025-10-14 15:12:43', NULL, '支付宝公钥', 417, '', 'zhifubao'),
(418, '2025-10-14 15:12:28', NULL, '支付宝商户秘钥', 418, '', 'zhifubao'),
(419, '2025-08-05 14:40:12', NULL, '支付宝应用公钥证书路径', 419, '', 'zhifubao'),
(420, '2025-08-05 14:40:12', NULL, '支付宝公钥证书路径', 420, '', 'zhifubao'),
(421, '2025-08-05 14:40:12', NULL, '支付宝根证书路径', 421, '', 'zhifubao'),
(422, '2025-08-05 14:40:12', NULL, '支付宝支付模式 1: 普通公钥模式 2: 证书模式', 422, '1', 'zhifubao'),
(423, '2025-10-14 15:11:00', NULL, '支付宝是否开启沙箱模式 1: 是 2: 否', 423, '2', 'zhifubao'),
(424, '2025-08-05 14:40:12', NULL, '微信商户key', 424, '', 'pay'),
(425, '2025-08-05 14:40:12', NULL, '微信商户号mchId', 425, '', 'pay'),
(426, '2025-08-05 14:40:12', NULL, '微信支付商户秘钥', 426, '', 'pay'),
(427, '2025-08-05 14:40:12', NULL, '微信支付商户号', 427, '', 'pay'),
(428, '2025-08-05 14:40:12', NULL, '微信支付app appid', 428, '', 'pay'),
(429, '2025-08-05 14:40:12', NULL, '微信支付h5 appid', 429, '', 'pay'),
(430, '2025-08-05 14:40:12', NULL, '微信支付jsapi appid', 430, '', 'pay'),
(431, '2025-08-05 14:40:12', NULL, '发票最少金额', 318, '1', 'xitong'),
(432, '2025-08-05 14:40:12', NULL, '开票说明', 319, '123', 'xitong'),
(433, '2025-08-07 09:50:11', NULL, '企业微信客服链接', 320, '', 'xitong'),
(434, '2025-08-07 09:50:11', NULL, '企业微信客服appid', 321, '', 'xitong'),
(435, '2025-08-07 09:50:11', NULL, '客服类型(1:电话，2:企业微信，3:客服二维码)', 322, '3', 'xitong'),
(436, '2025-08-07 09:50:11', NULL, '客服电话', 323, '', 'xitong'),
(437, '2025-10-13 11:26:19', NULL, '分享链接有效期（单位：小时）', 324, '1', 'xitong'),
(438, '2025-08-07 09:50:11', NULL, '阿里云快递app_code', 325, '', 'xitong'),
(439, '2025-10-15 16:32:01', NULL, '是否开启小程序自动登录', 326, '否', 'kaiguan');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `opinion` text COLLATE utf8mb4_bin COMMENT '意见',
  `contact` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系方式',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='意见反馈';
-- Data for feedback cleared (privacy / demo)

-- ----------------------------
-- Table structure for gift_blessing
-- ----------------------------
DROP TABLE IF EXISTS `gift_blessing`;
CREATE TABLE `gift_blessing` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '祝福语',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='祝福语表';

-- ----------------------------
-- Records of gift_blessing
-- ----------------------------
INSERT INTO `gift_blessing` (`id`, `content`, `create_time`, `update_time`) VALUES
(1, '生日快乐', '2025-07-31 11:14:10', NULL),
(2, '乔迁大吉', '2025-07-31 11:14:18', NULL),
(3, '爱你呦', '2025-07-31 11:14:45', '2025-09-28 16:06:50');

-- ----------------------------
-- Table structure for gift_card
-- ----------------------------
DROP TABLE IF EXISTS `gift_card`;
CREATE TABLE `gift_card` (
  `card_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '卡片id',
  `card_img` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '卡片图案',
  `card_category_id` int(11) DEFAULT NULL COMMENT '卡片分类id',
  `status` tinyint(1) DEFAULT NULL COMMENT '卡片状态 0启用 1禁用',
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`card_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='卡片表';

-- ----------------------------
-- Records of gift_card
-- ----------------------------
INSERT INTO `gift_card` (`card_id`, `card_img`, `card_category_id`, `status`, `sort`, `create_time`, `update_time`) VALUES
(1, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/645e96ab-e6b0-405a-9302-b6288da2052e.png', 1, 0, 1, NULL, '2026-01-09 09:25:00'),
(2, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/105d1009-5496-4d1c-a7f7-2902ace18bdc.png', 2, 0, 4, NULL, '2026-01-09 09:25:57'),
(3, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/fe10e7fe-da25-4c22-a80b-1e09611918d6.png', 4, 0, 3, NULL, '2026-01-09 09:25:19'),
(4, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/80658b2a-91b7-466d-9bab-15fca15f8149.png', 6, 0, 5, NULL, '2026-01-09 09:25:34'),
(5, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/c8bda20a-6008-4d44-9ee2-18e75011b659.png', 5, 0, 2, '2025-09-01 16:59:41', '2026-01-09 09:25:10'),
(7, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/d78f2407-9de3-44ff-9419-b3803c6c7a0e.png', 1, 0, 6, '2025-09-28 14:39:49', '2026-01-09 09:25:46');

-- ----------------------------
-- Table structure for gift_card_category
-- ----------------------------
DROP TABLE IF EXISTS `gift_card_category`;
CREATE TABLE `gift_card_category` (
  `card_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '卡片分类id',
  `card_category_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '卡片名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`card_category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='卡片分类表';

-- ----------------------------
-- Records of gift_card_category
-- ----------------------------
INSERT INTO `gift_card_category` (`card_category_id`, `card_category_name`, `sort`, `create_time`, `update_time`) VALUES
(1, '生日', 1, '2025-09-01 17:07:06', NULL),
(2, '感谢', 2, '2025-09-01 17:07:11', NULL),
(4, '周年庆', 3, '2025-09-28 14:33:55', NULL),
(5, '情人节', 4, '2025-09-28 14:34:06', NULL),
(6, '中秋节', 5, '2025-09-28 14:35:08', NULL);

-- ----------------------------
-- Table structure for gift_cart
-- ----------------------------
DROP TABLE IF EXISTS `gift_cart`;
CREATE TABLE `gift_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `sku_id` int(11) DEFAULT NULL COMMENT '商品skuId',
  `number` int(11) DEFAULT NULL COMMENT '数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='购物车';
-- Data for gift_cart cleared (privacy / demo)

-- ----------------------------
-- Table structure for gift_coupon
-- ----------------------------
DROP TABLE IF EXISTS `gift_coupon`;
CREATE TABLE `gift_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` tinyint(1) DEFAULT NULL COMMENT '优惠券类型（1通用券 2商品券）',
  `coupon_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '优惠券名称',
  `less_money` decimal(10,2) DEFAULT NULL COMMENT '优惠券面值',
  `min_money` decimal(10,2) DEFAULT NULL COMMENT '优惠券最低消费',
  `valid_day` int(11) DEFAULT NULL COMMENT '优惠券有效期限(天)',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（0开启 1关闭）',
  `product_ids` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品ids(多个) 逗号分隔',
  `product_images` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品图片(多个)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='优惠券信息';
-- No data for gift_coupon

-- ----------------------------
-- Table structure for gift_coupon_issue
-- ----------------------------
DROP TABLE IF EXISTS `gift_coupon_issue`;
CREATE TABLE `gift_coupon_issue` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `start_time` datetime NOT NULL COMMENT '领券开启时间',
  `end_time` datetime NOT NULL COMMENT '领券结束时间',
  `number` int(11) DEFAULT NULL COMMENT '发布数量',
  `remain_number` int(11) DEFAULT NULL COMMENT '剩余数量',
  `limited` tinyint(1) DEFAULT NULL COMMENT '是否限量（1限量 2不限量）',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（0开启 1关闭）',
  `coupon_id` int(11) DEFAULT NULL COMMENT '优惠券id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='优惠券发布表';
-- No data for gift_coupon_issue

-- ----------------------------
-- Table structure for gift_order
-- ----------------------------
DROP TABLE IF EXISTS `gift_order`;
CREATE TABLE `gift_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `order_no` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '订单号',
  `pay_no` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付单号',
  `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单备注',
  `number` int(11) DEFAULT NULL COMMENT '商品个数',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态（1待付款 2已付款 3已发货 4已收货 5已取消 6退款中 7已退款 8拒绝退款 9拼团中 10已评价 11待赠送 12已赠送 13已领取 14已拒绝）',
  `pay_way` tinyint(1) DEFAULT NULL COMMENT '支付平台 1微信 2支付宝 3paypal 4stripe 5余额',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `refund_support` tinyint(1) DEFAULT NULL COMMENT '是否可以退款（1可以退款 2不可退款）',
  `refund_reason` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '退款理由',
  `refund_refused_reason` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '拒绝退款理由',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `type_id` int(11) DEFAULT NULL COMMENT '商品类型id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `sku_id` int(11) DEFAULT NULL COMMENT '商品skuid',
  `sku_detail_json` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品sku信息',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '订单类型（1普通订单 2拼团 3秒杀）',
  `user_coupons_id` int(11) DEFAULT NULL COMMENT '用户优惠券id',
  `user_coupon_money` decimal(10,2) DEFAULT NULL COMMENT '优惠券优惠金额',
  `consignee` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `provinces` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省市区',
  `address_detail` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详细地址',
  `postage_price` decimal(10,2) DEFAULT NULL COMMENT '邮费',
  `express_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '快递名称',
  `express_number` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '快递单号',
  `express_time` datetime DEFAULT NULL COMMENT '发货时间',
  `blessing` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '祝福语',
  `card_id` int(11) DEFAULT NULL COMMENT '卡片id',
  `card_img` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '卡片图片地址',
  `give_user_id` int(11) DEFAULT NULL COMMENT '接收人id',
  `give_user_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接收人名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `province` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省',
  `city` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '市',
  `district` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区',
  `lat` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `lng` decimal(10,6) DEFAULT NULL COMMENT '经度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='订单';
-- Data for gift_order cleared (privacy / demo)

-- ----------------------------
-- Table structure for gift_order_product
-- ----------------------------
DROP TABLE IF EXISTS `gift_order_product`;
CREATE TABLE `gift_order_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '订单id',
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `num` int(11) NOT NULL COMMENT '商品数量',
  `type_id` int(11) DEFAULT NULL COMMENT '商品类型id',
  `sku_id` int(11) DEFAULT NULL COMMENT '商品skuid',
  `sku_detail_json` text COLLATE utf8mb4_bin COMMENT '商品sku信息',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='订单商品表';
-- Data for gift_order_product cleared (privacy / demo)

-- ----------------------------
-- Table structure for gift_product
-- ----------------------------
DROP TABLE IF EXISTS `gift_product`;
CREATE TABLE `gift_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品名称',
  `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品描述',
  `cover_img` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品封面图片',
  `img` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品图片',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `sales` int(11) DEFAULT NULL COMMENT '销量',
  `status` tinyint(1) DEFAULT NULL COMMENT '商品状态(默认0正常 1下架)',
  `good_pick` tinyint(1) DEFAULT NULL COMMENT '是否精选好物 0是 1不是',
  `special_offer` tinyint(1) DEFAULT NULL COMMENT '是否特价商品 0是 1不是',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
  `discount_ratio` decimal(2,2) DEFAULT NULL COMMENT '折扣比例 0.1表示10%1折',
  `type_id` int(11) DEFAULT NULL COMMENT '商品类型id',
  `scene_id` int(11) DEFAULT NULL COMMENT '送礼场景id',
  `target_id` int(11) DEFAULT NULL COMMENT '送礼对象id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='商品基本信息';

-- ----------------------------
-- Records of gift_product
-- ----------------------------
INSERT INTO `gift_product` (`id`, `name`, `description`, `cover_img`, `img`, `price`, `sales`, `status`, `good_pick`, `special_offer`, `original_price`, `discount_ratio`, `type_id`, `scene_id`, `target_id`, `create_time`, `update_time`) VALUES
(1, '盲盒生日礼物', '商品', 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/d3fc341e3df910239050e42f714ff97f.png', 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/d3fc341e3df910239050e42f714ff97f.png', '99.00', 1000, 0, 1, 1, '99.00', '0.10', 1, 1, 1, '2025-07-30 15:52:57', '2026-01-09 09:35:05'),
(10, '水果盲盒', '安定', 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/26/f4aa5896dbde518858cf6b4da7339775.jpg', 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/26/675d8f9e8b6eab8408ac60ac395e422e.jpg', '111.00', 0, 0, 0, 0, '1111.00', '0.50', 3, 1, 1, '2025-09-26 11:35:26', '2026-01-09 09:34:49'),
(11, '盲盒商品', '11111111111', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/a27cfcd1-0502-4a14-a1f9-a3fadf15e14a.png', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/b5f9d302-ab6e-4a64-a017-77e0df119b3e.png', '20.00', 0, 0, 0, 0, '30.00', '0.30', 1, 1, 1, '2025-09-26 11:35:55', '2026-01-09 09:35:36'),
(13, '蓝牙耳机-单规格', '蒂凡尼的海洋之星智联，爱的见证。', 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/28/04dc22fe8836f6a575a1aaabd6c3957f.png', 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/28/9849ce10ecc69043892da31d48472b2c.png', '100.00', 2, 0, 0, 0, '999.00', '0.50', 4, 2, 5, '2025-09-28 14:59:22', '2026-01-09 09:34:16'),
(14, '简约手机壳-多规格', '商品描述商品描述商品描述', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/831ea1a6-3970-4695-95a5-75b49e523bb9.jpg', 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/10/10/2babf57d09bc81ff539bd5f2f5c0dc96.png', '100.00', 8, 0, 0, 0, '199.00', '0.50', 1, 3, 2, '2025-10-10 17:03:19', '2026-01-09 09:33:52'),
(15, '富平柿饼3斤装包邮送礼礼盒老人小孩孕妇可直接吃', '富平柿饼3斤装包邮送礼礼盒老人小孩孕妇可直接吃，你值得拥有。', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/16/13108619-ea41-412b-a0bd-c2fbbfe9b4b8.png', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/16/3f2f3a9f-90eb-4701-9714-a7c2687672b1.png,https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/16/5d886261-22b0-43d7-ad6b-dfee0e34c128.png', '0.02', 2, 0, 0, 0, '120.00', '0.90', 6, 1, 1, '2026-01-16 10:42:44', '2026-04-08 10:24:00');

-- ----------------------------
-- Table structure for gift_product_attr
-- ----------------------------
DROP TABLE IF EXISTS `gift_product_attr`;
CREATE TABLE `gift_product_attr` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `rule_id` int(11) DEFAULT NULL COMMENT '规格模板id',
  `attr_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '属性名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='商品属性';

-- ----------------------------
-- Records of gift_product_attr
-- ----------------------------
INSERT INTO `gift_product_attr` (`id`, `product_id`, `rule_id`, `attr_name`, `create_time`, `update_time`) VALUES
(1, 1, 1, '大小', '2025-07-30 19:46:48', NULL),
(10, 10, 1, '大小', NULL, NULL),
(12, 14, 5, '首饰', NULL, NULL);

-- ----------------------------
-- Table structure for gift_product_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `gift_product_attr_value`;
CREATE TABLE `gift_product_attr_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `attr_id` int(11) DEFAULT NULL COMMENT '商品规格id',
  `detail` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '属性值组合:{尺寸: "7寸", 颜色: "红底"}',
  `value` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '规格属性名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='商品属性值';

-- ----------------------------
-- Records of gift_product_attr_value
-- ----------------------------
INSERT INTO `gift_product_attr_value` (`id`, `product_id`, `attr_id`, `detail`, `value`, `create_time`, `update_time`) VALUES
(13, 10, 10, '大,小', '大', NULL, NULL),
(14, 10, 10, '白,黑', '颜色', NULL, NULL),
(37, 1, 1, '大,小', '大', NULL, NULL),
(38, 1, 1, '白,黑', '颜色', NULL, NULL),
(41, 14, 12, '黑色,白色', '颜色', NULL, NULL),
(42, 14, 12, '是,否', '包邮', NULL, NULL);

-- ----------------------------
-- Table structure for gift_product_rule
-- ----------------------------
DROP TABLE IF EXISTS `gift_product_rule`;
CREATE TABLE `gift_product_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_name` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '规格名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='商品规格';

-- ----------------------------
-- Records of gift_product_rule
-- ----------------------------
INSERT INTO `gift_product_rule` (`id`, `rule_name`, `create_time`, `update_time`) VALUES
(1, '大小', '2025-07-30 19:47:43', '2025-09-03 15:18:16'),
(2, '标题', '2025-09-02 13:26:22', '2025-09-05 09:39:39'),
(5, '首饰', '2025-09-28 14:47:05', NULL);

-- ----------------------------
-- Table structure for gift_product_rule_value
-- ----------------------------
DROP TABLE IF EXISTS `gift_product_rule_value`;
CREATE TABLE `gift_product_rule_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_id` int(11) DEFAULT NULL COMMENT '规格id',
  `value` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '规格属性名称',
  `detail` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '规格属性值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='商品规格属性';

-- ----------------------------
-- Records of gift_product_rule_value
-- ----------------------------
INSERT INTO `gift_product_rule_value` (`id`, `rule_id`, `value`, `detail`) VALUES
(1, 1, '大', '大,小'),
(3, 2, '颜色', '白,黑,红'),
(4, 2, '长度', '10,15,20'),
(8, 1, '颜色', '白,黑'),
(9, 5, '颜色', '黑色,白色'),
(10, 5, '包邮', '是,否');

-- ----------------------------
-- Table structure for gift_product_sku
-- ----------------------------
DROP TABLE IF EXISTS `gift_product_sku`;
CREATE TABLE `gift_product_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `sku_img` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'sku图片',
  `sku_origin_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
  `sku_price` decimal(10,2) DEFAULT NULL COMMENT 'sku商品售价',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `sales` int(11) DEFAULT NULL COMMENT '销量',
  `detail_json` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'sku信息，json封装',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='商品SKU';

-- ----------------------------
-- Records of gift_product_sku
-- ----------------------------
INSERT INTO `gift_product_sku` (`id`, `product_id`, `sku_img`, `sku_origin_price`, `sku_price`, `stock`, `sales`, `detail_json`) VALUES
(54, 13, 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/28/04dc22fe8836f6a575a1aaabd6c3957f.png', '999.00', '100.00', 78, NULL, NULL),
(59, 11, 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/26/5747c70b24e61bcde80af0c194b5b86d.jpeg', '30.00', '20.00', 0, NULL, NULL),
(68, 14, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/831ea1a6-3970-4695-95a5-75b49e523bb9.jpg', '199.00', '100.00', 0, NULL, '黑色,是'),
(69, 14, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/831ea1a6-3970-4695-95a5-75b49e523bb9.jpg', '199.00', '100.00', 0, NULL, '黑色,否'),
(70, 14, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/831ea1a6-3970-4695-95a5-75b49e523bb9.jpg', '199.00', '100.00', 0, NULL, '白色,是'),
(71, 14, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/09/831ea1a6-3970-4695-95a5-75b49e523bb9.jpg', '199.00', '100.00', 0, NULL, '白色,否'),
(72, 10, 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/26/f4aa5896dbde518858cf6b4da7339775.jpg', NULL, '111.00', 0, NULL, '大,白'),
(73, 10, 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/26/f4aa5896dbde518858cf6b4da7339775.jpg', NULL, '111.00', 0, NULL, '大,黑'),
(74, 10, 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/26/f4aa5896dbde518858cf6b4da7339775.jpg', NULL, '111.00', 0, NULL, '小,白'),
(75, 10, 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/26/f4aa5896dbde518858cf6b4da7339775.jpg', NULL, '111.00', 0, NULL, '小,黑'),
(76, 1, 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/d3fc341e3df910239050e42f714ff97f.png', '99.00', '99.00', 194, NULL, '大,白'),
(77, 1, 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/d3fc341e3df910239050e42f714ff97f.png', '99.00', '99.00', 193, NULL, '大,黑'),
(78, 1, 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/d3fc341e3df910239050e42f714ff97f.png', '99.00', '99.00', 197, NULL, '小,白'),
(79, 1, 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/d3fc341e3df910239050e42f714ff97f.png', '99.00', '99.00', 203, NULL, '小,黑'),
(80, 15, 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/16/13108619-ea41-412b-a0bd-c2fbbfe9b4b8.png', '120.00', '0.02', 96, NULL, NULL);

-- ----------------------------
-- Table structure for gift_product_type
-- ----------------------------
DROP TABLE IF EXISTS `gift_product_type`;
CREATE TABLE `gift_product_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id，父级为0时为一级',
  `name` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分类名称',
  `img` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='商品分类';

-- ----------------------------
-- Records of gift_product_type
-- ----------------------------
INSERT INTO `gift_product_type` (`id`, `parent_id`, `name`, `img`, `create_time`, `update_time`, `sort`) VALUES
(1, 0, '手机配件', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2025/10/15/37287ffc-0ed2-4ac9-a79a-26e2da389a1a.png', '2025-07-30 19:48:39', '2025-10-15 17:51:23', 1),
(3, 0, '日常用品', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2025/10/15/b4429deb-661a-44a1-b3f6-320127b09515.png', '2025-07-30 19:48:39', '2025-10-15 17:52:05', 2),
(4, 0, '首饰', 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/28/e71811e42b39cbd5ba93fc79ec5bc50b.png', '2025-07-30 19:48:39', NULL, 3),
(5, 0, '车模型', 'https://tcxx.xianmaxiong.com/file/uploadPath/2025/09/28/fd792cba0c645c40011e075c9998be27.png', '2025-07-30 19:48:39', NULL, 4),
(6, 0, '零食商品', 'https://lwsc.xianmxkj.com/sqx_fast/upload/2026/01/16/176070c6-8a39-4b1a-8578-510bfafff7b8.png', '2026-01-16 10:41:01', '2026-01-16 10:41:12', 5);

-- ----------------------------
-- Table structure for gift_scene
-- ----------------------------
DROP TABLE IF EXISTS `gift_scene`;
CREATE TABLE `gift_scene` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '送礼场景名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='送礼场景';

-- ----------------------------
-- Records of gift_scene
-- ----------------------------
INSERT INTO `gift_scene` (`id`, `name`, `create_time`, `update_time`) VALUES
(1, '生日', '2025-08-04 10:14:26', NULL),
(2, '婚礼', '2025-08-04 10:14:37', NULL),
(3, '搬家', '2025-08-04 10:14:48', NULL),
(4, '生子', '2025-08-04 10:14:58', NULL);

-- ----------------------------
-- Table structure for gift_share
-- ----------------------------
DROP TABLE IF EXISTS `gift_share`;
CREATE TABLE `gift_share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '分享用户id',
  `receive_user_id` bigint(20) DEFAULT NULL COMMENT '领取用户id',
  `status` int(11) DEFAULT NULL COMMENT '状态 1待领取 2已领取 3已使用 4已拒绝',
  `receive_time` datetime DEFAULT NULL COMMENT '领取时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='礼物分享表';
-- Data for gift_share cleared (privacy / demo)

-- ----------------------------
-- Table structure for gift_target
-- ----------------------------
DROP TABLE IF EXISTS `gift_target`;
CREATE TABLE `gift_target` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '送礼对象名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='送礼对象';

-- ----------------------------
-- Records of gift_target
-- ----------------------------
INSERT INTO `gift_target` (`id`, `name`, `create_time`, `update_time`) VALUES
(1, '好友', '2025-08-04 10:18:01', NULL),
(2, '父母', '2025-08-04 10:18:09', NULL),
(3, '陌生人', '2025-08-04 10:18:19', NULL),
(5, '男女朋友', '2025-09-28 14:54:57', NULL);

-- ----------------------------
-- Table structure for gift_user_collect
-- ----------------------------
DROP TABLE IF EXISTS `gift_user_collect`;
CREATE TABLE `gift_user_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户收藏商品表';
-- Data for gift_user_collect cleared (privacy / demo)

-- ----------------------------
-- Table structure for gift_user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `gift_user_coupon`;
CREATE TABLE `gift_user_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `failure_time` datetime NOT NULL COMMENT '失效时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（1未使用 2已使用 3已过期）',
  `coupon_id` int(11) DEFAULT NULL COMMENT '优惠券id',
  `type` tinyint(1) DEFAULT NULL COMMENT '优惠券类型（1通用券 2商品券）',
  `coupon_issue_id` int(11) DEFAULT NULL COMMENT '优惠券发布id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='优惠券用户领取记录';
-- Data for gift_user_coupon cleared (privacy / demo)

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice` (
  `invoice_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '发票id',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '申请人id',
  `user_type` tinyint(4) DEFAULT NULL COMMENT '用户类型 1公司 2个人',
  `invoice_type` int(11) DEFAULT NULL COMMENT '发票类型',
  `invoice_content` text COLLATE utf8mb4_bin COMMENT '发票内容',
  `invoice_header` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发票抬头',
  `tax_payer_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '纳税人识别号',
  `bank_address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '注册地址',
  `bank_phone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '注册电话',
  `bank_deposit` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '开户行',
  `bank_number` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '银行卡号',
  `invoice_money` decimal(19,2) DEFAULT NULL COMMENT '发票金额',
  `user_email` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '申请人电子邮件',
  `invoice_title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发票标题',
  `user_phone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '申请人手机号码',
  `invoicing_time` datetime DEFAULT NULL COMMENT '开票时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `indent_number` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单号',
  `status` tinyint(4) DEFAULT NULL COMMENT '1待开票 2已开票 3拒绝开票',
  PRIMARY KEY (`invoice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='发票信息表';
-- Data for invoice cleared (privacy / demo)

-- ----------------------------
-- Table structure for message_info
-- ----------------------------
DROP TABLE IF EXISTS `message_info`;
CREATE TABLE `message_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `content` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '内容',
  `create_at` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  `image` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片',
  `is_see` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `send_state` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `send_time` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `state` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分类',
  `title` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
  `url` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地址',
  `type` varchar(600) COLLATE utf8mb4_bin DEFAULT NULL,
  `platform` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='消息';
-- Data for message_info cleared (privacy / demo)

-- ----------------------------
-- Table structure for msg
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短信验证码',
  `phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_name` (`code`,`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='验证码';
-- Data for msg cleared (privacy / demo)

-- ----------------------------
-- Table structure for pay_details
-- ----------------------------
DROP TABLE IF EXISTS `pay_details`;
CREATE TABLE `pay_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '充值id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `platform` int(11) DEFAULT NULL COMMENT '支付平台 1支付宝 2微信  5余额',
  `trade_type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付类型 应用：APP 网页：WEB 小程序：JSAPI',
  `order_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单编号',
  `trade_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '第三方平台支付流水号',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `business_type` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '业务类型',
  `state` tinyint(4) DEFAULT NULL COMMENT '0待支付 1支付成功 2失败',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `extend` text COLLATE utf8mb4_bin COMMENT '扩展字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=397 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户支付详情';
-- Data for pay_details cleared (privacy / demo)

-- ----------------------------
-- Table structure for recharge_config
-- ----------------------------
DROP TABLE IF EXISTS `recharge_config`;
CREATE TABLE `recharge_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='充值金额配置';

-- ----------------------------
-- Records of recharge_config
-- ----------------------------
INSERT INTO `recharge_config` (`id`, `amount`, `create_time`, `update_time`) VALUES
(1, '100.00', '2025-08-06 14:57:10', NULL),
(2, '1000.00', '2025-08-06 14:57:23', NULL),
(3, '10000.00', '2025-08-06 14:57:30', NULL),
(4, '10.00', '2025-08-06 14:57:36', NULL),
(5, '1.00', '2025-08-06 14:57:41', NULL),
(6, '0.10', '2025-08-06 14:57:48', NULL),
(7, '0.01', '2025-08-06 14:57:54', NULL);

-- ----------------------------
-- Table structure for referral_commission
-- ----------------------------
DROP TABLE IF EXISTS `referral_commission`;
CREATE TABLE `referral_commission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `rate` decimal(10,2) NOT NULL COMMENT '佣金比例',
  `amount` decimal(10,2) NOT NULL COMMENT '佣金金额',
  `type` int(11) DEFAULT NULL COMMENT '类型 0: 提现 1: 订单佣金 2: 充值佣金',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='佣金信息';

-- ----------------------------
-- Records of referral_commission
-- ----------------------------
INSERT INTO `referral_commission` (`id`, `user_id`, `order_id`, `rate`, `amount`, `type`, `create_time`, `update_time`) VALUES
(1, 4, 27, '0.10', '19.90', 1, '2025-08-04 17:06:12', NULL),
(2, 4, 28, '0.10', '9.90', 1, '2025-08-04 17:13:57', NULL),
(3, 4, 30, '0.10', '9.90', 1, '2025-08-04 17:48:35', NULL),
(4, 4, 31, '0.10', '19.90', 1, '2025-08-05 09:41:33', NULL),
(5, 8, 91, '0.11', '22.00', 1, '2025-10-13 18:12:10', NULL),
(6, 8, 96, '0.20', '60.00', 1, '2025-10-14 15:01:34', NULL),
(7, 8, 158, '0.20', '40.00', 1, '2025-10-15 16:45:58', NULL),
(8, 12, 159, '0.10', '20.00', 1, '2025-10-15 16:48:50', NULL);

-- ----------------------------
-- Table structure for referral_user_commission
-- ----------------------------
DROP TABLE IF EXISTS `referral_user_commission`;
CREATE TABLE `referral_user_commission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `commission` decimal(10,2) NOT NULL COMMENT '佣金',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户佣金';
-- Data for referral_user_commission cleared (privacy / demo)

-- ----------------------------
-- Table structure for search_history
-- ----------------------------
DROP TABLE IF EXISTS `search_history`;
CREATE TABLE `search_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `value` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '词',
  `create_time` datetime NOT NULL COMMENT '搜索时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='搜索历史';
-- Data for search_history cleared (privacy / demo)

-- ----------------------------
-- Table structure for sms_template
-- ----------------------------
DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `biz_code` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '业务标识 例如: login register',
  `content` text COLLATE utf8mb4_bin NOT NULL COMMENT '模板内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='短信模板';

-- ----------------------------
-- Records of sms_template
-- ----------------------------
INSERT INTO `sms_template` (`id`, `biz_code`, `content`, `create_time`, `update_time`) VALUES
(1, 'login', '【%s】验证码：%s,此验证码可用于登录或注册，10分钟内有效，如非您本人操作，可忽略本条消息。', '2025-07-09 13:12:39', '2025-07-30 13:52:33'),
(2, 'register', '【%s】验证码：%s,此验证码可用于登录或注册，10分钟内有效，如非您本人操作，可忽略本条消息。', '2025-07-09 13:13:21', '2025-07-30 13:52:37'),
(3, 'common', '【%s】您的验证码是%s。如非本人操作，请忽略本短信', '2025-07-09 13:14:02', '2025-07-30 13:52:39');

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) COLLATE utf8mb4_bin NOT NULL COMMENT 'uuid',
  `code` varchar(6) COLLATE utf8mb4_bin NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统验证码';
-- Data for sys_captcha cleared (privacy / demo)

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `param_key` (`param_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` (`id`, `param_key`, `param_value`, `status`, `remark`) VALUES
(1, 'CLOUD_STORAGE_CONFIG_KEY', '{"aliyunAccessKeyId":"","aliyunAccessKeySecret":"","aliyunBucketName":"","aliyunDomain":"","aliyunEndPoint":"","aliyunPrefix":"","qcloudBucketName":"","qcloudDomain":"","qcloudPrefix":"","qcloudSecretId":"","qcloudSecretKey":"","qiniuAccessKey":"","qiniuBucketName":"","qiniuDomain":"","qiniuPrefix":"upload","qiniuSecretKey":"","type":1}', 0, '云存储配置信息');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '字典名称',
  `type` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '字典类型',
  `code` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT '字典码',
  `value` varchar(1000) COLLATE utf8mb4_bin NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `type` (`type`,`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='数据字典表';
-- No data for sys_dict

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统日志';
-- Data for sys_log cleared (privacy / demo)

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES
(2, 0, '管理员列表', 'user', NULL, 1, 'svg:zhanghao.svg', 10),
(3, 0, '角色管理', 'role', NULL, 1, 'svg:user-fill.svg', 11),
(4, 0, '菜单管理', 'menu', NULL, 1, 'svg:icon-drag.svg', 12),
(15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0),
(16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0),
(17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0),
(18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0),
(19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0),
(20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0),
(21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0),
(22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0),
(23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0),
(24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0),
(25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0),
(26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0),
(32, 0, '用户中心', 'userList', '', 1, 'svg:user-group-fill.svg', 2),
(33, 0, '数据中心', 'home', '', 1, 'svg:Directory-tree.svg', 1),
(34, 0, '财务中心', 'financeList', '', 1, 'svg:supervise.svg', 2),
(35, 34, '查看', '', 'financeList:list', 2, '', 0),
(36, 34, '转账', '', 'financeList:transfer', 2, '', 0),
(37, 34, '退款', '', 'financeList:refund', 2, '', 0),
(38, 34, '退款', '', 'financeList:add', 2, '', 0),
(39, 34, '修改', '', 'financeList:update', 2, '', 0),
(40, 34, '删除', '', 'financeList:delete', 2, '', 0),
(41, 0, '消息中心', 'message', '', 1, 'svg:commentdots-fill.svg', 3),
(42, 41, '查看', '', 'message:list', 2, '', 0),
(43, 41, '消息推送', '', 'message:push', 2, '', 0),
(50, 0, '首页装修', 'bannerList', '', 1, 'svg:database-plus.svg', 3),
(51, 50, '查看', '', 'bannerList:list', 2, '', 0),
(52, 50, '添加', '', 'bannerList:add', 2, '', 0),
(53, 50, '修改', '', 'bannerList:update', 2, '', 0),
(54, 50, '删除', '', 'bannerList:delete', 2, '', 0),
(57, 0, '系统配置', 'allocationList', '', 1, 'svg:laptop-check.svg', 9),
(58, 57, '查看', '', 'allocationList:list', 2, '', 0),
(59, 57, '修改', '', 'allocationList:update', 2, '', 0),
(60, 32, '查看', '', 'userList:list', 2, '', 0),
(61, 32, '删除', '', 'userList:delete', 2, '', 0),
(62, 0, '订单中心', 'orderCenter', '', 1, 'svg:chart-area.svg', 4),
(63, 62, '查看', '', '', 2, '', 0),
(64, 62, '删除', '', 'orderCenter:delete', 2, '', 0),
(69, 41, '添加', '', 'message:add', 2, '', 0),
(70, 41, '修改', '', 'message:update', 2, '', 0),
(71, 41, '删除', '', 'message:delete', 2, '', 0),
(72, 0, '卡片管理', 'locality', '', 1, 'svg:picture.svg', 6),
(73, 72, '添加', '', 'locality:add', 2, '', 0),
(74, 72, '查看', '', 'locality:list', 2, '', 0),
(75, 72, '修改', '', 'locality:update', 2, '', 0),
(76, 72, '删除', '', 'locality:delete', 2, '', 0),
(77, 0, '实名认证', 'autonym', '', 1, 'shangpin', 6),
(78, 77, '列表', '', 'autonym:list', 2, '', 0),
(79, 0, '聊天室', 'vueMchat', '', 1, 'xiaoxi', 9),
(80, 0, '会员配置', 'memberDetails', '', 1, 'fenleilist', 8),
(81, 80, '查看', '', 'memberDetails:list', 2, '', 0),
(82, 80, '添加', '', 'memberDetails:add', 2, '', 0),
(83, 80, '修改', '', 'memberDetails:update', 2, '', 0),
(84, 80, '删除', '', 'memberDetails:delete', 2, '', 0),
(85, 0, '会员特权', 'vipPrivilege', '', 1, 'geren', 8),
(86, 85, '列表', '', 'vipPrivilege:list', 2, '', 0),
(87, 85, '添加', '', 'vipPrivilege:add', 2, '', 0),
(88, 85, '修改', '', 'vipPrivilege:update', 2, '', 0),
(89, 85, '删除', '', 'vipPrivilege:delete', 2, '', 0),
(90, 32, '修改积分', '', 'userList:updatejf', 2, '', 0),
(91, 32, '修改用户状态', '', 'userList:updateStatus', 2, '', 0),
(92, 77, '通过', '', 'autonym:tongguo', 2, '', 0),
(93, 77, '拒绝', '', 'autonym:jujue', 2, '', 0),
(94, 0, '升级配置', 'app', '', 1, 'sql', 9),
(95, 94, '查看', '', 'app:list', 2, '', 0),
(96, 94, '添加', '', 'app:add', 2, '', 0),
(97, 94, '修改', '', 'app:update', 2, '', 0),
(98, 94, '删除', '', 'app:delete', 2, '', 0),
(99, 0, '用户中心', 'userListRm', '', 1, 'role', 9),
(100, 99, '查看', '', 'userListRm:list', 2, '', 0),
(101, 0, '修改密码', 'updatePasswordRm', '', 1, 'sql', 9),
(102, 101, '修改', '', 'updatePasswordRm:update', 2, '', 0),
(103, 0, '聊天记录', 'chatRecord', '', 1, 'order', 9),
(104, 103, '查看', '', 'chatRecord:list', 2, '', 0),
(105, 0, '商品管理', NULL, NULL, 0, 'svg:all.svg', 5),
(106, 105, '商品列表', 'shopAdmin', NULL, 1, 'svg:alignright-fill.svg', 1),
(107, 106, '查看', NULL, 'shopAdmin:list', 2, NULL, NULL),
(108, 106, '修改', NULL, 'shopAdmin:update', 2, NULL, NULL),
(109, 106, '删除', NULL, 'shopAdmin:delete', 2, NULL, NULL),
(110, 105, '商品分类', 'classifyAdmin', '2', 1, 'svg:database.svg', 2),
(111, 110, '查看', NULL, 'classifyAdmin:list', 2, NULL, NULL),
(112, 110, '添加', NULL, 'classifyAdmin:add', 2, NULL, NULL),
(113, 110, '修改', NULL, 'classifyAdmin:update', 2, NULL, NULL),
(114, 110, '删除', NULL, 'classifyAdmin:delete', 2, NULL, NULL),
(115, 105, '商品规格', 'specification', NULL, 1, 'svg:gallery-view.svg', 3),
(116, 115, '查看', NULL, 'specification:list', 2, NULL, NULL),
(117, 115, '添加', NULL, 'specification:add', 2, NULL, NULL),
(118, 115, '修改', NULL, 'specification:update', 2, NULL, NULL),
(119, 115, '删除', NULL, 'specification:delete', 2, NULL, NULL),
(120, 0, '筛选配置', 'Filter', NULL, 1, 'svg:tag.svg', 6),
(121, 120, '查看', NULL, 'Filter:list', 2, NULL, NULL),
(122, 120, '添加', NULL, 'Filter:add', 2, NULL, NULL),
(123, 120, '修改', NULL, 'Filter:update', 2, NULL, NULL),
(124, 120, '删除', NULL, 'Filter:delete', 2, NULL, NULL),
(125, 0, '祝福语管理', 'blessing', NULL, 1, 'svg:g.svg', 7),
(126, 125, '查看', NULL, 'blessing:list', 2, NULL, NULL),
(127, 125, '添加', NULL, 'blessing:add', 2, NULL, NULL),
(128, 125, '修改', NULL, 'blessing:update', 2, NULL, NULL),
(129, 125, '删除', NULL, 'blessing:delete', 2, NULL, NULL),
(130, 0, '搜索记录', 'search', NULL, 1, 'svg:map-fill.svg', 8),
(131, 130, '查看', NULL, 'search:list', 2, NULL, NULL),
(132, 0, '发票管理', 'invoice', NULL, 1, 'svg:fankui.svg', 8),
(133, 132, '查看', NULL, 'invoice:list', 2, NULL, NULL),
(134, 0, '充值配置', 'IntegralGoods', NULL, 1, 'svg:chart-line.svg', 8),
(135, 134, '查看', NULL, 'IntegralGoods:list', 2, NULL, NULL),
(136, 134, '添加', NULL, 'IntegralGoods:add', 2, NULL, NULL),
(137, 134, '修改', NULL, 'IntegralGoods:update', 2, NULL, NULL),
(138, 134, '删除', NULL, 'IntegralGoods:delete', 2, NULL, NULL),
(139, 0, '注销管理', 'messageZx', NULL, 1, 'svg:commentlines-fill.svg', 3),
(140, 139, '查看', NULL, 'messageZx:list', 2, NULL, NULL),
(141, 139, '审核', NULL, 'messageZx:shenhe', 2, NULL, NULL),
(142, 132, '修改', NULL, 'invoice:update', 2, NULL, NULL),
(143, 32, '修改金额', NULL, 'userList:updateMoney', 2, NULL, NULL);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='文件上传';
-- Data for sys_oss cleared (privacy / demo)

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` (`role_id`, `role_name`, `remark`, `create_user_id`, `create_time`) VALUES
(1, '超级管理员', 'super', 2, '2021-06-04 17:38:28'),
(4, '普通用户', '普通用户', 6, '2021-09-10 15:33:47');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2213 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
(1676, 4, 33),
(1677, 4, 60),
(1678, 4, 35),
(1679, 4, 42),
(1680, 4, 51),
(1681, 4, 63),
(1682, 4, 74),
(1683, 4, 77),
(1684, 4, 78),
(1685, 4, 92),
(1686, 4, 93),
(1687, 4, 81),
(1688, 4, 86),
(1689, 4, 58),
(1690, 4, 79),
(1691, 4, 95),
(1692, 4, 15),
(1693, 4, 19),
(1694, 4, 23),
(1695, 4, -666666),
(1696, 4, 32),
(1697, 4, 34),
(1698, 4, 41),
(1699, 4, 50),
(1700, 4, 62),
(1701, 4, 72),
(1702, 4, 80),
(1703, 4, 85),
(1704, 4, 57),
(1705, 4, 94),
(1706, 4, 2),
(1707, 4, 3),
(1708, 4, 4),
(2123, 1, 33),
(2124, 1, 32),
(2125, 1, 143),
(2126, 1, 60),
(2127, 1, 61),
(2128, 1, 90),
(2129, 1, 91),
(2130, 1, 34),
(2131, 1, 35),
(2132, 1, 36),
(2133, 1, 37),
(2134, 1, 38),
(2135, 1, 39),
(2136, 1, 40),
(2137, 1, 41),
(2138, 1, 42),
(2139, 1, 43),
(2140, 1, 69),
(2141, 1, 70),
(2142, 1, 71),
(2143, 1, 50),
(2144, 1, 51),
(2145, 1, 52),
(2146, 1, 53),
(2147, 1, 54),
(2148, 1, 139),
(2149, 1, 140),
(2150, 1, 141),
(2151, 1, 62),
(2152, 1, 63),
(2153, 1, 64),
(2154, 1, 105),
(2155, 1, 106),
(2156, 1, 107),
(2157, 1, 108),
(2158, 1, 109),
(2159, 1, 110),
(2160, 1, 111),
(2161, 1, 112),
(2162, 1, 113),
(2163, 1, 114),
(2164, 1, 115),
(2165, 1, 116),
(2166, 1, 117),
(2167, 1, 118),
(2168, 1, 119),
(2169, 1, 72),
(2170, 1, 73),
(2171, 1, 74),
(2172, 1, 75),
(2173, 1, 76),
(2174, 1, 120),
(2175, 1, 121),
(2176, 1, 122),
(2177, 1, 123),
(2178, 1, 124),
(2179, 1, 125),
(2180, 1, 126),
(2181, 1, 127),
(2182, 1, 128),
(2183, 1, 129),
(2184, 1, 130),
(2185, 1, 131),
(2186, 1, 132),
(2187, 1, 133),
(2188, 1, 142),
(2189, 1, 134),
(2190, 1, 135),
(2191, 1, 136),
(2192, 1, 137),
(2193, 1, 138),
(2194, 1, 57),
(2195, 1, 58),
(2196, 1, 59),
(2197, 1, 2),
(2198, 1, 15),
(2199, 1, 16),
(2200, 1, 17),
(2201, 1, 18),
(2202, 1, 3),
(2203, 1, 19),
(2204, 1, 20),
(2205, 1, 21),
(2206, 1, 22),
(2207, 1, 4),
(2208, 1, 23),
(2209, 1, 24),
(2210, 1, 25),
(2211, 1, 26),
(2212, 1, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '盐',
  `email` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `inviter_code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` (`user_id`, `username`, `password`, `salt`, `email`, `mobile`, `status`, `create_user_id`, `create_time`, `inviter_code`) VALUES
(1, 'admin', '1cc7f0f4e29524bfbfb2a299dde135e6ebad047b2c0ef70eceb7fbc198293927', 'YKJpTRPDurwTYVsIhd0P', 'admin@example.com', '13800000000', 1, 1, '2016-11-11 11:11:11', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES
(1, 1, 1);

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统用户Token';
-- Data for sys_user_token cleared (privacy / demo)

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `phone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `sex` int(11) DEFAULT NULL COMMENT '性别 1男 2女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `open_id` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '微信小程序openId',
  `wx_open_id` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '微信App  openId',
  `password` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `apple_id` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '苹果id',
  `sys_phone` int(11) DEFAULT NULL COMMENT '手机类型 1安卓 2ios',
  `status` int(11) DEFAULT NULL COMMENT '状态 1正常 2禁用',
  `platform` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '来源 APP 小程序  公众号',
  `invitation_code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邀请码',
  `clientid` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'app推送id',
  `is_send_msg` int(11) DEFAULT NULL COMMENT '是否推送消息',
  `inviter_id` bigint(20) DEFAULT NULL COMMENT '邀请人id',
  `commission_rate` double DEFAULT NULL COMMENT '佣金比例',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户';
-- Data for tb_user cleared (privacy / demo)

-- ----------------------------
-- Table structure for user_browse
-- ----------------------------
DROP TABLE IF EXISTS `user_browse`;
CREATE TABLE `user_browse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `by_browse_id` bigint(20) DEFAULT NULL COMMENT '浏览用户id',
  `update_time` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新时间',
  `taking_id` bigint(20) DEFAULT NULL COMMENT '接单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户浏览';
-- Data for user_browse cleared (privacy / demo)

-- ----------------------------
-- Table structure for user_money
-- ----------------------------
DROP TABLE IF EXISTS `user_money`;
CREATE TABLE `user_money` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '钱包id',
  `money` decimal(10,2) DEFAULT NULL COMMENT '钱包金额',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户钱包';
-- Data for user_money cleared (privacy / demo)

-- ----------------------------
-- Table structure for user_money_details
-- ----------------------------
DROP TABLE IF EXISTS `user_money_details`;
CREATE TABLE `user_money_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '钱包详情id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `by_user_id` int(11) DEFAULT NULL COMMENT '邀请用户id',
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
  `classify` int(11) DEFAULT NULL COMMENT '1注册   2购买',
  `type` int(11) DEFAULT NULL COMMENT '类型（1增加 2减少）',
  `state` int(11) DEFAULT '1' COMMENT '状态 1待支付 2已到账 3取消',
  `money` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '内容',
  `create_time` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户钱包记录';
-- Data for user_money_details cleared (privacy / demo)

-- ----------------------------
-- Table structure for verify_code
-- ----------------------------
DROP TABLE IF EXISTS `verify_code`;
CREATE TABLE `verify_code` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `phone` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '手机号',
  `code` varchar(12) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '验证码',
  `status` tinyint(1) NOT NULL COMMENT '状态 1: 未使用 2: 已使用',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='验证码';
-- Data for verify_code cleared (privacy / demo)

-- ----------------------------
-- Table structure for vip_details
-- ----------------------------
DROP TABLE IF EXISTS `vip_details`;
CREATE TABLE `vip_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `vip_name_type` int(11) DEFAULT NULL COMMENT '会员类型0月1季2年',
  `money` decimal(10,0) DEFAULT NULL COMMENT '会员价格',
  `vip_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '会员名称',
  `rate` decimal(10,2) DEFAULT NULL,
  `award` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='会员信息';
-- Data for vip_details cleared (privacy / demo)

-- ----------------------------
-- Table structure for withdrawal_account
-- ----------------------------
DROP TABLE IF EXISTS `withdrawal_account`;
CREATE TABLE `withdrawal_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `account_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '账户名称',
  `account_number` text COLLATE utf8mb4_bin NOT NULL COMMENT '账户账号',
  `type` tinyint(4) NOT NULL COMMENT '账户类型: 1 微信 2 支付宝',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='提现账号';
-- Data for withdrawal_account cleared (privacy / demo)

-- ----------------------------
-- Table structure for withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `withdraw_record`;
CREATE TABLE `withdraw_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `actual_amount` decimal(10,2) DEFAULT NULL COMMENT '实际到账金额',
  `service_fee` decimal(10,2) DEFAULT NULL COMMENT '手续费',
  `status` int(11) DEFAULT NULL COMMENT '状态 1发起提现 2提现成功 3拒绝提现',
  `reason` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '拒绝理由',
  `account_type` int(11) DEFAULT NULL COMMENT '提现账户 1微信 2支付宝',
  `withdraw_time` datetime DEFAULT NULL COMMENT '转账时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='提现记录';
-- Data for withdraw_record cleared (privacy / demo)

SET FOREIGN_KEY_CHECKS = 1;
