package www.yy.review;

import javax.naming.InsufficientResourcesException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Author : YangY
 * @Description :输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
 * 题目地址：https://www.nowcoder.com/questionTerminal/6a296eb82cf844ca8539b57c23e6e9bf
 * @Time : Created in 22:24 2019/8/21
 */
/**
 * 第一种解法，大顶堆，先选取数组的前k个数组成一个大顶堆，那么此时的堆顶即为最大元素，此时再拿剩余的数来与堆顶比较
 * 如何比堆顶小，则替换堆顶，并进行下沉操作，如果比堆顶大，则这个数肯定不属于最小的K个数里面；
 * 此种时间复杂度为kLogn
 */
public class Solution {
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        int[] arr = new int[k];
        for(int i=0; i<k; i++) {
            arr[i] = input[i];
        }
        buildHeap(arr);
        for(int i=k; i<input.length; i++) {
            if(input[i] < arr[0]) {
                arr[0] = input[i];
                shiftDown(arr, 0);
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        for(int i:arr) {
            list.add(i);
        }
        return list;
    }

    public static void buildHeap(int[] arr) {
        for(int i=(arr.length-2)/2; i>=0; i--) {
            shiftDown(arr, i);
        }
    }
    public static void shiftDown(int[] arr, int index) {
        while(index*2+1 < arr.length) {
            int j = index*2+1;
            if(j+1 < arr.length) {
                //表明还有右孩子
                if(arr[j] < arr[j+1]) {
                    j++;
                }
            }
            if(arr[index] < arr[j]) {
                swap(arr, index, j);
                index = j;
            }else {
                break;
            }

        }
    }
    public static void swap(int[] arr, int indexA, int indexB) {
        int temp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = temp;
    }
}

/**
 * 第二种解法，冒泡排序法
 */
//public class Solution {
//    public static void main(String[] args) {
//        int[] arr = new int[]{1,5,78,3,10,5,7,3,-1};
//        Solution solution = new Solution();
//        ArrayList<Integer> list = solution.GetLeastNumbers_Solution(arr, 5);
//        System.out.println(list);
//    }
//    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        int[] arr = input;
//        //判断是否合格，否则返回空（这个根据题意来判断的）
//        if(input.length < k || k<0) {
//            return arrayList;
//        }
//        //降序冒泡，只需要冒泡k轮即可得到最小的K个值
//        for(int i=0; i<k; i++) {
//            for(int j=0; j<input.length-i-1; j++) {
//                if(arr[j] < arr[j+1]) {
//                    int temp = arr[j];
//                    arr[j] = arr[j+1];
//                    arr[j+1] = temp;
//                }
//            }
//        }
//        for(int index=arr.length-1; k>0; k--,index--) {
//            arrayList.add(arr[index]);
//        }
//        return  arrayList;
//    }
//}