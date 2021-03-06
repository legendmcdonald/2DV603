package main;

import java.io.IOException;

public class Main {

	private static Object JodelPostObject;
	private static String JSONString;

	public static void main(String[] args) throws IOException {
		
		/*
		 * Main method requests the post object from the jodel server through the HttpsRequester class,
		 * then passes that object to the ConvertSearcher class to convert it and look for matching keywords.
		 * Can add delay here at the end or something so that it doesnt constantly request new posts. Loops forever! 
		 */
		HttpsRequester requester = new HttpsRequester();

		ConvertSearcher cs = new ConvertSearcher();

		while(true) {

			//Get the topics first
			cs.convertTopics(requester.getJodelTopics());

			//For each topic, get all the replies and search for keywords
			for (JodelPost jodel : cs.getTopics().getRecent()) {
				cs.convertReplies(requester.getJodelReplies(jodel.getPostID()));
			}

			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
