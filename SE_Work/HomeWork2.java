//1.创建一个Test类，包含有一个public权限的int型成员变量与一个char类型的成员变量，观察在main方法中的初始值。 
// class Test{
//     public int a;
//     public char b;
// }

// public class HomeWork2{
//     public static void main(String[] args){
//         Test test = new Test();
//         System.out.println(test.a);   //输出0
//         System.out.println(test.b);   //输出空格
//     }
// }

//2.编写一个程序，展示无论你创建了某个特定类的多少个对象，这个类的某个特定的static成员变量只有一个属性。
// class Person {
//     static String country = "China";
//     private String name;
//     public void setName(String name) {
//         this.name = name;
//     }
// }

// public class HomeWork2 {
//     public static void main(String[] args) {
//         Person per1 = new Person();
//         Person per2 = new Person();
//         System.out.println(per1.country);
//         System.out.println(per2.country);
//     }
// }

//3.一个斐波那契数列是由数字1、1、2、3、5、8、13、21、34等等组成的，其中每一个数字(从第三个数字起)都是前两个数字的和。
//创建一个方法，接受一个整数参数，并显示从第一个元素开始总共由该参数指定的个数所构成的所有斐波那契数字。
//例如，如果运行 java Fibonacci 5(Fibonacci为类名)，那么输出应该是1、1、2、3、5。 
// public class HomeWork2 {
//     public static void main(String[] args) {
//         //System.out.println(args[0]);
//         fibonacci(5);

//     }
//     public static void fibonacci(int x) {
//         int former = 1;
//         int latter = 1;
//         int fib = 1;
//         System.out.print(fib+"、");
//         while(fib <= x){
//             System.out.print(fib+"、");
//             former = latter;
//             latter = fib;
//             fib = former + latter;
//         }
//     }
// }


//4.创建一个带默认构造方法（即无参构造）的类，在构造方法中打印一条消息"Hello Constructor";再为这个类添加一个重载构造方法，
//令其接收一个字符串参数，并在这个有参构造方法中把"Hello Constructor"和接收的参数一起打印出来。 
// class Print {
//     public Print() {
//         System.out.println("Hello Constructor");
//     }
//     public Print(String str) {
//         System.out.println("Hello Constuctor " + str);
//     }
// }
// public class HomeWork2 {
//     public static void main(String[] args) {
//         Print print = new Print();
//         Print print2 = new Print("lalal");
//     }
// }