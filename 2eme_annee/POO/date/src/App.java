
public class App {

    public static void main(String[] args) throws Exception {
        Integer[] arr = { 1, 2, 3, 4, 5, 6, 7 };
        for (int i = 0; i < 5; i++) {
            Gen.printArray(arr);
            Gen.shuffle(arr);
            Gen.printArray(arr);
            System.out.println("----------------------------");
        }

    }
}
