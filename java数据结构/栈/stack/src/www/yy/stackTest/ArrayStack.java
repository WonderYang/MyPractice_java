package www.yy.stackTest;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 16:55 2019/5/19
 */
public class ArrayStack<T> implements Stack<T> {
    private Object[] elementDate;
    private int maxSize;
    private int currentSize = -1;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        elementDate = new Object[maxSize];      //数组类型不能用泛型来定义
    }

    @Override
    public boolean push(T t) {
        if(currentSize == maxSize) {
            System.out.println("栈满！！！");
            return false;
        }
        elementDate[++currentSize] = t;
        return true;
    }

    @Override
    public T top() {
        if(currentSize < 0) {
            System.out.println("栈空！！！");
            return null;
        }
        T date =(T) elementDate[currentSize];
        currentSize--;
        return date;
    }

    @Override
    public T peek() {
        if(currentSize < 0) {
            System.out.println("栈空！！！");
            return null;
        }
        return (T)elementDate[currentSize];
    }

    @Override
    public int size(){
        return currentSize+1;
    }
}
