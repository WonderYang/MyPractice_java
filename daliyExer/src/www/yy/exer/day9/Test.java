package www.yy.exer.day9;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 12:21 2019/5/30
 */
public class Test {
    public static void fun() {
        System.out.println("lalal");
    }
    public static void main(String[] args) {
        ((Test)null).fun();     // 注意加括号
    }
}
