// class Person{
//     public Person(){
//         System.out.println("Person类的构造方法");
//     }
//     // private String name;
//     // private int age;
//     // public void setname(String name){
//     //     this.name = name;
//     // }
//     // public void setage(int age){
//     //     this.age = age;
//     // }
//     // public String getname(){
//     //     return this.name;
//     // }
//     // public int getage(){
//     //     return this.age; 
//     // }
// }

// //                             *****************继承********************
// class Student extends Person{
//     public Student(){
//         System.out.println("Student类的构造方法...");
//     }
    
//     //  private String school;
//     //  public void setschool(String school){
//     //      this.school = school;
//     //  }
//     //  public String getschool(){
//     //      return this.school;
//     //  }
// }


// public class Day5{
//     public static void main(String[] args){
//         Student stu = new Student();
//         // stu.setage(20);
//         // stu.setname("zangsan");
//         // stu.setschool("qinghua");
//         // System.out.println(stu.getage());
//         // System.out.println(stu.getname());
//         // System.out.println(stu.getschool());
//     }
// }

class Person{
    public Person(){
        System.out.println("Person类的构造方法");
    }
}

class Student extends Person{
    public Student(){
        System.out.println("Student类的构造方法...");
    }
}

public class Day5{
    public static void main(String[] args){
        Student stu = new Student();
    }
}