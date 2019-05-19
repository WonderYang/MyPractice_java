package www.yy.stackTest;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 22:41 2019/5/19
 */
public class LinkedListStack<T> implements Stack<T> {
    private class Node{
        T t;
        Node nextNode;
        public Node(T t) {
            this.t = t;
        }
    }
    private Node fistNode;
    private int currentSize;
    @Override
    public boolean push(T t) {
        Node node = new Node(t);
        if(fistNode == null) {
            fistNode = node;
        }
        node.nextNode = fistNode;
        fistNode = node;
        currentSize++;
        return true;
    }

    @Override
    public T top() {
        if(currentSize == 0) {
            System.out.println("栈空！！！");
            return null;
        }
        T t = fistNode.t;
        fistNode = fistNode.nextNode;
        currentSize--;
        return t;
    }

    @Override
    public T peek() {
        if(fistNode == null) {
            System.out.println("栈空！！！");
            return null;
        }
        return fistNode.t;
    }

    @Override
    public int size() {
        return currentSize;
    }
}
