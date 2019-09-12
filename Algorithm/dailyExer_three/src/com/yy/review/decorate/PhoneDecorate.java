package com.yy.review.decorate;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 20:23 2019/9/11
 */
public abstract class PhoneDecorate implements Phone{
    private Phone phone;
    public PhoneDecorate(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void call() {
        phone.call();
    }
}
