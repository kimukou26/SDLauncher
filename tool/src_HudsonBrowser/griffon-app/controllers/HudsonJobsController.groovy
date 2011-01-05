import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import static javax.swing.tree.TreeSelectionModel.*
import javax.swing.DefaultListModel
import javax.swing.event.TreeSelectionListener

class HudsonJobsController {
  // these will be injected by Griffon
  def model
  def view

  void mvcGroupInit(Map args) {
    doOutside {
      def jobTree = new DefaultMutableTreeNode("hudson")
      def hudsonXml = new XmlSlurper().parseText(args.response)
      hudsonXml.job.each { jobXml ->
        jobTree.add(new DefaultMutableTreeNode(new JobNode(jobXml)))
      }
      doLater {
        view.jobTreeView.model = new DefaultTreeModel(jobTree)
        view.jobTreeView.selectionModel.selectionMode = SINGLE_TREE_SELECTION
        view.jobTreeView.addTreeSelectionListener(treeSelectionListener)

        view.buildListView.mouseClicked = listClickedListener
      }
    }
  }

  def treeSelectionListener = { evt ->
    def path = evt.path
    //TODO rootNodeをクリックしたら再読み込み，とかしたらどうだ？
    if (path.pathCount == 2) {
      def node = view.jobTreeView.lastSelectedPathComponent
      doLater {
        def listModel = new DefaultListModel()
        node.userObject.builds.each { listModel.addElement(it) }
        view.buildListView.model = listModel
      }
    }
  } as TreeSelectionListener

  
  def listClickedListener = { evt ->
    if (evt.getClickCount() != 2) return
    def idx = view.buildListView.locationToIndex(evt.point)
    def buildNode = view.buildListView.model.elementAt(idx)
    if (!buildNode.hasTestReports()) {
      // testReport持ってないなら画面遷移しない。その代わり，再表示して最新状態にする
      view.jobPanel.repaint(); return
    }

    // testReport持っているなら，buildタブを増やすぞ
    createMVCGroup('HudsonBuild', buildNode.title, [rootPane:view.tab, build:buildNode])
  }

}