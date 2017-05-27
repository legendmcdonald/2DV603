package main;


import java.io.*;
import java.net.Socket;

public class HttpsRequester  {

	/*
	 * needs to be implemented by Benjamin, probably just a mock object that will be sent to main and converted by ConvertSearcher class
	 */


	public String getJodelTopics() throws IOException {

		Socket socket = new Socket("127.0.0.1",4955);

		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		outToServer.write(headerGet("topics"));
		outToServer.flush();

		String response="";
		String newResponse = "";
		boolean contentReached = false;

		while (newResponse != null) {

			newResponse=inFromServer.readLine();
			if(newResponse==null){
				break;
			}
			if(newResponse.equals("")){
				contentReached= true;
			}
			if (contentReached) {
				response+=newResponse;
			}
		}
		System.out.println(response);

		socket.close();
		return response;
		
	}

	public String getJodelReplies(String postID) throws IOException {

		Socket socket = new Socket("127.0.0.1",4955);

		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		outToServer.write(headerGet("replies/"+postID));
		outToServer.flush();

		String response="";
		String newResponse = "";
		boolean contentReached = false;

		while (newResponse != null) {

			newResponse=inFromServer.readLine();
			if(newResponse==null){
				break;
			}
			if(newResponse.equals("")){
				contentReached= true;
			}
			if (contentReached) {
				response+=newResponse;
			}
		}
		System.out.println(response);

		socket.close();
		return response;
	}

	//More header fields will need to be added for the real server
	public byte[] headerGet(String purpose){

		final String GET = "GET /";

		String httpGet = GET+purpose+"/ HTTP/1.1\r\n";
		String host = "Host: localhost:4955";

		String finalGet = httpGet+host;

		return finalGet.getBytes();
	}

}
