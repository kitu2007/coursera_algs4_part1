


public class myRBT<Item extends Comparable>{
    
    private Node root;
    private static final boolean RED = true; 
    private static final boolean BLACK = false; 
    
    private class Node{
      Item item; Node left; Node right; 
      boolean color;  int N;

      Node(Item _item){
       this.item = _item;  boolean color = BLACK;   
       left = null; right = null; N=0;
      }
        
    }
    
    void insert(Item item){
        //Node newNode = new Node(item);
       root = insert(root,item);
       root.color = BLACK;
    }
    
    private int size(Node x){
     if(x==null) return 0;   
      return x.N;
    };
    
    private boolean isRed(Node x){
        if(x==null) return false; 
        return x.color; 
    }

    private Node rotateLeft(Node x){
        
     Node z = x.right;
     x.right = z.left; 
     z.left = x;  

     z.color = x.color;
     x.color = RED; 

     // update size
     z.N = x.N;
     x.N = size(x.left) + size(x.right) + 1;  
      
     return z;     
    }
 
    private Node rotateRight(Node x){
        
     Node y = x.left;
     x.left  = y.right; 
     y.right = x;
     y.color = x.color;
     x.color = RED; 

     // update size
     y.N = x.N;
     x.N = size(x.left) + size(x.right) + 1;  
      
     return y;     
    }

    private void flipColors(Node x){
        
        x.left.color = BLACK;     
        x.right.color = BLACK; 
        x.color = RED; 
        
    }
    
    private Node insert(Node x, Item item){
        
        if(x == null){
         Node y = new Node(item);
         y.color = RED; 
         y.N = 1; 
         return y;
        }

        boolean cmp = less(item,x.item );
        if(cmp){
          x.left = insert(x.left,item);
        }
        else if( less(x.item,item ) ){
            x.right = insert(x.right,item);
        }
        else{
            x.item = item;  
        }
        
        
        // do the rotation there is a chance 
        if( isRed(x.right) && !isRed(x.left) ) x = rotateLeft(x);
        if( isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if( isRed(x.left) && isRed(x.right) ) flipColors(x);
        
        // since this rearranges color.. we do after the adjustments. 
        x.N = size(x.left) + size(x.right)+1;
        
        return x; 
    }
    
    public Node search(Item item){
        return search(root,item);
        
    }
    
    private Node search( Node x, Item item){
        if(x==null) 
            return null; 

        boolean cmp = less(item, x.item);
        if(cmp){
         x = search(x.left,item);   
        }
        else if( less(x.item, item) ){
            search(x.right,item);
        }

        return x; 
        
    }
    
    void inOrderprint(Node x){
        if(x==null)
            return; 
        inOrderprint(x.left);
        StdOut.print( " " + x.item + " " );
        inOrderprint(x.right);
        
    }
     // helper 
    private boolean less(Comparable item1, Comparable item2){
     return item1.compareTo(item2) < 0;
    }
    
    public static void main(String [] args){
        
     myRBT<Integer> rbt = new myRBT<Integer>();    
     rbt.insert(1);
     rbt.insert(10);
     rbt.insert(30);
     rbt.insert(3);
     rbt.insert(5);
     rbt.insert(11);
     
     // inorder traversal 
     rbt.inOrderprint(rbt.root);
     
    }
    
    
    
}