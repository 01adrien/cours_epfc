import java.util.Scanner;

public class ex_9 {
    public static void main(String[] args) {
        int a, b, c, d;
        Scanner scan = new Scanner(System.in);
        a = scan.nextInt();
        b = scan.nextInt();
        c = scan.nextInt();
        d = scan.nextInt();

        int temp1 = a;
        int temp2 = c;
        a = d;
        c = b;
        b = temp2;
        d = temp1;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);

    }
}
