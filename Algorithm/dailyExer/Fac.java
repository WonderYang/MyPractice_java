package www.yy.exer.day9;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :输入一个正整数n,求n!(即阶乘)末尾有多少个0？ 比如: n = 10; n! = 3628800,所以答案为2
 * 不要想到把阶乘结果算出来，再来求末尾零的个数，输入的数范围为1~1000，1000的阶乘那么大。。。怎么算啊
 * 所以我们得找规律，那么我们在计算n的阶乘时，实际上就是把所有小于等于n的正整数分解成质因数，然后再将其
 * 乘到一起，那么末尾0的个数实际上就是2*5的个数，而2的个数明显是很多很多的，所以问题就转化成了5的个数。
 * 而只有5的倍数才有5这个因数，所以，问题就进一步简化为小于等于n的数中有多少个数是5的倍数，当然25
 *的倍数，125的倍数，625还要单独考虑；
 * @Time : Created in 13:11 2019/5/30
 */
public class Fac {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt()) {   //多次测试数据
            int n = scanner.nextInt();
            int count = 0;
            while(n >= 5) {
                int temp = n;
                while(temp % 5 == 0) {
                    count++;
                    temp /= 5;
                }
                n--;
            }
            System.out.println(count);
        }
    }
}
