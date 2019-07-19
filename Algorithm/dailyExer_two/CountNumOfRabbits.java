package www.yy.day21;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :有一只兔子，从出生后第3个月起每个月都生一只兔子，小兔子长到第三个月后每个月又生一只兔子，
 * 假如兔子都不死，问每个月的兔子总数为多少？
 * @Time : Created in 20:45 2019/7/18
 */
public class CountNumOfRabbits {
    public static void main(String[] args) {
        //就是一个斐波那契数列
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt()) {
            int n = scanner.nextInt();
            int res = 0;
            if(n==1 || n==2) {
                res = 1;
            }
            n = n-2;
            int q = 1;
            int h = 1;
            while(n-- > 0) {
                res = q+h;
                q = h;
                h = res;
            }
            System.out.println(res);
        }

    }
}
