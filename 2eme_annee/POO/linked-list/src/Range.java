import java.util.Iterator;

public class Range implements Iterable<Integer> {

    private int max;
    private int min;

    Range(int max) {
        this.max = max;
        this.min = 0;
    }

    Range(int min, int max) {
        this.max = max;
        this.min = min;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            int current = min;

            @Override
            public boolean hasNext() {
                return current != max;
            }

            @Override
            public Integer next() {
                int e = current;
                current++;
                return e;
            }

        };
    }

}
