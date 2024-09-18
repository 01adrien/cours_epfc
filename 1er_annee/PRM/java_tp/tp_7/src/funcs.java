
public class funcs {

    public static double avg(int a, int b) {
        return (double) (a + b) / 2;
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static boolean isNeg(int a) {
        return a < 0;
    }

    public static boolean isEven(int a) {
        return (a & 1) == 0;
    }

    public static int abs(int a) {
        return a < 0 ? -a : a;
    }

    public static int mult(int a, int b) {
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
            int x = abs(a);
            int y = abs(b);
            for (int i = 0; i < y; i++) {
                res += x;
            }
        }
        return neg ? -res : res;
    }

    public static int pow1(int a, int b) {
        int res = 1;
        boolean neg = false;
        if (isNeg(a) && !isEven(b)) {
            neg = true;
            a = abs(a);
        }
        if (b != 0) {
            if (a == 0) {
                res = 0;
            }
            for (int i = 0; i < b; i++) {
                res *= a;
            }
        }
        return neg ? -res : res;

    }

    public static int pow2(int a, int b) {
        int res = 1;
        boolean neg = false;
        if (isNeg(a) && !isEven(b)) {
            neg = true;
        }
        a = abs(a);
        if (b != 0) {
            if (a == 0) {
                res = 0;
            }
            for (int i = 0; i < b; i++) {
                res = mult(res, a);
            }
        }
        return neg ? -res : res;

    }

    public static int fact(int a) {
        return a == 1 ? a : a * fact(a - 1);
    }

    public static boolean isMult(int a, int b) {
        return a % b == 0;
    }

    public static boolean isPrime(int n) {
        for (int i = 3; i > Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int isPrimeCount(int n) {
        int count = 0;
        for (int i = n - 1; i > 2; i--) {
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }

    public static void showSquare(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("X ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {

    }
}