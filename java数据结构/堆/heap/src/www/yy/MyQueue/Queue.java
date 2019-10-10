package www.yy.MyQueue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 16:03 2019/10/9
 */
public interface Queue<E> {
    void enqueue(E e);
    E dequeue();
    E peek();
    int size();
    boolean isEmpty();
}
