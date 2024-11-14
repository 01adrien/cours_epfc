import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

public class App {
    public static void main(String[] args) throws Exception {
        MyLinkedList<String> l1 = new MyLinkedList<>();
        l1.add("A");
        l1.add("B");
        l1.add("C");
        l1.add("C");
        l1.add("A");

        MyLinkedList<Integer> l2 = new MyLinkedList<>();
        // ArrayList<String> l3 = new ArrayList<>();
        for (String s : l1) {
            System.out.println(s);
        }
    }
}
