# Java面试

## 1. 基础

### 1. 三大特性

+ 封装（private、private修饰的内部类、final（例如String））
+ 继承（Java只能单继承，多继承用内部类来完成）
+ 多态
  + **多态必须满足三个条件：继承关系、方法的重写、向上转型（父类引用指向子类）⭐** 
  + **多态是同一个行为具有多种不同表现形式的能力，可以理解多态就是同一接口，使用不同的实例而执行不同的操作；** 
  + 多态的优点：
    + 可替换性
    + 可扩充性：增加新的子类不会影响其他子类的继承性和多态性
    + 接口性

### 2. 为什么使用包装类

+ 使用包装类体现了面对对象的设计思想；

+ 包装类拥有一些有用的方法（主要是类型转换方法如parseInt）

+ 包装类天然继承了Serializable（空接口），所以可以实现序列化和反序列化；

+ 包装类可以定义泛型参数，而基本数据类型不可以

  + **因为泛型在编译后会被类型擦除为Object类，而Object不能存储基本数据类型；** 
  + 比如你要使用基本类型（int）集合类，范型得用Integer；

+ 包装类还拥有高频数据的缓存，-128~127，Float和Double没有缓冲区；

  + 调用包装类的valueOf方法时，如何在缓存范围内，则返回已缓存的对象；

    ```true
            Integer x = Integer.valueOf(120);
            Integer y = Integer.valueOf(120);
            System.out.println(x==y);
    ```

### 3. 自动装箱和自动拆箱（范型擦除）

我们知道，Java 语言拥有 8 个基本类型，每个基本类型都有对应的包装（wrapper）类型。

对于基本类型的数值来说，我们需要先将其转换为对应的包装类，再存入容器之中。在 Java 程序中，这个转换可以是显式，也可以是隐式的，后者正是 Java 中的自动装箱。

```java
public int foo() {
  ArrayList<Integer> list = new ArrayList<>();
  list.add(0);
  int result = list.get(0);
  return result;
}
```

上面的代码我们构造了一个Integer范型的集合，下面来看看反编译之后的字节码：

```java

public int foo();
  Code:
     0: new java/util/ArrayList
     3: dup
     4: invokespecial java/util/ArrayList."<init>":()V
     7: astore_1
     8: aload_1
     9: iconst_0
    10: invokestatic java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
    13: invokevirtual java/util/ArrayList.add:(Ljava/lang/Object;)Z
    16: pop
    17: aload_1
    18: iconst_0
    19: invokevirtual java/util/ArrayList.get:(I)Ljava/lang/Object;
    22: checkcast java/lang/Integer
    25: invokevirtual java/lang/Integer.intValue:()I
    28: istore_2
    29: iload_2
    30: ireturn
```

**当向泛型参数为 Integer 的 ArrayList 添加 int 值时，便需要用到自动装箱了。在上面字节码偏移量为 10 的指令中，我们调用了 Integer.valueOf 方法，将 int 类型的值转换为 Integer 类型，再存储至容器类中。** 

下面来看看valueOf这个方法：

```java
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high) //-128～127
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```

可以看到当int数值在-128到127之间的时候会使用到已缓存的Integer对象;
当从泛型参数为 Integer 的 ArrayList 取出元素时，我们得到的实际上也是 Integer 对象。如果应用程序期待的是一个 int 值**，那么就会发生自动拆箱**。在我们的例子中，自动拆箱对应的是字节码偏移量为 25 的指令。**该指令将调用 Integer.intValue 方法。这是一个实例方法(不是静态方法哟)，直接返回 Integer 对象所存储的 int 值。** 

-----

在上面的字节码中可以看到，**往 ArrayList 中添加元素的 add 方法，所接受的参数类型是 Object；而从 ArrayList 中获取元素的 get 方法，其返回类型同样也是 Object。前者还好，但是对于后者，在字节码中我们需要进行向下强制转换🌟，将所返回的 Object 强制转换为 Integer**，方能进行接下来的自动拆箱。

**之所以会出现这种情况，是因为 Java 泛型的类型擦除**。这是个什么概念呢？简单地说，那便是 Java 程序里的泛型信息，在 Java 虚拟机里全部都丢失了。**这么做主要是为了兼容引入泛型之前的代码**。当然，**并不是每一个泛型参数被擦除类型后都会变成 Object 类。对于限定了继承类的泛型参数，经过类型擦除后，所有的泛型参数都将变成所限定的继承类**。也就是说，Java 编译器将选取该泛型所能指代的所有类中层次最高的那个，作为替换泛型的类。

既然泛型会被类型擦除，那么我们还有必要用它吗？当然有必要的。**Java 编译器可以根据泛型参数判断程序中的语法是否正确。举例来说，尽管经过类型擦除后，ArrayList.add 方法所接收的参数是 Object 类型，但是往泛型参数为 Integer 类型的 ArrayList 中添加字符串对象，Java 编译器是会报错的。**

总结一下：

+ 有了范型之后，虽然免去了强转**，但本质上类型强转的工作还是必须要做的，只是不是有开发人员来做了，由编译器来做**，并且编译器会擦除掉对应的泛型信息，使用合适的父类型来代替，可能是Object类也可能是声明泛型时指定的继承的类；

------

**泛型的类型擦除带来了不少问题。其中一个便是方法重写**，来看看这么一段代码：

```java

class Merchant<T extends Customer> {
  public double actionPrice(T customer) {
    return 0.0d;
  }
}

class VIPOnlyMerchant extends Merchant<VIP> {
  @Override
  public double actionPrice(VIP customer) {
    return 0.0d;
  }
}
```

由范型知识可知，在经过类型擦除后，父类的方法描述符为 (LCustomer;)D，而子类的方法描述符为 (LVIP;)D。**这显然不符合 Java 虚拟机关于方法重写的定义。** 那么这个问题是怎么解决的呢？

为了保证编译而成的 Java 字节码能够保留重写的语义，**Java 编译器额外添加了一个桥接方法。该桥接方法在字节码层面重写了父类的方法，并将调用子类的方法。** 🌟编译之后字节码如下：

```java

class VIPOnlyMerchant extends Merchant<VIP>
...
  public double actionPrice(VIP);
    descriptor: (LVIP;)D
    flags: (0x0001) ACC_PUBLIC
    Code:
         0: dconst_0
         1: dreturn

  public double actionPrice(Customer);
    descriptor: (LCustomer;)D
    flags: (0x1041) ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
    Code:
         0: aload_0
         1: aload_1
         2: checkcast class VIP
         5: invokevirtual actionPrice:(LVIP;)D
         8: dreturn

// 这个桥接方法等同于
public double actionPrice(Customer customer) {
  return actionPrice((VIP) customer);
}
```

在我们的例子中，VIPOnlyMerchant 类将包含一个桥接方法 actionPrice(Customer)，它重写了父类的同名同方法描述符的方法。该桥接方法将传入的 Customer 参数强制转换为 VIP 类型，再调用原本的 actionPrice(VIP) 方法。当一个声明类型为 Merchant，实际类型为 VIPOnlyMerchant 的对象，调用 actionPrice 方法时，字节码里的符号引用指向的是 Merchant.actionPrice(Customer) 方法。Java 虚拟机将动态绑定至 VIPOnlyMerchant 类的桥接方法之中，并且调用其 actionPrice(VIP) 方法。

### 4. String类相关

**1：StringBuffer、String、StringBuilder的区别是什么？** 

- **String内容不可修改，StringBuffer和StringBuider内容可修改；** 
  - String类底层是用一个**final**修饰的char型数组来实现的，所以长度不可变(**private final char value[]**)
- **运行速度上：StringBuilder > StringBuffer > String** 
  - String最慢的原因：String为字符串常量，而StringBuilder和StringBuffer均为字符串变量，即String对象一旦创建之后该对象是不可更改的，对于已经存在的String对象的修改都是重新创建一个新的对象,然后把新的值保存进去(效率低)。但后两者的对象是变量，是可以更改的
- **StringBuffer采用同步处理，属于线程安全操作；而StringBuilder采用异步处理，属于线程不安全操作；**
- 所以有：
  - String：适用于少量的字符串操作的情况
  - StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况
  - StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况



**2 ：StringBuilder和StringBuffer的区别？** 

- **StringBuffer采用同步处理，属于线程安全操作；而StringBuilder采用异步处理，属于线程不安全操作；**
  - StringBuffer是jdk1就提出的，StringBuilder是jdk5提出的，它们都继承于AbstractStringBuilder这个类，所以方法功能都差不多，只是StringBuffer的方法**大都采用了synchronized**来进行同步处理，效率比较低下

**3. String的不可变的好处** 

- **字符串常量池是建立在String类的不可变基础上的**，字符串池的建立节省了很多堆空间
- 可以**避免一些安全漏洞**，比如在Socket编程中，主机名和端口都是以字符串形式传入的，因为字符串是不可变的，所以避免了黑客改变字符串指向的对象的值，造成安全漏洞
- 因为不可变性，所以它是**线程安全的，一个字符串可以被多个线程所共享**
- **适合做缓存的key，因为字符串是不可变的，所以在它创建的时候哈希值就被缓存了，所以在使用hash值得时候不需要被重新计算，提升了效率** 



### 5. IO

+ 同步阻塞：A调用B，然后A一直等待B的返回；B执行完后通过原调用接口返回结果。 
+ 同步非阻塞：A调用B，然后A执行其他操作，隔段时间看看原调用接口是否有返回结果；B执行完后通过原调用接口返回结果。 
+ 异步阻塞：A调用B，然后A一直等待B的回调；B执行完后通过回调、状态等其他方式通知A结果。 
+ 异步非阻塞：A调用B，然后A继续做别的，不再搭理B；B执行完后通过回调、状态等其他方式通知A结果

**Java 中的BIO,NIO和AIO**   

在Java中，同步异步，阻塞非阻塞的概念也应用到了很多方面，比如最常见，也是面试官经常考察的就是有关Java中几种IO方式。  

Java中IO的方式通常分为**同步阻塞的BIO，同步非阻塞的NIO，异步非阻塞的AIO**。  BIO同步阻塞  如果你还记得我们在学习程序设计语言之初，完成的socket编程，大概就会了解到BIO的基本工作原理。  一个socket连接一个处理线程，这个线程负责相关数据传输操作，每个服务器需要多个这样的处理线程，然而这种情况下，当多个socket向服务器申请建立连接时，受限于操作系统所允许的最大线程数量的限制，服务器不能提供相应数量的处理线程，没有分配到处理线程的连接就会阻塞等待，所以BIO是阻塞的。  又因为，当进行IO操作时，由Java自己本身处理IO的读写，所以是同步的。  NIO同步非阻塞  在BIO的基础上，NIO作出了改进。考虑到每一个socket连接只有在部分时间才进行了数据传输，大多数时间都是空闲的，而在空闲的时间依然要占用线程，这就造成了浪费。  当客户端的socket连接到服务器端时，不再是每个连接分配一个处理线程，而是服务器端会专门开辟一个”注册中心”统一对其进行管理。当检测到有IO事件请求发生的时候，服务器此时才启动一个处理线程对其进行处理，这种方法解决了因为线程数量的限制，导致socket接入阻塞的问题，因此是非阻塞的。  

AIO异步非阻塞  在NIO中，当Java对IO请求进行处理时，可能会需要对后端资源(比如数据库连接)进行等待，并发量小的时候还好，一旦并发量增大，则也会对服务器的性能造成影响，因此，**有人提出了AIO的概念。**   👇
**与NIO不同的是，对于IO请求的处理，Java将其委托给了操作系统，不再阻塞等待，当操作系统完成了相应的IO处理之后，再去通知服务器，启动线程继续对结果进行处理**。因此是**异步的**。  

小结  
总的来说，大家可以这样记忆：  

+ BIO是一个连接一个线程。(连接阻塞，Java处理IO同步)  
+ NIO是一个请求一个线程。(没有请求时，连接不占用线程，连接非阻塞，Java处理IO同步)  
+ AIO是一个有效请求一个线程。(连接非阻塞，Java处理IO委托给操作系统，异步进行处理)  

### 6. Java9新特性

+ **平台级modularity（原名：Jigsaw） 模块化系统** 

  + Java 9 的定义功能是一套全新的模块系统。当代码库越来越大，创建复杂，盘根错节的**“意大利面条式代码”的几率呈指数级的增长**。这时候就得面对两个基础的问题: 很难真正地对代码进行封装, 而系统并没有对不同部分（也就是 JAR 文件）之间的依赖关系有个明确的概念。每一个公共类都可以被类路径之下任何其它的公共类所访问到, 这样就会导致无意中使用了并不想被公开访问的 API。此外，类路径本身也存在问题: 你怎么知晓所有需要的 JAR 都已经有了, 或者是不是会有重复的项呢? **模块系统把这俩个问题都给解决了**。

    在模块的 src 下创建 module-info.java 文件，来描述依赖和导出（暴露）。

    > requires：指明对其它模块的依赖。
    > exports：控制着哪些包可以被其它模块访问到。所有不被导出的包
    > 默认都被封装在模块里面。

+ Java 的 REPL 工具： jShell 命令

  + #### 2、Java 的 REPL 工具： jShell 命令

    REPL：read - evaluate - print - loop
    这个简单的说就是能想脚本语言那样，所见即所得。之前我们用java，哪怕只想输出一句hello world，都是非常麻烦的。需要建文件、写代码、编译、运行等等。现在有了jShell工具，实在太方便了

    - 即写即得、快速运行，可在命令行直接jshell回车后，直接输入Java代码直接运行

    - jShell 也可以从文件中加载语句或者将语句保存到文件中（使用Open命令）

+ 多版本兼容 jar 包（这个在处理向下兼容方面，非常好用）

  + 当一个新版本的 Java 出现的时候，你的库用户要花费数年时间才会
    切换到这个新的版本。这就意味着库得去向后兼容你想要支持的最老
    的 Java 版本（许多情况下就是 Java 6 或者 Java7）。这实际上意味着
    未来的很长一段时间，你都不能在库中运用 Java 9 所提供的新特性。
    幸运的是，多版本兼容 jar 功能能让你创建仅在特定版本的 Java 环境
    中运行库程序选择使用的 class 版本

+ **语法改进：接口的私有方法**

  + 在 Java 9 中，接口更加的灵活和强大，连方法的访问权限修饰符
    都可以声明为 private 的了，此时方法将不会成为你对外暴露的 API
    的一部分；

+ **底层结构：String 存储结构变更（这个很重要）**

  + JDK8的字符串存储在char类型的数组里面，不难想象在绝大多数情况下，char类型只需要一个字节就能表示出来了，比如各种字母什么的，两个字节存储势必会浪费空间，JDK9的一个优化就在这，内存的优化。
    Java8:

    > private final char value[];

    Java9:

    > private final byte[] value;

    结论：String 再也不用 char[] 来存储啦，改成了 byte[] 加上编码标
    记，节约了不少空间。**由于底层用了字节数组byte[]来存储，所以遇上非拉丁文，JDK9配合了一个encodingFlag来配合编码解码的**

+ 集合工厂方法：快速创建只读集合

  + 为了保证数据的安全性，有时候我们需要创建一个只读的List。在JDK8的时候，我们只能这么做：

    ```
    Collections.unmodifiableList(list)
    Collections.unmodifiableSet(set)
    Collections.unmodifiableMap(map)
    123
    ```

    > Tips：Arrays.asList(1,2,3)创建的List也是只读的，不能添加删除,但是一般我们并不会把他当作只读来用。

    可以说是比较繁琐的一件事。Java 9 因此引入了方便的方法，这使得类似的事情更容易表达。**调用集合中静态方法 of()，可以将不同数量的参数传输到此工厂方法。此功能可用于 Set 和 List，也可用于 Map 的类似形式。此时得到
    的集合，是不可变的：**

    ```java
    List<String> list = List.of("a", "b", "c");
            Set<String> set = Set.of("a", "b", "c");
            //Map的两种初始化方式，个人喜欢第二种，语意更加清晰些，也不容易错
            Map<String, Integer> map1 = Map.of("Tom", 12, "Jerry", 21,
                    "Lilei", 33, "HanMeimei", 18);
            Map<String, Integer> map2 = Map.ofEntries(
                    Map.entry("Tom", 89),
                    Map.entry("Jim", 78),
                    Map.entry("Tim", 98)
            );
    ```

+ 增强的 Stream API

+ 全新的 HTTP 客户端 API

  + HTTP，用于传输网页的协议，早在 1997 年就被采用在目前的 1.1
    版本中。直到 2015 年，HTTP2 才成为标准。

    Java 9 中有新的方式来处理 HTTP 调用。它提供了一个新的 HTTP客户端（ HttpClient ）， 它 将 替代仅适用于 blocking 模式的HttpURLConnection （HttpURLConnection是在HTTP 1.0的时代创建的，并使用了协议无关的方法），并提供对 WebSocket 和 HTTP/2 的支持。

    此外，HTTP 客户端还提供 API 来处理 HTTP/2 的特性，比如流和
    服务器推送等功能。全新的 HTTP 客户端 API 可以从 jdk.incubator.httpclient 模块中获取。因为在默认情况下，这个模块是不能根据 classpath 获取的，需要使用 add modules 命令选项配置这个模块，将这个模块添加到 classpath中。
    栗子：

    ```java
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest req = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).GET().build();
    HttpResponse<String> response = client.send(req, 
    HttpResponse.BodyHandler.asString());
    System.out.println(response.statusCode());
    System.out.println(response.version().name());
    System.out.println(response.body());
    ```

+ **Java9有一个重大的变化，就是垃回收器默认采用了G1。** 

  Java 9 移除了在 Java 8 中 被废弃的垃圾回收器配置组合，同时把G1设为默认的垃圾回收器实现。替代了之前默认使用的Parallel GC，对于这个改变，这项变更是很重要的，**因为相对于Parallel来说，G1会在应用线程上做更多的事情，而Parallel几乎没有在应用线程上做任何事情，它基本上完全依赖GC线程完成所有的内存管理。这意味着切换到G1将会为应用线程带来额外的工作，从而直接影响到应用的性能;** 

## 2. 容器

### 1. ConcurrentHashMap如何保证线程安全

**JDK1.7**
**Segment是ReentrantLock的子类**，ConcurrentHashMap将数据分别放到多个Segment中，默认16个，每一个Segment中又包含了多个HashEntry列表数组
![这里写图片描述](https://img-blog.csdn.net/20180228232428456?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGFva2Vycg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

```java
//Segment继承了ReentrantLock重入锁（这个概念这次先不看）
static final class Segment<K,V> extends ReentrantLock implements Serializable {
    //HashEntry与HashMap中类似，可以理解为一个单向链表元素，作为存放相同hash值不同key的键值对
    //这样一个Segment就相当于一个HashMap
    transient volatile HashEntry<K,V>[] table;
    V put(K key, int hash, V value, boolean onlyIfAbsent) {
        //在对Segment进行操作时，对当前对象加锁
        lock();
        try {
        //数据操作
        } finally {
           unlock();
        }
    }
}
```

不同Segement之间还是异步，这样一来对一个段的修改只会对该段加锁，不会影响到其他段的操作；
Segement初始化为16之后不可再扩容.
结构:16Segment+哈希表

----

**JDK1.8**
ConcurrentHashMap锁进一步细化，结构类似于HashMap.哈希表+红黑树，锁当前桶的头结点,锁的个数进一步提升(锁个数会随着哈希表扩容而增加),支持的并发线程数进一步提升。
**使用CAS+synchronized来保证线程安全** ；

ConcurrentHashMap在jdk1.8中取消segments字段，直接采用transient volatile Node<K,V>[] table保存数据，采用table数组元素（链表的首个元素或者红黑色的根节点）作为锁，从而实现了对每一行数据进行加锁，减少并发冲突的概率。

```java
transient volatile Node<K,V>[] table;

static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K,V> next;
        ......
}
```

ConcurrentHashMap在jdk1.8中将原先table数组＋单向链表的数据结构，变更为table数组＋单向链表＋红黑树的结构。jdk1.8中对于链表个数小于等于默认值8的链表使用单向链接，而个数超对默认值的时候将链表转换为红黑树，查询时间复杂度优化到了O(logN)【二分查找】，O(logN)即二叉树的深度。

**红黑树特点：（保证了最长是最短的两倍，高度差可控）** 

+ 节点只能是红色或黑色
+ **根是黑色的** 
+ 叶子节点（null）是黑色的
+ **红色不能和红色相邻** 
+ **从跟到任意叶子路径上，黑色的数量一样多** 

### 2. List，set，和Map集合的区别？ 

### 3. HashMap、TreeMap、Hashtable的关系和区别

- 这三者都是Map的常用子类；
- HashMap采用懒加载策略，插入第一个元素才初始化数组大小，为16，扩容策略为2倍扩容，线程不安全；Hashtable产生对象时初始化内部哈希表（默认大小为11），扩容策略为两倍加一扩容，采用synchronized同步方法，性能很低（读读互斥），hashtable的key还不能为空；
  **HashMap和Hashtable父类也不同**，前者为AbstractMap，后者为Dictionary；
- TreeMap是有序的，而HashMap和Hashtable是无序的；
- 关于null：**HashMap允许K和V都能为空，K不能重复；**
  **HashTable中K和V都不能为空**
  TreeMap中**K不能为空**（K要实现可排序，怎么能为空，它的compare方法调用时就产生空指针异常了），而**V可以为空**

### 4. HashSet和TreeSet的关系

**联系** ：

- HashSet和TreeSet都是Set接口的子类，**他们的元素都不能重复**
- HashSet和HashMap都是线程不安全的

**区别：**

- **HashSet中保证集合中元素是唯一的方法** ：**通过对象的hashCode和equals方法来完成对象唯一性的判断** ，具体是：先判断hashcode是否一样，如果一样再判断equals，如果equals返回true，则代表相同元素，**不进行存储**，若返回false，则代表不同元素并且有了hash冲突，**再按照HashMap中处理冲突的方式处理冲突** ，因为HashSet本质就是HashMap里的key的集合；
  所以HashSet的元素必须覆写equals和hashCode方法（如果不覆写就是Object类里的方法，而Object里面的是空方法）
  **TreeSet中判断元素唯一性的方法是：** 它的底层是TreeMap，而TreeMap又是红黑树而实现的，所以TreeSet元素不需要覆写equals和hashCode方法，它是根据我们提供的比较比较策略（内部或外部）的返回结果是否为0，如果是0，则是相同元素，不存，如果不是0，则是不同元素，存储
- **内部结构：**HashSet的内部结构是HashMap，TreeSet的内部结构是TreeMap；
- **HashSet允许存放null（但只允许存放一个，不能重复嘛），而TreeSet不允许存放null；**
  原因是TreeSet支持排序（null定然不支持排序吧），所以不能为null；
- **TreeSet能对元素进行排序，而HashSet是无序的（指你插入元素的顺序和输出的顺序）**
  TreeSet元素需要实现内部排序或者外部排序，内部排序即元素自身实现Comparable接口，另外一种方法是实现一个**外部排序类（实现Comparator接口），然后在创建HashSet的时候，有一个构造方法可以传入这个外部排序类，然后就会按照这个排序类来进行排序；**

### 5. Set与Map的关系(hashSet和hashMap)

**总的来说，Map所有的key其实是一个Set，Map所有的value是某种可以存储重复数据的集合，而把 key组成的Set中的元素和value组成的某种的集合中的元素一一对应起来后，就组成了一个Map**

**这里来看看HahSet的源码：** （⭐）

```
public class HashSet<E>
    extends AbstractSet<E>
    implements Set<E>, Cloneable, java.io.Serializable
{
	//底层结构就是一个hashMap
    private transient HashMap<E,Object> map;
	//这个值就是代表每一个插入的时候的value
    private static final Object PRESENT = new Object();

    public HashSet() {
        map = new HashMap<>();
    }
    
    //可以看到，操作方法内部都是直接在操作这个hashMap
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }
}
```

这也是Set集合里不允许保存重复数据的原因，因为在Map集合里，所有类型子类的key也都不能重复；

### 6. hashCode与equals的关系

**hashCode返回相等，equlas不一定相等**
**equals返回相等，hashCode一定相等**

HashSet保证元素唯一就是依靠equals和hashcode方法，所以HashSet元素必须覆写equals和hashcode方法；

### 7. 如何输出HashMap

HashMap不直接支持迭代器Iterator的输出，**但可以将HashMap转化为Set集合再调用迭代器输出；** 

```java

       System.out.println("利用foreach输出：");
        //1.key -->value
        //2.values(无法获取到key)
        //3.entryset
        for(Integer k:map.keySet()) {
            System.out.printf("%d = %s \n",k,map.get(k));
        }

        System.out.println("-------------------");

        System.out.println("利用迭代器输出：");
        Set<Map.Entry<Integer,String>> set = map.entrySet();
        Iterator<Map.Entry<Integer,String>> iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry<Integer,String> entry = iterator.next();
            Integer key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key+" = "+value);

            //下面这将会错误输出，因为包含了两个next调用了
//            Integer key = iterator.next().getKey();
//            String value = iterator.next().getValue();
//            System.out.println(key+"="+value);
        }
```

### 8. fail-fast 机制的坑

**其实，这是一种理念，fail-fast就是在做系统设计的时候先考虑异常情况，一旦发生异常，直接停止并上报**。举一个最简单的fail-fast的例子：

```java
public int divide(int divisor,int dividend){
    if(dividend == 0){
        throw new RuntimeException("dividend can't be null");
    }
    return divisor/dividend;
}
```

上面的代码是一个对两个整数做除法的方法，在divide方法中，我们对被除数做了个简单的检查，如果其值为0，那么就直接抛出一个异常，并明确提示异常原因。**这其实就是fail-fast理念的实际应用。**

这样做的好处就是可以预先识别出一些错误情况，一方面可以避免执行复杂的其他代码，另外一方面，这种异常情况被识别之后也可以针对性的做一些单独处理。

**既然，fail-fast是一种比较好的机制，为什么说fail-fast会有坑呢？** 

原因是Java的**部分**集合类中运用了fail-fast机制进行设计，**一旦使用不当，触发fail-fast机制设计的代码，就会发生非预期情况。** 

我们在Java中一般所谈的fail-fast就是指Java集合中用迭代器遍历元素时对元素进行修改会抛出ConcurrentModifyException，终止程序（除非你catch它）；

**这里要注意的坑就是下面的例子：** 🌟

```java
ArrayList<String> list = new ArrayList<>();
list.add("Bob");
list.add("Alice");
list.add("hello");
for (String str : list) {
    if (str.equals("Bob")) {
        list.remove(str);
    }
}
```

这个程序用增强for循环来遍历集合，并修改元素，你可能会说这又没用到迭代器怎么会抛出并发修改异常，但它确实会抛出，**我们可以反编译这段代码，可以发现增强for循环在遍历集合的时候底层是使用他的迭代器在遍历，所以会造成并发修改异常；** 

+ **产生原理：** 
  modCount != expectedModCount
  modCount记录当前集合修改(结构化修改)的次数
  expectedModCount记录获取集合迭代器时当前集合的修改次数

+ **这个异常的存在意义？**
  + **避免多线程场景下的脏读问题** 🌟
+ **fail-fast如何解决？**  
  + 遍历的时候不要修改集合内容（推荐）
  + 使用迭代器内部的删除方法 （itorator.remove）
  + 使用fail-safe集合（推荐）
    （**所有juc包下的集合（例如ConcurrentHashMap）都属于fail-safe**）
    （**java.util除了TreeMap以外的所有集合都是fail-fast**）

### 9. Java中有哪些线程安全和不安全的容器

**安全** ：（分为同步容器和并发容器）

+ Java在jdk5之前主要提供的线程安全的容器就是同步容器（Synchronized修饰），同步容器的实现其实就是简单的在方法上加上synchronized关键字来保证线程安全，我们也知道这种方式的并发度很低，性能很差；

  在java.util.Collections这个工具类下，提供了：

  ```java
      List<Integer> list1 = Collections.synchronizedList(new ArrayList<>());
      Map<Integer,Integer> hashMap = Collections.synchronizedMap(new HashMap<>());
      Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
  ```
  除此之外，Java还提供了如下同步容器：（**即下面这些容器都是线程安全的**） 

  + Vector （实现了List接口，继承了AbstractList）

    + Vector也就是ArrayList的Synchronized版本；

  + Stack  （继承了Vector）

    ```java
    public class Stack<E> extends Vector<E> 
    ```

    Stack里面的push方就是调用Vector里面的addElement（Synchronized）方法，在数组尾部加上元素而已，而pop方法逻辑就是，先getsize（len），然后调用Vector里面的removeElementAt(len - 1)而已；

  + HashTable （继承了Map接口）

  总而言之，同步容器都是用synchronized关键字来实现的，所以效率比较低下，现在一般情况也不咋用；

+ **在来看看并发容器** 

  **jdk5之前**提供的线程安全的容器，主要指的就是同步容器，这种容器串行度比较高，效率低下；
  而**jdk5之后提供的线程安全的容器，性能比较高，一般称为并发容器；** 
  ![img](https://static001.geekbang.org/resource/image/a2/1d/a20efe788caf4f07a4ad027639c80b1d.png)

  **1. CopyOnWriteArrayList** 

  ```java
  //可见它也是List接口的子类
  public class CopyOnWriteArrayList<E>
      implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
  ```

  顾名思义，这个类就是在写的时候会将共享变量新复制一份出来，这样一来，读的操作就完全无锁；

  **它的实现原理**是怎样的呢？下面就来简单的介绍一下： 
  **CopyOnWriteArrayList内部维护了一个数组，成员变量array就指向这个内部数组**，所有的读操作都是基于这个数组的，在迭代器进行对数组的迭代的时候也是迭代的这个内部数组（array）； 

  **那如果有写的操作呢？下面来看看add的源码：** 

  ```java
      public boolean add(E e) {
          final ReentrantLock lock = this.lock;
          lock.lock();
          try {
              Object[] elements = getArray();
              int len = elements.length;
              Object[] newElements = Arrays.copyOf(elements, len + 1);
              newElements[len] = e;
              setArray(newElements);
              return true;
          } finally {
              lock.unlock();
          }
      }
  ```

  可见在进行写操作的时候是加锁的，然后将原数组拷贝一份（长度加一），然后在拷贝数组上在尾部进行添加元素，所以原数组并没有受影响，读操作等依旧是操作的原数组，只是操作原数组会忽略新加的元素；

  **CopyOnWriteArrayList适用场景：** ⭐

  + 仅仅适用于写少读多的场景，**而且能够容忍读写的短暂不一致** ；因为新写入过程还没结束，读取的还是原数组；
  + CopyOnWriteArrayList迭代器是只读的，不支持增删改，因为迭代器迭代的只是一个快照，对快照进行增删改是没有意义的；

  **2. Map** 🌟

  线程安全的Map有两种：

  + ConcurrentHashMap
  + ConcurrentSkipListMap

  **这两个容器的主要区别就是：CurrentHashMap的key是无序的，CurrentSkipListMap的key是有序的！**

  **这里简单介绍一下CurrentSkipListMap：**
  ConcurrentSkipListMap是线程安全的有序的哈希表，适用于高并发的场景。

  在4线程1.6万数据的条件下，ConcurrentHashMap 存取速度是ConcurrentSkipListMap 的4倍左右。
  但ConcurrentSkipListMap有几个ConcurrentHashMap 不能比拟的优点：
  1、ConcurrentSkipListMap 的key是有序的。
  2、ConcurrentSkipListMap 支持更高的并发。ConcurrentSkipListMap 的存取时间是log（N），因为里面的数据结构是跳表，存取时间复杂度都是O（logN），和线程数几乎无关。**也就是说在数据量一定的情况下，并发的线程越多，ConcurrentSkipListMap越能体现出他的优势。**
  所以在多线程程序中，如果需要对Map的键值进行排序时，请尽量使用ConcurrentSkipListMap，可能得到更好的并发度。

  ----

  上面的ConcurrentSkipListMap原理就是**跳表**，这里来看一下它的优缺点；

  **一、跳表的优点**

  对于一个需要频繁删除的线性有序结构，如何使插入/删除的速度提升？

  - 对于单向链表，只能从头到尾遍历，时间复杂度为O(n)
  - 对于数组，删除插入复杂度太高O(n)，还会涉及数组的扩容操作
  - 平衡二叉树查询速度很快，但是需要平衡的操作开销很大
  - 红黑树的话，性能差不多，但是如果需要多进程同时访问修改的话，红黑树有个平衡的过程，争锁代价也比较大
  - 跳表的线程安全也是通过cas锁实现的，跳表的构建相对简单，**同时支持范围查找**
     缺点：
     相对于红黑树，空间消耗增加；（每个数据除了底层节点还需要索引节点，索引节点有两个指针，向右和向下的指针）；

  **二、增删改查** 

  + 加入多层索引，加快查询速度，理想的跳表上层节点数量为下层的一半，相当于二分，复杂度为O(logn)；

    + 如果完全实现上一层是下一层的一半数量的话，实现将非常复杂，所以跳表并没有严格控制，所以跳表也是一个基于概率的数据结构，即每个节点的总层数是通过概率计算出来的，我们可以通过抛硬币的思想来处理这个问题，当要插入某个节点，然后计算他的层数的时候，开始抛硬币，如果为正则从底层往上走一层，下一次还为正面继续走，直到硬币为反面时就停止，这样情况下在概率论看来就会满足每上一层是下一层总数的一半，最终查找的时间复杂度也为logn；一般我们会定义一个最高层；
    + ConcurrentSkipListMap的底层有三种节点，一种是最底层的节点，一种是索引节点，还有一个特殊节点head，代表最高层最左边那个节点，这个节点里面包含了最高层数；

    ![img](https://images0.cnblogs.com/blog/497634/201312/30222128-045c88b7e992443395a540ba2eb740f3.jpg)

  + 插入与删除都可由查找和更新两部分构成。查找的时间复杂度为O(logn)，更新部分的复杂度与跳跃表的高度成正比，所以总的时间复杂度为O(logn)



## 2. 线程相关

### 0. 进程和线程的区别

+ 进程是资源分配的最小单位，线程是程序执行的最小单位；
+ 进程有自己的独立地址空间，而线程使用相同的地址空间共享数据，因为地址共享，所以多进程比多线程程序更加健壮，因为一个进程挂了不会影响另一个线程，而一个线程挂了会影响到其他线程从而影响到整个进程；
+ 创建一个进程的开销远远比创建一个进程的开销大，cpu对于进程的调度消耗大于线程切换（调度）的消耗；
+ 线程之间通信更加方便（wait和notify、Condition的awiat和signal 、CountDownLatch、CyclicBarrier），而且线程之间共享静态变量、全局变量等数据，而进程之间的通信比较麻烦；

### 1. 线程的五大状态

Thread类里面有一个方法（**getState（）**），可以显示出当前线程的状态，这个方法里面用到了一个枚举，里面就是线程的几种状态：（如下）

```java
public enum State {
    NEW,
    RUNNABLE,
    BLOCKED,
    WAITING,
    TIMED_WAITING,
    TERMINATED;
}
```

- **初始(NEW)**：新创建了一个线程对象，但还没有调用start()方法。

  - NEW状态的线程，不会被操作系统调度，因此不会执行，Java线程要执行，必须是RUNNABLE状态；

- **运行(RUNNABLE)**：**Java线程中将就绪（ready）和运行中（running）两种状态笼统的称为“运行”**。线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取CPU的使用权，此时处于就绪状态（ready）。就绪状态的线程在获得CPU时间片后变为运行中状态（running）;

- **阻塞(BLOCKED)**：**表示线程阻塞于锁；** 

  - 阻塞状态是线程因为某种原因放弃CPU使用权,暂时停止运行… ；

  - 比如该线程正在访问的资源被其他线程锁住了（synchronized）；

- **等待(WAITING)**：进入该状态的线程需要等待其他线程做出一些特定动作（通知或中断）;

  - 比如一个线程调用**wait（）方法后**，它的状态就是WAITING；
  - 当一个线程A调用了**join（）方法**，执行这句调用的线程B就得等待A线程执行完毕，在等待的过程中，B线程的状态就会从RUNNABLE转换到WAITING状态； 

- **超时等待(TIMED_WAITING)**：该状态不同于WAITING，它可以在指定的时间后自行返回；

  - 当一个线程调用sleep方法后，处于的状态就是TIMED_WAITING；
  - 获得Synchronized隐式锁的线程，调用带超时参数的wait方法时，即wait方法带了时间参数；

- **终止(TERMINATED)**：表示该线程已经执行完毕；

  - 线程执行完run方法，自动从RUNNABLE转换到TERMINATED；

### 2. 创建线程的几种方法

+ 继承Thread类

  + 继承Thread和实现Runnable的区别
    - **前者单继承，不能再继承其他类； 后者可实现多继承**
    - 前者线程的创建调度跟业务耦合； 后者业务独立，可以复用（共享）

+ 实现Runnable接口，无返回值

  + 6. Runnable的代理模式 ⭐

    Runnable是一个@FunctionalInterface声名的函数式接口，再来看看Thread的几种构造方法：

    ```
    //第一种    
    public Thread(Runnable target, String name) {
    	init(null, target, name, 0);
    }
    //第二种
        public Thread(Runnable target) {
            init(null, target, "Thread-" + nextThreadNum(), 0);
        }
    ```

    我们需要知道，**要构建一个新的线程，始终要依靠Threa类来完成**，所以单单是Runnable类是不行的；
    所以我们只需要提供Runnable对象，就可以构造一个新的线程**，这里其实Thread类就是一个代理类，而我们的Runnable是一个真实业务类，所以这里实现了简单的代理模式** ；

+ **实现Callable接口，有返回值，注意FutureTask类的get方法是一个阻塞式的获取返回值；** 

  + ```java
           MyThreadC myThreadC = new MyThreadC();
           FutureTask<String> futureTask = new FutureTask<>(myThreadC);
           new Thread(futureTask).start();
           new Thread(futureTask).start();
       ```
    ```
  
    ```

+ **利用线程池来创建线程；** （推荐）

### 3. 线程常用方法 

+ sleep方法（不会释放锁😆）

  线程休眠：指的是让线程暂缓执行一下，等到了预计时间之后再恢复执行。
  **线程休眠会交出CPU，让CPU去执行其他的任务。但是有一点要非常注意，sleep方法不会释放锁（资源），也就是说如果当前线程持有对某个对象的锁（资源），则即使调用sleep方法，其他线程也无法访问这个对象**
  调用该方法后，该线程进入阻塞状态；

+ join方法（线程等待）（会释放锁☹）

  **等待该线程终止。意思就是如果在主线程中调用该方法时就会让主线程休眠，让调用该方法的线程run方法先执行完毕之后在开始执行主线程。** 
  调用该方法后，该线程进入阻塞状态；

+ yield()方法（线程让步）（不释放锁😆）

  yield() : 让步,**交出CPU时间不确定，由系统调度决定**，只会让拥有**相同优先级的线程有获取CPU的机会**，不释放锁；

  **yield方法调用后线程不是进入阻塞状态哟，而是就绪状态！！！** 

### 4. 停止线程的方法

+ （1）设立标志位

+ （2）stop方法，不安全已经被废弃（过期注解）

+ （3）中断⭐

  + 具体而言，这个方法只会给线程设置一个为true的中断标志（中断标志只是一个布尔类型的变量），而设置之后，则根据线程当前的状态进行不同的后续操作。如果，线程的当前状态处于非阻塞状态，那么仅仅是线程的中断标志被修改为true而已；如果线程的当前状态处于阻塞状态，那么在将中断标志设置为true后，还会有如下三种情况之一的操作：

    - 当线程处于**NEW和TERMINATED** 状态时，这时调用interrupt()是毫无意义的，既不会中断也不会改变标志位；
    - **如果是wait、sleep以及join三个方法引起的阻塞，那么会将线程的中断标志重新设置为false，并抛出一个InterruptedException；** (这是一个受查异常，即必须显示的捕获它)；我们可以在catch中来处理我们的逻辑；

    - 线程如果处于blocked（阻塞）状态，仅仅只是设立中断标志位；
- **如果在中断时，线程正处于非阻塞状态，则将中断标志修改为true,而在此基础上，一旦进入阻塞状态，则按照阻塞状态的情况来进行处理；例如，一个线程在运行状态中，其中断标志被设置为true之后，一旦线程调用了wait、jion、sleep方法中的一种，立马抛出一个InterruptedException，且中断标志被程序会自动清除，重新设置为false。**
    
    通过上面的分析，我们可以总结：
**调用线程类的interrupted方法，其本质只是设置该线程的中断标志，将中断标志设置为true，并根据线程状态决定是否抛出异常。** 因此，通过interrupted方法真正实现线程的中断原理是：开发人员根据中断标志的具体值，来决定如何退出线程。

### 5. 线程同步的使用场景

+ Object类的wait和notify机制；

+ **使用Semaphore信号量来实现同步：** 

  业务需求1：有二十人去火车站买火车票，但只有两个窗口，需要控制，同时买票只能有两个人。当2个人中任意一个买好票离开之后，等待的18个人中又会有一个人可以占用窗口买票。
  拆解：20个人是不是就是20个线程；2个窗口就是资源。
  实际含义：怎么控制同一时间的并发数为2。
  Semaphore信号量（控制并发线程数）：
  应用场景：1.限流；2.资源访问需要控制（**数据库连接，打印机的接口**） 

+ **CyclicBarrier(循环栅栏)** 实现同步：
  使用业务需求2：
  公司组织周末聚餐吃饭，首先员工们（线程）各自从家里到聚餐地点，全部到齐之后，才开始一起吃东西（同步点）。假如人员没有到齐（阻塞），到的人只能够等待，直到所有人都到齐之后才开始吃饭。

  **场景：在多线程计算数据之后，最后需要合并结果。** 

+ CountDownLatch 倒计时计数器，有一个任务，他需要等待其他某几个任务执行完毕之后才能执行 ；

### 6. ThreadLocal

**1.概念** 

**ThreadLocal 用于提供线程局部变量⭐**，在多线程环境可以保证各个线程里的变量独立于其它线程里的变量。也就是说**ThreadLocal 可以为每个线程创建一个单独的变量副本，相当于线程的 private static 类型变量。**
ThreadLocal 的作用和同步机制有些相反：同步机制是为了保证多线程环境下数据的一致性；而 ThreadLocal 是保证了多线程环境下数据的独立性。

在set方法中，每个线程都会新建自己的map，所以不同线程拥有自己的独立的map；

下面看它的简单应用：

```java
public class TestThreadLocal {
    private static ThreadLocal<String> threadLocal = new ThreadLocal();
    private String commonString = "thread---A";
    public static void main(String[] args) {
        threadLocal.set("alla");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("thread----子线程");
                System.out.println(threadLocal.get());  //thread----子线程
            }
        },"子线程");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadLocal.get());
    }
}
```

> 结果：
>
> thread—-子线程
> alla

从运行结果可以看出，对于 ThreadLocal 类型的变量，在一个线程中设置值，不影响其在其它线程中的值。也就是说ThreadLocal 类型的变量的值在每个线程中是独立的。

**2.ThreadLocal的内部实现** 

先看看set方法：

```
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}
```

- **set(T value) 方法中，首先获取当前线程，然后在获取到当前线程的 ThreadLocalMap，如果 ThreadLocalMap 不为null，则将 value 保存到 ThreadLocalMap 中，并用当前 ThreadLocal 作为 key；否则创建一ThreadLocalMap 并给到当前线程，然后保存 value。**
- **ThreadLocalMap 相当于一个 HashMap，是真正保存值的地方；**
- ThreadLocalMap是我们要关注的核心，从上面的讲解和它的名字可以猜想到这是每个线程所独立维护的**一个哈希表，这个哈希表从源码中我们可以得知它是一个链表数组（private Entry[] table）** ，这跟我们之前的hashMap差不多的，它的添加数据等等都是差不多的实现，**只不过这里的键值对中的键是以当前线程的ThreadLocal对象；**
- Entry 用于保存一个键值对，其中 key 以**弱引用的方式保存；**

### 7. 线程间如何通信的？

正常情况下，每个线程独立完成自己的任务就结束了，但某些特殊情况下，我们需要多个线程来共同完成某项任务，这时就涉及到了线程间通信了，线程间通信方式如下：

**thread.join()**

+ 当线程A调用线程B的join方法时，调用的这个线程A会等到B线程完全执行结束时才会继续执行A线程；

**object.wait()、object.notify()**

+ **这里以一个例子来说说这个：**写两个线程，一个打印1、3、5.。。。。，一个打印2，4，6.。。。。，打印到100为止；
+ 调用wait前提是先获取到对象锁，调用wait后会将该线程添加到这个锁的等待队列，当被notify唤醒后会被加到同步队列再去竞争锁资源；

**Lock中的Condition，他的signal（）、signalAll（）、await；（等待队列）**

+ 用法和Object的wait、notify一样，只不过我们的synchronized锁只有一个等待队列，而Lock的Condition可以new多个，我们可以根据场景的不同使用多个等待队列，功能更加强大；比如我们的生产消费者模式中，如果用synchronized去实现的话，生产者和消费者只能用同一个等待队列，我们在需要notifyAll的时候则是唤醒了所有的生产者和消费者，虽然还是能实现，但是在商品被卖完时还去唤醒生产者是没有必要的，所以用Lock的Condition的话就能很好的实现生产消费者模型；

**CountdownLatch**

+ 实现一等多的场景；多个线程持有同一把闭锁（CountdownLatch），new闭锁的时候需要传入一个int型的count，当调用countdown（）方法实现计数减1的操作，主线程调用await方法，直到计数器减为0，主线程才会退出阻塞；

+ 它的底层维护了一个Sync静态内部类继承自AQS，

  + 它的countDown方法：（调用了自己静态内部类的一个releaseShared方法，）

    ```
    public void countDown() {
        sync.releaseShared(1);
    }
    ```

**CyclicBarrier**

+ 对于线程 A B C 各自开始准备，直到三者都准备完毕，然后再同时运行 。也就是要实现一种线程之间互相等待 的效果，那应该怎么来实现呢？

  **上面的 CountDownLatch 可以用来倒计数，但当计数完毕，只有一个线程的 await() 会得到响应，无法让多个线程同时触发。**

  为了实现线程间互相等待这种需求，我们可以利用 CyclicBarrier 数据结构，它的基本用法是：

  1. 先创建一个公共 CyclicBarrier 对象，设置 同时等待 的线程数，CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
  2. 这些线程同时开始自己做准备，自身准备完毕后，需要等待别人准备完毕，这时调用 cyclicBarrier.await(); 即可开始等待别人；
  3. 当指定的 同时等待 的线程数都调用了 cyclicBarrier.await();时，意味着这些线程都准备完毕好，然后这些线程才 同时继续执行。

**FutureTask**、**Callable**

> FutureTask**是一个实体类**，而不像Future是一个接口，**这个类也是juc包下的**，下面首先来看看它的**继承关系：** 
>
> ```java
> public class FutureTask<V> implements RunnableFuture<V> 
> ```
>
> 继续看看RunnableFuture这个接口：
>
> ```java
> public interface RunnableFuture<V> extends Runnable, Future<V> {
>     void run();
> }
> ```
>
> 看到这，其实就是**FutureTask这个类同时实现了Runnable接口和Future接口，那么代表：** 
>
> + **实现了Runnable接口，所以可以将FutureTask对象作为任务提交给线程池去执行，也可以直接被Thread执行；**
> + **实现了Future接口，所以也能用来获取任务的执行结果；（get（）方法）** 
>
> 下面来看看它的构造方法：（**两个**） 
>
> ```java
> //传入一个Callable类，即支持返回值；
> public FutureTask(Callable<V> callable)
> ```
>
> ```java
> //这个构造方法是不是跟上面的那个submit的一个很像啊
> public FutureTask(Runnable runnable, V result)
>   //假设这个方法返回的Future对象是f，那么调用f.get（）的返回值就是参数result在执行完后的值；
> ```
>
> **可以看到，这个类没有无参构造，必须调用上面两种的一种；** 

在开发当中，我们经常需要一个任务在执行的时候等待另外一个任务的结果，拿到这个结果后才继续执行下去，这种情况我们一般可以通过join来实现等待，但Join它只是等待，获取不到这个任务的返回值，所以此时需要用到FutureTask来实现这个功能，下面看这个经典例子：

```java
class T1 implements Callable<String> {
    FutureTask futureTask ;
    public T1(FutureTask futureTask) {
        this.futureTask = futureTask;
    }
    public void kettle() throws Exception{
        System.out.println("T1：洗水壶......(1s)");
        Thread.sleep(1000);
    }

    public void boilWater() throws Exception{
        System.out.println("T1：烧开水......(5s)");
        Thread.sleep(3000);
    }

    public void makeTea() throws Exception{
        //泡茶前必须先拿到茶叶
        System.out.println("T1：等待T2的茶叶......");
        //重点在这⭐⭐，这里将会阻塞，直到取到结果；
        String tea = (String) futureTask.get();
        System.out.println("T1：拿到了："+tea);
        System.out.println("T1：开始泡茶啦！！");
        System.out.println("T1：上茶！！！");
    }
    @Override
    public String call() throws Exception {
        kettle();
        boilWater();
        makeTea();
        return null;
    }
}

class T2 implements Callable<String> {
    public void washTeaPot() throws Exception{
        System.out.println("T2：洗茶壶......(1s)");
        Thread.sleep(1000);
    }
    public void washTeabottle() throws Exception{
        System.out.println("T2：洗茶杯......(1s)");
        Thread.sleep(1000);
    }
    public String takeTea() throws Exception{
        System.out.println("T2：开始取茶叶啦，准备给T1......(4s)");
        Thread.sleep(4000);
        System.out.println("T2：取到啦，给T1了");
        return "上好的龙井！！";
    }
    @Override
    public String call() throws Exception {
        washTeaPot();
        washTeabottle();
        return takeTea();
    }
}
public class Test1 {
    public static void main(String[] args) throws Exception{
        //T2实现洗茶壶、洗茶杯、拿茶叶
        FutureTask<String> futureTaskT2 = new FutureTask<>(new T2());
	   //T1负责洗水壶、烧开水、泡茶
        FutureTask futureTaskT1 = new FutureTask(new T1(futureTaskT2));

        new Thread(futureTaskT1).start();
        new Thread(futureTaskT2).start();

    }
}
```

回顾线程的创建，我们一般会把 Runnable 对象传给 Thread 去执行，而 run() 在执行完后不会返回任何结果。那如果希望返回结果呢？这里可以利用另一个类似的接口类 Callable：

**可以看出 Callable 最大区别就是返回范型 V 结果。**

那么下一个问题就是，如何把子线程的结果回传回来呢？在 Java 里，有一个类是配合 Callable 使用的：**FutureTask，不过注意，它获取结果的 get 方法会阻塞主线程。🌟**

这里我们可以学到，通过 FutureTask 和 Callable 可以直接在主线程获得子线程的运算结果，只不过需要阻塞主线程。**当然，如果不希望阻塞主线程，可以考虑利用 ExecutorService，把 FutureTask 放到线程池去管理执行。**

### 8. AQS概述

1. 提供了一种框架，自定义了先进先出的同步队列，让获取不到锁的线程能进入同步队列中排队；
2. 同步器有个状态字段，我们可以通过状态字段来判断能否得到锁，此时设计的关键在于依赖安全的 atomic value 来表示状态（虽然注释是这个意思，但实际上是通过把状态声明为 volatile，在锁里面修改状态值来保证线程安全的）；
3. 子类可以通过给状态 CAS 赋值来决定能否拿到锁，可以定义那些状态可以获得锁，哪些状态表示获取不到锁（比如定义状态值是 0 可以获得锁，状态值是 1 就获取不到锁）；
4. AQS 提供了排它模式和共享模式两种锁模式。排它模式下：只有一个线程可以获得锁，共享模式可以让多个线程获得锁，子类 ReadWriteLock 实现了两种模式；
5. 内部类 ConditionObject 可以被用作 Condition，我们通过 new ConditionObject () 即可得到条件队列；
6. **AQS 实现了锁、排队、锁队列等框架，至于如何获得锁、释放锁的代码并没有实现，比如  tryAcquire、tryRelease、tryAcquireShared、tryReleaseShared、isHeldExclusively  这些方法，AQS 中默认抛 UnsupportedOperationException 异常，都是需要子类去实现的；** 
7. AQS提供了获取不到锁时对线程的阻塞原语，也就是LockSupport的park（）方法可以阻塞一个线程，而它的unpark（）可以去唤醒一个线程；
8. AQS 同步队列和条件队列，获取不到锁的节点在入队时是先进先出，但被唤醒时，可能并不会按照先进先出的顺序执行。

### 9. 线程池相关

线程池是为了避免线程频繁的创建和销毁带来的性能消耗，而建立的一种池化技术，它是把已创建的线程放入“池”中，当有任务来临时就可以重用已有的线程，无需等待创建的过程，这样就可以有效提高程序的响应速度。

+ 降低资源消耗、加快任务执行的响应速度、提高线程的可管理性；

**在阿里巴巴规范当中，线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样的处理方式让写的读者更加明确线程池的运行规则，规避资源耗尽的风险。** 而其实Executors底层就是通过ThreadPoolExecutor来实现的；

返回的线程池对象的弊端如下：1）FixedThreadPool和SingleThreadPool：允许的请求队列长度为Integer.MAX_VALUE（使用的队列为LinkedBlockingQueue，无界队列），可能会堆积大量的请求导致OOM；

来看ThreadPoolExecutor构造方法的参数：

```java
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
```

> 1. **corePoolSize（线程池的基本大小）**：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。如果调用了线程池的prestartAllCoreThreads()方法，线程池会提前创建并启动所有基本线程。
>
> 2. **runnableTaskQueue（任务队列）**：**用于保存等待执行的任务的阻塞队列，**可以选择以下几个阻塞队列： 
>
>    - ArrayBlockingQueue：是一个基于数组结构的有界阻塞队列，此队列按FIFO（先进先出）原则对元素进行排序。
>    - LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按FIFO排序元素，吞吐量通常要高于ArrayBlockingQueue。静态工厂方法Executors.newFixedThreadPool()使用了这个队列。 
>    - SynchronousQueue：一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于Linked-BlockingQueue，静态工厂方法Executors.newCachedThreadPool使用了这个队列。
>    - PriorityBlockingQueue：一个具有优先级的无限阻塞队列。
>
> 3. **maximumPoolSize（线程池最大数量）**：**线程池允许创建的最大线程数**。如果队列满了，并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。值得注意的是，如果使用了无界的任务队列这个参数就没什么效果。
>
> 4. **keepAliveTime（线程活动保持时间）**：**线程池的非核心线程（⭐）空闲后，保持存活的时间**。所以，如果任务很多，并且每个任务执行的时间比较短，可以调大时间，提高线程的利用率。
>
>    + **官方的解释：就是线程数大于corePoolSize时空闲线程存活的最大时间 ；**
>    + **所以这个参数只能回收超出核心线程后的线程，API还提供了一个方法：allowCoreThreadTimeOut（boolean）当参数设置为true时，核心线程空闲超时后也会被回收；** 
>
> 5. **TimeUnit（线程活动保持时间的单位）**：可选的单位有天（DAYS）、小时（HOURS）、分钟（MINUTES）、毫秒（MILLISECONDS）、微秒（MICROSECONDS，千分之一毫秒）和纳秒（NANOSECONDS，千分之一微秒）。
>
> 6. **RejectedExecutionHandler（饱和策略）**：当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常。在JDK 1.5中Java线程池框架提供了以下4种策略。
>    （**这四种都是ThreadPoolExecutor类中的静态内部类**） 
>
>    + DiscardPolicy：不处理，丢弃掉；
>
>    - DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务；
>
>    - AbortPolicy：直接抛出异常。(默认采用此策略) ；
>    - CallerRunsPolicy：只用调用者所在线程来运行任务；

## 2. 锁相关

### 1. synchronized和Lock的区别   

**1. 性能方面的比较** 

- **在JDK1.5中，**synchronized是性能低效的。因为这是一个重量级操作，**它对性能最大的影响是阻塞的是实现，挂起线程和恢复线程的操作都需要转入内核态中完成，这些操作给系统的并发性带来了很大的压力**。相比之下使用Java提供的Lock对象，性能更高一些。在资源竞争不是很激烈的情况下，Synchronized的性能要优于ReetrantLock，但是在资源竞争很激烈的情况下，Synchronized的性能会下降几十倍，但是ReetrantLock的性能能维持常态；
- **到了JDK1.6**，发生了变化，对synchronize加入了很多优化措施，有自适应自旋，锁消除，锁粗化，轻量级锁，偏向锁等等。导致在JDK1.6上synchronize的性能并不比Lock差。**官方也表示，他们也更支持synchronize，在未来的版本中还有优化余地，所以还是提倡在synchronized能实现需求的情况下，优先考虑使用synchronized来进行同步。**  

**2. 功能方面的比较** 

- 既然jdk6时，synchronized的性能优于了ReetrantLock，为什么还要使用ReetrantLock呢？
  **最大的一个原因就是：ReetrantLock能解决死锁的问题呀！！** 具体策略如下：
  - **boolean tryLock（）**
    - 非阻塞式获取锁，获取成功继续执行任务返回true，否则返回false线程直接退出；
    - 这就是支持非阻塞式的获取锁，能打破死锁的四大条件；
  - **void lockInterruptibly() throws InterruptedException**
    - 获取锁时响应中断；支持中断的获取锁，能打破死锁的四大条件；
  - **boolean tryLock(long time, TimeUnit unit) throws InterruptedException（与2是方法重载）**
    - 获取锁时支持超时,在指定时间内未获取到锁，线程直接退出；
    - 支持超时等待获取锁，能打破死锁四大条件；
- **synchronized是JVM级别的锁，属于Java中的关键字，使用sychronized，加锁与解锁都是隐式的；**
  **Lock是Java层面的锁，加锁与解锁都需要显示使用，一个好处就是让加锁和解锁交给程序员去控制；**
- Lock锁能提供公平锁、读写锁，而synchronized锁是非公平的；
- **synchronized的等待队列只有一个，而同一个Lock可以拥有多个等待队列（多个Condition对象）（节省开销，提高效率）** 
- ReentrantLock只能修饰代码块，而synchronized可以修饰代码块和方法；

### 2. Synchronized相关（原理，优化细节）

> 在synchronized加锁的代码反编译后，可以发现在加锁的时候会有monitorenter指令，在解锁的时候会有monitorexit指令，而且一般加锁一次的话会有多个monitorexit指令，这是因为Java不仅要保证正常情况下锁的释放，还要保证异常情况下也会对锁进行释放；

关于 monitorenter 和 monitorexit 的作用，我们可以抽象地理解为**每个锁对象拥有一个锁计数器和一个指向持有该锁的线程的指针**。

+ **当执行 monitorenter 时**，如果目标锁对象的计数器为 0，那么说明它没有被其他线程所持有。在这个情况下，Java 虚拟机会将该锁对象的持有线程设置为当前线程，并且将其计数器加 1。
+ 在目标锁对象的计数器不为 0 的情况下，如果锁对象的持有线程是当前线程，那么 Java 虚拟机可以将其计数器加 1，否则需要等待，直至持有线程释放该锁。

+ 当执行 monitorexit 时，Java 虚拟机则需将锁对象的计数器减 1。当计数器减为 0 时，那便代表该锁已经被释放掉了。

**之所以采用这种计数器的方式，是为了允许同一个线程重复获取同一把锁**。举个例子，如果一个 Java 类中拥有多个 synchronized 方法，那么这些方法之间的相互调用，不管是直接的还是间接的，都会涉及对同一把锁的重复加锁操作。**因此，我们需要设计这么一个可重入的特性，来避免编程里的隐式约束**。说完抽象的锁算法，下面我们便来介绍 HotSpot 虚拟机中具体的锁实现。

**重量级锁：** （**有了自适应自旋优化还是叫重量级锁哟**）

+ 在jdk1.6之前，synchronized是一个重量级锁；重量级锁是 Java 虚拟机中最为基础的锁实现。在这种状态下，Java 虚拟机会阻塞加锁失败的线程，并且在目标锁被释放的时候，唤醒这些线程。**Java 线程的阻塞以及唤醒，都是依靠操作系统来完成的。举例来说，对于符合 posix 接口的操作系统（如 macOS 和绝大部分的 Linux），上述操作是通过 pthread 的互斥锁（mutex）来实现的。此外，这些操作将涉及系统调用，需要从操作系统的用户态切换至内核态，其开销非常之大🌟**。为了尽量避免昂贵的线程阻塞、唤醒操作，Java 虚拟机会在线程进入阻塞状态之前，以及被唤醒后竞争不到锁的情况下，**进入自旋状态**，在处理器上空跑并且轮询锁是否被释放。如果此时锁恰好被释放了，那么当前线程便无须进入阻塞状态，而是直接获得这把锁。由于自旋的线程也不知道要等待的锁什么时候被释放，**所以自旋的时间采用了自适应自旋，即根据以往的自旋时间来判定；**

  > 内核态概念：
  >
  > 当一个进程在执行用户自己的代码时处于用户运行态（用户态），此时特权级最低，为3级，是普通的用户进程运行的特权级，大部分用户直接面对的程序都是运行在用户态。Ring3状态不能访问Ring0的地址空间，包括代码和数据；当一个进程因为系统调用陷入内核代码中执行时处于内核运行态（内核态），此时特权级最高，为0级。执行的内核代码会使用当前进程的内核栈，每个进程都有自己的内核栈。

+ **自旋状态还带来另外一个副作用，那便是不公平的锁机制。处于阻塞状态的线程，并没有办法立刻竞争被释放的锁。然而，处于自旋状态的线程，则很有可能优先获得这把锁。** 🌟

**加锁过程：** 🌟（轻量级锁------》重量级锁）

![img](https://img-blog.csdnimg.cn/2020031010272717.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)

每个对象头中都有一个标记字段（mark word）。它的最后两位便被用来表示该对象的锁状态。**其中，00 代表轻量级锁，01 代表无锁（或偏向锁），10 代表重量级锁**，11 则跟垃圾回收算法的标记有关。
当进行加锁操作时，

图的右侧说明了标准锁定过程。只要对象被解锁，最后两位的值将为01。当方法在对象上同步（即加锁）时，标头字和指向该对象的指针将存储在当前堆栈帧内的锁定记录中。然后，**VM尝试通过*比较和交换*操作在对象的标头字中安装指向锁定记录的指针**。如果成功，则当前线程将拥有该锁。此时会将标头字的最后两位为00，然后将对象标识为已锁定。

**如果比较交换操作由于对象之前被锁定而失败，则VM首先测试标头字是否指向当前线程的方法堆栈**。在这种情况下，线程已经拥有对象的锁，可以安全地继续执行它（锁的重入）。对于这种*递归*锁定的对象，将锁定记录初始化为0，而不是对象的标题字。仅当两个不同的线程同时在同一个对象上同步时，轻量级锁才必须*膨胀*到重量级锁，以管理等待的线程。

**个人总结：** 🌟

+ 只要锁对象标记字段锁状态位为01时就是无锁状态，当锁被释放时都会为01，当线程要加某个对象的锁时，如果为01，就会先以CAS的方式去加锁，加锁成功则设置锁状态为00，则目前这是一个轻量级锁，当这个锁释放（状态为01），下一个线程来获取锁又是重复上面过程，这正是反映了不同线程在不同时间段来获取锁的情况，**这样锁状态被加锁就是轻量级锁**！！（可以看到轻量级锁不会导致线程进入阻塞状态）
+ 但如果一个线程已经持有该锁期间，另一个线程来获取锁发现状态为为00即已经有线程持有该锁，这时就会升级为重量级锁（即监视器管理），该线程就会被操作系统阻塞；
  在被阻塞之前，首先会自适应自旋地去获取锁，如果期间还是获取不到则进入阻塞状态；
+ 上面都过程还存在一个小问题：如果从始至终只有一个线程请求某一把锁时，使用轻量级锁还是每次要进行CAS操作（这是个原子操作）就稍耗性能，则引入了偏向锁。具体来说，在线程进行加锁时，如果该锁对象支持偏向锁，那么 Java 虚拟机会通过 CAS 操作，将当前线程的地址记录在锁对象的标记字段之中，并且将标记字段的最后三位设置为 101。在接下来的运行过程中，每当有线程请求这把锁，Java 虚拟机只需判断锁对象标记字段中：最后三位是否为 101，是否包含当前线程的地址，以及 epoch 值是否和锁对象的类的 epoch 值相同。如果都满足，那么当前线程持有该偏向锁，可以直接返回。

---

**优化：** 
多线程同一时间访问同一个资源（锁），产生竞争，线程阻塞和唤醒，**这将会带来很大的效率问题！**

JVM实现，开发者经过大量的程序分析，多线程访问同一资源的时候，大多数情况下并不是同一时间进行的。

所以有了优化：

- **无锁： 无同步不使用synchronized；**
- **偏向锁：一个线程访问一个资源，产生竞争（多线程）**
- **轻量级锁：多线程不同时间访问同一资源**
- **重量锁：多线程同一时间访问同一资源**

**这三种都使用了synchronized关键字，但具体是哪一种锁，我们也不知道，这是由JVM决定的，这是JVM对synchronized的优化；**

**锁的升级（膨胀）：无锁 —> 偏向锁 —-> 轻量级锁 —-> 重量级锁**

3.总结

Java虚拟机中synchronized关键字的实现，按照代价由高到低可以分为重量级锁、轻量锁和偏向锁三种。

1. 重量级锁会阻塞、唤醒请求加锁的线程。它针对的是多个线程同时竞争同一把锁的情况。JVM采用了自适应自
   旋，来避免线程在面对非常小的synchronized代码块时，仍会被阻塞、唤醒的情况。
2. **轻量级锁采用CAS操作**，将锁对象的标记字段替换为一个指针，指向当前线程栈上的一块空间，存储着锁对象
   原本的标记字段。它针对的是多个线程在不同时间段申请同一把锁的情况。
3. 偏向锁只会在第一次请求时采用CAS操作，在锁对象的标记字段中记录下当前线程的地址。在之后的运行过程
   中，持有该偏向锁的线程的加锁操作将直接返回。**它针对的是锁仅会被同一线程持有的情况** 

**其他优化：**

锁的粗化：将连续的加锁解锁变成更大范围的加锁解锁 （JVM自动优化）

锁消除：即删除不必要的锁；（JVM自动优化）

```
public class Test{
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("a").append("b").append("c");
    }
}
```

**StringBuff属于线程安全**，每次执行appand方法就会加锁，完毕时会解锁，这样会让执行效率大大降低，而且，根本就不会有多线程来同时访问StringBuff对象，所以没有必要加锁，**所以使用StringBuilder，其实一般都是使用StringBuilder；**

### 3. 各种锁（用过哪些锁）

**1. 乐观锁和悲观锁** 

+ 乐观锁：
  + 原子类（atomic包下的AtomicInteger），采用的**CAS乐观锁技术；** 
  + **StampedLock**下的乐观读技术；（validate（）方法判断有无写线程，有的话就进行加读锁......）
    (这个锁的枷锁和解锁都需要一个stamp，这个值在加锁的时候获得，释放锁必须用这个值，如果不一致则会抛出)
  
    + **StampedLock**支持三种模式：悲观读锁、悲观写锁、乐观读锁（其实无锁），悲观读和悲观写互斥，这里来着重看看乐观读：
      ReadWriteLock支持多个线程的同时读，但在多个线程读的时候，不允许任何线程的写；
      **而StampedLock提供的乐观读，是允许一个线程获取写锁的，也就是说不是所有的写线程都会被阻塞！！** 
      下面看这样一个案例：⭐
  
      ```java
      class Point {
          private int x, y;
          StampedLock stampedLock = new StampedLock();
          //计算该点到原点的距离
          double distanceFromOrigin() {
              long stamp = stampedLock.tryOptimisticRead();
              int length = x;
              int width = y;
              //判断执行期间有没有写操作，因为读的期间也可能存在写操作嘛！！
              if(!stampedLock.validate(stamp)) {
                  //期间有其他线程在进行写操作，此时将升级为悲观读锁，将会阻塞在这获取到悲观读锁，因为悲观读和写锁是互斥的嘛，所以会阻塞；
                  long stamp1 = stampedLock.readLock();
                  try {
                      length = x;
                      width = y;
                  }finally {
                      //释放悲观读锁
                      stampedLock.unlock(stamp1);
                  }
              }
              return Math.sqrt(x*x + y*y);
          }
      }
      ```
  
      
+ 悲观锁
  
  + synchronized、ReentrantLock、ReadWriteLock；

**2. 公平锁和非公平锁** (**具体底层看第四个**🌟) 

+ 公平锁是指多个线程按照申请锁的顺序来获取锁；

+ **非公平锁** 

  非公平锁是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁；
  例如ReetrentLock就提供了公平锁和非公平锁；

  ```
  Lock lock = new ReetrentLock(true);   //公平锁
  Lock lock = new ReetrentLock();   //非公平锁
  ```

**3. 独占锁和共享锁** 

+ 独占锁

  独占锁是指任何时候都只有一个线程能执行资源操作；
  我们平时使用的**synchronized和ReentrantLock**等等都属于独占锁；

+ 共享锁
  共享锁指定是同时可以被多个线程读取，但只能被一个线程修改；比如Java中的ReetrantReadWriteLock就是共享锁的实现方式，它允许一个线程进行写操作，多个线程进行读操作；（**读和写是互斥的**）

----

**具体锁：**

+ Semaphere ——信号量
  + 一个计数器，一个等待队列，三个方法（构造、acquire、release），这三个方法都保证了原子性，当构造参数为1时，可以作为互斥锁；
  + 信号量与他所锁最大的区别就是可以允许指定个线程来访问临界区（连接池、对象池、线程池），这是lock不能实现的；
    + init（）（构造）**：设置计数器的初始值；**
    + **down（）**（acquire）：计数器的值减一，如果此时计数器的值**小于零**，则当前线程阻塞，否则当前当前线程可以继续执行； 
    + up（）（release）：计数器的值加一，如果此时计数器的值小于等于（可以等于，因为加一了才等于0，则代表之前为-1，有线程处于等待队列中）零，则唤醒等待队列的一个线程，并且将其从等待队列中移除； 
+ Lock体系
  + **ReentrantLock（最常用）** 
    - **ReentrantLock翻译为中文就是可重入锁的意思**，所以ReentrantLock是可重入的，跟内建锁（synchronized）一样； 
  + ReentrantReadWriteLock（**他本身并不是Lock的子类**，**而是实现了ReadWriteLock接口**，这个类中的静态内部类ReadLock、WriteLock则是实现了Lock接口）

### 4. Lock锁（为什么能实现公平和非公平锁）

> 非公平锁加锁大致流程：调用RetreentLock的lock方法，会调用Sync的lock抽象方法，而这个方法会被NoFairSync内部类的lock复写，这个lock方法会去调用AQS的acquire方法，而acquire会调用钩子方法即回调我们复写的tryAcquire方法，这个tryAcquire是Sync里面复写的；

**为什么能实现非公平锁？**

由FIFO队列（fist in，fist out，这里底层是双向链表队列）的特性知,先加入同步队列等待的线程会比后加入的线程更靠近队列的头部,那么它将比后者更早的被唤醒,它也就能更早的得到锁。从这个意义上,对于在同步队列中等待的线程而言,它们获得锁的顺序和加入同步队列的顺序一致，这显然是一种公平模式。然而,线程并非只有在加入队列后才有机会获得锁,哪怕同步队列中已有线程在等待,非公平锁的不公平之处就在于此。**回看下非公平锁的加锁流程,线程在进入同步队列等待之前有两次抢占锁的机会:**

- 第一次是非重入式的获取锁（**CAS去改变state变量一次**）,只有在当前锁未被任何线程占有（即state为0）(包括自身)时才能成功;

- 第二次是在进入同步队列前,包含所有情况的获取锁的方式（即调用acquire方法，而acquire中会去调用tryAcquire，这个方法必须由子类去实现也就是这里的ReetrantLock去实现的，这个tryAcquire方法会再去CAS获取锁、这里还包括重入锁）。

  ```java
  //这是Sync中的，而NonfairSync继承自Sync，NonfairSync中的tryAcquired调用的就是Sync中的如下方法
  final boolean nonfairTryAcquire(int acquires) {
      final Thread current = Thread.currentThread();//获取当前线程实例
      int c = getState();//获取state变量的值,即当前锁被重入的次数
      if (c == 0) {   //state为0,说明当前锁未被任何线程持有
          if (compareAndSetState(0, acquires)) { //以cas方式获取锁
              setExclusiveOwnerThread(current);  //将当前线程标记为持有锁的线程
              return true;//获取锁成功,非重入
          }
      }
      else if (current == getExclusiveOwnerThread()) { //当前线程就是持有锁的线程,说明该锁被重入了
          int nextc = c + acquires;//计算state变量要更新的值
          if (nextc < 0) // overflow
              throw new Error("Maximum lock count exceeded");
          setState(nextc);//非同步方式更新state值,因为这里不需要加锁去更新
          return true;  //获取锁成功,重入
      }
      return false;     //走到这里说明尝试获取锁失败
  }
  
  ```

只有这两次获取锁都失败后,线程才会构造结点并加入同步队列等待。**而线程释放锁时是先释放锁(修改state值),然后才唤醒后继结点的线程的**。试想下这种情况,线程A已经释放锁,但还没来得及唤醒后继线程C,而这时另一个线程B刚好尝试获取锁,此时锁恰好不被任何线程持有,它将成功获取锁而不用加入队列等待。线程C被唤醒尝试获取锁,而此时锁已经被线程B抢占,故而其获取失败并继续在队列中等待。

> 线程获取锁失败之后会调用执行**addWaiter 方法** ，这部分代码描述了当线程获取锁失败时如何安全的加入同步等待队列。这部分代码可以说是整个加锁流程源码的精华,充分体现了并发编程的艺术性
>
> addWaiter方法里首先创建了一个新节点,并将当前线程实例封装在其内部,然后会去CAS一下插入到尾节点，如果成功则就结束，如果失败会去调用enq(node)方法，中间这部分逻辑在enq(node)中都有,之所以加上这部分“重复代码”和尝试获取锁时的“重复代码”一样,对某些特殊情况 进行提前处理,牺牲一定的代码可读性换取性能提升。
> **来看enq方法：对于enq方法，我们可以归纳为：** 
>
> + 在当前线程是第⼀个加⼊同步队列时，调⽤compareAndSetHead(new Node())⽅法，完
>   成链式队列的头结点的初始化；
> + 若队列不为空，⾃旋不断尝试CAS尾插⼊节点直⾄成功为⽌；
>
> ```java
>     private Node enq(final Node node) {
>         for (;;) {
>             Node t = tail;
>             if (t == null) { // Must initialize
>                 if (compareAndSetHead(new Node()))
>                     tail = head;
>             } else {
>                 node.prev = t;
>                 if (compareAndSetTail(t, node)) {
>                     t.next = node;
>                     return t;
>                 }
>             }
>         }
>     }
> ```
>
> 上面已经完成了将获取锁失败的线程包装成节点并插入到同步队列中，下面自然而然得关心如何出队列，即如何获取到锁后出同步队列，也就是acquireQueued这个方法：
>
> ```java
> final boolean acquireQueued(final Node node, int arg) {
>     boolean failed = true;
>     try {
>         boolean interrupted = false;
>         //死循环,正常情况下线程只有获得锁才能跳出循环
>         for (;;) {
>             final Node p = node.predecessor();//获得当前线程所在结点的前驱结点
>             //第一个if分句，当前节点为head时，才有权尝试去获取锁
>             if (p == head && tryAcquire(arg)) { 
>                 setHead(node); //将当前结点设置为队列头结点
>                 p.next = null; // help GC
>                 failed = false;
>                 return interrupted;//正常情况下死循环唯一的出口
>             }
>             //第二个if分句，判断获取锁失败后是否可以挂起
>             if (shouldParkAfterFailedAcquire(p, node) &&  //判断是否要阻塞当前线程
>                 parkAndCheckInterrupt())      //阻塞当前线程
>                 interrupted = true;
>         }
>     } finally {
>         if (failed)
>             cancelAcquire(node);
>     }
> }
> ```
>
> shouldParkAfterFailedAcquire就是去判断当前线程是否要被阻塞，线程入队后需要阻塞的条件就是，前驱节点的状态为SIGNAL(一个int值，-1)，这个状态的含义就是后继节点处于等待状态，当前节点释放锁后就会将会唤醒后继节点，所以会先判断前驱节点的状态，如果为SIGNAL，则当前线程就可以被阻塞并返回true；如果前驱节点的状态大于0，则代表前驱节点被取消了，这时需要一直往前找，直到找到一个最近的正常等待的节点，然后把它作为当前节点的前驱节点，如果前驱节点正常（未被取消），则修改前驱节点状态为SIGNAL；
>
> ```java
>     private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
>         int ws = pred.waitStatus;
>         if (ws == Node.SIGNAL) //-1
>             /*
>              * This node has already set status asking a release
>              * to signal it, so it can safely park.
>              */
>             return true;
>         if (ws > 0) {
>             /*
>              * Predecessor was cancelled. Skip over predecessors and
>              * indicate retry.
>              */
>             do {
>                 node.prev = pred = pred.prev;
>             } while (pred.waitStatus > 0);
>             pred.next = node;
>         } else {
>             /*
>              * waitStatus must be 0 or PROPAGATE.  Indicate that we
>              * need a signal, but don't park yet.  Caller will need to
>              * retry to make sure it cannot acquire before parking.
>              */
>             compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
>         }
>         return false;
>     }
> ```
>
> ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200320145432168.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)
>
> 到这里整个加锁流程也就结束了，最后的情况就是，没有拿到锁的线程会在队列中被挂起，直到拥有锁的线程释放锁后来唤醒，然后在死循环中继续去获取锁；
>
> 接下来就是unlock操作，他就很简单了，就是一个减state的操作，其中会先判断当前线程是不是占有锁的线程，不是的话就抛出异常，如果是的话就会先计算出state，然后减去realease判断是否为0，如果为0则代表可以正常的释放锁，释放锁之后就会查看头节点的状态是否为SIGNAL，如果是，则唤醒头节点的下个节点的线程；

如果以线程第一次尝试获取锁到最后成功获取锁的次序来看,非公平锁确实很不公平。因为在队列中等待很久的线程相比还未进入队列等待的线程并没有优先权,甚至竞争也处于劣势:在队列中的线程要等待其他线程唤醒,在获取锁之前还要检查前驱结点是否为头结点。**在锁竞争激烈的情况下,在队列中等待的线程可能迟迟竞争不到锁。这也就非公平在高并发情况下会出现的饥饿问题**。**那我们再开发中为什么大多使用会导致饥饿的非公平锁？很简单,因为它性能好啊。**🌟

获取独占锁锁的整个流程如下：

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZXMyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTQyMjIzNy8yMDE4MDgvMTQyMjIzNy0yMDE4MDgwNTE4NDMzMTE0OC0xNTcyNzQ2NjAyLnBuZw?x-oss-process=image/format,png)

----

**公平锁：**

+ 而公平锁，我们调用其lock方法时，也会先获取该锁的状态值state，如果为零，则此时不是直接CAS去加锁（将0改为1）而是先判断此时FIFO队列中是否还有节点即是否还有等待这个锁的线程，如果有则此时该线程会直接加入到队列尾部，还有一种情况是state不为0，但此时获取锁的线程是重入的，此时会直接将状态值加一加锁完毕，除上面两种情况外都是直接加入FIFO队列尾部，此时就等待前面节点被唤醒然后释放锁后才来唤醒这个节点，此时就会按照队列顺序来进行唤醒；

  ```java
          protected final boolean tryAcquire(int acquires) {
              final Thread current = Thread.currentThread();
              int c = getState();
              if (c == 0) {
                  if (!hasQueuedPredecessors() &&
                      compareAndSetState(0, acquires)) {
                      setExclusiveOwnerThread(current);
                      return true;
                  }
              }
              else if (current == getExclusiveOwnerThread()) {
                  int nextc = c + acquires;
                  if (nextc < 0)
                      throw new Error("Maximum lock count exceeded");
                  setState(nextc);
                  return true;
              }
              return false;
          }
  ```

  这个方法是FairSync中复写的，公平锁加锁的时候会调用

  ```java
  final void lock() {
      acquire(1);
  }
  ```

  而acquire是AQS中的方法，如下：

  ```java
  public final void acquire(int arg) {
    //tryAcquire是个钩子方法，里面的逻辑是直接抛异常，所以子类必须复写，后面则是入FIFO队列的操作
      if (!tryAcquire(arg) &&
          acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
          selfInterrupt();
  }
  ```

  

---

**非公平锁对比公平锁的优势：**🌟

+ 非公平锁对锁的竞争是抢占式的(队列中线程除外),线程在进入等待队列前可以进行两次尝试,这大大增加了获取锁的机会。这种好处体现在两个方面:
  - 1.线程不必加入等待队列就可以获得锁,不仅免去了构造结点并加入队列的繁琐操作,同时也节省了线程阻塞唤醒的开销,线程阻塞和唤醒涉及到线程上下文的切换和操作系统的系统调用,是非常耗时的。在高并发情况下,如果线程持有锁的时间非常短,短到线程入队阻塞的过程超过线程持有并释放锁的时间开销,那么这种抢占式特性对并发性能的提升会更加明显。
  - 2.减少CAS竞争。如果线程必须要加入阻塞队列才能获取锁,那入队时CAS竞争将变得异常激烈,CAS操作虽然不会导致失败线程挂起,但不断失败重试导致的对CPU的浪费也不能忽视。除此之外,加锁流程中至少有两处通过将某些特殊情况提前来减少CAS操作的竞争,增加并发情况下的性能。一处就是获取锁时将非重入的情况提前,如下图所示



### 5. volatile关键字原理

volatile 字段可以看成一种轻量级的、不保证原子性的同步，其性能往往优于（至少不亚于）锁操作。然而，频繁地访问 volatile 字段也会因为不断地强制刷新缓存而严重影响程序的性能。在 X86_64 平台上，**只有 volatile 字段的写操作会强制刷新缓存（并会使得这个字段的其他处的缓存无效）**。因此，**理想情况下对 volatile 字段的使用应当多读少写**，并且应当只有一个线程进行写操作。**volatile 字段的另一个特性是即时编译器无法将其分配到寄存器里。换句话说，volatile 字段的每次访问均需要直接从内存中读写。** 🌟

在加入volatile关键字后，反编译后会发现多了一个lock前缀指令，这个lock前缀指令相当于一个内存屏障，而内存屏障提供了三个功能：

+ 他确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，也不会把它之前的指令排到它之后，这样就保证了一个有序性；
+ 他会强制将对缓存的修改操作立即写入主内存；
+ 如果是写操作，他会导致其他线程对应的缓存无效（工作内存），而无效之后要读取只能去主内存读取，而主内存又是最新的值，所以保证了一个可见性；

## 4. 排序

[堆排序](https://baike.baidu.com/item/%E5%A0%86%E6%8E%92%E5%BA%8F)、[快速排序](https://baike.baidu.com/item/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F)、[直接选择排序](https://baike.baidu.com/item/%E7%9B%B4%E6%8E%A5%E9%80%89%E6%8B%A9%E6%8E%92%E5%BA%8F)是不稳定的排序算法，而[基数排序](https://baike.baidu.com/item/%E5%9F%BA%E6%95%B0%E6%8E%92%E5%BA%8F)、[冒泡排序](https://baike.baidu.com/item/%E5%86%92%E6%B3%A1%E6%8E%92%E5%BA%8F)、[直接插入排序](https://baike.baidu.com/item/%E7%9B%B4%E6%8E%A5%E6%8F%92%E5%85%A5%E6%8E%92%E5%BA%8F)、[折半插入排序](https://baike.baidu.com/item/%E6%8A%98%E5%8D%8A%E6%8F%92%E5%85%A5%E6%8E%92%E5%BA%8F)、[归并排序](https://baike.baidu.com/item/%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F)是稳定的排序算法。 

**1. 直接插入排序** 

+ 思路：将待排序的数据分为两个区间，已排序区间与待排序区间。算法刚开始时，已排序空间有一个元素，在待排序空间中选择第一个元素与已排序空间的最后一个元素比较，若比已排序的最大元素大直接放入，若小则进行插入 ；
+ **时间复杂度：**
  **最好情况：O(n)**   有序时则为O（n）
  最坏情况：O(n^2)
  平均复杂度：O(n^2)
+ 空间复杂度：O（1）；
+ **算法的稳定性**：属于稳定性排序方法 

**2. 折半插入排序**

+ 思路：相对于直接插入，**优化：利用二分查找来节省时间；**  

**3. 选择排序** 

+ 思路：遍历数组，每次选择出一个最大/最小的值出来放到指定位置；
+ 时间复杂度：O（n^2）

**4. 冒泡排序（O(n^2)）**

+ 思路：两两比较，到最后能选出最大的值；

**5. 堆排序** （**堆排序的时间复杂度是稳定的：nlog（n）** ）

```java
排序思想：
- 首先将数组建成堆
- 将数组的头元素（堆顶）与最后一个元素交换
- 将除了最后一个元素，剩下的又调整成一个堆
- 重复2、3，直到全部排序完成

        /**
         * 原地堆排序，即空间复杂度为O（1）
         * @param arr
         */
        public static void heapSort2(Integer[] arr) {
            //1.把数组变成堆
            int length = arr.length;
            for(int i=(length-1-1)/2; i>=0; i--) {
                siftdown(arr, length, i);
            }
            //2.依次将最大值换到末尾
            for(int i=length-1; i>=0; i--) {
                swap(arr, 0, i);
                siftdown(arr, i, 0);
            }
        }
        public static void swap(Integer[]arr, int indexA, int indexB) {
            int temp = arr[indexA];
            arr[indexA] = arr[indexB];
            arr[indexB] = temp;
        }
        private static void siftdown(Integer[] arr, int n, int k) {
            while(2*k+1 < n) {
                int j=2*k+1;
                //取出左右孩子最大值
                if(j+1 < n) {
                    //此时有右孩子
                    if(arr[j].compareTo(arr[j+1]) < 0) {
                        j++;
                    }
                }
                //此时arr[j]存放了左右两个孩子的最大值
                if(arr[k] > arr[j]) {
                    break;
                }
                swap(arr, k, j);
                k = j;
            }
        }
    }
```

**6. 归并排序**

+ 时间复杂度：**（恒为nlogn）** 
+ 空间复杂度：O（n）；

+ ```java
      public static void mergeSortIntern(int[] arr, int left, int right) {
          if(left >= right) {
              return;
          }
          int mid = (left + right) / 2;
          mergeSortIntern(arr, left, mid);
          mergeSortIntern(arr,mid+1,right);
          merge(arr,left,mid,right);
  
      }
  ```

**7.快速排序** 

+ 时间复杂度：**假如每次基准值的选取都恰好是中间，这种是最优情况**，那么相当于分的时间复杂度为O（logn），而具体每次的选取partion那个方法的时间复杂度为O（n），所以最优情况下是O（nlogn）； 

+ ```java
  public class quickSortCase {
      //GreateArr.arrRandom是我自己建立的工具类的一个静态方法，用来获取随机数组
      static int[] arr = GreateArr.arrRandom;
      public void quick_1() {
          quickSort(arr);
      }
      public static void quickSort(int[] arr) {
          int length = arr.length;
          if(length <= 1) {
              return;
          }
          quickSortInternal(arr,0,length-1);
      }
      private static void quickSortInternal(int[] arr, int l, int r) {
          if(l >= r) {
              return;
          }
          //获取基准值
          int par = patition(arr, l, r);
          quickSortInternal(arr, l, par-1);
          quickSortInternal(arr, par+1, r);
      }
      private static int patition(int[] arr, int l, int r) {
          //选取第一个为val
          int val = arr[l];
          //[l+1, j]区间内都是小于val
          int j = l;
          //[j+1, i]区间内都是大于等于val
          int i = l+1;
          for(; i<=r;i++) {
              if(arr[i] < val) {
                  swap(arr, i, j+1);
                  j++;
              }
          }
          swap(arr, l, j);
          return j;
      }
      private static void swap(int[] arr, int a, int b){
          int temp = arr[a];
          arr[a] = arr[b];
          arr[b] = temp;
      }
  }
  ```

+ 快拍优化：
  **上面我们是选取第一个元素为val，假如数组接近有序（无论升序还是降序都一样）的话，如1，2，3，4，5，6，7这种情况，快排的最优情况就是每次选取的基准值在中间，而这种情况就是最恶劣的情况，每次的基准值都是在最左边，导致分的时间复杂度为O（n），导致最终为O（n^2）;** 

  + （1）第一种想法就是将第一个元素与其他随机一个元素进行交换，这样一来随机性就有了，就算是接近有序得情况也不至于退化为O（n^2）；
  + （2）**第二种思路就是推荐的一种，叫做三数取中法，思路就是保证arr[mid] <= arr[low] <= arr[high]；** 

    + 至于为什么要这样，自行百度😄，这里就举个栗子吧，假如是1、2、3、4、5、6、7，此时经过三数取中法后，变为4、2、3、1、5、6、7，这样一来，选取的基准值恰好就在中间，达到了最理想的效果；

  **还有一个有话就是当大量元素重复的时候，分得又不均匀**，此时时间复杂度会降到O（n2），解决办法就是划分成三部分，小于的一部分，等于的一部分，大于的一部分；

#### 1. 什么工具类对List里面的元素进行排序 

**Collections 里面的sort方法；这个方法没有返回值；** 



## 5. JVM

### 1. 说一下JVM的内存分配 

**1.程序计数器（线程私有）** 

这个是每个线程都私有的，每个线程都是要得到CPU的时间分片才能够处于运行状态，当CPU对这个线程的时间分片到了，就得去调度其他线程，**此时程序计数器就需要记录当前线程的一个执行位置**，当CPU调度下次到来时，能继续从上次的地方开始执行； 

- **程序计数器是一块比较小的内存；**
- **用途：可以看作是当前线程所执行的字节码的行号指示器；**
- **每个线程都需要有一个独立的程序计数器，各条线程间的程序计数器互不影响，独立存贮，称这类内存为“线程私有”内存；**

**2.Java虚拟机栈（线程私有）** 

- **线程私有区域，生命周期和线程相同**
- **用途：描述Java方法执行的内存模型，每个方法在执行的同时都会创建一个栈帧，用于存储局部变量表、操作数栈、动态链接、方法出口等信息；**
- **局部变量表所需的内存空间在编译期间完成分配，当进入一个方法时，这个方法需要在帧中分配多大的局部变量空间是完全确定的在方法运行期间不会改变局部变量表的大小；**
- 这个区域规定了两种异常状况：
  - **StackOverflowError异常：线程申请的栈深度大于虚拟机所允许的深度；**
  - **OutOfMemoryError异常：虚拟机栈动态扩展时无法申请到足够的内存；**

**3.本地方法栈（线程私有）**

- 本地方法栈和Java虚拟机栈非常相似；
- 本地方法栈为虚拟机使用到的Native方法服务，虚拟机栈为虚拟机执行Java方法服务；

**4.Java堆（所有线程共享的一块内存区域）** 

- Java堆基本是Java虚拟机所管理内存中最大的一块；
- **Java堆的唯一目的：存放对象实例，几乎所有的对象实例都在这里分配内存；** 
- Java堆是垃圾收集器管理的主要区域；
- Java堆还可细分：新生代、老年代；
- Java堆可以处于物理上不连续的内存空间中；
- 这个区域规定了一个异常状况：
  - **OutOfMemoryError异常：在堆中已经没有内存完成实例分配，并且堆也无法完成扩展时，将会抛出此异常**  

**5.方法区（所有线程共享的一块内存区域）** 

- 目的：用于存储已被虚拟机加载的类信息、常量、静态变量、即编译器编译后的代码等数据；**（jdk6之前）** 
  （在虚拟机进行类加载的加载阶段，就是将类的二进制流所代表的静态存储结构转化为**方法区**的运行时数据结构）
- 方法区可以选择不实现垃圾收集，这个区域的回收条件比较苛刻，但还是必须的，有些bug就是因为对方法区的回收没有做好；
- 这个区域规定了一个异常状况：
  - **OutOfMemoryError异常：当方法区无法满足内存分配需求** ； 

**备注：方法区是虚拟机规范中对运行时数据区划分的一个内存区域，不同的虚拟机厂商可以有不同的实现，而HotSpot虚拟机以永久代来实现方法区，所以方法区是一个规范，而永久代则是其中的一种实现方式。**  

**Java6和6之前，常量池是存放在方法区中的；**

**Java7，将常量池存放到了堆中，常量池就相当于是在永久代中，所以永久代存放在堆中；**

**Java8之后，取消了整个永久代区域，取而代之的是元空间（metaSpace），没有再对常量池进行调整；** 

> HotSpot虚拟机在1.8之后已经取消了永久代，改为元空间，类的元信息被存储在元空间中。元空间没有使用堆内存，而是与堆不相连的本地内存区域。所以，理论上系统可以使用的内存有多大，元空间就有多大，所以不会出现永久代存在时的内存溢出问题。这项改造也是有必要的，永久代的调优是很困难的，虽然可以设置永久代的大小，但是很难确定一个合适的大小，因为其中的影响因素很多，比如类数量的多少、常量数量的多少等。永久代中的元数据的位置也会随着一次full GC发生移动，比较消耗虚拟机性能。同时，HotSpot虚拟机的每种类型的垃圾回收器都需要特殊处理永久代中的元数据。将元数据从永久代剥离出来，不仅实现了对元空间的无缝管理，还可以简化Full GC以及对以后的并发隔离类元数据等方面进行优化；

**5.1运行时常量池** 

- 运行时常量池是方法区的一部分，Class文件中除了有类的版本、字段、方法、接口等描述信息外、还有一项信息是常量池；
- 作用：用于存放编译器生成的各种字面量和符号引用，这部分内容将在类加载后进入方法区的运行时常量池中存放



### 2. 如何判断对象已死

**Java语言和C、C++语言的一个比较大的区别就是，Java语言不用关心它的内存开辟与释放，而是交给JVM去处理**；所以要好好理解它的回收机制，当出现问题时才能上手分析；

**1.引用计数法** 

给对象增加一个计数器，当有引用它时，计数器就加一，当引用失效时，计数器就减一

JVM并没有采用这种方式来判断对象是否已死

**原因**：**循环引用会导致引用计数法失效**，循环引用就是A类中一个属性引用了B类对象，B类中一个属性引用了A类对象，这样一来，就算你把A类和B类的实例对象引用置为null，它们还是不会被回收；
循环引用例码：

```java
public class MyObject {
    public Object ref = null;
    public static void main(String[] args) {
        MyObject myObject1 = new MyObject();
        MyObject myObject2 = new MyObject();
        myObject1.ref = myObject2;
        myObject2.ref = myObject1;
        myObject1 = null;
        myObject2 = null;
    }
}
```

但是具体是为什么呢？当代码执行完line7时，两个对象的引用计数均为2。此时将myObject1和myObject2分别置为null，以前一个对象为例，它的引用计数将减1。若要满足垃圾回收的条件，需要清除myObject2中的ref这个引用，而要清除掉这个引用的前提条件是myObject2引用的对象被回收，可是该对象的引用计数也为1，因为myObject1.ref指向了它。以此类推，也就进入一种**死循环**的状态。

**2.可达性分析法** 

> 4. **Java则是用了这种方法来判断是否需要回收对象；** 
>    
>    > 此算法的核心思想为 : 通过一系列称为"GC Roots"的对象作为起始点，从这些节点开始向下搜索，搜索走过的路径称之为"引用链"，当一个对象到GC Roots没有任何的引用链相连时(从GC Roots到这个对象不可达)时，证明此对象是不可用的；
>    
>    可作为GC Roots的对象有以下几种：
>    
>    1. **虚拟机栈(栈帧中的本地变量表)中引用的对象**
>    2. **方法区中类静态属性引用的对象**
>    3. **方法区中常量引用的对象**
>    4. **本地方法栈中JNI(Native方法)引用的对象** 
>    5. **已启动且未停止的 Java 线程** （其他地方而知）
>    
>    > 在JDK1.2之后，Java补充了几种引用方式，来对垃圾回收进行更合理的管理：
>    >
>    > 1. 强引用 : 强引用指的是在程序代码之中普遍存在的，类似于"Object obj = new Object()"这类的引用，只要强引用还存在，垃圾回收器永远不会回收掉被引用的对象实例。
>    > 2. 软引用 : 软引用是用来描述一些还有用但是不是必须的对象。对于软引用关联着的对象，在系统将要发生内存溢出之前，会把这些对象列入回收范围之中进行第二次回收。如果这次回收还是没有足够的内存，才会抛出内存溢出异常。在JDK1.2之后，提供了SoftReference类来实现软引用。
>    > 3. 弱引用 : 弱引用也是用来描述非必需对象的。但是它的强度要弱于软引用。被弱引用关联的对象只能生存到下一次垃圾回收发生之前。当垃圾回收器开始进行工作时，无论当前内容是否够用，都会回收掉只被弱引用关联的对象。在JDK1.2之后提供了WeakReference类来实现弱引用。
>    > 4. 虚引用 : 虚引用也被称为幽灵引用或者幻影引用，它是最弱的一种引用关系。一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，也无法通过虚引用来取得一个对象实例。为一个对象设置虚引用的，唯一目的就是能在这个对象被收集器回收时收到一个系统通知。在JDK1.2之后，提供了PhantomReference类来实现虚引用。
>    
>    #### 1. 可达性分析法存在的问题
>    
>    > 可达性分析可以解决引用计数法所不能解决的循环引用问题。举例来说，即便对象 a 和 b 相互引用，只要从 GC Roots 出发无法到达 a 或者 b，那么可达性分析便不会将它们加入存活对象合集之中。
>    > 虽然可达性分析的算法本身很简明，但是在实践中还是有不少其他问题需要解决的。
>    
>    比如说，**在多线程环境下，其他线程可能会更新已经访问过的对象中的引用，从而造成漏报（将引用设置为 null）或者误报（将引用设置为未被访问过的对象）**。🌟🌟
>    
>    + **漏报并没有什么伤害，Java 虚拟机至多损失了部分垃圾回收的机会。**（即经过GC Roots链查找后，某个对象有到这个GC Roots的可达，不应该被回收，但现在GC Roots这个引用置空了，即不再引用那个对象了，那个对象则现在应该被回收，但这次并没有被判断为要被回收，这种情况就是这次回收没回收到它而已，即损失了一次回收，影响不大，下次总能回收它！）
>    + **误报则比较麻烦，因为垃圾回收器可能回收事实上仍被引用的对象内存。一旦从原引用访问已经被回收了的对象，则很有可能会直接导致 Java 虚拟机崩溃。**🌟
>    
>    ---
>    
>    **那么Java虚拟机是如何来解决这种问题的呢？**
>    
>    归根到底是因为在进行垃圾回收的时候存在其他线程的干扰导致上面的误报和漏报！下面来看解决办法：
>    
>    #### 2. Stop-the-world 以及安全点
>    
>    在 Java 虚拟机里，传统的垃圾回收算法采用的是一种简单粗暴的方式，那便是 Stop-the-world，停止其他非垃圾回收线程的工作，直到完成垃圾回收。这也就造成了垃圾回收所谓的暂停时间（GC pause）。
>    
>    Java 虚拟机中的 **Stop-the-world 是通过安全点（safepoint）机制来实现的**。当 Java 虚拟机收到 Stop-the-world 请求，它便**会等待所有的线程都到达安全点，才允许请求 Stop-the-world 的线程进行独占的工作**。
>    当然，安全点的初始目的并不是让其他线程停下，而是找到一个稳定的执行状态。在这个执行状态下，Java 虚拟机的堆栈不会发生变化。这么一来，垃圾回收器便能够“安全”地执行可达性分析。**举个例子，当 Java 程序通过 JNI 执行本地代码时，如果这段代码不访问 Java 对象、调用 Java 方法或者返回至原 Java 方法，那么 Java 虚拟机的堆栈不会发生改变，也就代表着这段本地代码可以作为同一个安全点**。只要不离开这个安全点，Java 虚拟机便能够在垃圾回收的同时，继续运行这段本地代码。由于本地代码需要通过 JNI 的 API 来完成上述三个操作，因此 Java 虚拟机仅需在 API 的入口处进行安全点检测（safepoint poll），测试是否有其他线程请求停留在安全点里，便可以在必要的时候挂起当前线程。
>    
>    **除了执行 JNI 本地代码外，Java 线程还有其他几种状态：** 
>    
>    + **解释执行字节码**
>      + 对于解释执行来说，字节码与字节码之间皆可作为安全点。Java 虚拟机采取的做法是，当有安全点请求时，执行一条字节码便进行一次安全点检测。
>    + **此时线程处于阻塞状态**
>      + 阻塞的线程由于处于 Java 虚拟机线程调度器的掌控之下，因此属于安全点。
>    + **执行即时编译器生成的机器码**
>      + 执行即时编译器生成的机器码则比较复杂。由于这些代码直接运行在底层硬件之上，不受 Java 虚拟机掌控，因此在生成机器码时，即时编译器需要插入安全点检测，以避免机器码长时间没有安全点检测的情况。**HotSpot 虚拟机的做法便是在生成代码的方法出口以及非计数循环的循环回边（back-edge）处插入安全点检测**。
>      + 那么为什么不在每一条机器码或者每一个机器码基本块处插入安全点检测呢？原因主要有两个。
>        + 第一，安全点检测本身也有一定的开销。不过 HotSpot 虚拟机已经将机器码中安全点检测简化为一个内存访问操作。在有安全点请求的情况下，Java 虚拟机会将安全点检测访问的内存所在的页设置为不可读，并且定义一个 segfault 处理器，来截获因访问该不可读内存而触发 segfault 的线程，并将它们挂起。
>        + 第二，即时编译器生成的机器码打乱了原本栈桢上的对象分布状况。**在进入安全点时，机器码还需提供一些额外的信息，来表明哪些寄存器，或者当前栈帧上的哪些内存空间存放着指向对象的引用，以便垃圾回收器能够枚举 GC Roots**。由于这些信息需要不少空间来存储，因此即时编译器会尽量避免过多的安全点检测。
>
> ### 枚举根节点
>
> 何为枚举根节点？
> 我们先回忆一下Java判定对象是否回收的方法是**可达性分析法**，前面已经了解到，这是通过一些GC Roots的节点来进行延申查询哪些对象与它存在引用链，没存在引用链上的对象就是可回收对象；
>
> 那么问题来了，**作为GC Roots的节点主要为：**
>
> + 方法区中（即类中属性）常量、静态变量引用的对象
> + 虚拟机栈中（即局部变量）引用的对象
>
> 我们知道，在现在的应用中，一个程序的方法区都上了数百兆，里面的引用数不胜数，如果我们**挨个去判断每个引用是不是GC Roots**，然后来判断这些GC Roots引用链中的对象，**得花费虚拟机多大的时间** ，所以我们得用到枚举根节点；
>
> **枚举根节点特点：** 
>
> + **枚举根节点前必须停止其他所有工作线程的执行**，因为这样才能保障在此时间段中对象的引用不会变化，从而保障枚举根节点工作的准确性；
>
> **在HotSpot实现中，利用了空间换取时间，是使用一组OopMap的数据结构来完成的。类加载完成后，JVM会把对象内什么偏移量上是什么类型的数据计算出来，在JIT编译过程中，在安全点处使用OopMap记录下栈和寄存器哪些位置是引用，这样GC发生的时候就不用全部扫描了。**  
>
> ### 安全点
>
> 在**OopMap**的协助下，我们可以快速的完成GC Roots枚举，但我们也不能随时随地都生成OopMap，那样一方面会需要更多的空间来存放这些对象，另一方面效率也会简单低下。所以只会在特定的位置来记录一下，主要是正在：
>
> + 循环的末尾
> + 方法临返回前/调用方法的call指令后
> + 可能抛异常的位置
>
> **这些位置称为安全点；** 
>
> **在程序到达安全点时，就会暂停当前的线程然后开始进行GC，所以安全点的选择和数量尤为重要，太少会导致GC等待时间过长，太多又会导致工作效率低下；**
>
>  
>
> 又有一个问题随之而来，如何在GC发生时让所有的线程都跑到最近的安全点上再停顿下来，这里有两种方案：
>
> #### 1. 抢先式中断
>
> 就是**先中断所有的线程，如果发现有的线程没在安全点上，就继续执行该线程使之跑到安全点上，当所有线程都达到安全点时，就开始GC；**
> 大家想想，这种方法显然不咋地嘛！！你要考虑什么时候中断所有线程，这样会显得很盲目，现在几乎**没有虚拟机实现这种方式来暂停线程从而相应GC；**
>
> #### 2. 主动式中断
>
> 主动式中断是让GC在需要中断线程的时候不直接对线程操作，设置一个标志，这个标志的地方与安全点是重合的， 让各个线程主动轮询这个标志，如果中断标志位为真时就让自己中断然后响应GC。 



> **如何判断一个常量是废弃常量：**🌟
>
> 运行时常量池主要回收的是废弃的常量。那么，我们如何判断一个常量是废弃常量呢?
> **假如在常量池中存在字符串"abc" ,如果当前没有任何String对象引用该字符串常量的话，就说明常量"abc"就是废弃常量,如果这时发生内存回收的话而且有必要的话，" abc"就会被系统清理出常量池。**
>
> 注意:JDK1.7及之后版本的JVM已经将运行时常量池从方法区中移了出来,在Java堆(Heap) 开辟了一块区域存放运行时常池；

### 3. 垃圾回收算法

**在上面，我讲述了Java虚拟机如何判断一个对象是否需要回收，这里就来讲讲当判定需要回收的时候，虚拟机是如何回收”垃圾“的；**

**1.标记——清除法** 

根据名称就可以想到，该回收算法是将需要回收的对象进行标记，然后在**标记完所有该回收的对象后进行统一回收；**

**下面来看这张图，可一目了然：**

[![在这里插入图片描述](https://img-blog.csdnimg.cn/20190503161947412.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)](https://img-blog.csdnimg.cn/20190503161947412.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)

当进行回收之后，可以明显发现这种方法的不足：

- **标记清除后会产生大量不连续的内存碎片** ，空间碎片太多可能会导致以后在程序运行中需要分配较大
  对象时，无法找到足够连续内存而不得不提前触发另一次垃圾收集；
- 还有一个问题就是，其实标记和清除这两个过程的**效率都比较低；**

**2. 复制算法（新生代回收算法（Minor GC））** 

顾名思义：这种算法我们采用一分为二的思想，每次将内存一分为二，每次只使用一半，当发生垃圾回收时，我们将这一半中的存活对象依次复制到另一半中，然会对这一半内存进行一次性清除；
**这样一来，是不是解决了标记清楚法的两大不足🙂；**

**呈上一图，一目了然：**
[![1556866909630](https://img-blog.csdnimg.cn/20190503162027606.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)](https://img-blog.csdnimg.cn/20190503162027606.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)

[![1556866920389](https://img-blog.csdnimg.cn/20190503162041313.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)](https://img-blog.csdnimg.cn/20190503162041313.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)

> 以这种算法为思想，稍作一点改进，就是我们的新生代回收算法；
> 具体改进：
>
> - 内存不按一比一进行划分，而是将内存（新生代内存）划分为一块较大的Eden（伊甸园）空间，和两块较小的survivor（幸存者）空间；
> - 两块survivor空间，一块叫From区，一块叫To区；
> - Eden区与survivor区的比例为8：1；
> - Eden区与From区用来使用，此时可使用空间提升到了90%；

**回收具体步骤：**

1. **当Eden区满的时候,会触发第一次Minor gc,把还活着的对象拷贝到Survivor From区；当Eden区再次触发**
   **Minor gc的时候,会扫描Eden区和From区域,对两个区域进行垃圾回收,经过这次回收后还存活的对象,则直**
   **接复制到To区域,并将Eden和From区域清空。**
2. **当后续Eden又发生Minor gc的时候,会对Eden和To区域进行垃圾回收,存活的对象复制到From区域,并将**
   **Eden和To区域清空。**
3. **部分对象会在From和To区域中复制来复制去,如此交换15次(由JVM参数MaxTenuringThreshold决定,这个**
   **参数默认是15),最终如果还是存活,就存入到老年代** 。

**为啥这是新生代回收算法** ？

这种算法在新生代中被商用虚拟机大量采用，包括Hotspot虚拟机；

**到这里很多人疑问，这样一来，只有10%的内存留给我们的存活对象，这难道不会不够吗？**
**答案：之所以叫新生代，这里的对象大都具备朝生夕死的特点，所以存活下来的很少；**

**3.标记——整理算法（老年代回收算法（Full GC））** 

在老年带中，这里的大多数对象都不会被回收，采用复制算法的话会带来大量的复制操作，所以采用上面的算法是显然不可行的；

这里我们采用标记整理算法，这种算法就是对标记清楚法进行了一个改进：

- 在进行回收的时候，不是进行标记完后进行统一回收，而是让所有存活对象向一端移动，然后直接清理掉剩余的空间；

呈上一图：

[![1556870868806](https://img-blog.csdnimg.cn/20190503162111199.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)](https://img-blog.csdnimg.cn/20190503162111199.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)

**4.分代收集算法（Java虚拟机所采用）** 

分代收集算法其实就是**结合了复制算法和标记整理算法；**

**当前JVM垃圾收集都是采用的这种算法** ，即根据对象的存活时间不同，将内存划分为几个区域；
**一般是把Java堆分为新生代和老年代** 。在新生代中，每次垃圾回收都有大批对象死去，只有少量存活，因此我们采用复制算法；而老年代中对象存活率高、没有额外空间对它进行分配担保，就必须采用”标记-清理”或者”标记-理”算法；





### 4. Java内存模型（JMM）

> **官方术语：**java内存模型(Java Memory Model，JMM)是java虚拟机规范定义的，用来屏蔽掉java程序在各种不同的硬件和操作系统对内存的访问的差异，这样就可以实现java程序在各种不同的平台上都能达到内存访问的一致性。可以避免像c++等直接使用物理硬件和操作系统的内存模型在不同操作系统和硬件平台下表现不同，比如有些c/c++程序可能在windows平台运行正常，而在linux平台却运行有问题。

一定不要将Java内存模型和Java运行时数据区域搞混了，根本不是一个概念。
可以先从几个例子入手：

+ 多个线程对同一个值进行自加（i++）时，造成最终值偏小；

+ ```java
  //线程1:
  context = loadContext();   //语句1
  inited = true;             //语句2
   
  //线程2:
  while(!inited ){
    sleep()
  }
  doSomethingwithconfig(context);
  ```

  当线程1中语句1和语句2发生指令重排序时，会导致线程2在context没有初始化的情况下就去执行dosomething（）造成错误！！

可以看到上面的两个例子：

+ 第一个是因为Java存在主内存和工作内存，而工作内存不一致导致的，没有保证原子性（i++不是原子性的），也没有保证可见性（下一个线程的读取并不一定读取到的是前一个线程加一后的值）
+ 第二个是因为线程一在执行的时候没有保证有序性；

这两个例子都是Java内存模型惹的祸，第一个是工作内存和主内存策略惹的祸，第二个是Java编译器优化惹的祸，对于这种情况，你可能还去怪Java，**但是，Java 语言规范将其归咎于应用程序没有作出恰当的同步操作**。Java 内存模型与 happens-before 关系为了让应用程序能够免于数据竞争的干扰，**Java 5 引入了明确定义的 Java 内存模型。其中最为重要的一个概念便是 happens-before 关系**。happens-before 关系是用来描述两个操作的内存可见性的。如果操作 X happens-before 操作 Y，那么 X 的结果对于 Y 可见。

> 大家都知道，计算机在执行程序时，**每条指令都是在CPU中执行的**，而执行指令过程中，势必涉及到数据的读取和写入。由于程序运行过程中的临时数据是存放在**主存（物理内存）**当中的，这时就存在一个问题，由于CPU执行速度很快，而从内存读取数据和向内存写入数据的过程跟CPU执行指令的速度比起来要慢的多，因此如果任何时候对数据的操作都要通过和内存的交互来进行，**会大大降低指令执行的速度**。因此在CPU里面就有了**高速缓存**。    
>
> 也就是，当程序在运行过程中，会将运算需要的数据从主存复制一份到CPU的高速缓存当中，那么CPU进行计算时就可以直接从它的高速缓存读取数据和向其中写入数据，当运算结束之后，再将高速缓存中的数据刷新到主存当中。
>
> 上面说了那么一堆，那么在Java中，内存模型又是怎样的呢？
> 当然，在Java中规定了自己的内存模型（Java Memory Model，JMM），上面那种优化也得必须汲取，**只是对上述优化进行了一波抽象**。JMM规定：
>
> + 所有的变量都在**主内存**中，类似于上面提到的**普通内存**； 
> + 每个线程又包含自己的**工作内存**，为了便于理解可以看成**CPU上的寄存器或者高速缓存**。因此，线程的操作都是以工作内存为主，**它们只能访问自己的工作内存（一个线程不能访问另一个线程的工作内存）**，并且在工作之前和之后，**该值被同步回主内存**，但是这个同步回去的时间就不确定了； 
>
> ---
>
> 而对于指令重排序，为什么需要指令重排序呢？因为一个汇编指令也会涉及到很多步骤，每个步骤可能会用到不同的寄存器，现在的CPU一般采用流水线来执行指令，也就是说，CPU有多个功能单元（如获取、解码、运算和结果），一个指令的执行被分成：取指、译码、访存、执行、写回、等若干个阶段，流水线是并行的, 第一条指令执行还没完毕，就可以执行第二条指令，前提是这两条指令功能单元相同或类似，所以一般可以通过指令重排使得具有相似功能单元的指令接连执行来减少流水线中断的情况。
> 在Java中的指令重排序，对于单线程编译器会保证最终结果一致，但是多线程我们就不敢保证了；

针对上面的问题，不能怪Java的内存模型，而是怪你自己不会用Java内存模型提供的happens-before原则！！！

**假如你很了解happens-before原则：** （给面试官扯happens-berore原则，因为JMM核心就是它）

+ 对于问题一，你可以给给自增操作加锁就解决了原子性、可见性、有序性三个问题，因为happens-before原则规定一个unLock操作先行发生于后面对同一个锁额lock操作。
+ 而对于问题二，你要是了解happens-before原则，你会给init字段设置为volatile类型，它能保证执行inited=true语句执行时，前面的初始化操作一定已经完成，所以线程二读取到init为true时，初始化也就一定完成了；这里体现了有序性；也可以提现可见性，即当线程一执行完init=true后，线程二下一次在判断inited时候一定是去主内存读取inited，因为volatile变量的写会导致所有缓存失效并会将更新后的值立即写入主内存！

> 在Java虚拟机规范中试图定义一种Java内存模型（Java Memory Model，JMM）来屏蔽各个硬件平台和操作系统的内存访问差异，以实现让Java程序在各种平台下都能达到一致的内存访问效果。那么Java内存模型规定了哪些东西呢，它定义了程序中变量的访问规则，往大一点说是定义了程序执行的次序。注意，为了获得较好的执行性能，Java内存模型并没有限制执行引擎使用处理器的寄存器或者高速缓存来提升指令执行速度，也没有限制编译器对指令进行重排序。**也就是说，在java内存模型中，也会存在缓存一致性问题和指令重排序的问题。** 



### 5. happens-before原则

另外，**Java内存模型具备一些先天的“有序性”**，即不需要通过任何手段就能够得到保证的有序性，这个通常也称为 happens-before 原则。如果两个操作的执行次序无法从happens-before原则推导出来，那么它们就不能保证它们的有序性，虚拟机可以随意地对它们进行重排序。 

下面就来具体介绍下happens-before原则（先行发生原则）：

- 程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作
- 锁定规则：一个unLock操作先行发生于后面对同一个锁额lock操作
- volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作
- 传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C
- 线程启动规则：Thread对象的start()方法先行发生于此线程的每个一个动作
- 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生
- 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行
- 对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始

下面我们来解释一下前4条规则：

**1. 程序次序规则⭐（不要认为有了它就真正的“有序”了）** 

对于程序次序规则来说，我的理解就是一段程序代码的执行在**单个线程中看起来是有序的**。注意，虽然这条规则中提到“书写在前面的操作先行发生于书写在后面的操作”，这个应该是程序看起来执行的顺序是按照代码顺序执行的，**因为虚拟机可能会对程序代码进行指令重排序**。虽然进行重排序，但是最终执行的结果是与程序顺序执行的结果一致的，它只会对不存在数据依赖性的指令进行重排序。因此，在单个线程中，程序执行看起来是有序执行的，这一点要注意理解。事实上，**这个规则是用来保证程序在单线程中执行结果的正确性，但无法保证程序在多线程中执行的正确性。 ** 

**2. 锁定规则** 

第二条规则也比较容易理解，也就是说无论在单线程中还是多线程中，同一个锁如果出于被锁定的状态，那么必须先对锁进行了释放操作，后面才能继续进行lock操作。

**3. volatile变量规则** 

第三条规则是一条比较重要的规则，也是后文将要重点讲述的内容。直观地解释就是，如果一个线程先去写一个变量，然后一个线程去进行读取，那么写入操作肯定会先行发生于读操作。

**4. 传递规则** 

第四条规则实际上就是体现happens-before原则具备传递性。

### 6. Java中new 一个对象过程中发生了什么？ 

Java在new一个对象的时候，会首先查看这个对象所属的类是否被加载到了内存，如果没有的话，则需要先进行该类的类加载过程

假设这个类是**第一次使用**，这样的话new 一个对象就得分为两个大块：

- 加载并初始化类（第一次加载哟）
- 创建对象

**一、类加载过程（第一次使用该类）** 

**虚拟机把描述类的class文件加载到内存，并对数据进行校验、转换解析和初始化，最终形成可以被虚拟机直接使用的Java类型，这整个过程就是虚拟机的类加载机制；** 

**先讲一讲类加载器，即进行类加载的工具（代码），pian一pian双亲委派模型：** 

> java是使用**双亲委派模型**来进行类的加载的，所以在描述类加载过程前，我们先看一下它的工作过程： 双亲委托模型的工作过程是：如果一个类加载器（ClassLoader）收到了类加载的请求，它首先不会自己去尝试加载这个类，而是把这个请求委托给父类加载器去完成，每一个层次的类加载器都是如此，因此所有的加载请求最终都应该传送到顶层的启动类加载器中，只有当父类加载器反馈自己无法完成这个加载请求（它的搜索范围中没有找到所需要加载的类）时，子加载器才会尝试自己去加载  **使用双亲委托机制的好处是：能够有效确保一个类的全局唯一性，当程序中出现多个限定名相同的类时，类加载器在执行加载时，始终只会加载其中的某一个类。**  （防止用户自定义类影响jdk原生类、防止类的重复加载）  

1、加载  

- 由类加载器负责根据一个类的全限定名来读取此类的二进制字节流到JVM内部，**并存储在运行时内存区的方法区，然后将其转换为一个与目标类型对应的java.lang.Class对象实例**  ； 

- 何时开始加载？

  + Java虚拟机规范中并没有进行强制约束，然而对于初始化阶段，虚拟机规范则是严格规定了**有且只有（⭐）如下5种情况必须立即对类进行初始化**（而加载、验证、准备自然会在初始化之前） 

    + （1）：**使用new关键字实例化对象的时候、读取或设置一个类的静态字段（被final修饰、已在编译器把结果放入常量池的静态字段除外）的时候、调用一个类的静态方法的时候；** 
    + （2）：**使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没进行过初始化，则需要先触发其初始化；**
    + （3）：**当初始化一个类的时候，如果发现其父类还没有初始化，则需要先对其父类进行初始化；** 
    + （4）：**当虚拟机启动的时候，用户需要指定一个要执行的主类（即包含main方法的那个类），虚拟机会先初始化这个主类；** 
    + （5）：当使用jdk1.7的动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄，并且这个方法句柄所对应的类没有初始化，则需要先触发其初始化；

    **这五种场景称为：对一个类的主动引用**，除此之外，其他所有对类的引用方法都不会触发其初始化，称为被动引用； 

**2、验证**   

- 验证这一阶段的目的就是： 

  + **确保Class文件中的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全；** 

  这个阶段是非常重要的，它的严谨决定了Java虚拟机是否会遭受恶意代码的攻击！！！

  验证阶段又细分为如下四个阶段：

  + **（1）：文件格式验证** 
    + 验证字节流是否符合Class文件格式的规范，并且能被当前版本的虚拟机处理；
  + **（2）：元数据验证（实质就是语法规范分析）** 
    + 对字节码描述的信息进行语义分析，以保证其描述的信息符合Java语言规范，具体验证内容如下：
      + 这个类是否有父类（除了java.lang.object外其他类都应该有父类）
      + 这个类是否继承了不应该继承的类（比如继承了final修饰的类）
      + 这个类不是抽象类的话，是否实现了继承的接口或抽象类中的方法
      + .............
  + **（3）：字节码验证** 
    + 这个阶段比较复杂，主要目的是通过数据流和控制流分析，确定程序语义是合法的，符合逻辑的；
  + （4）符号引用验证
    + 所谓符号引用，就是在class文件被加载到JVM之前，这个类是无法知道其他类及其方法、字段所对应的具体位置，甚至不知道自己的方法、字段的地址，因此，在这个类中每当需要引用这些成员时，编译器会生成一个符号引用，在运行阶段，这个符号引用一般能够无歧义的定位到具体目标上，比如说对于一个方法的调用，编译器会生成一个包含目标方法所在类的名字、目标方法的名字、接收参数类型以及返回值类型的符号引用来指代所要调用的方法；

  **注意：对于虚拟机的验证阶段来说，是一个非常重要的但又不是必要的阶段；**如果所运行的全部代码都已经被反复验证和使用过，则可以通过**-Xverify：none**参数来关闭大部分的类验证措施，以缩短虚拟机类加载的时间；

3、准备  

- **为类中的所有静态变量分配内存空间（方法区中分配）**，并为其设置一个初始值（由于还没有产生对象，实例变量不在此操作范围内），这里的初始值注意是类型的一个默认值，比如int默认为零；
- 注意：被final修饰的static变量（常量），会直接赋值；   

4、解析  

- **将符号引用转为直接引用**（得到类或者字段、方法在内存中的指针或者偏移量，以便直接调用该方法）；

5、**初始化**（先父后子，如果有父类，先对父类进行加载再对子类加载）

+ 初始化阶段是类加载的最后一个阶段，简单来说就是为常量赋值和执行< client >构造器；Java虚拟机会通过加锁来确保类的< client >方法仅被执行一次，即我们的静态代码只会被执行一次，所以是线程安全的，所以我们的单例模式（饿汉）才能得到保障；
  + 如果我们的静态字段被final修饰的时候并且他的类型为基本类型或字符串的时候，此时这个字段会被Java编译器标记为常量值，其初始化直接由Java虚拟机完成，除此之外的静态字段或者静态代码块中的代码则会被Java编译器置于同一个方法中，这个方法也就是< client >构造器；  

- **最终，方法区会存储当前类类信息，包括类的静态变量、类初始化代码（定义静态变量时的赋值语句 和 静态初始化代码块）、实例变量定义、实例初始化代码（定义实例变量时的赋值语句实例代码块和构造方法）和实例方法，还有父类的类信息引用**。  

**二、创建对象** 

1、**在堆区分配对象需要的内存**   

- 分配的内存包括本类和父类的所有实例变量，**但不包括任何静态变量**  （静态变量在方法区嘛，共有的）

2、**对所有实例变量赋默认值**   

- 将方法区内对实例变量的定义拷贝一份到堆区，然后赋默认值  

3、**执行实例初始化代码**   

- 初始化顺序是先初始化父类再初始化子类，**初始化时先执行实例代码块然后是构造方法**  ；

4、如果有类似于Child c = new Child()形式的c引用的话，在栈区定义Child类型引用变量c，然后将堆区对象的地址赋值给它 ；



### 7. Java存在内存泄露吗？

内存泄露就是堆内存中不再使用的对象，但是垃圾回收期无法从内存中删除他们的情况，因此他们会被不必要的一直存在，**这种情况会耗尽内存资源并降低系统性能，最终以OOM终止。**

垃圾回收器会定期删除未引用的对象，但它永远不会收集那些仍在引用的对象。

内存泄露的症状：

+ 应用程序长时间连续运行时性能严重下降；
+ 应用程序中的OutOfMemoryError堆错误；
+ 自发且奇怪的应用程序崩溃；
+ 应用程序偶尔会耗尽连接对象。

**内存泄漏是指一个不再被程序使用的对象或变量还在内存中占有存储空间。** 

在c/c++语言中，内存的分配和释放是由开发人员负责的。

**在java语言中，判断一个内存空间是否符合垃圾回收的标准有两个：** 

（一）给对象赋予了null，以后再没有使用过；

（二）给对象赋予了新值，重新分配了内存空间。

**在java语言中，内存泄漏的原因很多：** 

1.**静态集合类**：例如static HashMap和static Vector，由于它们的生命周期与程序一致，那么容器中的对象在程序结束之前将不能被释放。

+ 解决办法：最大限度的减少静态变量的使用；单例模式时，依赖于延迟加载对象而不是立即加载方式。

2.各种连接：例如数据库连接、网络连接和IO连接等，当不再使用时，需调用close()方法来释放连接。

+ 每当创建连接或者打开流时，JVM都会为这些资源分配内存。如果没有关闭连接，会导致持续占有内存。在任意情况下，资源留下的开放连接都会消耗内存，如果我们不处理，就会降低性能，甚至OOM。
  解决办法：**使用finally块关闭资源；关闭资源的代码，不应该有异常；jdk1.7后，可以使用try-with-resource块。** 

3.变量不合理的作用域：一个变量定义的作用范围大于其使用范围（例如一个本可以定义为方法内局部变量的变量，却被定义为程序对象内的全局变量），并且在使用完后没有及时地把它设为null。

4.如果我们读取一个很大的String对象，并调用了inter(），那么它将放到字符串池中，位于PermGen中，只要应用程序运行，该字符串就会保留，这就会占用内存，可能造成OOM。

解决办法：增加PermGen的大小，-XX:MaxPermSize=512m；升级Java版本，JDK1.7后字符串池转移到了堆中。

5.**使用ThreadLocal造成内存泄露**

+ 使用ThreadLocal时，每个线程只要处于存货状态就可保留对其ThreadLocal变量副本的隐式调用，且将保留其自己的副本。使用不当，就会引起内存泄露。
+ 一旦线程不在存在，ThreadLocals就应该被垃圾收集，而现在线程的创建都是使用线程池，线程池有线程重用的功能，因此线程就不会被垃圾回收器回收。所以使用到ThreadLocals来保留线程池中线程的变量副本时，ThreadLocals没有显示的删除时，就会一直保留在内存中，不会被垃圾回收。
+ 解决办法：不在使用ThreadLocal时，调用remove()方法，该方法删除了此变量的当前线程值。不要使用ThreadLocal.set(null)，它只是查找与当前线程关联的Map并将键值对设置为当前线程为null。





　　一：**当长生命周期的对象持有短生命周期的对象的引用，就很可能发生内存泄漏。尽管短生命周期的对象已经不再需要，但是长生命周期的对象一直持有它的引用导致其无法被回收。例如，缓存系统；加载一个对象放在缓存系统中，一直不去使用这个对象，但是它一直被缓存引用，所以不会被回收导致缓存泄漏。** 

### 8. Java遇到内存溢出时如何处理？

**内存溢出**是指应用系统中存在无法回收的内存或使用的内存过多，最终使得程序运行要用到的内存大于虚拟机能提供的最大内存。

  引起内存溢出的**原因**有很多种，常见的有以下几种：
　　1.内存中加载的数据量过于庞大，如一次从数据库取出过多数据；
　　2.集合类中有对对象的引用，使用完后未清空，使得JVM不能回收；
　　3.代码中存在死循环或循环产生过多重复的对象实体；
　　4.使用的第三方软件中的BUG；
　　5.启动参数内存值设定的过小；

内存溢出的**解决方案**：
   **第一步**，修改JVM启动参数，直接增加内存。(-Xms，-Xmx参数一定不要忘记加。)

　　**第二步**，检查错误日志，查看“OutOfMemory”错误前是否有其它异常或错误。

　　**第三步**，对代码进行走查和分析，找出可能发生内存溢出的位置。

重点排查以下几点：
　　1.检查对数据库查询中，是否有一次获得全部数据的查询。一般来说，如果一次取十万条记录到内存，就可能引起内存溢出。这个问题比较隐蔽，在上线前，数据库中数据较少，不容易出问题，上线后，数据库中数据多了，一次查询就有可能引起内存溢出。因此对于数据库查询尽量采用分页的方式查询。
　　2.检查代码中是否有死循环或递归调用。 

　　3.检查是否有大循环重复产生新对象实体。 

　　4.检查对数据库查询中，是否有一次获得全部数据的查询。一般来说，如果一次取十万条记录到内存，就可能引起内存溢出。这个问题比较隐蔽，在上线前，数据库中  数据较少，不容易出问题，上线后，数据库中数据多了，一次查询就有可能引起内存溢出。因此对于数据库查询尽量采用分页的方式查询。 

　　5.检查List、MAP等集合对象是否有使用完后，未清除的问题。List、MAP等集合对象会始终存有对对象的引用，使得这些对象不能被GC回收。

以上内容来自：https://www.cnblogs.com/dyh004/p/8296958.html

### 9. MinorGC和FullGC的触发机制

**堆内存分为：Eden、Survivor（2个，from和to）（这三个合称新生代） 和 Tenured/Old（老年代） 空间，如下图所示：** 
![img](http://img.blog.csdn.net/20150731163641941?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

从年轻代空间（包括 Eden 和 Survivor 区域）回收内存被称为 Minor GC，对老年代GC称为Major GC,**而Full GC是对整个堆来说的**，在最近几个版本的JDK里默认包括了对永生带即方法区的回收（JDK8中无永生带了），**出现Full GC的时候经常伴随至少一次的Minor GC,但非绝对的**。Major GC的速度一般会比Minor GC慢10倍以上。下边看看有哪种情况触发JVM**进行Full GC及应对策略。** 

+ **System.gc()方法的调用会建议虚拟机进行一次fullGC，但不是一定执行，尽量不要去调用这个方法；**
+ 老年代空间不足时会触发Full GC；
+ 统计得到的Minor GC晋升到老年代的平均大小大于老年代的剩余空间；
  + 这是一个较为复杂的触发情况，**Hotspot为了避免由于新生代对象晋升到旧生代导致旧生代空间不足的现象，在进行Minor GC时，做了一个判断，如果之前统计所得到的Minor GC晋升到旧生代的平均大小大于旧生代的剩余空间，那么就直接触发Full GC。** 
    例如程序第一次触发Minor GC后，有6MB的对象晋升到旧生代，那么当下一次Minor GC发生时，首先检查旧生代的剩余空间是否大于6MB，如果小于6MB，则执行Full GC。
+ **堆中分配很大的对象** ；
  + **所谓大对象，是指需要大量连续内存空间的java对象，例如很长的数组，此种对象会直接进入老年代**，而老年代虽然有很大的剩余空间，但是无法找到足够大的连续空间来分配给当前对象，此种情况就会触发JVM进行Full GC。（**我们的老年代GC器一般使用的CMS收集器，这种收集器是采用标记清楚法来进行的，所以会产生内存碎片，但是提供了参数我们可以优化这个缺陷，具体参数在下面**）
  + 为解决这个问题，CMS垃圾收集器提供了一个可配置的参数，即-XX:+UseCMSCompactAtFullCollection开关参数，用于在“享受”完Full GC服务之后额外免费赠送一个碎片整理的过程，内存整理的过程无法并发的，空间碎片问题没有了，但提顿时间不得不变长了，JVM设计者们还提供了另外一个参数 -XX:CMSFullGCsBeforeCompaction,这个参数用于设置在执行多少次不压缩的Full GC后,跟着来一次带压缩的; 

其实远远比这些复杂，涉及不同的垃圾回收器，机制也有一些区别！  



### 10. JVM如何处理异常

在 Java 语言规范中，所有异常都是 Throwable 类或者其子类的实例。Throwable 有两大直接子类。

+ 第一个是 Error，涵盖程序不应捕获的异常。**当程序触发 Error 时，它的执行状态已经无法恢复，需要中止线程甚至是中止虚拟机**。

+ 第二子类则是 Exception，涵盖程序可能需要捕获并且处理的异常。Exception 有一个特殊的子类 RuntimeException，用来表示“程序虽然无法继续执行，但是还能抢救一下”的情况。前边提到的数组索引越界便是其中的一种。

RuntimeException 和 Error 属于 Java 里的非检查异常（unchecked exception）。其他异常则属于检查异常（checked exception）。在 Java 语法中，所有的检查异常都需要程序显式地捕获，或者在方法声明中用 throws 关键字标注。通常情况下，程序中自定义的异常应为检查异常，以便最大化利用 Java 编译器的编译时检查。异常实例的构造十分昂贵。**这是由于在构造异常实例时，Java 虚拟机便需要生成该异常的栈轨迹（stack trace）。该操作会逐一访问当前线程的 Java 栈帧，并且记录下各种调试信息，包括栈帧所指向方法的名字，方法所在的类名、文件名，以及在代码中的第几行触发该异常**。当然，在生成栈轨迹时，Java 虚拟机会忽略掉异常构造器以及填充栈帧的 Java 方法（Throwable.fillInStackTrace），直接从新建异常位置开始算起。此外，Java 虚拟机还会忽略标记为不可见的 Java 方法栈帧。我们在介绍 Lambda 的时候会看到具体的例子。既然异常实例的构造十分昂贵，我们是否可以缓存异常实例，在需要用到的时候直接抛出呢？从语法角度上来看，这是允许的。然而，该异常对应的栈轨迹并非 throw 语句的位置，而是新建异常的位置。因此，这种做法可能会误导开发人员，使其定位到错误的位置。这也是为什么在实践中，我们往往选择抛出新建异常实例的原因。



### 11. Class.forName 和 ClassLoader 有什么区别？

**在 Java 中 Class.forName() 和 ClassLoader 都可以对类进行加载**。ClassLoader 就是遵循双亲委派模型最终调用启动类加载器的类加载器，实现的功能是“通过一个类的全限定名来获取描述此类的二进制字节流”，获取到二进制流后放到 JVM 中。Class.forName() 方法实际上也是调用的 CLassLoader 来实现的。

Class.forName(String className)；这个方法的源码是：

![img](https://mmbiz.qpic.cn/mmbiz_png/HrWw6ZuXCsjTMiaADiaS3aEmgGeTxuWSVysqr0chxOzOspiaTicBWFGAqFmvOYk98kBdrIibrP498z7Rm0w77IKzKng/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

最后调用的方法是 forName0 这个方法，在这个 forName0 方法中的第二个参数被默认设置为了 true，**这个参数代表是否对加载的类进行初始化，设置为 true 时会类进行初始化，代表会执行类中的静态代码块，以及对静态变量的赋值等操作。**

也可以调用 Class.forName(String name, boolean initialize,ClassLoader loader) 方法来手动选择在加载类的时候是否要对类进行初始化。

下面还是举例来说明结果吧：一个含有静态代码块、静态变量、赋值给静态变量的静态方法的类。

```java
class MyTest {
    static {
        System.out.println("进行了初始化");
    }
}
public class Test5 {
    public static void main(String[] args) throws Exception{
        ClassLoader.getSystemClassLoader().loadClass("com.yy.test.MyTest");
        System.out.println("这里是界限｜这里是界限｜这里是界限｜这里是界限｜这里是界限｜这里是界限｜");

        Class.forName("com.yy.test.MyTest");
    }
}
```

> 输出：
> 这里是界限｜这里是界限｜这里是界限｜这里是界限｜这里是界限｜这里是界限｜
> 进行了初始化

**根据运行结果得出 Class.forName 加载类是将类进了初始化，而 ClassLoader 的 loadClass 并没有对类进行初始化，只是把类加载到了虚拟机中。** 🌟

**应用场景** 

在我们熟悉的 Spring 框架中的 IOC 的实现就是使用的 ClassLoader。

而在我们使用 JDBC 时通常是使用 Class.forName() 方法来加载数据库连接驱动。这是因为在 JDBC 规范中明确要求 Driver(数据库驱动)类必须向 DriverManager 注册自己。

以 MySQL 的驱动为例解释：

![img](https://mmbiz.qpic.cn/mmbiz_jpg/HrWw6ZuXCsjTMiaADiaS3aEmgGeTxuWSVyYJFUtaLwvznC4Vp1RujwX87Puib8549ZUlwlic3EoF8jWCNAzjibyFGeA/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

我们看到 Driver 注册到 DriverManager 中的操作写在了静态代码块中，这就是为什么在写 JDBC 时使用 Class.forName() 的原因了。



### 12. 四大引用（阿里常考）

DK1.2 之前，一个对象只有“已被引用”和"未被引用"两种状态，这将无法描述某些特殊情况下的对象，比如，当内存充足时需要保留，而内存紧张时才需要被抛弃的一类对象。

所以在 JDK.1.2 之后，Java 对引用的概念进行了扩充，将引用分为了：强引用（Strong Reference）、软引用（Soft Reference）、弱引用（Weak Reference）、虚引用（Phantom Reference）4 种，这 4 种引用的强度依次减弱。

强引用：

+ Java中默认声明的就是强引用，比如：

  ```java
  Object obj = new Object(); //只要obj还指向Object对象，Object对象就不会被回收
  obj = null;  //手动置null
  ```

  只要强引用存在，垃圾回收器将永远不会回收被引用的对象，哪怕内存不足时，JVM也会直接抛出OutOfMemoryError，不会去回收。如果想中断强引用与对象之间的联系，可以显示的将强引用赋值为null，这样一来，JVM就可以适时的回收对象了；

软引用：

+ 软引用是用来描述一些非必需但仍有用的对象。**在内存足够的时候，软引用对象不会被回收，只有在内存不足时，系统则会回收软引用对象，如果回收了软引用对象之后仍然没有足够的内存，才会抛出内存溢出异常**。这种特性常常被用来实现缓存技术，比如网页缓存，图片缓存等。
  在 JDK1.2 之后，用java.lang.ref.SoftReference类来表示软引用。来看个例子：

  ```java
  public class TestOOM {
      private static List<Object> list = new ArrayList<>();
      public static void main(String[] args) {
           testSoftReference();
      }
      private static void testSoftReference() {
          for (int i = 0; i < 10; i++) {
              byte[] buff = new byte[1024 * 1024];
              SoftReference<byte[]> sr = new SoftReference<>(buff);
              list.add(sr);
          }
          
          System.gc(); //主动通知垃圾回收
          
          for(int i=0; i < list.size(); i++){
              Object obj = ((SoftReference) list.get(i)).get();
              System.out.println(obj);
          }
          
      }
      
  }
  ```

  输出：![img](https://img2018.cnblogs.com/blog/662236/201809/662236-20180922194016719-117632363.png)

  我们发现无论循环创建多少个软引用对象，打印结果总是只有最后一个对象被保留，其他的obj全都被置空回收了。
  这里就说明了在内存不足的情况下，软引用将会被自动回收。
  值得注意的一点 , 即使有 byte[] buff 引用指向对象, 且 buff 是一个strong reference, 但是 SoftReference sr 指向的对象仍然被回收了，这是因为Java的编译器发现了在之后的代码中, buff 已经没有被使用了, 所以自动进行了优化。
  如果我们将上面示例稍微修改一下：

```java
    private static void testSoftReference() {
        byte[] buff = null;

        for (int i = 0; i < 10; i++) {
            buff = new byte[1024 * 1024];
            SoftReference<byte[]> sr = new SoftReference<>(buff);
            list.add(sr);
        }

        System.gc(); //主动通知垃圾回收
        
        for(int i=0; i < list.size(); i++){
            Object obj = ((SoftReference) list.get(i)).get();
            System.out.println(obj);
        }

        System.out.println("buff: " + buff.toString());
    }
```

​	则 buff 会因为强引用的存在，而无法被垃圾回收，从而抛出OOM的错误。
![img](https://img2018.cnblogs.com/blog/662236/201809/662236-20180922194030314-105853688.png)

​	**如果一个对象惟一剩下的引用是软引用，那么该对象是软可及的（softly reachable）。垃圾收集器并不像其收	集弱可及的对象一样尽量地收集软可及的对象，相反，它只在真正 “需要” 内存时才收集软可及的对象。**

**软引用：**

+ 弱引用的引用强度比软引用要更弱一些，**无论内存是否足够，只要 JVM 开始进行垃圾回收，那些被弱引用关联的对象都会被回收**。在 JDK1.2 之后，用 java.lang.ref.WeakReference 来表示弱引用。
  我们以与软引用同样的方式来测试一下弱引用：

**虚引用：** 

+ 虚引用是最弱的一种引用关系，如果一个对象仅持有虚引用，那么它就和没有任何引用一样，它随时可能会被回收，在 JDK1.2 之后，用 PhantomReference 类来表示，通过查看这个类的源码，发现它只有一个构造函数和一个 get() 方法，而且它的 get() 方法仅仅是返回一个null，也就是说将永远无法通过虚引用来获取对象，虚引用必须要和 ReferenceQueue 引用队列一起使用。为一个对象设置虚引用的
  **虚引用唯一目的就是能在这个对象被收集器回收时收到一个系统通知** ！！

  ```java
  public class PhantomReference<T> extends Reference<T> {
      /**
       * Returns this reference object's referent.  Because the referent of a
       * phantom reference is always inaccessible, this method always returns
       * <code>null</code>.
       *
       * @return  <code>null</code>
       */
      public T get() {
          return null;
      }
      public PhantomReference(T referent, ReferenceQueue<? super T> q) {
          super(referent, q);
      }
  }
  ```

  那么传入它的构造方法中的 ReferenceQueue 又是如何使用的呢？

  **引用队列（ReferenceQueue）**：

  引用队列可以与软引用、弱引用以及虚引用一起配合使用，当垃圾回收器准备回收一个对象时，如果发现它还有引用，那么就会在回收对象之前，把这个引用加入到与之关联的引用队列中去。程序可以通过判断引用队列中是否已经加入了引用，来判断被引用的对象是否将要被垃圾回收，这样就可以在对象被回收之前采取一些必要的措施。

  与软引用、弱引用不同，虚引用必须和引用队列一起使用。

## 6. Spring

### 1. Spring IOC（DI）

https://www.jianshu.com/p/8c24e0c804cc

Spring中有两大核心机制，就是IoC和AOP，这里我们先讲述IoC，它是Spring框架的灵魂！

**IoC就是工厂模式的一个典型运用！！**

IoC：Inversion of Control ， 也叫**控制反转**
问题：什么叫控制反转，反转的是什么？
在一般的程序开发中，要获取一个对象时，往往由开发之自己手动去new一个对象，假如这个类有什么改变的时候（比如改变类名），其他依赖这个类的模块也就要发生一些改变，这样不利于项目的改造和维护，也就是不满足“高内聚低耦合”，但在Spring中，这件事就交给IoC容器去管理，我们只需直接获取即可，整个流程就完成了反转；

**这样就实现了可插拔式的接口编程，有效的降低了代码的耦合度，降低了扩展和维护的成本；** 

> 概括的描述一下 Spring 背后的操作，解析 applicationgContext.xml，将 xml 中定义的 bean 解析成 Spring 内部的 BeanDefinition，
>
> 加载 Bean 到 BeanFactory 中，也就是读取配置文件信息将每个bean封装到BeanDefinitions中（每个Bean对应一个BeanDefinition对象），封装完每个bean后将BeanDefinitions信息注册到beanFactory（即DefaultListableBeanFactory）
>
> + 这个方法其实就是将我们的配置文件（xml）进行xml解析，它会将其解析成Document对象，即从每个xml文件的根节点开始进行解析，最终将xml文件转换为DOM树，以便我们获取其中的各种bean，然后实现后面的封装到BeanDefinitions；（也不是封装，因为BeanDefinitions是个接口嘛，反正就是可以通过BeanDefinitions接口或者和设置bean的各种信息）
>
> + **BeanDefinition**这个接口里面有关于bean的大量方法，比如：设置/读取 bean的scope，设置/读取该bean是否为懒加载，是否可以注入到其他bean中； 由于bean不一定是靠反射生成，还可以通过工厂模式，这个接口里面面就有设置/获取工厂名称的方法；还有获取bean中属性值的方法，以用来后面的属性注入；
>
>   还可以设置/获取这个bean所依赖的**所有bean**，即一个bean中用@Autowired注入的bean，或者用depends-on来配置的bean，这些才是当前bean所依赖的bean，不是指属性的依赖；
>
>   还提供了判断是Singleton还是prototype，即该bean是单例还是多例，注意BeanDefinition里面只定义了两种类型，即Singleton、prototype，可能我们知道还有request、session等作用域，但是在Spring中其实并没有这两种类型，需要我们基于Web的扩展；
>
> 并以 beanName(如 loginService) 为 key，BeanDefinition(如 loginService 相应的 BeanDefinition) 为 value 存储到 **DefaultListableBeanFactory 中的 beanDefinitionMap** (其实就是一个 ConcurrentHashMap) 中，同时将 beanName 存入 beanDefinitionNames(List 类型) 中，然后**遍历 beanDefinitionNames 中的 beanName，进行 bean 的实例化并填充属性**（当然这里并不是所有的bean都会在这里实例化，因为某些bean配置的是通过工厂模式（静态或实例）来产生，还有些bean设置的是懒加载），这里的bean实例化都是单例的bean，都是通过反射来实例化也是通过反射来注入属性（所以要有属性一定要有set方法），在属性注入完成后，还需要处理各种回调方法，首先是回调实现Aware接口后复写的方法。
>
> + 假如bean实现了Aware类接口，这类接口有BeanNameAware、BeanFactoryAware、ApplicationContextAware等接口，这类接口实现后都必须复写一个方法，这个方法也就是能够获取到Aware前面的东西，此时你的bean就可以获取到这些信息进行一些操作；
>
> 然后判断这个bean是否实现了BeanPostProcessor接口（后置处理器），如果实现了：
>
> + 这个接口有两个抽象方法，一个是前置处理一个是后置处理，此时会调用前置处理方法（postProcessBeforeInitialization），可以通过这个接口中的这两个抽象方法来对**容器中的所有bean做一个操作**⭐（注意是针对所有bean），相当于Spring会调用每一个bean的这两个方法，而覆写这两个具体方法的逻辑中，就可以找到我们要操作的具体哪一个bean，然后执行对应的逻辑操作；
>
> 然后会判断bean是否有自己自定义的初始化方法，此时会去执行这个初始化方法：
>
> + 有时我们需要在Bean属性值set好之后和Bean销毁之前做一些事情，比如检查Bean中某个属性是否被正常的设置好值了。Spring框架**提供了多种方法**让我们可以在Spring Bean的生命周期中执行initialization和pre-destroy方法。 
>
>   + 第一种：在你的具体类实现**实现InitializingBean和DisposableBean接口** ，这两个接口中都只有一个抽象方法，实现这两个抽象方法后就需要覆写对应的方法，然后就可以实现对应的操作；
>     + 这种方法比较简单，但是不建议使用。因为这样会将Bean的实现和Spring框架耦合在一起。
>   + 第二种：在spring的配置文件的bean块中，增加属性**init-method 和 destroy-method** 的值就可以在 Bean 初始化时和销毁之前执行一些操作。
>
> 此时这个bean如果实现了BeanPostProcessor（后置处理器），就会调用复写的postProcessAfterInitialization方法；
>
> 在整个实例化的过程中，**如果有依赖没有被实例化将先实例化其依赖（depends-on这种依赖），然后实例化本身，实例化完成后将实例存入单例 bean 的缓存中**，当调用 getBean 方法时，到单例 bean 的缓存中查找，如果找到并经过转换后返回这个实例 (如 LoginResource 的实例)，之后就可以直接使用了。

Spring Ioc底层模拟实现：底层其实就用到两个技术：

+ **XML解析和反射；** 

我们获取一个bean最原始的方式就是：

```java
applicationContext = new ClassPathXmlApplicationContext
        ("applicationContext.xml");
//（1）通过id的方式        
Person person = (Person) applicationContext.getBean("person");
//（2）通过运行时类
Person person1 = (Person) applicationContext.getBean(Person.class);
```

我们来分析一下，我们的bean块是在xml文件中配置的，而底层的大致设计就是，在我们的ClassPathXmlApplicationContext这个类中，它的构造方法传入我们的xml配置文件，然后会去解析这个xml文件，我当时用Dom解析来模拟的这个解析过程：

```java
 SAXReader saxReader = new SAXReader();
            Document document = saxReader.read("./src/main/resources/"+path);
            //获取根节点，即beans
            Element root = document.getRootElement();
            //获取根节点下的所有子节点（主要是bean）的迭代器
            Iterator<Element> iterator = root.elementIterator();
            while (iterator.hasNext()) {
              //...
            }
```

首先获取根节点，然后获取根节点的字节点的迭代器，这里的字节点就是我们的bean块，而bean中的两个重要属性我们都可以得到了，即id和这个类的全名，通过全名我们就可以获得它的class对象（Class.forName），此时可以获取到它的构造器（默认无参构造），此时可以通过构造器调用newInstance（）方法来获取这个类的对象，剩下的步骤就是往对象里面添加我们在xml中所指定的属性，还是通过bean这个节点获取到bean下所有字节点的迭代器，此时又能获取到每个属性名和属性值，通过属性名我们就可以拼接出“set属性名”，即设置属性的方法，有了这个方法名，我们就可以通过反射来取得方法，然后进行调用，完成每个属性的一个设置。

最后就是将这个bean块的id作为key，最后生成的对象作为value放到Spring的容器中去，而这个容器在Spring实现中就是ConcurrentHashMap；

```
<bean id="person" class="www.yy.been.Person">
    <property name="id" value="001"/>
    <property name="pName" value="yy"/>
</bean>
```

最后我们要获取某个bean，就可以直接通过id获取，也就是ClassPathXmlApplicationContext中的getBean这个方法！！

----

**DI也就是依赖注入：**   

+ 当IoC容器管理多个对象，并且对象之间有级联关系（即一个类中的属性是另一个类的对象），又该如何实现呢？

  ```java
  <bean id="classes" class="www.yy.been.Classes">
      <property name="classId" value="1701"/>
  </bean>
  <bean id="person" class="www.yy.been.Person">
      <property name="classes" ref="classes"/>
  </bean>
  ```

  这里我们用到了ref属性，这将其他bean赋值给当前bean对象，这种方式就叫做依赖注入（DI），

  - 这也是Spring中非常重要的一种机制
  - DI是将不同对象进行关联的一种方式，是IoC的具体实现方式
  - **通常IoC和DI紧密联系在一起，所以一般说IoC包含DI**

### 2. Spring AOP （动态代理）

AOP意味面向切面编程，我们之前一直使用的是OOP（面向对象编程）：

- **OOP：将程序中所有参与模块都抽象化为对象，然后通过对象之间的相互调用来完成业务需求；**
- **AOP：是对OOP的一个补充，是在另外一个维度上抽象出对象，具体是指程序运行时动态的将非业务代码切入到业务代码中，从而实现代码的解耦和；**

主要的场景是：将日志记录，性能统计，安全控制（校验），事务处理，异常处理等代码从业务逻辑代码中划分出来，通过对这些行为的分离，我们希望可以将它们独立到非指导业务逻辑的方法中，进而改 变这些行为的时候不影响业务逻辑的代码。

**Spring AOP底层就是通过代理模式来实现的，通过代理，可以详细控制访问某个或者某类对象的方法，在调用这个方法前做前置处理，调用这个方法后做后置处理。**

> **AOP的优点** 
>
> - **降低模块耦合度**
> - 使系统**容易扩展**
> - 延迟设计决定：使用AOP，设计师可以推迟为将来的需求作决定，因为需求作为独立的方面很容易实现
> - **更好的代码复用性**

```java
@Aspect
@Component
public class LoggerAspect {
    //int代表返回值类型，必须写,后面是具体类名，*代表这个类的所有方法，..代表所有参数
    @Before("execution(public int www.springAOP.ComImpl.*(..))")
    public void before(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println("方法名为："+name+" 参数为："+args);
    }

    @After("execution(public int www.springAOP.ComImpl.*(..))")
    public void after(JoinPoint joinPoint) {
        System.out.println("方法结束");
    }

    @AfterReturning(value = "execution(public int www.springAOP.ComImpl.*(..))", returning = "result")
    //注意上面的result必须与下面的形参名一模一样，且必须有这个形参
    public void afterReturn(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法结果为："+result);
    }

    @AfterThrowing(value = "execution(public int www.springAOP.ComImpl.*(..))", throwing = "ex")
    public void afterThrow(JoinPoint joinPoint, Exception ex) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name+"方法抛出异常："+ex);
    }
}
```

**在Spring AOP中，有如下几个术语：** 

**1. Aspect（切面类）** （包含了下面的所有）

- 切面是一个模板，它定义了所有需要完成的工作，比如切入的范围和时间，都是在切面中来完成；
- 在Spring中，通过实现@Aspect注解来构造一个切面；
- **上面的例子中，LoggerAspect就是一个切面类；** 

**2. Advice（通知）** 

- 定义了切面是什么，何时使用，描述了切面要完成的工作，还解决何时执行这个工作的问题。 
  - **其实通知简单地说就是切面类的代码，即非业务代码，上面例子中就是LoggerAspect中的代码；** 
- 在切面的某个特定的连接点上执行的动作。**其中包括了“around”（环绕通知）、“before”和“after”、“异常”等不同类型的通知**。许多AOP框架（包括Spring）都是以*拦截器*做通知模型，并维护一个以连接点为中心的拦截器链。  
- 这里讲讲最6b的环绕通知，环绕通知有一个特色就是可以对参数进行修改，再用你新的参数去执行原方法，环绕通知中需要传入一个ProceedingJoinPoint对象，调用这个对象的proceed方法就是执行目标方法，其实环绕通知就是类似于jdk动态代理中的invoke方法，几乎一摸一样；环绕通知不是注解哟！

**3. Target Object（目标）** 

- 被横切的对象，即还未被增强的对象，对应上面例子中的ComImpl类的实例化对象，将通知放入其中；

**4. AOP Proxy（代理）** 

- 切面对象、通知、目标混合之后的内容，即我们用JDK动态代理或者Cglib机制创建的对象；

**5. Join point（连接点）** 

- 在Java程序执行中，我们可以把每个方法看成一个点，所有方法的执行就是这些点串联的结果；**而连接点就是目标类需要被切入的的位置，即通知要插入业务代码的具体位置；** 

**6. Pointcut（切点）** 

每个方法是一个Join point，而Pointcut就是所有连接点的集合，具体上来说就是匹配连接点（Join point）的表达式，是AOP的核心，并且Spring 默认使用AspectJ作为切入点表达式语言；具体实现就是在切面类中用@Pointcut注解来定义切点，即从哪些类哪些方法进行切入（用excution表达式），再配合通知就可以准确到哪个方法什么时机去增强我们的方法；

上面的案例是直接在通知中写excution表达式（excution最小粒度是方法，还有within表达式，最小粒度是类，还有很多很多。。。），我们可以先定义切点，然后通知直接引用这个

**7. weaving （织入）**

即我们的普通功能到增强功能这样一个过程，可以看成织入；

#### 🌟代理模式

**静态代理**：一个公共接口，被代理类实现它，代理类也实现它，代理类中需要传入被代理类，**静态代理的缺点就是：**

+ 每一个代理类只能为一个接口服务，这样一来程序开发中必然会产生过多的代理，而且，所有的代理操作除了调用的方法不一样之外，其他的操作都一样，则此时肯定是重复代码。解决这一问题最好的做法是可以通过一个代理类完成全部的代理功能，那么此时就必须使用动态代理完成。

**SpringAOP中是使用动态代理模式来实现的，而动态代理模式我们知道有两种**，一种是JDK自带的，代理类需要实现InvocationHandler并复写invoke方法，被代理的目标类需要实现统一接口才能够被代理

> 与静态代理类对照的是动态代理类，动态代理类的字节码在程序运行时由Java反射机制动态生成，无需程序员手工编写它的源代码。动态代理类不仅简化了编程工作，而且提高了软件系统的可扩展性，因为Java 反射机制可以生成任意类型的动态代理类。java.lang.reflect 包中的Proxy类和InvocationHandler 接口提供了生成动态代理类的能力；

**Cglib动态代理 **：**JDK的动态代理机制只能代理实现了接口的类，而不能实现接口的类就不能实现JDK的动态代理🌟，cglib是针对类来实现代理的**，**他的原理是对指定的目标类生成一个子类**，并覆盖其中方法实现增强，但因为采用的是继承，**所以不能对final修饰的类进行代理。**

```java
/** 
 * 使用cglib动态代理 
 * @author student  
 */  
public class BookFacadeCglib implements MethodInterceptor {  
    private Object target;  

    /** 
     * 创建代理对象 
     */  
    public Object getInstance(Object target) {  
        this.target = target;  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());  
        // 回调方法  
        enhancer.setCallback(this);  
        // 创建代理对象  
        return enhancer.create();  
    }  

    @Override  
    // 回调方法  
    public Object intercept(Object obj, Method method, Object[] args,  
            MethodProxy proxy) throws Throwable {  
        System.out.println("事物开始");  
        proxy.invokeSuper(obj, args);  
        System.out.println("事物结束");  
        return null; 
    }  
}  

public class TestCglib { 
    public static void main(String[] args) { 
        BookFacadeCglib cglib=new BookFacadeCglib(); 
        BookFacadeImpl1 bookCglib=(BookFacadeImpl1)cglib.getInstance(new BookFacadeImpl1()); 
        bookCglib.addBook(); 
    } 
} 
```

**那么Spring AOP什么时候用JDK代理，什么时候用cglib代理，在源码中，如果目标对象实现了接口，默认情况下会采用 JDK 的动态代理实现 AOP；如果目标对象没有实现了接口，则采用 CGLIB 库，Spring 会自动在 JDK 动态代理和 CGLIB 动态代理之间转换。** 

---

AOP底层实现：

根据Spring AOP的动态代理的过程，我们可以把AOP的设计分为两大块：第一，需要为目标对象建立代理对象（如何生成代理对象？）；第二，需要启动代理对象的拦截器来完成各种横切面的织入（如何织入横切面同时如何拦截对目标对象方法的调用？）。

首先是我们如何获取代理对象：

![图片描述](http://img1.sycdn.imooc.com/597dadc80001f1ff09520680.png)

### 3. Spring bean的生命周期

先看看Spring的整个继承关系：

![img](https://img-blog.csdnimg.cn/20200322143735629.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)

先来看看Spring中启动一个容器并初始化的大概流程：

我们Spring中操作的基石就是下面这一行：
![springIoc1](/Users/yangyun/Documents/picture/springIoc1.png)

意思是加载xml文件创建一个ApplicationContext 的Spring 容器，来看看ApplicationContext的构造方法：

```java
    public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, @Nullable ApplicationContext parent) throws BeansException {
        super(parent);
      //configLocations里面就是我们输入的参数：xml文件路径
        this.setConfigLocations(configLocations);
      //refresh默认为true
        if (refresh) {
            this.refresh();
        }
    }
```

所以这里的核心就是refresh（）方法了，这个方法包含了整个Ioc的加载过程！！

进入这个方法：

```java
    public void refresh() throws BeansException, IllegalStateException {
        Object var1 = this.startupShutdownMonitor;
        synchronized(this.startupShutdownMonitor) {
     //这个方法是做准备工作的，记录容器的启动时间、标记“已启动”状态、处理配置文件中的占位符，这里就不多说了。
            this.prepareRefresh();
          //🌟🌟很重要的一个方法，这一步是把配置文件解析成一个个Bean，并且注册到BeanFactory中，注意这里只是注册进去，并没有初始化。后面详解
            ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();
          //设置 BeanFactory 的类加载器，添加几个 BeanPostProcessor，手动注册几个特殊的 bean，这里都是spring里面的特殊处理，不用多看
            this.prepareBeanFactory(beanFactory);

            try {
              //提供给子类的扩展点，到这里的时候，所有的 Bean 都加载、注册完成了，但是都还没有初始化，具体的子类可以在这步的时候添加一些特殊的 BeanFactoryPostProcessor 的实现类，来完成一些其他的操作。
                this.postProcessBeanFactory(beanFactory);
              //这个方法是调用 BeanFactoryPostProcessor 各个实现类的 postProcessBeanFactory(factory) 方法；
                this.invokeBeanFactoryPostProcessors(beanFactory);
              //注册 BeanPostProcessor 的实现类，和上面的BeanFactoryPostProcessor 是有区别的，这个方法调用的其实是PostProcessorRegistrationDelegate类的registerBeanPostProcessors方法；这个类里面有个内部类BeanPostProcessorChecker，BeanPostProcessorChecker里面有两个方法postProcessBeforeInitialization和postProcessAfterInitialization，这两个方法分别在 Bean 初始化之前和初始化之后得到执行。
                this.registerBeanPostProcessors(beanFactory);
              
                this.initMessageSource();
                this.initApplicationEventMulticaster();
                this.onRefresh();
              //registerListeners();方法注册事件监听器，监听器需要实现 ApplicationListener 接口；继续往下
                this.registerListeners();
              //🌟🌟重点到了：初始化所有的 singleton beans（单例bean），懒加载（non-lazy-init）的除外，这个方法也是等会细说
                this.finishBeanFactoryInitialization(beanFactory);
                this.finishRefresh();
            } catch (BeansException var9) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn("Exception encountered during context initialization - cancelling refresh attempt: " + var9);
                }

                this.destroyBeans();
                this.cancelRefresh(var9);
                throw var9;
            } finally {
                this.resetCommonCaches();
            }

        }
    }
```

这就是整个refresh()方法调用的所有方法。这里只是简单描述一下，我们重点来看ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();和finishBeanFactoryInitialization(beanFactory);这两个方法。

先来看obtainFreshBeanFactory（）这个方法，点进去源码就主要关注一个核心方法：**refreshBeanFactory**，
来看看这个方法：（目的就是注册bean到工厂，**DefaultListableBeanFactory**即是我们的工厂）

```java
    protected final void refreshBeanFactory() throws BeansException {
        if (this.hasBeanFactory()) {
          //1⃣️
            this.destroyBeans();
            this.closeBeanFactory();
        }

        try {
          //2⃣️
            DefaultListableBeanFactory beanFactory = this.createBeanFactory();
            beanFactory.setSerializationId(this.getId());
          //4⃣️
            this.customizeBeanFactory(beanFactory);
          //5⃣️
            this.loadBeanDefinitions(beanFactory);
            Object var2 = this.beanFactoryMonitor;
            synchronized(this.beanFactoryMonitor) {
                this.beanFactory = beanFactory;
            }
        } catch (IOException var5) {
            throw new ApplicationContextException("I/O error parsing bean definition source for " + this.getDisplayName(), var5);
        }
    }
```

> 1. if里面的逻辑就是：首先如果 ApplicationContext 中已经加载过 BeanFactory了，销毁所有 Bean，关闭 BeanFactory；这里指的是当前ApplicationContext 是否有 BeanFactory。
>
> 2. 接下来就是try块了，第一句就是创建我们的工厂类，也就是createBeanFactory();初始化一个DefaultListableBeanFactory，它的顶层就是BeanFactory接口；
>
> 3. beanFactory.setSerializationId(getId());方法用于 BeanFactory 的序列化，不用管；
>
> 4. customizeBeanFactory(beanFactory)，方法**设置 BeanFactory 的两个配置属性**：是否允许 Bean 覆盖、是否允许循环引用，BeanDefinition 的覆盖问题就是在配置文件中定义 bean 时使用了相同的 id 或 name，默认情况下，allowBeanDefinitionOverriding 属性为 null，如果在同一配置文件中重复了，会抛错，但是如果不是同一配置文件中，会发生覆盖。
>    循环引用：A 依赖 B，而 B 依赖 A。或 A 依赖 B，B 依赖 C，而 C 依赖 A。默认情况下，Spring 允许循环依赖，**当然如果你在 A 的构造方法中依赖 B，在 B 的构造方法中依赖 A 是不行的。**
>
> 5. **loadBeanDefinitions(beanFactory)，这个方法很重要：**加载 Bean 到 BeanFactory 中，也就是读取配置文件信息将每个bean封装到BeanDefinitions中，上面也详细介绍了BeanDefinitions接口，封装完每个bean后将BeanDefinitions信息注册到beanFactory（即DefaultListableBeanFactory）
>
>    + 这个方法其实就是将我们的配置文件（xml）进行xml解析，它会将其解析成Document对象，即从每个xml文件的根节点开始进行解析，最终将xml文件转换为DOM树，以便我们获取其中的各种bean，然后实现后面的封装到BeanDefinitions；（也不是封装，因为BeanDefinitions是个接口嘛，反正就是可以通过BeanDefinitions接口或者和设置bean的各种信息）
>
>    + **BeanDefinition**这个接口非常重要，里面有关于bean的大量方法，比如：设置/读取 bean的scope，设置/读取该bean是否为懒加载，是否可以注入到其他bean中； 由于bean不一定是靠反射生成，还可以通过工厂模式，这个接口里面面就有设置/获取工厂名称的方法；还有获取bean中属性值的方法，以用来后面的属性注入；
>
>      还可以设置/获取这个bean所依赖的**所有bean**，即一个bean中用@Autowired注入的bean，或者用depends-on来配置的bean，这些才是当前bean所依赖的bean，不是指属性的依赖；
>
>      还提供了判断是Singleton还是prototype，即该bean是单例还是多例，注意BeanDefinition里面只定义了两种类型，即Singleton、prototype，可能我们知道还有request、session等作用域，但是在Spring中其实并没有这两种类型，需要我们基于Web的扩展；

----

下面再来看看finishBeanFactoryInitialization(beanFactory)，到这一步为止BeanFactory 已经创建完成，并且所有的实现了 BeanFactoryPostProcessor 接口的 Bean 都已经初始化并且其中的 postProcessBeanFactory(factory) 方法已经得到回调执行了。而且 Spring 已经“手动”注册了一些特殊的 Bean，如 ‘environment’、‘systemProperties’ 等。剩下的就是初始化 singleton beans 了，我们知道它们是单例的，如果没有设置懒加载，那么 Spring 会在接下来初始化所有的 singleton beans。

finishBeanFactoryInitialization方法中又有一个非常重要的方法：**beanFactory.preInstantiateSingletons()**,这个方法里面才开始真正的实例化（可以通过调试，当这个方法执行过后，才会调用我们的bean的构造方法）；（这个beanFactory也就是我们上面经过bean注册后的DefaultListableBeanFactory），**这里开始初始化，来看看源码：** 

```java
    public void preInstantiateSingletons() throws BeansException {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Pre-instantiating singletons in " + this);
        }
				//这里只包含可能需要实例化的class，因为有些是prototype，或者懒加载，都不会在这里实例化，都是在使用的时候才去实例化；即这里只实例化需要缓存的，即一开始就需要放在Map里面的，这里的Map代码层面是SingletonObjects，是个ConcurrentHashMap
        List<String> beanNames = new ArrayList(this.beanDefinitionNames);
        Iterator var2 = beanNames.iterator();

        while(true) {
            String beanName;
            Object bean;
            do {
                while(true) {
                    RootBeanDefinition bd;
                    do {
                        do {
                            do {
                                if (!var2.hasNext()) {
                                    var2 = beanNames.iterator();

                                    while(var2.hasNext()) {
                                        beanName = (String)var2.next();
                                        Object singletonInstance = this.getSingleton(beanName);
                                        if (singletonInstance instanceof SmartInitializingSingleton) {
                                            SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton)singletonInstance;
                                            if (System.getSecurityManager() != null) {
                                                AccessController.doPrivileged(() -> {
                                                    smartSingleton.afterSingletonsInstantiated();
                                                    return null;
                                                }, this.getAccessControlContext());
                                            } else {
                                                smartSingleton.afterSingletonsInstantiated();
                                            }
                                        }
                                    }

                                    return;
                                }

                                beanName = (String)var2.next();
                                bd = this.getMergedLocalBeanDefinition(beanName);
                            } while(bd.isAbstract());
                        } while(!bd.isSingleton());
                    } while(bd.isLazyInit());

                    if (this.isFactoryBean(beanName)) {
                        bean = this.getBean("&" + beanName);
                        break;
                    }

                    this.getBean(beanName);
                }
            } while(!(bean instanceof FactoryBean));

            FactoryBean<?> factory = (FactoryBean)bean;
            boolean isEagerInit;
            if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
                SmartFactoryBean var10000 = (SmartFactoryBean)factory;
                ((SmartFactoryBean)factory).getClass();
                isEagerInit = (Boolean)AccessController.doPrivileged(var10000::isEagerInit, this.getAccessControlContext());
            } else {
                isEagerInit = factory instanceof SmartFactoryBean && ((SmartFactoryBean)factory).isEagerInit();
            }

            if (isEagerInit) {
                this.getBean(beanName);
            }
        }
    }
```

在这段源码中，**this.beanDefinitionNames 保存了所有的 beanNames**，然后再循环，**判断非prototype、非懒加载的 singletons**，如果是FactoryBeanFactoryBean 的话，在 beanName 前面加上 ‘&’ 符号，再调用getBean(beanName);，如果是普通的bean，那么直接getBean(beanName);，这里都是在循环中的，循环结束后，所有的非单例bean就初始化完成了，接着下面如果我们定义的 bean 是实现了 SmartInitializingSingleton 接口的，那么在smartSingleton.afterSingletonsInstantiated();这里得到回调，我们直接进入getBean()方法：

> 如果bean实现了SmartInitializingSingleton接口，当所有单例 bean 都初始化完成以后， 容器会回调该接口的方法 `afterSingletonsInstantiated`。
> **主要应用场合就是在所有单例 bean 创建完成之后，可以在该回调中做一些事情。**



同时将 beanName 存入 beanDefinitionNames(List 类型) 中，然后**遍历 beanDefinitionNames 中的 beanName，进行 bean 的实例化并填充属性**，在实例化的过程中，**如果有依赖没有被实例化将先实例化其依赖，然后实例化本身，实例化完成后将实例存入单例 bean 的缓存中**，当调用 getBean 方法时，到单例 bean 的缓存中查找，如果找到并经过转换后返回这个实例 (如 LoginResource 的实例)，之后就可以直接使用了。

Spring 中 Bean 的生命周期如下：  
① 实例化 Bean：对于 BeanFactory 容器，当客户向容器请求一个尚未初始化的 Bean 时，或初始化 Bean 的时候需要注入另一个尚未初始化的依赖时，容器就会调用 createBean 进行实例化。对于 ApplicationContext 容器，当容器启动结束后，通过获取 **BeanDefinition** 对象中的信息，实例化所有的 Bean； 
② 设置对象属性（依赖注入）：实例化后的对象被封装在 **BeanWrapper** 对象中，紧接着 Spring 根据 **BeanDefinition** 中的信息以及通过 BeanWrapper 提供的设置属性的接口完成依赖注入； 
③ 处理 Aware 接口：Spring 会检测该对象是否实现了 xxxAware 接口，并将相关的 xxxAware 实例注入给 Bean： 如果这个 Bean 已经实现了 BeanNameAware 接口，会调用它实现的 setBeanName(String BeanId) 方法，此处传递的就是 Spring 配置文件中 Bean 的 id 值； 如果这个 Bean 已经实现了 BeanFactoryAware 接口，会调用它实现的 setBeanFactory() 方法，传递的是 Spring 工厂自身； 如果这个 Bean 已经实现了 ApplicationContextAware 接口，会调用 setApplicationContext(ApplicationContext) 方法，传入 Spring 上下文； 
④ BeanPostProcessor：如果想对 Bean 进行一些自定义的处理，那么可以让 Bean 实现了 BeanPostProcessor 接口，那将会调用 postProcessBeforeInitialization(Object obj, String s) 方法；
⑤ InitializingBean 与 init-method：如果 Bean 在 Spring 配置文件中配置了 init-method 属性，则会自动调用其配置的初始化方法； 
⑥ 如果这个 Bean 实现了 BeanPostProcessor 接口，将会调用 postProcessAfterInitialization(Object obj, String s) 方法；由于这个方法是在 Bean 初始化结束时调用的，因而可以被应用于内存或缓存技术； 以上几个步骤完成后，Bean 就已经被正确创建了，之后就可以使用这个 Bean 了。  
⑦ DisposableBean：当 Bean 不再需要时，会经过清理阶段，如果 Bean 实现了 DisposableBean 这个接口，会调用其实现的 destroy() 方法； 
⑧ destroy-method：最后，如果这个 Bean 的 Spring 配置中配置了 destroy-method 属性，会自动调用其配置的销毁方法。 



### 4. Spring MVC 和 MVC的区别

**什么是MVC**

   **MVC是一种架构模式** --- 程序分层，分工合作，既相互独立，又协同工作

   MVC是一种思考方式 --- 需要将什么信息展示给用户? 如何布局？ 调用哪些业务逻辑？

MVC核心思想：业务数据抽取同业务数据实现相分离

  模型层(M) ：业务数据的信息表示，关注支撑业务的信息构成，通常是多个业务实体的组合

  视图层(V) ： 为用户提供UI，重点关注数据的呈现

  控制器(C) ： 接受用户请求，并调用相应的模型处理

​          (相当于一个总调配中心，有什么需求，就去调用相应模型进行处理，最后通过视图给用户进行展示)

**SpringMVC的原理：**

+ 首先用户发出请求，请求到达SpringMVC的前端控制器（DispatcherServlet）；
+ 前端控制器根据用户的url，请求处理器映射器(HandlerMapping)查找匹配该url的handler，并返回一个执行链(HandlerExecutionChain)；
+ 前端控制器再请求处理器适配器(HandlerAdapter)调用相应的handler进行处理并返回给前端控制器一modelAndView；（或者Map、Model等）
  + 所谓返回一个modelAndView，这里就是做一个**业务数据的绑定：是指将业务数据绑定给 JSP 域对象；** 
    JSP四大域对象：（**作用域从上到下依次增大**🔱）
    - **pageContext**：范围在当前页面 ；
    - **request**：范围在同一次请求 ；
    - **session** ：范围在一次会话，浏览器打开这个页面访问到后台到关闭这个页面叫一次会话 ；
    - **application**：范围在当前web应用，只要在一处设置了，当前web应用下的其他地方都可以获取到 
  + 在我们的控制器业务方法完成，返回业务数据和视图信息给 DispatcherServlet，DispatcherServlet 通过 ViewResolver 对视图信息进行解析，逻辑视图映射到物理视图，**同时将业务数据绑定到 JSP 的 request 域对象中**，在 JSP 页面可直接通过 EL 表达式取值。
+ 前端控制器再请求视图解析器(ViewResolver)对返回的逻辑视图进行解析；
+ 最后前端控制器将返回的视图进行渲染并把数据装入到request域，返回给用户；

**答案：**🌟

+ **MVC是一种架构模式，而Spring MVC正是这种模式下在web开发时代的一种实现；**



### 5. Spring中的bean的类型

bean是根据scope来生成的，便是bean的作用域，**scope有以下4种类型：** ⭐

- **singleton（单例）：表示通过Spring容器获取的该对象是唯一的**
- **prototype（原型）：表示通过Spring容器获取的对象都是不同的**
- request（请求）：表示在一次HTTP求情内有效
- session（会话）：表示在一个用户会话内有效

后两种属于web项目，大多数情况下，我们一般使用前两种，**bean标签中scope属性的默认值是singleton（⭐）；**

> **可以去测试一下，用同一个bean（默认的scope）生成两个对象，然后判断它们是否相等（肯定相等）；**
> **当我们在bean标签中添加属性scope=”prototype“后，上面案例会产生false；**

### 6. Spring 如何解决循环依赖

https://zhuanlan.zhihu.com/p/84267654

**下面这些是针对Singleleton模式的bean：**

> 注意构造器注入的不能循环依赖，此时会直接报BeanCurrentlyInCreationException，你想想，对象都还在创建中，vm都还没返回给你实例，怎么注入。
> 所说的循环依赖，是指属性的循环依赖，这种循环依赖就可以实现，因为都是先初始化了实例再填充字段值的。
>
> - **Spring是通过递归的方式获取目标bean及其所依赖的bean的；**
> - **Spring实例化一个bean的时候，是分两步进行的，首先实例化目标bean，然后为其注入属性。**
>
> 结合这两点，也就是说，Spring在实例化一个bean的时候，是首先递归的实例化其所依赖的所有bean，直到某个bean没有依赖其他bean，此时就会将该实例返回，然后反递归的将获取到的bean设置为各个上层bean的属性的。

而对于原形的bean（prototype）：

进行属性的循环依赖就会报错，对于“prototype”作用域Bean，Spring容器无法完成依赖注入，因为“prototype”作用域的Bean，Spring容器不进行缓存，因此无法提前暴露一个创建中的Bean。

## 7. 操作系统

### 1. 同步、异步、阻塞、非阻塞概念

**同步：**
所谓同步，就是在发出一个功能调用时，在没有得到结果之前，该调用就不返回。**也就是必须一件一件事做**,等前一件做完了才能做下一件事。

例如普通B/S模式（同步）：提交请求->等待服务器处理->处理完毕返回 这个期间客户端浏览器不能干任何事

**异步：**
异步的概念和同步相对。当一个异步过程调用发出后，调用者不能立刻得到结果。实际处理这个调用的部件在完成后，通过状态、通知和回调来通知调用者。

例如 ajax请求（异步）: 请求通过事件触发->服务器处理（这是浏览器仍然可以作其他事情）->处理完毕

**阻塞：**
**阻塞调用是指调用结果返回之前，当前线程会被挂起（线程进入非可执行状态，在这个状态下，cpu不会给线程分配时间片，即线程暂停运行）**。函数只有在得到结果之后才会返回。

有人也许会把阻塞调用和同步调用等同起来，实际上他是不同的。对于同步调用来说，很多时候当前线程还是激活的，只是从逻辑上当前函数没有返回,它还会抢占cpu去执行其他逻辑，也会主动检测io是否准备好。

**非阻塞**
非阻塞和阻塞的概念相对应，指在不能立刻得到结果之前，该函数不会阻塞当前线程，而会立刻返回。

再简单点理解就是：

+ 同步，就是我调用一个功能，该功能没有结束前，我死等结果。
+ 异步，就是我调用一个功能，不需要知道该功能结果，该功能有结果后通知我（回调通知）
+ 阻塞，就是调用我（函数），我（函数）没有接收完数据或者没有得到结果之前，我不会返回。
+ 非阻塞，就是调用我（函数），我（函数）立即返回，通过select通知调用者

同步IO和异步IO的区别就在于：数据拷贝的时候进程是否阻塞
阻塞IO和非阻塞IO的区别就在于：应用程序的调用是否立即返回

综上可知，同步和异步,阻塞和非阻塞,有些混用,其实它们完全不是一回事,**而且它们修饰的对象也不相同。**

**还可以结合Linux的五种IO模型来说明。。。。请看我的那篇博客；** 





## 8. 网络

### 1. 浏览器输入URL后全流程

+ **DNS域名解析** 

  + 当用户输入一个网址并按下回车键的时候，浏览器得到了一个域名。而在实际通信过程中，我们需要的是一个IP地址。因此我们需要先把域名转换成相应的IP地址，这个过程称作DNS解析。

  + 浏览器首先搜索浏览器自身缓存的DNS记录。（每个浏览器一般都会缓存1000条域名IP地址一分钟左右）

  + 如果浏览器缓存中没有找到需要的记录或记录已经过期，则搜索hosts文件和操作系统缓存。

  + 如果在hosts文件和操作系统缓存中没有找到需要的记录或记录已经过期，则向域名解析服务器发送解析请求。

    + 其实第一台被访问的域名解析服务器就是我们平时在设置中填写的DNS服务器一项，当操作系统缓存中也没有命中的时候，系统会向DNS服务器正式发出解析请求。这里是真正意义上开始解析一个未知的域名。一般一台域名解析服务器会被地理位置临近的大量用户使用（特别是ISP的DNS），一般常见的网站域名解析都能在这里命中。

  + 如果域名解析服务器也没有该域名的记录，则开始递归+迭代解析。

    + 首先我们的域名解析服务器会向根域服务器（全球只有13台）发出请求。显然，仅凭13台服务器不可能把全球所有IP都记录下来。所以根域服务器记录的是com域服务器的IP、cn域服务器的IP、org域服务器的IP……。如果我们要查找.com结尾的域名，那么我们可以到com域服务器去进一步解析。所以其实这部分的域名解析过程是一个树形的搜索过程。

  +  获取域名对应的IP后，一步步向上返回，直到返回给浏览器。

  + **DNS查询的两种方式：** （上面讲的也就是递归的方式，也是默认方式）

    1、递归解析：

    当局部DNS服务器自己不能回答客户机的DNS查询时，它就需要向其他DNS服务器进行查询。此时有两种方式，如图所示的是递归方式。局部DNS服务器自己负责向其他DNS服务器进行查询，一般是先向该域名的根域服务器查询，再由根域名服务器一级级向下查询。最后得到的查询结果返回给局部DNS服务器，再由局部DNS服务器返回给客户端。

    ![img](https://pic2.zhimg.com/80/v2-ea4f5f5cf28a9a6e166c4fc11b37ad21_1440w.jpg)

    2、迭代解析：

    当局部DNS服务器自己不能回答客户机的DNS查询时，也可以通过迭代查询的方式进行解析，如图所示。局部DNS服务器不是自己向其他DNS服务器进行查询，而是把能解析该域名的其他DNS服务器的IP地址返回给客户端DNS程序，客户端DNS程序再继续向这些DNS服务器进行查询，直到得到查询结果为止。也就是说，迭代解析只是帮你找到相关的服务器而已，而不会帮你去查。

    ![img](https://pic2.zhimg.com/80/v2-4e0687456ae11fc76b0a77fc7e55eb21_1440w.jpg)

+ 浏览器会选择一个大于1024的本机端口向目标IP地址的80端口发起TCP连接请求。经过标准的TCP握手流程，建立TCP连接。
  ![img](https://img-blog.csdnimg.cn/20191130164621519.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2V0ZXJuYWxfeWFuZ3l1bg==,size_16,color_FFFFFF,t_70)

+ 一般，如果我们的平台配备了负载均衡的话，前一步DNS解析获得的IP地址应该是我们Nginx负载均衡服务器的IP地址。所以，我们的浏览器将我们的网页请求发送到了Nginx负载均衡服务器上。Nginx根据我们设定的分配算法和规则，选择一台后端的真实Web服务器，与之建立TCP连接、并转发我们浏览器发出去的网页请求。

+ Web服务器收到请求，产生响应，并将网页发送给Nginx负载均衡服务器。

+ Nginx负载均衡服务器将网页传递给filters链处理，之后发回给我们的浏览器。

+ **浏览器渲染** 

  　　1) 浏览器根据页面内容（HTML文件），生成DOM Tree。根据CSS内容，生成CSS Rule Tree(规则树)。调用JS执行引擎执行JS代码。

  　　2) 根据DOM Tree和CSS Rule Tree生成Render Tree(呈现树)

  　　3) 根据Render Tree渲染网页

但是在浏览器解析页面内容的时候，会发现页面引用了其他未加载的image、css文件、js文件等静态内容，因此开始了第二部分。

**网页静态资源加载** ：

以阿里巴巴的淘宝网首页的logo为例，其url地址为 img.alicdn.com/tps/i2/TB1bNE7LFXXXXaOXFXXwFSA1XXX-292-116.png_145x145.jpg

我们清楚地看到了url中有cdn字样。

什么是CDN？如果我在广州访问杭州的淘宝网，跨省的通信必然造成延迟。如果淘宝网能在广东建立一个服务器，静态资源我可以直接从就近的广东服务器获取，必然能提高整个网站的打开速度，这就是CDN。CDN叫内容分发网络，是依靠部署在各地的边缘服务器，使用户就近获取所需内容，降低网络拥塞，提高用户访问响应速度。

接下来的流程就是浏览器根据url加载该url下的图片内容。本质上是浏览器重新开始第一部分的流程，所以这里不再重复阐述。区别只是负责均衡服务器后端的服务器不再是应用服务器，而是提供静态资源的服务器。

### 2. Cookie和Session

1. 由于HTTP协议是无状态的协议，所以服务端需要记录用户的状态时，就需要用某种机制来识具体的用户，这个机制就是Session.典型的场景比如购物车，当你点击下单按钮时，由于HTTP协议无状态，所以并不知道是哪个用户操作的，所以服务端要为特定的用户创建了特定的Session，用用于标识这个用户，并且跟踪用户，这样才知道购物车里面有几本书。这个Session是保存在服务端的，有一个唯一标识。在服务端保存Session的方法很多，内存、数据库、文件都有。集群的时候也要考虑Session的转移，在大型的网站，一般会有专门的Session服务器集群，用来保存用户会话，这个时候 Session 信息都是放在内存的，使用一些缓存服务比如Memcached之类的来放 Session。
2. 思考一下服务端如何识别特定的客户？这个时候Cookie就登场了。每次HTTP请求的时候，客户端都会发送相应的Cookie信息到服务端。实际上大多数的应用都是用 Cookie 来实现Session跟踪的，第一次创建Session的时候，服务端会在HTTP协议中告诉客户端，需要在 Cookie 里面记录一个Session ID，以后每次请求把这个会话ID发送到服务器，我就知道你是谁了。有人问，如果客户端的浏览器禁用了 Cookie 怎么办？一般这种情况下，会使用一种叫做URL重写的技术来进行会话跟踪，即每次HTTP交互，URL后面都会被附加上一个诸如 sid=xxxxx 这样的参数，服务端据此来识别用户。
3. Cookie其实还可以用在一些方便用户的场景下，设想你某次登陆过一个网站，下次登录的时候不想再次输入账号了，怎么办？这个信息可以写到Cookie里面，访问网站的时候，网站页面的脚本可以读取这个信息，就自动帮你把用户名给填了，能够方便一下用户。这也是Cookie名称的由来，给用户的一点甜头。
   所以，总结一下：
   Session是在服务端保存的一个数据结构，用来跟踪用户的状态，这个数据可以保存在集群、数据库、文件中；
   Cookie是客户端保存用户信息的一种机制，用来记录用户的一些信息，也是实现Session的一种方式。

----

**简要版本：** 

+ session 在服务器端，cookie 在客户端（浏览器）
+ session 默认被存在在服务器的一个文件里（不是内存）
+ session 的运行依赖 session id，而 session id 是存在 cookie 中的，也就是说，如果浏览器禁用了 cookie ，同时 session 也会失效（但是可以通过其它方式实现，比如在 url 中传递 session_id）
+ session 可以放在 文件、数据库、或内存中都可以。
+ 用户验证这种场合一般会用 session

-----

重要思想：

类似这种面试题，实际上都属于“开放性”问题，你扯到哪里都可以。不过如果我是面试官的话，我还是希望对方能做到一点——

**不要混淆 session 和 session 实现。**

本来 session 是一个抽象概念，开发者为了实现中断和继续等操作，将 user agent 和 server 之间一对一的交互，抽象为“会话”，进而衍生出“会话状态”，也就是 session 的概念。

 而 cookie 是一个实际存在的东西，http 协议中定义在 header 中的字段。可以认为是 session 的一种后端无状态实现。

而我们今天常说的 “session”，是为了绕开 cookie 的各种限制，通常借助 cookie 本身和后端存储实现的，一种更高级的会话状态实现。

所以 cookie 和 session，你可以认为是同一层次的概念，也可以认为是不同层次的概念。具体到实现，session 因为 session id 的存在，通常要借助 cookie 实现，但这并非必要，只能说是通用性较好的一种实现方案。