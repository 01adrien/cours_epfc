import java.util.Scanner;

public class ex_9 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();
        int c = s.nextInt();

        int max = a;
        if (a < b) max = b;
        if (c > max) max = c;

        System.out.println(max);
    }
}
