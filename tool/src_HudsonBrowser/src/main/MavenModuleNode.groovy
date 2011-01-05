public class MavenModuleNode {
  static def myRegexp = /https?:\/\/.*?\$(.*?)\/[0-9]+\//

  String name
  String url
  def testClasses = []

  MavenModuleNode(node) {
    url = node.url
    // url から name を抽出できないかな
    def matcher = (url =~ myRegexp)
    name = matcher[0][1]
  }

  private getChildren() {
    // testReportを抜き出す。BuildNode の freeStyleProjectコースとやることは同じ
    if (testClasses.isEmpty()) {
      def testReportUrl = "${url}testReport/"
      def testReportResponse = new URL("${testReportUrl}api/xml?depth=1").text
      def testReportXml = new XmlSlurper().parseText(testReportResponse)
      testReportXml.child.each { packageXml ->
        packageXml.child.each { classXml ->
          testClasses << new TestClassNode(classXml, packageXml.name, testReportUrl)
        }
      }
    }
    testClasses
  }

  public String toString() { name }
}