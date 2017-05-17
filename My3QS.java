/* 
 * 
 * 
 * Three way quick sort. 
 * Kshitiz garg
 */

public class My3QS{
    
    public My3QS(){
        
    }; 
 // use comparable interface    
 // is in place     
    
    public void sort(Object [] arr){
                
        sort(arr,0,arr.length-1);
    }
    
    public void sort( Object [] arr, int lo, int hi ){
     if(lo>=hi)
         return; 
     int [] pivots = partition(arr,lo,hi);
     assert(pivots.length==2); 
     sort(arr,lo,pivots[0]);
     sort(arr,pivots[1]+1,hi);
     
    }
    
    public  int[] partition( Object [] arr, int lo, int hi ){
        int lt = lo;  int i = lo+1; int gt = hi;
        // pick the first element as pivot
        while(i<=gt){   
            // if i is less than pivot (element at lt) then swap and increement both. 
            if( compare(arr[i],arr[lo] )<0 ){ 
                exch(arr,i,lo); i++; lo++; 
            }
            // if i is greater swap with gt; (don't increment i decrement gt) swap with gtif i > i
            else if ( compare( arr[i],arr[lo] ) > 0 ){
                exch(arr,i,gt);  --gt;
            }
            else{
                ++i;   
            }
        }
        int [] tmp = new int[2];
        tmp[0] = i; tmp[1] = gt;
        return tmp; 
    }
    
    // helper function 
    private int compare(Object a1, Object a2){
        
        return ((Comparable) a1).compareTo(a2);
        
    }
    
    private void exch( Object [] arr, int i, int j){
        assert(i>=0 && i< arr.length && j>=0 && j< arr.length);
        Object tmp = arr[i]; 
        arr[i] = arr[j]; 
        arr[j] = tmp;
    }
    
     public static void main(String args[]){
        
        int size = 6;
        Integer [] arr = new Integer[size]; 
        My3QS qs = new My3QS(); //<Integer>(size);
        for(int i=0; i<size; ++i){
            arr[i] = size-i; 
        }
        
        //arr[0]=8; arr[1] = 7; arr[2] = 3; arr[3] = 5; arr[4] = 4; arr[5] = 8; 
        
        //StdRandom.shuffle(arr);
        StdOut.println("Before sorting");
        for(int i:arr){
         StdOut.print(" "+i+" ");
        }
        StdOut.println("");
        
        int k = 4; 
        StdOut.println("Select "+ k);
        //StdOut.println(qs.select(arr,k));
        
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