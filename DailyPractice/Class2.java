// class Person{
//     private String name;
//     private int age;
//     //构造方法重载
//     public Person(){
//         //this(name, age);   这里将会报错，this调用构造方法不能成环；！！！！
//         System.out.println("*********************");
//     }
//     //构造方法重载
//     public Person(String name){
//         this();
//         //System.out.println("*********************");
//         this.name = name;
//     }
//     public Person(String name, int age){
//         this(name);
//         //System.out.println("*********************");
//         this.name = name;   //若不使用this关键字，将会导致就近原则，录入不了初始值
//         this.age = age;
//     }
//     public String getMessage(){
//         return name + " " + age; 
//     }
//     public void setName(String name){
//         this.name = name;
//     }
//     public void setAge(int age){
//         this.age = age;
//     }
// }

// public class Class2{
//     public static void main(String[] args){
//         Person per = new Person("peter", 18);
//         System.out.println(per.getMessage());
//     }
// }



//        this表示当前对象
class Person{
    public void fun(){
        //this表示当前对象
        //this表示当前调用fun方法的对象
        System.out.println("当前对象为" + this);
    }
}
public class Class2{
    public static void main(String[] args){
        Person per1 = new Person();
        per1.fun();
        System.out.println(per1);
        System.out.println("-------------------------------");
        Person per2 = new Person();
        per2.fun();
        System.out.println(per2);
    }
}