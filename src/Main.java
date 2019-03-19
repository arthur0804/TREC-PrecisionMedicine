import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
import java.util.HashSet;
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
		//String filePath_queries = "/proj/wangyue/trec/pm/topics_qrel/2017/topics2017.xml";
		// read genes and diseases
		//ArrayList<String> genes = XMLParser.ReadGenes(filePath_queries);
		//ArrayList<String> diseases = XMLParser.ReadDiseases(filePath_queries);
		
		// 2.3 get query expansion terms
		//String filePath_expansionterms_disease = "/pine/scr/j/i/jiaming/expansionterms/mesh_synonyms.xml";
		//ArrayList<String> expanded_diseases = XMLParser.ReadExpandedDiseases(filePath_expansionterms_disease);	
		
		//String filePath_expansionterms_gene = "/pine/scr/j/i/jiaming/expansionterms/gene_synonyms.xml";
		//ArrayList<String> expanded_genes = XMLParser.ReadExpandedGenes(filePath_expansionterms_gene);			
		
		// 2.2 combine into the queries
		//final String boost_constant = "^0.3";
		
		// 30 queries to be executed
		//ArrayList<String> queries = new ArrayList<>();
		//for(int i = 0; i < genes.size(); i++) {
			//String disease_expansionterms = expanded_diseases.get(i);
			//String gene_expansionterms = expanded_genes.get(i);
			
			/*
			 * Disease part
			 */
			//String[] disease_expansionterms_parts = disease_expansionterms.split(" ");
			
			// remove duplicate terms in the query
			//Set<String> disease_set = new HashSet<String>();
			//for(int j = 0; j < disease_expansionterms_parts.length; j++) {
			//disease_set.add(disease_expansionterms_parts[j]);
			//}
			
			// Iterate through the set to get refined query with weight assigned
			//String disease_expansion_refined = "";
			//for(String s : disease_set) {
				//disease_expansion_refined += s + boost_constant + " ";
			//}
			
			/*
			 * Gene part
			 */
			//String[] gene_expansionterms_parts = gene_expansionterms.split(" ");
			//String gene_expansion_refined = "";
			//for(int j = 0; j < gene_expansionterms_parts.length; j++) {
				//gene_expansion_refined += gene_expansionterms_parts[j] + boost_constant + " ";
			//}
		
			//String query = diseases.get(i) + " " + genes.get(i) + " " + disease_expansion_refined + " " + gene_expansion_refined;
			//queries.add(query);
			//String query = "\"" + diseases.get(i) + "\"" + " " + genes.get(i);
			//queries.add(query);
			//}
				 
		// 2.4 run queries
		//BM25Retrieval.SearchMethod(queries);
		
		
		
		 // Create index for xml files
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
		
	}
}
