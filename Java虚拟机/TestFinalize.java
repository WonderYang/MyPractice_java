package com.yy.jvm;

/**
 * @Author : YangY
 * @Description :  覆写Object中finalize方法后对垃圾回收产生的影响
 * @Time : Created in 20:25 2019/4/29
 */
class Test1 {
    public static Test1 test1;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        test1 = this;
        System.out.println("Test1类对象执行了finalize方法");
    }
}
public class TestFinalize {
    public static void main(String[] args) {
        Test1.test1 = new Test1();
        Test1.test1 = null;
        //第一次回收，因为该类覆写了finalize方法，则会在第一次回收的时候拯救
        System.gc();
        //若没有这个延时，会观察不到想要的结果，因为gc（）方法调用后并不是马上就执行，类似于中断一样；
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Test1.test1 != null) {
            System.out.println("test1 is alive!");
        }else {
            System.out.println("test1 is dead!");
        }

        //第二次回收
        Test1.test1 = null;
        System.gc();
        if(Test1.test1 != null) {
            System.out.println("test1 is alive!");
        }else {
            System.out.println("test1 is dead!");
        }
    }
}
