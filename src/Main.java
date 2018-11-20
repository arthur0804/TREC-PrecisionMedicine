import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.lucene.queryparser.classic.ParseException;
import org.dom4j.DocumentException;

public class Main {

	public static void main(String[] args) throws IOException, DocumentException, ParseException {
		
		/*
		 * INDEXED HAVE BEEN CREATED
		 * 
		 * DO
		 * NOT
		 * CALL
		 * THIS
		 * 
		 1. create index
		 1.1 get file path of collections
		 String filePath_collection = "/proj/wangyue/trec/pm/collection/medline_xml"; 
		
		 1.2 get all the URLs in this file path
		 ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_collection);
		
		 1.3 iterate through the list and create index for each file
		 for(String str:filePathtList) {
			CreateIndexWithTitle.CreateIndexMethod(str);
			String log = "indexed: " + str + "\n";
			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/log.txt"), log.getBytes(), StandardOpenOption.APPEND);
		 }	
		 *
		 */
		
		// 2. query
		// 2.1 file path 
		//String filePath_queries = "/proj/wangyue/trec/pm/topics_qrel/2017/topics2017.xml";
		// read genes and diseases
		//ArrayList<String> genes = XMLParser.ReadGenes(filePath_queries);
		//ArrayList<String> diseases = XMLParser.ReadDiseases(filePath_queries);
		
		// 2.2 query expansion
		//ArrayList<String> expanded_genes = QueryExpansion.ExpandGene(genes);
		//ArrayList<String> expanded_diseases = QueryExpansion.ExpandDisease(diseases);
		
		// 2.3 combine into the queries
		//ArrayList<String> queries = new ArrayList<>();
		//for(int i = 0; i < expanded_genes.size(); i++) {
		//	String query = expanded_diseases.get(i) + expanded_genes.get(i);
		//	queries.add(query);
		//}
		
		// 2.4 run queries
		//SearchWithTitle.SearchMethod(queries);

			
		/*
		 * Get document id collection
		 *  
		 * DO
		 * NOT
		 * CALL
		 * THIS
		 * 			
			String filePath_collection = "/proj/wangyue/trec/pm/collection/medline_xml";		 
	 		ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_collection);
	 		for(String str:filePathtList) {
			XMLParser.GetDocumentID(str);
			String log = str + "\n";
        	Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/documentidcollectionlog.txt"), str.getBytes(), StandardOpenOption.APPEND);
	 		}
		 * 	
		 */
		
		/*
		 * check whether there are duplicate document IDs
		 *  
		 * DO
		 * NOT
		 * CALL
		 * THIS
		 * 
			FileInputStream inputStream = new FileInputStream("/proj/wangyue/jiamingfolder/dat/documentidcollection.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
			// crate a hashmap to store the document id its occurrences
			HashMap <String, Integer> DocumentOccurence = new HashMap<>();
			
			String str = null;
			while((str = bufferedReader.readLine()) != null)
			{
				if(DocumentOccurence.get(str) == null) {
					// if the file not exist, add first
					DocumentOccurence.put(str, 1);
				}else {
					// else, update its occurrences
					int occurrence = DocumentOccurence.get(str) + 1;
					DocumentOccurence.put(str, occurrence);
				}			
			// end of while	
			}
			bufferedReader.close();
		
			// write the result into text file
			for (Entry<String, Integer> entry : DocumentOccurence.entrySet()) {
		    	String key = entry.getKey();
		    	int value = entry.getValue();
		    	String record = key + "\t" + value + "\n";
		    	Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/checkduplicate.txt"), record.getBytes(), StandardOpenOption.APPEND);
			} 
		 * 	
		 */

		
		TopicOneSearch.SearchMethod();
		
	}
}
