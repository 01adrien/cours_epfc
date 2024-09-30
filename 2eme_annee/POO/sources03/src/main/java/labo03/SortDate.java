package labo03;

public class SortDate {

    public static void swap(int a, int b, Date[] dates) {
        Date temp = dates[a];
        dates[a] = dates[b];
        dates[b] = temp;
    }

    public static void BubbleSort(Date[] dates) {
        for (int i = 1; i < dates.length; i++) {
            for (int j = 0; j < dates.length - i; j++) {
                if (dates[j].compareTo(dates[j + 1]) > 0) {
                    swap(j, j + 1, dates);
                }
            }
        }

    }

    public static void printArray(Date[] dates) {
        for (Date date : dates) {
            System.out.println(date);
        }
    }
}
