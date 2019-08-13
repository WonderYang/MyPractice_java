package www.yy.review;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :给定一个数组和一个K，找出数组中最小的K个数
 * @Time : Created in 11:24 2019/8/13
 */
public class FindMinK {
    public static void main(String[] args) {
        int[] arr = new int[]{1,6,2,10,-1,7,3,15,8};
        int k = 7;
        int[] res = GetLeastNumbers_Solution(arr, k);
        for(int i=0; i<res.length; i++) {
            System.out.print(res[i]+" ");
        }
    }
    public static int[] GetLeastNumbers_Solution(int [] input, int k) {
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

        return arr;
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
