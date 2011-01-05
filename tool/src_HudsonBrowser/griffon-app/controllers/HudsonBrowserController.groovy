import java.awt.CardLayout
import static java.awt.BorderLayout.*

class HudsonBrowserController {
  // these will be injected by Griffon
  def model
  def view

  void mvcGroupInit(Map args) {
    // this method is called after model and view are injected

//20101105 add start kimukou.buzz
     	def key
      def argsSet = [:]
      app.getStartupArgs().each{
          //println "${it}"
          String val = it
//griffon run-app Provisional correspondence  start==>
          if(val.startsWith("'")){
            val = val.replaceAll("'","")
            key = val
          }
          else if(val.endsWith("'")){
            val = val.replaceAll("'","")
						println "(${key},${val})"
            argsSet.put(key,val)
          }
//griffon run-app Provisional correspondence  end<==
          else{
						String[] sp = val.split("=")
						println "(${sp[0]},${sp[1]})"
            argsSet.put(sp[0],sp[1])
          }
      }
			if(argsSet.containsKey("--hudsonUrl")){
				model.hudsonUrl = argsSet.get("--hudsonUrl")
			}
//20101105 add end kimukou.buzz
  }

  def login = {
    doOutside {
      if (model.useProxy) {
        System.setProperty("useProxy", model.useProxy.toString())
        System.setProperty("proxyHost", model.proxyHost)
        System.setProperty("proxyPort", model.proxyPort)
      }
      // TODO: Hudsonへの認証とHudsonのバージョンチェック(1.272以上)
      // TODO: エラー処理
      def hudsonResponse = new URL("${model.hudsonUrl}/api/xml").text

      doLater {
        ((CardLayout) cardSwitcher).show(rootPanel, 'main')
        createMVCGroup('HudsonJobs', 'jobs', [url: model.hudsonUrl, response:hudsonResponse])

        // HudsonJobsViewをHudsonBrowserViewに貼り付ける(こんなでいいのかな？)
        mainPanel.add(app.views.jobs.jobPanel, CENTER)
      }
    }
  }
}