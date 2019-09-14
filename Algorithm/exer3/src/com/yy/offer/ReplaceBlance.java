package com.yy.offer;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 22:06 2019/9/10
 */
public class ReplaceBlance {
    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer("he ll s ");
        //System.out.println(replaceSpace(stringBuffer));

        StringBuffer stringBuffer1 = new StringBuffer("hello word");
        stringBuffer1.replace(1,3,"lala");
        System.out.println(stringBuffer1);
    }
    public static String replaceSpace(StringBuffer str) {
        return str.replace(0,str.length(),"s").toString();
//        String s = str.toString();
//        return s.replaceAll(" ","%20");
    }
}
