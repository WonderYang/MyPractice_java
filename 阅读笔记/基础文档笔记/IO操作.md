# I/O操作

[TOC]



## 一：文件操作

File类并不会操作文件内容

### 1.判断文件是否存在

```java
        File file = new File("E:\\文件测试\\测试2");
        System.out.println(file.exists());  //true
```

+ 文中的是一个路径，所以exists方法是判断路径是否存在，而不是该路径是不是文件

### 2.判断是否为文件

```Java
        File file = new File("E:\\文件测试\\测试2");
        System.out.println(file.isFile());   //false
```

+ 这里的E:\\文件测试\\测试2是一个文件夹，所以为false

### 3.创建文件与删除文件

```java
        File file = new File("E:\\文件测试\\测试2\\lala");
        if(file.createNewFile()) {
            System.out.println("create file success!");
        }else {
            System.out.println("create file fail");
        }
        System.out.println(file.isFile());  //true
```

+ 该段代码中，E:\\文件测试\\测试2 是我们windows下已经有的路径，执行上操作就会自动在测试2这个文件夹下新建一个lala.txt文本文档，**当不指定新建文件格式时，默认为文本文档；** **若文件已存在，则会创建失败；** 
+ 所以File类提供的createNewFile是创建一个文件，而不是文件夹或路径；
+ 删除文件就不说了，为File类的：delete（）方法，**返回值与创建文件一样，为boolean；**  



### 4.创建目录

File提供的：public boolean mkdirs() 

```java
        File file = new File("E:\\文件测试\\测试3\\测试3-1");
        if(file.mkdirs()) {
            System.out.println("创建目录成功");
        }else {
            System.out.println("创建目录失败");
        }
```

+ 原本我们文件测试下没有测试3，更没有测试3-1，执行上面后，文件测试这个目录中新建了一个名为测试3的文件夹，测试3文件夹中又新建了测试3-1的文件夹，**注意这里新生成的都是文件夹；** 
+ File类还提供了：public boolean mkdir() ，**这个方法与上面不同的是，当file对象的父目录不存在时，就会创建路径失败，而mkdirs方法是无论父路径是否存在，如若不存在，就创建对应的所有父路径；** 

## 二：I\O流操作

**File类不支持文件内容处理，如果要处理文件内容，必须要通过流的操作模式来完成。流分为输入流和输出流。**

> 在java.io包中，流分为两种：字节流与字符流
>
> 1. 字节流：InputStream、OutputStream
> 2. 字符流：Reader、Writer

OutPutStream、InputStream 都是抽象类，所以不能直接创建对象使用，需要用它的子类，看它们的定义：

```java
public abstract class InputStream implements Closeable
public abstract class OutputStream implements Closeable, Flushable 
```

Closeable这个接口主要是关于close的方法，Flushable是关于flush（写入时刷新，读取不需要）的方法

### 1.（1）用字节流进行文件写入（OutputStream）

**OutPutStream、InputStream 都是抽象类，所以不能直接创建对象使用，需要用它的子类**，看它的定义：


```java
//如果是  E:\\文件测试\\测试   就会编译报错，路径必须为一个具体文件，而不能为文件夹
		File file = new File("E:\\文件测试\\测试\\hello.txt");
//第一种写法：
        //写入数据时一定要判断父路径是否存在，若果不存在则给他创建
//        if(!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        OutputStream out = new FileOutputStream(file);
//        String str = "i love you";
//        byte[] buff = str.getBytes();
//        out.write(buff);
//        out.flush();
//        out.close();

//第二种写法：
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try(OutputStream out = new FileOutputStream(file)){
            String str = "i love you";
            byte[] buff = str.getBytes();
            out.write(buff);
            //刷新缓存，最好写上
            out.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }
```

+ **对于IO操作属于资源处理，所有的资源处理操作(IO操作、数据库操作、网络)最后必须要进行关闭；** 第一种写法和第二种写法的区别就是：**第一种要手动close，第二种是从JDk1.7开始追加了一个AutoCloseable接口**， 这个接口的主要目的是自动进行关闭处理，使用它必须结合try..catch；

+ **在操作字节输出流时，给定的file对象必须应该为文件类型（⭐），即不能是一个文件夹，如果这个文件不存在，（前提是这个文件的父目录必须存在），则会自动创建这个文件，即不用使用createNewFile方法来新建；** ⭐

+ **进行写入时，每执行的时候，会将目标写入文件中清空，然后写入，若果想保留目标写入文件中的内容，继续添加内容的话，需要在创建OutputStream类对象时调用另一个有参构造**，即：（上面代码只用改这一处就行）

  ```java
   OutputStream out = new FileOutputStream(file，true)
  ```


### 1.（2）用字符输出流（Writer）进行写操作

```java
public abstract class Writer implements Appendable, Closeable, Flushable
```

该类实现了三个接口，相对于字节输出流多了Appendable接口
该类也有write方法，如下所示，

```java
    public void write(String str) throws IOException 
```

字符输出流与字节输出流的使用方法几乎一模一样，**最大的区别就是该类提供方的write方法可以直接以字符串作为参数进行写入，无须转为字节，**除了这其他使用基本不变，**同样的，Writer作为一个抽象方法，我们需要用FileWriter这个子类来为它实例化对象。**

```java
        File file = new File("E:\\文件测试\\测试2\\test.txt");
        try(Writer writer = new FileWriter(file,true)) {
            writer.write("i like you dream!!!"); 
        }catch (IOException e) {
            e.printStackTrace(); 
        }
```

### 2.用字节输入流来读取数据

输入输出其实都差不多，这里直接呈上代码：

```java
        File file = new File("E:\\文件测试\\测试2\\test.txt");
        try(InputStream in = new FileInputStream(file)) {
            byte[] arr = new byte[10];
            //如果这里就先定义了 int len = in.read(arr) 的话，下面while条件里就会从第二次开始读取了               //⭐
            int len = -1;
            while((len = in.read(arr)) != -1) {
                String str = new String(arr,0,len);
                System.out.println(str);
            }
        }
```

在上面的例子中，我们定义的byte数组用来接受每次读取的字节数据，你可以随意设置这个数组的大小，但一般为1024*1024，这里我们为了凸显特性选取了10，假如我们没有这个while循环来一直读取的话，读一次是读不完的，

+ **当读取完时，read函数将返回-1** ，
+ **每次读取结束，下一次读取时是接着上一次结束的地方继续读取的！**  

### 3.文件拷贝实例代码（内包含了读取和写入的详细操作）

```java
package com.bitten.file;
import java.io.*;
/**
 * @Author : YangY
 * @Description : 文件拷贝
 * @Time : Created in 16:33 2019/3/31
 */
public class FileCopyUtil {
    public static void main(String[] args) {
        //destFilePath必须是一个具体文件的路径，不能为文件夹；⭐⭐
        cp("E:\\文件测试\\测试\\hello.txt","E:\\文件测试\\测试2\\test.txt");
    }

    /**
     *
     * @param sourceFilePath:源文件
     * @param destFilePath：目的文件
     */
    public static void cp(String sourceFilePath,String destFilePath) {
        checkArgumentNotNull(sourceFilePath,"souce file path must not be NULL!!");
        checkArgumentNotNull(destFilePath,"dest file path must not be NULL!!");
        File souceFile = new File(sourceFilePath);
        checkFile(souceFile,"SouceFile must not be null!!");
        File destFile = new File(destFilePath);
        checkDestFile(destFile);
        dataCopy(souceFile,destFile);

    }
    public static void checkArgumentNotNull(Object args, String msg) {
        if(args == null ) {
            throw new IllegalArgumentException(msg);
        }
    }

    private static void checkDestFile(File file) {
        //如果父目录不存在，则会copy失败，为了避免这，如果不存在，我们给它创建对应的父目录；
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    private static void checkFile(File file,String msg) {
        //源文件不能为空，而且必须为文件，是目录的话就不行，得抛出异常；
        if(file == null || !file.exists() ) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 真正实现拷贝得方法
     * @param souce
     * @param dest
     */
    private static void dataCopy(File souce, File dest) {
        long start = System.currentTimeMillis();
        try(InputStream in = new FileInputStream(souce);
            OutputStream out = new FileOutputStream(dest);
        ){
            byte[] buff = new byte[1024*1024];  //每次传输1024*1024个字节，即1M
            int length = -1;
            //当read方法返回值为-1时，代表已经将数据读取完；
            while((length=in.read(buff)) != -1) {
                out.write(buff,0,length);
            }
            out.flush();
            long end = System.currentTimeMillis();
            System.out.println("COPY SUCCESS!!!"+"耗时："+(end-start));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}

```

### 4.转换流

在Java中，还提供了字节流转换为字符流的类：

> **OutputStreamWriter** :将字节输出流变为字符输出流（Writer对于文字的输出要比OutputStream方便）**InputStreamReader** :将字节输入流变为字符输入流（InputStream读取的是字节，不方便中文的处理）

看看这两个类的定义：

```
public class OutputStreamWriter extends Writer
public class InputStreamReader extends Reader
```

我们可以看到他们的继承关系，可以作为了解

### 5.字符编码

```java

```

了解：在开发之中使用的编码只有一个：UTF-8

### 6.内存流

> 对于内存流也分为两类：
>
> 1. 字节内存流:ByteArrayInputStream、ByteArrayOutputStream
> 2. 字符内存流:CharArrayReader、CharArrayWriter

下面看一个对两个文件进行合并的例子：

```java
package com.bitten.file;

import java.io.*;

/**
 * @Author : YangY
 * @Description :  进行两个文件的合并
 * @Time : Created in 19:06 2019/4/5
 */
public class TestFileMerge {

    /*
第一种思路
1. data-a.txt  =>  data.txt
2. data-b.txt append => data.txt

第二种思路 : 适合小量的合并，一般不会采取这种措施
1. data-a.txt  => ByteArrayOutputStream
2. data-b.txt  => ByteArrayOutputStream
3. ByteArrayOutputStream byte[] => FileOutputStream

  */

    public static void main(String[] args) {
        String fileA = "E:" + File.separator + "文件测试" + File.separator + "测试" + File.separator + "data-a.txt";
        String fileB = "E:" + File.separator + "文件测试" + File.separator + "测试" + File.separator + "data-b.txt";
        String file = "E:" + File.separator + "文件测试" + File.separator + "测试" + File.separator + "data.txt";

        try (FileInputStream ain = new FileInputStream(fileA);
             FileInputStream bin = new FileInputStream(fileB);
             ByteArrayOutputStream bout = new ByteArrayOutputStream();
             FileOutputStream fout = new FileOutputStream(file);
        ) {
            byte[] buff = new byte[4];
            int len = -1;
            //ain  -> bout
            while ((len = ain.read(buff)) != -1) {
                bout.write(buff, 0, len);
            }

            //bin  -> bout
            while ((len = bin.read(buff)) != -1) {
                bout.write(buff, 0, len);
            }

            //bout -> fout
            byte[] joinData = bout.toByteArray();
            fout.write(joinData);
            fout.flush();
        } catch (IOException e) {

        }
    }
}

```



### 7.打印流（PrintWriter）

**打印流分为字节打印流：PrintStream、字符打印流:PrintWriter，以后使用PrintWriter几率较高。** 
来看他们的定义：

```java
public class PrintStream extends FilterOutputStream
    implements Appendable, Closeable
```

```java
public class PrintWriter extends Writer
```

**打印流的设计属于装饰设计模式：核心依然是某个类的功能，但是为了得到更好的操作效果，让其支持的功能更多一些**   

```java
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File("E:"+
                File.separator+"文件测试" + File.separator
                +"测试"+File.separator+ "hello.txt")));
        printWriter.print(3);
        printWriter.print("lalallala");
        printWriter.print(3.16);
        //要习惯加上flush，不然有时候会看不到输出
        printWriter.flush();
```



### 8.系统输出

> 面试考点：System.out.println()分别代表啥
>
> **System表示类名**
>
> **out表示对象，printStream**  
>
> print属于方法，属于out对象的方法

我们可以看到System类中out的定义如下：（可见out为一个PrintStream类的对象常量）

```java
    public final static PrintStream out = null;
```

> 系统输出一共有两个常量:out、err,并且这两个常量表示的都是PrintStream类的对象。
>
> 1. out输出的是希望用户能看到的内容
> 2. err输出的是不希望用户看到的内容

out和err其实在开发中用处很少，会被日志所代替
下面看一段简单代码：

```java
        PrintStream outputStream = System.out;
        outputStream.println("lalalla");
```

### 9.系统输入

**System.in对应的类型是InputStream** ，而这种输入流指的是由用户通过键盘进行输入(用户输入)。java本身并没有
直接的用户输入处理，如果要想实现这种操作，必须使用java.io的模式来完成；

**在实际操作中，很少用System.in，因为这个用起来还要结合内存流，很麻烦；** 

### 10.两种输入流

####  1.BufferedReader类

这个是十多年前Java用的输入类，所以不多讲解；

####  2.java.util.Scanner类（⭐）

打印流解决的是OutputStream类的缺陷，BufferedReader解决的是InputStream类的缺陷。而Scanner解决的是
BufferedReader类的缺陷(替换了BufferedReader类)
Scanner是一个专门进行输入流处理的程序类，利用这个类可以方便处理各种数据类型，同时也可以直接结合正
则表达式进行各项处理，在这个类中主要关注以下方法：

+ 先来看它的构造方法：

```java
    public Scanner(InputStream source) {
        this(new InputStreamReader(source), WHITESPACE_PATTERN);
    }
//可见它需要一个InputStream作为参数，而我们又知道，System.in正是InputStream类型；
```

+  判断是否有指定类型数据: public boolean hasNextXxx()

  ```java
          Scanner scanner = new Scanner(System.in);
          System.out.println( scanner.hasNextInt());
  ```

+  取得指定类型的数据: public 数据类型 nextXxx()

+ 定义分隔符:public Scanner useDelimiter(Pattern pattern)

##### 2.1 **Scanner的基本用法用法：** 

```java
        Scanner scanner = new Scanner(System.in) ;
        System.out.println("请输入数据：") ;
        if (scanner.hasNext()) { // 有输入内容,不判断空字符串
            System.out.println("输入内容为: "+scanner.next());
        }
        scanner.close() ;
```

> 输出：
> 请输入数据：
> ijbhu
> 输入内容为: ijbhu
>
> Process finished with exit code 0

##### **2.2使用Scanner操作文件：**  （⭐） 

```java
public class Test {
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(new FileInputStream("E://文件测试测试print.txt"));
        //以c为分隔符；
        scanner.useDelimiter("c");
        if(scanner.hasNext()) {
            System.out.println(scanner.next());
        }
        scanner.close();
    }
}
```

**输出：**
**aaa**
**bbbbb** 

> 已知在文件测试测试print.txt中的数据为：
> aaa
> bbbbb
> ccccc

### 11.序列化

对象序列化指的是：将内存中保存的对象变为二进制数据流的形式进行传输，或者是将其保存在文本中。但是并不
意味着所有类的对象都可以被序列化，严格来讲，需要被序列化的类对象往往需要传输使用，**同时这个类必须实现**
**java.io.Serializable接口**。但是这个接口并没有任何的方法定义，只是一个标识而已。

**如果要想实现序列化与反序列化的对象操作，在java.io包中提供有两个处理类:ObjectOutputStream、**
**ObjectInputStream** 