// 综合编程题：
// 定义一个抽象的"Role"类，有姓名，年龄，性别等成员变量
// 1）要求尽可能隐藏所有变量(能够私有就私有,能够保护就不要公有)，
// 再通过GetXXX()和SetXXX()方法对各变量进行读写。具有一个抽象的play()方法，
// 该方法不返回任何值，同时至少定义两个构造方法。Role类中要体现出this的几种用法。
// 2）从Role类派生出一个"Employee"类，该类具有Role类的所有成员（构造方法除外），
// 并扩展salary成员变量，同时增加一个静态成员变量“职工编号（ID）”。
// 同样要有至少两个构造方法，要体现出this和super的几种用法，还要求覆盖play()方法，
// 并提供一个final sing()方法。
// 3）"Manager"类继承"Employee"类，有一个final成员变量"vehicle"
// 在main()方法中制造Manager和Employee对象,并测试这些对象的方法。

abstract class Role{
    private String name;
    private int age;
    private String sex;

    public Role(String name, int age) {         //有参构造方法1
        this.name = name;
        this.age = age;
    }

    public Role(String name, int age, String sex) {   //有参构造方法2，构造方法重写
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public abstract void play();    //抽象方法

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}

class Employee extends Role{
    private double salary;
    static int ID;
    public void play(){   //它父类的抽象方法的覆写，必须有；
        System.out.println(super.getName()+"在"+super.getAge()+"岁时工资才"+this.salary);
    }

    public Employee(String name, int age, double salary){
        super(name, age);   //父类没有无参构造，这里的super语句不能省略，下面同理
        this.salary = salary;
    }

    public Employee(String name,int age,String sex,double salary) {
        super(name,age,sex);
        this.salary = salary;
    }

    public final void sing() {   //final表示此方法不能被覆写
        System.out.println(this.getName()+"寂寞的唱歌");
    }

}

class Manager extends Employee{
    public final static String vehicle = "Benz";

    public Manager(String name,int age,double salary) {
        super(name, age,salary);
    }
}

public class Exercise{
    public static void main(String[] args){
        System.out.println(Manager.vehicle);
        Role role = new Manager("yy", 13, 100000);
        role.play();
        System.out.println("姓名： "+role.getName());
        System.out.println("年龄： "+role.getAge());
        //role.sing();   //这句会报错，因为role这个对象没有sing这个函数，要用这个函数，得如下
        Manager man = new Manager("zangsan", 19, 2000);
        man.sing();

        // Role role = new Employee("zhangsan", 18, "male",1000);
        // role.play();
        // Manager manager = new Manager("lisi", 20, 10000000.1);
        // manager.play();
        // manager.sing();
        // System.out.println(Manager.vehicle);

    }
}