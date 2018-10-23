import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader; 
import org.apache.lucene.index.IndexReader; 
import org.apache.lucene.queryparser.classic.ParseException; 
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher; 
import org.apache.lucene.search.Query; 
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs ;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;

public class SearchWithoutTitle {
	public static void main (String args[]) throws ParseException, IOException {
		
		// only search for the CONTENT field (abstract text), which is defined in CreateIndex
		String field = "content";
		
		// set working directory
		Path indexPath = Paths.get("/Users/arthur_0804/Desktop/ThesisIndexes/IndexWithoutTitle");
		Directory dir = FSDirectory.open(indexPath);
		
		// create index reader, writer, analyzer, query parser
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser(field, analyzer);
		parser.setDefaultOperator(Operator.AND);
		
		// create a test query adapted from document 1
		Query query = parser.parse("Remote ischemic");
		System.out.println ("Query :"+ query.toString());
		
		// alternative test query
		// Query query_2 = parser.parse("This study explored cerebral metabolic rates of glucose");
		  
		// write the result into a text file
		// create headers
		String header = "TOPIC_NO" + "\t" + " Q0" + "\t" + "ID" + "\t" + "RANK" + "\t" + "SCORE" + "\t" + "RUN_NAME" + "\n";
		Files.write(Paths.get("/Users/arthur_0804/Desktop/ThesisIndexes/Output/RetrievalOutputWithoutTitle.txt"), header.getBytes(), StandardOpenOption.APPEND);
		
		// top K results, in this test case K is set to 10
		TopDocs tds = searcher.search(query, 10);
		for(ScoreDoc sd : tds.scoreDocs) {
			int rank = 1; // document rank in the retrieval result
			
			Document document = searcher.doc(sd.doc);
			
			String TOPIC_NO = "1";
			String Q0 = "0";
			String ID = document.get("id");
			String RANK = String.valueOf(rank);
			String SCORE = String.valueOf(sd.score);
			String RUN_NAME = "my_run";
			String NEW_RECORD = TOPIC_NO + "\t" + Q0 + "\t" + ID + "\t" + RANK + "\t" + SCORE + "\t" + RUN_NAME + "\n";
			Files.write(Paths.get("/Users/arthur_0804/Desktop/ThesisIndexes/Output/RetrievalOutputWithoutTitle.txt"), NEW_RECORD.getBytes(), StandardOpenOption.APPEND);
			
			rank ++;
			// System.out.println("Document index: " + sd.doc);
			// System.out.println("Article No. " + document.get("id"));
			// System.out.println("Abastract text content: " + document.get("content"));
			// System.out.println("Score: " + sd.score);
					
		}
		
	}
}
