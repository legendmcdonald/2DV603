package main;

import java.util.ArrayList;

public class ConvertSearcher {
	
	private Object JodelPostObject;
	
	private JodelPost post = new JodelPost();
	
	private Boolean matchFound = false;
	private String matchingKeyword;
	
	/*
	 * Convert searcher first convert json object to strings and stores those values in the JodelPost object.
	 * Then the DBhandler is used in the search method to get the list of keywords. If match is found,
	 * matchFound boolean is set to true and Notifier class is called to send emails (JodelPost object passed to
	 * Notifier class)
	 */
	
	public ConvertSearcher(Object JodelPostObject){
		
		this.JodelPostObject = JodelPostObject;
		convert();
		search();
		
		if(matchFound){
			
			post.setMatchingKeyword(matchingKeyword);
			
			@SuppressWarnings("unused")
			Notifier notifier = new Notifier(post);
			
		}
		
	}
	
	// TODO : Maybe Benjamin can implement the convert method since Im not sure how the json object will look.
	
	private void convert(){
			
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
