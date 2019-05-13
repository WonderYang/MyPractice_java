package multi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:41 2019/5/7
 */
public class ClientHandle implements Runnable{
    private final Socket socket;
    //构建一个Map来存储所有注册的客户端
    private static final Map<String,Socket> map= new ConcurrentHashMap<>();
    private String currentName;

    public ClientHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = this.socket.getInputStream();
            Scanner scanner = new Scanner(in);
            String message = null;
            while(true) {
                if(scanner.hasNextLine()) {
                    message = scanner.nextLine();
                }
                System.out.println(message);
                String[] arr = message.split(":");
                currentName = arr[1];
                if(arr.length==2 && arr[0].equals("register")) {
                    registerUser(currentName);
                }
                else if(message.equals("exit")) {
                    exit();
                }
                else if(arr.length==2 && arr[0].equals("group")) {
                    groupChat(message);
                }
                else {
                    sendMessage("输入格式有误，请重新输入：",this.socket);
                }
                printOnlineUsers();

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void registerUser(String name) {
        map.put(name, this.socket);
        sendMessage(name+"注册成功！！！",this.socket);

    }
    private void groupChat(String message) {
        //发送消息给在线的客户端
        Set<Map.Entry<String,Socket>> entrySet = map.entrySet();
        for(Map.Entry<String,Socket> entry:entrySet) {
            Socket socket = entry.getValue();
            if(socket == this.socket) {
                continue;
            }
            sendMessage(message,socket);
        }
    }
    private void privateChat(String name, String message) {
        Socket socket = map.get(name);
        sendMessage(message,socket);
    }

    private void sendMessage(String message,Socket socket) {
        try {
            OutputStream out = socket.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            printStream.println(currentName+":"+message);
            printStream.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printOnlineUsers() {
        System.out.println("当前在线的客户为："+"总在线："+map.size());
        for(String key: map.keySet()) {
            System.out.println(key);
        }

    }
    private void exit() {
        map.remove(currentName,this.socket);
    }

    //扩展点：如何处理异常关闭（比如：用户直接关闭进程）
    //Socket -》写数据 抛异常 关闭
}
