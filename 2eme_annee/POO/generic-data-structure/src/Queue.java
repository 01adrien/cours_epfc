
import java.util.LinkedList;
import java.util.List;

public class Queue<E> {
    private final List<E> queue = new LinkedList<E>();

    public boolean offer(E e) {
        return this.queue.add(e);

    }

    public E poll() throws RuntimeException {
        if (this.isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        return this.queue.remove(0);
    }

    public E peek() {
        if (this.isEmpty()) {
            return null;
        }
        return this.queue.get(0);
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public int size() {
        return this.queue.size();
    }
}
