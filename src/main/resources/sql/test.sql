-- user table
-- @author leo
-- @data 2013-05-15

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL COMMENT '用户唯一标识',
    `account` varchar(64) NOT NULL COMMENT '',
    `name` varchar(64) NOT NULL COMMENT '',
    `password` varchar(64) NOT NULL COMMENT '',
    `avatar` varchar(128) COMMENT '',
    `email` varchar(64) COMMENT '',
    `phone` varchar(64) COMMENT '',
    `tel` varchar(64) COMMENT '',
    `desc` varchar(256) COMMENT '',
    `corpId` varchar(16) COMMENT '',
    `deptId` varchar(16) COMMENT '',
    `province` varchar(32) COMMENT '',
    `city` varchar(32) COMMENT '',
    `updateTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '',
    `createTime` TIMESTAMP COMMENT '',
    `opUserId` varchar(64) COMMENT '',
    `cat` varchar(32) COMMENT '',
    `type` varchar(32) COMMENT '',
    `drole` varchar(64) COMMENT '',
    `frole` varchar(64) COMMENT '',
    `stat` varchar(64) COMMENT '',
    PRIMARY KEY (`id`),
    UNIQUE(`id`, `account`)
) ;



