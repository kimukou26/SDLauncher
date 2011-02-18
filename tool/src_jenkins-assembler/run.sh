JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home
GROOVY_HOME=/usr/share/groovy-1.7.8
GRIFFON_HOME=/usr/share/griffon-0.9.2-rc1

export JAVA_OPTS='-Dgroovy.source.encoding=UTF-8 -Dfile.encoding=UTF-8'

export PATH=$JAVA_HOME/bin:$GROOVY_HOME/bin:$GRIFFON_HOME/bin

PROJECT_HOME=`pwd`
griffon run-app --xmlCurPath=$PROJECT_HOME --warCurPath=$PROJECT_HOME