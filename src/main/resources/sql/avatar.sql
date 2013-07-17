-- avatar table
-- @author leo
-- @data 2013-05-15

CREATE TABLE PICC."avatar"
(
  "url"   VARCHAR2(64 CHAR)                     NOT NULL,
  "data"  BLOB                                  NOT NULL
)

LOB ("data") STORE AS (
  ENABLE      STORAGE IN ROW
  RETENTION
  NOCACHE
  LOGGING
      STORAGE    (
                  PCTINCREASE      0
                 ))
RESULT_CACHE (MODE DEFAULT)
STORAGE    (
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;


ALTER TABLE PICC."avatar" ADD (
  CONSTRAINT "avatar_PK"
  PRIMARY KEY
  ("url")
  ENABLE VALIDATE);  