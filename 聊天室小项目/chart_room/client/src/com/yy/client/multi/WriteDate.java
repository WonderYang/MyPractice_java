package com.yy.client.multi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:53 2019/5/7
 */
public class WriteDate extends Thread {
    private final Socket socket;
    public WriteDate(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            OutputStream out = socket.getOutputStream();
            PrintStream printStream = new PrintStream(out);
                //从控制台输入数据
                Scanner scanner = new Scanner(System.in);
                String message = null;
                while(true) {
                    System.out.print("请输入：");
                    if(scanner.hasNextLine()) {
                        message = scanner.nextLine();
                    }
                    //将数据发送给服务器
                    //当初最大的坑，这里要用println方法，不要用print方法，不然读不出数据；
                    printStream.println(message);
                    if(message.equals("quit")) {
                        printStream.close();
                        scanner.close();
                        break;
                    }
                    //System.out.println("消息已发送....");
                    printStream.flush();
                }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
