package com.bittech.com.bittech.pc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : YangY
 * @Description :  生产与消费者模式初级版，消费者生产者的数量和速率都是我们在代码中先设置好的；
 * @Time : Created in 10:25 2019/4/21
 */
public class TestProducerConsumer {
    public static void code1() {
        System.out.println("请输入容量大小：");
    }
    public static void main(String[] args) {
        final long Pspeed;
        final long Cspeed;
        final int numOfProducer;
        final int numOfComsumer;
        final int maxGoods = 10;
        final Queue<Goods> goodsQueue = new LinkedList<>();
        System.out.println("请输入生产者的数量：");
        Scanner scanner2 = new Scanner(System.in);
        numOfProducer = scanner2.nextInt();
        System.out.println("请输入生产者的速率：");
        Scanner scanner = new Scanner(System.in);
        Pspeed = scanner.nextLong();
        System.out.println("请输入消费者的数量：");
        Scanner scanner3 = new Scanner(System.in);
        numOfComsumer = scanner2.nextInt();
        System.out.println("请输入消费者的速率：");
        Scanner scanner1 = new Scanner(System.in);
        Cspeed = scanner1.nextLong();
        //producer
        final Producer producer = new Producer(goodsQueue,maxGoods,Pspeed);
        final Consumer consumer = new Consumer(goodsQueue,Cspeed);

//      只有一个消费者和一个生产者的情况
//        Thread thread1 = new Thread(producer,"生产者");
//        Thread thread2 = new Thread(consumer,"消费者");
//        thread1.start();
//        thread2.start();

        //多线程版本，多个消费者和多个生产者
        for(int i = 0; i<numOfProducer; i++) {
            new Thread(producer,"生产者-"+i).start();
        }
        for(int i=0; i<numOfComsumer; i++) {
            new Thread(consumer,"消费者-"+i).start();
        }
    }
}
