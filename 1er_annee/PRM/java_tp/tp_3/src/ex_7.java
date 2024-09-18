import java.util.Scanner;

public class ex_7 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int input;
        int last = -1;
        while ((input = s.nextInt()) != -1) {
            last = input;
        }
        System.out.println("le dernier est: " + (last == -1 ? "no input" : last));
    }
}
