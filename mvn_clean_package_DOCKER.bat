@echo off
SET DIRECTORY_HOME=C:\develop\Java-literalura-Oracle-Alura-


cd %DIRECTORY_HOME%\java-service\
call mvn clean package install -P docker

@pause