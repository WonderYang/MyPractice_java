package www.yy.exer.day16;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/987123efea5f43709f31ad79a318ca69
 *     读入N名学生的成绩，将获得某一给定分数的学生人数输出。
 *
 * 输入描述:
 * 测试输入包含若干测试用例，每个测试用例的格式为
 *
 * 第1行：N
 * 第2行：N名学生的成绩，相邻两数字用一个空格间隔。
 * 第3行：给定分数
 *
 * 当读到N=0时输入结束。其中N不超过1000，成绩分数为（包含）0到100之间的一个整数。
 *
 * 输出描述:
 * 对每个测试用例，将获得给定分数的学生人数输出。
 * 示例1
 * 输入
 * 3
 * 80 60 90
 * 60
 * 2
 * 85 66
 * 0
 * 5
 * 60 75 90 55 75
 * 75
 * 0
 * 输出
 * 1
 * 0
 * 2
 * @Time : Created in 21:45 2019/7/7
 */
public class CountGrade {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] res = new int[1000];
        StringBuilder s = new StringBuilder();
        int j = 0;
        while(scanner.hasNext()) {
            int num = scanner.nextInt();
            if(num == 0) {
                break;
            }
            int[] arr = new int[101];
            int i = 0;
            while(num-- > 0) {
                arr[scanner.nextInt()]++;
            }
            int aim = scanner.nextInt();
            s.append(arr[aim]);
        }
        for(int x =0; x<s.length();x++) {
            System.out.println(s.toString().charAt(x));
        }

    }
}
