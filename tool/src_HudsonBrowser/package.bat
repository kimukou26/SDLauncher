call ../../setEnvG9.bat

call griffon clean
call griffon prod package jar

copy dist\jar\HudsonBrowser.jar ..\HudsonBrowser /Y
