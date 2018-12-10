import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document; 
import org.apache.lucene.document.Field; 
import org.apache.lucene.document.FieldType; 
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter; 
import org.apache.lucene.index.IndexWriterConfig; 
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;


public class CreateIndexExtraTopics {
	public static void CreateIndexMethod(String url) throws IOException {
		// prepare the ID, title and abstract
			
		FileInputStream inputStream = new FileInputStream(url);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		// skip the first line (meeting info)
		// be sure to check this before simply skipping
		bufferedReader.readLine();
		
		// list to store the text
		ArrayList<String> aList = new ArrayList<>();
		// read file
		String str = null;
			while((str = bufferedReader.readLine()) != null)
			{
				if(str.trim().length()>0) {
				// ">6" is because there are some lines with a length smaller than 6
				// Most documents are well-structured but some documents have a few words after
				// the content
				
				if(str.length()>6) {
					if(str.substring(0,6).equals("Title:")){
						// remove the "Title:"
						aList.add(str.substring(7));
					}else {
						// this is the content field
						aList.add(str);
					}			
				}else {
					// some other parts following the content part
					aList.add(str);
				}			
			}		
		// end of while	
		}
		bufferedReader.close();
		
		// some files may have several paragraphs of abstract, simply combine to one
		String combined_text = "";
		for(int i=1; i<aList.size(); i++) {
			// start from 1, as 0 is the title
			combined_text = combined_text + " " + aList.get(i);
		}
		
		// ID
		String _id =  url.substring(49,url.length()-4);
		// Title
		String _title = aList.get(0);
		// Abstract
		String _abstract = combined_text;
		
		// new instance
		Article article = new Article(_id, _abstract, _title);
		
		// create analyzer
		Analyzer analyzer = new StandardAnalyzer();
				
		// create an index writer configure
		IndexWriterConfig icw = new IndexWriterConfig(analyzer); 
		// append mode: change CREATE to CREATE_OR_APPEND
		icw.setOpenMode(OpenMode.APPEND);
		// set BM25 similarity
		BM25Similarity similarity = new BM25Similarity(1.2f, 0.75f); 
		icw.setSimilarity(similarity);
				
		// directory and index writer
		Directory dir = null; 
		IndexWriter inWriter = null;
		
		Path indexPath = Paths.get("/proj/wangyue/jiamingfolder/index_BM25");
				
		if ( !Files.isReadable(indexPath)) {
			System.out.println("the path cannot find");
			System.exit(1);
		}
				
		dir = FSDirectory.open (indexPath);
				
		// create an index write
		inWriter = new IndexWriter (dir, icw);
				
		// set ID field
		FieldType idType = new FieldType();
		idType.setIndexOptions(IndexOptions.DOCS); 
		idType.setTokenized(false);
		idType.setStored(true) ;
				
		// set Title filed
		FieldType titleType = new FieldType();
		titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS); 
		titleType.setTokenized(true);
		titleType.setStored(true) ;
				
		// set content field
		FieldType contentType = new FieldType();
		contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
		contentType.setTokenized(true);
		contentType.setStored(true);
				
		// write
		Document doc = new Document();
		doc.add(new Field("id", article.getId(), idType));
		doc.add(new Field("title", article.getTitle(), titleType));
		doc.add(new Field("content", article.getArticleAbstract(), contentType));
		inWriter.addDocument(doc);
	
		inWriter.commit();
		inWriter.close();
		dir.close();
	
	}
}
