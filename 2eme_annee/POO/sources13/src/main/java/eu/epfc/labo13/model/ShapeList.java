package eu.epfc.labo13.model;

import java.util.ArrayList;
import java.util.Iterator;

public class ShapeList implements Iterable<Shape> {
    private final ArrayList<Shape> list = new ArrayList<>();

    @Override
    public Iterator<Shape> iterator() {
        return list.iterator();
    }

    public boolean add(Shape s) {
        return list.add(s);
    }

    public void clear() {
        list.clear();
    }


}
