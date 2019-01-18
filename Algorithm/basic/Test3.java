package www.alg.java;
import java.util.Scanner;

// 算法提高 素数求和
//         问题描述
//         　　输入一个自然数n，求小于等于n的素数之和
//         样例输入
//         2
//         样例输出
//         2
public class Test3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int sum = 0;
        for(int i = 2; i <= x; i++) {
            if(isPrimeNumber(i)) {
                sum += i;
            }
        }
        System.out.println(sum);
    }
    public static boolean isPrimeNumber(int x) {
        int i = 0;
        int sq = (int)Math.sqrt(x);
        for(i = 2; i <= sq; i++) {
            if(x % i == 0) {
                break;
            }
        }
        if(i > sq) {    //坑，这里必须是>,不能是>=;
            return true;
        }else {
            return false;
        }
    }
}
