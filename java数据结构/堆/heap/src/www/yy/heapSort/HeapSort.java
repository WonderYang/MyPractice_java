package www.yy.heapSort;

import www.yy.heap.Heap;

import java.util.Random;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 7:54 2019/8/6
 */
public class HeapSort {
    public static void main(String[] args) {
        int n = 100;
        Random random = new Random();
        Integer[] data = new Integer[n];
        for(int i=0; i<n; i++) {
            data[i] = random.nextInt(1000);
        }
        heapSort2(data);
        for(int x:data) {
            System.out.print(x+"、");
        }
    }

    /**
     * 堆排序的时间复杂度是稳定的，永远都是nlogn
     * 下面的第一种方法的空间复杂度为O（n）
     * @param arr
     */
    public static void heapSort1(Integer arr[]) {
        long start = System.currentTimeMillis();
        //首先将数组调整成堆
        Heap<Integer> heap = new Heap<>(arr);
        //升序输出，所以从最后一个下标开始
        for(int i=arr.length-1; i>=0; i--) {
            arr[i] = heap.extractMax();
        }
        long end = System.currentTimeMillis();
        System.out.println("堆排序共耗时："+(end-start)+"ms");
    }

    /**
     * 原地堆排序，即空间复杂度为O（1）
     * @param arr
     */
    public static void heapSort2(Integer[] arr) {
        //1.把数组变成堆
        int length = arr.length;
        for(int i=(length-1-1)/2; i>=0; i--) {
            siftdown(arr, length, i);
        }
        //2.依次将最大值换到末尾
        for(int i=length-1; i>=0; i--) {
            swap(arr, 0, i);
            siftdown(arr, i, 0);
        }
    }
    public static void swap(Integer[]arr, int indexA, int indexB) {
        int temp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = temp;
    }
    private static void siftdown(Integer[] arr, int n, int k) {
        while(2*k+1 < n) {
            int j=2*k+1;
            //取出左右孩子最大值
            if(j+1 < n) {
                //此时有右孩子
                if(arr[j].compareTo(arr[j+1]) < 0) {
                    j++;
                }
            }
            //此时arr[j]存放了左右两个孩子的最大值
            if(arr[k] > arr[j]) {
                break;
            }
            swap(arr, k, j);
            k = j;
        }
    }
}
