>SDLoader StandAlone Clone(use griffon)


>Use Library
	griffon 0.9.2-beta-1
		http://griffon.codehaus.org/
	sdloader
		http://code.google.com/p/sdloader/

-------------------------
>How to use.


>CommandLineOption

	--Port
 Listen Port number If you do not use 30000. 

Cases)
 --port=8080

	--WebApps
 war file applications for deployment, or specify the directory.
";" To separate, you can deploy multiple applications. 
Root context, war is a directory name or file name used. 

Cases)
 --webApps=/path/to/app.war

	--Home
 SDLoader your home directory.
Under this directory "webapps" or [--webAppsDir] to deploy the application in the directory option. 
Otherwise, Java uses a run directory. 

Cases)
 --home=/path/to/sdloader

	--WebAppsDir
 specifies the directory containing your application.
If a relative path, the path is relative to the home directory. Otherwise, "webapps" is used. 

Cases)
 --webAppsDir=weblibs

	--AutoPortDetect
 designated port is in use, whether to find a free port.
Otherwise, false is used. 

Cases)
 --autoPortDetect=true

	--UseOutSidePort
 whether to use a port accessible from the outside.
If not specified, localhost only accept requests. 

Cases)
 --useOutSidePort=true

	--SslEnable
 SSL if you use.
If not specified, SSL is not used. 

Cases)
 --sslEnable=true

	--LineSpeed
 pseudo set the line speed.
Otherwise, there is no speed limit.

 Cases)
 - lineSpeed = 64000 (64Kbps only)

	--WorkDir
 JSP files for deployment, war, and specify the work directory.
Otherwise, $ {java.io.tmp} /. Sdloader used. 
Cases)
 --workDir=/path/to/dir

	--OpenBrowser
whether to open the browser (default true) false start by opening a browser <v0_3_04 support from

Cases)
 --openBrowser=false

However, this list [--help] it can output.

-----------------------------------------------------------------
>Additonal Option

	--webAppContext
";" To separate, you can deploy multiple applications.
":" To separate, contextRoot:contextRealPath.

Case)
 --webAppContext=multiDoc:multiDocRoot/bin-debug,multiDocRoot/WebContent
