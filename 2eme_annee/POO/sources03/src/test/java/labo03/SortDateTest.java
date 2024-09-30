package labo03;

import static org.junit.Assert.*;

import org.junit.Test;

public class SortDateTest {

    @Test
    public void testSwapDates() {
        Date dates[] = {
                new Date(5, 5, 2024),
                new Date(5, 7, 2024),
                new Date(5, 9, 2024) };
        assertEquals(dates[0].getMonth(), 5);
        SortDate.swap(0, 2, dates);
        assertEquals(dates[0].getMonth(), 9);
    }

    @Test
    public void testBubbleSortDate() {
        Date dates[] = {
                new Date(5, 5, 2021),
                new Date(5, 7, 2023),
                new Date(5, 9, 2019),
                new Date(5, 5, 2013),
                new Date(5, 7, 2056),
                new Date(5, 9, 2001) };
        SortDate.BubbleSort(dates);
        for (int i = 0; i < dates.length - 1; i++) {
            assertTrue(dates[i].compareTo(dates[i + 1]) <= 0);
        }

    }

}
