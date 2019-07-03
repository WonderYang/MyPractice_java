package www.yy.exer.day14;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/86ede762b450404dbab59352963378e9
 * 来源：牛客网
 *
 * 给定数字0-9各若干个。你可以以任意顺序排列这些数字，但必须全部使用。目标是使得最后得到的数尽可能小（注意0不能做首位）。例如：
 *
 * 给定两个0，两个1，三个5，一个8，我们得到的最小的数就是10015558。
 * @Time : Created in 19:30 2019/6/25
 */
public class MinNum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] arr = s.split(" ");
        int[] arrNum = new int[10];
        for(int i=0; i<10; i++) {
            arrNum[i] = Integer.parseInt(arr[i]);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<10; i++) {
            while(arrNum[i] > 0) {
                stringBuilder.append(i);
                arrNum[i]--;
            }
        }
        if(stringBuilder.charAt(0) == '0') {
            for(int i=1; i<stringBuilder.length(); i++) {
                if(stringBuilder.charAt(i)!='0') {
                    stringBuilder.deleteCharAt(0);
                    stringBuilder.insert(0,stringBuilder.charAt(i));
                    stringBuilder.deleteCharAt(i);
                    stringBuilder.insert(i, '0');
                    break;
                }
            }
        }
        System.out.println(stringBuilder);
    }
}
