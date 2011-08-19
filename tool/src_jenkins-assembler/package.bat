call ../../setEnvG9.bat

call griffon clean
call griffon prod package jar

copy dist\jar\jenkins-assembler.jar ..\jenkins-assembler /Y
