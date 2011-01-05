public class TestClassNode {
  String packageName
  String className
  Integer failCount
  String url
  def testMethods = []

  TestClassNode(node, packagename, testReportUrl) {
    packageName = packagename
    className = node.name
    failCount = Integer.parseInt("${node.failCount}")
    url = "${testReportUrl}${packageName}/${className}/"
  }

  def getChildren() {
    if (testMethods.isEmpty()) {
      def testMethodsXml = new XmlSlurper().parseText(new URL("${url}api/xml?depth=1").text)
      testMethodsXml.child.each { methodXml ->
        testMethods << new TestMethodNode(methodXml, url)
      }
    }
    testMethods
  }

  public String toString() { "${failCount > 0 ? '×' : '○'}:${className} (${packageName})" }

}