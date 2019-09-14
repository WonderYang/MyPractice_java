package com.yy.review.ThreadReview;

import java.util.HashSet;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 13:34 2019/9/13
 */
class MyThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" : hello");
    }
}
public class Test1 {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread th1 = new Thread(myThread);
        Thread th2 = new Thread(myThread);
        th1.start();
        th1.yield();
        th2.start();
    }
}

