import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

public class GetDuplicateDocumentID {
	
	//Check whether there are duplicate document IDs
	// PART 1. Get document id collection
	public static void GetAllDocumentIDCollection() throws DocumentException, IOException { 
		
		// get the collection of file paths
		String filePath_collection = "/proj/wangyue/trec/pm/collection/medline_xml";		 
		ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_collection);
		
		int i = 0;
		// iterate the ArrayList to get all the document IDs
		for(String str:filePathtList) {
			XMLParser.PrintDocumentID(str);
			i ++ ;
		}
		String log = i + " files has been searched";
		Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/CheckDuplicateIDLog.txt"), log.getBytes(), StandardOpenOption.APPEND);
	}
	
	
	
	// Check whether there are duplicate document IDs
	// PART 2. Print out document IDs with occurrences >= 2
	public static void GetDuplicateDocumentIDs() throws IOException{ 
		
		// read all the document IDs
		FileInputStream inputStream = new FileInputStream("/proj/wangyue/jiamingfolder/dat/AllDocumentIDCollection.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		// crate a HashMap to store the document id its occurrences
		HashMap <String, Integer> DocumentOccurence = new HashMap<>();

		String str = null;
		while((str = bufferedReader.readLine()) != null)
		{
			if(DocumentOccurence.get(str) == null) {
				// if the ID does not exist, add it first
				DocumentOccurence.put(str, 1);
			}else {
				// else, update its occurrences
				int occurrence = DocumentOccurence.get(str) + 1;
				DocumentOccurence.put(str, occurrence);
				// write the duplicate ID into file
				String record = str + "\n";
    			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/DuplicateDocIDCollection.txt"), record.getBytes(), StandardOpenOption.APPEND);
			}			
		// end of while	
		}
		bufferedReader.close();
	}
	
	/*
	 * This method read the text file with duplicate document IDs
	 * And return a HashMap of <document ID (String), 0>  
	 * This is used for indexing, in CreateIndexMethod, if the duplicated document ID has been indexed,
	 * the value will be changed to 1
	 * 
	 */
	public static HashMap<String, Integer> GetIDMap () throws IOException{
		
		FileInputStream inputStream = new FileInputStream("/proj/wangyue/jiamingfolder/dat/DuplicateDocIDCollection.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		
		HashMap<String, Integer> DuplicateID = new HashMap<>();
		String str = null;
		while((str = bufferedReader.readLine()) != null)
		{
			// mark the occurrence as 0
			DuplicateID.put(str, 0);
			
		// end of while	
		}
		bufferedReader.close();
		return DuplicateID;	
	}
	
}
