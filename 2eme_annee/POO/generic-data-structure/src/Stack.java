import java.util.LinkedList;
import java.util.List;

public class Stack<E> {
    private final List<E> stack = new LinkedList<>();

    public void push(E e) {
        this.stack.add(0, e);
    }

    public E pop() throws RuntimeException {
        if (this.stack.isEmpty()) {
            throw new RuntimeException("stack is empty..");
        }
        return this.stack.remove(0);
    }

    public E peek() {
        if (this.stack.isEmpty()) {
            return null;
        }
        return this.stack.get(0);
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public int size() {
        return this.stack.size();
    }

}
