package server;

import java.net.*;
import java.util.Date;
import java.io.*;

public class ServerMain {
    

    public static void main(String[] args) throws IOException{

        Thread threadName = new Thread();

        int port = 3000;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        //create server specific port named server
        ServerSocket server = new ServerSocket(port);
        
        //this while loop makes it so server will never terminate
        while(true){
        //wait for an incoming connection, accept only LISTENS for connection. auto connects (via accept) when client tries to connect
        //once connected from the client, we get a socket
        System.out.printf("[%s]Waiting for connection, listening on port %d\n", threadName, port);
        Socket sock = server.accept();
            //once accetped, this line creates sock dedicated to the connecting client
            //another client can connect, where each sock object represents a client. We only need ONE accept
            //the first client to conenct is the first to be processed by the server
        System.out.println("Got a new connection");

        //get the input stream called on socket object, open the input stream first while client does the reverse
        //safer this way
        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        //get the output stream 
        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);


        //reade theMessage from the client side
        //***************if the 1st client does NOT type anything, server just sit here and wait. 2nd client will be stuck waiting at line 20

        String fromClient = br.readLine();
        System.out.printf(">>> CLIENT: %s\n", fromClient);

        //process the data
        fromClient = (new Date()).toString() +  " "  + fromClient.toUpperCase();


        //write result back to client
        bw.write(fromClient);
        bw.newLine();
        bw.flush();
        os.flush();
        

        os.close();
        is.close();
        sock.close();
        server.close();
        
        }

    }
}
