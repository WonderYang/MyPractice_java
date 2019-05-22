package www.yy.queue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:52 2019/5/22
 */
public class LinkedQueue<E> implements impl<E>{
    private class Node {
        private E data;
        private Node next;
        public Node(E data) {
            this.data = data;
        }
    }
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void enQueue(E data) {
        Node newNode = new Node(data);
        if(head == null) {
            head = tail = newNode;
            size++;
            return;
        }
        tail.next = newNode;
        tail = tail.next;
        size++;
    }

    @Override
    public E deQueue() {
        E e = head.data;
        head = head.next;
        size--;
        return e;
    }

    @Override
    public E peek() {
        return head.data;
    }

    @Override
    public int getSize() {
        return size;
    }
}
