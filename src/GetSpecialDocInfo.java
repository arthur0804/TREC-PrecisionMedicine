import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*
 * there are some documents staring with AACR or ACSO
 * and some of them do not have title or abstract
 * this method extract these special documents
 */

public class GetSpecialDocInfo {
	public static void getInfo(String url) throws DocumentException, IOException {
		
		File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read( inputFile );
        Element rootElement = document.getRootElement();
        
        String outputurl = "/proj/wangyue/jiamingfolder/dat/SpecialDocInfo.txt";
    
        // store all the first child
        List allElements = rootElement.elements("PubmedArticle");
        // iterate the list
        for(int i = 0; i < allElements.size(); i++) {
        	Element element = (Element) allElements.get(i);
        	Element element_id = element.element("MedlineCitation").element("PMID");
        	        	
        	String id = element_id.getText();
        	String prefix = id.substring(0, 4);
        	// check whether PMID contains special id
        	if(prefix.equals("AACR") || prefix.equals("ASCO")) {
        		
        		String log = "In file: " + url.substring(url.length()-7) + " Document ID: " + id + "\n";
        		Files.write(Paths.get(outputurl), log.getBytes(), StandardOpenOption.APPEND);
        			
        		Element element_title = element.element("MedlineCitation").element("Article").element("ArticleTitle");
        		String title = "Document title: " + element_title.getText() + "\n";
        		Files.write(Paths.get(outputurl), title.getBytes(), StandardOpenOption.APPEND);
        		
        		// abstract may be empty
        		if(element.element("MedlineCitation").element("Article").element("Abstract") == null) {
        			String content = "No document abstract" + "\n";
            		Files.write(Paths.get(outputurl), content.getBytes(), StandardOpenOption.APPEND); 
        		}else {
        			Element element_abstracttext = element.element("MedlineCitation").element("Article").element("Abstract").element("AbstractText");
            		String content = "Document abstract: " + element_abstracttext.getText() + "\n";
            		Files.write(Paths.get(outputurl), content.getBytes(), StandardOpenOption.APPEND); 
				}          	      		
        	}else {
        		// other document ids
        		continue;
        	}    	
        } 
	}
}
