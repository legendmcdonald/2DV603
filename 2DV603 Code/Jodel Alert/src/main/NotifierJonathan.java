package main;

import java.util.ArrayList;

public class NotifierJonathan {

	private ArrayList<String> emails;
	
	private JodelPost post;
	
	public NotifierJonathan(JodelPost post){
		
		this.post = post;
		
		getEmails();
		sendEmails();
		
	}
	
	private void getEmails(){
		
		DBhandlerJonathan db = new DBhandlerJonathan();
		
		emails = new ArrayList<String>(db.getEmails());
		
	}
	
	// TODO : sendEmails needs to be implemented, just use JodelPost object post to get the info needed.
	
	private void sendEmails(){
		
		
		
	}
	
}
