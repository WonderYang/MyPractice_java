package com.bittech.com.bittech.pc;

/**
 * @Author : YangY
 * @Description :  产品的定义
 * @Time : Created in 10:13 2019/4/21
 */
public class Goods {
    private String name;
    private double price;

    public Goods(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
