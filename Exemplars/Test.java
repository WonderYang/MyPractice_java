package www.bitten.java;

/**
 * @Author : YangY
 * @Description :   翻转字符串的几种常用方法；
 * @Time : Created in 8:52 2019/3/17
 */
public class Test {
    public static void main(String[] args) {
        String str = "i love you";
        System.out.println(reverseStr1(str));
        System.out.println(reverseStr2(str));
    }
    //递归方式翻转
    public static String reverseStr1(String str) {
        if(str==null || str.length()<=1) {
            return str;
        }
        return reverseStr1(str.substring(1))+str.charAt(0);
    }
    //
    public static String reverseStr2(String str) {
        if(str==null || str.length()<=1) {
            return str;
        }
        char[] arr = str.toCharArray();
        for(int i=0,j=str.length()-1; i<j; i++,j--) {
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        Object obj = new Object();
        obj.toString();

        //这里不要return str.toString(); 这个方法返回的是当前对象的地址
        return new String(arr); //这个方法才是将数组转变为字符串，这是String类的一个构造方法；
    }
    
}
