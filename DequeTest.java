import junit.framework.TestCase;
public class DequeTest extends TestCase {

  public void testDeque() {
        Deque<String> q = new Deque<String>();
        String [] str={"a","b","c"};
        for(int i=0; i<3; ++i){
            String item = str[i];
             q.addLast(item);
        }
        assertEquals("size check", 3, q.size());
        StdOut.println(q.toString());
  }

}