import java.util.Scanner;

public class ex_12 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();
        int c = s.nextInt();

        if (a == b || a == c) {
            System.out.println(a);
        }
        if (b == c) {
            System.out.println(b);
        }
    }
}
