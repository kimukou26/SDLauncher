// key signing information
environments {
    development {
        signingkey {
            params {
                sigfile = 'GRIFFON'
                keystore = "${basedir}/griffon-app/conf/keys/devKeystore"
                alias = 'development'
                storepass = 'BadStorePassword'
                keypass   = 'BadKeyPassword'
                lazy      = true // only sign when unsigned
            }
        }

    }
    test {
        griffon {
            jars {
                sign = false
                pack = false
            }
        }
    }
    production {
        signingkey {
            params {
                sigfile = 'GRIFFON'
                keystore = "$basedir/../../debug.keystore"
                alias = 'androiddebugkey'
                storepass = 'android'
                keypass   = 'android'
                //keystore = "${System.getProperty('user.home')}/.keystore"
                //alias = 'JenkinsAssemblerKey'
                //storepass = null
                //keypass = null
                lazy = false
            }
        }

        griffon {
            jars {
                sign = true
                pack = true
                destDir = "${basedir}/staging"
            }
            webstart {
                codebase = 'http://jenkins-assembler.googlecode.com/files/'
            }
        }
    }
}

griffon {
    memory {
        //max = '64m'
        //min = '2m'
        //maxPermSize = '64m'
    }
    jars {
        sign = false
        pack = false
        destDir = "${basedir}/staging"
        jarName = "${appName}.jar"
    }
    extensions {
        jarUrls = []
        jnlpUrls = []
        /*
        props {
            someProperty = 'someValue'
        }
        resources {
            linux { // windows, macosx, solaris
                jars = []
                nativelibs = []
                props {
                    someProperty = 'someValue'
                }
            }
        }
        */
    }
    webstart {
        codebase = "${new File(griffon.jars.destDir).toURI().toASCIIString()}"
        jnlp = 'application.jnlp'
    }
    applet {
        jnlp = 'applet.jnlp'
        html = 'applet.html'
    }
}

// required for custom environments
signingkey {
    params {
        def env = griffon.util.Environment.current.name
        sigfile = 'GRIFFON-' + env
        keystore = "${basedir}/griffon-app/conf/keys/${env}Keystore"
        alias = env
        // storepass = 'BadStorePassword'
        // keypass   = 'BadKeyPassword'
        lazy      = true // only sign when unsigned
    }
}

griffon.project.dependency.resolution = {
    // inherit Griffon' default dependencies
    inherits("global") {
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        griffonPlugins()
        griffonHome()
        griffonCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        compile group:'net.sf.json-lib', name:'json-lib', version:'2.3', classifier:'jdk15'
        compile 'commons-beanutils:commons-beanutils:1.8.0'
        compile 'commons-collections:commons-collections:3.2.1'
        compile 'commons-httpclient:commons-httpclient:3.1'
        compile 'commons-lang:commons-lang:2.4'
        compile 'net.sf.ezmorph:ezmorph:1.0.6'
    }
}

griffon {
    doc {
        logo = '<a href="http://griffon.codehaus.org" target="_blank"><img alt="The Griffon Framework" src="../img/griffon.png" border="0"/></a>'
        sponsorLogo = "<br/>"
        footer = "<br/><br/>Made with Griffon (0.9.2-rc1)"
    }
}

app.archetype = 'default'
app.fileType = '.groovy'
