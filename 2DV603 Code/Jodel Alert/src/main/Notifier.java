package main;

import java.util.ArrayList;

public class Notifier {

	private ArrayList<String> emails;
	
	private JodelPost post;
	
	public Notifier(JodelPost post){
		
		this.post = post;
		
		getEmails();
		sendEmails();
		
	}
	
	private void getEmails(){
		
		DBhandler db = new DBhandler();
		
		emails = new ArrayList<String>(db.getEmails());
		
	}
	
	// TODO : sendEmails needs to be implemented, just use JodelPost object post to get the info needed.
	
	private void sendEmails(){
		
		
		
	}
	
}
