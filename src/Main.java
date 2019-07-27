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
	
	public static void RunSearching() throws DocumentException, IOException, ParseException {

			String filePath_queries = "/proj/wangyue/trec/pm/topics_qrel/2018/topics2018.xml";
			
			// read genes and diseases
			ArrayList<String> genes = XMLParser.ReadGenes(filePath_queries);
			ArrayList<String> diseases = XMLParser.ReadDiseases(filePath_queries);
					
			// 2.3 get query expansion terms
			String filePath_expansion = "/pine/scr/j/i/jiaming/expansionterms/new_HPI-DHC-Expansion.xml";
			ArrayList<String> expanded_diseases = XMLParser.ReadExpandedDiseases(filePath_expansion);	
			ArrayList<String> expanded_genes = XMLParser.ReadExpandedGenes(filePath_expansion);	
			
			String filePath_acronym = "/pine/scr/j/i/jiaming/expansionterms/acronyms.xml";
			ArrayList<String> diseases_acronym = XMLParser.ReadDiseases(filePath_acronym);
			
			final String disease_boost = "^0.1"; 
			final String gene_boost = "^0.3";
			final String acronym_boost = "^0.5";
			
			ArrayList<String> queries = new ArrayList<>();
			for(int m = 0; m < genes.size(); m++) {
				
				String query = diseases.get(m) + " "+ genes.get(m);
				
				// expansion
				String disease_expansionterms = expanded_diseases.get(m);
				String gene_expansionterms = expanded_genes.get(m);
						
				/*
				* Disease part
				*/
				String[] disease_expansionterms_parts = disease_expansionterms.split(" ");
				
				if (disease_expansionterms_parts.length != 1) {
					String disease_expansion_boosted = "";
					for(int k = 0; k < disease_expansionterms_parts.length; k++) {
						disease_expansion_boosted += disease_expansionterms_parts[k] + disease_boost + " ";
					}
					disease_expansion_boosted = disease_expansion_boosted.trim();
					query += " " + disease_expansion_boosted ;
				}
				 
				/*
				* Gene part
				*/
				String[] gene_expansionterms_parts = gene_expansionterms.split(" ");
				if (gene_expansionterms_parts.length != 1) {
					String gene_expansion_boosted = "";
					for(int k = 0; k < gene_expansionterms_parts.length; k++) {
						gene_expansion_boosted += gene_expansionterms_parts[k] + gene_boost + " ";
					}
					gene_expansion_boosted = gene_expansion_boosted.trim();
					
					// replace forward slash
					gene_expansion_boosted = gene_expansion_boosted.replaceAll("/", " ");
					query += " " + gene_expansion_boosted;
				}						
				
				// add acronyms
				String acronyms = diseases_acronym.get(m);
				if (acronyms.length() != 0) {
					String acronym_boosted = "";
					String[] acronyms_parts = acronyms.split(" ");
					for(int k = 0; k < acronyms_parts.length; k++) {
						acronym_boosted += acronyms_parts[k] + acronym_boost + " ";
					}
					acronym_boosted = acronym_boosted.trim();
					query += " " + acronym_boosted;
				}
						
				queries.add(query);
			}
			
			
			BM25Retrieval.SearchMethod(queries);
	}

	public static void RunIndexing() throws IOException, DocumentException {
		/*
		 * INDEXES HAVE BEEN CREATED
		 */
		
		 // Create index for XML files
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
			//String log = str + "\n";
			//Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/indexinglog.txt"), log.getBytes(), StandardOpenOption.APPEND);
			i++;
		 }	
		 String total = i + " XML files has been indexed \n";
		 Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/indexinglog.txt"), total.getBytes(), StandardOpenOption.APPEND);
	}
	
	public static void main(String[] args) throws IOException, DocumentException, ParseException {
		RunSearching();
	}
}
