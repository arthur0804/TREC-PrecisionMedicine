import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class NER {
	public static String GeneNER (String id) throws IOException{
		URL url_Submit;
		url_Submit = new URL("https://www.ncbi.nlm.nih.gov/CBBresearch/Lu/Demo/RESTful/tmTool.cgi/Gene/" + id + "/JSON/");
		HttpURLConnection conn_Submit = (HttpURLConnection) url_Submit.openConnection();
		conn_Submit.setDoOutput(true);
		BufferedReader br_Submit = new BufferedReader(new InputStreamReader(conn_Submit.getInputStream()));
		String output = "";
		String line="";
		while((line = br_Submit.readLine()) != null)
		{
			output = output + line;
		}	
		conn_Submit.disconnect();
		
		if(output.length() > 0) {
			output = output.substring(1,output.length()-1);
		}else {
			output = "";
		}
		
		return output;
	}
	
	public static String DiseaseNER (String id) throws IOException{
		URL url_Submit;
		url_Submit = new URL("https://www.ncbi.nlm.nih.gov/CBBresearch/Lu/Demo/RESTful/tmTool.cgi/Disease/" + id + "/JSON/");
		HttpURLConnection conn_Submit = (HttpURLConnection) url_Submit.openConnection();
		conn_Submit.setDoOutput(true);
		BufferedReader br_Submit = new BufferedReader(new InputStreamReader(conn_Submit.getInputStream()));
		String output = "";
		String line="";
		while((line = br_Submit.readLine()) != null)
		{
			output = output + line;
		}	
		conn_Submit.disconnect();
		
		if(output.length() > 0) {
			output = output.substring(1,output.length()-1);
		}else {
			output = "";
		}
		
		return output;
	}
	
	public static Set<String> JSONParser (String APIoutput){
		Set<String> NamedEntity = new HashSet<>();
		if(APIoutput.length() != 0) {
			JSONObject obj = new JSONObject(APIoutput);
			JSONArray arr = obj.getJSONArray("denotations");
			for (int i = 0; i < arr.length(); i++)
			{
			    String disease_id = arr.getJSONObject(i).getString("obj");
			    NamedEntity.add(disease_id);
			}	
		}
		return NamedEntity;
	}
	
	public static void PrintMentions() throws IOException {
		String inputpath = "/Users/arthur_0804/Desktop/RelDocs/"; 
		String outputpath = "/Users/arthur_0804/Desktop/Output/";
		
		for(int i = 1; i <= 9; i++) {
			String input = inputpath + i;
			FileInputStream inputStream = new FileInputStream(input);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String str;
			Set<String> geneSet = new HashSet<>();
			Set<String> diseaseSet = new HashSet<>();
			while((str = bufferedReader.readLine()) != null) {
				String docid = str.split("   ")[1];
				geneSet.addAll(JSONParser(GeneNER(docid)));
				diseaseSet.addAll(JSONParser(DiseaseNER(docid)));
			}
			bufferedReader.close();
			String output = outputpath + i;
			for(String s : diseaseSet) {
				Files.write(Paths.get(output), (s + "\n").getBytes(), StandardOpenOption.APPEND);
			}
			for(String s : geneSet) {
				Files.write(Paths.get(output), (s + "\n").getBytes(), StandardOpenOption.APPEND);
			}
			
			System.out.println("Topic " + i + " done");
		}
		
		for(int i = 10; i <= 30; i++) {
			String input = inputpath + i;
			FileInputStream inputStream = new FileInputStream(input);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String str;
			Set<String> geneSet = new HashSet<>();
			Set<String> diseaseSet = new HashSet<>();
			while((str = bufferedReader.readLine()) != null) {
				String docid = str.split("  ")[1];
				geneSet.addAll(JSONParser(GeneNER(docid)));
				diseaseSet.addAll(JSONParser(DiseaseNER(docid)));
			}
			bufferedReader.close();
			String output = outputpath + i;
			for(String s : diseaseSet) {
				Files.write(Paths.get(output), (s + "\n").getBytes(), StandardOpenOption.APPEND);
			}
			for(String s : geneSet) {
				Files.write(Paths.get(output), (s + "\n").getBytes(), StandardOpenOption.APPEND);
			}
			
			System.out.println("Topic " + i + " done");
		}
		
	}
}
