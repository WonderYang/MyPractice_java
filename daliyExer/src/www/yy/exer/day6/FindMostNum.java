package www.yy.exer.day6;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :  输入一行数字，每个数字之间用空格分开，最后输出这些数字中出现个数大于这些数字总和一半的数字
 * @Time : Created in 21:00 2019/5/26
 */
public class FindMostNum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        if(scanner.hasNext()) {
            String str = scanner.nextLine();
            String[] arrStr = str.split(" ");
            int[] arrNum = new int[arrStr.length];
            for(String s:arrStr) {
                arrNum[i++] = Integer.parseInt(s);
            }
            int Most = arrNum[0];
            int count = 0;
            for(int j=1; j<arrNum.length; j++) {
                if(arrNum[j] == Most) {
                    count++;
                }else if(count > 0) {
                    count--;
                }else {
                    Most = arrNum[j];
                }
            }
            System.out.println(Most);
        }

    }
}
