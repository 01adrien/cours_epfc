import java.util.Scanner;

public class ex_3 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = 0;
        while (n <= 0) {
            System.out.print("n = ");
            n = s.nextInt();
        }
        int i = n;
        double sum = 0;
        while (i != 0) {
            sum += s.nextInt();
            i--;
        }
        System.out.println(sum / n);
    }
}
