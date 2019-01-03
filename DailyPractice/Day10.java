//***********************************     代理模式    ***********************************
//一个接口
//两个子类
//一个真实业务，一个辅助业务

// interface IBuy{
//     void buyBuy();
// }
// //真实主题类
// class RealSubject implements IBuy{
//     public void buyBuy(){
//         System.out.println("买一台mac");
//     }
// }
// //代理类
// class ProxySubject implements IBuy{
//     private IBuy buyer;
//     public ProxySubject(IBuy buyer){
//         this.buyer = buyer;
//     }

//     public void beforeBuy(){
//         System.out.println("去店里排队");
//     }
//     public void buyBuy(){
//         this.beforeBuy();
//         this.buyer.buyBuy();
//         this.afterBuy();
//     }
//     public void afterBuy(){
//         System.out.println("发快递到客户");
//     }
// }
// public class Day10{
//     public static void main(String[] args){
//         IBuy buyer = new ProxySubject(new RealSubject());
//         buyer.buyBuy();
//     }
// }



//****************************************************************************************************** */
//                                     String类
//****************************************************************************************************** */
public class Day10{
    public static void main(String[] args){
        // //String类直接赋值
        // String str = "hello";
        // //通过构造方法赋值
        // String str1 = new String("hello");
        // System.out.println(str == str1);  //false,说明str和str1不是一个地址
        // //则字符串不能用==来比较

        // //比较字符串用equals()方法
        // System.out.println(str.equals(str1));  //true

        // //字符串常量是String类的匿名对象
        // String str3 = "i love you";
        // //证明“i love you”是个对象
        // System.out.println("i love you".equals(str1));  //输出false，假如str3是“hello”就是true；


        // //入池操作
        // String str4 = new String("hello").intern();
        // System.out.println("hello".equals(str4));    //true,因为此时str4入了字符串常量池

        // //字符串不可变更（一定要理解清楚，并不是不可加）
        // String str5 = "hello";
        // str5 += "world";
        // str5 += "!!!";
        // System.out.println(str5);   //输出helloworld！！！  将产生大量垃圾空间
        // //所以不要写出这种代码，尽量少写

        // //字符串与字符数组的相互转换
        // //1. 将char[] --> String
        // //调用String
        // char[] date = new char[]{'h','e','l'};
        // String str = new String(date);
        // String str1 = new String(date,1,2);
        // System.out.println(str);
        // System.out.println(str1);

        // //2.将String -->char[]
        // String str = "hello";
        // char c = str.charAt(4);
        // char date[] = str.toCharArray();
        // System.out.println(c);
        // System.out.println(date.length);

        String str = "12a45";
        System.out.println(isAllNumber(str));   //输出false

    }

    //判断一个字符串是否全部由数字组成⭐important!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static boolean isAllNumber(String str){
        char[] date = str.toCharArray();
        for (int i = 0; i < date.length; i++) {
            char c = date[i];
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}