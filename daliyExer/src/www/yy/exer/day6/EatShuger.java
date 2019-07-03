package www.yy.exer.day6;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 22:01 2019/5/26
 */
public class EatShuger {
    public static void main(String[] args) {
        int x,y,z,w;

        Scanner scanner = new Scanner(System.in);
        x = scanner.nextInt();   //遇到空格就结束
        y = scanner.nextInt();
        z = scanner.nextInt();
        w = scanner.nextInt();
        int a,b,c;
        a = (x+z) / 2;

        // 还有一种方式判断是不是整数，double a,b,c
        //a = (x+z)/2f
        //if（a-(x+z)/2 != 0）  则不是整数
        if(2*a != (x+z)) {     //通过这种方式来判断a是不是整数，如果不是则直接返回
            System.out.println("No");
            return;
        }
        b = a-x;
        int bNew = (y+w)/2;
        if(bNew*2 != (y+w)) {
            System.out.println("No");
            return;
        }
        if(b!= bNew) {
            System.out.println("No");
            return;
        }
        System.out.println(a+" "+b+" "+(w-b));

    }
}
