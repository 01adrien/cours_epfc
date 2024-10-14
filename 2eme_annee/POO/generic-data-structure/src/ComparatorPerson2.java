import java.util.Comparator;

public class ComparatorPerson2 implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        // TODO Auto-generated method stub
        return o1.lastName.compareToIgnoreCase(o2.lastName);
    }

}
