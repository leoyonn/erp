
CREATE TABLE PICC."func_role_funcs"
(
  "roleCode"      NUMBER(8) NOT NULL,
  "funcCode"      NUNBER(6) NOT NULL,
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

ALTER TABLE PICC."func_role_funcs"
    ADD CONSTRAINT "func_role_funcs_PK"
    PRIMARY KEY ("roleCode", "funcCode")
    ENABLE VALIDATE;