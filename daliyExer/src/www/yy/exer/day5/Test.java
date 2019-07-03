package www.yy.exer.day5;

import java.lang.reflect.Array;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 15:52 2019/5/25
 */
class S {
    String s = "good";

}
public class Test {
    String s = "good";
    public static void main(String[] args) {
//        int x,y;
//        x = 5>>2;
//        y = x>>>2;
//        System.out.println(5>>2);  //1
//        System.out.println(x);   //1
//        System.out.println(y);  //0

//        String s;
//        System.out.println("s="+s);  //编译报错，未初始化
        Test test = new Test();
        fun(test.s);
        System.out.println(test.s);

        System.out.println(test.s.hashCode());

        String ss = "hello";
        ss = "lalala";
        System.out.println(ss);
    }
    public static void fun(String str) {
        System.out.println(str.hashCode());
        str = "hahah";
    }

}
