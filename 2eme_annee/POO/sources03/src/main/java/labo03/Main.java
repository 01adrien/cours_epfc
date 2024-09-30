package labo03;

public class Main {
    public static void main(String[] args) {
        Person p = new Person("", "", 5, 12, 2000);
        System.out.println(p.getAgeAt(new Date(4, 12, 2012)));
        System.out.println(p.getAgeAt(new Date(6, 12, 2012)));
    }
}
