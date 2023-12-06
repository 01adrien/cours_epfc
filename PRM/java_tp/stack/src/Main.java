import pack.Stack;

public class Main {
    public static void main(String[] args) {
        Stack list = new Stack();
        list.push(45);
        list.push(4);
        System.out.println(list.pop());
        System.out.println(list.pop());
        System.out.println("**");
        System.out.println(list);
    }
}