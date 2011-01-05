JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home
export PATH=$JAVA_HOME/bin

$JAVA_HOME/bin/java -jar ../webapps/hudson/WEB-INF/hudson-cli.jar -s http://localhost:38080/hudson groovy $*
