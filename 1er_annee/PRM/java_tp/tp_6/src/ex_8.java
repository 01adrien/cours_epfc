import java.util.Scanner;

public class ex_8 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int input;
        int pos = 1;
        while ((input = s.nextInt()) != n) {
            pos++;
        }
        System.out.println("pos: " + pos);
    }
}