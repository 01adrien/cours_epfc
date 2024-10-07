
import org.junit.Test;
import static org.junit.Assert.*;

public class QueueAndStackTest {
    
    @Test
    public void testMyQueueBasics()
    {
        Queue<Integer> q = new Queue<>();
        q.offer(1);
        q.offer(2);
        q.offer(3);
        //on peut mettre des int dans des XXX<Integer>
        //mais on ne peut pas passer un int à la méthode
        //assertRquals qui attend deux Objects
        assertEquals(Integer.valueOf(1),q.peek());
        assertEquals(Integer.valueOf(1),q.poll());
        assertEquals(2,q.size());
        assertEquals(Integer.valueOf(2),q.poll());
        assertEquals(Integer.valueOf(3),q.poll());
    }
    
    @Test
    public void testMyQueueEdge()
    {
        Queue<Integer> q = new Queue<>();
        assertTrue(q.isEmpty());
        assertTrue(q.poll() == null);
    }

        
    }
    