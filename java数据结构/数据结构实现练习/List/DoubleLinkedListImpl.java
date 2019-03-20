package www.List;

import www.ClassWork.DoubleLinkedList;

/**
 * @Author : YangY
 * @Description :  基于泛型的双链表基本功能实现
 * @Time : Created in 20:54 2019/3/19
 */
public class DoubleLinkedListImpl<T> implements Sequence<T>{
    int size;
    private Node<T> head;    //头节点
    private Node<T> tail;    //尾节点

    private class Node<T> {
        private T date;
        Node<T> next;
        Node<T> prev;
        public Node(T date){
            this.date = date;
        }
        public Node(Node<T> prev, T date, Node<T> next) {
            this.prev = prev;
            this.date = date;
            this.next = next;
        }
    }
    @Override
    public void add(T date) {
        if(head == null) {
            head = new Node<>(date);
            tail = head;
        }else {
            Node<T> newNode = new Node(date);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public boolean remove(int index) {
        rangeCheck(index);
        // 取得指定位置Node
        Node<T> temp = head;
        int i=0;
        for(;i < index-1; i++) {
            temp = temp.next;
        }
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        size--;
        return true;
    }

    @Override
    public T get(int index) {   //这个T代表返回类型为泛型，并没有定义泛型方法
        rangeCheck(index);
        int i = 0;
        Node<T> temp = head;
        for(;i<index-1;i++) {
            temp = temp.next;
        }
        return temp.date;
    }

    @Override
    public boolean contains(Object data) {
        Node<T> temp = head;
        for(;temp!=null;temp=temp.next) {
            if(temp.date == data) {
                return true;
            }
        }
        return false;
    }

    @Override
    /**
     * 这个方法是在第index个节点插入一个新节点
     */
    public void insertNode(int index, Object newData) {
        rangeCheck(index);
        int i = 0;
        Node<T> temp = head;
        for(; i < index-1; i++) {
            temp = temp.next;
        }
        Node<T> newNode = new Node(newData);
        temp.prev.next = newNode;
        newNode.prev = temp.prev;
        newNode.next = temp;
        temp.prev = newNode;
        size++;

    }
    @Override
    /**
     * 这个方法是将第index个节点的值替换为新设定的值
     */
    public T set(int index, T newData) {
        rangeCheck(index);
        // 取得指定位置Node
        Node<T> temp = head;
        int i=0;
        for(;i < index-1; i++) {
            temp = temp.next;
        }
        T oldData = temp.date;
        temp.date = newData;
        return oldData;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for(;head!=null; ) {
            Node<T> temp = head.next;
            head.next = head.prev = null;
            head.date = null;
            head = temp;
            size--;
        }
    }

    @Override
    //不能够创建泛型数组
    public  Object[] toArray() {
        Object[] arr = new Object[size];
        Node<T> temp = head;
        for(int i=0; temp!= null;temp = temp.next) {
            arr[i++] = temp.date;
        }
        return  arr;
    }

    private void rangeCheck(int index) {
        if(index<0 ||index>size) {
            throw new IndexOutOfBoundsException("索引非法");
        }
    }
}
