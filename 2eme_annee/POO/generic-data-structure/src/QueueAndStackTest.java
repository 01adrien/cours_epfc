
import org.junit.Test;
import static org.junit.Assert.*;

public class QueueAndStackTest {

    @Test
    public void testMyQueueBasics() {
        Queue<Integer> q = new Queue<>();
        q.offer(1);
        q.offer(2);
        q.offer(3);
        // on peut mettre des int dans des XXX<Integer>
        // mais on ne peut pas passer un int à la méthode
        // assertRquals qui attend deux Objects
        assertEquals(Integer.valueOf(1), q.peek());
        assertEquals(Integer.valueOf(1), q.poll());
        assertEquals(2, q.size());
        assertEquals(Integer.valueOf(2), q.poll());
        assertEquals(Integer.valueOf(3), q.poll());
    }

    @Test
    public void testMyQueueEdge() {
        Queue<Integer> q = new Queue<>();
        assertTrue(q.isEmpty());
        assertTrue(q.poll() == null);
    }

    @Test
    public void testMyStackBasics() {
        Stack<Integer> q = new Stack<>();
        q.push(1);
        q.push(2);
        q.push(3);
        // on peut mettre des int dans des XXX<Integer>
        // mais on ne peut pas passer un int à la méthode
        // assertEquals qui attend deux Objects
        assertEquals(Integer.valueOf(3), q.peek());
        assertEquals(Integer.valueOf(3), q.pop());
        assertEquals(2, q.size());
        assertEquals(Integer.valueOf(2), q.pop());
        assertEquals(Integer.valueOf(1), q.pop());
    }

    @Test(expected = RuntimeException.class)
    public void testMyStackEdge() {
        Stack<Integer> q = new Stack<>();
        assertTrue(q.isEmpty());
        q.pop();
    }

}
