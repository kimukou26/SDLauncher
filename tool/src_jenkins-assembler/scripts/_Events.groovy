//------------------------------------------------------------------
eventStatusFinal = { msg ->
  println "==eventStatusFinal(${msg})=="
  growlNotify(msg)
}
 
eventStatusUpdate = { msg ->
  println "==eventStatusUpdate(${msg})=="
  growlNotify(msg)
}
 
growlNotify = { message ->
    println "==growlNotify(${message})=="
    return

    //path="/usr/local/bin/growlnotify"
    path="c:/opt/Growl/growlnotify.exe"
    if(!new File(path).exists())return
    imgpath="${basedir}/griffon-app/resources/griffon-icon-32x32.png"

    ant.exec(executable:path) {
          arg(value:"/t:Griffon")
          arg(value:"/i:\"${imgpath}\"")
          arg(value:"\"${message}\"")
    }
}


eventCompileStart = {msg->
  println "==compile start(${msg})=="
}

eventCompileEnd = {msg->
  println "==compile end(${msg})=="
  growlNotify("eventCompileEnd")
}


eventPackageStart={msg->
  println "==package start(${msg})=="
}

eventPackageEnd = {msg->
  println "==package end(${msg})=="
  growlNotify("eventPackageEnd")

  srcDir  = "${basedir}/dist/jar"
  destDir = "${basedir}/../jenkins-assembler"
  ant.mkdir(dir:destDir)
  ant.copy(todir: destDir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.jar')
  }
}

//--------------------------------------------------------------------------------------------
eventGenerateJNLPStart = {
  println "==onGenerateJNLPStart(${packageType})=="
  if(packageType == 'applet') {
    destDir="${basedir}/dist/applet"
    buildConfig.griffon.webstart.codebase = "${new File(destDir).toURI().toASCIIString()}"
  } else if(packageType == 'webstart') {
    destDir="${basedir}/dist/webstart"
    buildConfig.griffon.webstart.codebase = "${new File(destDir).toURI().toASCIIString()}"
  }
}

