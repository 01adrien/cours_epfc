package ex01;

public class Program {
    public static void main(String[] args) {
        Person p1 = new Person(
                "bob", "dupont", new Date(5, 11, 2023));
        Person p2 = new Person(p1);

        p2.setDateOfBirth(new Date(9, 9, 1989));
        System.out.println(p1.getAge());
        System.out.println(p2.getAge());

    }
}
