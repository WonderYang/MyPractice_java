package www.sort.java;

/**
 * @Author : YangY
 * @Description :一路快排
 * @Time : Created in 19:10 2019/3/14
 */
public class SortQuick {
    public static void main(String[] args) {
        int[] arr = new int[]{1,4,2,7,-1,10,4};
        //int[] arr = SortHelper.produceArr(10000000,1000,10000);
        //int[] arr = SortHelper.produceSortyArr(5500);
        long start = System.currentTimeMillis();
        quickSort(arr);
        long end = System.currentTimeMillis();
        System.out.println("所耗时间为："+(end-start)+"ms");
        SortHelper.print(arr);

    }
    public static void quickSort(int[] arr) {
        int length = arr.length;
        if(length <= 1) {
            return;
        }
        quickSortInternal(arr,0,length-1);

    }
    private static void quickSortInternal(int[] arr, int l, int r) {
        if(l >= r) {
            return;
        }
        //获取基准值
        int par = patition(arr, l, r);
        quickSortInternal(arr, l, par-1);
        quickSortInternal(arr, par+1, r);
    }
    private static int patition(int[] arr, int l, int r) {
        //随机选取一个下标作为基准值，防止了原数组近乎有序导致的时间复杂度近乎降为O(n^2);
        swap(arr,randomIndex(l,r),l);
        int val = arr[l];
        //[l+1, j]区间内都是小于val
        int j = l;
        //[j+1, i]区间内都是大于等于val
        int i = l+1;
        for(; i<=r;i++) {
            if(arr[i] < val) {
                swap(arr, i, j+1);
                j++;
            }
        }
        swap(arr, l, j);
        return j;
    }
    private static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    private static int randomIndex(int l, int r) {
         int random = (int)(Math.random()*(r-l+1)) + l;
         return random;
    }
}
