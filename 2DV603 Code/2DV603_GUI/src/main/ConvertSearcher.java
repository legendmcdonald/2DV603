package main;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertSearcher {

	private RepliesPosts replies;
	private TopicsPosts topics;

	private Boolean matchFound = false;
	private String matchingKeyword;

	private List<String> alreadySearchedID = new ArrayList<>();
	
	/*
	 * Convert searcher first convert json object to strings and stores those values in the JodelPost object.
	 * Then the DBhandlerJonathan is used in the search method to get the list of keywords. If match is found,
	 * matchFound boolean is set to true and NotifierJonathan class is called to send emails (JodelPost object passed to
	 * NotifierJonathan class)
	 */

	public ConvertSearcher(){}

	//Topics or "recent" have different structure from "replies"
	public void convertTopics(String JSONString){

		ObjectMapper mapper = new ObjectMapper();

		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			topics = mapper.readValue(JSONString.toLowerCase(), TopicsPosts.class);

			String prettyTopics = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(topics);
			System.out.println(prettyTopics);
		}catch (JsonGenerationException e) {
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void convertReplies(String JSONString){

		ObjectMapper mapper = new ObjectMapper();

		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			replies = mapper.readValue(JSONString.toLowerCase(), RepliesPosts.class);

			String prettyTopics = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(replies);
			System.out.println(prettyTopics);
		}catch (JsonGenerationException e) {
			e.printStackTrace();
		}catch (JsonMappingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

		//Jodel "replies" consist of 1 "header" jodel and then a list of replies
		if(search(replies.getDetails())){
			Notifier notifier = new Notifier(replies.getDetails().getMessage());
		}
		for (JodelPost jodel : replies.getReplies()) {
			if(search(jodel)){
				Notifier notifier = new Notifier(jodel.getMessage());
			}
		}

	}

	private boolean search(JodelPost jodel){

		if(!alreadySearchedID.contains(jodel.getPostID())){
			DBhandler db = new DBhandler();

			ArrayList<String> keywords = db.getKeywords();

			// array of all words in the post is gotten
			String[] postWords = jodel.getMessage().split(" ");

			// words are compared with keywords, if match found then boolean is true and matchingKeyword is set in JodelPost Object
			for(int i = 0; i < postWords.length; i++){

				for(int j = 0; j < keywords.size(); j++){

					if(postWords[i].equals(keywords.get(j))){

						matchingKeyword = keywords.get(j);
						System.out.println("===============================================================");
						System.out.println("--------------------------keyword found------------------------");
						System.out.println("--------------------------"+matchingKeyword+"------------------------");
						System.out.println("===============================================================");
						//matchFound = true;

						//All searched unique jodel posts are added in a "searched" list. TODO: implement a fixed size list, maybe 500
						alreadySearchedID.add(jodel.getPostID());
						return true;
					}

				}

			}


		}
		//All searched unique jodel posts are added in a "searched" list. TODO: implement a fixed size list, maybe 500
		alreadySearchedID.add(jodel.getPostID());
		return false;
	}

	public RepliesPosts getReplies() {return replies;}
	public TopicsPosts getTopics() {return topics;}
}
