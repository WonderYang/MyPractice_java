import java.util.Arrays;
public class Array{
    public static void main(String[] args){
        //1.数组动态初始化
        // int[] arr = new int[5];
        // arr[1] = 1;
        // arr[3] = 1;
        // for (int i = 0; i < arr.length; i++){
        //     System.out.println(arr[i]);            //打印出0 1 0 1 0，可见数组最开始会被初始化为0；
        // }

        // int a;
        // System.out.println(a);   //未被初始化不能使用，a默认值在主方法中无效

        //2.数组静态初始化
        // int[] arr = new int[]{1, 2, 2, 3, 4};  //new int[]{}这[]中必须为空，否则报错
        // System.out.println(arr[1]);

        //匿名数组
        //System.out.println(new int[]{2,3,4,5,6}.length);
        
        //数组的引用传递
        // int[] arr = null;
        // arr = new int[3];
        // int[] temp = arr;
        // //int[] temp = null;
        // //temp = arr;
        // System.out.println(temp[0]);
        
        //main函数里args数组的传参与输出
        // for (int i = 0; i < args.length; i++){
        //     System.out.println(args[i]);
        // }


        //二维数组的静态初始化
        // int [][] arr = new int [][]
        // {
        //     {1,2},
        //     {4,5,6},
        //     {7,7}
        // };
        // //二维数组的输出
        // for (int i = 0; i < arr.length; i++){
        //     for (int j = 0; j < arr[i].length; j++){
        //         System.out.print(arr[i][j]+" ");
        //     }
        //     System.out.println();   //可以起换行作用
        // }
        
        //定义一个Java方法来输出整型数组
        // int[] arr = new int[]{3,4,5,6};
        //引用传递
        // arrprint(arr);

        //定义一个方法来返回整型数组
        //引用传递
        // int[] arr = init();
        // for (int i: arr){
        //     System.out.print(i+" ");   //这里是i，不是arr[i];
        // }


        //定义一个方法将整型数组中的全部元素*2
        // int[] arr = new int[]{2,3,4,5};
        // mul(arr);
        // for (int i: arr){
        //     System.out.print(i+" ");
        // }

        //实现数组排序
        // int[] arr = new int[]{2,5,3,7,1};
        // java.util.Arrays.sort(arr);
        // for (int i: arr){
        //     System.out.print(i+" ");
        // }

        //实现数组的元素替换（部分元素）(且必须是连续的)
        // int[] source = new int[]{1,2,3,4,5,6};
        // int[] aim = new int[]{7,8,9,10};
        // System.arraycopy(source, 2, aim, 1, 2);
        // for (int i: aim){
        //     System.out.print(i+" ");   //7 3 4 10
        // }

        //数组全拷贝
        int[] arr = new int[]{1,2,3,4,5};
        int[] arr2 = Arrays.copyOf(arr, 10);   //切记是copyOf，O是大写啊
        for (int i : arr2){
            System.out.print(i+" ");
        }




    }

    //定义一个Java方法来输出整型数组
    // public static void arrprint(int[] temp){
    //     for (int i: temp)
    //     {
    //         System.out.print(i+", ");
    //     }
    // }

    //定义一个方法来返回整形数组
    // public static int[] init(){
    //     return new int[]{2,3,4,5,6};   //匿名数组
    // }

    //定义一个方法将整型数组中的全部元素*2
    // public static void mul(int[] arr){
    //     for (int i = 0; i < arr.length; i++){
    //         arr[i] *= 2;
    //     }
    // }
}