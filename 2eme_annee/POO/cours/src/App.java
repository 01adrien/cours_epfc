import java.util.Arrays;
import java.util.Scanner;

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
            int last = array[array.length - 1];
            for (int i = array.length - 2; i != -1; i--) {
                array[i + 1] = array[i];
            }
            array[0] = last;
        }

    }

    public static int[] ex4(int[] array1, int[] array2) {
        int length = array1.length + array2.length;
        int[] array3 = new int[length];
        for (int i = 0; i < length; i++) {
            if (i < array1.length) {
                array3[i] = array1[i];
            } else {
                array3[i] = array2[i - array1.length];
            }
        }
        return array3;

    }

    public static int[] ex5(int[] a, int[] b) {
        int length = a.length + b.length;
        int[] c = new int[length];
        int ia, ib, ic;
        ia = ib = ic = 0;
        while (ia < a.length && ib < b.length) {
            if (a[ia] < b[ib]) {
                c[ic++] = a[ia++];
            } else {
                c[ic++] = b[ib++];
            }
        }
        while (ia < a.length) {
            c[ic++] = a[ia++];
        }
        while (ib < b.length) {
            c[ic++] = b[ib++];
        }
        return c;
    }

    public static void ex6() {
        try (Scanner s = new Scanner(System.in)) {
            System.out.print("Quelle largeur? ");
            int lg = s.nextInt();
            System.out.print("Quelle hauteur? ");
            int ht = s.nextInt();
            int mat[][] = new int[lg][ht];
            int value;
            for (int i = 0; i < lg; i++) {
                for (int j = 0; j < ht; j++) {
                    System.out.println("Quelle valeur pour (" + i + ", " + j + ") ? ");
                    value = s.nextInt();
                    mat[i][j] = value;
                }
            }
            System.out.println("Voila la matrice:");
            for (int i = 0; i < lg; i++) {
                for (int j = 0; j < ht; j++) {
                    System.out.print(mat[i][j] + " ");
                }
                System.out.println("");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ex6();
    }
}
