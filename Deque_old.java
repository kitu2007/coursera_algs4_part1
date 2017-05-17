import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    
    private class Node{    
        Item item;
        Node prevNode; 
        Node nextNode;
        
        Node( ){
            item = null;
            nextNode = null; 
            prevNode = null; 
        };
        
        Node(Item _item , Node _prevNode , Node _nextNode )
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
    public Deque(){
        head = new Node();
        tail = new Node();
        size = 0;
        //      assert check();
    }
    
    
    public boolean isEmpty()           // is the deque empty?
    {
        //assert check();
        return (size <= 0);
        //wreturn (head == null);
    }
    
    public int size()                  // return the number of items on the de
    {
        return size; 
    }
    
    public void addFirst(Item item)    // insert the item at the front
    {
        Node currFirstNode = head.nextNode;
        Node tmpNode = new Node(item,head,currFirstNode);
        head.nextNode = tmpNode;
        
        if(tail.prevNode==null)
            tail.prevNode = tmpNode; 
        
        //also update the next one if there is any
        if(currFirstNode!=null)
            currFirstNode.prevNode = tmpNode;
        
        size = size+1;
    }
    
    public void addLast(Item item)     // insert the item at the end
    {
        Node currLastNode = tail.prevNode; 
        
        // the nextNode is pointing to the tail
        // the prevNode is pointing to earlier node. 
        Node tmpNode = new Node(item,currLastNode,tail);
        tail.prevNode = tmpNode;
        
        if(head.nextNode==null)
            head.nextNode = tmpNode;
        
        // update the tail.  
        if( currLastNode!=null )
            currLastNode.nextNode = tmpNode;
        
        size = size+1;
    }
    
    public Item removeFirst()          // delete and return the item at the front
    {
        if(size<=0)
            throw new NoSuchElementException("Queue underflow");
        Node currNode = head.nextNode;
        if(size==1){
            tail.prevNode = null;
            head.nextNode = null;
        }
        else{
            head.nextNode = currNode.nextNode;        
        }
        
        currNode.prevNode = null;
        currNode.nextNode = null;
        size = size -1;
        return currNode.item; 
    }
    
    public Item removeLast()           // delete and return the item at the end
    {
        if(size<=0)
            throw new NoSuchElementException("Queue underflow");
        
        Node currNode = tail.prevNode;
        
        if(size==1){
            tail.prevNode = null;
            head.nextNode = null;
        }
        else{
            tail.prevNode = currNode.prevNode;
        }
        
        currNode.prevNode = null;
        currNode.nextNode = null;
        
        size = size -1;
        return currNode.item;
        
    }
    
    
//    private String toString1() {
//        StringBuilder s = new StringBuilder();
//        for (Item item : this)
//            s.append(item + "--" +" ");
//        return s.toString();
//    } 
    
    public Iterator<Item> iterator()   // return an iterator over items in order from front to end
    {
        return new ListIterator(); //Iterator<Item>;
    }
    
    private class ListIterator implements Iterator<Item>{
        private Node current = head.nextNode;
        public boolean hasNext() {return current.nextNode!=null; };
        public void remove() {throw new UnsupportedOperationException();}
        public Item next(){
            if(!hasNext()) throw new NoSuchElementException(); 
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
            if(head != null) return false;
            if(tail !=null) return false; 
        }
        else if(size==1){
            if(head == null || tail == null) return false; 
            if( head != tail) return false; 
        }
        else{
            if(head == null || tail == null) return false;
            if(head == tail) return false;
        }
        
        return true;    
    }
  
    
    
    /**
     * A test client.
     */
    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        for(int i=0; i<3; ++i){
            StdIn.isEmpty();
            String item = StdIn.readString();
            if (!item.equals("-")) q.addLast(item);
            else if (!q.isEmpty()) StdOut.print(q.removeFirst() + " ");
        }
        
        StdOut.println("(" + q.size() + " left on queue)");
        StdOut.println(q.toString()); 
        
    }
    
    
    
}
