import javax.swing.tree.*
import javax.swing.event.*
import static javax.swing.tree.TreeSelectionModel.*

class HudsonBuildController {
  // these will be injected by Griffon
  def model
  def view

  void mvcGroupInit(Map args) {
    //TODO 処理し終わるまで，JTreeの初期状態が表示されたままっての，どうにかならんか？
    doOutside {
      def contents = new DefaultMutableTreeNode(args.build)
      args.build.children.each { contents.add(new DefaultMutableTreeNode(it)) }
      doLater {
        view.testTreeView.model = new DefaultTreeModel(contents)
        view.testTreeView.selectionModel.selectionMode = SINGLE_TREE_SELECTION
        view.testTreeView.addTreeSelectionListener( treeSelectionListener )
      }
    }
  }

  def treeSelectionListener = { evt ->
    def path = evt.path
    def node = view.testTreeView.lastSelectedPathComponent
    def userObject = node.userObject

    doLater {
      if (userObject instanceof BuildNode) viewConsole(userObject)
      if (userObject instanceof TestMethodNode) viewResult(userObject)
      else {
        node.removeAllChildren()
        userObject.children.each { node.add(new DefaultMutableTreeNode(it)) }
      }
    }
  } as TreeSelectionListener


  private viewResult(testMethod) {
    view.resultView.text = """${testMethod.stackTrace}
---------------------------------------------------
${testMethod.stderr}
---------------------------------------------------
${testMethod.stdout}
"""
  }

  private viewConsole(build) {
    view.resultView.text = build.consoleText
  }

  def closeTab = {
    def tabIndex = view.tab.selectedIndex
    if (tabIndex < 0) return
    view.tab.remove(tabIndex)
    view.tab.selectedIndex = 0
  }
}
