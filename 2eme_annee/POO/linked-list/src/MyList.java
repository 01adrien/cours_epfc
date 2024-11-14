
public interface MyList<E> extends Iterable<E> {
    boolean add(E e);

    void add(int index, E e);

    E get(int index);

    boolean isEmpty();

    E remove(int index);

    E set(int index, E element);

    int size();
}
