application {
    title = 'SDLauncher'
    startupGroups = ['SDLauncher']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "SDLauncher"
    'SDLauncher' {
        model = 'sdlauncher.SDLauncherModel'
        controller = 'sdlauncher.SDLauncherController'
        view = 'sdlauncher.SDLauncherView'
				service = 'sdlauncher.SDLauncherService'
    }

}

def shutdown_hook_f=false

def controller
def service
def view
def model
