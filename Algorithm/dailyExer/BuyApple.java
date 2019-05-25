package www.yy.exer.day4;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description : 链接：https://www.nowcoder.com/questionTerminal/61cfbb2e62104bc8aa3da5d44d38a6ef
 * 小易去附近的商店买苹果，奸诈的商贩使用了捆绑交易，只提供6个每袋和8个每袋的包装(包装不可拆分)。 可是小易
 * 现在只想购买恰好n个苹果，小易想购买尽量少的袋数方便携带。如果不能购买恰好n个苹果，小易将不会购买。
 * 输入：买苹果个数
 * 输出：最少的袋子个数
 * @Time : Created in 14:21 2019/5/25
 */
public class BuyApple {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int appleNum = scanner.nextInt();
        System.out.println(numOfpackges(appleNum));
    }
    public static int numOfpackges(int appleNum) {
        int e = appleNum/8;
        for(int i=e; i>=0; i--) {
            int yu = appleNum-i*8;
            if(yu%6 == 0) {
                return i+ yu/6;
            }
        }
        if(appleNum%6 == 0) {
            return appleNum/6;
        }
        return -1;
    }
}

//解法二：回溯加数字分析
//import java.util.*;
//public class Main{
//    public static void main(String args[]) {
//        Scanner in = new Scanner(System.in);
//        while(in.hasNextInt()){
//            int n = in.nextInt();
//            System.out.println(count(n));
//        }
//    }
//    public static int count(int n){
//        if(n%2!=0||n==10||n<6)
//            return -1;
//        if(n%8==0)
//            return n/8;
//        return 1+n/8;
//    }
//}