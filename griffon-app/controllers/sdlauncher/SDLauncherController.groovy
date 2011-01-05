package sdlauncher


class SDLauncherController {

    // these will be injected by Griffon
    def model
    def view
    def service

    void mvcGroupInit(Map args) {

      //delay access
      app.config.controller = args.controller
      app.config.service = service
      app.config.view = args.view
      app.config.model = args.model

       println "[CL]service=${service}"
       service.mvcGroupInit(args)
    }

    void mvcGroupDestroy(Map args) {
      app.execOutside {
          //griffon.effects.Effects.dropOut(view.frame, wait: true)
          griffon.effects.Effects.puff(view.frame, wait: true)
          //griffon.effects.Effects.fade(view.frame, wait: true)
          app.shutdown()
      }
      service.mvcGroupDestroy(args)
    }

    
    def quit={
      app.execOutside {
          //griffon.effects.Effects.dropOut(view.frame, wait: true)
          griffon.effects.Effects.puff(view.frame, wait: true)
          //griffon.effects.Effects.fade(view.frame, wait: true)
          app.shutdown()
      }
    }

    def openBrowser={
        service.openBrowser()
    }

    def onStartupEnd = {
        doOutside {
        //hiding test
          //view.frame.visible = false
          //case 1
            //view.sdlauncher.visible = false
          //case 2
            app.windowManager.with {
              hide(findWindow('sdlauncher'))
            }
        }
    }

    //Directory Select
    def openDirectory={
        def directoryChooser = new com.l2fprod.common.swing.JDirectoryChooser()
        directoryChooser.currentDirectory = new File(model.workf==null ? '.' : model.workf)
        def result = directoryChooser.showOpenDialog(view.frame)
        if (result == com.l2fprod.common.swing.JDirectoryChooser.APPROVE_OPTION) {
            def file = directoryChooser.getSelectedFile()
            //view.workdir.text = "${file.getAbsolutePath()}"
            model.workf = "${file.getAbsolutePath()}"
        }
    }
    def workfSet ={filename->
        model.workf = filename
        println "workfSet:${model.workf}"
    }
}

