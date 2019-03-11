package www.school.java;

/**
 * @Author : YangY
 * @Description :将数组向右移动n位并打印
 * @Time : Created in 21:21 2019/3/11
 */
public class ResArr {
    public static void main(String[] args) {
        int[] arr = new int[]{0,1,2,3,4,5,6,7,8,9};
        reverseN(arr, 3);
        print(arr);

    }
    public static void reverseN(int[] arr, int n) {
        int length = arr.length;
        for(int i = 0; i < n; i++) {
            int temp = arr[length-1];
            for(int j = length-1; j>=0; j--) {
                if(j == 0) {
                    arr[j] = temp;
                }else {
                    arr[j] = arr[j-1];
                }
            }
        }
    }
    public static void print(int[] arr) {
        for(int i:arr) {
            System.out.print(i+"、");
        }
    }
}
