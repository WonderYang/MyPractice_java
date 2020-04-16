# JVM调优策略

https://blog.csdn.net/weixin_42447959/article/details/81637909

### 1. 调优目的

首先明确JVM调优目的：**使用较小的内存占用来获得较高的吞吐量或者较低的延迟。**

程序在上线前的测试或运行中有时会出现一些大大小小的JVM问题，比如cpu load过高、请求延迟、tps降低等，甚至出现内存泄漏（每次垃圾收集使用的时间越来越长，垃圾收集频率越来越高，每次垃圾收集清理掉的垃圾数据越来越少）、内存溢出导致系统崩溃，因此需要对JVM进行调优，使得程序在正常运行的前提下，获得更高的用户体验和运行效率。

**这里有几个比较重要的指标：**

- 内存占用：程序正常运行需要的内存大小。
- 延迟：由于垃圾收集而引起的程序停顿时间。
- 吞吐量：用户程序运行时间占用户程序和垃圾收集占用总时间的比值。

当然，和CAP原则一样，同时满足一个程序内存占用小、延迟低、高吞吐量是不可能的，程序的目标不同，**调优时所考虑的方向也不同，在调优之前，必须要结合实际场景，有明确的的优化目标**，找到性能瓶颈，对瓶颈有针对性的优化，最后进行测试，通过各种监控工具确认调优后的结果是否符合目标。

**对于JVM服务可能出现的问题，我们一般依次排查内容为：**

  （1）. 宿主机器问题 （即你运行程序的机器的内存情况是否正常）

+ 内存，CPU，磁盘，内存和CPU问题，通过简单的top命令，可以看到具体的情况；

  （2）. JVM内存，是否频繁GC

  （3）. 线程栈，是否线程暴涨，线程死锁

+ 这种一般会导致CPU过高，可以用jstack来分析；

  （4）. 排查日志，检查程序代码

### 2. 调优工具

调优可以依赖、参考的数据有：

#### 1. 系统运行日志

+ 系统运行日志：系统运行日志就是在程序代码中打印出的日志，描述了代码级别的系统运行轨迹（执行的方法、入参、返回值等），一般系统出现问题，系统运行日志是首先要查看的日志。

#### 2. 堆栈错误信息

+ 堆栈错误信息：当系统出现异常后，可以根据堆栈信息初步定位问题所在，比如根据“java.lang.OutOfMemoryError: Java heap space”可以判断是堆内存溢出；根据“java.lang.StackOverflowError”可以判断是栈溢出；根据“java.lang.OutOfMemoryError: PermGen space”可以判断是方法区溢出等。

#### 3. gc日志

+ GC日志：程序启动时用 **-XX:+PrintGCDetails 和 -Xloggc:/data/jvm/gc.log** 可以在程序运行时把gc的详细过程记录下来，或者直接配置“-verbose:gc”参数把gc日志打印到控制台，通过记录的gc日志可以分析每块内存区域gc的频率、时间等，从而发现问题，进行有针对性的优化。 
  比如如下一段GC日志：

  ```java
  2018-08-02T14:39:11.560-0800: 10.171: [GC [PSYoungGen: 30128K->4091K(30208K)] 51092K->50790K(98816K), 0.0140970 secs] [Times: user=0.02 sys=0.03, real=0.01 secs] 
  
  2018-08-02T14:39:11.574-0800: 10.185: [Full GC [PSYoungGen: 4091K->0K(30208K)] [ParOldGen: 46698K->50669K(68608K)] 50790K->50669K(98816K) [PSPermGen: 2635K->2634K(21504K)], 0.0160030 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
  
  
  2018-08-02T14:39:14.045-0800: 12.656: [GC [PSYoungGen: 14097K->4064K(30208K)] 64766K->64536K(98816K), 0.0117690 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
  
  
  2018-08-02T14:39:14.057-0800: 12.668: [Full GC [PSYoungGen: 4064K->0K(30208K)] [ParOldGen: 60471K->401K(68608K)] 64536K->401K(98816K) [PSPermGen: 2634K->2634K(21504K)], 0.0102020 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
  ```

  上面一共是4条GC日志，来看第一行日志，“2018-08-02T14:39:11.560-0800”是精确到了毫秒级别的UTC 通用标准时间格式，配置了“-XX:+PrintGCDateStamps”这个参数可以跟随gc日志打印出这种时间戳，**“10.171”是从JVM启动到发生gc经过的秒数**。

  **第一行日志正文开头的“[GC”说明这次GC没有发生Stop-The-World（用户线程停顿），第二行日志正文开头的“[Full GC”说明这次GC发生了Stop-The-World，所以说，[GC和[Full GC跟新生代和老年代没关系，和垃圾收集器的类型有关系；**

  如果直接调用System.gc()，将显示[Full GC(System)。接下来的“[PSYoungGen”、“[ParOldGen”表示GC发生的区域，具体显示什么名字也跟垃圾收集器有关：

  + 比如这里的“[PSYoungGen”表示Parallel Scavenge收集器，“
  + [ParOldGen”表示Serial Old收集器，此外，Serial收集器显示“[DefNew”，ParNew收集器显示“[ParNew”等。

  **再往后的“30128K->4091K(30208K)”表示进行了这次gc后，该区域的内存使用空间由30128K减小到4091K，总内存大小为30208K。每个区域gc描述后面的“51092K->50790K(98816K), 0.0140970 secs”进行了这次垃圾收集后，整个堆内存的内存使用空间由51092K减小到50790K，整个堆内存总空间为98816K，gc耗时0.0140970秒。**

#### 4. 线程快照(stack)

顾名思义，根据线程快照可以看到线程在某一时刻的状态，当系统中可能存在请求超时、死循环、死锁等情况是，可以根据线程快照来进一步确定问题。**通过执行虚拟机自带的“jstack pid”命令，可以dump出当前进程中线程的快照信息;**（pid可以通过**jps命令来查看所有Java进程的名字和对应的pid**）

**jstack是java虚拟机自带的一种堆栈跟踪工具。jstack用于打印出给定的java进程ID或core file或远程调试服务的Java堆栈信息**，如果是在64位机器上，需要指定选项"-J-d64"，Windows的jstack使用方式只支持以下的这种方式：

```
jstack [-l] pid
```

主要分为两个功能： 

**a． 针对活着的进程做本地的或远程的线程dump；** 

**b． 针对core文件做线程dump。**

jstack用于生成java虚拟机**当前时刻**的线程快照。线程快照是当前java虚拟机内每一条线程正在执行的方法堆栈的集合，**生成线程快照的主要目的是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等**。 线程出现停顿的时候通过jstack来查看各个线程的调用堆栈，就可以知道没有响应的线程到底在后台做什么事情，或者等待什么资源。 如果java程序崩溃生成core文件，jstack工具可以用来获得core文件的java stack和native stack的信息，从而可以轻松地**知道java程序是如何崩溃和在程序何处发生问题**。另外，jstack工具还可以附属到正在运行的java程序中，看到当时运行的java程序的java stack和native stack的信息, 如果现在运行的java程序呈现hung的状态，jstack是非常有用的。

**所以，jstack命令主要用来查看Java线程的调用堆栈的，可以用来分析线程问题（如死锁）**

**线程状态**：

想要通过jstack命令来分析线程的情况的话，首先要知道线程都有哪些状态，下面这些状态是我们使用jstack命令查看线程堆栈信息时可能会看到的线程的几种状态：

> - **初始(NEW)**：新创建了一个线程对象，但还没有调用start()方法。 
>
>   + NEW状态的线程，不会被操作系统调度，因此不会执行，Java线程要执行，必须是RUNNABLE状态；
>
> - **运行(RUNNABLE)**：**Java线程中将就绪（ready）和运行中（running）两种状态笼统的称为“运行”**。线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取CPU的使用权，此时处于就绪状态（ready）。就绪状态的线程在获得CPU时间片后变为运行中状态（running）;
>
> - **阻塞(BLOCKED)**：表示线程阻塞于锁；
>
>   + 阻塞状态是线程因为某种原因放弃CPU使用权,暂时停止运行... ；
>
>   - 比如该线程正在访问的资源被其他线程锁住了（synchronized）；
>
> - **等待(WAITING)**：进入该状态的线程需要等待其他线程做出一些特定动作（通知或中断）;
>
>   - 比如一个线程调用wait（）方法后，它的状态就是WAITING；
>   - 当一个线程A调用了join（）方法，执行这句调用的线程B就得等待A线程执行完毕，在等待的图中，B线程的状态就会从RUNNABLE转换到WAITING状态；
>
> - **超时等待(TIMED_WAITING)**：该状态不同于WAITING，它可以在指定的时间后自行返回；
>
>   - 当一个线程调用sleep方法后，处于的状态就是TIMED_WAITING；
>   - 获得Synchronized隐式锁的线程，调用带超时参数的wait方法时，即wait方法带了时间参数；
>
> - **终止(TERMINATED)**：表示该线程已经执行完毕； 
>
>   + 线程执行完run方法，自动从RUNNABLE转换到TERMINATED；

----

**来看看这个简单的案例：** 🌟

```java
public class Test {
    public static void main(String[] args) {
        Object lock = new Object();
        Thread thread1 = new Thread(new Runnable()  {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(200000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable()  {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread2.start();
    }
}
```

**此时可以看到，线程1（thread1）获取到了锁，然后进入sleep状态，此时不会释放锁，而线程二就会阻塞于获取锁的过程，下面来看看这个进程的线程dump（我只截取了这两个线程的部分，其他的无关线程我也看不懂😯）** （注意看jstck是从下往上的，你从序号也可以发现，下面是11，上面是12，即最先启动的线程会在最底下打印，由此往上，还有这里的线程命名不是我们那个，上面我们没给线程命名，所以这里是Thread-0这种形式）

```bash
"Thread-1" #12 prio=5 os_prio=31 tid=0x00007fe5f6827800 nid=0x5703 waiting for monitor entry [0x0000700003d3a000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at com.yy.test.Test$2.run(Test.java:35)
	- waiting to lock <0x000000076ac235b8> (a java.lang.Object)
	at java.lang.Thread.run(Thread.java:748)

"Thread-0" #11 prio=5 os_prio=31 tid=0x00007fe5f3865800 nid=0x5603 waiting on condition [0x0000700003c37000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(Native Method)
	at com.yy.test.Test$1.run(Test.java:21)
	- locked <0x000000076ac235b8> (a java.lang.Object)
	at java.lang.Thread.run(Thread.java:748)

```

线程状态产生的原因对照：

> runnable:状态一般为RUNNABLE。
>
> in Object.wait():等待区等待,状态为WAITING或TIMED_WAITING。
>
> waiting for monitor entry:进入区等待,状态为BLOCKED。
>
> waiting on condition:等待区等待、被park。
>
> sleeping:休眠的线程,调用了Thread.sleep()。

**Wait on condition 该状态出现在线程等待某个条件的发生**。具体是什么原因，可以结合 stacktrace来分析。 最常见的情况就是线程处于sleep状态，等待被唤醒。 常见的情况还有等待网络IO：在java引入nio之前，对于每个网络连接，都有一个对应的线程来处理网络的读写操作，即使没有可读写的数据，线程仍然阻塞在读写操作上，这样有可能造成资源浪费，而且给操作系统的线程调度也带来压力。在 NewIO里采用了新的机制，编写的服务器程序的性能和可扩展性都得到提高。 

正等待网络读写，这可能是一个网络瓶颈的征兆。因为网络阻塞导致线程无法执行。一种情况是网络非常忙，几乎消耗了所有的带宽，仍然有大量数据等待网络读 写；另一种情况也可能是网络空闲，但由于路由等问题，导致包无法正常的到达。所以要结合系统的一些性能观察工具来综合分析，比如 netstat统计单位时间的发送包的数目，如果很明显超过了所在网络带宽的限制 ; 观察 cpu的利用率，如果系统态的 CPU时间，相对于用户态的 CPU时间比例较高；如果程序运行在 Solaris 10平台上，可以用 dtrace工具看系统调用的情况，如果观察到 read/write的系统调用的次数或者运行时间遥遥领先；这些都指向由于网络带宽所限导致的网络瓶颈。

#### 5. 堆转储快照等(jmap)

程序启动时推荐使用 “-XX:+HeapDumpOnOutOfMemory” 和 “-XX:HeapDumpPath=/data/jvm/dumpfile.hprof”，当程序发生内存溢出时，把当时的内存快照以文件形式进行转储（**也可以直接用jmap命令转储程序运行时任意时刻的内存快照**），事后对当时的内存使用情况进行分析。

jmap命令两个常用方式：

+ **jmap -heap pid** ：查看堆内存的使用情况，会具体显示每部分（伊甸区、from、to、老年代、永久带）的具体大小，使用了多少，还剩多少；
+ **jmap -histo pid** ：查看每个类有多少个对象，分别占用多少空间；
  + 这个可以具体到包名，不然直接这个命令查出来的数据量比较庞大；

可以直接用jmap（Memory Map for Java）查看堆内存信息那一时刻的信息；
执行**jmap -histo pid**可以打印出当前堆中所有每个类的实例数量和内存占用，如下，class name是每个类的类名（[B是byte类型，[C是char类型，[I是int类型），bytes是这个类的所有示例占用内存大小，instances是这个类的实例数量：

```java
num     #instances         #bytes  class name
----------------------------------------------
  1:          2291       29274080  [B
  2:         15252        1961040  <methodKlass>
  3:         15252        1871400  <constMethodKlass>
  4:         18038         721520  java.util.TreeMap$Entry
  5:          6182         530088  [C
  6:         11391         273384  java.lang.Lon
  7:          5576         267648  java.util.TreeMap
  8:            50         155872  [I
  9:          6124         146976  java.lang.String
 10:          3330         133200  java.util.LinkedHashMap$Entry
 11:          5544         133056  javax.management.openmbean.CompositeDataSupport
```

执行 jmap -dump 可以转储堆内存快照到指定文件，比如执行 jmap -dump:format=b,file=/data/jvm/dumpfile_jmap.hprof 3361 可以把当前堆内存的快照转储到dumpfile_jmap.hprof文件中，然后可以对内存快照进行分析。

生成的快照文件（.hprof）我们可以通过各种工具来打开进行更加形象和详细的分析，如jconsole、jvisualvm等工具，这些工具还可以实现一个实时的监测JVM的各种信息；

在jvisualvm菜单的“文件”-“装入”，选择堆内存快照，快照中的信息就以图形界面展示出来了，如下，主要可以查看每个类占用的空间、实例的数量和实例的详情等： 

![img](https://img-blog.csdn.net/2018081322012130?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjQ0Nzk1OQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)



#### 6. jstat（查看gc总体情况）

 查询GC情况，我们可以通过jstat命令，可以查询到每个堆分代的内存占用情况和Young GC和Full GC次数和时间等。如下：（每一千秒打印一次，总共打印10次）

```plain
  jstat -gcutil ${pid} 1000 10
```

![img](https://img-blog.csdn.net/20150402212059283?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvamFjaW4x/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)







### 3. JVM调优软件



④利用jconsole、jvisualvm分析内存信息(各个区如Eden、Survivor、Old等内存变化情况)，如果查看的是远程服务器的JVM，程序启动需要加上如下参数：

```java
"-Dcom.sun.management.jmxremote=true" 
"-Djava.rmi.server.hostname=12.34.56.78" 
"-Dcom.sun.management.jmxremote.port=18181" 
"-Dcom.sun.management.jmxremote.authenticate=false" 
"-Dcom.sun.management.jmxremote.ssl=false"
```

下图是jconsole界面，概览选项可以观测堆内存使用量、线程数、类加载数和CPU占用率；内存选项可以查看堆中各个区域的内存使用量和左下角的详细描述（内存大小、GC情况等）；线程选项可以查看当前JVM加载的线程，查看每个线程的堆栈信息，还可以检测死锁；VM概要描述了虚拟机的各种详细参数。（jconsole功能演示） 



### 4. JVM调优经验

JVM配置方面，一般情况可以先用默认配置（基本的一些初始参数可以保证一般的应用跑的比较稳定了），在测试中根据系统运行状况（会话并发情况、会话时间等），**结合gc日志、内存监控、使用的垃圾收集器等进行合理的调整，当老年代内存过小时可能引起频繁Full GC，当内存过大时Full GC时间会特别长。**

那么JVM的配置比如新生代、老年代应该配置多大最合适呢？答案是不一定，调优就是找答案的过程，物理内存一定的情况下，新生代设置越大，老年代就越小，Full GC频率就越高，但Full GC时间越短；相反新生代设置越小，老年代就越大，Full GC频率就越低，但每次Full GC消耗的时间越大。建议如下：

- **-Xms和-Xmx的值设置成相等**，堆大小默认为-Xms指定的大小，默认空闲堆内存小于40%时，JVM会扩大堆到-Xmx指定的大小；空闲堆内存大于70%时，JVM会减小堆到-Xms指定的大小。**如果在Full GC后满足不了内存需求会动态调整，这个阶段比较耗费资源。**
  + 我用jps -v可以看到我的参数为： -Xms128m -Xmx750m
- 新生代尽量设置大一些，让对象在新生代多存活一段时间，每次Minor GC 都要尽可能多的收集垃圾对象，**防止或延迟对象进入老年代的机会，以减少应用程序发生Full GC的频率。**
- 老年代如果使用CMS收集器，新生代可以不用太大，因为CMS的并行收集速度也很快，收集过程比较耗时的并发标记和并发清除阶段都可以与用户线程并发执行。
- 方法区大小的设置，1.6之前的需要考虑系统运行时动态增加的常量、静态变量等，1.7只要差不多能装下启动时和后期动态加载的类信息就行。

**代码实现方面，性能出现问题比如程序等待、内存泄漏除了JVM配置可能存在问题，代码实现上也有很大关系：**

- 避免创建过大的对象及数组：过大的对象或数组在新生代没有足够空间容纳时会直接进入老年代，如果是短命的大对象，会提前出发Full GC。
- 避免同时加载大量数据，如一次从数据库中取出大量数据，或者一次从Excel中读取大量记录，可以分批读取，用完尽快清空引用。
- 当集合中有对象的引用，这些对象使用完之后要尽快把集合中的引用清空，这些无用对象尽快回收避免进入老年代。
- 可以在合适的场景（如实现缓存）采用软引用、弱引用，比如用软引用来为ObjectA分配实例：SoftReference objectA=new SoftReference(); 在发生内存溢出前，会将objectA列入回收范围进行二次回收，如果这次回收还没有足够内存，才会抛出内存溢出的异常。 
  避免产生死循环，产生死循环后，循环体内可能重复产生大量实例，导致内存空间被迅速占满。
- 尽量避免长时间等待外部资源（数据库、网络、设备资源等）的情况，缩小对象的生命周期，避免进入老年代，如果不能及时返回结果可以适当采用异步处理的方式等。





## 2. 常见问题

### 1. 如何排查死循环导致的CPU过高

+ 先top命令找到CPU过高的进程PID
+ 再通过top命令列出这个进程下所有的线程，然后找到占用CPU过高的线程ID
+ 用Jstack命令分析这个线程（参数需要的是十六进制的线程ID，所以上面的ID要做一个进制转换），分析的结果就可以看到这个线程的状态和代码具体位置；

具体操作：

1. 使用如下命令找到最耗CPU的进程，然后再按一下 1，就会显示你服务器逻辑CPU的数量以及现在服务器CPU各个参数。占用最高的排在前面，我们可以看到占用高的ava进程

   > top -c

2. 找出占用高的Java线程，显示一个进程的线程运行信息列表，按一下P查看最高占用

   > top -Hp pid

3. 使用JVM自带的jstack命令导出当前所有线程的运行情况和线程当前状态，jstack工具可以用来获得core文件的java stack和native stack的信息，从而可以轻松地知道java程序是如何崩溃和在程序何处发生问题

   > jstack pid > error.log

4. 目标线程pid转16进制

   > printf “%x\n” pid

5. 查找error.log中pid16进制转化后的位置

   > grep pid error.log –color

### 2. JVM常用参数

+ GC日志：程序启动时用 **-XX:+PrintGCDetails 和 -Xloggc:/data/jvm/gc.log** 可以在程序运行时把gc的详细过程记录下来，或者直接配置“-verbose:gc”参数把gc日志打印到控制台，通过记录的gc日志可以分析每块内存区域gc的频率、时间等，从而发现问题，进行有针对性的优化。 

  **还可以加上-XX:+PrintGCTimeStamps和-XX:+PrintGCDateStamps 打印GC发生的具体日期和时间戳**，即很细粒度的一个时间；
  （注意只有-XX:+PrintGCTimeStamps啥都不会打印，他必须配合-XX:+PrintGCDetails一起使用）

  ```java
  2020-03-28T17:26:50.063-0800: [GC (Allocation Failure) [PSYoungGen: 6458K->528K(9216K)] 6458K->5656K(29696K), 0.0035669 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
  Heap
   PSYoungGen      total 9216K, used 2740K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
    eden space 8192K, 27% used [0x00000007bf600000,0x00000007bf829140,0x00000007bfe00000)
    from space 1024K, 51% used [0x00000007bfe00000,0x00000007bfe84010,0x00000007bff00000)
    to   space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
   ParOldGen       total 20480K, used 5128K [0x00000007be200000, 0x00000007bf600000, 0x00000007bf600000)
    object space 20480K, 25% used [0x00000007be200000,0x00000007be702010,0x00000007bf600000)
   Metaspace       used 2962K, capacity 4496K, committed 4864K, reserved 1056768K
    class space    used 328K, capacity 388K, committed 512K, reserved 1048576K
  ```

  上面打印的是配置了三个参数（-XX:+PrintGCTimeStamps、-XX:+PrintGCDateStamps 、-XX:+PrintGCDetails），

  如果这个程序没有发生GC的话，就是只会打印Heap下面那些，不会打印上面的GC信息；

  -XX:+PrintGCDetails的特点就是程序执行后都会打印出堆的信息，不论有无GC，有GC则还会打印GC信息，而-XX:PrintGC则是只在GC时打印出GC信息，而且还不会打印出堆的信息；

+ -XX:+PrintHeapAtGC：每次GC后，打印堆信息

+ -Xmx20m  -Xms20m    : 堆内存的最大值和最小值，我们最好将他们设计成一样的值；

+ -Xmn：设置新生代的大小

+ -XX:NewRatio ：新生代和老年代的比例（JDK8默认为2，表示新生代占老年代的1/2）

+ -XX:SurvivorRatio 可以配置新生代Eden：from：to，默认8:1:1

+ 程序启动时推荐使用 “-XX:+HeapDumpOnOutOfMemory” 和 “-XX:HeapDumpPath=/data/jvm/dumpfile.hprof”，当程序发生内存溢出时，把当时的内存快照以文件形式进行转储（**也可以直接用jmap命令转储程序运行时任意时刻的内存快照**），事后对当时的内存使用情况进行分析。

+ -Xss：设置栈的大小，栈都是每个线程独有的一个，所以一般是几百k的一个大小；

+ **使用Java 8以后，关于元空间的JVM参数有两个：`-XX:MetaspaceSize=N`和 `-XX:MaxMetaspaceSize=N`，对于64位JVM来说，元空间的默认初始大小是20.75MB，默认的元空间的最大值是无限,会进行动态扩展；** 但是我们一般都会去设置一个上限值；

+ **配置GC垃圾收集器：** 

  + -XX:+UseConcMarkSweepGC：使用CMS收集器
  + -XX:+UseG1GC ：使用G1收集器
  + -XX:+UseSerialGC：设置串行收集器
  + -XX:+UseParallelOldGC ：设置并行老年代收集器
  + -XX:+UseParNewGC：设置ParNew收集器

### 3. 如何排除死锁

Jstack 如果有死锁会显示dead lock



### 4. 调优案例

**JVM中GC调优的主要两个指标：**🌟 

+ 停顿时间：即STW（stop the word）时间；
+ 吞吐量：吞吐量=运行用户代码时间 / （运行用户代码时间+垃圾收集时间）；
  + 所谓高吞吐量，就是减少垃圾收集时间，让用户代码获得更长的运行时间；

**这是官方的说法，转换为我们的说法就是减少STW等同于减少Full GC，吞吐量也自然而然就上去了；**

首先我们应该设置如下参数：

```java
-XX:+PrintGCDetails -XX:+PrintGCStamps -XX:+PrintGCDateStamps 
-Xloggc:/Users/yangyun/IdeaProjects/gclog.log
```

然后我们启动项目，日志信息就会打印到上面的日志文件中；

我们把该日志文件导入到我们的GC easy这款在线工具上，就可以得到很多的图形界面分析：
（注意虽然使用工具，但工具里的信息我们都可以通过GC 日志找到，只是很麻烦而已，但必须会）

+ 堆内存的使用情况：Yong Genneration、Old Genneration、Meta Space（元空间）
  + 元空间是用的本地内存，存放的是类加载中类的信息文件；
+ 可以直接看到吞吐量；
+ 可以看到最大停顿时间、平均停顿时间；
+ 可以看到总共GC次数，其中Minor GC次数和Full GC次数；

我们在第一次运行后，绘制如下表格：

| 吞吐量  | 最大停顿 | 平均停顿 | YGC  | FGC  |
| ------- | -------- | -------- | ---- | ---- |
| 97.622% | 300ms    | 46.5ms   | 15   | 2    |

记录参数，然后开始调优：

+ 先调大一下元空间，在JVM中在上面的基础上加上如下参数：（我们元空间最开始是64M）

  ```
  -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=128M
  ```

  即我们调大了元空间，因为我们的项目加载的类比较多，元空间满的时候是会发生Full GC的，会去回收一些无用的类，所以我们这里尝试加大元空间，从64M加到128M；
  （注意默认JVM默认元空间无上限，但我们实际中肯定会去设置上限的，不然出了什么问题吧我们电脑本地内存吃完了咋整）

来看看调整后的数据：（直接根据工具得来的）

| 吞吐量  | 最大停顿 | 平均停顿 | YGC  | FGC  |
| ------- | -------- | -------- | ---- | ---- |
| 99.058% | 60ms     | 23.1ms   | 13   | 0    |

可以看到，吞吐量已经达到99%了，这是一个很理想的值，而且Full GC次数也下来了，因为下来了所以最大停顿才比较小嘛，停顿时间小了，所以吞吐量上来了嘛！！

这里已经可以不用调优了！！

**如果还有些问题，可以增大年轻代动态扩容增量（默认20%），可以减少YGC，参数如下：** 

```java
-XX:YoungGenerationSizeIncrement=30
```



> 当然，针对不同垃圾收集器，调优思路步骤也不一样，比如对于企业常用的G1垃圾收集器，我们可以根据他们提供的一些参数去适当的修改以达到一个具体的调优目的；
> 比如说对于G1垃圾收集器，有两个参数可以控制指定的吞吐量和指定的最大停顿时间：
>
> + -XX:GCTimeRatio=99
> + -XX:MaxGCPauseMillis=10
>
> **我们可以去设置这两个参数来调试出最佳状态，注意这两个参数并不是越往好处设置就会达到那样的效果，如果我们的硬件达不到那样的水平，反而会适得其反，所以我们才需要调优，就是去找出最合适的参数来；** 