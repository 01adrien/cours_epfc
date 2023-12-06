import java.util.Scanner;

public class ex_8 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int a = s.nextInt();
        int b = s.nextInt();
        boolean neg = false;
        double res = 1;
        if (a == 0) {
            if (b == 0) {
                System.out.println("exposant invalide");
            } else {
                System.out.println(a + " exp " + b + " = 0");
            }
        } else if (b == 0) {
            System.out.println(a + " exp " + b + " = 1");
        } else {
            if (b < 0) {
                neg = true;
                b *= -1;
            }
            for (int i = b; i > 0; i--) {
                res *= a;
            }
            if (neg == true) {
                res = 1 / res;
            }
            System.out.println(a + " exp " + b + " = " + res);
        }
    }
}