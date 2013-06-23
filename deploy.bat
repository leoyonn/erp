@echo stop resin
call D:/programs/resin-pro-4.0.36/resin stop
@echo compile
call mvn -U clean package
@echo copy resource
cp -r -f src/main/resources/* target/erp-0.0.1-SNAPSHOT/WEB-INF/
@echo start resin
call D:/programs/resin-pro-4.0.36/resin start
pause