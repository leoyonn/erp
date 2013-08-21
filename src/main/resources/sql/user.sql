-- user_info table
-- @author leo
-- @data 2013-05-15

CREATE TABLE PICC."user"
(
  "id"          VARCHAR2(64 CHAR)   NOT NULL,
  "account"     VARCHAR2(64 CHAR)   UNIQUE NOT NULL,
  "name"        VARCHAR2(64 CHAR)   NOT NULL,
  "password"    VARCHAR2(64 CHAR)   NOT NULL,
  "avatar"      VARCHAR2(128 CHAR),
  "email"       VARCHAR2(128 CHAR),
  "phone"       VARCHAR2(32 CHAR),
  "tel"         VARCHAR2(32 CHAR),
  "desc"        VARCHAR2(256 CHAR),
  "province"    VARCHAR2(64 CHAR),
  "city"        VARCHAR2(64 CHAR),
  "createTime"  TIMESTAMP(6) DEFAULT SYSDATE,
  "creatorId"   VARCHAR2(64 CHAR),
  "updateTime"  TIMESTAMP(6) DEFAULT SYSDATE,
  "operId"      VARCHAR2(64 CHAR),

  "catCode"     NUMBER(6),
  "posCode"     NUMBER(6),
  "froleCode"   NUMBER(6),
  "droleCode"   NUMBER(6),
  "statCode"    NUMBER(6),
  "corpId"      VARCHAR2(32 CHAR),
  "deptId"      VARCHAR2(32 CHAR)

)

TABLESPACE PICCTABLE
RESULT_CACHE (MODE DEFAULT)
PCTUSED    40
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

ALTER TABLE PICC."user"
 ADD CONSTRAINT "user_PK"
  PRIMARY KEY
  ("id")
  ENABLE VALIDATE;

CREATE INDEX PICC."user_account_index" ON PICC."user"("account");
CREATE INDEX PICC."user_name_index" ON PICC."user"("name");
CREATE INDEX PICC."user_cat_index" ON PICC."user"("catCode");
CREATE INDEX PICC."user_pos_index" ON PICC."user"("posCode");
CREATE INDEX PICC."user_frole_index" ON PICC."user"("froleCode");
CREATE INDEX PICC."user_drole_index" ON PICC."user"("droleCode");
CREATE INDEX PICC."user_corp_index" ON PICC."user"("corpId");
CREATE INDEX PICC."user_dept_index" ON PICC."user"("deptId");

SELECT INDEX_NAME FROM ALL_INDEXES WHERE TABLE_NAME = 'PICC."user"';

