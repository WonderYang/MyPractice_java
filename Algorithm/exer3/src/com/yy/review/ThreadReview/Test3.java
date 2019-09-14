package com.yy.review.ThreadReview;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:47 2019/9/13
 */
class MyThread2 extends Thread {
    @Override
    public void run() {
        System.out.println(currentThread().getName()+" : hello");
    }
}
public class Test3 {
    public static void main(String[] args) {
        MyThread2 thread1 = new MyThread2();
        MyThread2 thread2 = new MyThread2();
        thread1.start();
        thread2.start();
    }
}
