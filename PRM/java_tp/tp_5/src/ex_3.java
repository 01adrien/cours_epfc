import java.util.Scanner;

public class ex_3 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int input;
        int last = -1;
        do {
            input = s.nextInt();
            if (input != -1) {
                last = input;
            }
        } while (input != -1);
        if (last != -1) {
            System.out.println("dernier " + last);
        }
    }

}