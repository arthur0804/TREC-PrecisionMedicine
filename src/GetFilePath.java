import java.io.File;
import java.util.ArrayList;

public class GetFilePath {
	public static ArrayList<String> GetFilePaths(String filePath) {
		ArrayList<String> FilePathList = new ArrayList<String>();
		 
		 File f = null;  
	     f = new File(filePath);  
	     File[] files = f.listFiles(); // get all the files   
	     for (File file : files) {  
	    	 if(file.isDirectory()) {  
	    		 // if the current path is a folder, then get all the files inside
	    		 /*
	    		  * optional: add a if statement to make sure only include XML file
	    		  * but in this case the pre-check has been done
	    		  */
	    		 File[] subfiles = file.listFiles();
	    		 for(File subfile : subfiles) {
	    			 FilePathList.add(subfile.getAbsolutePath());
	    		 }
	         }
	      }  	
	      return FilePathList;
	}
}
