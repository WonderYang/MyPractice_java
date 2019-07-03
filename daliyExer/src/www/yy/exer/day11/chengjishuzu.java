package www.yy.exer.day11;

/**
 * @Author : YangY
 * @Description :链接：https://www.nowcoder.com/questionTerminal/94a4d381a68b47b7a8bed86f2975db46
 * 来源：牛客网
 *
 *   给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]
 *    *A[i+1]*...*A[n-1]。不能使用除法。
 * @Time : Created in 22:13 2019/6/1
 */
public class chengjishuzu {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        chengjishuzu chengjishuzu = new chengjishuzu();
        int[] arr2 = chengjishuzu.multiply(arr);
    }
    public int[] multiply(int[] A) {
        int length = A.length;
        int[] B = new int[length];
        if(length != 0 ){
            B[0] = 1;
            //计算下三角连乘
            for(int i = 1; i < length; i++){
                B[i] = B[i-1] * A[i-1];
            }
            int temp = 1;
            //计算上三角
            for(int j = length-2; j >= 0; j--){
                temp *= A[j+1];
                B[j] *= temp;
            }
        }
        return B;
    }
}
