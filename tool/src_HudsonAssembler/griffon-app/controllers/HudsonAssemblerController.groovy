import groovy.xml.*
import java.util.jar.*
import javax.swing.*
import net.sf.json.groovy.*

class HudsonAssemblerController {

    final CERTIFICATE_FILES = ["META-INF/HUDSON.SF", "META-INF/HUDSON.RSA"]

    def model
    def view

    void mvcGroupInit(Map args) {
        //def jsonp = new URL('http://updates.hudson-labs.org/update-center.json').text
        def jsonp = new URL('http://updates.jenkins-ci.org/update-center.json').text
        def jsonText = jsonp.substring(jsonp.indexOf('(') + 1, jsonp.lastIndexOf(')')).trim()
        def json = new JsonSlurper().parseText(jsonText)
        model.coreUrl = json.core.url
        model.plugins = json.plugins.collect {[
            install:false,
            name:it.key,
            displayName:(it.value.title ?: it.key),
            wiki:it.value.wiki,
            url:it.value.url,
            excerpt:it.value.excerpt,
            version:it.value.version,
            dependencies:it.value.dependencies
        ]}.sort { a, b -> a.displayName.toUpperCase() <=> b.displayName.toUpperCase()}



//20101105 add start kimukou.buzz
     	def key
      def argsSet = [:]
      app.getStartupArgs().each{
          //println "${it}"
          String val = it
//griffon run-app Provisional correspondence  start==>
/*
          if(val.startsWith("'")){
            val = val.replaceAll("'","")
            key = val
          }
          else if(val.endsWith("'")){
            val = val.replaceAll("'","")
						println "(${key},${val})"
            argsSet.put(key,val)
          }
*/
//griffon run-app Provisional correspondence  end<==
          //else{
						String[] sp = val.split("=")
						println "(${sp[0]},${sp[1]})"
            argsSet.put(sp[0],sp[1])
          //}
      }
			model.xmlCurPath = System.getProperty('user.home')
			model.warCurPath = System.getProperty('user.home')

			if(argsSet.containsKey("--xmlCurPath")){
				model.xmlCurPath = argsSet.get("--xmlCurPath")
			}
			if(argsSet.containsKey("--warCurPath")){
				model.warCurPath = argsSet.get("--warCurPath")
			}
//20101105 add end kimukou.buzz


    }

    def save = { evt ->
        if (!evt.source.selectedFile) return;
        new MarkupBuilder(evt.source.selectedFile.newWriter()).hudson() {
            model.plugins.findAll { it.install }.each { plugin(name:it.name) }
        }
				model.xmlCurPath = evt.source.selectedFile.parentFile.absolutePath //20101105 kimukou.buzz add
    }

    def open = { evt ->
        if (!evt.source.selectedFile) return;
        def pluginNames = new XmlSlurper().parse(evt.source.selectedFile).plugin.@name*.text()   
        model.plugins.each { it.install = pluginNames.contains(it.name) }
        view.pluginTableModel.rowsModel.value = model.plugins
				model.xmlCurPath = evt.source.selectedFile.parentFile.absolutePath //20101105 kimukou.buzz add
    }

    def assemble = { evt ->
        if (!evt.source.selectedFile) return;
        doOutside {
            resolveDependencies model.plugins, model.plugins.findAll { it.install }
            view.pluginTable.enabled = false
            try {
                //def jar = new JarInputStream(toInputStream(model.coreUrl, 'hudson.war'))
								def jar = null
                try{
									jar = new JarInputStream(toInputStream(model.coreUrl, 'hudson.war'))
								}
								catch(Exception ex){ ex.printStackTrace()}
                def dest = new JarOutputStream(evt.source.selectedFile.newOutputStream(), jar.getManifest());
                def defaultPlugins = []
                def entry
                while (entry = jar.nextJarEntry) {
                    (entry.name =~ /WEB-INF\/plugins\/(.*)\.hpi/).with {
                        if (it) defaultPlugins << it[0][1]
                    }
                    if (!CERTIFICATE_FILES.contains(entry.name)) {
                        dest.putNextEntry(entry)
                        dest << jar
                        jar.closeEntry()
                        dest.closeEntry()
                    }
                }
                model.plugins.findAll { it.install && !defaultPlugins.contains(it.name) }.each {
                    entry = new JarEntry("WEB-INF/plugins/${it.name}.hpi")
                    dest.putNextEntry(entry)
                    dest << toInputStream(it.url, it.name)
                    dest.closeEntry()
                }
                jar.close()
                dest.close()
            } finally {
                view.pluginTable.enabled = true
            }
        }
    }

    def toInputStream = { url, name ->
				//println "$url,$name"
        def u = new URL(url)
        def uc = u.openConnection()
				//println "uc = $uc"
			
//griffon 0.9 instead add start
        def result = new ProgressMonitorInputStream(app.windowManager.windows[0], "Downloading $name", uc.inputStream)
        //def result = new ProgressMonitorInputStream(app.appFrames[0], "Downloading $name", uc.inputStream)
//griffon 0.9 instead add end
				//println "contentLength = ${uc.contentLength}"
				//println "P: result.progressMonitor.maximum $result.progressMonitor.maximum"
        result.progressMonitor.maximum = uc.contentLength
				//println "A: result.progressMonitor.maximum $result.progressMonitor.maximum"
				println "result=${result.dump()}"
        return result
    }

    def resolveDependencies = { allPlugins, targetPlugins, resolved = [] ->
        targetPlugins.each {
            resolved << it.name
            it.install = true
            resolveDependencies allPlugins, it.dependencies.findAll { !it.optional && !resolved.contains(it.name) }.collect { depends ->
                allPlugins.find {it.name == depends.name}
            }, resolved
        }
    }
}