package www.yy.exer.day15;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/5ee8df898312465a95553d82ad8898c3
 * 来源：牛客网
 *
 * 小易总是感觉饥饿，所以作为章鱼的小易经常出去寻找贝壳吃。最开始小易在一个初始位置x_0。对于小易所处
 * 的当前位置x，他只能通过神秘的力量移动到 4 * x + 3或者8 * x + 7。因为使用神秘力量要耗费太多体力，
 * 所以它只能使用神秘力量最多100,000次。贝壳总生长在能被1,000,000,007整除的位置(比如：位置0，位置
 * 1,000,000,007，位置2,000,000,014等)。小易需要你帮忙计算最少需要使用多少次神秘力量就能吃到贝壳。
 *
 * 输入描述:
 * 输入一个初始位置x_0,范围在1到1,000,000,006
 * 输出描述:
 * 输出小易最少需要使用神秘力量的次数，如果使用次数使用完还没找到贝壳，则输出-1
 * 示例1
 * 输入
 * 125000000
 * 输出
 * 1
 *
 * [解题思路]
 * 小易的移动公式:
 * f(x) = 4*x + 4
 * g(x) = 8*x + 7
 * 计算可以得出两个规律：
 * 1. g(f(x)) = f(g(x)) 即f和g的执行顺序没有影响
 * 2. f(f(f(x))) = g(g(x)) 即做3次f的变换等价于做2次g的变换
 * 由规律1可以得出对于一个可行方案，可以调整其变换顺序。比如：ffggfggff 可以转换为fffffgggg
 * 由规律2并且为了减少执行次数，每3个f可以转换为2个g 如方案fffffgggg可以转换为ffgggggg. 因此一
 * 个最优的策略：f的执行次数为 0， 1， 2。 对于输入x， 只需要要求x ,4x+3, 4(4x+3)+3 的最小g执行
 * 次数即可。
 * @Time : Created in 16:45 2019/7/2
 */
public class EatBake {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLong()) {
            Long x = scanner.nextLong();
            Long aim = 1000000007L;
            int count = 100000;
            Long[] arr = new Long[3];
            arr[0] = x;
            arr[1] = 4*x+3;
            arr[2] = 4*(4*x+3)+3;
            int min = count;
            for(int i=0; i<3; i++) {
                int step = i;
                while(arr[i]!=0 && step<min) {
                    arr[i] = (8*arr[i] + 7 )%aim;
                    step++;
                }
                min = step<min? step:min;
            }
            if(min < count) {
                System.out.println(min);
            }else {
                System.out.println("-1");
            }
        }
    }
}
