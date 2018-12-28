//**************************     *内部类*      ***********************************

// class Outer{
//     private String msg = "i love you";

//     class Inner{
//         public void print(){
//             System.out.println(msg);   //这里不能写this.msg
//         }
//     }

//     public void fun(){
//         Inner in = new Inner();
//         in.print();
//     }
// }

// public class Day7{
//     public static void main(String[] args){
//         Outer out = new Outer();
//         out.fun();
//     }
// }


//将上述代码转换成两个类（以前的写法）
// class Outer{
//     private String msg = "i love you";
//     public String getMsg(){
//         return this.msg;
//     }
//     public void fun(){
//         Inner in = new Inner(this);
//         in.print();
//     }
// }

// class Inner{
//     private Outer out;
//     public Inner(Outer out){
//         this.out = out;
//     }
//     public void print(){
//         System.out.println(out.getMsg());
//     }
// }

// public class Day7{
//     public static void main(String[] args){
//         Outer out = new Outer();
//         out.fun();
//     }
// }

//**********************************    使用内部类实现“多继承”     *************************************
class A{
    private String name = "A的私有域";
}
