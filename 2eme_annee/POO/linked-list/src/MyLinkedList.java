import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<E> implements List<E> {

    private class Node<E> {
        E elem;
        Node<E> next;

        Node(E elem, Node<E> next) {
            this.elem = elem;
            this.next = next;
        }
    }

    private Node<E> head = null;
    private int size = 0;

    @Override
    public void add(int index, E e) {
        if (index == 0) {
            this.head = new Node<>(e, head);
        } else {
            Node<E> previous = getNode(index - 1);
            previous.next = new Node<>(e, previous.next);
        }
        ++size;
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
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public E get(int index) {
        return this.getNode(index).elem;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public E remove(int index) {
        E oldElem;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }
        if (index == 0) {
            oldElem = head.elem;
            head = head.next;
        } else {
            Node<E> previous = getNode(index - 1);
            Node<E> toRemove = previous.next;
            oldElem = toRemove.elem;
            previous.next = toRemove.next;
        }
        --size;
        return oldElem;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = this.getNode(index);
        E oldElem = node.elem;
        node.elem = element;
        return oldElem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            Node<E> node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public E next() {
                E element = node.elem;
                node = node.next;
                return element;
            }

        };
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        for (E e : this) {
            if (e.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
        for (E e : this) {
            if (e.equals(o)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i = 0;
        int res = -1;
        for (E e : this) {
            if (e.equals(o)) {
                res = i;
            }
            i++;
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        boolean res = false;
        if (o instanceof MyLinkedList<?>) {
            MyLinkedList<E> other = (MyLinkedList<E>) o;
            Iterator<E> it1 = this.iterator();
            Iterator<E> it2 = other.iterator();
            res = it1.hasNext() && it2.hasNext();
            while (it1.hasNext() && it2.hasNext() && res) {
                E x = it1.next();
                E y = it2.next();
                if (!x.equals(y)) {
                    res = false;
                }
            }
        }

        return res;
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Unimplemented method 'subList'");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

}
