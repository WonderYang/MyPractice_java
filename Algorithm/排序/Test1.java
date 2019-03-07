package www.sort.java;

/**
 * @Author : YangY
 * @Description :  冒泡排序,时间复杂度O(n^2);
 * @Time : Created in 19:25 2019/3/7
 */
public class Test1 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,7,4,2,10,10,0,-1};
        bubbleSort(arr);
        SortHelper.print(arr);
    }
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for(int i = 0; i < n-1; i++) {
            boolean flag = true;
            for(int j = 0; j < n-i-1; j++) {
                if(arr[j] > arr[j+1]) {
                    flag = false;
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            //优化
            if(flag) {
                return;
            }
        }
    }
}
