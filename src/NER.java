import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
		output = output.substring(1,output.length()-1);
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
		output = output.substring(1,output.length()-1);
		return output;
	}
	
	public static ArrayList<String> JSONParser (String APIoutput){
		ArrayList<String> NamedEntity = new ArrayList<>();
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
}
