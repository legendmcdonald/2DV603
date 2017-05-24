package main;

// Jodel post object that contains values specified in class diagram, also matching keyword for reference

public class JodelPost {

	private int postID;
	private String message;
	private String location;
	private String matchingKeyword;
	
	public int getPostID(){
		return postID;
	}
	
	public void setPostID(int postID){
		this.postID = postID;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocation() {
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
	
}
