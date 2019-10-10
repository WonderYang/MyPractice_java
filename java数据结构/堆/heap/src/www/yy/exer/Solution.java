package www.yy.exer;

import java.util.*;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:19 2019/10/10
 */
public class Solution {

    class Freq implements Comparable<Freq>{
        private int key;
        private int frequent;

        public Freq(int key, int frequent) {
            this.key = key;
            this.frequent = frequent;
        }

        @Override
        public int compareTo(Freq o) {
            //频率越小，优先级越高
            return (this.frequent - o.frequent);
        }
    }
    public List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer, Freq> hashMap = new HashMap<>();
        for(int x: nums) {
            if(hashMap.get(x) == null) {
                hashMap.put(x, new Freq(x, 1));
            }else {
                int frequent = hashMap.get(x).frequent + 1;
                hashMap.put(x, new Freq(x, frequent));
            }
        }
        PriorityQueue<Freq> priorityQueue = new PriorityQueue<Freq>();
        for(int key: hashMap.keySet()) {
            Freq freq = hashMap.get(key);
            if(priorityQueue.size() < k) {
                priorityQueue.add(freq);
            }else {
                //遍历的当前Freq的频率（frequent）比优先级队列顶端（堆顶）的频率高
                if(hashMap.get(key).compareTo(priorityQueue.peek()) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(freq);
                }
            }
        }

        List<Integer> list = new ArrayList<>();
        while(!priorityQueue.isEmpty()) {
            list.add(priorityQueue.poll().key);
        }
        return list;
    }
}
