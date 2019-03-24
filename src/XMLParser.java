import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLParser {

    // return a HashMap of <ID, Abstract>
    public static HashMap < String, String > ReadIDAndAbstract(String url) throws DocumentException {
        // create a HashMap to store the <id, string> value
        HashMap < String, String > ArticleidAndAbstract = new HashMap();

        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("PubmedArticle");

        // iterate the list
        for (int i = 0; i < allElements.size(); i++) {
            Element element = (Element) allElements.get(i);
            Element element_id = element.element("MedlineCitation").element("PMID");

            /* check whether the abstract text node exists
             * if does not exist, use an empty string instead
             */
            if (element.element("MedlineCitation").element("Article").element("Abstract") == null) {
                // add into the HashMap
                String empty_abstracttext = "";
                ArticleidAndAbstract.put(element_id.getText(), empty_abstracttext);
            }else {
            	// some articles have several "AbstractText" tags
                Element element_abstract = element.element("MedlineCitation").element("Article").element("Abstract");
                List abstract_list = element_abstract.elements("AbstractText");
                String abstract_text = "";
                for(int j = 0; j < abstract_list.size(); j ++) {
            		Element abstract_parts = (Element) abstract_list.get(j);
            		abstract_text += abstract_parts.getText() + " ";
            	}
                abstract_text = abstract_text.trim();
                ArticleidAndAbstract.put(element_id.getText(), abstract_text);
            }
        }
        return ArticleidAndAbstract;
    }

    // return a HashMap of <ID, Title>
    public static HashMap < String, String > ReadIDAndTitle(String url) throws DocumentException {
        // create a HashMap to store the <id, string> value
        HashMap < String, String > ArticleidAndTitle = new HashMap();

        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("PubmedArticle");

        // iterate the list
        for (int i = 0; i < allElements.size(); i++) {
            Element element = (Element) allElements.get(i);
            Element element_title = element.element("MedlineCitation").element("Article").element("ArticleTitle");
            Element element_id = element.element("MedlineCitation").element("PMID");
            // add into the HashMap
            ArticleidAndTitle.put(element_id.getText(), element_title.getText());
        }
        return ArticleidAndTitle;
    }

    // return a HashMap of <ID, Type>
    public static HashMap<String , String> ReadIDAndType(String url) throws DocumentException{
    	// create a HashMap to store the <id, string> value
        HashMap < String, String > ArticleidAndType = new HashMap();

        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("PubmedArticle");
        for (int i = 0; i < allElements.size(); i++) {
        	Element element = (Element) allElements.get(i);
            Element element_id = element.element("MedlineCitation").element("PMID");
        	
            if (element.element("MedlineCitation").element("Article").element("PublicationTypeList") == null) {
                // add into the HashMap
                String empty_type = "";
                ArticleidAndType.put(element_id.getText(), empty_type);
            }else {
                Element element_type = element.element("MedlineCitation").element("Article").element("PublicationTypeList");
                List type_list = element_type.elements("PublicationType");
                String type_text = "";
                for(int j = 0; j < type_list.size(); j ++) {
            		Element type_parts = (Element) type_list.get(j);
            		type_text += type_parts.getText() + ",";
            	}
                type_text = type_text.substring(0, type_text.length()-1);
                ArticleidAndType.put(element_id.getText(), type_text);
            }
        	
        }   
        return ArticleidAndType;
    }
    
    // return a HashMap of <ID, Heading>
    public static HashMap<String , String> ReadIDAndHeading(String url) throws DocumentException{
    	// create a HashMap to store the <id, string> value
    	HashMap < String, String > ArticleidAndHeading = new HashMap();

        File inputFile = new File("url");
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("PubmedArticle");
        for (int i = 0; i < allElements.size(); i++) {
        	Element element = (Element) allElements.get(i);
            Element element_id = element.element("MedlineCitation").element("PMID");
        	
            if (element.element("MedlineCitation").element("MeshHeadingList") == null) {
                // add into the HashMap
                String empty_type = "";
                ArticleidAndHeading.put(element_id.getText(), empty_type);
            }else {
                Element element_heading = element.element("MedlineCitation").element("MeshHeadingList");
                List heading_list = element_heading.elements("MeshHeading");
                
                String heading_text = "";
                String descriptors = "";
                String qualifiers = "";
                
                for(int j = 0; j < heading_list.size(); j ++) {
            		Element heading_parts = (Element) heading_list.get(j);
            		if(heading_parts.element("DescriptorName") != null) {
            			descriptors += heading_parts.element("DescriptorName").getText() + ",";
            		}
            		if(heading_parts.element("QualifierName") != null) {
            			qualifiers += heading_parts.element("QualifierName").getText() + ",";
            		}
 
            	}
                
                descriptors = descriptors.substring(0, descriptors.length()-1);
                qualifiers = qualifiers.substring(0, qualifiers.length()-1);
                heading_text = descriptors + "-----" + qualifiers;
                
                ArticleidAndHeading.put(element_id.getText(), heading_text);
            }
        }   
        return ArticleidAndHeading;
    }
    
    // return a String List of genes
    public static ArrayList < String > ReadGenes(String url) throws DocumentException {
        // create an Arraylist to store queries
        ArrayList < String > Genes = new ArrayList < > ();

        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("topic");

        // iterate the list
        for (int i = 0; i < allElements.size(); i++) {
            Element element = (Element) allElements.get(i);
            Element gene = element.element("gene");
            // add into the List
            String Gene = gene.getText();
            Genes.add(Gene);
        }
        return Genes;
    }

    // return a String List of diseases
    public static ArrayList < String > ReadDiseases(String url) throws DocumentException {
        // create an Arraylist to store queries
        ArrayList < String > Diseases = new ArrayList < > ();

        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("topic");

        // iterate the list
        for (int i = 0; i < allElements.size(); i++) {
            Element element = (Element) allElements.get(i);
            Element disease = element.element("disease");
            // add into the List
            String Disease = disease.getText();
            Diseases.add(Disease);
        }
        return Diseases;
    }

    // return a String List of other info
    public static ArrayList < String > ReadOtherInfo(String url) throws DocumentException {
        // create an Arraylist to store queries
        ArrayList < String > OtherInfo = new ArrayList < > ();

        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("topic");

        // iterate the list
        for (int i = 0; i < allElements.size(); i++) {
            Element element = (Element) allElements.get(i);
            Element other = element.element("other");
            // add into the List
            String Other = other.getText();
            if (Other.equals("None")) {
                OtherInfo.add(" ");
            } else {
                OtherInfo.add(Other);
            }
        }
        return OtherInfo;
    }

    // get all the document id
    public static void PrintDocumentID(String url) throws DocumentException, IOException {
        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("PubmedArticle");

        // iterate the list
        for (int i = 0; i < allElements.size(); i++) {
            Element element = (Element) allElements.get(i);
            Element element_id = element.element("MedlineCitation").element("PMID");
            String log = element_id.getText() + "\n";
            Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/AllDocumentIDCollection.txt"), log.getBytes(), StandardOpenOption.APPEND);
        }
    }
    
    // get expanded disease terms
    public static ArrayList<String> ReadExpandedDiseases (String url) throws DocumentException, IOException{
    	// create an Arraylist to store queries
        ArrayList < String > Diseases = new ArrayList < > ();

        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("topic");

        // iterate the list
        for (int i = 0; i < allElements.size(); i++) {
            Element element = (Element) allElements.get(i);
            Element disease = element.element("DiseaseSynonyms");
            // add into the List
            String Disease = disease.getText();
            Diseases.add(Disease);
        }
        return Diseases;
    }
    
    
    // get expanded disease terms
    public static ArrayList<String> ReadExpandedGenes (String url) throws DocumentException, IOException{
    	 // create an Arraylist to store queries
        ArrayList < String > Genes = new ArrayList < > ();

        File inputFile = new File(url);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);
        Element rootElement = document.getRootElement();

        // store all the first child
        List allElements = rootElement.elements("topic");

        // iterate the list
        for (int i = 0; i < allElements.size(); i++) {
            Element element = (Element) allElements.get(i);
            Element gene = element.element("GeneSynonyms");
            // add into the List
            String Gene = gene.getText();
            Genes.add(Gene);
        }
        return Genes;
    }
}