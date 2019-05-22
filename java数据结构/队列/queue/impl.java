package www.yy.queue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:19 2019/5/22
 */
public interface impl<E> {
    void enQueue(E data);
    E deQueue();
    E peek();
    int getSize();

}
