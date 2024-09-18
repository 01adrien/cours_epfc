import java.util.Scanner;

public class ex_11 {
    public static void main(String[] args) {
        int a, b, c, d;
        Scanner scan = new Scanner(System.in);
        a = scan.nextInt();
        b = scan.nextInt();
        c = scan.nextInt();
        d = scan.nextInt();

        int temp1 = a;

        a = b;
        b = c;
        c = d;
        d = temp1;

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }
}
