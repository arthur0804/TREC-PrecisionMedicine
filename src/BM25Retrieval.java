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
import org.apache.lucene.search.IndexSearcher; 
import org.apache.lucene.search.Query; 
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs ;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;

public class BM25Retrieval {
	public static void SearchMethod(ArrayList<String> queries) throws ParseException, IOException {
		// set directory of indexes
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index");
		Directory dir = FSDirectory.open(indexPath);
						
		// create index reader
		IndexReader reader = DirectoryReader.open(dir);
				
		// create index searcher and set BM25 similarity
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(new BM25Similarity());
				
		// create analyzer
		Analyzer analyzer = new StandardAnalyzer();
				
		// create query parser
		
		
		
		
		
	}
}
