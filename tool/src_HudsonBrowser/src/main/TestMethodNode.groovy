public class TestMethodNode {
  String name
  String status
  String stackTrace
  String stderr
  String stdout
  String url

  TestMethodNode(node, testClassUrl) {
    name = node.name
    status = node.status
    stackTrace = node.errorStackTrace
    stderr = node.stderr
    stdout = node.stdout
    url = "${testClassUrl}${name}/"
  }

  public String toString() { "${status}:${name}" }

}