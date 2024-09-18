package pack;

public class Node {
    int data;
    Node next;

    public Node(int value) {
        this.data = value;
        this.next = null;
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }
}
