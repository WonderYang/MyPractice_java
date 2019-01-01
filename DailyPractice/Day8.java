//********************************抽象类**********************************
//1.有抽象方法的类必须是抽象类,但抽象类不一定必须有抽象方法；
//2.abstract与final一定不能同时出现，不然编译出错，因为final修饰的类没有子类，而抽象类必须有子类
//3.抽象类的子类（前提：不是抽象类）必须覆写抽象类的所有抽象方法；
// abstract class Person{    //抽象类
//     //抽象方法
//     public abstract void print();
//     public Person(){
//         System.out.println("抽象类的构造方法！");
//     }

// }
// class Student extends Person{
//     public  void print(){  //看第三点，它的子类不是抽象类时，必须覆写抽象类的所有抽象方法；
//         System.out.println("hello student");
//     }
//     public Student(){
//         System.out.println("子类的构造方法");
//     }
// }
// public class Day8{
//     public static void main(String[] args){
//         //Person per = new Person();      error，因为抽象类是个半成品，无法直接产生实例化对象;
//         //抽象类可以使用子类向上转型为其实例化。抽象类一定不能直接实例化对象（无论是否有抽象方法）
//         Person per = new Student();   //向上转型为其产生实例化对象
//         per.print();
//     }
// }


//典例
// abstract class Person{  
//     public Person(){    //2
//         this.print();  //3
//     }
//     public abstract void print();  //3
// }
// class Student extends Person{
//     private int num = 100;
//     public Student(int num){  // 1
//         this.num = num;
//     }
//     public void print(){   //4
//         System.out.println(this.num);  //5
//     }
// }
// public class Day8{
//     public static void main(String[] args){
//         new Student(30); // 1                          //输出0⭐
//     }
// }