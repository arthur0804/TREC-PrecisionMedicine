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
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException; 
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher; 
import org.apache.lucene.search.Query; 
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs ;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;


public class BooleanRetrieval {
	public static void SearchMethod(ArrayList<String> queries) throws ParseException, IOException {

		// set directory of indexes
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index");
		Directory dir = FSDirectory.open(indexPath);
				
		// create index reader, writer, analyzer, query parser
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		
		// execute queries and write the result into a text file			
		// create headers in the result log
		String header = "TOPIC_NO" + " " + " Q0" + " " + "ID" + " " + "RANK" + " " + "SCORE" + " " + "RUN_NAME" + "\n";
		Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/boolean_retrieval.txt"), header.getBytes(), StandardOpenOption.APPEND);
			
		// iterate through the queries list to execute
		int topic_no = 1;
		for(String query : queries) {
			String[] SplittedQueryTerms = query.split("\\s+");
			Query[] QuerryTermArray = new Query[SplittedQueryTerms.length];
			BooleanClause[] booleanClauses = new BooleanClause[SplittedQueryTerms.length];
			
			for(int i = 0; i < SplittedQueryTerms.length; i++) {
				QuerryTermArray[i] = new TermQuery (new Term("content",  SplittedQueryTerms[i]));
				booleanClauses[i] = new BooleanClause (QuerryTermArray[i], Occur.MUST);
			}
			
			switch (booleanClauses.length) {
			case 1:
				BooleanQuery boolQuery_1=new BooleanQuery.Builder().add(booleanClauses[0]).build();
				TopDocs tds_1 = searcher.search(boolQuery_1, 1000);
				rankDocuments (searcher, tds_1,topic_no);
				break;
			case 2:
				BooleanQuery boolQuery_2=new BooleanQuery.Builder().add(booleanClauses[0]).add(booleanClauses[1]).build();
				TopDocs tds_2 = searcher.search(boolQuery_2, 1000);
				rankDocuments (searcher, tds_2,topic_no);
				break;
			case 3:
				BooleanQuery boolQuery_3=new BooleanQuery.Builder().add(booleanClauses[0]).add(booleanClauses[1]).add(booleanClauses[2]).build();
				TopDocs tds_3 = searcher.search(boolQuery_3, 1000);
				rankDocuments (searcher, tds_3,topic_no);
				break;
			default:
				BooleanQuery boolQuery_4=new BooleanQuery.Builder().add(booleanClauses[0]).add(booleanClauses[1]).add(booleanClauses[2]).add(booleanClauses[3]).build();
				TopDocs tds_4 = searcher.search(boolQuery_4, 1000);
				rankDocuments (searcher, tds_4,topic_no);
				break;
			}			
		topic_no ++ ;
		// end of the loop for 30 queries
		}	
	}
	public static void rankDocuments (IndexSearcher searcher, TopDocs tds,int topic_no) throws IOException {
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
					Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/boolean_retrieval.txt"), NEW_RECORD.getBytes(), StandardOpenOption.APPEND);
								
					rank ++;
					// end of the loop for 1k documents
			}
	}
}
