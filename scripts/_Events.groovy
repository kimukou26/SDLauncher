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

  //ROOT
  destDir = "${basedir}/staging"
	copySetting(destDir)
}


eventPackageStart={msg->
  println "==package start(${msg})=="
}

eventPackageEnd = {msg->
  println "==package end(${msg})=="
  growlNotify("eventPackageEnd")

  destDir = "${basedir}/dist/jar"
  copySetting(destDir)
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


//--------------------------------------------------------------------------------------------
def copySetting(distDir){
	println "==copySetting(${distDir})=="
  //ROOT
  srcDir  = "${basedir}/setting"
  ant.mkdir(dir:destDir)
  ant.copy(todir: destDir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.sh,*.txt,*.bat,*.pdf,*.inf' ,excludes:'autorun.bat,Autorun.inf')
  }

  //Setting
  srcDir  = "${basedir}/setting"
  setting_dir = "${destDir}/setting"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.xml')
  }



  //Webapps
  srcDir  = "${basedir}/setting/webapps"
  setting_dir = "${destDir}/webapps"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    //fileset(dir: srcDir, includes: '*.exe')
    fileset(dir: srcDir)
  }

  //multiDocRoot
  srcDir  = "${basedir}/setting/multiDocRoot"
  setting_dir = "${destDir}/multiDocRoot"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    //fileset(dir: srcDir, includes: '*.exe')
    fileset(dir: srcDir)
  }

  //ci-test
  srcDir  = "${basedir}/setting/ci-test"
  setting_dir = "${destDir}/ci-test"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    //fileset(dir: srcDir, includes: '*.exe')
    fileset(dir: srcDir)
  }


  //Tool
  srcDir  = "${basedir}/tool"
  setting_dir = "${destDir}/tool"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.txt')
  }

  srcDir  = "${basedir}/tool/jenkins-assembler"
  setting_dir = "${destDir}/tool/jenkins-assembler"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    fileset(dir: srcDir)
  }

  srcDir  = "${basedir}/tool/HudsonBrowser"
  setting_dir = "${destDir}/tool/HudsonBrowser"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    fileset(dir: srcDir)
  }

}

def copySettingExe={destDir->
	println "==copySettingExe(${distDir})=="
  //ROOT
  println "destDir=${destDir}"
  srcDir  = "${basedir}/setting"
  ant.mkdir(dir:destDir)
  ant.copy(todir: destDir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.sh,*.txt,*.bat,*.pdf,*.inf' ,excludes:'start.bat,start.sh')
  }

  //pic
  srcDir  = "${basedir}/setting/pic"
  setting_dir = "${destDir}/pic"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    //fileset(dir: srcDir, includes: '*.exe')
    fileset(dir: srcDir)
  }

  //Setting
  srcDir  = "${basedir}/setting"
  setting_dir = "${destDir}/setting"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.xml')
  }



  //Webapps
  srcDir  = "${basedir}/setting/webapps"
  setting_dir = "${destDir}/webapps"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    //fileset(dir: srcDir, includes: '*.exe')
    fileset(dir: srcDir)
  }

  //multiDocRoot
  srcDir  = "${basedir}/setting/multiDocRoot"
  setting_dir = "${destDir}/multiDocRoot"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    //fileset(dir: srcDir, includes: '*.exe')
    fileset(dir: srcDir)
  }

  //ci-test
  srcDir  = "${basedir}/setting/ci-test"
  setting_dir = "${destDir}/ci-test"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    //fileset(dir: srcDir, includes: '*.exe')
    fileset(dir: srcDir)
  }

  
  //Tool
  srcDir  = "${basedir}/tool"
  setting_dir = "${destDir}/tool"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    fileset(dir: srcDir, includes: '*.txt')
  }

  srcDir  = "${basedir}/tool/jenkins-assembler"
  setting_dir = "${destDir}/tool/jenkins-assembler"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    fileset(dir: srcDir)
  }

  srcDir  = "${basedir}/tool/HudsonBrowser"
  setting_dir = "${destDir}/tool/HudsonBrowser"
  ant.mkdir(dir:setting_dir)
  ant.copy(todir: setting_dir, overwrite: true ) {
    fileset(dir: srcDir)
  }
}

//--------------------------------------------------------------------------------------------
//installer plugin

eventPreparePackageStart={ type->
  println "==eventPreparePackageStart [${type}]=="
}


eventPreparePackageEnd={ type->
  println "==eventPreparePackageEnd [${type}]=="
  switch(type){
    case "windows":
      tmplfile="${basedir}/setting/${griffonAppName}.jsmooth"
      dstfile="${basedir}/target/installer/jsmooth/${griffonAppName}.jsmooth"
      ant.copy(tofile:dstfile,file:tmplfile, overwrite: true )
      break

    case "izpack":
      destDir = "${basedir}/installer/izpack/resources/"
      copySetting(destDir)
      ant.copy( todir: "${basedir}/installer/izpack/resources", overwrite: true ) {
        fileset( dir: "${basedir}/src/installer/izpack/resources", includes: "**" )
      }
      ant.replace( dir: "${basedir}/installer/izpack/resources" ) {
        replacefilter(token: "@app.name@", value: griffonAppName)
        replacefilter(token: "@app.version@", value: griffonAppVersion)
      }
      break
  }

  //growlNotify("eventPreparePackageEnd(${type})")

}


eventCreatePackageStart = { type->
  println "==eventCreatePackageStart ${type}=="
}

eventCreatePackageEnd = { type->
  println "==eventCreatePackageEnd  ${type}=="

  println "==eventCreatePackageEnd  ${type}=="
  switch(type){
    case "windows":
    destDir = "${basedir}/dist/windows"
    copySettingExe(destDir)
    break;
  }

  growlNotify("eventCreatePackageEnd(${type})")
}

