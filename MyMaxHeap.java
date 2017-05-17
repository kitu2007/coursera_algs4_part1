import java.lang.*;


public class MyMaxHeap<Item> { //<Item extends Comparable<Item> > {
    // first entry is not used
    // child is given by 2i % 2i+1
    // parent of i is given by i/2 
    private Item[] arr; 
    private int N; 
    
    MyMaxHeap(int initCapacity){
        arr = (Item[]) new Object[initCapacity+1];   
        N = 0; 
    }; 
    
    Item pop(){
        Item tmp = arr[1];   
        arr[1] = arr[N--];
        sink(1);
        
        arr[N+1] = null;
        return tmp;
    }
    
    void insert(Item it){
        arr[++N] = it;
        swim(N);
    }
    
// An item swims to the right location
    void swim(int ind){
        if(ind > arr.length ) throw new java.lang.IndexOutOfBoundsException();
        while(ind>1){
            // if parent is less than child exchange else quit
            boolean cmp = less(arr[ind/2],arr[ind]);
            if(!cmp) break;
            exch(arr,ind,ind/2);
            ind = ind/2; 
        }
    }
    
    int size(){
        return N;     
    }; 
    
    void sink(int ind){
        while(2*ind <= N ){
            int child = 2*ind; 
            if ( child<N && less(arr[child],arr[child+1]) )
                ++child;
            if(less(arr[child],arr[ind])) break;
            exch(arr,ind,child); 
            ind = child;
        }
    }
    
// helper functions for comparing
    
    private void exch(Item [] arr, int i, int j){
        
        Item tmp = arr[i]; 
        arr[i] = arr[j]; 
        arr[j] = tmp;
        
    }
    
    private boolean less(Item obj1, Item obj2){
        return ( (Comparable<Item>) obj1).compareTo(obj2) < 0;
    } 
    
    
    
    public static void main(String args[]){
        
        int size = 10;
        MyMaxHeap<Integer> hp = new MyMaxHeap<Integer>(size);
        for(int i=0; i<size; ++i){
            hp.insert(i);
//            hp.insert("A");
//            hp.insert("D");
//            hp.insert("B");            
//      
        }
        
        StdOut.println("Print Heap");
        while(hp.size()>0){
        //for(int i=0; i<hp.N+1; ++i){
            //Integer i = (Integer)hp.arr[1];
            StdOut.print(" " + hp.pop() + " " );    
          //  StdOut.println("");    
        } 
        //}
        
    }
    
}