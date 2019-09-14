package com.yy.offer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 15:39 2019/9/14
 */
interface A {
    void fun1();
}
class B implements A {
    @Override
    public void fun1() {
        System.out.println("lala");
    }
    public void fun2() {
        System.out.println("cds");
    }
}
public class MaxLength2 {
    public static void main(String[] args) {
        HashMap<Character,Integer> map = new HashMap<>();
    }
}
