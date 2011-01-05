application {
	title="HudsonAssembler"
	startupGroups=["HudsonAssembler"]
	autoShutdown=true
}
mvcGroups {
	HudsonAssembler {
		model="HudsonAssemblerModel"
		controller="HudsonAssemblerController"
		view="HudsonAssemblerView"
	}
}
