import static java.awt.BorderLayout.*

panel(id: 'jobPanel') {
  borderLayout()
  textField(id: 'url', editable: false, text: url, constraints: NORTH)
  tabbedPane(id: 'tab', constraints: CENTER) {
    splitPane(title:"jobs") {
      panel {
        borderLayout()
        scrollPane(constraints:CENTER) {
          tree(id:'jobTreeView')
        }
      }
      panel {
        borderLayout()
        scrollPane(constraints:CENTER) {
          list(id:'buildListView')
        }
      }
    }
  }
}
