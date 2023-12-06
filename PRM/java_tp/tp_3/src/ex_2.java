import java.util.Scanner;

public class ex_2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int i = n;
        double sum = 0;
        while (i != 0) {
            sum += s.nextInt();
            i--;
        }
        System.out.println(sum / n);
    }
}