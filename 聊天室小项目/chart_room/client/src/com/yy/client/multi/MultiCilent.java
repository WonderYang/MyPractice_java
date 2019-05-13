package com.yy.client.multi;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:53 2019/5/7
 */
public class MultiCilent {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8084;
        try {
            Socket socket = new Socket(host,port);
            Thread read = new ReadDate(socket);
            read.setName("Thread-Client-Read");
            read.start();

            Thread write = new WriteDate(socket);
            write.setName("Thread-Client-Write");
            write.start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
