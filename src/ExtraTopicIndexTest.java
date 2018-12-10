import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document; 
import org.apache.lucene.document.Field; 
import org.apache.lucene.document.FieldType; 
import org.apache.lucene.document.IntPoint; 
import org.apache.lucene.document.SortedNumericDocValuesField;
import org.apache.lucene.document.StoredField; 
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter; 
import org.apache.lucene.index.IndexWriterConfig; 
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;

public class ExtraTopicIndexTest {

	 /*
	  * This method check the first line of extra topics text files
	  * To see whether there are some "first files" are not meeting info
	  * If all the first lines are meeting info, we can just ignore the first line 
	  * 	
	  * UPDATE: All the first lines are meeting info.
	  * 
	  * 	String filePath_extratopic = "/proj/wangyue/trec/pm/collection/extra_abstracts";

	 		ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_extratopic);
	 

	 		for(String path:filePathtList) {
		 		FileInputStream inputStream = new FileInputStream(path);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				// read the first line
				String first_line = bufferedReader.readLine();
				String log = "File: " + "\t" + path + "\t"+ "First line is:" + first_line + "\n";
				Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/checkfirstline.txt"), log.getBytes(), StandardOpenOption.APPEND);
				if(!first_line.substring(0, 7).equals("Meeting")) {
					String special = "File: " + "\t" + path + "'s first line is special.";
					Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/checkfirstline.txt"), special.getBytes(), StandardOpenOption.APPEND);
				}
				bufferedReader.close();
	 		}
	  * 
	  */
	
	/*
	 *  Same as above, this method check the title format of extra topics
	 * 	//String filePath_extratopic = "/proj/wangyue/trec/pm/collection/extra_abstracts";

		//ArrayList<String> filePathtList = GetFilePath.GetFilePaths(filePath_extratopic);


		//for(String path:filePathtList) {
	 	//	FileInputStream inputStream = new FileInputStream(path);
		//	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		//	// skip the first line
		//	bufferedReader.readLine();
		//	// read title
		//	String titlelog = bufferedReader.readLine() + "\n";
		//	Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/checktitle.txt"), titlelog.getBytes(), StandardOpenOption.APPEND);
		//	bufferedReader.close();
		//}
	 */

}
