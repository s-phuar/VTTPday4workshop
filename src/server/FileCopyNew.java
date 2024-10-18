package server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCopyNew {


    public static void main(String[] args)throws IOException{

        //connect to server
        int port = 3000;
        System.out.println("Connecting to server");
        //open seeking socket
        Socket sock = new Socket("localhost", port);
        System.out.println("Successfullly connected to server");

        //writing over connection
        File file = new File("src/server/catinthehat.txt"); //will get created in working directory (day4workshop)
        //get file information
        String fileName = file.getName();
        long fileSize = file.length();

        System.out.printf("Transferring file: %s of size %d\n", fileName, fileSize);

        //opening stream to transmit data over
        OutputStream os = sock.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos); // we will write structured bytes into this

        //opening stream to hold byte data to transfer over
        //read file via bis and send it over connection(dos)
        FileInputStream fis = new FileInputStream(file); // good for raw bytes
        BufferedInputStream bis = new BufferedInputStream(fis);

        //transmitting file metadata
        dos.writeUTF(fileName);
        dos.writeLong(fileSize);

        int readBytes = 0;
        int sendBytes = 0;
        byte[] buffer = new byte[4 * 1024];

        //readBytes holds number of bytes read from bis and stored in buffer
        //buffer holds byte data in byte array, read from bis
        //dos.write writes from buffer to dos (DataOutputStream)
        while((readBytes = bis.read(buffer)) != -1){
            dos.write(buffer, 0, readBytes);
            sendBytes += readBytes; //accumulate sendBytes
            System.out.printf("Sending %d byte data to DataOutputStream, out of %d\n", sendBytes, fileSize);
        }

        //close input streams
        bis.close();
        fis.close();

        //flushing output streams
        dos.flush();
        bos.flush();
        os.flush();

        //closing output streams and socket
        dos.close();
        bos.close();
        os.close();
        sock.close();

    }
    
}
