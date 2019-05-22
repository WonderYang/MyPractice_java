package www.yy.queue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:23 2019/5/22
 */
public class ArrayQueue<E> implements impl<E>{
    private Object[] arrayQueue;
    private int head;
    private int tail;

    public ArrayQueue(int maxSize) {
        arrayQueue = new Object[maxSize];
    }

    @Override
    public void enQueue(E data) {
        if(tail == arrayQueue.length) {
            if(head == 0) {
                System.err.println("队列已满！！！");
                return;
            }else {
                int i = 0;
                for( i=head; i<tail; i++) {
                    arrayQueue[i-head] = arrayQueue[i];
                }
                tail = tail - head;
                head = 0;  //别忘把head置零
            }
        }
        arrayQueue[tail++] = data;
    }

    @Override
    public E deQueue() {
        E e = (E)arrayQueue[head];
        head++;
        return e;
    }

    @Override
    public E peek() {
        return (E)arrayQueue[head];
    }

    @Override
    public int getSize() {
        return tail-head;
    }
}
