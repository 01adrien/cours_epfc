import java.util.Scanner;

public class ex_3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        a *= a < 0 ? -1 : 1;
        System.out.println(a);
    }
}