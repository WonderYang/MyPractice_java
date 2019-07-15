package www.yy.exer.day18;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/ab900f183e054c6d8769f2df977223b5

 * 牛牛又从生物科研工作者那里获得一个任务,这次牛牛需要帮助科研工作者从DNA序列s中找出最短没有出现在DNA序列s中的DNA片段的长度。
 * 例如:s = AGGTCTA
 * 序列中包含了所有长度为1的('A','C','G','T')片段,但是长度为2的没有全部包含,例如序列中不包含"AA",所以输出2。
 *
 * 输入描述:
 * 输入包括一个字符串s,字符串长度length(1 ≤ length ≤ 2000),其中只包含'A','C','G','T'这四种字符。
 *
 * 输出描述:
 * 输出一个正整数,即最短没有出现在DNA序列s中的DNA片段的长度。
 * 示例1
 * 输入
 * AGGTCTA
 * 输出
 * 2
 * @Time : Created in 12:38 2019/7/12
 */
public class DNAStr {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        for(int i=1; i<=str.length(); i++) {
            Set<String> set = new TreeSet<>();
            for(int j=0; j<str.length()-i; j++) {
                //set集合会过滤掉重复元素
                set.add(str.substring(j,j+i));
            }
            if(set.size() < Math.pow(4,i)) {
                System.out.println(i);
                return;
            }
        }
    }
}
