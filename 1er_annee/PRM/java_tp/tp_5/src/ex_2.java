import java.util.Scanner;

public class ex_2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = 0;
        double sum = 0;
        int input = 0;
        do {
            input = s.nextInt();
            n++;
            sum += input;
        } while (input != -1);
        n--;
        sum += 1;
        System.out.println("moyenne = " + sum / n);
    }
}