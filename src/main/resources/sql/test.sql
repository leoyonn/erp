select * from PICC."user_info";
select * from PICC."user_role";
select * from PICC."func_role_info";
select * from PICC."func_role_funcs";
select * from PICC."func_role_users";
select * from PICC."data_role_info";
select * from PICC."data_role_scopes";
select * from PICC."data_role_users";
select * from PICC."corp";
select * from PICC."dept";

desc PICC."user_info";
desc PICC."user_role";
desc PICC."func_role_info";
desc PICC."func_role_funcs";
desc PICC."func_role_users";
desc PICC."data_role_info";
desc PICC."data_role_scopes";
desc PICC."data_role_users";
desc PICC."corp";
desc PICC."dept";

drop table PICC."user_info";
drop table PICC."user_role";
drop table PICC."func_role_info";
drop table PICC."func_role_funcs";
drop table PICC."func_role_users";
drop table PICC."data_role_info";
drop table PICC."data_role_scopes";
drop table PICC."data_role_users";
drop table PICC."corp";
drop table PICC."dept";

SELECT * FROM  PICC."func_role_info";
-- + " WHERE \"code\" = ':code'")
update PICC."data_role_info"
   set "desc"='修改后的说明', "name"='修改后的数据角色！'
   where  "code"=100;

DELETE FROM PICC."func_role_info";

insert into PICC."data_role_info" ("name", "desc", "levelCode") VALUES('测试自增id', '测试自增id啊呵呵', 10);
