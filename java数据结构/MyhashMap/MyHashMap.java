package com.yy.MyHashMap;

import javax.swing.tree.TreeNode;
import java.util.Objects;

/**
 * @program: testExer
 * @description: 我的手写HashMap
 * @author: yangyun86
 * @create: 2020-03-13 10:59
 **/
public class MyHashMap<K, V> {


    transient Node<K, V>[] hashTable;   //底层链表数组，没有初始化

    public static final int TREEIFY_THRESHOLD = 8;  //链表长度大于8时进行树化
    static final int UNTREEIFY_THRESHOLD = 6;
    transient int modCount;
    transient int size;  //当前元素个数
    int threshold; //阈值，即总容量 * 加载因子，size超过阈值就会扩容
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // 16

    double DEFAULT_LOAD_FACTOR = 0.75;


    static class Node<K, V> {
        final int hashCode;  //存放该key的hashCode，这个是根据我们的hash算法算出来的，保存它，在扩容迁移数据时能直接获取嘛！
        final K key;    //用final修饰的key，必须初始化（这里用构造方法），不能更改
        V value;
        Node<K, V> next;

        public Node(int hashCode, K key, V value, Node<K, V> next) {
            this.hashCode = hashCode;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
        //这里要返回之前的值哟
        public V setValue(V val) {
            V oldVal = value;
            this.value = val;
            return oldVal;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) { // 同一对象
                return true;
            }else if(obj instanceof Node) {
                Node<?, ?> node = (Node<?, ?>) obj;
                if (Objects.equals(key, node.key) && Objects.equals(value, node.value)) {
                    return true;
                }
                return false;
            }else {
                //根本不是Node类型的，直接返回false
                return false;
            }
        }
    }

    public V put(K key, V value) {
        int hash = hash(key);
        Node<K, V>[] table = hashTable;
        Node<K, V> first;
        int length;
        //这里代表table还没初始化，即第一次进行put操作
        if (table==null || (length =table.length)==0) {
            length = (table = resize()).length;
        }
        int index = hash & (length - 1);  //下标

        //这里代表要插入的位置为null，即这个位置没有发生冲突，直接插入即可
        if((first = table[index]) == null) {
            table[index] = new Node<>(hash, key, value, null);
        }else {
            Node<K, V> node = null;  //这个node代表在这个链中跟要插入的key相同的节点
            //这里代表发生了冲突了,首先查看第一个节点是不是我们要插入的节点，即去比较他们的key是否相等，如果相等，替换掉value即可
            if (first.hashCode == hash &&
                    (key==first.key || (key!=null && key.equals(first.key))) ) {
                node = first;
            }else if (first instanceof TreeNode) {
                //......
            }else {
                //此时代表不是树结构也不是头节点，为什么要把头节点单独列出？
                // 因为无论是树或者链表，头节点的与插入的key相同时，操作方法都是一样的，所以单独列出；
                for (int count=0; ; count++) {
                    if ((node=first.next) == null) {
                        first.next = new Node<>(hash, key, value, null);
                        if (count > TREEIFY_THRESHOLD) {
                            //...转换为红黑树
                        }
                        break;
                    }
                    if (node.hashCode == hash &&
                            (key==node.key || (key!=null && key.equals(node.key))) ) {
                        break;
                    }
                    first = node;  //便于继续往下遍历
                }
            }
            //找到了该链中key与新插入key重复的值，hashMap的key是不能重复的，所以替换掉其value即可
            if (node != null) {
                //此时将node这个节点的value更新为现在的value
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        //到这里说明新插入的节点在原HashMap中不存在，是新增的一个节点
        modCount++;  //维护更改次数
        if (++size > threshold) {
            resize();
        }
        return null;
    }

    private int hash(K key) {
        int h;
        //这里能看出key为空的话hash为0哦！！
        return key==null? 0 : (h=key.hashCode()) ^ (h>>>16);
    }

    //获取当前元素个数，即容量
    public int size() {
        return size;
    }

    public int cap() {
        return hashTable.length;
    }

    public Node<K, V>[] resize() {
        Node<K, V>[] oldTatble = hashTable;
        int oldCap = oldTatble==null? 0 : oldTatble.length;
        int oldThr = threshold;
        int newCab=0, newThr = 0;

        if (oldCap > 0) {  //本来这里还要判断扩容后的cap是否超过了最大值，这里为了简便就不考虑了
            newCab = oldCap << 1;
            newThr = oldThr << 1;    //容量和阈值都扩大为原来的2倍，此时还是满足倍数关系的；
        }else if (oldThr > 0) {

        }else {
            //这里代表oldCap等于0，初始化总长为16，阈值为16*0.75=12
            newCab = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(newCab * DEFAULT_LOAD_FACTOR);
        }
        //更新阈值，新建一个hashTable，容量为上面计算出来的扩容容量
        threshold = newThr;
        Node<K, V>[] newHashTable = new Node[newCab];
        //将hashTable替换为新容量的table，这里是多线程操作的话，一个线程执行到这就被挂起了，其他线程去执行时操作的就是一个空表；🌟
        hashTable = newHashTable;

        if (oldTatble != null) {

            for (int i=0; i<oldCap; i++) { //遍历原来的hashmap每个桶头节点（即数组元素），进行转移到新的map上
                Node<K, V> first;
                if ((first=oldTatble[i]) != null) {
                    oldTatble[i] = null;  //助于原table的垃圾回收
                    if (first.next == null) {
                        //该位置就一个节点，直接重新计算一下下标放到新的table中即可
                        hashTable[first.hashCode&(newCab-1)] = first;
                    }else if( first instanceof TreeNode){
                       //......
                    }else {
                        Node<K, V> cur;
                        Node<K, V> lHead = null, lTail = null;
                        Node<K, V> hHead = null, hTail = null;
                        while (first != null) {
                            cur = first;
                            if((cur.hashCode & oldCap) == 0) {
                                if (lHead == null) {
                                    lHead = cur;
                                    lTail = cur;
                                }else {
                                    lTail.next = cur;
                                    lTail = cur;
                                }
                            }else if((cur.hashCode & oldCap) == 1){
                                if (hHead == null) {
                                    hHead = cur;
                                    hTail = lHead;
                                }else {
                                    hTail.next = cur;
                                    hTail = cur;
                                }
                            }
                            first = first.next;
                        }

                        if (lTail != null) {
                            lTail.next = null;   //注意啦！！这里一定要置空哟，因为原来的节点加到新的链表中，这个节点不一定刚好是原来链表的最后一个；
                            hashTable[i] = lHead;
                        }
                        if (hTail != null) {
                            hTail.next = null;
                            hashTable[i + oldCap] = hHead;
                        }
                    }
                }
            }

        }

        //说明这是第一次扩容，根本没有数据转移到新的table上
        return newHashTable;

    }

}