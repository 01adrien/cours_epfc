import java.util.Scanner;

public class ex_1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n;
        double sum = 0;
        do {
            System.out.println("Entrer un nombre positif");
            n = s.nextInt();
        } while (n <= 0);
        int m = n;
        do {
            sum += s.nextInt();
            n--;
        } while (n > 0);
        System.out.println("moyenne = " + sum / m);
    }
}