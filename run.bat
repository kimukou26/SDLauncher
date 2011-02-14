set GROOVY_HOME=c:\opt\groovy-1.7.7
set GRIFFON_HOME=c:\opt\griffon-0.9.2-rc1
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

:: http://localhost:38080/multiDoc/index.html
:: http://localhost:38080/multiDoc/index2.html ‚ÅŠm”F
::griffon run-app --port=38080 --useOutSidePort=true --openBrowser=false --webAppContext=multiDoc:multiDocRoot/bin-debug,multiDocRoot/WebContent --webApps=webapps/jenkins.war
griffon run-app --port=38080 --useOutSidePort=true --openBrowser=false
::griffon run-app --help
