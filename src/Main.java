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
		 * Create index for xml files
		 // 1. create index
		 // 1.1 get file path of collections
		 String filePath_collection = "/proj/wangyue/trec/pm/collection/medline_xml";
		
		 // 1.2 get all the URLs in this file path
		 ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_collection);
		
		 // get the info the duplicate documents
		 HashMap<String, Integer> DuplicateDocs = GetDuplicateDocumentID.GetIDMap();
		 
		 // 1.3 iterate through the list and create index for each file
		 int i = 0;
		 for(String str:filePathtList) {
			// create index and update the duplicate docs HashMap
			DuplicateDocs = CreateIndexWithTitle.CreateIndexMethod(str, DuplicateDocs); 
			String log = str + "\n";
			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/indexinglog.txt"), log.getBytes(), StandardOpenOption.APPEND);
			i++;
		 }	
		 String total = i + " XML files has been indexed";
		 Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/indexinglog.txt"), total.getBytes(), StandardOpenOption.APPEND);
		 
		 
		 *
		 *	Create index for extra topics
		 	// 1.1 get file path of collections
		 	String filePath_collection = "/proj/wangyue/trec/pm/collection/extra_abstracts";
		
		 	// 1.2 get all the URLs in this file path
		 	ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_collection);
	
		 	int i = 0;
		 	// 1.3 iterate through the list and create index for each file
		 	for(String str:filePathtList) {
				// create index and update the duplicate docs HashMap
				CreateIndexExtraTopics.CreateIndexMethod(str);
				i++;
		 	}	
		 
		 	// check the result
		 	String log = i + " extra topics are indexed" + "\n";
		 	Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/indexinglog.txt"), log.getBytes(), StandardOpenOption.APPEND);	
		 */
		
		// 2. query
		// 2.1 file path 
		// String filePath_queries = "/proj/wangyue/trec/pm/topics_qrel/2017/topics2017.xml";
		// read genes and diseases
		// ArrayList<String> genes = XMLParser.ReadGenes(filePath_queries);
		// ArrayList<String> diseases = XMLParser.ReadDiseases(filePath_queries);
		// ArrayList<String> otherinfo = XMLParser.ReadOtherInfo(filePath_queries);
		 	
		
		// 2.2 combine into the queries
		//ArrayList<String> queries = new ArrayList<>();
		//for(int i = 0; i < genes.size(); i++) {
		//String query = diseases.get(i) + " " + genes.get(i);
		//	queries.add(query);
		// }
		 
		// 2.4 run queries
		//BM25Retrieval.SearchMethod(queries);

		
		// 2. query
		// 2.1 file path 
		String filePath_queries = "/pine/scr/j/i/jiaming/mesh_expanded.xml";
		// read genes and diseases
		ArrayList<String> genes = XMLParser.ReadGenes(filePath_queries);
		ArrayList<String> diseases = XMLParser.ReadDiseases(filePath_queries);
		
		// 2.2 combine into the queries
		ArrayList<String> queries = new ArrayList<>();
		for(int i = 0; i < genes.size(); i++) {
			String query = diseases.get(i) + " " + genes.get(i);
			queries.add(query);
		}
				 
		// 2.4 run queries
		BM25Retrieval.SearchMethod(queries);

	}
}
