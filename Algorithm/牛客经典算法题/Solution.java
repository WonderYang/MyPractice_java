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
//第一种解法
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
