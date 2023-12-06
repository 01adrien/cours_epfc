import java.util.Scanner;

public class ex_7 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int c = 0;
        int input;
        while ((input = s.nextInt()) != -1) {
            if (input == n) {
                c++;
            }
        }
        System.out.println("count: " + c);
    }
}