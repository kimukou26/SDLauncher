application {
	title="HudsonBrowser"
	startupGroups=["HudsonBrowser"]
	autoShutdown = true
}
mvcGroups {
	HudsonBuild {
		model="HudsonBuildModel"
		controller="HudsonBuildController"
		view="HudsonBuildView"
	}
	HudsonJobs {
		model="HudsonJobsModel"
		controller="HudsonJobsController"
		view="HudsonJobsView"
	}
	HudsonBrowser {
		model="HudsonBrowserModel"
		controller="HudsonBrowserController"
		view="HudsonBrowserView"
	}
}
