public class ArrayCount{
    public static void main(String[] args){
        int[] arr = new int[]{6,10,4,8,2};
        int[] arr1 = new int[]{4,5,6};
        System.out.println("max is "+arrayMaxElement(arr));
        System.out.println("min is "+arrayMinElement(arr));
        System.out.println("sum is "+arraySum(arr));
        //数组拼接检测
        int []arr3 = arrayJoin(arr, arr1);
        printArray(arr3);
        //数组截取检测
        printArray(arraySub(arr, 1, 3));
        //数组反转检测
        arrayReverse(arr);
        printArray(arr);


        
    }
    //数组打印
    public static void printArray(int[] temp){
        for (int i : temp){
            System.out.print(i+" ");
        }
        System.out.println();
    }

    //数组找最大值
    public static int arrayMaxElement(int[] temp){
        int max = temp[0];
        for (int i = 1; i < temp.length; i++){
            if (temp[i] > max){
                max = temp[i];
            }
        }        
        return max;

    }

    //数组找最小值
    public static int arrayMinElement(int[] temp){
        int min = temp[0];
        for (int i = 1; i < temp.length; i++){
            if (temp[i] < min){
                min = temp[i];
            }
        }        
        return min;
    }

    //数组求和
    public static int arraySum(int[] temp){
        int sum = 0;
        for (int i = 0; i < temp.length; i++){
            sum += temp[i];
        }
        return sum;
    }

    //数组拼接
    public static int[] arrayJoin(int[] a, int[] b){
        int[] arr = new int[a.length+b.length];
        for (int i = 0; i < a.length; i++){
            arr[i] = a[i];
        }
        for (int i = a.length, j = 0; i < arr.length; i++){
            arr[i] = b[j++];
        }
        return arr;
    }

    //数组截取
    public static int[] arraySub(int[] temp, int start, int end){
        int[] arr = new int[end - start + 1];
        for (int i = start, j = 0; i <= end; i++){
            arr[j++] = temp[i];
        }
        return arr;
    }

    //数组反转
    public static void arrayReverse(int[] temp){
        for (int num = 0, i = 0, j = temp.length-1; num < temp.length / 2; num++, i++, j--){
            int mid;
            mid  = temp[i];
            temp[i] = temp[j];
            temp[j] = mid;
        }
    }

}