import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class App {
    public static void main(String[] args) throws Exception {
        List<Person> array = Arrays.asList(new Person("Y", "M", 15),
                new Person("o", "a", 46),
                new Person("D", "T", 45));

        Arrays.asList(array).stream().forEach(s -> System.out.println(s));

        Collections.sort(array, new ComparatorPerson1());

        Arrays.asList(array).stream().forEach(s -> System.out.println(s));

        Collections.sort(array, new ComparatorPerson2());

        Arrays.asList(array).stream().forEach(s -> System.out.println(s));

        System.out.println();
        System.out.println();

        TreeSet<Person> set = new TreeSet<>(new ComparatorPerson3());
        set.add(new Person("B", "B", 45));
        set.add(new Person("B", "B", 12));
        set.add(new Person("B", "B", 5));
        set.add(new Person("B", "B", 23));

        System.out.println(set);

    }
}
