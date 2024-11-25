package ex02;

import labo14.ex01.Person;

public class Program {
    public static void main(String[] args) {
        MyPersonLinkedList l1 = new MyPersonLinkedList();
        System.out.println("------------------------------------");
        l1.add(new Person("bob", "dupont", new Date(5, 5, 1989)));
        l1.add(new Person("henry", "james", new Date(2, 5, 1889)));
        System.out.println(l1);
        System.out.println("------------------------------------");
        MyPersonLinkedList l2 = l1.copy();
        System.out.println(l2);
        System.out.println("------------------------------------");
        l2.get(0).getDateOfBirth().setYear(1959);
        l2.get(1).getDateOfBirth().setYear(1000);
        System.out.println(l2);
        System.out.println("------------------------------------");
        System.out.println(l1);
    }
}
