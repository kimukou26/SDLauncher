/*
 * This script is executed inside the UI thread, so be sure to  call 
 * long running code in another thread.
 *
 * You have the following options
 * - execOutside { // your code }
 * - execFuture { // your code }
 * - Thread.start { // your code }
 *
 * You have the following options to run code again inside the UI thread
 * - execAsync { // your code }
 * - execSync { // your code }
 */



import java.io.*
import java.nio.channels.*

 final FileOutputStream fos = new FileOutputStream(new File("lock"))
 final FileChannel fc = fos.getChannel()
 final FileLock lock = fc.tryLock()
 if (lock == null) {
	 //exists process => to end
	 System.exit(0)
	 return
 }
 //lock action regist
 Runtime.getRuntime().addShutdownHook(
	 new Thread() {
		 public void run() {
			 //shutdown hook
			 griffon.util.ApplicationHolder.application.config.shutdown_hook_f=true
			 
			 if (lock != null && lock.isValid()) {
				 lock.release()
			 }
			 fc.close()
			 fos.close()
			 
			 //Map arg = new HashMap()
			 griffon.util.ApplicationHolder.application.config.controller.mvcGroupDestroy();//arg) //db close connection
		 }
	 }
 )





//statup log delete
import java.io.File;
import java.io.FilenameFilter;

FilenameFilter logfilter = new FilenameFilter() {
	public boolean accept(File dir, String name) {
		if (name.endsWith(".log") ) {
			return true;
		}
		return false;
	}
}

srcDir="logs"
println "srcDir=${srcDir}"
File[] files = new File("${srcDir}").listFiles(logfilter);
println files

st_time = new Date()
files.each{
	it.delete()
}











import groovy.swing.SwingBuilder
import static griffon.util.GriffonApplicationUtils.*

SwingBuilder.lookAndFeel((isMacOSX ? 'system' : 'nimbus'), 'gtk', ['metal', [boldFonts: false]])


//Exception catch logger
import groovy.swing.SwingBuilder
import static griffon.util.GriffonApplicationUtils.*

if(griffon.util.RunMode.current==griffon.util.RunMode.STANDALONE && new File('setting/log4j.xml').exists()){
	org.apache.log4j.xml.DOMConfigurator.configure("setting/log4j.xml")
}
else{
	org.apache.log4j.BasicConfigurator.configure()
}

import org.apache.log4j.Logger
import org.codehaus.groovy.runtime.StackTraceUtils
import java.lang.Thread.UncaughtExceptionHandler

class LoggingExceptionHandler implements UncaughtExceptionHandler {
 private static Logger logger = Logger.getLogger(LoggingExceptionHandler.class)

 public void uncaughtException(Thread t, Throwable e)
 {
   logger.error("Uncaught exception ${e.message}", StackTraceUtils.deepSanitize(e) )
 }
}

Thread.setDefaultUncaughtExceptionHandler(new LoggingExceptionHandler());
System.setProperty("sun.awt.exception.handler",LoggingExceptionHandler.class.getName())


//grobal access pointer setting
griffon.util.ApplicationHolder.application = app
