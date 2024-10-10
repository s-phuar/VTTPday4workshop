package server;

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.io.OutputStream;

public class ClientMain {

    public static void main(String[] args) throws IOException{

        //set the deafult port to 3000
        int port = 3000;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        //create a connection to the server
        //connect to the port on the server
        //local host - 127.0.0.1

        System.out.println("Connecting to the server");
        //new socket where we want to connect to localhost (localmachine) at the specified port
        Socket sock = new Socket("localhost", port);

        System.out.println("Connected!");


        Console cons = System.console();
        //write a messaage to the server
        String theMessage = cons.readLine("Input: ");


        //get the output stream, open the outstream first
        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        //get the input stream
        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        //write the message out
        bw.write(theMessage);
        bw.newLine();
        bw.flush(); // do NOT close streams or connection cuts
        os.flush();

        //read the result from the server
        String fromServer = br.readLine();
        System.out.printf(">>> SEFVER: %s\n", fromServer);

        os.close();
        is.close();
        sock.close();

    }
    
}
