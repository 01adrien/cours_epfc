import java.util.Scanner;

public class ex_4 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int max = s.nextInt();
        int n = 0;
        int sum = 0;
        while ((sum + n) < max) {
            n++;
            System.out.print(n + " ");
            sum += n;
        }
        System.out.println();
        System.out.println(n);
    }
}
