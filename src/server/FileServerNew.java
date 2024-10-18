package server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServerNew {


    public static void main(String[] args)throws IOException{

        int port = 3000;

        //open server
        ServerSocket server = new ServerSocket(port);

        while(true){
            //open listening socket
            Socket sock = server.accept();
            System.out.println("received new connection from client");

            //open inputstream to receive file
            InputStream is = sock.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            //read metadata
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            //create file based on filename
            //read file from connection (dis) and write it out via bos
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream (fos);

            int bytesRead = 0;
            int recvCount = 0;
            byte[] buffer = new byte[4 * 1024];

            while(recvCount < fileSize){
                bytesRead = dis.read(buffer);
                bos.write(buffer, 0, bytesRead); // write to bos
                recvCount += bytesRead;
                System.out.printf("Received: %d of %d\n", bytesRead, recvCount);
            }

            //close the input streams
            dis.close();
            bis.close();
            is.close();

            //flush output streams
            bos.flush();
            fos.flush();

            //close the outputstreams + socket
            bos.close();
            fos.close();
            sock.close();

        }
        
    }
}
