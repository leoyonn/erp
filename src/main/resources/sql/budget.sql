-- budget table
-- @author leo
-- @data 2013-05-15

CREATE TABLE PICC."budget"
(
    "code"          NUMBER(8),
    "type"          VARCHAR2(64 CHAR),
    "progress"      VARCHAR2(64 CHAR), 
    "status"        VARCHAR2(64 CHAR), 
    "year"          VARCHAR2(64 CHAR),
    "org"           VARCHAR2(64 CHAR),
    "createTime"    TIMESTAMP(6),
    "creatorId"     VARCHAR2(64 CHAR),
    "amountApply"   VARCHAR2(64 CHAR),
    "amountApprove" VARCHAR2(64 CHAR),
    "amountAlloc"   VARCHAR2(64 CHAR) 
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


ALTER TABLE PICC."budget"
    ADD CONSTRAINT "budget_PK"
    PRIMARY KEY ("code")
    ENABLE VALIDATE;


DROP SEQUENCE PICC."budget_indexer";

CREATE SEQUENCE PICC."budget_indexer"
   INCREMENT BY 1
   START WITH 1
   NOMAXVALUE
   NOCYCLE
   NOCACHE;

DROP TRIGGER PICC."budget";

CREATE TRIGGER PICC."budget"
   BEFORE INSERT
   ON PICC."budget"
   FOR EACH ROW
   WHEN (New."code" IS NULL)
BEGIN
   SELECT PICC."budget_indexer".NEXTVAL INTO :New."code" FROM DUAL;
END;

SELECT PICC."budget_indexer".NEXTVAL FROM DUAL;