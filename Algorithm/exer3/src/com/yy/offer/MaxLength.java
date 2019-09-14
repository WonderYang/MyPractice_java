package com.yy.offer;

import java.util.HashSet;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:27 2019/9/14
 */
public class MaxLength {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        if(n == 1) {
            return 1;
        }
        int max = 0;
        for(int i=0; i<n; i++) {
            for(int j=i; j<n; j++) {
                if(isRepeat(s, i, j)) {
                    max = Math.max(max, j-i+1);
                }
            }
        }
        return max;
    }
    public boolean isRepeat(String s, int i, int j) {
        HashSet<Character> set = new HashSet<>();
        while(i <= j) {
            Character ch = s.charAt(i);
            if(set.contains(ch)) {
                return false;
            }

            set.add(ch);
            i++;
        }
        return true;
    }
}
