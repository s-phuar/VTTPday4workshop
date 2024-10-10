package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;

// work for a thread to perform
// make sure threads dont update global variables

public class ClientHandler implements Runnable{

    private final Socket sock;

    //we need to pass the freshly created socket from the Server
    public ClientHandler(Socket s){
        sock = s;
    }

    //entry point for the thread
    @Override
    public void run(){

        String threadName = Thread.currentThread().getName();

        try{
        //get the input stream, open the input stream first while client does the reverse
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
        //***************consider non-blocking readLine(), if such a thing exists. See NIO
            String fromClient = br.readLine();

        System.out.printf(">>> [%s] CLIENT %s\n", threadName, fromClient);

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

        }catch (IOException e){
            //exception handler
            e.printStackTrace();
        }


    }
}
