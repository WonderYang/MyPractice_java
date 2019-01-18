package www.alg.java;
import java.lang.Math;
//问题描述
//        　　勾股数是一组三个自然数，a < b < c，以这三个数为三角形的三条边能够形成一个直角三角形
//        　　输出所有a + b + c <= 1000的勾股数
//        　　a小的先输出；a相同的，b小的先输出。
//        输出格式
//        　　每行为一组勾股数，用空格隔开
public class Test1 {
    public static void main(String[] args) {
        int a = 0;
        int b = 0;
        int c = 0;
        for (a = 3; a < 500; a++) {
            for (b = 4; b < 500; b++) {
                int m = a*a + b*b;
                double sqrt = Math.sqrt(m);
                if (is_same(sqrt)) {
                    c = (int)sqrt;
                    System.out.println(a+"、"+b+"、"+c);
                }
            }
        }
    }
    static boolean is_same(double x) {
        int z = (int)x;
        double res = z;
        if (res == x) {
            return true;
        }else {
            return false;
        }
    }
}
