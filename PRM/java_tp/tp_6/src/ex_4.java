import java.util.Scanner;

public class ex_4 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int input = s.nextInt();
        int min = input;
        while (input != 0) {
            input = s.nextInt();
            if (input < min) {
                min = input;
            }
        }
        System.out.println("min = " + min);
    }
}