package jodelMock;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by benjamin on 2017-05-24.
 */
@SuppressWarnings("Duplicates")
public class MockMain {
    private static final int MYPORT= 4955;
    protected static final int BUFSIZE= 512;


    public static void main(String[] args)throws IOException {

        ServerSocket welcomeSocket = new ServerSocket(MYPORT);

        System.out.println("Up and running..");

        while(true){

            Socket connectionSocket = welcomeSocket.accept();
            String newConn;
            newConn = connectionSocket.toString();
            System.out.println("New Connection: "+newConn);
            newConnection handler = new newConnection(connectionSocket);
            handler.start();
        }
    }
    private static class newConnection extends Thread{

        private byte[] buff= new byte[BUFSIZE];
        private Socket socket;
        private int connectionTest = 0;
        private ByteBuffer header;
        private ByteBuffer payload;

        public newConnection(Socket socket){
            this.socket= socket;
        }

        @Override
        public void run() {
            try{

                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                DataInputStream inFromClient = new DataInputStream(socket.getInputStream());

                String clientSentence;
                connectionTest = inFromClient.read(buff);
                clientSentence= new String (buff);

                System.out.println("Received port: " +socket.getPort()+" Message: "+ clientSentence.trim());

                String tmpArr[] = clientSentence.split(" ", 3);
                System.out.println("First: "+tmpArr[0]);
                System.out.println("Second: "+tmpArr[1]);

                String newtmpArr[] = tmpArr[1].split("/");

                String st ="";

                if(tmpArr[1].equals("/topics/")){

                    byte[] test = topics();
                    System.out.println(test.toString());

                    outToClient.write(test);

                }else if(tmpArr[1].contains("/replies/")){

                    //System.out.println(newtmpArr[0]);
                    //System.out.println(newtmpArr[1]);
                    System.out.println(newtmpArr[2]);
                    //System.out.println(newtmpArr[3]);

                    outToClient.write(replies(newtmpArr[2]));
                }

                outToClient.flush();
                socket.close();


            }catch (Exception e){e.printStackTrace();}
            try {
                /* The interrupt() is not really needed, but it felt better to include it anyway*/
                socket.close();
                System.out.println("Connection closed: "+socket.toString());
                interrupt();
            } catch (IOException e) {e.printStackTrace();}
        }
        public static byte[] replies(String st){

            ByteArrayOutputStream outResp = new ByteArrayOutputStream();

            final File f = new File(MockMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            Path path = Paths.get(f.getAbsolutePath()+"/jodelMock/"+st+".json");

            try {
                byte[] tmpBytes = Files.readAllBytes(path);
                outResp.write(header(tmpBytes));
                outResp.write(tmpBytes);
                return outResp.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public static byte[] topics(){

            ByteArrayOutputStream outResp = new ByteArrayOutputStream();

            final File f = new File(MockMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            Path path = Paths.get(f.getAbsolutePath()+"/jodelMock/topics.json");

            try {
                byte[] tmpBytes = Files.readAllBytes(path);
                outResp.write(header(tmpBytes));
                outResp.write(tmpBytes);
                return outResp.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public static byte[] header(byte[] tmpFileData) throws IOException{
            final String OK200Starter = "HTTP/1.1 200 OK\r\nServer: jason\r\n";
            final String CONTENTlENGTH = "Content-Length: ";
            final String JSONCONTENT = "Content-Type: application/json\r\nConnection: close\r\n\r\n";
            ByteArrayOutputStream outResp = new ByteArrayOutputStream();

            outResp.write(OK200Starter.getBytes());
            outResp.write((CONTENTlENGTH + tmpFileData.length + "\r\n").getBytes());
            outResp.write(JSONCONTENT.getBytes());

            return outResp.toByteArray();
        }

    }

}
