package www.yy.heap;

import www.yy.MyQueue.PriorityQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : YangY
 * @Description : 找前K个高频元素
 * @Time : Created in 16:31 2019/10/9
 */

public class FindTopK {
    public static void main(String[] args) {
        FindTopK findTopK = new FindTopK();
        int[] nums = new int[] {1,1,1,2,2,3,-2,-2,-4,-4,-4,-4};
        List<Integer> list = findTopK.topKFrequent(nums, 3);
        System.out.println(list);
    }
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
            return (o.frequent - this.frequent);
        }
    }
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Freq> map = new HashMap<>();
        for(int i: nums) {
            if(map.get(i) == null) {
                map.put(i, new Freq(i,1));
            }else {
                Freq freq = map.get(i);
                freq.frequent += 1;
                map.put(i, freq);
            }
        }

        PriorityQueue<Freq> priorityQueue = new PriorityQueue<Freq>();
        //开始遍历map集合
        for(int key: map.keySet()) {
            if(priorityQueue.size() < k) {
                priorityQueue.enqueue(new Freq(key, map.get(key).frequent));
            }
                //优先级队列元素达到K之后，每次替换队列中频率最小的元素
            if (map.get(key).frequent > priorityQueue.peek().frequent) {
                //将优先级队列中最小的频率替换
                priorityQueue.dequeue();
                priorityQueue.enqueue(map.get(key));
            }
        }

        List<Integer> list = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            list.add(priorityQueue.dequeue().key);
        }
        return list;
    }
}
