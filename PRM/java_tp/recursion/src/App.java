import eu.epfc.prm2.Array;

public class App {

    public static boolean isOdd(int n) {
        return n % 2 != 0;
    }

    public static boolean isEven(int n) {
        return !isOdd(n);
    }

    // EX 2
    public static int fact(int n) {
        return n > 1 ? n * fact(n - 1) : n;
    }

    // EX 3
    public static int fib(int n) {
        return n >= 2 ? fib(n - 2) + fib(n - 1) : n;
    }

    // EX 4
    public static int pow(int n, int exp) {
        if (exp == 1) {
            return n;
        } else if (exp == 0) {
            return 1;
        }
        return n * pow(n, exp - 1);
    }

    // EX 5
    public static int pow2(int n, int exp) {
        return isEven(n) ? pow(n * n, exp / 2) : pow(n * n, (exp - 1) / 2) * n;
    }

    // EX 6
    public static int ex6(int n) {
        return n >= 10 ? 1 + ex6(n / 10) : 1;
    }

    // EX 7
    public static int ex7(int n) {
        return n < 10 ? n : n % 10 + ex7(n / 10);
    }

    // EX 8
    public static int ex8(Array<Integer> arr) {
        if (arr.size() == 1) {
            return arr.get(0);
        }
        int last = arr.get(arr.size() - 1);
        arr.reduceTo(arr.size() - 1);
        return last + ex8(arr);

    }

    public static void main(String[] args) throws Exception {
        System.out.println(pow2(5, 5));
    }

}
