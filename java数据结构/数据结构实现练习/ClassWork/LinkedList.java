package www.ClassWork;

/**
 * @Author : YangY
 * @Description :单链表简单功能的实现
 * @Time : Created in 19:26 2019/3/3
 */

public class LinkedList implements Sequence{
    private Node dummyHead;
    private int size;
    //构造初始化
    public LinkedList() {
        this.dummyHead = new Node(null,null);
    }

    @Override
    public void add(Object data) {
        //头插法
        addFirst(data);
    }

    @Override
    public boolean remove(int index) {
        isIndexOut(index);
        Node temp = dummyHead;
        //将temp移到index处；
        for (int i = 0; i<index-1; i++) {
            temp = temp.next;
        }
        temp.next.date = null;
        temp.next = temp.next.next;
        size--;
        return true;
    }

    @Override
    public Object get(int index) {
        isIndexOut(index);
        Node temp = dummyHead;
        //将temp移到index处；
        for (int i = 0; i<index && temp.next!=null; temp = temp.next, i++) {

        }
        return temp.date;
    }

    @Override
    public boolean contains(Object data) {
        Object[] array = toArray();
        Node temp = dummyHead;
        if (data == null) {
            for(int i = 0; i < array.length; i++) {
                if (data == array[i]) {
                    return true;
                }
            }
        }
        else {
            for (int i = 0; i < array.length; i++) {
                if(data.equals(array[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object set(int index, Object newData) {
        //暂不实现
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for(;dummyHead.next != null; dummyHead = dummyHead.next) {
            dummyHead.date = null;
            size--;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node temp = dummyHead;
        for(int i = 0; i < size && temp.next != null; temp = temp.next) {
            array[i++] = temp.next.date;
        }
        return array;
    }

    private class Node {
        Object date;
        Node next;
        public Node(Object date, Node next) {
            this.date = date;
            this.next = next;
        }
        public Node(Object date) {
            this.date = date;
        }
    }

    //使用头插将节点插入火车
    public void addFirst(Object date) {
        //创建新节点并存放数据
        Node newnode = new Node(date);
        //将当前节点指向原来的头节点
        newnode.next = dummyHead.next;
        dummyHead.next = newnode;
        //dummyHead.next = new Node(date,dummyHead.next);   //这条语句等效于上面两条语句，更加精炼；
        size++;
    }

    //在任意位置插入节点  (插入在index的后面）
    public void add(int index,Object data) {
        isIndexOut(index);
        Node prev = dummyHead;
        for (int i = 0;i < index;i++) {
            prev = prev.next;
        }
        Node newNode = new Node(data);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }

    public void isIndexOut(int index) {
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("index Illegal");
        }
    }

    public void printList() {
        for(Object x: toArray()) {
            System.out.print(x+"、");
        }
        System.out.println("\n");
    }

}
