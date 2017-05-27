package main;

// Jodel post object that contains values specified in class diagram, also matching keyword for reference

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JodelPost {

	@JsonProperty("post_id")
	private String postID;

	@JsonProperty("message")
	private String message;

	//private String location;
	//private String matchingKeyword;

	private boolean alreadySearched= false;

	public String getPostID(){
		return postID;
	}
	
	public void setPostID(String postID){
		this.postID = postID;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getAlreadySearched() {
		return alreadySearched;
	}

	public void setAlreadySearched(boolean alreadySearched) {
		this.alreadySearched = alreadySearched;
	}

	/*public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMatchingKeyword(){
		return matchingKeyword;
	}
	
	public void setMatchingKeyword(String matchingKeyword) {
		this.matchingKeyword = matchingKeyword;
	}
	*/
}
