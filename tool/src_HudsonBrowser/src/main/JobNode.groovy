public class JobNode {
  String name
  String url
  String color

  JobNode(node) {
    name = node.name
    url = node.url
    color = node.color
  }

  // rssAllからビルドを辿っていくよ。
  def getBuilds() {
    //TODO rssFailedでもいい気がする...
    def projectType = getProjectType()
    def list = []
    def rssResponse = new URL("${url}rssAll").text
    def rssXml = new XmlSlurper().parseText(rssResponse)
    rssXml.entry.each {entryXml ->
      // なんかの役に立つかもしれないので，プロジェクトタイプをとっておく
      list << new BuildNode(entryXml, projectType)
    }
    list
  }

  private getProjectType() {
    def projectXml = new XmlSlurper().parseText(new URL("${url}/api/xml").text)
    projectXml.name()  // <- nameノードではなくて，rootノードのノード名を取ってる
  }

  public String toString() { "${name} - ${color}" }
}