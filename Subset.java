//import java.io.*; 
import java.util.Iterator;
import java.util.*;

public class Subset{
    
    public static void main(String args[]){
        
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()){
            String str = StdIn.readString();     
            q.enqueue(str);
        }
        
         int k =0;
       // StdOut.println("args[0]" + args[0]);
        try{
             k = Integer.parseInt(args[0]);
        }
        catch(Exception e){
            throw new RuntimeException(e);   
        }

        Iterator<String> it = q.iterator(); 
        for(int i=0; i<k; ++i){
         StdOut.println(it.next());   
        }
    }
    
    
} 
