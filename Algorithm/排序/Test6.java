package www.sort.java;

/**
 * @Author : YangY
 * @Description :  归并排序算法，时间复杂度nlogn;
 * @Time : Created in 15:38 2019/3/12
 */
public class Test6 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,5,4,7,2,5,9,-1};
        mergeSort(arr);
        SortHelper.print(arr);

    }
    public static void mergeSort(int[] arr) {
        int n = arr.length;
        if(n <= 1) {
            return;
        }
        mergeSortIntern(arr,0,n-1);
    }
    public static void mergeSortIntern(int[] arr, int left, int right) {
        if(left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSortIntern(arr, left, mid);
        mergeSortIntern(arr,mid+1,right);
        merge(arr,left,mid,right);

    }
    public static void merge(int[] arr, int left, int mid, int right) {
        int[] newArr = new int[right - left + 1];
        int k = 0;
        //前半部分数组的起始
        int i = left;   //当初写成i = 0，啊啊啊
        //后半部分数组的起始
        int j = mid + 1;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                newArr[k++] = arr[i];
                i++;
            } else {
                newArr[k++] = arr[j];
                j++;
            }
        }
        //总会有一个数组没有拷贝完，此时通过以下方法将剩余的元素拷贝到新数组
        if(i > mid) {
            while(j <=right) {
                newArr[k++] = arr[j++];
            }
        }else {
            while(i <= mid) {
                newArr[k++] = arr[i++];
            }
        }
        //将newArr拷贝到原数组
        for(int n = 0; n < right-left + 1; n++) {
            arr[left+n] = newArr[n];
        }
    }
}
