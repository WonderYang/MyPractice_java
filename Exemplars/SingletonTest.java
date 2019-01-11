//  ******************                  饿汉式单例                 *********************************
//在类加载时就产生了对象（因为为static变量和方法嘛）⭐
class Singleton {
    //这里必须是static变量，因为下面的返回方法是静态方法，静态方法必须调用静态变量，不能调用非静态变量;
    private static Singleton singleton = new Singleton();
    //把构造方法私有化，让根源上外部不能产生对象；
    private Singleton() {}
    //对象获取方法
    public static Singleton getInstance() {
        return singleton;    //这里不能是this.singleton,因为为static变量
    }
}
public class SingletonTest {
    public static void main(String[] args) {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        if(singleton1 == singleton2) {
            System.out.println("无论多少调取获得对象方法，所有获得的对象都是同一个");   //将会输出这
        }
        else {
            System.out.println("获取的对象不一样");
        }
    }
}


// 饿汉和懒汉区别：饿汉上来就new，懒汉式后面再new
//懒汉式存在线程安全问题，在多线程并发下可能会产生不止一个对象，所以一般写饿汉式

// //  ******************                  懒汉式单例                 *********************************
// //
// class Singleton {
//     private static Singleton singleton;
//     //把构造方法私有化，让根源上外部不能产生对象；
//     private Singleton() {}
//     //对象获取方法
//     public static Singleton getInstance() {
//         if (singleton == null) {
//             singleton = new Singleton();
//         }
//         return singleton; 
//     }
// }
// public class SingletonTest {
//     public static void main(String[] args) {
//         Singleton singleton1 = Singleton.getInstance();
//         Singleton singleton2 = Singleton.getInstance();
//         if(singleton1 == singleton2) {
//             System.out.println("无论多少调取获得对象方法，所有获得的对象都是同一个");   //同样输出这
//         }
//         else {
//             System.out.println("获取的对象不一样");
//         }
//     }
// }