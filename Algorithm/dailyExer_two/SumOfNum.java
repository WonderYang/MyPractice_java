package www.yy.day26;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description : 对于给定的正整数 n，计算其十进制形式下所有位置数字之和，并计算其平方的各位数字之和。
 * @Time : Created in 15:58 2019/7/30
 */
public class SumOfNum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            int x = scanner.nextInt();
            int y = x*x;
            int sum1 = 0;
            int sum2 = 0;
            while(x > 0) {
                int temp = x%10;
                sum1 += temp;
                x /= 10;
            }
            while(y > 0) {
                int temp = y%10;
                sum2 += temp;
                y /= 10;
            }
            System.out.print(sum1);
            System.out.println(" "+sum2);
        }
    }
}
