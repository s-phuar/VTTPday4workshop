package server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCopy {

    //copy and paste myFile from FileCopy client to FileServer server under the same name e.g. myfile via dataoutputstream
    //1st we send over file name, writeUTF()
    //2nd we send over file size, writeLong()
    //3rd we send over bytes, write(byte[])
    //4th we close the client
    public static void main(String[] args)throws IOException{

        //set default port to 3000
        int port = 3000;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        System.out.println("connecting to server");
        //connecting to localhost, common parameter input include remote IP or website
        Socket socky = new Socket("localhost", port);
        System.out.println("Successfullly connected to server");


        String fileDirect = "src/server/catinthehat.txt";
            Path path = Paths.get(fileDirect);
            File file = new File(fileDirect);
        String fileName = fileDirect.replace("src/server/", "");
        long fileSize = file.length();
        byte[] fileBytes = Files.readAllBytes(path);

        //once connected, feed lines to server via outputstream and write using writeUTF
        OutputStream os = socky.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);

        //get the input stream, dont need to flush input stream
        InputStream is = socky.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis); //String line = dis.readUTF();


        //write to server
        dos.writeUTF(fileName);
        dos.writeLong(fileSize);
        dos.write(fileBytes);
        //flush in reverse order of os being wrapped
        dos.flush();
        bos.flush();
        os.flush();

        //receive from server
        String fromServer = dis.readUTF();
        System.out.printf(">>>Server: %s\n", fromServer);

        //closing
        os.close();
        dos.close();
        dis.close();
        socky.close();

    }
}
