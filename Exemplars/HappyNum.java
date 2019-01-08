public class HappyNum {
    public static void main(String[] args) {
        int num = 20;
        System.out.println(isHappy(num));
    }
    public static boolean isHappy(int num) {
        while(true) {
            int count = 0;
            //求num的每个位平方的和
            while (num>0) {
                count += (num%10)*(num%10);
                num /= 10;
            }

            num = count;
    
            if (count == 1) {
                return true;
            }

            else if (count == 89) {   //若不是快乐数，则总有一个中间值为89（找规律得）
                return false;
            }
        } 
    }
}