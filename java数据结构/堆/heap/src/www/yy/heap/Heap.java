package www.yy.heap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author : YangY
 * @Description :  数组实现大顶堆
 * @Time : Created in 19:11 2019/6/6
 */
public class Heap<E> {
    //传入一个外部比较器，实现任意类的大小比较
    private Comparator<E> comparator;
    private int size;
    private E[] elementData;
    private static final int DEFAULT_CAPACITY = 10;
    public Heap() {
        this.elementData = (E[])new Object[DEFAULT_CAPACITY];
    }
    public Heap(int initialCapacity) {
        this.elementData = (E[])new Object[initialCapacity];

    }

    public Heap(int initialCapacity, Comparator<E> comparator) {
        this.elementData = (E[])new Object[initialCapacity];
        this.comparator = comparator;
    }

    //构造方法，传入数组，返回建立好的堆
    public Heap(E[] arr) {
        elementData = (E[])new Object[arr.length];
        for(int i=0; i<arr.length; i++) {
            elementData[i] = arr[i];
        }
        size = elementData.length;
        //最后一个非叶子节点的下标
        int index = (arr.length-2) / 2;
        while(index >= 0) {
            shiftDown(index);
            index--;
        }
    }

    public int getSize() {
        return size;
    }
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * 比较方法，这个方法是模拟TreeMap的比较方法
     * @param o1
     * @param o2
     * @return
     */
    public int compare(E o1, E o2) {
        if(comparator == null) {
            //没有传入外部比较器
            //此时E必须为Compareable接口的子类，如果不是，下面这句代码就会抛出类型转换异常⭐
            return ((Comparable<E>)o1).compareTo(o2);
        }
        //有自己的比较器，构造方法传入进来的
        return comparator.compare(o1,o2);
    }
    //返回当前节点的左孩子下标
    public int leftChildIndex(int index) {
        return index*2 + 1;
    }
    //返回右孩子节点下标
    public int rightChildIndex(int index) {
        return index*2 + 2;
    }
    //取得当前节点的父节点的索引下标
    public int parentIndex(int index) {
        if(index == 0) {
            System.err.println("当前节点没有父节点！！！");
            return 0;
        }
        return (index-1)/2;
    }
    public void add(E e) {
        if(size == elementData.length) {
            grow();
        }
        elementData[size++] = e;
        siftUp(size-1);
    }
    //堆中很重要的一个方法，元素上浮
    //因为插入的值可能会打破堆的性质，大顶堆中，父节点一定大于它的孩子
    private void siftUp(int index) {
        while(index>0 && compare(elementData[index],elementData[parentIndex(index)])>0) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }
    private void swap(int indexA, int indexB) {
        E temp = elementData[indexA];
        elementData[indexA] = elementData[indexB];
        elementData[indexB] = temp;
    }

    private void grow() {
        int oldCap = elementData.length;
        int newCap = oldCap + (oldCap < 64
                ? oldCap
                :oldCap>>1);
        if(newCap > Integer.MAX_VALUE -8) {
            throw  new IndexOutOfBoundsException("数组太大");
        }
        //把原来长度的数组拷贝到新长度的数组里
        elementData =  Arrays.copyOf(elementData, newCap);
    }
//找出大顶堆的最大值
    public E findMax() {
        if(isEmpty()) {
            throw new IndexOutOfBoundsException("empty!!!!");
        }
        return elementData[0];
    }

    //去掉大顶堆的最大值，即去掉第一个元素，去掉后需要重新调整这个堆
    public E extractMax() {
        E result = findMax();
        swap(0,size-1);
        elementData[--size] = null;
        shiftDown(0);
        return result;
    }
    //将二叉树当前节点下沉到正确位置
    private void shiftDown(int index) {
        //这里不能小于等于哟
        while(leftChildIndex(index) < size) {
            int j = leftChildIndex(index);
            if(j+1 < size) {
                //此时有右孩子，得找出最大的那一个
                if(compare(elementData[j],elementData[j+1]) < 0) {
                    //现在j代表值较大的那一个孩子的下标
                    j++;
                }
            }
            if(compare(elementData[index],elementData[j]) > 0) {
                break;
            }
            swap(index,j);
            index = j;
        }
    }

    //顶元素替换为新值将堆
    public E replace(E newValue) {
        E max = findMax();
        elementData[0] = newValue;
        shiftDown(0);
        return max;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(E e:elementData) {
            if(e == null) {
                break;
            }
            res.append(e+"、");
        }
        return res.toString();
    }
}
