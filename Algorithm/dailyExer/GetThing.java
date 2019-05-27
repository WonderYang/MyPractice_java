package www.yy.exer.day5;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/9aaea0b82623466a8b29a9f1a00b5d35

 * 有一个神奇的口袋，总的容积是40，用这个口袋可以变出一些物品，这些物品的总体积必须是40。John现在有n个想要
 * 得到的物品，每个物品的体积分别是a1，a2……an。John可以从这些物品中选择一些，如果选出的物体的总体积是40，
 * 那么利用这个神奇的口袋，John就可以得到这些物品。现在的问题是，John有多少种不同的选择物品的方式。
 *
 * 输入描述:
 * 输入的第一行是正整数n (1 <= n <= 20)，表示不同的物品的数目。接下来的n行，每行有一个1到40之间的正整数，
 * 分别给出a1，a2……an的值。
 * 输出描述:
 * 输出不同的选择物品的方式的数目。
 * @Time : Created in 23:04 2019/5/25
 */
public class GetThing {
    public static int count;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int num = x;  //保留x的值，因为下面的循环输入后x值会减为零
        int[] arr = new int[20];
        int i = 1;
        while(x>0) {
            arr[i++] = scanner.nextInt();
            x--;
        }
        fun(arr,40,num);
        System.out.println(count);
    }
    public static void fun(int[] arr,int n,int num) {
        if(n==0) {
            count++;
            return;
        }else if(n<0||(n>0&&num<1)) {
            return;
        }
        fun(arr,n-arr[num],num-1);
        fun(arr,n,num-1);
    }
}
