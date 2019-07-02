package www.yy.exer.day15;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/e7e0d226f1e84ba7ab8b28efc6e1aebc
 * 来源：牛客网
 *
 * 二进制加法。发现一个特点。
 * 位的异或运算跟求'和'的结果一致：
 * 异或 1^1=0 1^0=1 0^0=0
 * 求和 1+1=0 1+0=1 0+0=0
 * 位的与运算跟求'进位‘的结果一致：
 * 位与 1&1=1 1&0=0 0&0=0
 * 进位 1+1=1 1+0=0 0+0=0
 * 于是可以用异或运算和与运算来表示加法
 * @Time : Created in 15:29 2019/7/2
 */
public class Add {
    public static void main(String[] args) {
        System.out.println(addAB(23,5));
    }
    public static int addAB(int A, int B) {
        while(B != 0) {
            int res = A^B;     //相加的结果，但没有加上进位
            int jinwei = (A&B)<<1;    //进位,进位是加到上一位，所以需要左移
            A = res;
            B = jinwei;
        }
        return A;
    }

}
