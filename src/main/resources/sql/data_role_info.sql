
CREATE TABLE PICC."data_role_info"
(
  "code"        NUMBER(8) NOT NULL,
  "name"        VARCHAR2(64 CHAR) UNIQUE NOT NULL,
  "desc"        VARCHAR2(256 CHAR),
  "levelCode"   NUMBER(8) NOT NULL,
  "corpId"      VARCHAR2(32 CHAR),
  "deptId"      VARCHAR2(32 CHAR),
  "creatorId"   VARCHAR2(32 CHAR),
  "createTime"  TIMESTAMP(6)
  "updateTime"  TIMESTAMP(6),
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

ALTER TABLE PICC."data_role_info"
    ADD CONSTRAINT "data_role_info_PK"
    PRIMARY KEY ("code")
    ENABLE VALIDATE;