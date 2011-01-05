public class BuildNode {
  String title
  String url
  String projectType
  String hasTest = "?"

  BuildNode(node, type) {
    title = node.title
    url = node.link.@href
    projectType = type
  }

  boolean hasTestReports() {
    // "${url}/testReport/api/xml" があるかないかを調べる(カコワルイ)
    try {
      new URL("${url}testReport/api/xml").text
      hasTest = "!"
    } catch(e) { hasTest = "x"; return false }
    return true
  }


  // プロジェクトタイプによって，子供が違うから getChildren() で抽象化しとく
  def getChildren() {
    if (projectType == "freeStyleProject") getTestReports()
    else if (projectType == "mavenModuleSet") getModules()
    else  []
  }

  // freeStyleProjectだったら，TestClassNode を返す
  private getTestReports() {
    def list = []
    def testReportUrl = "${url}testReport/"
    // パッケージ一覧は無視するから，depth=1 を指定
    // TODO できれば失敗したテストだけに絞りたいんだけど，exclude=の指定の仕方がわからん
    def testReportResponse = new URL("${testReportUrl}api/xml?depth=1").text
    def testReportXml = new XmlSlurper().parseText(testReportResponse)
    testReportXml.child.each { packageXml ->
      // パッケージすっとばしてクラス一覧を作る
      packageXml.child.each { classXml ->
        list << new TestClassNode(classXml, packageXml.name, testReportUrl)
      }
    }
    list
  }

  // mavenModuleProjectだったら，MavenModuleNode を返す
  private getModules() {
    def list = []
    def testReportResponse = new URL("${url}testReport/api/xml").text
    def testReportXml = new XmlSlurper().parseText(testReportResponse)
    testReportXml.childReport.child.each { moduleXml ->
      list << new MavenModuleNode(moduleXml)
    }
    list
  }


  def getConsoleText() {
    new URL("${url}consoleText").text
  }


  public String toString() { "${title} - ${projectType} - ${hasTest}" }
}