import java.util.Scanner;

public class ex_5 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();

        if (a > b) {
            System.out.println("a > b");
        } else if (b > a) {
            System.out.println("b > a");
        } else {
            System.out.println("b = a");
        }
    }
}
