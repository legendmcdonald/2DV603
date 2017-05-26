package main;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class ConvertSearcher {

	private Object JodelPostObject;

	private JodelPost post = new JodelPost();
	private RepliesPosts replies;
	private TopicsPosts topics;

	private Boolean matchFound = false;
	private String matchingKeyword;
	
	/*
	 * Convert searcher first convert json object to strings and stores those values in the JodelPost object.
	 * Then the DBhandler is used in the search method to get the list of keywords. If match is found,
	 * matchFound boolean is set to true and Notifier class is called to send emails (JodelPost object passed to
	 * Notifier class)
	 */

	public ConvertSearcher(String JSONstring, boolean topic){

		this.JodelPostObject = JodelPostObject;
		convert(JSONstring, topic);
		//search();

		if(matchFound){

			//post.setMatchingKeyword(matchingKeyword);

			@SuppressWarnings("unused")
			Notifier notifier = new Notifier(post);

		}

	}

	// TODO : Maybe Benjamin can implement the convert method since Im not sure how the json object will look.

	private void convert(String JSONString, boolean topic){

		ObjectMapper mapper = new ObjectMapper();

		if(topic){

			try {
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				topics = mapper.readValue(JSONString, TopicsPosts.class);

				String prettyTopics = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(topics);
				System.out.println(prettyTopics);
			}catch (JsonGenerationException e) {
				e.printStackTrace();
			}catch (JsonMappingException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}


		}else if(!topic){
			try {
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				replies = mapper.readValue(JSONString, RepliesPosts.class);

				String prettyTopics = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(replies);
				System.out.println(prettyTopics);
			}catch (JsonGenerationException e) {
				e.printStackTrace();
			}catch (JsonMappingException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}


		}

			
		/*post.setPostID();
		post.setMessage();
		post.setLocation();*/

	}

	private void search(){

		DBhandler db = new DBhandler();

		ArrayList<String> keywords = new ArrayList<String>(db.getKeywords());

		// array of all words in the post is gotten
		String[] postWords = post.getMessage().split(" ");

		// words are compared with keywords, if match found then boolean is true and matchingKeyword is set in JodelPost Object
		for(int i = 0; i < postWords.length; i++){

			for(int j = 0; j < keywords.size(); j++){

				if(postWords[i].equals(keywords.get(j))){

					matchFound = true;
					matchingKeyword = keywords.get(j);

				}

			}

		}

	}

}
