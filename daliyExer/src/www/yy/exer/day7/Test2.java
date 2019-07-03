package www.yy.exer.day7;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:35 2019/5/29
 */
public class Test2 {
    static int cout = 6;
    static {
        cout += 9;
    }
    public static void main(String[] args) {
        System.out.println(cout);
    }
    static {
        cout/=3;
    }
}
