package www.yy.exer;

import org.omg.PortableServer.ForwardRequest;
import www.yy.MyQueue.PriorityQueue;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 22:52 2019/10/9
 */
public class FindKLargest {
    public static void main(String[] args) {
        int add[] = new int[]{1,2,3,4,5,6,1,7,8,9};
        System.out.println(findKthLargest(add,4));
        for(int x:add) {
            System.out.print(x+" ");
        }
    }
    public static int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        return quickSort(nums, 0, len-1, k-1);
    }
    public static int quickSort(int[] arr, int left, int right, int k) {
        int par = partion(arr, left, right);
        if(par == k) {
            return arr[par];
        }else if(k > par) {
            return quickSort(arr, par+1, right, k);
        }else {
            return quickSort(arr, left, par-1, k);
        }

    }

    public static int partion(int[] arr, int left, int right) {
        int temp = arr[left];
        int j = left;
        int i = j+1;
        for(; i <= right; i++) {
            if(arr[i] >= temp) {
                swap(arr, i, j+1);
                j++;
            }
        }
        swap(arr, j, left);
        return j;
    }
    public static void swap(int[] arr, int indexA, int indexB) {
        int temp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = temp;

    }
}
