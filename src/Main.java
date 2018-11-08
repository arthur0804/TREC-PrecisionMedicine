import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;
import org.dom4j.DocumentException;

public class Main {

	public static void main(String[] args) throws IOException, DocumentException, ParseException {
		// 1. create index
		// 1.1 get file path of collections
		String filePath_collection = "/proj/wangyue/trec/pm/collection/medline_xml"; 
		
		// 1.2 get all the URLs in this file path
		ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_collection);
		
		// 1.3 iterate through the list and create index for each file
		for(String str:filePathtList) {
			CreateIndexWithTitle.CreateIndexMethod(str);
		}
		
		
		// 2. query
		// 2.1 file path of queries
		String filePath_queries = "(/proj/wangyue/trec/pm/topics_qrel/2017/topics2017.xml";
		ArrayList<String> queries = XMLParser.ReadQueries(filePath_queries);
		
		// 2.2 run queries
		SearchWithTitle.SearchMethod(queries);
	}
}
