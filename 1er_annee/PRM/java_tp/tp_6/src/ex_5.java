import java.util.Scanner;

public class ex_5 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int input = s.nextInt();
        int min = input;
        int max = input;
        while (input != 0) {
            input = s.nextInt();
            if (input < min) {
                min = input;
            }
            if (input > max) {
                max = input;
            }
        }
        System.out.println("min = " + min);
        System.out.println("max = " + max);
    }
}