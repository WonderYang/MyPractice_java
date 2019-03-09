# Java中产生随机数

[TOC]



## 1.通过Math类

Math类是java.lang包中的一个类，包含了许许多多的数学方法，进入Math类，可看到如下源码：

```java
public final class Math {

    /**
     * Don't let anyone instantiate this class.
     */
    private Math() {}
```

+ 可见Math类是一个由final修饰的类，不能够拥有子类，体现了它的封装性；
+ 再看它的构造方法，是由private修饰的，可以看出Math类不能够产生对象；
+ 在后面的定义中，Math类中的方法都是静态方法，可直接由类名.方法名直接调用；

产生随机数的就是Math类中的random（）方法，看下面这段代码：

```java
public class Rand {
    public static void main(String[] args) {
        int[] arr = new int[30];
        for(int i=0; i<30; i++) {
            arr[i] = (int)(Math.random()*10);
        }
        for(int i:arr) {
            System.out.print(i+"、");
        }
    }
}
```

> 结果：
> 9、1、5、5、8、5、3、4、5、6、9、2、8、3、8、0、1、8、8、4、9、6、3、0、4、6、3、5、7、4、

#### 结论：

+ random（）方法能产生0~1之间的double型小数，**包含0不包含1** ；
+ 可通过对random（）的返回值进行处理得到我们想要的随机数；
+ random的返回值为double类型，注意进行强转；
+ 得到[m,n]闭区间范围得表达式为：int res  =  ( int ) (Math.random( ) * (m+1) + n-m ) ;



## 2.通过Random类

**Random类是java.util包中得一个类，专门用来产生各种类型得随机数；** 

使用该类必须先生成对象，再调用它的方法，Random类中所含方法大致如下：

| 方法                    | 说明                                                         |
| ----------------------- | ------------------------------------------------------------ |
| boolean nextBoolean()   | 生成一个随机的 boolean 值，生成 true 和 false 的值概率相等   |
| double nextDouble()     | 生成一个随机的 double 值，数值介于[0,1.0)，含 0 而不包含 1.0 |
| int nextlnt()           | 生成一个随机的 int 值，该值介于 int 的区间，也就是 -231~231-1。如果  需要生成指定区间的 int 值，则需要进行一定的数学变换 |
| int nextlnt(int n)      | 生成一个随机的 int 值，该值介于 [0,n)，包含 0 而不包含 n。如果想生成  指定区间的 int 值，也需要进行一定的数学变换 |
| void setSeed(long seed) | 重新设置 Random 对象中的种子数。设置完种子数以后的 Random 对象  和相同种子数使用 new 关键字创建出的 Random 对象相同 |
| long nextLong()         | 返回一个随机长整型数字                                       |
| boolean nextBoolean()   | 返回一个随机布尔型值                                         |
| float nextFloat()       | 返回一个随机浮点型数字                                       |
| double nextDouble()     | 返回一个随机双精度值                                         |

看示例代码：

```java
public class Rand {
    public static void main(String[] args) {
        int[] arr = new int[30];
        Random random = new Random();
        for(int i=0; i<30; i++) {
            arr[i] = random.nextInt(5+1)+10-5;  //产生5~10之间的数
        }
        for(int i:arr) {
            System.out.print(i+"、");
        }
    }
}
```

> 输出：
> 7、10、5、10、6、7、8、9、6、9、8、6、10、5、6、10、9、7、5、10、9、8、10、9、5、7、8、9、9、7、

#### 总结：

+ 产生[m,n]闭区间的表达式为：res = random().nextInt(m+1) + n - m；
+ 使用前得先产生Random的对象；

