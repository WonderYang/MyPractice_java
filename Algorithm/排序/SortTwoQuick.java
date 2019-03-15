package www.sort.java;

/**
 * @Author : YangY
 * @Description :  双路快排的实现
 * @Time : Created in 16:49 2019/3/15
 */
public class SortTwoQuick {
    public static void main(String[] args) {
        //int[] arr = new int[]{1,4,2,7,-1,10,4};
        //产生大量数据相同的一个数组
        int[] arr = SortHelper.produceArr(60000,20,30);
        //int[] arr = SortHelper.produceSortyArr(5500);
        long start = System.currentTimeMillis();
        twoQuickSort(arr);
        long end = System.currentTimeMillis();
        System.out.println("所耗时间为："+(end-start)+"ms");   //5ms,当使用一路快排时则为174ms;
        //SortHelper.print(arr);
}

    public static void twoQuickSort(int[] arr) {
        int length = arr.length;
        if(length <= 1) {
            return;
        }
        twoQuickSortInternal(arr,0,length-1);

    }
    private static void twoQuickSortInternal(int[] arr, int l, int r) {
        if(l >= r) {
            return;
        }
        //获取基准值
        int par = patition(arr, l, r);
        twoQuickSortInternal(arr, l, par-1);
        twoQuickSortInternal(arr, par+1, r);
    }
    private static int patition(int[] arr, int l, int r) {
        //找一个随机下标的数组值作为基准值
        int random = randomIndex(l, r);
        int val = arr[random];
        swap(arr, l, random);
        int i = l + 1;
        int j = r;
        while(true) {
            while(i<=r && arr[i]<val) {
                i++;
            }
            while(j>=l+1 && arr[j]>val) {
                j--;
            }
            if(i > j) {
                break;
            }
            swap(arr, i, j);
            i++;
            j--;
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
