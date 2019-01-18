package www.alg.java;
import java.lang.Math;
//问题描述
//        　　对一个数十进制表示时的每一位数字乘五次方再求和，会得到一个数的五次方数
//        　　例如：1024的五次方数为1+0+32+1024=1057
//        　　有这样一些神奇的数，它的五次方数就是它自己，而且这样的数竟然只有有限多个
//        　　从小到大输出所有这样的数
//        输出格式
//        　　每个数独立一行输出
public class Test2 {
    public static void main(String[] args) {
        //System.out.println(Math.pow(9,5));
        for(int x = 0; x < 400000; x++) {   //400000，超过这个值它的每个位的五次方恒小于它自己，所以不可能相等，这个可以解方程组而来；
            if(isSame(x)) {
                System.out.println(x);
            }
        }
    }
    public static boolean isSame(int x) {
        int temp = 0;
        int sum = 0;
        int res = x; //保存x的值
        while(x>0) {
            temp = x % 10;
            x /= 10;
            sum += Math.pow(temp, 5);  //返回的sum是一个整数，不是double型
        }
        if(sum == res) {
            return true;
        }else {
            return false;
        }
    }
}
