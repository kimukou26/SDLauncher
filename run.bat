set GROOVY_HOME=c:\opt\groovy-1.7.6
set GRIFFON_HOME=c:\opt\griffon-0.9.2-beta-3
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

::griffon run-app '--port=38080' '--useOutSidePort=true' '--openBrowser=false' '--webAppContext=multiDoc:multiDocRoot/bin-debug,multiDocRoot/WebContent' '--webApps=webapps/hudson.war'
griffon run-app '--port=38080' '--useOutSidePort=true' '--openBrowser=false'
