package com.bittech.com.bittech.pc;

import java.util.Queue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:21 2019/4/21
 */
public class Consumer implements Runnable{
    private final Queue<Goods> goodsQueue;

    public Consumer(Queue<Goods> goodsQueue) {
        this.goodsQueue = goodsQueue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this.goodsQueue) {
                if(this.goodsQueue.isEmpty()) {
                    System.out.println(Thread.currentThread().getName()+" 商品队列已空，通知生产");
                    this.goodsQueue.notifyAll();
                }else {
                    Goods goods = this.goodsQueue.poll(); //检索并删除此队列的头，如果此队列为空，则返回 null 。
                    if(goods != null) {
                        System.out.println(Thread.currentThread().getName()+"获取到商品 ："+goods);
                    }

                }
            }
        }
    }
}
