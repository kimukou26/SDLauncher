package sdlauncher

//import groovy.beans.Bindable

class SDLauncherModel {

  def view
  def service
	def controller

  // @Bindable String propName
	@Bindable def workf

	//SDLoader pointer
	def loader 
	def port
}
