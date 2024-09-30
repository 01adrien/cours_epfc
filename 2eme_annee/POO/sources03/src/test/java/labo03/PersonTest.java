package labo03;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

    @Test
    public void testPersonCompare() {
        Person p1 = new Person("P1", "P1", 5, 12, 1995);
        Person p2 = new Person("P2", "P2", 9, 11, 1995);
        assertTrue(p1.compareTo(p2) < 0);
        Person p3 = new Person("P3", "P3", 1, 1, 1995);
        Person p4 = new Person("P4", "P4", 1, 1, 1995);
        assertTrue(p3.compareTo(p4) == 0);
        Person p5 = new Person("P5", "P5", 5, 1, 1995);
        Person p6 = new Person("P6", "P6", 1, 8, 1995);
        assertTrue(p5.compareTo(p6) > 0);
    }

    @Test(expected = RuntimeException.class)
    public void testPersonCompareFail() {
        Date d = new Date(31, 1, 2012);
        Person p = new Person("", "", 1, 1, 1);
        d.compareTo(p);
    }

    @Test(expected = RuntimeException.class)
    public void testCreatePersonnImpossibleDate() {
        Date d = new Date(30, 2, 2013);
        Person p = new Person("", "", 1, 1, 1);
    }

    @Test
    public void testGetAge() {
        Person p = new Person("", "", 5, 12, 2000);
        assertEquals(p.getAgeAt(new Date(4, 12, 2012)), 11);
        assertEquals(p.getAgeAt(new Date(6, 12, 2012)), 12);
    }

    @Test
    public void testPersonEqual() {
        Person p = new Person("bob", "bob", 5, 12, 2000);
        Person p1 = new Person("bob", "bob", 5, 12, 2000);
        assertTrue(p.equals(p1));
    }

}
