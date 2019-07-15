package www.yy.exer.day17;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 23:08 2019/7/9
 */
class A implements Runnable {
    private Object object;
    private int ID;
    private static volatile int count = 0;

    public A(Object object, int ID) {
        this.object = object;
        this.ID = ID;
    }

    @Override
    public void run() {
        synchronized (object) {
            while(count<=75) {
                if(count/5%3+1 == ID) {
                    int i=0;
                    while((i++ < 5) ) {
                        count++;
                        System.out.println(Thread.currentThread().getName()+"输出："+count);

                    }
                    object.notifyAll();
                }else {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
public class ThreadPrintNum {

    public static void main(String[] args) {
        Object object = new Object();
        Thread thread1 = new Thread(new A(object,1),"thread1");
        Thread thread2 = new Thread(new A(object,2),"thread2");
        Thread thread3 = new Thread(new A(object,3),"thread3");
        thread1.start();
        thread2.start();
        thread3.start();

    }
}
