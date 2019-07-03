package www.yy.exer.day14;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :验证尼科彻斯定理，即：任何一个整数m的立方都可以写成m个连续奇数之和。
 *
 * 例如：
 * 1^3=1
 * 2^3=3+5
 * 3^3=7+9+11
 * 4^3=13+15+17+19
 *  输入描述:
 *输入一个int整数

 *输出描述:
 *输出分解后的string
 * @Time : Created in 16:00 2019/6/29
 */
public class Nikechesi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextInt()) {
            int x = scanner.nextInt();
            int start = x*x - (x-1);
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0; i<x; i++) {
                stringBuilder.append(start);
                if(i<x-1) {
                    stringBuilder.append('+');
                }
                start += 2;
            }
            System.out.println(stringBuilder.toString());
        }
    }
}
