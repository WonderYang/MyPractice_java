package com.yy.server.singleServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:24 2019/5/7
 */
public class SingleServer {
    public static void main(String[] args) {
        //1.创建ServerSocket
        try {
            ServerSocket serverSocket = new ServerSocket(8082);
            System.out.println("服务器启动....."+serverSocket.getInetAddress()+":"+serverSocket.getLocalPort());

            //2.等待客户端链接
            //这一步是阻塞操作，直到客户端连接上了才会继续执行
            final Socket socket = serverSocket.accept();
            System.out.println("客户端链接..."+socket.getRemoteSocketAddress()+":"+socket.getPort());

            //3.服务器可以进行接受和发送数据
            InputStream in = socket.getInputStream();
            Scanner scanner = new Scanner(in);
            String message = scanner.next();
            System.out.println("接收到客户端发送的数据："+message);

            //3.2 服务端发送数据
            OutputStream out = socket.getOutputStream();
            PrintStream printStream = new PrintStream(out);



        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
