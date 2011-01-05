package sdlauncher

import static griffon.util.GriffonApplicationUtils.*

import groovy.ui.Console
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import javax.swing.KeyStroke

actions {
	//use system resources
  action( id: 'workfolderAction',
    name: getMessage("view.menu.workfolder"),
    closure: controller.openDirectory,
    mnemonic: 'F',
    accelerator: shortcut('ctrl F'),
    smallIcon: imageIcon(resource:"icons/folder_page.png", class: Console),
    shortDescription: getMessage("view.menu.workfolder")
   )

	//use griffon-app/resources
  action( id: 'openbrowserAction',
    name: getMessage("view.menu.openbrowser"),
    closure: controller.openBrowser,
    mnemonic: 'F',
    accelerator: shortcut('ctrl O'),
    smallIcon: imageIcon(resource:"/icons/script_palette.png", class: SDLauncherActions),
    shortDescription: getMessage("view.menu.openbrowser")
   )


   action( id: 'quitAction',
      name: getMessage("view.menu.quit"),
      closure: controller.quit,
      mnemonic: 'Q',
      accelerator: shortcut('ctrl Q'),
      smallIcon: imageIcon(resource:"/icons/cancel.png", class: SDLauncherActions),
      shortDescription: getMessage("view.menu.quit")
   )
}
