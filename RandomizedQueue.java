import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array; 
    private int N;
    //private int arraySize;
    private int tail; 
    
    @SuppressWarnings("unchecked")
    public RandomizedQueue()           // construct an empty randomized queue
    {
        array = (Item[]) new Object[2]; 
        N = 0;
        tail = 0; 
    }
    
    /* 
     * private toArray(Object[] arr){
     Item [] arr2 = new Item[arr.length];
     for(int i=0; i<arr.length; ++i)
     arr2[i] = arr[i]; 
     }; 
     */ 
    
    public boolean isEmpty(){
        return (N<=0); 
    }        
    
    public int size()                  // return the number of items on the queue
    {
        return N; 
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int newSize){
        
        //create a new array 
        Item tmpArray[] = (Item[]) new Object[newSize];
        for(int i=0; i<array.length; ++i){
            tmpArray[i]=array[i];  
        }
        array = tmpArray;
    }; 
    
    @SuppressWarnings("unchecked")
    private void reduce(int newSize){
        //create a new array 
        Item tmpArray[] = (Item[]) new Object[newSize];
        int counter = 0;
        for(int i=0; i<array.length; ++i){
            if(array[i]!=null){
                counter = counter + 1;
                assert(counter<=newSize);
                tmpArray[counter]=array[i]; 
            }
        };
        array = tmpArray;
    }; 
    
    // add the item to the tail, increment tail
    public void enqueue(Item item)     // add the item{
    {
        if(item == null) 
            throw new java.lang.NullPointerException("Item set to Null");
        
        if(size() == array.length)
            resize(2*array.length);
        
        while(array[tail]!=null){
            tail = tail + 1;
            if(tail > array.length)
                tail = 0;
        }
        
        array[tail] = item;
        tail = tail + 1;
        N = N + 1;
    }
    
    
    public Item dequeue()              // delete and return a random item
    {  
        if(N == 0) 
            throw new NoSuchElementException("trying to dequeing from empty queue");
        
        int x = StdRandom.uniform(array.length);
        //if x is null then get another rand number
        while(array[x]==null)
            x = StdRandom.uniform(array.length);
        
        Item item = array[x]; 
        array[x] = null; 
        
        //resize array
        N = N-1; 
        if( N <= array.length/4 ) 
            reduce(array.length/2);
        
        return item; 
    }
    
    public Item sample()               // return (but do not delete) a random item
    {
        if(N <= 0) 
            throw new NoSuchElementException("cannot sample from empty queue");
        
        int x = StdRandom.uniform(array.length); 
        while(array[x]==null){
            x = StdRandom.uniform(array.length);
        }
        return array[x]; 
    }
    
    public Iterator<Item> iterator()   // return an independent iterator over items in random order
    {
        return new RandomArrayIterator(); 
    }
    
    // the code doesn't look pretty that is the biggest problem 
    // I am having a hard time understanding my own code. because they are no comment.
    // no reason why I did, what I did. 
    private class RandomArrayIterator implements Iterator<Item>{    
        // create a randArr shuffle it
        int randArr[] = new int[array.length];
        int current = 0;
        
        public RandomArrayIterator(){
            for(int i=0; i<array.length; i=i+1){
                randArr[i] = i; 
            }
            StdRandom.shuffle(randArr);  
        }
        
        // now has next just gives a random index
        public boolean hasNext(){
            Item item = null;
            if(current < array.length)
                item = array[randArr[current]];
            // why arraySize -1 here.
            while( (item == null) && current<array.length-1){
                current +=1;
                item = array[randArr[current]];
            }
            
            if(item != null)
                return true;
            else
                return false; 
        };
        
        // have no idea why I am getting weird effect here. 
        // it starts as null but somehow null becomes "null" when the condiition 
        // is checked
        private boolean hasNext1(){
            Item item1 = null;
            
            while( (item1 == null) && current<array.length){
                
                item1 = array[randArr[current++]];
            }
            if(item1 != null)
                return true;
            else
                return false; 
        };
        
        public void remove() {throw new UnsupportedOperationException();}
        
        public Item next(){
            if(!hasNext())
                throw new NoSuchElementException("no more elements left");
            
            return array[randArr[current++]];
        };
    };
    
    private String toString1() {
        StringBuilder s = new StringBuilder();
        for (int i=0; i<array.length; ++i) //Item item : this)
            s.append(array[i] + "--" +" ");
        return s.toString();
    } 
    
    
    /* test client */
    /**
     * A test client.
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        for(Integer i=0; i<16; ++i){
            //StdIn.isEmpty();
            Integer item = i; //%tdIn.readString();
            q.enqueue(item);
            //if (!item.equals("-")) q.enqueue(item);
            //else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println( q.toString1() );
        
        StdOut.println("deleted");
        for(Integer i=0; i<7; ++i){
            if(!q.isEmpty()){
                Integer item = q.dequeue();
                StdOut.print(item + " ");
            }
        }
        
        StdOut.println("");
        StdOut.println("new string done");
        
        StdOut.println( q.toString1() );
        
        Iterator<Integer> iter1 = q.iterator();
        Iterator<Integer> iter2 = q.iterator();
        
        for(; iter1.hasNext(); ){
            StdOut.print(iter1.next() + " " );            
        }
        StdOut.println();
        StdOut.println( q.toString1() );
        for(; iter2.hasNext(); ){
            StdOut.print(iter2.next() + " ");            
        }
        StdOut.println();
        StdOut.println( q.toString1() );
        StdOut.println( "(" + q.size() + " left on queue)" );
        
        //for (String s: q)
        //  StdOut.println(s);
        
    }
    
}