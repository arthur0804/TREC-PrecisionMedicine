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
	    		 File[] subfiles = file.listFiles();
	    		 for(File subfile : subfiles) {
	    			 FilePathList.add(subfile.getAbsolutePath());
	    		 }
	         }else {
	        	 // if the current path is not a folder, then get the file path
	        	 FilePathList.add(file.getAbsolutePath());
	         }
	      }  	
	      return FilePathList;
	}
}
