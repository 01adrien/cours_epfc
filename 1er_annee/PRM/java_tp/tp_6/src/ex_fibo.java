import lib.Fibo;

public class ex_fibo {
    public static void main(String[] args) {
        Fibo fibo = new Fibo(Integer.valueOf(args[0]));
        // fibo.printToRank();
        // fibo.numLowerThan();
        fibo.isIn();
    }
}