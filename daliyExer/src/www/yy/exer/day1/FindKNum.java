package www.yy.exer.day1;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :  寻找第K大元素
 * @Time : Created in 18:35 2019/5/23
 */
public class FindKNum {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,7,4,9};
        Scanner scanner = new Scanner(System.in);
        while(true) {
            int k = scanner.nextInt();
            System.out.println(fun(arr,k));
        }
    }
    public static int fun(int[] arr,int k) {
        int length = arr.length;
        //下面函数的参数为k-1，因为我们把数组按降序排序，第1大就是下标对应为0的元素，所以是k-1；
        return findK(arr,0,length-1,k-1);
    }
    public static int findK(int[] arr, int l, int r, int k) {

        int par = partion(arr,l,r);
        if(k == par) {
            return arr[par];
        }else if(par < k) {
            return findK(arr,par+1,r,k);

        }else {
            return findK(arr,l,par-1,k);
        }
    }

    public static int partion(int[] arr, int l, int r) {
        int val = arr[l];
        int i = l+1;
        int j = l;          //[j+1,i]是小于val的区间
        for(; i<=r; i++) {
            if(arr[i] > val) {   //这里是降序排序
                swap(arr,i,j+1);
                j++;
            }
        }
        swap(arr,l,j);
        return j;
    }
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
