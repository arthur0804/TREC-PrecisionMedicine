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
import java.util.HashMap;
import java.util.Map.Entry;

public class GetDuplicateDocumentID {
	/*
	 * This method read the text file with duplicate document IDs
	 * And return a HashMap of <document ID (String), 0>  
	 * This is used for indexing, in CreateIndexMethod, if the duplicated document ID has been indexed,
	 * the value will be changed to 1
	 * 
	 */
	public static HashMap<String, Integer> GetIDMap () throws IOException{
		
		FileInputStream inputStream = new FileInputStream("/proj/wangyue/jiamingfolder/dat/duplicateID.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		
		HashMap<String, Integer> DuplicateID = new HashMap<>();
		String str = null;
		while((str = bufferedReader.readLine()) != null)
		{
			DuplicateID.put(str, 0);			
		// end of while	
		}
		// should be 518 rows
		return DuplicateID;	
	}
	
}
