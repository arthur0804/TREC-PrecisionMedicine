import java.io.File;
import java.io.IOException; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.Adler32;
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
import org.apache.lucene.store.Directory; 
import org.apache.lucene.store.FSDirectory;
import org.dom4j.DocumentException;

public class CreateIndexWithTitle {

	public static void main(String[] args) throws IOException, DocumentException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String filePath = "/Users/arthur_0804/Desktop/example_data"; 
		new CreateIndexWithoutTitle();
		
		//get all the URLs
		ArrayList<String> IDAndAbstractList = GetFilePath.GetFilePaths(filePath);
		
		for(String str:IDAndAbstractList) {
			// iterate through the list and create index for each file
			CreateIndexMethod(str);
		}
	}
	
	public static void CreateIndexMethod(String url) throws IOException, DocumentException {
		// get a HashMap from the XML parser
		HashMap <Integer, String> IDAndAbstract = XMLParser.ReadIDAndAbstract(url);
		HashMap <Integer, String> IDAndTitle = XMLParser.ReadIDAndTitle(url);
		
		// create an object array
		Article[] articles = new Article[IDAndAbstract.size()];
		
		// create objects in the array by iterating the HashMap
		int index = 0;
		for (Entry<Integer, String> entry : IDAndAbstract.entrySet()) {
		    int key = entry.getKey();
		    String value = entry.getValue();
		    // create an object and write the ID and CONTENT into the object field
		    articles[index] = new Article();
		    articles[index].setId(key);
		    articles[index].setArticleAbstract(value);
		    index++;
		} 
		
		// add the title
		int index_2 = 0;
		for(Entry<Integer, String> entry : IDAndTitle.entrySet()) {
			int key = entry.getKey();
			String value = entry.getValue();
			if(articles[index_2].getId() == key) {
				articles[index_2].setTitle(value);
			}
			index_2 ++;
		}
		
		// create analyzer
		Analyzer analyzer = new StandardAnalyzer();
		
		// create an index writer configure
		IndexWriterConfig icw = new IndexWriterConfig(analyzer); 
		icw.setOpenMode(OpenMode.CREATE_OR_APPEND); // append mode: change CREATE to CREATE_OR_APPEND
		Directory dir = null; 
		IndexWriter inWriter = null;
		
		Path indexPath = Paths.get("/Users/arthur_0804/Desktop/ThesisIndexes/IndexWithTitle");
		Date start= new Date();
		
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
		idType.setStored(true) ;
		
		// set Title filed
		FieldType titleType = new FieldType();
		titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS); 
		titleType.setStored(true) ;
		
		// set content field
		FieldType contentType = new FieldType();
		contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
		contentType.setStored(true);
		
		
		// create and add article fields to the document object in lucene
		for(int j = 0; j<articles.length; j++) {
			Document doc = new Document();
			doc.add(new Field("id", String.valueOf(articles[j].getId()), idType));
			doc.add(new Field("title", articles[j].getTitle(), titleType));
			doc.add(new Field("content", articles[j].getArticleAbstract(), contentType));
			
			// add documents to the index writer
			inWriter.addDocument(doc);
		}
		
		inWriter.commit();
		inWriter.close();
		dir.close();
		
		Date end = new Date();
		
		System.out.println("indexing is done");
		System.out.println("time taken:" + (end.getTime() - start.getTime()) + "milliseconds");
	}
}
