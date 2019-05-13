package multi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:36 2019/5/7
 */
public class MultiThreadServer {
    private static final ExecutorService excutor = Executors.newFixedThreadPool(10,
            new ThreadFactory() {
                private final AtomicInteger id = new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("Thread-ClientHandler-"+id.getAndIncrement());
                    return t;
                }
            });
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8084);
            System.out.println("服务器启动......");

            while(true) {
                //这里是一个阻塞操作
                final Socket socket = serverSocket.accept();
                //使用线程池来接受客户端
                System.out.println("客户端链接..."+socket.getRemoteSocketAddress()+":"+socket.getPort());
                excutor.submit(new ClientHandle(socket));

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
