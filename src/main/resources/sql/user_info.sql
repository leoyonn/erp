-- user_info table
-- @author leo
-- @data 2013-05-15

CREATE TABLE PICC."user_info"
(
  "id"          VARCHAR2(64 CHAR)   NOT NULL,
  "account"     VARCHAR2(64 CHAR)   NOT NULL,
  "name"        VARCHAR2(64 CHAR)   NOT NULL,
  "password"    VARCHAR2(64 CHAR)   NOT NULL,
  "avatar"      VARCHAR2(128 CHAR),
  "email"       VARCHAR2(128 CHAR),
  "phone"       VARCHAR2(32 CHAR),
  "tel"         VARCHAR2(32 CHAR),
  "desc"        VARCHAR2(256 CHAR),
  "province"    VARCHAR2(64 CHAR),
  "city"        VARCHAR2(64 CHAR),
  "opUserId"    VARCHAR2(64 CHAR),
  "updateTime"  TIMESTAMP(6),
  "createTime"  TIMESTAMP(6)
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