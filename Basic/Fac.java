//递归实现20的阶乘
public class Fac{
    public static void main(String[] args){
        long x = 20;   //用int会超出范围
        System.out.println("20! = "+fac(x));
    }
    public static long fac(long x){
        if (x > 1){
            return x * fac(x-1);
        }
        else{
            return 1;
        }
    }
}