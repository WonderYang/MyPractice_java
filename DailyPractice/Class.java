class Person{
    private String name;
    private int age;

    //下面这个是个命名不规范的普通方法，不是构造方法，因为多了个void；
    public void Person(){
        System.out.print("***********************");
    }

    // //构造方法    //构造方法会在产生对象时就自动执行，所以会看到一行##################，而上面是个普通方法，不主动调用它不会打印出*******
    // public Person(){
    //     System.out.print("#########################");
    // }

    //利用构造方法时可以把属性设置进来
    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public void setName(String n){
        name = n;
    }
    public void setAge(int n){         //这样就可以避免年龄的随意设置
        if (n <= 0 || n >= 100){
            System.out.println("年龄设置错误");
        }else{
            age = n;
        }
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public void printPer(){
        System.out.println(name+"在"+age+"岁时努力拼搏");
    }
}

// class Person{
//     public String name;
//     public int age;
//     public void printPer(){
//         System.out.println(name+"在"+age+"岁时努力拼搏");
//     }
// }

public class Class{
    public static void main(String[] args){
        //Person per = new Person();

        //pulic修饰的属性可以在类外直接调用
        // per.name = "yangyun";
        // per.age = 19;
        // per.printPer();

        // //下面属于引用传递，per1会在栈上开辟一个空间，而堆上，没有产生新的空间，只是此时per1也指向了堆中的Person类；
        // Person per1 = per;
        // System.out.println(per1.name);

        // //下面的per就会指向一个新开的堆空间，由于没有初始化，所以会输出默认值；
        // //而per之前指向的堆空间就变成了垃圾空间
        // per = new Person();
        // System.out.println(per.age);    //会输出0

        //private修饰的属性需要用setter和getter方法；


        Person per = new Person("yangyun", 19);
        System.out.print(per.getName());           //想获得名字必须用getname方法，因为name属性是由private修饰的，不能直接调用；

        
    }
}