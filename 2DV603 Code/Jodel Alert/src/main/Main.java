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
		JSONString = "{\"details\":{\"message\":\"Har fått en hård knöl i bakhuvudet, typ stort som en enkrona.. Gör lite ont när man trycker mot den, vad kan detta vara?! Ett bett av nåt slag? Inte slagit i huvudet heller vad jag vet \uD83D\uDE05\",\"created_at\":\"2017-05-24T17:37:25.589Z\",\"updated_at\":\"2017-05-24T17:46:53.810Z\",\"pin_count\":0,\"color\":\"FFBA00\",\"notifications_enabled\":false,\"got_thanks\":false,\"child_count\":7,\"replier\":0,\"post_id\":\"5925c4d58393aa100007eee8\",\"discovered_by\":0,\"vote_count\":1,\"share_count\":0,\"user_handle\":\"oj\",\"post_own\":\"friend\",\"distance\":4,\"location\":{\"name\":\"Växjö\",\"loc_coordinates\":{\"lat\":0,\"lng\":0},\"loc_accuracy\":0,\"country\":\"\",\"city\":\"\"}},\"replies\":[{\"message\":\"Låter som cancer\",\"created_at\":\"2017-05-24T17:37:38.061Z\",\"updated_at\":\"2017-05-24T17:37:38.061Z\",\"pin_count\":0,\"color\":\"FFBA00\",\"got_thanks\":false,\"thanks_count\":0,\"child_count\":0,\"replier\":1,\"post_id\":\"5925c4e23bec3517000d24ba\",\"discovered_by\":0,\"vote_count\":0,\"user_handle\":\"replier\",\"post_own\":\"friend\",\"distance\":1,\"location\":{\"name\":\"far\",\"loc_coordinates\":{\"lat\":0,\"lng\":0},\"loc_accuracy\":0,\"country\":\"\",\"city\":\"\"}},{\"message\":\"En insekt som lagt ett ägg månne? Du har inte varit på en resa till ett exotiskt land på sistone? \",\"created_at\":\"2017-05-24T17:38:26.127Z\",\"updated_at\":\"2017-05-24T17:38:26.127Z\",\"pin_count\":0,\"color\":\"FFBA00\",\"got_thanks\":false,\"thanks_count\":0,\"child_count\":0,\"replier\":2,\"post_id\":\"5925c5120a62b216000a4cb0\",\"discovered_by\":0,\"vote_count\":-1,\"user_handle\":\"replier\",\"post_own\":\"friend\",\"distance\":5,\"location\":{\"name\":\"far\",\"loc_coordinates\":{\"lat\":0,\"lng\":0},\"loc_accuracy\":0,\"country\":\"\",\"city\":\"\"}},{\"message\":\"Jobbar inte på min fritid tyvär\",\"created_at\":\"2017-05-24T17:38:42.001Z\",\"updated_at\":\"2017-05-24T17:38:42.001Z\",\"pin_count\":0,\"color\":\"FFBA00\",\"got_thanks\":false,\"thanks_count\":0,\"child_count\":0,\"replier\":3,\"post_id\":\"5925c5213bec3517000d24cb\",\"discovered_by\":0,\"vote_count\":0,\"user_handle\":\"replier\",\"post_own\":\"friend\",\"distance\":1,\"location\":{\"name\":\"far\",\"loc_coordinates\":{\"lat\":0,\"lng\":0},\"loc_accuracy\":0,\"country\":\"\",\"city\":\"\"}},{\"message\":\"Min kompis Jonas säger att man får kancer av mcdonalds mat\",\"created_at\":\"2017-05-24T17:42:47.039Z\",\"updated_at\":\"2017-05-24T17:42:47.039Z\",\"pin_count\":0,\"color\":\"FFBA00\",\"got_thanks\":false,\"thanks_count\":0,\"child_count\":0,\"replier\":4,\"post_id\":\"5925c61740a9821700ece64c\",\"discovered_by\":0,\"vote_count\":2,\"user_handle\":\"replier\",\"post_own\":\"friend\",\"distance\":5,\"location\":{\"name\":\"far\",\"loc_coordinates\":{\"lat\":0,\"lng\":0},\"loc_accuracy\":0,\"country\":\"\",\"city\":\"\"}},{\"message\":\"En gammal eller en ny enkrona stor? \",\"created_at\":\"2017-05-24T17:43:26.702Z\",\"updated_at\":\"2017-05-24T17:43:26.702Z\",\"pin_count\":0,\"color\":\"FFBA00\",\"got_thanks\":false,\"thanks_count\":0,\"child_count\":0,\"replier\":5,\"post_id\":\"5925c63ed3631710007994f4\",\"discovered_by\":0,\"vote_count\":4,\"user_handle\":\"replier\",\"post_own\":\"friend\",\"distance\":5,\"location\":{\"name\":\"far\",\"loc_coordinates\":{\"lat\":0,\"lng\":0},\"loc_accuracy\":0,\"country\":\"\",\"city\":\"\"}},{\"message\":\"En finne\",\"created_at\":\"2017-05-24T17:44:10.777Z\",\"updated_at\":\"2017-05-24T17:44:10.777Z\",\"pin_count\":0,\"color\":\"FFBA00\",\"got_thanks\":false,\"thanks_count\":0,\"child_count\":0,\"replier\":6,\"post_id\":\"5925c66ae5926c110065c4c2\",\"discovered_by\":0,\"vote_count\":2,\"user_handle\":\"replier\",\"post_own\":\"friend\",\"distance\":1,\"location\":{\"name\":\"far\",\"loc_coordinates\":{\"lat\":0,\"lng\":0},\"loc_accuracy\":0,\"country\":\"\",\"city\":\"\"},\"from_home\":true},{\"message\":\"Som #6 säger\\n\\nEn böld eller finner\",\"created_at\":\"2017-05-24T17:46:53.800Z\",\"updated_at\":\"2017-05-24T17:46:53.800Z\",\"pin_count\":0,\"color\":\"FFBA00\",\"got_thanks\":false,\"thanks_count\":0,\"child_count\":0,\"replier\":7,\"post_id\":\"5925c70d1efaca110000e41f\",\"discovered_by\":0,\"vote_count\":0,\"user_handle\":\"replier\",\"post_own\":\"friend\",\"distance\":2,\"location\":{\"name\":\"far\",\"loc_coordinates\":{\"lat\":0,\"lng\":0},\"loc_accuracy\":0,\"country\":\"\",\"city\":\"\"}}],\"next\":null,\"remaining\":0,\"shareable\":true,\"readonly\":false}";
		
		//while(true){
			
			HttpsRequester requester = new HttpsRequester();
			
			//JodelPostObject = requester.getJodelPosts();
			
			@SuppressWarnings("unused")
			ConvertSearcher cs = new ConvertSearcher(requester.getJodelReplies(), false);

		//}
		
	}

}
