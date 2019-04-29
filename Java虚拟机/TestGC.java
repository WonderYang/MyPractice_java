package com.yy.jvm;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 18:51 2019/4/29
 */
class Test {
    private byte[] bigSize = new byte[1024*1024];
    public Object instance;
}
public class TestGC {
    public static void main(String[] args) {
        Test test1 = new Test();
        Test test2 = new Test();

        test1.instance = test2;
        test2.instance = test1;
        //test1、test2应该在虚拟机栈上
        test1 = null;  //test1不再引用堆上的对象
        test2 = null; //test2不再引用堆上的对象

        //垃圾回收器是一个守护线程
        System.gc();

    }
}
