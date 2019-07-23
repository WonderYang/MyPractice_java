package www.yy.day22;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/2f13c507654b4f878b703cfbb5cdf3a5
 * 来源：牛客网
 *
 * 小红想买些珠子做一串自己喜欢的珠串。卖珠子的摊主有很多串五颜六色的珠串，但是不肯把任何一串拆散了卖。于是
 * 小红要你帮忙判断一下，某串珠子里是否包含了全部自己想要的珠子？如果是，那么告诉她有多少多余的珠子；如果不是
 * ，那么告诉她缺了多少珠子。
 * 为方便起见，我们用[0-9]、[a-z]、[A-Z]范围内的字符来表示颜色。例如，YrR8RrY是小红想做的珠串；那么ppRYYGrrYBR2258可以买，因为包含了
 * 全部她想要的珠子，还多了8颗不需要的珠子；ppRYYGrrYB225不能买，因为没有黑色珠子，并且少了一颗红色的珠子。
 *
 * 输入描述:
 * 每个输入包含1个测试用例。每个测试用例分别在2行中先后给出摊主的珠串和小红想做的珠串，两串都不超过1000个珠子。
 * 输出描述:
 * 如果可以买，则在一行中输出“Yes”以及有多少多余的珠子；如果不可以买，则在一行中输出“No”以及缺了多少珠子。其间以1个空格分隔。
 * 示例1
 * 输入
 * ppRYYGrrYBR2258
 * YrR8RrY
 * 输出
 * Yes 8
 * @Time : Created in 15:20 2019/7/19
 */
public class BuyOrNot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //src代表老板的珠子
        String src = scanner.nextLine();
        int[] arr = new int[128];
        int i = 0;
        while(i < src.length()) {
            arr[src.charAt(i)]++;
            i++;
        }
        String des = scanner.nextLine();
        int j = 0;
        boolean flag = false;
        int lack = 0;
        while(j < des.length()) {
            if(arr[des.charAt(j)] > 0) {
                arr[des.charAt(j)]--;
            }else {
                flag = true;
                lack++;
            }
            j++;
        }
        if(flag) {
            System.out.println("No "+lack);
        }else {
            int sum = src.length()-des.length();
            System.out.println("Yes "+sum);
        }
    }
}
