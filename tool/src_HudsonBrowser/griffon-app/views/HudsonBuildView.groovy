import java.awt.*
import static java.awt.BorderLayout.*

tabbedPane(id:'tab', rootPane, selectedIndex:rootPane.tabCount) {
  panel(title:build.title) {
    borderLayout()
    panel(constraints:NORTH) {
      button('CLOSE', actionPerformed:controller.closeTab)
    }
    panel(constraints:CENTER) {
      borderLayout()
      splitPane(constraints:CENTER) {
        panel {
          borderLayout()
          scrollPane(constraints:CENTER) {
            tree(id:'testTreeView')
          }
        }
        panel {
          borderLayout()
          scrollPane(constraints:CENTER) {
            editorPane(id:'resultView', editable:false, //contentType:'text/html',
                       font:new Font('Monospaced', Font.PLAIN, 12))
          }
        }
      }
    }
  }
}