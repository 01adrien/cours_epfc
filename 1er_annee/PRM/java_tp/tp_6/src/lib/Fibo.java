package lib;

public class Fibo {

    int n;

    public Fibo(int n) {
        this.n = n;
    }

    public void printToRank() {
        int a = 0;
        int b = 1;
        int c;
        for (int i = 0; i <= this.n; i++) {
            c = a + b;
            System.out.println(a);
            b = a;
            a = c;
        }
    }

    public void numLowerThan() {
        int a = 0;
        int b = 1;
        int c = 0;
        int inf = c;
        for (;;) {
            c = a + b;
            if (c > this.n) {
                break;
            }
            inf = c;
            b = a;
            a = c;
        }
        System.out.println(inf);
    }

    public void isIn() {
        int a = 0;
        int b = 1;
        int c = 0;
        boolean in = false;
        while (a < this.n) {
            c = a + b;
            if (c == this.n) {
                in = true;
            }
            b = a;
            a = c;
        }
        System.out.println(in ? "IN" : "OUT");
    }
}