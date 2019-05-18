package com.bittech.com.bittech.pc;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:15 2019/4/21
 */
public class Producer implements Runnable{
    private final Queue<Goods> goodsQueue;
    private final Integer maxGoods;
    private final long speed;
    //goodID用来给产品编号
    private final AtomicInteger goodsID = new AtomicInteger(0);

    public Producer(Queue<Goods> goodsQueue, Integer maxGoods, long speed) {
        this.goodsQueue = goodsQueue;
        this.maxGoods = maxGoods;
        this.speed = speed;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this.goodsQueue){
                if(this.goodsQueue.size() >= this.maxGoods) {
                    try {
                        System.out.println(Thread.currentThread().getName()+" 商品队列已满...等待。。。。");
                        this.goodsQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    Goods goods = new Goods("商品-"+goodsID.getAndIncrement(),Math.random()*10);
                    this.goodsQueue.add(goods);
                    System.out.println(Thread.currentThread().getName()+"生成商品:"+goods.toString());
                }
            }
        }
    }
}
