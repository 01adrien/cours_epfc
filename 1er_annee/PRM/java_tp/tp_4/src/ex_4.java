import java.util.Scanner;

public class ex_4 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = 5;
        int input = s.nextInt();
        int min = input;
        for (int i = 0; i < n - 1; i++){
            input = s.nextInt();
            if (input < min) {
                min = input;
            }
        }
        System.out.println(min);
    }
}