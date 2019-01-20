import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class GetPublicationTypes {
	public static void getAllPublicationTypes() throws DocumentException, IOException {
		
		//String filePath_collection = "/proj/wangyue/trec/pm/collection/medline_xml";
		ArrayList<String> filePathList = new ArrayList<>();
		
		Set<String> types_set = new HashSet<String>();
		for(int i=1; i<=9; i++) {
			String url = "/proj/wangyue/trec/pm/collection/medline_xml/medline17n000" + i + ".xml";
			filePathList.add(url);
		}
		for(int i=10; i<=99; i++) {
			String url = "/proj/wangyue/trec/pm/collection/medline_xml/medline17n00" + i + ".xml";
			filePathList.add(url);
		}
		
		for(String str : filePathList) {
			// loop through all the files
						File inputFile = new File(str);
				        SAXReader reader = new SAXReader();
				        Document document = reader.read(inputFile);
				        Element rootElement = document.getRootElement();

				        // loop inside the file (all the nodes)
				        List allElements = rootElement.elements("PubmedArticle");
				        for(int i = 0; i < allElements.size(); i++) {
				        	Element element = (Element) allElements.get(i);
				        	Element element_publicationtype = element.element("MedlineCitation").element("Article").element("PublicationTypeList");
				        	List publicationtype_list = element_publicationtype.elements("PublicationType");
				        	for(int j = 0; j < publicationtype_list.size(); j ++) {
				        		Element type_parts = (Element) publicationtype_list.get(j);
				        		types_set.add(type_parts.getText());
				        	}
				        }

				
		}
			
		String log = "";
		for(String string : types_set) {
			log += log + string + "\n";
		}
		
		Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/AllPublicationTypes.txt"), log.getBytes(), StandardOpenOption.APPEND);
	}
}
