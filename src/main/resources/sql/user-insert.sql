﻿-- Generate Random Data
INSERT INTO PICC."user" (
    "id", "account", "name", "password",
    "avatar", "email", "phone", "tel",
    "desc", "corpId", "deptId",
    "province", "city",
    "opUserId", "updateTime", "createTime"
) VALUES (
    '0', 'superuser', 'superusername', 'superpass',
    'http://picc.com/1.jpg','testuser@picc.com', '13811818888', '95518',
    '这是个测试用户，testuser', 'CORP01', 'DEPT01',
    '河北省', '石家庄市',
    '0', sysdate, sysdate
);
