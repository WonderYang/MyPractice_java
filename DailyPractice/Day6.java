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
class Person{
    private String name;
    private int age;
    public Person(String name){
        this.name = name;
    }
    public Person(String name, int age){
        this(name);   //this调用构造方法必须放在第一行，这里这样写是为了避免代码重复
        this.age = age;
    }
    // public Person(){
    //     System.out.println("1.Person类的构造方法");
    // }
}

class Student extends Person{
    private String school;
    public Student(){
        super("yy");
        System.out.println("Student类的构造方法");
    }
    public Student(String school){
        //super();  //可写可不写，不写系统会自动在子类无参构造前加上super();表示先调用父类的无参构造；
        //当父类没有无参构造时，必须写上super();
        //this();
        super("yy");    //对super的调用必须在构造方法的第一行
        System.out.println("2.Student类的构造方法");
    }
}

public class Day6{
    public static void main(String[] args){
        Student stu = new Student("beida");
    }
}