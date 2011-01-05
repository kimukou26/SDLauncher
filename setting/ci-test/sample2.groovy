import hudson.util.*
import hudson.model.*
script = """new File(".").eachFile { println it }"""
RemotingDiagnostics.executeGroovy(script, Hudson.MasterComputer.localChannel)
