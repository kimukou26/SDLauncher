package sdlauncher

import static griffon.util.GriffonApplicationUtils.*
import java.awt.datatransfer.*
import java.awt.dnd.*

abstract class ConvertDropTarget extends DropTarget {

	def _func
	public ConvertDropTarget(target){
		super()
		_func = target
	}

	/** Method of calling when dropped as its name suggests */
	public void drop(DropTargetDropEvent event) {
    // It is a thing like the data form that does D&D that says fravor . 
    // The sending side mostly supports some forms to having as for mime-type. 
    // The receiver chooses from among that. In feeling of it if there is a plain text this time
//		println "a"
//		event.currentDataFlavors.mimeType.each{x ->
//			println x
//		}
//		println "b"

		def flavor = event.currentDataFlavors.find {
			it.isMimeTypeEqual("text/plain") ||
			it.isMimeTypeEqual("application/x-java-file-list")
		}

    // It returns after it event.rejectDrop()s it when not treatable well 
		//(The sending side doesn't send the plain text in case of this time). 
		if (flavor == null) {
			event.rejectDrop()
			return;
		}
		println flavor
    // After it event.acceptDrop()s it when treatable well
		event.acceptDrop(DnDConstants.ACTION_COPY)

    // Necessary processing
		if(flavor.isMimeTypeEqual("text/plain")){
			component.text = convertText(event.transferable.getTransferData(flavor))
		}
		else{
			def fileList = event.transferable.getTransferData(DataFlavor.javaFileListFlavor);
			fileList.each{file->
				if(file.isDirectory())component.text = file.getAbsoluteFile()
				else				  component.text = file.getParentFile().getAbsoluteFile()
				return
			}
		}
		println "component.text=${component.text}"
		println "${_func.dump()}"
		_func.call(component.text)

		// event.dropComplete() from return
		event.dropComplete(true)
	}

	/** The real conversion processing implements it in a subclass. */
	abstract String convertText(droped);
}

class WorkspaceDropTarget extends ConvertDropTarget {
	
	public WorkspaceDropTarget(target){
		super(target)
	}

	String convertText(droped) {
		  BufferedReader br = new BufferedReader(droped);
		  String msg = br.readLine();
		  println msg
		  return msg
	}
	public String toString() {"URL or File Path Get"}
}
