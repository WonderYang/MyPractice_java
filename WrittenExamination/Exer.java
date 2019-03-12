package www.www.niuke.java;

/**
 * @Author : YangY
 * @Description : 对象实例化的例子
 *解析：分为两块，new B() 和 new B().getValue()。
 * 输出： 22
 *       34
 *       17
 * @Time : Created in 20:09 2019/3/12
 */

public class Exer {
    public static void main(String[] args) {
        System.out.println(new B().getValue());
    }
    static class A {
        protected int value;
        public A (int v) {
            setValue(v);
        }
        public void setValue(int value) {
            this.value= value;
        }
        public int getValue() {
            try {
                value ++;
                return value;
            } finally {              //无论怎样都要执行finally块，执行前，将try中的value值保留，等执行完，再return
                this.setValue(value);
                System.out.println(value);
            }
        }
    }
    static class B extends A {
        public B () {
            super(5);
            setValue(getValue()- 3);
        }
        public void setValue(int value) {
            super.setValue(2 * value);
        }
    }
}

/*
* new B()的时候，我们知道，实例化对象先实例化父类对象（执行父类构造再执行子类构造），为啥要这样，其实是子类
* 的构造中首先会执行super（）语句来调用父类构造，以往父类都是无参构造构造，所以子类的构造中可以省略super语句，
* 但在此类中，父类为有参构造，则不能省略；
* 执行super（5），来到父类的构造，执行setValue(5)，那麽这个setValue是父类的方法还是子类覆写后的呢？我们记住，
* 此时我们正在创建子类的对象，则调用的都是子类覆写后的方法，只有明确用super关键字时，才会调用父类方法；
*
*再try 、finally 语块时，无论怎样，finally语块都会执行，try中的return语句会保留他的返回值，等到finally语句执行完
* 后再返回去执行try中的return语句
* */