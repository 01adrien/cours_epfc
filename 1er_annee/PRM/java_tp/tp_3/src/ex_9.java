import java.util.Scanner;

public class ex_9 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int input = s.nextInt();
        int last = 0;
        while (input != -1) {
            if (last > input) {
                System.out.println("suite non croissante");
                return;
            }
            last = input;
            input = s.nextInt();
        }
        System.out.println("suite croissante");
    }
}
