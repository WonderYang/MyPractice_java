package www.yy.exer.day16;

import java.util.Date;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 14:10 2019/7/6
 */
interface Hello {

}
class HelloImp implements Hello {

}
public class Test {
    public static void main(String[] args) {
        Hello hello = null;
        Hello hello1 = new HelloImp();
        boolean s = hello instanceof Object;
        boolean s1 = hello1 instanceof Hello;
        System.out.println(s);   //flase,
        System.out.println(s1);   //true
    }
}
