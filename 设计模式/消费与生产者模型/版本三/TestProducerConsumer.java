package com.bittech.com.bittech.pc;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : YangY
 * @Description :  生产与消费者模式升级版，消费者生产者的数量和速率设置在我们的属性文件中（）；
 * 注意：属性文件中不能有分号；
 * @Time : Created in 10:25 2019/4/21
 */
public class TestProducerConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try(InputStream inputStream = TestProducerConsumer
                .class.getClassLoader().getResourceAsStream("com/bittech/com/bittech/pc/hello.properties")) {
            properties.load(inputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
        Manager manager = new Manager(properties);
        manager.start();

    }
}
