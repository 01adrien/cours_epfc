import java.util.Arrays;

public class App {

    public static int ex1(int[] array) {
        if (array.length == 0) {
            return -1;
        }
        int min = array[0];
        for (int i : array) {
            min = i < min ? i : min;
        }
        return min;
    }

    public static void ex2(int[] array) {
        if (array.length > 1) {
            int j = array.length - 1;
            for (int i = 0; i < (array.length / 2); i++, j--) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
    }

    public static void ex3(int[] array) {
        if (array.length > 1) {
            int last = array[array.length - 1] ;
            for (int i = array.length -2; i != -1; i--) {
                array[i + 1] = array[i];
            }
            array[0] = last;
        }
        
    }

    public static int[] ex4(int[] array1, int[] array2) {
        int length = array1.length + array2.length;
        int[] array3 = new int[length];

        for (int i = 0; i < length ; i++) {
            if (i < array1.length) {
                array3[i] = array1[i];
            } else {
                array3[i] = array2[i - array1.length];
            }
        }
        return array3;
        
    }

    public static void main(String[] args) throws Exception {
        int[] array1 = {1,2,3,4};
        int[] array2 = {5,6,7,8};
        System.out.println();
        System.out.println(Arrays.toString(ex4(array1, array2)));
    }
}
