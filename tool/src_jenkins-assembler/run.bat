call ../../setEnvG9.bat

for /F "delims=" %%s in ('cd') do @set PWD=%%s

griffon run-app --xmlCurPath=%PWD% --warCurPath=%PWD%
