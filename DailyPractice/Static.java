// class Person{
//     //普通属性--对象属性
//     private String name;
//     private int age;
//     //类属性--共享属性
//     public static String country;  //都共享了，所以用public来修饰

//     public Person(String name, int age){
//         this.name = name;
//         this.age = age;
//     }

//     public void fun(){
//         System.out.println(name + age + "岁在" + country);
//     }
//     //static方法，描述工具方法时，一般使用静态方法
//     public static void put(){
//         //静态方法不能调用类中普通属性；  但普通方法能调用类中静态属性；
//         //System.out.println(name);
//         System.out.println("******************");
//     }
// }

// public class Static{
//     public static void main(String[] args){
//         Person per1 = new Person("yy", 15);
//         Person.country = "中国";   //static修饰的属性直接用类名称调用
//         per1.fun();
//         Person.put();

//     }
// }



// ***************************代码块****************************
class Person{
    public Person(){
        System.out.println("Person类的构造方法");
    }

    //构造块(定义在类中的代码块)   (构造块的会优先于构造方法执行，作用是完成类中普通属性的初始化)
    {
        System.out.println("Person类的构造块！！");
    }

    //静态代码块
    //优先于构造块执行，并且无论有多少实例化对象产生，静态块只在类加载时执行一次⭐
    static{
        System.out.println("静态代码块");
    }
}

public class Static{
    public static void main(String[] args){
        // //普通代码块
        // {
        //     int x = 10;
        //     System.out.println(x);
        // }  //这里x就被销毁了
        // int x = 20;
        // System.out.println(x);

        // //构造代码块
        // Person per1 = new Person();
        // Person per2 = new Person();

        // //类中的静态块
        // //下面没产生对象，但有类加载，此时会执行静态块
        // new Person();
        
        //主类构造块的例子
        //必须在主类实例化对象后才可以执行
        Static static1 = new Static();


    }

    //主类中的构造块⭐
    //必须在主类实例化对象后才可以执行
    {
        System.out.println("主类中的构造块");
    }


         //主类中的静态块 ,优先于主方法执行⭐
        static{
            System.out.println("主类中的静态块");
        }
}


