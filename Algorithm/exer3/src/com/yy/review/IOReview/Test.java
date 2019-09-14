package com.yy.review.IOReview;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 17:48 2019/9/11
 */
public class Test {
    public static void main(String[] args) {

        HashMap<Integer, Integer> map = new HashMap<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("dfcs");
            }
        }).start();
        new Thread(()-> System.out.println("jvjd"));


    }
}
