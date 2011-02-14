import net.miginfocom.swing.MigLayout

//application(title:'HudsonBrowser', size:[300, 400], show:true, pack:true, locationByPlatform:true) {
application(title:'JenkinsBrowser', size:[300, 400], show:true, pack:true, locationByPlatform:true) {
  panel(id:'rootPanel') {
    cardSwitcher = cardLayout()
    panel(constraints:'login', layout:new MigLayout()) {
      label('Hudson Url:')
      textField(id:'hudsonUrl', columns:40, text: model.hudsonUrl, constraints:'wrap')
      label('Use Proxy:')
      checkBox(id:'useProxy', selected: model.useProxy, constraints:'span 2, wrap')
      panel(constraints:'span 2, wrap', layout:new MigLayout()) {
        textField(id:'proxyHost', columns:30, text: model.proxyHost)
        label(':')
        textField(id:'proxyPort', columns:6, text: model.proxyPort, constraints:'wrap')

        button('LOGIN', actionPerformed:controller.login)
      }
    }
    panel(constraints:'main', id:'mainPanel') {
      borderLayout()
    }
  }
}
bean(model, hudsonUrl:bind{ hudsonUrl.text })
bean(model, useProxy:bind{ useProxy.selected })
bean(model, proxyHost:bind{ proxyHost.text })
bean(model, proxyPort:bind{ proxyPort.text })
