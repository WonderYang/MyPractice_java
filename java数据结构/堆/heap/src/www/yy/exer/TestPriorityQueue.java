package www.yy.exer;

import sun.net.ftp.FtpDirEntry;

import java.util.PriorityQueue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:28 2019/10/10
 */
public class TestPriorityQueue {
    class Person {
        private String name;
        private int age;
    }
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
        priorityQueue.add(2);
        priorityQueue.add(-2);
        priorityQueue.add(10);
        System.out.println(priorityQueue.poll());
        PriorityQueue<Integer> priorityQueue1 = new PriorityQueue<>();

    }
}
