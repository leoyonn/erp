-- corp table
-- @author leo
-- @data 2013-05-15

CREATE TABLE PICC."corp"
(
  "id"          VARCHAR2(64 CHAR)   NOT NULL,
  "type"        VARCHAR2(16 CHAR)   NOT NULL,
  "name"        VARCHAR2(64 CHAR)   UNIQUE NOT NULL,
  "desc"        VARCHAR2(128 CHAR),
  "address"     VARCHAR2(128 CHAR),
  "contact"     VARCHAR2(32 CHAR),
  "tel"         VARCHAR2(32 CHAR)
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

ALTER TABLE PICC."corp"
 ADD CONSTRAINT "corp_PK"
  PRIMARY KEY
  ("id")
  ENABLE VALIDATE;