import java.util.Scanner;

public class ex_18 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();
        
        if (b < 0 && a < 0) {
            System.out.println("negative");

        }
        if (b > 0 && a > 0) {
            System.out.println("posit");

        } else if (a >= 0) {
            if ((b * -1) > a) {
                System.out.println("negative");
            } else if ((b * -1) == a) {
                System.out.println("zero");
            } else {
                System.out.println("posit");
            }

        } else if (b >= 0) {
            if ((a * -1) > b) {
                System.out.println("negative");
            } else if ((a * -1) == b) {
                System.out.println("zero");
            } else {
                System.out.println("posit");
            }

        }
    }
}
