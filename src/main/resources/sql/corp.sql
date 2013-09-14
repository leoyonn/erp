-- corp table
-- @author leo
-- @data 2013-05-15

ALTER TABLE PICC."corp"
 DROP PRIMARY KEY CASCADE;

DROP TABLE PICC."corp" CASCADE CONSTRAINTS;

CREATE TABLE PICC."corp"
(
  "id"           VARCHAR2(64 CHAR),
  "type"         VARCHAR2(16 CHAR),
  "name"         VARCHAR2(64 CHAR),
  "desc"         VARCHAR2(128 CHAR),
  "address"      VARCHAR2(128 CHAR),
  "contact"      VARCHAR2(32 CHAR),
  "tel"          VARCHAR2(32 CHAR),
  "superCorpId"  VARCHAR2(64 CHAR),
  "mode"         VARCHAR2(32 CHAR),
  "status"       VARCHAR2(32 CHAR),
  "creatorId"    VARCHAR2(64 CHAR),
  "createTime"   TIMESTAMP(6),
  "startTime"    TIMESTAMP(6),
  "endTime"      TIMESTAMP(6),
  "stype"        VARCHAR2(32 CHAR)
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


--  There is no statement for index PICC.SYS_C0039212.
--  The object is created when the parent object is created.

CREATE UNIQUE INDEX PICC."corp_PK" ON PICC."corp"
("id")
LOGGING
TABLESPACE PICCTABLE
PCTFREE    10
INITRANS   2
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
NOPARALLEL;


ALTER TABLE PICC."corp" ADD (
  CONSTRAINT "corp_PK"
  PRIMARY KEY
  ("id")
  USING INDEX PICC."corp_PK"
  ENABLE VALIDATE,
  UNIQUE ("name")
  USING INDEX
    TABLESPACE PICCTABLE
    PCTFREE    10
    INITRANS   2
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
  ENABLE VALIDATE);
