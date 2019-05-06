package www.yy;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 18:51 2019/5/5
 */
/*
饿汉式
* */
//class Singleleton {
//    private static final Singleleton single = new Singleleton();
//    private Singleleton() { }
//    public static Singleleton getSingle(){
//        return single;
//    }
//}

/*
* 懒汉式多线程模式（一）
* */
//class Singleleton {
//    private static volatile Singleleton single = null;
//    private Singleleton() { }
//    public static Singleleton getSingle(){
//        synchronized (Singleleton.class) {
//            if(single == null) {
//                single = new Singleleton();
//            }
//        }
//        return single;
//    }
//}

/**
 * 双重检验锁模式
 */
//class Singleleton {
//    private static volatile Singleleton single = null;
//    private Singleleton() { }
//    public static Singleleton getSingle(){
//        if(single == null) {
//            synchronized (Singleleton.class) {
//                if(single == null) {
//                    single = new Singleleton();
//                }
//            }
//        }
//        return single;
//    }
//}

/**
 * 静态内部类式
 */
//class Singleleton {
//    private static class Inner {
//        static Singleleton single = new Singleleton();
//    }
//    private Singleleton() { }
//    public static Singleleton getSingle(){
//        return Inner.single;
//    }
//}

/**
 * 枚举方式
 */
enum Singleleton {
    INSTANCE;
    private String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
public class Single {
    public static void main(String[] args) {
        Singleleton singleleton = Singleleton.INSTANCE;
        singleleton.setName("yy");
        System.out.println(singleleton.getName());
    }

}
