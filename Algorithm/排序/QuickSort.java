package www.yy.review;

/**
 * @Author : YangY
 * @Description : 时间复杂度：nlog2n
 * 空间复杂度：log2n（递归）
 * 优化：
 * 当序列为：1，2，3，4，5，6，7，8时
 * 选取temp为1，此时low为0，high为7，找到基准值就是1，此时左边不用递归了，而右边又递归
 * 以此类推下来，时间复杂度退化为了n^2
 * 优化一：用随机取值法来实现temp的取值
 * 但假如人品不好，第一次随机下来temp为1，实现一趟快排后随机的temp又为二...................
 * 优化二：三数取中法
 * 这种方法能有效预防数组的有序
 *
 * 优化三：当low和high的差值比较小的时候，其实差不多已经有序了，而我们知道，在接近有序的情况下，插入排序的效率最高
 * ，所以可以把那一段排序进行优化为直接插入排序
 * @Time : Created in 16:23 2019/8/14
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[]{2,5,10,22,1,5,7,3,19,4,0,-1,100};
        quick(arr);
        for(int i =0; i<arr.length; i++) {
            System.out.print(arr[i]+"、");
        }
    }

    public static void quick(int[] arr) {
        if(arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length-1);
    }

    public static void quickSort(int[] arr, int low, int high) {
        if(low == high) {
            return;
        }
        if(high-low+1 <= 10) {
            //直接插入排序
            insertSort(arr, low, high);
            return;
        }
        takeThreeNumber(arr, low, high);
        int par = partition(arr, low, high);
        if(par > low+1) {
            quickSort(arr, low, par-1);
        }
        if(par < high-1) {
            quickSort(arr, par+1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int temp = arr[low];
        //从后面开始找小于temp的值
        while(low < high) {
            while(low<high && arr[high]>=temp) {
                high--;
            }
            if(low == high) {
                break;
            }else {
                arr[low] = arr[high];
            }
            //从前面开始找大于temp的值
            while(low<high && arr[low]<=temp) {
                low++;
            }
            if(low == high) {
                break;
            }else {
                arr[high] = arr[low];
            }
        }
        //此时low等于high
        arr[low] = temp;
        return low;
    }

    public static void swap(int[] arr, int indexA, int indexB) {
        int temp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = temp;
    }

    //保证arr[mid] <= arr[low] <= arr[high]
    public static void takeThreeNumber(int[] arr, int low, int high) {
        int mid = (low+high)>>>1;
        if(arr[mid] > arr[low]) {
            swap(arr, mid, low);
        }
        if(arr[mid] > arr[high]) {
            swap(arr, mid, high);
        }
        if(arr[low] > arr[high]) {
            swap(arr, low, high);
        }
    }

    //直接插入排序
    public static void insertSort(int[] arr, int low, int high) {
        for(int i =low+1; i<=high; i++) {
            int temp = arr[i];
            int j;
            for(j=i-1; j>=low; j--) {
                if(temp < arr[j]) {
                    arr[j+1] = arr[j];
                }else {
                    break;
                }
            }
            arr[j+1] = temp;
        }
    }
}
