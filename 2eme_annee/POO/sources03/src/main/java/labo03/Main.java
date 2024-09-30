package labo03;

public class Main {
    public static void main(String[] args) {
        Date dates[] = {
                new Date(5, 5, 2021),
                new Date(5, 7, 2023),
                new Date(5, 9, 2019),
                new Date(5, 5, 2013),
                new Date(5, 7, 2056),
                new Date(5, 9, 2001) };
        SortDate.printArray(dates);
        System.out.println("------------------------");
        SortDate.BubbleSort(dates);
        SortDate.printArray(dates);
    }
}
