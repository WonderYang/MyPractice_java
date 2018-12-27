class A{
    static{
        System.out.println("1.A类的静态块");
    }
    {
        System.out.println("2.A类的构造块");
    }
    public A(){
        System.out.println("3.A类的构造方法");
    }
}

public class Test1 extends A{
    static{
        System.out.println("4.B类的静态块");
    }
    {
        System.out.println("5.B类的构造块");
    }
    public Test1(){
        System.out.println("6.B类的构造方法");
    }

    public static void main(String[] args){
        System.out.println("7.主方法开始");
        new Test1();
        new Test1();
        System.out.println("8.主方法结束");
    }
}

//输出结果
//1 4 7   2 3 5 6   2 3 5 6   8
//先加载类，加载类时调用静态代码块，而子类加载时先加载父类；
//静态代码块只在类加载时调用一次