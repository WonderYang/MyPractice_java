package www.yy.exer.day18;

/**
 * @Author : YangY
 * @Description : 链接：https://www.nowcoder.com/questionTerminal/9fe25b6cf93e46dcb09ba67aeef2c4cc

 * 一个百万富翁遇到一个陌生人，陌生人找他谈了一个换钱的计划。该计划如下：我每天给你10 万元，你第一天给我1 分钱，第二天2 分钱，
 * 第三天4 分钱……
 * 这样交换 30 天后，百万富翁交出了多少钱？陌生人交出了多少钱？（注意一个是万元，一个是分）
 *
 * 输入描述:
 * 该题没有输入

 * 输出描述:
 * 输出两个整数，分别代表百万富翁交出的钱和陌生人交出的钱，富翁交出的钱以万元作单位，陌生人交出的钱以分作单位。
 * @Time : Created in 18:04 2019/7/12
 */
public class Millionaire {
    public static void main(String[] args) {
        int sum = 0;
        for(int i=1; i<=30; i++) {
            sum += Math.pow(2,i-1);
        }
        System.out.println(30);
        System.out.println(sum);
    }
}
