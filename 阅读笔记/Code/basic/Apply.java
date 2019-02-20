package www.baidu.java;
import java.util.*;   //java.lang包是自动导入的，记住；

/**
 * 策略设计模式  P175
 * 能够根据所传递参数对象的不同而具有不同行为的方法，被称为策略设计模式；
 */

class Processor {
    public String name() {
        return getClass().getSimpleName();
    }
    Object process(Object input) {
        return input;
    }
}

class Upcase extends Processor {
    String process(Object input) {
        return ((String)input).toUpperCase();
    }
}
class Downcase extends Processor {
    String process(Object input) {
        return ((String)input).toLowerCase();
    }
}
class Splitter extends Processor {
    String process(Object input) {
        return Arrays.toString(((String)input).split(" ")); //split方法返回值是数组，Arrays.toString是为了把它转成字符串；
    }
}

public class Apply {
    public static void process(Processor p, Object s) {
        System.out.println("Using Processor :" + p.name());
        System.out.println(p.process(s));
    }
    public static String s = "i love you";

    public static void main(String[] args) {
        process(new Upcase(),s);
        process(new Downcase(),s);
        process(new Splitter(),s);
    }
}
