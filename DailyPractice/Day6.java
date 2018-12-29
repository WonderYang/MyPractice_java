// class Person{
//     //private String name;
//     public String info = "dad";
//     public void fun(){
//         this.print();
//     }
//     public void print(){
//         System.out.println("1.Person类的print方法");
//     }
// }
// class Student extends Person{
//     //************属性覆写 **********
//     //子类定义了与父类同名的属性
//     public String info = "son";

//     //*******************            方法覆写          *********************************
//     //方法覆写发生在有继承关系的类之间，子类定义了与父类相同的方法（方法名相同、参数的个数、类型、返回值全都相同）
//     //被覆写的方法不能拥有比父类更为严格的访问控制权限；
//     //private（仅限本类访问）< default(包访问权限)(同一个文件下即可访问)(默认访问权限) < public
//     //方法覆写不能出现private权限⭐，出现了不报错但这不叫方法覆写（后面再谈原因）
//     public void print(){   //所以这里不能是private或者default权限
//         System.out.println("2.Student类的print方法");
//     }
// }

// public class Day6{
//     public static void main(String[] args){
//         Student stu = new Student();
//         // stu.print();      //将输出2，不会输出一，这是方法覆写的规则
//         // Person per = new Person();
//         // per.print();          //输出1；
//         stu.fun();   //将会输出2；

//         System.out.println(stu.info);    //输出son

//     }
// }



//*****************************super关键字*********************
// class Person{
//     private String name;
//     private int age;
//     public Person(String name){
//         this.name = name;
//     }
//     public Person(String name, int age){
//         this(name);   //this调用构造方法必须放在第一行，这里这样写是为了避免代码重复
//         this.age = age;
//     }
//     // public Person(){
//     //     System.out.println("1.Person类的构造方法");
//     // }
// }

// class Student extends Person{
//     private String school;
//     public Student(){
//         super("yy");
//         System.out.println("Student类的构造方法");
//     }
//     public Student(String school){
//         //super();  //可写可不写，不写系统会自动在子类无参构造前加上super();表示先调用父类的无参构造；
//         //当父类没有无参构造时，必须写上super();
//         //this();
//         super("yy");    //对super的调用必须在构造方法的第一行
//         System.out.println("2.Student类的构造方法");
//     }
// }

// public class Day6{
//     public static void main(String[] args){
//         Student stu = new Student("beida");
//     }
// }


// class Person{
//     public void print(){
//         System.out.println("1.i love you");
//     }
// }

// class Student extends Person{
//     public void print(){
//         super.print();          //这里的super关键字起到可调用父类方法的作用
//         System.out.println("2.i love you");
//     }
// }
// public class Day6{
//     public static void main(String[] args){
//         Student stu = new Student();
//         stu.print();
//     }
// }


//**************************************               final关键字         */
// class Person{
//     public final int a;         //final修饰的变量必须要手动初始化
//     public Person(){
//         a = 10;
//     }
// }
// public class Day6{
//     public static void main(String[] args){
//         Person per = new Person();
//         //per.a = 19;      //不能改
//     }
// }


// class Person{
//     public static final int A = 10;    //Java中常量的定义方法；
//     public final Student stu = new Student();

// }
// class Student{
//     private int test;
//     public void setTest(int test){
//         this.test = test;
//     }
//     public int getTest(){
//         return test;
//     }
// }
// public class Day6{
//     public static void main(String[] args){
//         Person per = new Person();
//         Student stu2 = per.stu;        //合法，引用传递而已，并没有改变stu所指向的地址；⭐
//         per.stu.setTest(10);
//         System.out.println(per.stu.getTest());
//         stu2.setTest(20);
//         System.out.println(per.stu.getTest());
//     }
// }


//校招题
//找错误语句
// public class Day6{
//     byte b1=2, b2=3, b3, b6, b8;
//     final byte b4=4, b5=5, b7=7;
//     public void test(){
//         b3 = (b1+b2);        //error ,从int到byte必须强转 ，应该改成：b3 = (byte)(b1+b2)
//         b6 = (b4+b5);
//         b7 = (b1+b5);           //error，final修饰的变量不能被修改
//         b8 = (byte)(b1+b4);             //同b3错误一样
//     }
//     public static void main(String[] args){

//     }
// }


//******************************         多态(方法覆写)                ****************/
//向上转型（90%）------目的：参数统一化
// class Person{
//     public void print(){
//         System.out.println("i am father!");
//     }
// }
// class Student extends Person{
//     public void print(){
//         System.out.println("i am son!");
//     }
// }
// public class Day6{
//     public static void main(String[] args){
//         Person per = new Student();   //向上转型，无需强转
//         per.print();                  //i am son!
//         //必须有了向上转型才能有向下转型(认爹)⭐
//         Student stu = (Student) per;  //向下转型，必须强转
//         stu.print();
//     }
// }

//向上转型目的：参数统一化
//实例：定义一个方法，接受Person类的所有子类对象并调用print()
class Person{
    public void print(){
        System.out.println("Person类的");
    }
}
class Student extends Person{
    public void print(){
        System.out.println("Student类的");
    }
}
class Worker extends Person{
    public void print(){
        System.out.println("Worker类的");
    }
}
public class Day6{
    public static void main(String[] args){
        fun(new Person());
        fun(new Student());   //自动向上转型
        fun(new Worker());
    }
    public static void fun(Person per){
        per.print();
    }
}
//如果是以前，我们需要用方法重载来写三个方法；而有了向上转型后，只需定义一个方法就行，能接受那几个全部参数；

