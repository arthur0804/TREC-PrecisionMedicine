import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XMLParser {
	
	// return a HashMap of <ID, Abstract>
	public static HashMap<Integer, String> ReadIDAndAbstract(String url) throws DocumentException {
		// create a HashMap to store the <id, string> value
		HashMap <Integer, String> ArticleidAndAbstract = new HashMap();
		
		File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read( inputFile );
        Element rootElement = document.getRootElement();
     	         
        // store all the first child
        List allElements = rootElement.elements("PubmedArticle");	     
        
        // iterate the list
        for(int i = 0; i < allElements.size(); i++) {
        	Element element = (Element) allElements.get(i);
        	Element element_id = element.element("MedlineCitation").element("PMID");
        	
        	/* check whether the abstract text node exists
        	 * if does not exist, use an empty string instead
        	 */ 	
        	if(element.element("MedlineCitation").element("Article").element("Abstract") == null) {
        		// add into the HashMap
        		String empty_abstracttext = "";
           	 	ArticleidAndAbstract.put(Integer.parseInt(element_id.getText()), empty_abstracttext);
        	}else {
        		Element element_abstracttext = element.element("MedlineCitation").element("Article").element("Abstract").element("AbstractText");
        		ArticleidAndAbstract.put(Integer.parseInt(element_id.getText()), element_abstracttext.getText());
        	}       	 		        	 
        }	         
        return ArticleidAndAbstract;
	}
	
	// return a HashMap of <ID, Title>
	public static HashMap<Integer, String> ReadIDAndTitle(String url) throws DocumentException {
		 // create a HashMap to store the <id, string> value
		 HashMap <Integer, String> ArticleidAndTitle = new HashMap();
		
		 File inputFile = new File(url);
         SAXReader reader = new SAXReader();
         Document document = reader.read( inputFile );
         Element rootElement = document.getRootElement();
    	         
         // store all the first child
         List allElements = rootElement.elements("PubmedArticle");	     
       
         // iterate the list
         for(int i = 0; i < allElements.size(); i++) {
        	 Element element = (Element) allElements.get(i);
        	 Element element_title = element.element("MedlineCitation").element("Article").element("ArticleTitle");
        	 Element element_id = element.element("MedlineCitation").element("PMID");
        	 // add into the HashMap
        	 ArticleidAndTitle.put(Integer.parseInt(element_id.getText()), element_title.getText());	        	 
         }	         
         return ArticleidAndTitle;
	}
	
	// return a String List of genes
	public static ArrayList<String> ReadGenes (String url) throws DocumentException{
		// create an Arraylist to store queries
		ArrayList<String> Genes = new ArrayList<>();
		
		File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read( inputFile );
        Element rootElement = document.getRootElement();
        
        // store all the first child
        List allElements = rootElement.elements("topic");
        
        // iterate the list
        for(int i = 0; i < allElements.size(); i++) {
         	 Element element = (Element) allElements.get(i);
         	 Element gene = element.element("gene");
         	 // add into the List
         	 String Gene = gene.getText();
         	 Genes.add(Gene);
          }	         
		return Genes;
	}
	
	// return a String List of diseases
	public static ArrayList<String> ReadDiseases (String url) throws DocumentException{
			// create an Arraylist to store queries
			ArrayList<String> Diseases = new ArrayList<>();
			
			File inputFile = new File(url);
	        SAXReader reader = new SAXReader();
	        Document document = reader.read( inputFile );
	        Element rootElement = document.getRootElement();
	        
	        // store all the first child
	        List allElements = rootElement.elements("topic");
	        
	        // iterate the list
	        for(int i = 0; i < allElements.size(); i++) {
	         	 Element element = (Element) allElements.get(i);
	         	 Element disease = element.element("disease");
	         	 // add into the List
	         	 String Disease = disease.getText();
	         	 Diseases.add(Disease);
	          }	         
			return Diseases;
		}
	
	// return a String List of other info
		public static ArrayList<String> ReadOtherInfo (String url) throws DocumentException{
				// create an Arraylist to store queries
				ArrayList<String> OtherInfo = new ArrayList<>();
				
				File inputFile = new File(url);
		        SAXReader reader = new SAXReader();
		        Document document = reader.read( inputFile );
		        Element rootElement = document.getRootElement();
		        
		        // store all the first child
		        List allElements = rootElement.elements("topic");
		        
		        // iterate the list
		        for(int i = 0; i < allElements.size(); i++) {
		         	 Element element = (Element) allElements.get(i);
		         	 Element other = element.element("other");
		         	 // add into the List
		         	 String Other = other.getText();
		         	 if(Other.equals("None")) {
		         		OtherInfo.add(" ");
		         	 }else {
		         		OtherInfo.add(Other);
		         	 }
		          }	         
				return OtherInfo;
			}
		
	// get all the document id
	public static void GetDocumentID (String url) throws DocumentException, IOException{
		File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read( inputFile );
        Element rootElement = document.getRootElement();
     	         
        // store all the first child
        List allElements = rootElement.elements("PubmedArticle");	     
        
        // iterate the list
        for(int i = 0; i < allElements.size(); i++) {
        	Element element = (Element) allElements.get(i);
        	Element element_id = element.element("MedlineCitation").element("PMID");
        	String log = element_id.getText() + "\n";
        	Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/documentidcollection.txt"), log.getBytes(), StandardOpenOption.APPEND);
        }
	}
}
