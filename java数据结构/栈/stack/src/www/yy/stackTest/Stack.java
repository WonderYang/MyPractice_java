package www.yy.stackTest;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 16:53 2019/5/19
 */
public interface Stack <T>{
    /**
     * 入栈
     * @param t
     * @return
     */
    boolean push(T t);

    /**
     * 出栈
     * @return
     */
    T top();

    /**
     * 返回栈顶元素但不出栈
     */
    T peek();
    int size();
}
