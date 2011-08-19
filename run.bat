call setEnvG9.bat

:: http://localhost:38080/multiDoc/index.html
:: http://localhost:38080/multiDoc/index2.html ‚ÅŠm”F
::griffon run-app --port=38080 --useOutSidePort=true --openBrowser=false --webAppContext=multiDoc:multiDocRoot/bin-debug,multiDocRoot/WebContent --webApps=webapps/jenkins.war
griffon run-app --port=38080 --useOutSidePort=true --openBrowser=false
::griffon run-app --help
