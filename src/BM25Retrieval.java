import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader; 
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException; 
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher; 
import org.apache.lucene.search.Query; 
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs ;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;

public class BM25Retrieval {
	public static void SearchMethod(ArrayList<String> queries) throws ParseException, IOException {
		// set directory of indexes
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index_BM25");
		Directory dir = FSDirectory.open(indexPath);
						
		// create index reader
		IndexReader reader = DirectoryReader.open(dir);
				
		// create index searcher and set BM25 similarity
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(new BM25Similarity(1.2f, 0.75f));
				
		// create analyzer
		Analyzer analyzer = new StandardAnalyzer();
				
		// create query parser
		
		// execute queries and write the result into a text file			
		// create headers in the result log
		String header = "TOPIC_NO" + " " + "Q0" + " " + "ID" + " " + "RANK" + " " + "SCORE" + " " + "RUN_NAME" + "\n";
		Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/BM25Result.txt"), header.getBytes(), StandardOpenOption.APPEND);
		
		// title query
		QueryParser titleQP = new QueryParser("title", analyzer);
		titleQP.setDefaultOperator(Operator.OR);
		// content query
		QueryParser contentQP = new QueryParser("content", analyzer);
		contentQP.setDefaultOperator(Operator.OR);
		
		// iterate through the queries list to execute
		int topic_no = 1;
		for(String query : queries) {
			
			// boolean query
			Query titleQuery = titleQP.parse(query);
			Query contentQuery = contentQP.parse(query);
			BooleanClause bc1 = new BooleanClause(titleQuery, Occur.SHOULD);
			BooleanClause bc2 = new BooleanClause(contentQuery, Occur.MUST);
			BooleanQuery finalQuery = new BooleanQuery.Builder().add(bc1).add(bc2).build();
							
			// top 1000 results
			TopDocs tds = searcher.search(finalQuery, 1000);
			
			// print out the query
			String QueryLog = "The query of topic: " + String.valueOf(topic_no) + " is " + finalQuery.toString() + "\n";
			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/BM25Log.txt"), QueryLog.getBytes(), StandardOpenOption.APPEND);
			
			// document rank in the retrieval result
			int rank = 1; 
			
			for(ScoreDoc sd : tds.scoreDocs) {		
				Document document = searcher.doc(sd.doc);
						
				String TOPIC_NO = String.valueOf(topic_no);
				String Q0 = "0";
				String ID = document.get("id");
				String RANK = String.valueOf(rank);
				String SCORE = String.valueOf(sd.score);
				String RUN_NAME = "my_run";
				String NEW_RECORD = TOPIC_NO + " " + Q0 + " " + ID + " " + RANK + " " + SCORE + " " + RUN_NAME + "\n";
				Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/BM25Result.txt"), NEW_RECORD.getBytes(), StandardOpenOption.APPEND);						
				rank ++;
				// end of the loop for 1k documents
			}
		topic_no ++ ;
		// end of the loop for 30 queries
		}		
	}
}
