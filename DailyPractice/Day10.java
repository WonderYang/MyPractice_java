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

        // String str = "12a45";
        // System.out.println(isAllNumber(str));   //输出false
        
        //字符串的替换
        // String str = "hb";
        // str = str.replace("h", "*");   //h和*一定要加双引号啊，还有这个方法的返回值类型是String，所以得赋值;
        // System.out.println(str);

        //字符串的拆分
        //public String[] split(String regex)   将字符串全部拆分
        //public String[] split(String regex, int limit)    将字符串按照指定格式拆分为limit个子字符串
        // String str = "i love you";
        // String[] result = str.split(" ");
        // for (String str1 : result){
        //     System.out.print(str1+"、");
        // }
        //拆一个Ip地址
        // String str = "127.0.0.1";
        // String[] result = str.split("\\.");   //.在Java中代表方法运用，有特殊意义，必须加上转义字符，//而不是一个/
        // for(String str1 : result){
        //     System.out.print(str1+" ");
        // }

        
        //将字符串首字母大写
        // String str = "i love you";
        // System.out.println(upperCaseFirstChar(str));


    }

    //判断一个字符串是否全部由数字组成⭐important!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // public static boolean isAllNumber(String str){
    //     char[] date = str.toCharArray();
    //     for (int i = 0; i < date.length; i++) {
    //         char c = date[i];
    //         if (c < '0' || c > '9') {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    //将一个字符串首字母大写 ⭐！！！！！！！！！！！！！
    // public static String upperCaseFirstChar(String str) {
    //     return str.substring(0,1).toUpperCase() + str.substring(1);
    // }
}