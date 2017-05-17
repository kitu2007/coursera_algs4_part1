 import java.util.Random;
import java.util.Comparator;

public class MyQS
{
    // the object A doesn't really belong to QS.. we can use this
    // interface for anything else 
    private Comparator comparator; 
    
    public void sort (Object [] A) {
        
        sort(A,0,A.length-1); 
    }
    
    public void sort (Object [] A, Comparator comp) {
        this.comparator = comp;
        sort(A,0,A.length-1); 
    }
    
    public void sort (Object [] A, int lo, int hi) {
        if(lo>=hi) return;
        
        // k is the partitinong element that is in place
        int k = partition(A, lo,hi);
        // sort is in place so just sort left array then sort right array.
        if (true){
        sort(A,lo,k-1);
        sort(A,k+1,hi);
        }
    }
    
    public Object select(Object [] A, int r){

        // make sure k is within 1 and N.
//shuffle the array
        int lo = 0; int hi= A.length-1; 

        while(hi>lo){
        int k = partition(A,lo,hi);    
        if (r<k) hi = k-1;
        else if (r>k) lo = k+1;
        else return A[k];
        }
        return A[lo];
    }
    
    // method returns the partitioning element final index 
    // the partitioning element is put in place. 
    private int partition (Object [] A, int lo, int hi){
        //check if lo and hi are within the limits. 
        
        if(lo<0 || hi > A.length-1) throw new java.lang.IndexOutOfBoundsException();
        int i = lo, j = hi+1; 
        while(true){
            // increase i till you i is lower than pivot
            while( less(A[++i],A[lo]) && i < hi ) ;
            // decrease j till you j is higher than pivot
            while( less(A[lo],A[--j]) && j > lo ) ;
            if( i >= j ) break;
            exch( A, i, j ); 
        }
        exch(A,lo,j);
        return j; 
    } 
    
    
    private void exch(Object [] arr, int i, int j){
        
        Object tmp = arr[i]; 
        arr[i] = arr[j]; 
        arr[j] = tmp;
        
    }
    
    private boolean less(Object obj1, Object obj2){
        if(this.comparator==null){
        return ((Comparable) obj1).compareTo((Comparable)obj2) < 0;
        }
        else{
            return comparator.compare(obj1, obj2) < 0; 
        }
    }
    
    public static void main(String args[]){
        
        int size = 6;
        Integer [] arr = new Integer[size]; 
        MyQS qs = new MyQS(); //<Integer>(size);
        //for(int i=0; i<size; ++i){
          //  arr[i] = size-i; 
        //}
        arr[0]=8; arr[1] = 7; arr[2] = 3; arr[3] = 5; arr[4] = 4; arr[5] = 8; 
        
        //StdRandom.shuffle(arr);
        StdOut.println("Before sorting");
        for(int i:arr){
         StdOut.print(" "+i+" ");
        }
        StdOut.println("");
        
        int k = 4; 
        StdOut.println("Select "+ k);
        StdOut.println(qs.select(arr,k));
        
        if (false) {
        qs.sort(arr);

        StdOut.println("After sorting");

        for(int i:arr){
         StdOut.print(" "+i+" ");
        }
         StdOut.println("");
        }
    }
}