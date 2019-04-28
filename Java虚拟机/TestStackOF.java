package com.yy.jvm;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 17:14 2019/4/28
 */
public class TestStackOF {
    private int stackDeep = 1;
    public void setDeep() {
        stackDeep++;
        setDeep();
    }

    //-Xss100k(自己设置的参数)，这个参数用来更改栈的深度；
    public static void main(String[] args) {
        TestStackOF testStackOF = new TestStackOF();
        try {
            testStackOF.setDeep();
        }catch (Throwable e) {  //将要抛出的是error，所以这里不是Exception，而是他们的父类Throwable
            e.printStackTrace();
            System.out.println("Call deep:" + testStackOF.stackDeep);
        }
    }
}
