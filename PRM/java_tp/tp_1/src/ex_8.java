import java.util.Scanner;

public class ex_8 {
    public static void main(String[] args) {
        int a, b, c;
        Scanner scan = new Scanner(System.in);
        a = scan.nextInt();
        b = scan.nextInt();
        c = scan.nextInt();

        int temp = a;
        a = c;
        c = temp;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}