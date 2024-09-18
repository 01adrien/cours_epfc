import java.util.Scanner;

public class ex_5 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int input;
        double sum = 0;
        int count = 0;
        while ((input = s.nextInt()) != -1) {
            sum += input;
            count++;
        }
        System.out.println(sum / count);
    }
}
