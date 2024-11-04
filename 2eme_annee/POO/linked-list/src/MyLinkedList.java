public class MyLinkedList<E> implements MyList<E> {

    private Node<E> head; // liste vide par défaut
    private int size;

    public MyLinkedList() {
        head = null;
        size = 0;
    }

    private Node<E> getNode(int index) {
        if (index >= 0 && index < this.size()) {
            Node<E> current = head;
            for (int i = 0; i < index; ++i) {
                current = current.next;
            }
            return current;
        }
        throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
    }

    @Override
    public boolean add(E element) {
        Node<E> node = new Node<E>(element, head);
        head = node;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException {
        if (index == 0) {
            add(element);
        } else if (index < 0 && index >= this.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        } else {
            Node<E> prev = getNode(index - 1);
            Node<E> next = new Node<E>(element, prev.next)
            prev.next = next;
        }
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        return getNode(index).element;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public E set(int index, E element) throws IndexOutOfBoundsException {
        Node<E> node = getNode(index);
        node.element = element;
        return node.element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        Node<E> temp = head;
        String str = "[";
        while (temp != null) {
            str += String.format(
                    "%s%s", temp.element.toString(), (temp.next == null) ? "]" : ", ");
            temp = temp.next;
        }
        return str;
    }

}
