package www.yy.exer.day2;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :有这样一道智力题：“某商店规定：三个空汽水瓶可以换一瓶汽水。小张手上有十个空汽水瓶，她最多可以换
 * 多少瓶汽水喝？”答案是5瓶，方法如下：先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3
 * 个再换一瓶，喝掉这瓶满的，这时候剩2个空瓶子。然后你让老板先借给你一瓶汽水，喝掉这瓶满的，喝完以
 * 后用3个空瓶子换一瓶满的还给老板。如果小张手上有n个空汽水瓶，最多可以换多少瓶汽水喝？
 * @Time : Created in 16:38 2019/5/22
 */
public class Num1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = new int[10];
        int i = 0;
        while(scanner.hasNextInt()) {
            int data = scanner.nextInt();
            if(data == 0) {
                break;
            }
            arr[i++] = data;
        }
        for(int j=0; j<i; j++) {
            int num = arr[j];
            int res = 0;
            int he = 0;
            int yu = 0;
            while(num >= 3) {
                he = num/3;
                yu = num%3;
                num = he+yu;
                res += he;
            }
            if(num == 2) {
                System.out.println(res+1);
            }else {
                System.out.println(res);
            }

        }

    }
}
