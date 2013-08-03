-- supplier table
-- @author leo
-- @data 2013-07-21

CREATE TABLE PICC."supplier"
(
  "id"          VARCHAR2(64 CHAR)   NOT NULL,
  "name"        VARCHAR2(64 CHAR)   NOT NULL,
  "desc"        VARCHAR2(256 CHAR),
  "type"        VARCHAR2(32 CHAR),
  "mode"        VARCHAR2(32 CHAR),
  "contact"     VARCHAR2(32 CHAR),
  "tel"         VARCHAR2(32 CHAR),
  "email"       VARCHAR2(128 CHAR),
  "status"      VARCHAR2(32 CHAR),
  "startTime"   TIMESTAMP(6),
  "endTime"     TIMESTAMP(6),
  "createTime"  TIMESTAMP(6),
  "creatorId"   VARCHAR2(64 CHAR)
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

ALTER TABLE PICC."supplier"
 ADD CONSTRAINT "supplier_PK"
  PRIMARY KEY
  ("id")
  ENABLE VALIDATE;

CREATE INDEX PICC."supplier_index" ON PICC."supplier"("name");  

SELECT INDEX_NAME FROM ALL_INDEXES WHERE TABLE_NAME = 'PICC."supplier"';
