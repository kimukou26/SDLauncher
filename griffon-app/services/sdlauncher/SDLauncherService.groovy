package sdlauncher


import com.dreamarts.system.util.CpuUsageAnalyzer


import sdloader.*
import sdloader.internal.*
import sdloader.lifecycle.*
import sdloader.log.*
import sdloader.util.Browser
import sdloader.javaee.WebAppContext

class SDLauncherService {

    private static final SDLoaderLog sdlog = SDLoaderLogFactory.getLog(SDLauncherService)

    def model
    def view
    def controller

    void mvcGroupInit(Map args) {

      println "cpu=${CpuUsageAnalyzer.getCpuUsage()}"

      args.each{
        println it
      }

      println "[SV]model=${model}"
      println "[SV]view=${view}"
      println "[SV]controller=${controller}"
      

      def key=''
      def sb =new StringBuilder()
      def argsList = []
      def argsSet = [:]
      app.getStartupArgs().each{
          //println "${it}"
          String val = it
//griffon run-app Provisional correspondence  start==>
          if(val.startsWith("'--help")){
            val = val.replaceAll("'","")
            argsList.add val
          }
          else if(val.startsWith("'")){
            val = val.replaceAll("'","")
            key = val             //Map add
            sb = new StringBuilder()
            sb.append val
            sb.append "="
          }
          else if(val.endsWith("'")){
            val = val.replaceAll("'","")
            if(!key.startsWith("--webAppContext")){
              argsSet.put(key,val)  //Map add
            }
            sb.append val
            val = sb.toString()
            println "${val}"
            if(!key.startsWith("--webAppContext")){
              argsList.add val
            }
            else{
              String[] sp = val.split("=")
              println "(${sp[0]},${sp[1]})"
              argsSet.put(sp[0],sp[1])  //Map Add
            }
          }
          else if(val.indexOf("=")==-1){
            sb.append val
            sb.append ","
          }
//griffon run-app Provisional correspondence  end<==
          else{
            println "${val}"
            if(!val.startsWith("--webAppContext")){
              argsList.add val
            }
            String[] sp = val.split("=")
            println "(${sp[0]},${sp[1]})"
            argsSet.put(sp[0],sp[1])  //Map Add

          }
      }

      controller.doOutside {
        start argsList,argsSet
      }
    }

    void mvcGroupDestroy(Map args) {
/*
      if(model.loader!=null){
        model.loader.waitForStop()
        model.loader.stop()
      }
*/
    }


    def start = { argsList,argsSet->

      String[] args = argsList.toArray(new String[0])
      def helper = new CommandLineHelper(args)
      helper.setOpenBrowser(true)
      if (helper.hasHelpOption()) {
        helper.printUsage(sdlog)
        showInfoDialog(helper.getUsageText())
        System.exit(0)
        return
      }

      model.loader = new SDLoader()
      model.loader.setAutoPortDetect(true)

/*
      model.loader.addEventListener(LifecycleEvent.AFTER_STOP,
        new LifecycleListener() {
          public void handleLifecycle(LifecycleEvent<?> event) {
              println "event=${event}"
              System.exit(0)
          }
        })
*/
      try {
        helper.applySDLoaderProperties(model.loader)
      } catch (Exception e) {
        log.error(e.getMessage())
        helper.printHelpOption(sdlog)
        showErrorDialog(e.getMessage())
        showInfoDialog(helper.getUsageText())
        System.exit(0)
        return
      }
      
      if(argsSet.containsKey("--webAppContext")){
        def val = argsSet.get("--webAppContext")
        String[] webAppContext = null
        if(val.indexOf(";")==-1){
          webAppContext= new String[1]
          webAppContext[0] = val
        }
        else{
          webAppContext= val.split(";")
        }
        println webAppContext
        webAppContext.each{
          String[] sp = it.split(":")
          String[] spPath = null
          if(sp[1].indexOf(",")==-1){
            spPath = new String[1]
            spPath[0]=sp[1]
          }
          else{
            spPath = sp[1].split(",")
          }
          def context 
          if(spPath.length>=2){
            context = new WebAppContext(sp[0],spPath[0],spPath[1])
          }
          else{
            context = new WebAppContext(sp[0],spPath[0])
          }
          println context
          model.loader.addWebAppContext(context);
        }
      }

      model.port = model.loader.getPort()
      model.loader.start()
      if (helper.isOpenBrowser()) {
        openBrowser()
      }
  }

  def openBrowser = {
    try {
      //int port = model.loader.getPort() //difference get portEE
      int port = model.port
      println "port=${port}"
      String protocol = model.loader.isSSLEnable() ? "https" : "http"
      String url = protocol + "://localhost:" + port
      Browser.open(url);
    } catch (Exception e) {
      log.error(e.getMessage(), e)
    }
  }

  def showErrorDialog={message->
    javax.swing.JOptionPane.showMessageDialog(
      app.windowManager.windows[0], 
      message,
      "ERROR",
      javax.swing.JOptionPane.ERROR_MESSAGE
    )
  }

  def showInfoDialog={message->
    javax.swing.JOptionPane.showMessageDialog(
      app.windowManager.windows[0], 
      message,
      "INFOMATION",
      javax.swing.JOptionPane.INFORMATION_MESSAGE
    )
  }

}
