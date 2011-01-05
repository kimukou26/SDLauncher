import groovy.beans.Bindable

class HudsonAssemblerModel {
    String coreUrl
    @Bindable String status
    @Bindable List plugins

//20101105 add start kimukou.buzz
		String xmlCurPath
		String warCurPath
//20101105 add end kimukou.buzz
}