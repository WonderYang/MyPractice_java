# JDK各大版本新特性

[TOC]

## JDK1.5新特性

### 1.方法的可变参数：数组

```java
    public static int add(int ... arr) {
        int length = arr.length;
        int sum = 0;
        for(int i =0; i < length; i++) {
             sum = sum + arr[i];
        }
        return sum;
    }
```

在方法中用 ...  表示可变参数

**可变参数必须放在方法参数的*最后一个*并且有且只有一个 ；**

### 2.for-eanch语句（不再详解）

### 3.静态导入（import）（了解）

import static  导入一个类的所有静态域（方法与属性），
使用导入类的静态域就像是在本类中定义的一样；
**使用静态导入后调用那个包中的方法可不加类名直接调用**； 

注意：不推荐使用静态导入；

### 4.泛型（类型守门员）

**定义：类或方法在定义时类型不确定。使用时才确定类型。** 

> alt+insert快速导入（例如getter（）、setter（））
>
> **ClassCastException**（RuntimeException）：在强转时，两个毫无关系的类产生的异常。
>
> 安全隐患：存在于强转，泛型可以避免向下转型的安全隐患；



#### 4.1泛型：在定义时类型不确定，只有在具体使用时才能确定类型。



#### 4.2泛型类

```java
class MyClass<T，E> {  //泛型类可以接受多个类型
    private T t;
    private E e;
}
```

+ <>内的T被称作是类型参数，用于指代任意类型，不包括基本类型；
+ 一般使用单个大写字母
+ 引入泛型后，**一个泛型类的类型在使用时已经确定好，因此无需向下转型；（解除了向下转型的安全隐患）**
+ 技巧：IDEA变量自动补全：ctrl+alt+V

#### 4.3泛型方法（）

定义：在方法声名上**用<>定义**的方法才叫泛型方法，泛型方法可以脱离于泛型类独立存在；

```java
class MyClass2 {
    public <T> void method(T t) {
        System.out.println(t);
    }
}
```

例：

**当泛型类与泛型方法共存时，泛型方法始终与自己的类型参数为准，与泛型类中的类型参数无关。**
所以为了避免混淆，一般使用不同的类型参数来定义泛型类与泛型方法；

```java
class MyClass<T> {
    private T t;
    public <T> void method(T t) {
        System.out.println(t);
    }
}
```

### 通配符：（⭐）

问题：引入泛型后参数类型确定，方法参数只能接受一种类型的泛型类

#### 1.？通配符

只能取得泛型对象中的值，无法通过类似setter方法的设置值
**由于传入类型无法确定，因此无法设置具体值到相应对象中** 
例码：

```java
class Myclass<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
public class Test1 {
    public static void main(String[] args) {
        fun(new Myclass<String>());
        //fun(new Myclass<Integer>());    
    }
    public static void fun(Myclass<?> myclass) {
        //根本就不知道？代表哪种类型，当然不能取设置这个对象里的属性的值，只能查看
        //myclass.setT("lalala");   //将会报错
        System.out.println(myclass.getT());   //输出null
    }
}
```



##### 2.  ？extends类 . 表示泛型上限，类与方法均可使用。

类：class Myclass<T extends Number> {} 
T必须是Number的子类            

​       //Number类自己去看，他的子类有很多，例如Integer、.......            （String类不是Number的子类）
方法：

```
public static void fun(Myclass<? extends Number> myclass)
```

**此时方法中还依然不能设置值，只能取得值** 。现在只能确定是父类，由于子类不确定，此时发生向下转型不再不确定性因此不能确定具体值；

3.？super 类 . 表示泛型下限 ，**只能用在方法级别**   

```java
public void fun(Myclass<? super String> myclass){}
```

例如：

```java
package www.bitten.java;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 9:37 2019/3/17
 */
class Myclass<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
public class Test1 {
    public static void main(String[] args) {
        fun(new Myclass<String>());
        //fun(new Myclass<Integer>());    //将会报错
    }
    public static void fun(Myclass<? super String> myclass) {
        //天然的向上转型，所以能修改值；
        myclass.setT("lalala");
        System.out.println(myclass.getT());
    }
}

```



#### 4.4泛型接口

定义：

```java
interface Imessege<T> {
    void fun(T t);
}
```

子类在实现接口时有两种方式：

##### 1. 子类实现接口时就确定好类型

```java
interface Imessege<T> {
    void fun(T t);
}
class messageImpl implements Imessege<String> {
    @Override
    public void fun(String t) {
        System.out.println(t);
    }
}
public class Test1 {
    public static void main(String[] args){
        Imessege imessege = new messageImpl();
        imessege.fun("hello");   //输出hello
    }
}
```

##### 2. 子类实现接口时仍然保留泛型类（此时子类为泛型类）

```java
interface Imessege<T> {
    void fun(T t);
}
class messageImpl<T> implements Imessege<T> {
    @Override
    public void fun(T t) {
        System.out.println(t);
    }
}
public class Test1 {
    public static void main(String[] args){
        Imessege<String> imessege = new messageImpl();
        imessege.fun("hello");   //输出hello
    }
}
```



## 类型擦除（向下兼容）

**泛型只存在于编译阶段，进入JVM之前，与泛型相关的信息会被完全擦除。**
**在JVM看来，根本就不存在泛型的概念；**

泛型类在进行类型擦除时，若未指定泛型的上限，泛型相关信息会被擦除为Object类，否则，擦除为相应的类型上限。



## 枚举（jdk1.5）

枚举：单例&&多例

枚举设计就是多例的形式（多例太过于复杂，被枚举替代）

```java
//这实际是一个继承于Enum类的一个类
enum Color {
    //private int a = 5;  这条语句将会报错，这不能写在第一行，第一行必须是要定义的枚举变量；
    红, 黄, 蓝;
}
public class Test1 {
    public static void main(String[] args) {
        System.out.println(Color.红);
        System.out.println(Color.红.ordinal());  //输出对应下标
        System.out.println(Color.蓝.name());
        //产生一个包含所有枚举变量的数组
        Color[] arr = Color.values();
        for(Color c : arr) {
            System.out.print(c+"、");
        }
    }

}
```

> 输出：
>
> 红
> 0
> 蓝
> 红、黄、蓝、

> **注意：enum并不是一个新的结构，使用enum定义的枚举实际上默认继承Enum枚举类。因此enum定义的，枚举结构实际上就是一个多例类；**   

**Enum类常用的三个方法：** 

1. ordinal（）：返回枚举对象下标，默认第一个对象为0；
2. name（）：返回枚举对象名称，默认与对象名称保持一致；
3. values（）：返回所有枚举对象的数组；

**应用枚举注意：** 

+ 在枚举中支持定义其他结构，如构造方法、普通方法和普通属性等；
+ **当枚举类中还包含其他结构时，枚举对象的定义必须放在首行；**  （否则会编译报错）
+ 枚举不能够再继承类，因为它默认继承了Enum类，但枚举可以实现一个或多个接口；



### 5. jdk内置的三大注解

注解：@标识符    @Override   @Controller   @Date

#### 1.准确覆写： @Override

用途：**编译器** （⭐）检查当前覆写方法是否满足要求（方法名称是否正确、权限是否合理），以达到及时显示错误；

#### 2. 过期声名：@Deprecated

在早期版本的代码（方法），表示该方法在当前版本中已经不推荐使用，**但是使用了也不会报错**。只是起一个警告提示作用；

#### 3. 压制警告：@SuppressWarnings

压制警告信息，压制信息不再提示；





## JDK1.8新特性

### 1.JDK1.8最大的改变在接口 

发现问题：早期版本的接口已经有很多子类实现了，此时发现接口中的方法需要改动甚至新增方法，无法修改接口（全局常量+抽象方法）

**所以在JDK1.8之后将接口做了扩充：支持default定义的普通方法与静态方法：**

1. 接口中default定义的**普通方法**，通过子类调用，所有子类均拥有此方法（default不能省略，省略了就会认为是个抽象方法）；
2. 接口中的static定义的方法，可直接通过接口名称调用；
   **注意：接口中static方法不是抽象方法哟，必须要有方法体，否则编译报错；** 

**注意：一般情况下在接口中还是只是用全局常量与抽象方法；** 



### 2.Lambda表达式：函数式编程

Lambda表达式虽然在学习中用的不多，但在一些大公司企业中这是一种高校简洁的编码风格，值得学习；

> 使用Lambda表达式的前提：
> **要求接口中有且只有一个抽象方法。** ⭐⭐
>
> JDK8新增注解：@FunctionalInterfa  ，用来检查这个接口是否符合函数实编程接口的规则，若不满足，则会编译报错；
>

#### Lambda表达式语法：

1. 方法体实现只有一行，通过：（）->  具体实现代码；

2. 方法体有多行实现，通过：（）->   {具体实现代码};

3. 方法若有返回值：

   （1）.只有一行代码，return都可以省略
    （2）多行代码加个   { }；就行


看如下代码，这是以往的编程方式：

```java
interface Imessage {
    void fun();
}
public class Test1 {
    public static void main(String[] args){
        //匿名内部类
        Imessage imessage = new Imessage() {
            @Override
            public void fun() {
                System.out.println("hello");
            }
        };   //匿名内部类这必须有分号
        imessage.fun();   //输出hello
    }
}
```

用函数式编程（Lambda表达式）可简化为：

```java
//函数式编程接口必须只能有一个接口
    @FunctionalInterface     //这个注解是jdk8新增的，用来检查接口是否符合函数式编程；
interface Imessage {
    void fun(String s);
}
public class Test1 {
    public static void main(String[] args){
        //语法：(参数)-> 方法体； ⭐     无参的时候（）内直接啥都不写
        Imessage imessage = (String s)-> System.out.println(s);
        imessage.fun("lalal");
    }
}
```

当有返回值时：

```java
//函数式编程接口必须只能有一个接口
    @FunctionalInterface     //这个注解是jdk8新增的，用来检查接口是否符合函数式编程；
interface Imessage {
    int fun(int a, int b);
}
public class Test1 {
    public static void main(String[] args){
        //单行语句时return都可以不用写，直接（）-> a+b ;
        Imessage imessage = (int a, int b)-> {
            return a+b;
        };  //必须有分号，类似于匿名内部类；
        System.out.println(imessage.fun(3,5));   //输出8
    }
}

```



### 3.方法引用（JDK1.8）

**一般结合Lambda表达式共同使用** 

1. 引用静态方法：类名称 :: static 方法名称 ； 
2. 引用某个对象的方法：实例化对象 :: 普通方法 ； 
3. 引用某个特定类的方法： 类名称 :: 普通方法 ； 
4. 引用构造方法： 类名称 :: new 

#### 1.引用某个类的静态方法 

String类的valueOf（）方法：

```java

interface ISubject<P,R> {
    R switchTo(P p);
}
public class Test1 {
    public static void main(String[] args) {
        ISubject<Integer, String> subject =
                String :: valueOf;
        System.out.println(subject.switchTo(123));
    }
}
```



#### 2.引用某个对象的普通方法

```java
interface ISubject<P> {
     P switchTo();
}
public class Test1 {
    public static void main(String[] args) {
        ISubject<String> subject =
                "hello" :: toUpperCase;  //进行方法引用
        System.out.println(subject.switchTo());   //输出：HELLO
    }
}
```



#### 3. 引用某个类的普通方法

不再举例....

