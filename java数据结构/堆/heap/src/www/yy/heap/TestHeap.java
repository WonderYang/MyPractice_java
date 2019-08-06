package www.yy.heap;

import java.util.TreeMap;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 18:49 2019/7/29
 */
public class TestHeap {
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{62,41,30,28,16,22,13,19,17,15};
//        Heap<Integer> heap = new Heap<>();
//        for(int i:arr) {
//            heap.add(i);
//        }
//        heap.add(52);
//        System.out.println(heap);
//
//        heap.extractMax();
        //要使用这个构造方法时，必须使用Integer数组，而不是int型数组
        Heap<Integer> heap = new Heap<>(arr);
        System.out.println(heap);
//62、41、30、28、16、22、13、19、17、15、
    }
}
