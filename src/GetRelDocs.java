import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader; 
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException; 
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher; 
import org.apache.lucene.search.Query; 
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs ;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;

public class GetRelDocs {

	/*
	 * This method get all the relevant documents with each topic from the corpus
	 * and write the tile and abstract text into the text file
	 * 
	 * input: an ArrayList of document id
	 */
	public static void GetRelevantDocuments () throws IOException, ParseException {
		
		// array of topics
		int[] topic_collection = new int[50];
		for(int i = 0; i < topic_collection.length; i++) {
			topic_collection[i] = i + 1;
		}
		
		// iterate through the topics
		for(int i = 0; i < topic_collection.length; i++) {
			// get an ArrayList of document collections with a topic no
			int topic_no = topic_collection[i];
			ArrayList<String> document_collection = GetTopicRelDoc(topic_no);
			
			// set directory of indexes
			Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index_BM25_withpos");
			Directory dir = FSDirectory.open(indexPath);
					
			// create index reader
			IndexReader reader = DirectoryReader.open(dir);
			
			// create index searcher
			IndexSearcher searcher = new IndexSearcher(reader);
			
			// create analyzer
			Analyzer analyzer = new StandardAnalyzer();
			
			// create query parser with ID field
			QueryParser queryParser = new QueryParser("id", analyzer);

			String url = "/proj/wangyue/jiamingfolder/dat/2018RelDocs/" + topic_no + ".txt";
					
			// start to search through the ArrayList
			for(int j = 0; j < document_collection.size(); j++) {
				String document_id = document_collection.get(j);
				// search for document ID
				Query query = queryParser.parse(document_id);
				// only 1 document will be retrieved
				TopDocs tds = searcher.search(query, 1);		
				
				for(ScoreDoc sd : tds.scoreDocs) {
					Document document = searcher.doc(sd.doc);
									
					String documentid = "ID" + "\t" + document.get("id") +  "\n";
					Files.write(Paths.get(url), documentid.getBytes(), StandardOpenOption.APPEND);
				    
					String documenttitle = "Title" + "\t" + document.get("title") + "\n";
				    Files.write(Paths.get(url), documenttitle.getBytes(), StandardOpenOption.APPEND);
					
				    String documentabstract = "Abstract" + "\t" + document.get("content") + "\n";
					Files.write(Paths.get(url), documentabstract.getBytes(), StandardOpenOption.APPEND);
					
				}
			// end loop of documents
			}
						
		// write into log file	
		String url_log = "/proj/wangyue/jiamingfolder/dat/2018RelDocs/log.txt";
		String log = "Topic: " + topic_no + " has " + document_collection.size() + " relevant documents" + "\n";
		Files.write(Paths.get(url_log), log.getBytes(), StandardOpenOption.APPEND);
		// end loop of topics
		}
	}

	/*
	 * this method returns an ArrayList of document id from a text file
	 */
	public static ArrayList<String> GetTopicRelDoc (int topic_no) throws IOException{
		FileInputStream inputStream = new FileInputStream("/proj/wangyue/jiamingfolder/dat/2018RelDocs/2018RelDoc.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		ArrayList<String> collection = new ArrayList<>();
		
		String str = null;
		while((str = bufferedReader.readLine()) != null)
		{
			String[] splitted = str.split(" ");
	        String topic = splitted[0].trim();
	        String document_id = splitted[1].trim();
	        // if the topic no equals the topic_no passed in, add into the ArrayList
	        if(topic.equals(String.valueOf(topic_no))) {
	        	collection.add(document_id);
	        }
		// end of while	
		}
		
		bufferedReader.close();
		return collection;	
	}
	
	public static ArrayList<String> GetAllRelDoc () throws IOException{
		FileInputStream inputStream = new FileInputStream("/proj/wangyue/jiamingfolder/dat/2018RelDocs/2018RelDoc.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));	
		ArrayList<String> collection = new ArrayList<>();	
		String str = null;
		while((str = bufferedReader.readLine()) != null)
		{
			String[] splitted = str.split(" ");
	        String document_id = splitted[1].trim();
	        collection.add(document_id);
		// end of while	
		}
		bufferedReader.close();
		return collection;	
	}
	
	public static void GetRelDocHeadings () throws ParseException, IOException {
		ArrayList<String> document_collection = GetAllRelDoc();
		
		// set directory of indexes
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index_BM25_withposheadingtype");
		Directory dir = FSDirectory.open(indexPath);
				
		// create index reader
		IndexReader reader = DirectoryReader.open(dir);
		
		// create index searcher
		IndexSearcher searcher = new IndexSearcher(reader);
		
		// create analyzer
		Analyzer analyzer = new StandardAnalyzer();
		
		// create query parser with ID field
		QueryParser queryParser = new QueryParser("id", analyzer);
		
		String url = "/proj/wangyue/jiamingfolder/dat/2018RelDocs/Headings.txt";
		
		// start to search through the ArrayList
		for(int j = 0; j < document_collection.size(); j++) {
			String document_id = document_collection.get(j);
			
			// search for document ID
			Query query = queryParser.parse(document_id);
			
			// only 1 document will be retrieved
			TopDocs tds = searcher.search(query, 1);		
			
			for(ScoreDoc sd : tds.scoreDocs) {
				Document document = searcher.doc(sd.doc);
				String documentheading = document.get("heading") +  "\n";
				Files.write(Paths.get(url), documentheading.getBytes(), StandardOpenOption.APPEND);			
			}
		}
		System.out.println(document_collection.size());
	}
	
	public static void GetRelDocTypes () throws ParseException, IOException{
		ArrayList<String> document_collection = GetAllRelDoc();
		
		// set directory of indexes
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index_BM25_withposheadingtype");
		Directory dir = FSDirectory.open(indexPath);
				
		// create index reader
		IndexReader reader = DirectoryReader.open(dir);
		
		// create index searcher
		IndexSearcher searcher = new IndexSearcher(reader);
		
		// create analyzer
		Analyzer analyzer = new StandardAnalyzer();
		
		// create query parser with ID field
		QueryParser queryParser = new QueryParser("id", analyzer);
		
		String url = "/proj/wangyue/jiamingfolder/dat/2018RelDocs/Types.txt";
			
		// start to search through the ArrayList
		for(int j = 0; j < document_collection.size(); j++) {
			String document_id = document_collection.get(j);
			
			// search for document ID
			Query query = queryParser.parse(document_id);
			
			// only 1 document will be retrieved
			TopDocs tds = searcher.search(query, 1);		
			
			for(ScoreDoc sd : tds.scoreDocs) {
				Document document = searcher.doc(sd.doc);
				String documenttype = document.get("doctype") +  "\n";
				Files.write(Paths.get(url), documenttype.getBytes(), StandardOpenOption.APPEND);			
			}
		}
		System.out.println(document_collection.size());
	}
	
}
