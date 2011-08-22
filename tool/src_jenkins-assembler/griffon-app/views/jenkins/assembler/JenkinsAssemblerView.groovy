package jenkins.assembler

import javax.swing.*
import javax.swing.table.*
import javax.swing.filechooser.*
import static java.awt.BorderLayout.*

application(title:'JenkinsAssembler',
  size:[600,400],
  locationByPlatform:true,
  iconImage: imageIcon('/jenkins-48x48.png').image,
  iconImages: [imageIcon('/jenkins-48x48.png').image,
               imageIcon('/jenkins-32x32.png').image,
               imageIcon('/jenkins-16x16.png').image]
) {

//20101105 mod start kimukou.buzz
    //def newFileChooser = { actionListener, ext = "xml" ->
    def newFileChooser = { actionListener, ext = "xml",curDir=System.getProperty('user.home') ->
        fileChooser(
            fileSelectionMode:JFileChooser.FILES_ONLY,
            actionPerformed:actionListener,
            //currentDirectory:new File(System.getProperty('user.home')),
            currentDirectory:new File(curDir),
            fileFilter:[
                getDescription: {-> "*.$ext"},
                accept:{file-> file ==~ /.*?\.$ext/ || file.isDirectory() }
            ] as FileFilter)
    }

    //saveFileChooser = newFileChooser(controller.save)
    //openFileChooser = newFileChooser(controller.open)
    //assembleFileChooser = newFileChooser(controller.assemble, "war")
    saveFileChooser = newFileChooser(controller.save,"xml",model.xmlCurPath)
    openFileChooser = newFileChooser(controller.open,"xml",model.xmlCurPath)
    assembleFileChooser = newFileChooser(controller.assemble, "war",model.warCurPath)
//20101105 mod end kimukou.buzz

    menuBar {
        menu(text:'File') {
            menuItem(text:'Open', actionPerformed:{openFileChooser.showOpenDialog(mainPanel)})
            menuItem(text:'Save', actionPerformed:{saveFileChooser.showSaveDialog(mainPanel)})
        }
        menu(text:'Assemble') {
            menuItem(text:'Assemble Jenkins', actionPerformed:{assembleFileChooser.showSaveDialog(mainPanel)})
        }        
    }

    scrollPane(id:'mainPanel', constraints:CENTER) {
        table = table(id:'pluginTable', rowHeight:60, rowMargin:5, rowSelectionAllowed:false,
                autoResizeMode:JTable.AUTO_RESIZE_LAST_COLUMN) {
            tableModel = tableModel(id:'pluginTableModel', list:model.plugins) {
                propertyColumn(
                    header:'Install',
                    propertyName:'install',
                    type:Boolean,
                    maxWidth:50
                )
                closureColumn(
                    header:'Name',
                    cellRenderer : { table, value, isSelected, hasFocus, row, column ->
                        editorPane(contentType:'text/html', text:value, editable:false)
                    } as TableCellRenderer,
                    read : {
                        "<body style='font-family:Verdana,Helvetica,sans serif'>" +
                        "<b>${it.displayName}</b>" +
                        (it.excerpt ? "<br><div style='margin-left:20px;'>${it.excerpt}</div>" : "") +
                        "</body>"
                    }
                )
            }
        }
    }

    sorter = new TableRowSorter(tableModel)
    table.rowSorter = sorter
    search = searchField(prompt:'Search plugins', constraints:NORTH, outerMargin:[0,0,100,50], actionPerformed:{
        sorter.rowFilter = {
            def plugin = model.plugins[it.identifier]
println plugin
            return "${plugin.name} ${plugin.displayName} ${plugin.wiki}".toLowerCase()
                .indexOf(search.text.toLowerCase()) >= 0
        } as RowFilter
    })
}