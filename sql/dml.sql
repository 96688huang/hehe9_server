alter table video 
change column `play_count_weekly` `play_count_weekly` varchar(100) DEFAULT NULL COMMENT '周播放量',
change column `play_count_total` `play_count_total` varchar(100) DEFAULT NULL COMMENT '总播放量';