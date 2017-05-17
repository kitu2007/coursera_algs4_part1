/*
 * 
 * Write a merge sort algorithm. 
 * 
 * 
 */

import java.util.Random;
import java.util.Comparator;

public class MyMS  {
  
    private Object [] aux;  
    public MyMS(){
       
    }
    
    // main call
    public void sort(Object[] arr){
        aux = new Object[arr.length];
        sort(arr,0,arr.length-1);
    }
    
    // internal call function
    private void sort(Object[] arr, int lo, int hi){
        
        if(lo==hi)
            return ; 
        int mid = (lo+hi)/2; 
        sort(arr,lo,mid);
        sort(arr,mid+1,hi);
        merge(arr,lo,mid,hi); 
    }
    
    private void test(int a){
     a = 10;   
        
    }
    
    public void merge(Object [] arr, int lo, int mid, int hi){
        
        // copy the arr back 
        for(int i=lo;i<=hi; ++i){
            aux[i] = arr[i];   
        }
     
        // you again failed to realize the issue here. 
        int i=lo; int j= mid+1; int k=lo; 
        for(k=lo;k<=hi; ++k ){
            if (i>mid)
                arr[k] = aux[j++];
            else if(j>hi)
                arr[k] = aux[i++];
           else if( less(aux[i],aux[j])  ){
                arr[k] = aux[i++]; 
            }
            else{
                arr[k] = aux[j++];
            }
        }
    }
    
    // helper functions.s
    boolean less(Object a, Object b){
        return  ( (Comparable)a).compareTo( (Comparable) b) < 0  ;   
    }
    
    public static void main(String args[]){
        
        MyMS ms = new MyMS();
        int N= 10;
        Integer [] arr = new Integer[N];
        for(int i=0; i<N; ++i){
            arr[i] = N-i;    
        }
        StdRandom.shuffle(arr);
        
        
        StdOut.println("Before sort");
        for(Integer i:arr){
            StdOut.print(" " + i + " " ); 
        }
        StdOut.println(); 
        
        ms.sort(arr);
        int a = 1;
        ms.test(a); 
        
        StdOut.println("After sort");
        for(Integer i:arr){
            StdOut.print(" " + i + " " ); 
        }
        
        StdOut.println(); 
        
    }
    
    
}



/*


    public void merge(Object [] arr, int lo, int mid, int hi){
        Object [] aux = new Object[arr.length]; 
        
        // copy the arr back 
        for(int i=lo;i<=hi; ++i){
            aux[i] = arr[i];   
        }
        
        // why did I have problems here?
        int i=lo; int j= mid+1; int k=lo; 
        // if one arr has reached end simply add the remaining items
        // I only look at a small portion of the whole array. I only need to worry about that portion

        // one way to deal with the problem of copying the whole array is to have one copy and copy only 
        // the required portion.. arr = modified (aux) where aux is only a small portion is not correct.  -> 
        // i had problem with loop invariant and when to stop the loop. i<mid, because don't want i++ go beyond mid... 
        // but that was incorrect
        
        // there was the memory issue.. if you change within the array it will get changed. 
        // but if you created another local array aux and then assign arr = aux.. it is only 
        // a local modification.. arr address was copied and that got changed.. not the actual arr and allocate it to arrthat won't change.
        
        // any local variable won't change until unless I return it.. 
        
        for(k=lo;k<=hi; ++k ){
            if(i>mid)
                arr[k] = aux[j++];
            if(j>hi)
                arr[k] = aux[i++];

            if( less(arr[i],arr[j])  ){
                arr[k] = aux[i++]; 
            }
            else{
                arr[k] = aux[j++];
            }
        }
/*
        // also there was a 
        while(i <= mid && j <= hi){
            if( less(arr[i],arr[j])  ){
                aux[k++] = arr[i++];
            }
            else{
                aux[k++] = arr[j++];
            }
        };

        // one might reach the upper limit first. in that case copy the rest
        while(i<=mid){
                aux[k++] = arr[i++];   
        }
        while(j<=hi){
                aux[k++] = arr[j++];   
        }
            
  * /      
        
        // assign new aux array 
        //return aux; 
        
    }

*/
