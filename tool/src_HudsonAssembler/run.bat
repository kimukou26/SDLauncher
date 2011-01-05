set GROOVY_HOME=C:\opt\groovy-1.7.6
set GRIFFON_HOME=C:\opt\griffon-0.9.2-beta-3
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

for /F "delims=" %%s in ('cd') do @set PWD=%%s

griffon run-app '--xmlCurPath=%PWD%' '--warCurPath=%PWD%'
