package pack;

public class Stack {
    Node head;
    int length;

    public Stack() {
        this.length = 0;
        this.head = null;
    }

    public void push(int value) {
        Node node = new Node(value);
        node.next = this.head;
        this.head = node;
        this.length++;
    }

    public int pop() {
        Node node = this.head;
        this.head = node.next;
        return node.data;
    }

    @Override
    public String toString() {
        Node temp = this.head;
        while (temp != null) {
            System.out.print(temp + " ");
            temp = temp.next;
        }
        return "";
    }
}
