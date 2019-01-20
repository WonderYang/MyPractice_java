package www.baidu.java;
public class InitialValues {
    boolean t;   //false
    char c;      //空
    byte b;     //0
    short s;    //0
    int i;      //0
    long l;     //0
    float f;    //0
    double d;   //0
    InitialValues reference;
    void printInitialValues() {
        System.out.println(t);
        System.out.println(c);
        System.out.println(s);
        System.out.println(i);
        System.out.println(l);
        System.out.println(f);
        System.out.println(d);
    }
    public static void main(String[] args) {
        InitialValues init = new InitialValues();
        init.printInitialValues();
    }
}
