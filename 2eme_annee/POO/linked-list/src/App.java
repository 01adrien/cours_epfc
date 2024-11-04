public class App {
    public static void main(String[] args) throws Exception {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        System.out.println(list.toString());
    }
}
