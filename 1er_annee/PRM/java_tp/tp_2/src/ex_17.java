import java.util.Scanner;

public class ex_17 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();

        if (a < 0 && b > 0 || b < 0 && a > 0) {
            System.out.println("negative");
        } else if (a == 0 || b == 0) {
            System.out.println("zero");
        } else {
            System.out.println("posit");
        }

    }
}
