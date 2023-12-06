import java.util.Scanner;

public class ex_15 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();

        if (a > 0) {
            if (a == 1) {
                System.out.println("'a' vaut 1");
            }
        } else {
            System.out.println("'a' est inferieur ou egal a zero");
        }

    }
}
