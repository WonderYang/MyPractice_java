package www.baidu.java;
/*
    下面这段代码表明了this的用法，表示当前对象；
    this一般都是使用在要表明当前对象时，一般不要乱用；
    this带参数的时候代表调用构造方法；
* */
class Person{
    public void eat(Apple apple) {
        Apple peeled = apple.getPeeled();
        System.out.println("yummy");
    }
}
class Peeler {
    static Apple peel(Apple apple) {
        return apple;
    }
}
class Apple {
    Apple getPeeled() {
        return Peeler.peel(this);   //⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐
    }
}
public class PassingThis {
    public static void main(String[] args) {
        new Person().eat(new Apple());
    }
}
