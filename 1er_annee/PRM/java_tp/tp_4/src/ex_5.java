import java.util.Scanner;

public class ex_5 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int p, n, z;
        p = n = z = 0;
        int x = s.nextInt();
        int input;
        for (int i = 0; i < x; i++){
            input = s.nextInt();
            if (input > 0){
                p++;
            } else if (input < 0){
                n++;
            } else {
                z++;
            }
        }
        System.out.println("positifs : " + p);
        System.out.println("negatifs : " + n);
        System.out.println("nulles : " + z);
    }
}