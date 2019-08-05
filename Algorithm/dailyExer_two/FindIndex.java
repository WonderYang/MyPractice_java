package www.yy.day27;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :输入一个数n，然后输入n个数值各不相同，再输入一个值x，输出这个值在这个数组中的下标（从0开始，若不在
 * 数组中则输出-1）。
 * @Time : Created in 14:44 2019/8/5
 */
public class FindIndex {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int[] arr = new int[x];
        for(int i=0; i<x; i++) {
            int num = scanner.nextInt();
            arr[i] = num;
        }
        int res = scanner.nextInt();
        for(int i=0; i<x; i++) {
            if(res == arr[i]) {
                System.out.println(i);
                return;
            }
        }
        System.out.println("-1");;
    }
}
