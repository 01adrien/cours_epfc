import java.util.Scanner;

public class ex_8 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int last = 0;
        int input = s.nextInt();
        int first = input;
        if (first == -1) {
            System.out.println("la suite est vide");
        } else {
            while (input != -1) {
                last = input;
                input = s.nextInt();
            }
            if (first == last) {
                System.out.println("premier == dernier");
            } else if (first < last) {
                System.out.println("premier < dernier");
            } else {
                System.out.println("premier > dernier");
            }
        }
    }
}
