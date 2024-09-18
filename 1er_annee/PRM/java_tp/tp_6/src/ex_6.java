
public class ex_6 {

    public static void main(String[] args) {
        int a = Integer.valueOf(args[0]);
        int b = Integer.valueOf(args[1]);
        boolean neg = false;
        boolean zero = false;
        int res = 0;
        if (a == 0 || b == 0) {
            zero = true;
        }
        if (a < 0 && b > 0 || a > 0 && b < 0) {
            neg = true;
        }
        if (!zero) {
            for (int i = 0; i < b; i++) {
                res += a;
            }
            if (neg) {
                res = -res;
            }
        }
        System.out.println(res);
    }
}