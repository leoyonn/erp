-- user role table
-- @author leo
-- @data 2013-05-15

CREATE TABLE IF NOT EXISTS `userRole` (
    `id` BIGINT NOT NULL COMMENT '用户唯一标识',
    `cat` varchar(32) COMMENT '',
    `type` varchar(32) COMMENT '',
    `drole` varchar(64) COMMENT '',
    `frole` varchar(64) COMMENT '',
    `stat` varchar(64) COMMENT '',
    PRIMARY KEY (`id`),
    UNIQUE(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
