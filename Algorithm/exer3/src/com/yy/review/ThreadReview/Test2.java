package com.yy.review.ThreadReview;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:41 2019/9/13
 */
public class Test2 {
    public static void main(String[] args) {
        new Thread(()-> System.out.println(Thread.currentThread().getName()+" :hello")).start();
        new Thread(()-> System.out.println(Thread.currentThread().getName()+" :hello")).start();
    }
}
