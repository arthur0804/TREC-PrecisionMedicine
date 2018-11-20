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
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException; 
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher; 
import org.apache.lucene.search.Query; 
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs ;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;

public class TopicOneSearch {
	public static void SearchMethod() throws ParseException, IOException {
		String[] fields ={"title", "content"};
		
		// set directory of indexes
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index");
		Directory dir = FSDirectory.open(indexPath);
		
		// create index reader, writer, analyzer, query parser
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		MultiFieldQueryParser parser = new MultiFieldQueryParser (fields, analyzer);
		parser.setDefaultOperator(Operator.OR);
		
		// Query multifieldquery = parser.parse(query);
		Query multifieldquery = parser.parse("Liposarcoma CDK4 GERD");
		
		// top 1000 results
		TopDocs tds = searcher.search(multifieldquery, 1000);
		// document rank in the retrieval result
		int rank = 1; 
		
		for(ScoreDoc sd : tds.scoreDocs) {		
			Document document = searcher.doc(sd.doc);
			
			String ID = document.get("id");
			String log = ID + "\n";
			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/topiconequery1120_did.txt"), log.getBytes(), StandardOpenOption.APPEND);
			
			//String RANK = String.valueOf(rank);
			//String SCORE = String.valueOf(sd.score);
			//String HEADER = RANK  + "\t" + SCORE + "\t"+ ID +"\n";
			//Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/topiconequery1120_4.txt"), HEADER.getBytes(), StandardOpenOption.APPEND);
			
			//String TITLE = document.get("title") + "\n";
			//Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/topiconequery1120_4.txt"), TITLE.getBytes(), StandardOpenOption.APPEND);
			
			//String CONTENT = document.get("content") + "\n";
			//Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/topiconequery1120_4.txt"), CONTENT.getBytes(), StandardOpenOption.APPEND);
								
			rank ++;
			// end of the loop for 1k documents
		}
	}
}
