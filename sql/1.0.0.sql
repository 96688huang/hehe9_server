/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : dmvcd_server

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-06-20 16:56:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comic
-- ----------------------------
DROP TABLE IF EXISTS `comic`;
CREATE TABLE `comic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `source_id` int(11) NOT NULL COMMENT '来源id',
  `name` varchar(255) NOT NULL COMMENT '视频名称',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `place` varchar(20) DEFAULT NULL COMMENT '产地',
  `birth_year` varchar(50) DEFAULT NULL COMMENT '年份',
  `read_count_weekly` varchar(100) DEFAULT NULL COMMENT '周阅读量',
  `read_count_total` varchar(100) DEFAULT NULL COMMENT '总阅读量',
  `story_line` varchar(1024) DEFAULT NULL COMMENT '剧情',
  `poster_big_url` varchar(255) DEFAULT NULL COMMENT '大海报url',
  `poster_mid_url` varchar(255) DEFAULT NULL COMMENT '中海报url',
  `poster_small_url` varchar(255) DEFAULT NULL COMMENT '小海报url',
  `icon_url` varchar(255) DEFAULT NULL COMMENT '图标url',
  `list_page_url` varchar(255) NOT NULL COMMENT '视频列表页面url',
  `update_remark` varchar(255) DEFAULT NULL COMMENT '更新备注',
  `serialize_status` varchar(20) DEFAULT NULL COMMENT '连载状态',
  `types` varchar(255) DEFAULT NULL COMMENT '类型，多个时以`号分隔',
  `first_char` varchar(20) DEFAULT NULL COMMENT '漫画名称首字母，或英文首字母',
  `rank` int(11) NOT NULL DEFAULT '10000' COMMENT '排名, 越小越靠前',
  `hot` int(11) NOT NULL DEFAULT '0' COMMENT '热度',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_first_char` (`first_char`),
  KEY `idx_types` (`types`),
  KEY `idx_source_id_list_page_url` (`source_id`,`list_page_url`)
) ENGINE=InnoDB AUTO_INCREMENT=23867 DEFAULT CHARSET=utf8 COMMENT='漫画信息';

-- ----------------------------
-- Table structure for comic_episode
-- ----------------------------
DROP TABLE IF EXISTS `comic_episode`;
CREATE TABLE `comic_episode` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `comic_id` int(11) NOT NULL COMMENT '漫画id',
  `title` varchar(255) DEFAULT NULL COMMENT '分集标题',
  `episode_no` int(11) NOT NULL DEFAULT '1' COMMENT '第几集',
  `read_page_url` varchar(255) DEFAULT NULL COMMENT '阅读页面url',
  `pic_urls` varchar(255) DEFAULT NULL COMMENT '图片url,多张时以`号分隔',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `comic_id_episode_no` (`comic_id`,`episode_no`),
  KEY `comic_id_play_page_url` (`comic_id`,`read_page_url`)
) ENGINE=InnoDB AUTO_INCREMENT=172478 DEFAULT CHARSET=utf8 COMMENT='漫画分集信息';

-- ----------------------------
-- Table structure for comic_source
-- ----------------------------
DROP TABLE IF EXISTS `comic_source`;
CREATE TABLE `comic_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `name` varchar(255) NOT NULL COMMENT '采集网站名称',
  `root_url` varchar(255) NOT NULL COMMENT '根域名',
  `collect_page_url` varchar(255) NOT NULL COMMENT '采集页面url',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='漫画来源信息';

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `source_id` int(11) NOT NULL COMMENT '来源id',
  `name` varchar(255) NOT NULL COMMENT '视频名称',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `place` varchar(20) DEFAULT NULL COMMENT '产地',
  `birth_year` varchar(50) DEFAULT NULL COMMENT '年份',
  `play_count_weekly` varchar(100) DEFAULT NULL COMMENT '周播放量',
  `play_count_total` varchar(100) DEFAULT NULL COMMENT '总播放量',
  `story_line` varchar(1024) DEFAULT NULL COMMENT '剧情',
  `story_line_brief` varchar(1024) DEFAULT NULL COMMENT '剧情简介',
  `poster_big_url` varchar(255) DEFAULT NULL COMMENT '大海报url',
  `poster_mid_url` varchar(255) DEFAULT NULL COMMENT '中海报url',
  `poster_small_url` varchar(255) DEFAULT NULL COMMENT '小海报url',
  `icon_url` varchar(255) DEFAULT NULL COMMENT '图标url',
  `list_page_url` varchar(255) NOT NULL COMMENT '视频列表页面url',
  `update_remark` varchar(255) DEFAULT NULL COMMENT '更新备注',
  `first_char` varchar(20) DEFAULT NULL COMMENT '视频名称首字母，或英文首字母',
  `rank` int(11) NOT NULL DEFAULT '10000' COMMENT '排名, 越小越靠前',
  `hot` int(11) NOT NULL DEFAULT '0' COMMENT '热度',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_first_char` (`first_char`),
  KEY `idx_source_id_list_page_url` (`source_id`,`list_page_url`)
) ENGINE=InnoDB AUTO_INCREMENT=3225 DEFAULT CHARSET=utf8 COMMENT='视频信息';

-- ----------------------------
-- Table structure for video_episode
-- ----------------------------
DROP TABLE IF EXISTS `video_episode`;
CREATE TABLE `video_episode` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `video_id` int(11) NOT NULL COMMENT '视频id',
  `title` varchar(1024) DEFAULT NULL COMMENT '视频标题',
  `episode_no` int(11) NOT NULL DEFAULT '1' COMMENT '第几集',
  `play_page_url` varchar(255) DEFAULT NULL COMMENT '播放页面url',
  `snapshot_url` varchar(255) DEFAULT NULL COMMENT '视频截图url',
  `file_url` varchar(255) NOT NULL COMMENT '视频文件url',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `video_id_episode_no` (`video_id`,`episode_no`),
  KEY `video_id_play_page_url` (`video_id`,`play_page_url`)
) ENGINE=InnoDB AUTO_INCREMENT=51984 DEFAULT CHARSET=utf8 COMMENT='视频分集信息';

-- ----------------------------
-- Table structure for video_source
-- ----------------------------
DROP TABLE IF EXISTS `video_source`;
CREATE TABLE `video_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `name` varchar(255) NOT NULL COMMENT '采集网站名称',
  `root_url` varchar(255) NOT NULL COMMENT '根域名',
  `collect_page_url` varchar(255) NOT NULL COMMENT '采集页面url',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='视频来源信息';
