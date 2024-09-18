import java.util.Scanner;

public class ex_6 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int max = s.nextInt();
        int n = 1;
        while ((n * n) < max) {
            System.out.print(n + " ");
            n++;
        }
    }
}
