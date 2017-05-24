package main;

public class Main {

	private static Object JodelPostObject;
	
	public static void main(String[] args) {
		
		/*
		 * Main method requests the post object from the jodel server through the HttpsRequester class,
		 * then passes that object to the ConvertSearcher class to convert it and look for matching keywords.
		 * Can add delay here at the end or something so that it doesnt constantly request new posts. Loops forever! 
		 */
		
		while(true){
			
			HttpsRequester requester = new HttpsRequester();
			
			JodelPostObject = requester.getJodelPosts();
			
			@SuppressWarnings("unused")
			ConvertSearcher cs = new ConvertSearcher(JodelPostObject);
			
		}
		
	}

}
