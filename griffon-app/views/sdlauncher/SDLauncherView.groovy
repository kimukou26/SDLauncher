package sdlauncher

import groovy.ui.Console
import net.miginfocom.swing.MigLayout


//Acttion define outside
build(SDLauncherActions)

frame = application(id:'sdlauncher',name:'sdlauncher',title: 'SDLauncher',
  size: [480,96],
	visible:false,
  //pack: true,
  //location: [50,50],
  locationByPlatform:true,
  iconImage: imageIcon('/sdloader-icon-48x48.png').image,
  iconImages: [imageIcon('/sdloader-icon-48x48.png').image,
               imageIcon('/sdloader-icon-32x32.png').image,
               imageIcon('/sdloader-icon-16x16.png').image]) {

		migLayout(layoutConstraints: "gap 0,insets 0,fill")
		button(id:'workdir_btn',
			   icon:imageIcon(resource:"icons/folder_page.png", class: Console),
			   text:getMessage("view.workfolder.btn"),
			   actionPerformed:controller.openDirectory ,constraints: "")
		workdir_btn.setToolTipText(getMessage("view.workfolder.btn.tooltip"))
		
		//D & D action ：WorkspaceDropTarget.groovy into outside
		label(id:'workdir',text:bind{model.workf},dropTarget:new WorkspaceDropTarget(controller.workfSet),constraints: "growx, wrap, gaptop 0,wrap",minimumSize:[480,48])
		workdir.setToolTipText(getMessage("view.workfolder.label.tooltip"))

    //Double Click change disp or undisp
    systemTray {
        trayIcon(id: "trayIcon",
            resource: "/sdloader-icon-48x48.png",
            class: groovy.ui.Console,
            toolTip: getMessage("view.tray.tooltip") ,
            actionPerformed: {  frame.visible = !frame.visible }) 
				{
						popupMenu {
				       menuItem(openbrowserAction)
			  	     separator()
			    	   menuItem(quitAction)
				    }
				}
    }
}

/*
frame.windowOpened={println 'Opened'}//first start
frame.windowClosing={println 'closing'}//menu closed windos
frame.windowClosed={println 'closed'}//dispose call
*/
//normal＝＞min
frame.windowIconified={
    println 'Iconified'
}   
//min＝＞normal
frame.windowDeiconified={
    println 'Deiconified'
}

//active
frame.windowActivated={
    println 'Activated'
    if(!frame.visible)return
}

//not active
frame.windowDeactivated={
    println 'Deactivated'
    if(frame.visible)return
}

