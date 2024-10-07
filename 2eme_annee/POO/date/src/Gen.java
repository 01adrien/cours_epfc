import java.util.Random;

public class Gen {

    public static <E> void swap(E[] arr, int a, int b) {
        E tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static <E> void shuffle(E[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int max = arr.length;
            int min = i;
            int pos = new Random().nextInt(max - min) + min;
            swap(arr, i, pos);

        }
    }

    public static <E> void printArray(E[] arr) {
        System.out.print("[ ");
        for (E e : arr) {
            System.out.print(e + " ");
        }
        System.out.println("]");
    }
}
