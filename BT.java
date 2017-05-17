import java.util.NoSuchElementException;
import java.util.*;

public class BT<Item extends Comparable<Item> >{
    private Node  root;
    
    private class Node{
        Item item; 
        Node left; 
        Node right; 
        int size;
        Node(Item _item){
            left = null; right = null;
            size = 0; 
            item = _item;
        }
    }
    
    BT(){
        root = null;
    }
    
    public int rank(Item item){
        return rank(root,item);   
    }
    
    public int size(){
        return size(root);   
    };
    
    private int floor(Node x, Item item){
        
        
        
    }
    
    private Node select(Node x, int rank){
        if (x== null) return null;
        int t = size(x.left);
        if(rank< t){
            select(x,rank);
        }
        else if(rank>t){
            select(x,rank-x.left-1);
        }
        else    return x; 
        
    }

    
    private int size(Node x){
        if(x==null) return 0;     
        return size(x.left) + size(x.right) + 1;
    }; 
    
    
    private int rank(Node x, Item item){
        if(x==null) return 0; 
        int rank = 0; 
        int cmp = item.compareTo(x.item);
        if(cmp<0){
            rank = rank(x.left,item);
        }
        else if(cmp>0){
            rank = size(x.left)+1+ rank(x.right,item);  
        }
        else{ // if(cmp==0){
            rank = size(x.left);
        }
        
        return rank;    
    }; 
    
    void insert(Item item){
        root = insert(root,item); 
    }
    
    public Node insert( Node x, Item item ){
        if(item == null) 
            throw new NoSuchElementException(); 
        
        if(x == null){
            x = new Node(item);
            x.size +=1;
            return x; 
        }
        
        int cmp =  item.compareTo(x.item);
        if( cmp<0){ //newNode.item < x.item ){
            x.left = insert(x.left,item);
            x.size +=1;
            
        }
        else if (cmp>0){ // newNode.item > x.item){
            x.right = insert(x.right,item);
            x.size +=1;
            
        }
        else{
            //don't do anything for now
        }
        return x;
    }; 
    
    
    
    public Node search(Item item){
        return search(root,item); 
    }
    
    
    private Node search(Node x, Item item){
        
        if (item == null) 
            throw new NoSuchElementException();
        
        if(x==null)
            return null; 
        
        int cmp =  item.compareTo(x.item);
        if(cmp<0){
            search(x.left,item);
        }
        else if(cmp>0){
            search(x.right,item);   
        }
        else{
            return x;   
        }
        return null; 
    }
    
    
    /* public String inOrder(Node x){
     if(x==null){
     return ""; 
     }
     //String str = inOrder(x.left); 
     StdOut.print( " " + inOrder(x.left) );
     StdOut.print( " " +  x.item); 
     StdOut.print( " " + inOrder(x.right));
     return "";
     }
     */
    
    
    public String inOrder(Node x){
        String str = " " ; 
        if(x==null){
            return " ";
        }
        str += inOrder(x.left);
        str +=  x.item; 
        str += inOrder(x.right);
        return str; 
    }
    
    public String preOrder(Node x){
        String str = " " ; 
        if(x==null){
            return " ";
        }
        str +=  x.item; 
        str += inOrder(x.left);
        str += inOrder(x.right);
        return str; 
    }
    
    
    
    public static void main(String args[]){
        
        BT<Integer> bt = new BT<Integer>();
        int numItems = 10; 
        int [] arr = new int[numItems];
        for(int i=0; i<numItems; ++i){
            arr[i] = i;
        };
        StdRandom.shuffle(arr); 
        bt.insert(7); 
        bt.insert(9);
        bt.insert(6);
        bt.insert(2);
        bt.insert(11);
        
        /*for(int i=0; i<numItems; ++i){
         bt.insert(arr[i]);     
         }
         */
        
        StdOut.println(bt.preOrder(bt.root));
        int i=4; 
        StdOut.println("Rank:"+ i+ "=" + bt.rank(i));
//        bt.inOrder(bt.root);
        
    }
    
}




