import groovy.beans.Bindable

class HudsonBrowserModel {
  String hudsonUrl = "http://deadlock.netbeans.org/hudson/"
//  String hudsonUrl = "https://www.seasar.org/hudson/"
  Boolean useProxy = false
  String proxyHost
  String proxyPort

  public String toString() { "${hudsonUrl} - ${useProxy} ${proxyHost}:${proxyPort}" }
}