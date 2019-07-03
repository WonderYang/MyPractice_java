package www.yy.exer.day7;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :  求最小公倍数（关键是求出最大公因数）
 * @Time : Created in 22:35 2019/5/27
 */
public class LowestComMul {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int maxGongyinshu = 1;
        //求最大公因数，我的方法
//        int min = x>y?y:x;
//        for(int i=2; i<=min; i++) {
//            if(x%i==0 && y%i==0) {
//                maxGongyinshu = i;
//            }
//        }
        //递归方法，⭐
        maxGongyinshu = maxGongyinshu(x,y);
        int minMul = x*y/maxGongyinshu;
        System.out.println(minMul);
    }
    public static int maxGongyinshu(int x, int y) {
        if(y == 0) {
            return x;
        }else {
            return maxGongyinshu(y,x%y);
        }
    }
}
