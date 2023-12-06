import java.util.Scanner;

public class ex_11 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();
        int c = s.nextInt();

        int max1 = 0;
        int max2 = 0;

        if (a > b && a > c) {
            max1 = a;
            max2 = b > c ? b : c;
        } else if (b > a && b > c) {
            max1 = b;
            max2 = a > c ? a : c;
        } else {
            max1 = c;
            max2 = a > b ? a : b;
        }
        System.out.println(max1 + " " + max2);
    }
}
