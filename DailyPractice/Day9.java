import java.util.Scanner;
//定义一个接口
// interface IMessage{
//     public static final String MSG = "hello";   //可以省略public static final
//     public abstract void print();               //可以省略public abstract
// }

// interface INews{
//     public abstract void getMsg();
// }

// class MessageImpl implements IMessage, INews{
//     public void print(){   //必须要覆写此方法
//         System.out.println(MSG);
//     }

//     public void getMsg(){   //必须要覆写此方法
//         System.out.println(MSG);
//     }
// }

// public class Day9{
//     public static void main(String[] args){
//         IMessage msg = new MessageImpl();
//         msg.print();
//         //msg.getMsg();   //错误语句
    
//         // MessageImpl mImpl = new MessageImpl();
//         // mImpl.getMsg();
//         // mImpl.print();

//     }
// }

//******************************************  应用实例   *************************************************

//下面这种模式的最大问题在于，每当有新商品产生，均需要修改客户端的源代码才能支持新商品
// interface Computer{
//     void printRealComputer();
// }

// class Client{
//     public void buyComputer(Computer computer){
//         computer.printRealComputer();
//     }
// }

// class MacbookPro implements Computer{
//     public void printRealComputer(){
//         System.out.println("买一个MacbookPro");
//     }
// }

// class SurfaceBook implements Computer{
//     public void printRealComputer(){
//         System.out.println("买一个SurfaceBook");
//     }
// }

// public class Day9{
//     //客户端
//     public static void main(String[] args){
//         Client client = new Client();
//         client.buyComputer(new MacbookPro());
//     }
// }

//将上述改进为：1.简单工厂模式⭐
// interface Computer{
//     void printRealComputer();
// }

// class Client{
//     public void buyComputer(Computer computer){
//         computer.printRealComputer();
//     }
// }

// class MacbookPro implements Computer{
//     public void printRealComputer(){
//         System.out.println("买一个MacbookPro");
//     }
// }

// class SurfaceBook implements Computer{
//     public void printRealComputer(){
//         System.out.println("买一个SurfaceBook");
//     }
// }

// //工厂类⭐
// //有了工厂类，商品更新时不需要在客户端更改源代码，只需要在工厂类里添加即可
// class ComputerFactory {
//     //工具方法
//     public static Computer getInstanse(String computerName){  //返回类型为Computer
//         if (computerName.equals("mac")){
//             return new MacbookPro();
//         }else if(computerName.equals("surface")) {
//             return new SurfaceBook();
//         }
//         return null;
//     }
// }

// public class Day9{
//     //客户端
//     public static void main(String[] args){
//         Client client = new Client();
//         Scanner scanner =new Scanner(System.in);
//         System.out.println("请输入你想要买的电脑型号：");
//         String computerName = scanner.nextLine();
//         Computer computer = ComputerFactory.getInstanse(computerName);
//         client.buyComputer(computer);
//     }
// }



//
interface Computer{
    void printRealComputer();
}

class Client{
    public void buyComputer(Computer computer){
        computer.printRealComputer();
    }
}

class MacbookPro implements Computer{
    public void printRealComputer(){
        System.out.println("买一个MacbookPro");
    }
}

class SurfaceBook implements Computer{
    public void printRealComputer(){
        System.out.println("买一个SurfaceBook");
    }
}
//工厂类⭐
interface ComputerFactory {
    Computer creatComputer();
}

class AppleFactory implements ComputerFactory {
    public Computer creatComputer(){
        return new MacbookPro();
    }
}

class MSFactory implements ComputerFactory {
    public Computer creatComputer(){
        return new SurfaceBook();
    }
}
public class Day9{
    //客户端
    public static void main(String[] args){
        ComputerFactory computerFactory = new AppleFactory();
        Client client = new Client();
        client.buyComputer(computerFactory.creatComputer());
    }
}