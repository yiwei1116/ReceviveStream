import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MyReceiver extends Receiver<String> {

    String host = null;
    int port = -1;
    Decode decode = new Decode();
    Reconstruct reConstruct = new Reconstruct();
    public MyReceiver(String host_ , int port_) {
        super(StorageLevel.MEMORY_AND_DISK_2());
        host = host_;
        port = port_;
    }

    public void onStart() {
        // Start the thread that receives data over a connection
        new Thread()  {
            @Override public void run() {
                receive();
            }
        }.start();
    }

    public void onStop() {
        // There is nothing much to do as the thread calling receive()
        // is designed to stop by itself if isStopped() returns false
    }

    /** Create a socket connection and receive data until receiver is stopped */
    private void receive() {
        Socket socket = null;
        String userInput = null;
        BufferedReader reader = null;
        List<Integer> compressList = new ArrayList<>();
        String decompressString="";
        List<Integer> reConstruct = new ArrayList<>();
        try {
            // connect to the server
            socket = new Socket(host, port);

             reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            // Until stopped or connection broken continue reading
            while (!isStopped() && (userInput = reader.readLine()) != null) {
                System.out.println("Receive data :" + userInput );

        /*        if(userInput.length()>12)
                {
                for(int i=0; i < userInput.length()-10; i+=10){
                compressList.add(Integer.parseInt(userInput.substring(i,i+10),2));
                }
                decompressString = Decode.decompress(compressList);
                reConstruct = Reconstruct.reConstruct(decompressString);
                System.out.println("reConstruct :" + compressList );
                }*/

                store(userInput);
            }
            reader.close();
            socket.close();

            // Restart in an attempt to connect again when server is active again
            restart("Trying to connect again");
        } catch(ConnectException ce) {
            // restart if could not connect to server
            restart("Could not connect", ce);
        } catch(Throwable t) {
            // restart if there is any other error
            restart("Error receiving data", t);
        }
    }
}