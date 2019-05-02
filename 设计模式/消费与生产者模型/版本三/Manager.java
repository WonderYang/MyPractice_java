package com.bittech.com.bittech.pc;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

/**
 * @Author : YangY
 * @Description :  用来将属性文件中的值读取出来并进行对应赋值；
 * @Time : Created in 22:48 2019/5/2
 */
public class Manager {
    final long Pspeed;
    final long Cspeed;
    final int numOfProducer;
    final int numOfComsumer;
    final int maxGoods;
    final Queue<Goods> goodsQueue = new LinkedList<>();

    public Manager(Properties properties) {
        Pspeed = Integer.parseInt(properties.getProperty("producer.speed"));
        Cspeed = Integer.parseInt(properties.getProperty("consumer.speed"));
        numOfProducer = Integer.parseInt(properties.getProperty("producer.num"));
        numOfComsumer = Integer.parseInt(properties.getProperty("consumer.num"));
        maxGoods = Integer.parseInt(properties.getProperty("good.maxGoods"));
    }
    public void start() {
        for(int i=0; i<numOfProducer; i++) {
            new Thread(new Producer(goodsQueue,maxGoods,Pspeed),"Producer--"+i).
            start();
        }
        for(int i=0; i<numOfComsumer; i++) {
            new Thread(new Consumer(goodsQueue,Cspeed),"Consumer--"+i).
            start();
        }
    }
    public String getCurrentInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- 当前生产者消费者模型中的配置信息如下 --");
        sb.append("\n");
        sb.append("容量:");
        sb.append(this.maxGoods);
        sb.append("\n");
        sb.append("生产者数量:");
        sb.append(this.numOfProducer);
        sb.append("\n");
        sb.append("生产速率(ms):");
        sb.append(this.Pspeed);
        sb.append("\n");
        sb.append("消费者数量:");
        sb.append(this.numOfComsumer);
        sb.append("\n");
        sb.append("消费速率(ms):");
        sb.append(this.Cspeed);
        sb.append("\n");
        return sb.toString();
    }
}
