
CREATE TABLE `firmware_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '固件包名称',
  `version` varchar(50) NOT NULL COMMENT '固件版本',
  `download_url` varchar(255) NOT NULL COMMENT '固件包下载地址',
  `md5` varchar(32) NOT NULL COMMENT 'md5校验值',
  `upgrade_remark` text NOT NULL COMMENT '更新说明',
  `status` char(1) NOT NULL COMMENT '固件状态: A-未提交 S-提交 D-删除 ',
  `models` varchar(255) DEFAULT NULL COMMENT '适用的路由器型号',
  `update_versions` varchar(255) NOT NULL COMMENT '可升级到此固件的版本',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` datetime DEFAULT NULL COMMENT '录入时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '录入人',
  `mtime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='固件包信息';


CREATE TABLE `firmware_upgrade_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `firmware_id` int(11) NOT NULL COMMENT '固件id',
  `match_expression` text NOT NULL COMMENT '匹配表达式',
  `status` char(1) NOT NULL COMMENT '规则状态: A-已发布; B-未发布; C-暂停发布; D-关闭',
  `upgrade_start_time` datetime NOT NULL COMMENT '规则生效的起始时间',	
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` datetime DEFAULT NULL COMMENT '录入时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '录入人',
  `mtime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='固件升级规则';


CREATE TABLE `firmware_upgrade_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_id` int(11) NOT NULL COMMENT '固件升级规则id',
  `para_name` varchar(30) NOT NULL COMMENT '条件参数名称',
  `operator` varchar(10) NOT NULL COMMENT '操作符',
  `para_value` text NOT NULL COMMENT '条件数值, 多个数值时, 以英文逗号分隔',
  `status` char(1) NOT NULL COMMENT '状态: A - 正常; B - 删除',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` datetime DEFAULT NULL COMMENT '录入时间',
  `creator` varchar(20) DEFAULT NULL COMMENT '录入人',
  `mtime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='固件升级规则明细';


CREATE TABLE `firmware_upgrade_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_sn` varchar(50) NOT NULL COMMENT '序列号',
  `status` char(1) NOT NULL COMMENT '状态: A-发起固件升级请求; B-固件升级请求成功; C-固件升级请求失败; D-下载固件升级包; E-固件升级包下载成功; F-固件升级包下载失败; G-固件升级; H-固件升级执行成功; I-固件升级执行失败;',
  `previous_version` varchar(100) NOT NULL COMMENT '升级之前的版本',
  `upgrade_version` varchar(100) NOT NULL COMMENT '升级之后的版本',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` datetime DEFAULT NULL COMMENT '录入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='固件升级日志';


CREATE TABLE `flux` (                       
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceid` varchar(50) NOT NULL COMMENT '设备序列号',
  `cversion` varchar(50) DEFAULT NULL COMMENT '路由器固件版本',
  `upload` varchar(50) NOT NULL COMMENT '上行流量，字节数(byte)',
  `download` varchar(50) NOT NULL COMMENT '下行流量，字节数(byte)',
  `create_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `device_apps` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `weight` int(11) DEFAULT 10000 COMMENT '应用权重',
  `update_time` datetime DEFAULT NULL COMMENT '记录更新时间,数值与uc_app 表中的update_time相同;用于比较uc_app表上的记录是否有更新',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_app_id` (`app_id`),
  KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='路由器应用列表';


CREATE TABLE `device_apps_sync` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_sn` varchar(50) NOT NULL COMMENT '路由器序列号',
  `sync_time` datetime NOT NULL COMMENT '路由同步应用列表的时间',
  `status` enum('success','doing','failed') NOT NULL COMMENT '路由器更新本地应用的状态, 路由回调应用列表更新结果时,会修改为success或failed. 取值: success - 更新成功; doing - 更新中; failed - 更新失败; ',
  `modify_time` datetime DEFAULT NULL COMMENT '修改状态的时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_device_sn` (`device_sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='路由器应用列表同步记录';
