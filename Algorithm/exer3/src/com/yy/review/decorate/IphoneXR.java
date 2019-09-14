package com.yy.review.decorate;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:17 2019/9/11
 */
public class IphoneXR implements Phone {
    @Override
    public void call() {
        System.out.println("给某人打电话......");
    }
}
