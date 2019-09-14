package com.yy.review.ThreadReview;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:06 2019/9/13
 */
class MyRunnableT implements Runnable {
    TestThread testThread;
    public MyRunnableT(TestThread testThread) {
        this.testThread = testThread;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getState());
        System.out.println("lala");
    }
}
public class TestThread {
    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        MyRunnableT myRunnableT = new MyRunnableT(testThread);
        Thread thread = new Thread(myRunnableT,"线程A");
        thread.start();
        try {
            Thread.sleep(2000);
            testThread.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState());

    }
}