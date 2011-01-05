set JAVA_HOME=c:\opt\jdk

%JAVA_HOME%/bin/java -jar ../webapps/hudson/WEB-INF/hudson-cli.jar -s http://localhost:38080/hudson groovy %*
