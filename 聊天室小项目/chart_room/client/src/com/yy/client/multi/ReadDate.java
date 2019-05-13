package com.yy.client.multi;

import com.yy.client.singleCilent.SingleClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:53 2019/5/7
 */
public class ReadDate extends Thread {
    private final Socket socket;
    public ReadDate(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        super.run();
        try {
            InputStream in = socket.getInputStream();
            Scanner scanner = new Scanner(in);
            while(true) {
                if(scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    System.out.println(message);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
