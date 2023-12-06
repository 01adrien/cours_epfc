import java.util.Scanner;

public class ex_7 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        if (n > 0){
            System.out.print("!" + n + " = ");
            for (int i = n - 1; i > 0; i--) {
                 n *= i;
            }
            System.out.println(n);
        } else {
            if (n == 0) {
                System.out.println("!0 = 1");
            } else {
                System.out.println("invalide");
            }
        }

    }
}