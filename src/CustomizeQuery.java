import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader; 
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.function.BoostedQuery;
import org.apache.lucene.queryparser.classic.ParseException; 
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher; 
import org.apache.lucene.search.Query; 
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs ;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;
import org.dom4j.DocumentException;

public class CustomizeQuery {
	public static void SearchMethod(ArrayList<String> queries, int para1, int para2) throws ParseException, IOException {
		// set directory of indexes
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index_BM25_withpos");
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
		Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/searchresultandlog/combination/papersettingandbooster/D" + para1 + "G" + para2 + ".txt"), header.getBytes(), StandardOpenOption.APPEND);
		
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
						
			// boost title field
			Query boostedtitleQuery = new BoostQuery(titleQuery, 2.0f);
			Query boostedcontentQuery = new BoostQuery(contentQuery, 1.0f);
						
			BooleanClause bc1 = new BooleanClause(boostedtitleQuery, Occur.MUST);
			BooleanClause bc2 = new BooleanClause(boostedcontentQuery, Occur.MUST);
						
			BooleanQuery finalQuery = new BooleanQuery.Builder().add(bc1).add(bc2).build();
			
							
			// top 1000 results
			TopDocs tds = searcher.search(finalQuery, 1000);
			
			// print out the query
			String QueryLog = "The query of topic: " + String.valueOf(topic_no) + " is " + finalQuery.toString() + "\n";
			System.out.println(QueryLog);			
			
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
				String TYPE = document.get("doctype");
				
				// print retrieval result
				String NEW_RECORD = TOPIC_NO + " " + Q0 + " " + ID + " " + RANK + " " + SCORE + " " + RUN_NAME + "\n";
				Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/searchresultandlog/combination/papersettingandbooster/D" + para1 + "G" + para2 + ".txt"), NEW_RECORD.getBytes(), StandardOpenOption.APPEND);
					
				rank ++;
				// end of the loop for 1k documents
			}
		topic_no ++ ;
		// end of the loop for 30 queries
		}		
	}
	
	public static void SearchMethodWithBoosters(ArrayList<String> queries, String booster, int para1, int para2) throws ParseException, IOException {
		// set directory of indexes
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index_BM25_withpos");
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
		Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/searchresultandlog/combination/papersettingandbooster/D" + para1 + "G" + para2 + ".txt"), header.getBytes(), StandardOpenOption.APPEND);
		
		// title query
		QueryParser titleQP = new QueryParser("title", analyzer);
		titleQP.setDefaultOperator(Operator.OR);
		// content query
		QueryParser contentQP = new QueryParser("content", analyzer);
		contentQP.setDefaultOperator(Operator.OR);
		
		// title booster
		
		
		// content booster
		
		
		
		// iterate through the queries list to execute
		int topic_no = 1;
		for(String query : queries) {
			
			// boolean query
			Query titleQuery = titleQP.parse(query);
			Query contentQuery = contentQP.parse(query);
						
			// boost title field
			Query boostedtitleQuery = new BoostQuery(titleQuery, 2.0f);
			Query boostedcontentQuery = new BoostQuery(contentQuery, 1.0f);
						
			BooleanClause bc1 = new BooleanClause(boostedtitleQuery, Occur.MUST);
			BooleanClause bc2 = new BooleanClause(boostedcontentQuery, Occur.MUST);
						
			BooleanQuery finalQuery = new BooleanQuery.Builder().add(bc1).add(bc2).build();
			
							
			// top 1000 results
			TopDocs tds = searcher.search(finalQuery, 1000);
			
			// print out the query
			String QueryLog = "The query of topic: " + String.valueOf(topic_no) + " is " + finalQuery.toString() + "\n";
			System.out.println(QueryLog);			
			
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
				String TYPE = document.get("doctype");
				
				// print retrieval result
				String NEW_RECORD = TOPIC_NO + " " + Q0 + " " + ID + " " + RANK + " " + SCORE + " " + RUN_NAME + "\n";
				Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/searchresultandlog/combination/papersettingandbooster/D" + para1 + "G" + para2 + ".txt"), NEW_RECORD.getBytes(), StandardOpenOption.APPEND);
					
				rank ++;
				// end of the loop for 1k documents
			}
		topic_no ++ ;
		// end of the loop for 30 queries
		}		
	}
	
	
	public static void RunTests() throws DocumentException, IOException, ParseException {
			String filePath_queries = "/proj/wangyue/trec/pm/topics_qrel/2018/topics2018.xml";
			
			// read genes and diseases
			ArrayList<String> genes = XMLParser.ReadGenes(filePath_queries);
			ArrayList<String> diseases = XMLParser.ReadDiseases(filePath_queries);
					
			// 2.3 get query expansion terms
			String filePath_expansion = "/pine/scr/j/i/jiaming/expansionterms/HPI-DHC-Expansion.xml";
			ArrayList<String> expanded_diseases = XMLParser.ReadExpandedDiseases(filePath_expansion);	
			ArrayList<String> expanded_genes = XMLParser.ReadExpandedGenes(filePath_expansion);			
					
			// 2.2 combine into the queries
			
			/*
			 * try different combinations of boosts
			 * Disease/Gene: 0.1, 0.3, 0.5, 0.7
			 */
			
			// add positive and negative Boosters
			String negative_boosters = "dna tumor cell mouse model tissue development specific staining pathogenesis case";
			String positive_boosters = "gefitinib treatment survival prognostic clinical prognosis therapy outcome resistance Gleason targets";
			
			// combine into a string
			String[] negative_boosters_array = negative_boosters.split(" ");
			String[] positive_boosters_array = positive_boosters.split(" ");
			String booster = "";
			for(int i=0; i<negative_boosters_array.length;i ++) {
				booster += positive_boosters_array[i] + "^1 " + negative_boosters_array[i] + "^-1 ";
			}
			booster = booster.trim();

			int[] disease_boosts = new int[]{1,3,5};
			int[] gene_boosts = new int[]{1,3,5,7};
			
			for(int i:disease_boosts) {
				for (int j:gene_boosts) {
					
					final String disease_boost = "^0." + i; 
					final String gene_boost = "^0." + j;
					
					// 50 queries to be executed
					ArrayList<String> queries = new ArrayList<>();
					for(int m = 0; m < genes.size(); m++) {
						
						String query = diseases.get(m) + " " + genes.get(m) ;
						
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
						
						queries.add(query);

					}

					// 2.4 run queries
					CustomizeQuery.SearchMethodWithBoosters(queries, booster, i, j);
				}
			}
	}
}
