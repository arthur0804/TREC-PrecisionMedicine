
public class TestCase {
	/*
	 * TEST CASE
	 * Specific topic search: topic one
	 * check whether there are duplicate document IDs in the 1,000 result
	 * 
	 * 1. get 1,000 document IDs: /proj/wangyue/jiamingfolder/dat/topiconequery1120_did
	 * 
	 * 		TopicOneSearch.SearchMethod();
	 * 
	 * 2. checked result is in /proj/wangyue/jiamingfolder/dat/checktopiconeresultduplicate.txt
	 * 
	 * 		FileInputStream inputStream = new FileInputStream("/proj/wangyue/jiamingfolder/dat/topiconequery1120_did.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			// crate a hashmap to store the document id its occurrences
			HashMap <String, Integer> DocumentOccurence = new HashMap<>();
	
			String str = null;
			int line_num = 0;
			while((str = bufferedReader.readLine()) != null)
			{
				if(DocumentOccurence.get(str) == null) {
					// if the file not exist, add first
					DocumentOccurence.put(str, 1);
					line_num ++;
				}else {
					// else, update its occurrences
					int occurrence = DocumentOccurence.get(str) + 1;
					DocumentOccurence.put(str, occurrence);
					line_num ++;
				}			
			// end of while	
			}
			bufferedReader.close();

			// write the result into text file
			for (Entry<String, Integer> entry : DocumentOccurence.entrySet()) {
    			String key = entry.getKey();
    			int value = entry.getValue();
    			String record = key + "\t" + value + "\n";
    			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/checktopiconeresultduplicate.txt"), record.getBytes(), StandardOpenOption.APPEND);
			} 
			String lines = "total lines are:" + line_num;
			Files.write(Paths.get("/proj/wangyue/jiamingfolder/dat/checktopiconeresultduplicate.txt"), lines.getBytes(), StandardOpenOption.APPEND);
	 * 
	 * 
	 */
	
}
