package www.yy.day21;

import java.util.Scanner;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/9f6b8f6ec26d44cfb8fc8c664b0edb6b
 * 来源：牛客网
 * 难度：简单
 * NowCoder生活在充满危险和阴谋的年代。为了生存，他首次发明了密码，用于军队的消息传递。假设你是军团中的一名
 * 军官，需要把发送来的消息破译出来、并提供给你的将军。
 * 消息加密的办法是：对消息原文中的每个字母，分别用该字母之后的第5个字母替换（例如：消息原文中的每个字母A 都
 * 分别替换成字母F），其他字符不 变，并且消息原文的所有字母都是大写的。密码中的字母与原文中的字母对应关系如下。
 * 密码字母：A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
 * 原文字母：V W X Y Z A B C D E F G H I J K L M N O P Q R S T U
 *
 * 输入描述:
 * 输入包括多组数据，每组数据一行，为收到的密文。
 * 密文仅有空格和大写字母组成。
 * 输出描述:
 * 对应每一组数据，输出解密后的明文。
 * 示例1
 * 输入
 * HELLO WORLD
 * SNHJ
 * 输出
 * CZGGJ RJMGY
 * NICE
 * @Time : Created in 14:10 2019/7/19
 */

//唯一要注意的就是A~E的转换，不是简单的减5
public class Decode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNextLine()) {
            String password = scanner.nextLine();
            StringBuilder res = new StringBuilder();
            for(int i=0; i<password.length(); i++) {
                char s = ' ';
                char pass = password.charAt(i);
                if(pass != ' ') {
                    if(pass >='A' && pass <= 'E') {
                        s = pass;
                        s += 'Z'-'A'-4;
                    }else {
                        s = pass;
                        s -= 5;
                    }
                }
                res.append(s);
            }
            System.out.println(res);
        }

    }
}
