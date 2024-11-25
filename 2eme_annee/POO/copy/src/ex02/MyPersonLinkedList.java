package ex02;

import java.util.*;

import labo14.ex01.Person;

public class MyPersonLinkedList implements List<Person> {

    private class PersonNode {
        private Person elem;
        private PersonNode next;

        private PersonNode(PersonNode n) {
            this(new Person(n.elem), n.next == null ? null : new PersonNode(n.next));

        }

        private PersonNode(Person elem, PersonNode next) {
            this.elem = elem;
            this.next = next;
        }
    }

    /*
     * 
     * 
     * public MyPersonLinkedList(int size, PersonNode head) {
     * 
     * }
     * 
     * public MyPersonLinkedList(MyPersonLinkedList l) {
     * 
     * }
     * 
     */
    public MyPersonLinkedList copy() {
        MyPersonLinkedList newList = new MyPersonLinkedList();
        newList.size = this.size;
        newList.head = new PersonNode(this.head);
        return newList;
    }

    private PersonNode head = null; // liste vide par défaut
    private int size = 0;

    @Override
    public void add(int index, Person e) {
        if (index == 0) {
            this.head = new PersonNode(e, head);
        } else {
            PersonNode prec = getNode(index - 1);
            prec.next = new PersonNode(e, prec.next);
        }
        ++size;
    }

    private PersonNode getNode(int index) {
        if (index >= 0 && index < this.size()) {
            PersonNode current = head;
            for (int i = 0; i < index; ++i) {
                current = current.next;
            }
            return current;
        }
        throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
    }

    @Override
    public boolean add(Person e) {
        add(size, e);
        return true;
    }

    @Override
    public Person get(int index) {
        return this.getNode(index).elem;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    // attention à ne faire qu'un seul parcours
    @Override
    public Person remove(int index) {
        Person old;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }
        if (index == 0) {
            old = head.elem;
            head = head.next;
        } else {
            PersonNode prec = getNode(index - 1);
            old = prec.next.elem;
            prec.next = prec.next.next;
        }
        --size;
        return old;
    }

    @Override
    public Person set(int index, Person element) {
        PersonNode node = this.getNode(index);
        Person oldElement = node.elem;
        node.elem = element;
        return oldElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Person> iterator() {
        return new Iterator<Person>() {
            PersonNode current = MyPersonLinkedList.this.head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Person next() {
                if (current == null)
                    throw new NoSuchElementException();
                Person element = current.elem;
                current = current.next;
                return element;
            }
        };
    }

    // Peut être remplacé par Objects.equals()
    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

    // attention à ne faire qu'un seul parcours
    @Override
    public int indexOf(Object o) {
        int position = 0;
        for (Person element : this) {
            if (equals(element, o)) {
                return position;
            }
            ++position;
        }
        return -1;
    }

    // attention à ne faire qu'un seul parcours
    @Override
    public int lastIndexOf(Object o) {
        int currentPosition = 0;
        int lastIndex = -1;
        for (Person element : this) {
            if (equals(element, o))
                lastIndex = currentPosition;
            ++currentPosition;
        }
        return lastIndex;
    }

    // attention à ne faire qu'un seul parcours
    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) != -1;
    }

    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    // attention à ne faire qu'un seul parcours
    @Override
    public boolean equals(Object o) {
        if (o instanceof List) {
            List otherList = (List) o;
            if (size() != otherList.size()) {
                return false;
            }
            Iterator i1 = this.iterator();
            Iterator i2 = otherList.iterator();
            while (i1.hasNext()) {
                if (!equals(i1.next(), i2.next()))
                    return false;
            }
            return true;
        }
        return false;
    }

    // attention à ne faire qu'un seul parcours
    @Override
    public boolean remove(Object o) {
        if (head == null) {
            return false;
        }
        if (equals(head.elem, o)) {
            head = head.next;
            --size;
            return true;
        }
        PersonNode previousNode = head;
        while (previousNode.next != null && !equals(previousNode.next.elem, o)) {
            previousNode = previousNode.next;
        }
        if (previousNode.next == null) {
            return false;
        }
        previousNode.next = previousNode.next.next;
        --size;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Person person : this) {
            builder.append(person + ", ");
        }
        if (this.size() > 0) {
            builder.delete(builder.length() - 3, builder.length());
        }
        builder.append("]");
        return builder.toString();
    }

    // Les méthodes suivantes restent non implémentées

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public ListIterator<Person> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public ListIterator<Person> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public List<Person> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

}
