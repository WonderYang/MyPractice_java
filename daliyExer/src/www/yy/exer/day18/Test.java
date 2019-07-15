package www.yy.exer.day18;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 15:30 2019/7/13
 */
public class Test {
    public static void main(String[] args) {
        Integer I1 = new Integer(53);
        int I2 = 53;
        Integer I3 = new Integer(53);
        System.out.println(I1==I2);  //true
        System.out.println(I1==I3); //false
        System.out.println(I2==I3);  //true
    }
}
