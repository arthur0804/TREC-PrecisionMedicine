import java.io.IOException; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
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

public class SearchWithTitle {
	public static void main(String[] args) throws ParseException, IOException {

				// search for the CONTENT field (abstract text) and Title field , which is defined in CreateIndex
				String[] fields ={"title", "content"};
				
				// set working directory
				Path indexPath = Paths.get("/Users/arthur_0804/Desktop/ThesisIndexes/IndexWithTitle");
				Directory dir = FSDirectory.open(indexPath);
				
				// create index reader, writer, analyzer, query parser
				IndexReader reader = DirectoryReader.open(dir);
				IndexSearcher searcher = new IndexSearcher(reader);
				Analyzer analyzer = new StandardAnalyzer();
				MultiFieldQueryParser parser = new MultiFieldQueryParser (fields, analyzer);
				parser.setDefaultOperator(Operator.AND);
				
				// create a test query adapted from document 1
				Query multifieldquery = parser.parse("Remote ischemic");
				System.out.println ("Query :"+ multifieldquery.toString());
				
				// alternative test query
				// Query query_2 = parser.parse("This study explored cerebral metabolic rates of glucose");
				
				// top 10 results
				TopDocs tds = searcher.search(multifieldquery, 10);
				for(ScoreDoc sd : tds.scoreDocs) {
					Document document = searcher.doc(sd.doc);
					System.out.println("Document index: " + sd.doc);
					System.out.println("Article No. " + document.get("id"));
					System.out.println("Abastract text content: " + document.get("content"));
					System.out.println("Score: " + sd.score);
					
				}		
	}
}
