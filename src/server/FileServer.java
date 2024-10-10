package server;

import java.io.*;
import java.net.*;

public class FileServer {

    //copy over myFile from FileCopy client under the same name e.g. myfile via datainputstream
    //1st we read file name, readUTF()
    //2nd we read file size, readLong()
    //3rd we read bytes, read(byte[])
    //4th we close the client

    public static void main(String[] args)throws IOException{
        
        //initialise port to 3000
        String threadName = Thread.currentThread().getName();

        int port = 3000;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        //create server
        ServerSocket server1 = new ServerSocket(port);

        while(true){
            System.out.printf("[%s]Waiting for connection, listening on port %d\n", threadName, port);
            //create socket, after client closes, server still runs so we can recreate the socket
            Socket socky = server1.accept();
            System.out.println("received new connection from client");

            //get the input stream from client
            InputStream is = socky.getInputStream(); // establish connect
            BufferedInputStream bis = new BufferedInputStream(is); //buffers input
            DataInputStream dis = new DataInputStream(bis); //Structured data reading | String line = dis.readUTF();

            //once connected, feed lines to server via outputstream and write using writeUTF
            OutputStream os = socky.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            //read client input
            String fromClientName = dis.readUTF();
            long fromClientSize = dis.readLong();
            //create byte array to hold the file data
            byte[] fileBytes = new byte[(int) fromClientSize];
            dis.readFully(fileBytes);

            //write back to client
            String fromClientInfo = String.format(">>> Client: Filename: %s, Filesize: %d, Filebytes: %d \n", fromClientName, fromClientSize, fileBytes.length);            
            System.out.println(fromClientInfo);

            dos.writeUTF(fromClientInfo);
            //flushing in reverse of wrapped OS
            dos.flush();
            bos.flush();
            os.flush();

            dos.close();
            bos.close();
            os.close();
            dis.close();
            socky.close();
    


    }



    }
}
