package com.yy.client.singleCilent;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:37 2019/5/7
 */
public class SingleClient {
    public static void main(String[] args) {
        int port = 8082;
        InetAddress host = null;
        try {
            host = InetAddress.getLocalHost();

        }catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            final Socket socket = new Socket(host,port);
            //2.1发送数据
            OutputStream out = socket.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            printStream.print("i coming.....");
            printStream.flush();

            //2.2收取数据
            InputStream in = socket.getInputStream();



        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
