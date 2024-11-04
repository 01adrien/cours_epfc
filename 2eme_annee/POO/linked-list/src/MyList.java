public interface MyList<E> {
    boolean add(E element);

    void add(int index, E element) throws IndexOutOfBoundsException;

    E get(int index) throws IndexOutOfBoundsException;

    boolean isEmpty();

    E remove(int index) throws IndexOutOfBoundsException;

    E set(int index, E element) throws IndexOutOfBoundsException;

    int size();

}
