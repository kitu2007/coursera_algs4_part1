import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    
    private class Node {    
        private Item item;
        private Node prevNode; 
        private Node nextNode;
        
        public Node( ) {
            item = null;
            nextNode = null; 
            prevNode = null; 
        };
        
        public Node(Item _item , Node _prevNode , Node _nextNode )
        {
            item = _item;
            nextNode = _nextNode; 
            prevNode = _prevNode; 
        }
    };
    
    private Node head;
    private Node tail;
    private int size;
    
    
    
    // construct an empty deque
    public Deque() {
        head = null; //new Node();
        tail = null; //new Node();
        size = 0;
        //      assert check();
    }
    
    
    public boolean isEmpty()           // is the deque empty?
    {
        //assert check();
        return (size <= 0);
       
    }
    
    public int size()                  // return the number of items on the de
    {
        return size; 
    }
    
    public void addFirst(Item item)    // insert the item at the front
    {
        if (size == 0) { 
            Node tmpNode = new Node(item, null, null);
            head = tmpNode;
            tail = tmpNode;
        }
        else {
            Node tmpNode = new Node(item, null, null);
            tmpNode.nextNode = head;
            head.prevNode = tmpNode; 
            head = tmpNode; 
        }
        size = size + 1;
    }
    
    public void addLast(Item item)     // insert the item at the end
    {
        if (size == 0) { 
            Node tmpNode = new Node(item, null, null);
            head = tmpNode;
            tail = tmpNode;
        }
        else {
            Node tmpNode = new Node(item, null, null);
            tmpNode.prevNode = tail;
            tail.nextNode = tmpNode;
            tail = tmpNode;
        }
        size = size+1;
    }
    
    public Item removeFirst()          // delete and return the item at the front
    {
        if (size <= 0)
            throw new NoSuchElementException("Queue underflow");
        Item it  = head.item;
        head = head.nextNode;
        size = size -1;
        if (size == 0) {
            head = null;
            tail = null;
        }
        return it; 
    }
    
    public Item removeLast()           // delete and return the item at the end
    {
        if (size <= 0)
            throw new NoSuchElementException("Queue underflow");
        
        Node currNode = tail;
        
        if (size == 1) {
            tail  = null;
            head = null;
        }
        else {
            tail  = tail.prevNode;
        }
        
        
        size = size -1;
        return currNode.item;
        
    }
    
    
//    private String toString1() {
//        StringBuilder s = new StringBuilder();
//        for (Item item : this)
//            s.append(item + "--" +" ");
//        return s.toString();
//    } 
    
 // return an iterator over items in order from front to end
    public Iterator<Item> iterator()   
    {
        return new ListIterator(); //Iterator<Item>;
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = head;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if(current == null) throw new NoSuchElementException(); 
            Item item = current.item;
            current = current.nextNode;
            return item;
        }
    }
    
    /** 
     *  Check basic things
     */
    private boolean check(){
        
        if(size ==0) { 
            if (head != null) return false;
            if (tail !=null) return false; 
        }
        else if(size == 1) {
            if (head == null || tail == null) return false; 
            if ( head != tail) return false; 
        }
        else {
            if (head == null || tail == null) return false;
            if (head == tail) return false;
        }
        
        return true;    
    }
    
    
    
    /**
     * A test client.
     */
    public static void main(String[] args) {
        Deque<Integer> q = new Deque<Integer>();
      
        for(int i=0; i<10; ++i){
          q.addFirst( i );
          q.addLast( i );  
        }; 
        
        /* if (false) {
        while ( !StdIn.isEmpty() ) {
            String item = StdIn.readString();
            if ( !item.equals("-") ) q.addFirst( item );
            else if ( !q.isEmpty() ) StdOut.print( q.removeFirst() + " " );
        }
        }
        */ 
        Iterator itr1 = q.iterator(); 
        Iterator itr2 = q.iterator();
        StdOut.println( itr1.next() ); 
        StdOut.println( itr1.next() );
        StdOut.println( itr1.next() ); 
        StdOut.println( itr2.next() );
        
        for(Integer i:q)
            StdOut.print(" " + i + " ");
        StdOut.println("done");
            
        StdOut.println("(" + q.size() + " left on queue)");
        StdOut.println(q.toString()); 
        
    }
    
    
    
}
