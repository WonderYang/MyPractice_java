package www.yy.exer.day8;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 17:23 2019/5/29
 */
public class FindMinK {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = null;
        if (scanner.hasNextLine()) {
            str = scanner.nextLine();
        }
        String[] arr = str.split(" ");
        int[] arrNum = new int[arr.length-1];
        for(int i=0; i<arr.length-1; i++) {
            arrNum[i] = Integer.parseInt(arr[i]);
        }
        int k = Integer.parseInt(arr[arr.length-1]);
        minK(arrNum,k);

    }
    public static void minK(int[] arr, int k) {
        for(int i=0; i<k; i++) {
            //注意这里的j每回都必须走到底
            for(int j=i+1; j<arr.length; j++) {
                if(arr[j] <arr[i]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            System.out.print(arr[i]+" ");
        }
    }
}
