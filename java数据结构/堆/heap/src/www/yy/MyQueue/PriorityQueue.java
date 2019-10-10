package www.yy.MyQueue;

import www.yy.heap.Heap;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 16:05 2019/10/9
 */
public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {
    private Heap<E> heap = new Heap<>();
    @Override
    public void enqueue(E e) {
        heap.add(e);
    }

    @Override
    public E dequeue() {
        //移除最大元素
        return heap.extractMax();
    }

    @Override
    public E peek() {
        return heap.findMax();
    }

    @Override
    public int size() {
        return heap.getSize();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

}
