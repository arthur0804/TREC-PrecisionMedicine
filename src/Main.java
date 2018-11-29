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
		 * INDEXES HAVE BEEN CREATED
		 * 
		 * DO
		 * NOT
		 * CALL
		 * THIS
		 * 
		 // 1. create index
		 // 1.1 get file path of collections
		 String filePath_collection = "/proj/wangyue/trec/pm/collection/medline_xml";
		
		 // 1.2 get all the URLs in this file path
		 ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_collection);
		
		 // get the info the duplicate documents
		 HashMap<String, Integer> DuplicateDocs = GetDuplicateDocumentID.GetIDMap();
		 
		 // 1.3 iterate through the list and create index for each file
		 for(String str:filePathtList) {
			DuplicateDocs = CreateIndexWithTitle.CreateIndexMethod(str, DuplicateDocs); // create index and update the duplicate docs HashMap
			String log = str + "\n";
			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/indexinglog.txt"), log.getBytes(), StandardOpenOption.APPEND);
		 }	
		 *
		 */
		
		// 2. query
		// 2.1 file path 
		 String filePath_queries = "/proj/wangyue/trec/pm/topics_qrel/2017/topics2017.xml";
		// read genes and diseases
		 ArrayList<String> genes = XMLParser.ReadGenes(filePath_queries);
		 ArrayList<String> diseases = XMLParser.ReadDiseases(filePath_queries);
		 ArrayList<String> otherinfo = XMLParser.ReadOtherInfo(filePath_queries);
		 
		// 2.2 query expansion
		//ArrayList<String> expanded_genes = QueryExpansion.ExpandGene(genes);
		//ArrayList<String> expanded_diseases = QueryExpansion.ExpandDisease(diseases);
		
		// 2.3 combine into the queries
		ArrayList<String> queries = new ArrayList<>();
		 for(int i = 0; i < genes.size(); i++) {
			String query = diseases.get(i) + " " + genes.get(i) + " " + otherinfo.get(i);
			queries.add(query);
		 }
		
		// 2.4 run queries
		BM25Retrieval.SearchMethod(queries);

			
		/*
		 * Check whether there are duplicate document IDs
		 * PART 1. Get document id collection
		 * called on Nov.20, results are in /proj/wangyue/jiamingfolder/dat/documentidcollection.txt
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
		 * Check whether there are duplicate document IDs
		 * PART 2. Print out document ids with occurrences >= 2
		 * called on Nov.20, results are in /proj/wangyue/jiamingfolder/dat/checkduplicate.txt
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
						String record = str + "\n";
		    			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/printduplicateid.txt"), record.getBytes(), StandardOpenOption.APPEND);
					}			
				// end of while	
				}
				bufferedReader.close();
		 * 	
		 */
		
		/*
		 * !TEST CASE!
		 * Specific topic search: topic one
		 * check whether there are duplicate document IDs in the 1,000 result
		 * 
		 * 1. get 1,000 document IDs: /proj/wangyue/jiamingfolder/dat/topiconequery1120_did
		 * 
		 * 		TopicOneSearch.SearchMethod();
		 * 
		 * 2. checked result is in /proj/wangyue/jiamingfolder/dat/checktopiconeresultduplicate.txt
		 * 
		 * 		FileInputStream inputStream = new FileInputStream("/proj/wangyue/jiamingfolder/dat/topiconequery1120_did.txt");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	
				// crate a hashmap to store the document id its occurrences
				HashMap <String, Integer> DocumentOccurence = new HashMap<>();
		
				String str = null;
				int line_num = 0;
				while((str = bufferedReader.readLine()) != null)
				{
					if(DocumentOccurence.get(str) == null) {
						// if the file not exist, add first
						DocumentOccurence.put(str, 1);
						line_num ++;
					}else {
						// else, update its occurrences
						int occurrence = DocumentOccurence.get(str) + 1;
						DocumentOccurence.put(str, occurrence);
						line_num ++;
					}			
				// end of while	
				}
				bufferedReader.close();
	
				// write the result into text file
				for (Entry<String, Integer> entry : DocumentOccurence.entrySet()) {
	    			String key = entry.getKey();
	    			int value = entry.getValue();
	    			String record = key + "\t" + value + "\n";
	    			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/checktopiconeresultduplicate.txt"), record.getBytes(), StandardOpenOption.APPEND);
				} 
				String lines = "total lines are:" + line_num;
				Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/checktopiconeresultduplicate.txt"), lines.getBytes(), StandardOpenOption.APPEND);
		 * 
		 * 
		 */
		
	}
}
