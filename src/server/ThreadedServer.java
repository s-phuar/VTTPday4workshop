package server;

import java.net.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.io.*;

public class ThreadedServer {
    

    public static void main(String[] args) throws IOException{

        //create a thread pool, fixed size does not grow or shrink
        //we have 2 workers but essentially use 3 cores due to the +1 from the main method
        ExecutorService thrPool = Executors.newFixedThreadPool(2);
        
        
        String threadName = Thread.currentThread().getName();
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

        //create a worker to handle the work in the client handler
        //sock will be passed in ClientHandler.java
        ClientHandler handler = new ClientHandler(sock);

        thrPool.submit(handler);
        server.close();

        }


    }
}
