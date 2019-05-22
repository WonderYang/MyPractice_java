package www.yy.queue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 21:09 2019/5/22
 */
public class ArrayLoopQueue<E> implements impl<E> {
    private  Object[] arrayLoopQueue;
    private int head;
    private int tail;
    private int size;

    public ArrayLoopQueue(int maxSize) {
        //得多开辟一个空间，这个空间不能存放数据，用来区分循环队列的空与满
        arrayLoopQueue = new Object[maxSize+1];
    }

    @Override
    public void enQueue(E data) {
        if((tail+1)%arrayLoopQueue.length == head) {
            System.err.println("循环队列已满！！！");
            return;
        }
        arrayLoopQueue[tail] = data;
        tail = (tail+1)%arrayLoopQueue.length;
        size++;
    }

    @Override
    public E deQueue() {
        if(head == tail) {
            System.err.println("循环队列为空！！！");
            throw new NullPointerException();
        }
        E e = (E)arrayLoopQueue[head];
        head = (head+1)%arrayLoopQueue.length;
        size--;
        return e;
    }

    @Override
    public E peek() {
        return (E)arrayLoopQueue[head];
    }

    @Override
    public int getSize() {
        return size;
    }
}
