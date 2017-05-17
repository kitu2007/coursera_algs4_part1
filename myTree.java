
public class myTree{
    
    
    public class Node{
        float val; 
        Node lnode;
        Node rnode;
        
        public Node(float _val ){
            val = _val; 
            lnode = null;
            rnode = null;
        }
        
        public Node(float _val, Node _lnode , Node _rnode ){
            val = _val; 
            lnode = _lnode;
            rnode = _rnode;
        }
    }
    
     //
    private Node root; 
    
    public myTree(){
     root = null;   
    }
    
    public void insert(float val){
        root = insert(root,val);   
    }
    
    public Node insert(Node node, float val){
        if (node == null) return new Node(val);
        if(val == node.val) return node; 
        if( val < node.val ) {
            node.lnode = insert(node.lnode,val);   
        } 
        if( val > node.val){
            node.rnode = insert(node.rnode,val);
        }
        return node;
    }
    
    public float min2(){ 
        float min_val[] = new float[1]; 
        min_val[0] =1000;
         min_helper2(root,min_val);
        return min_val[0];
    }

    public float min(){ 
        float min_val = 1000;
        min_val = min_helper(root,min_val);
        return min_val;
    }

    public float min_helper(Node node, float min_val){
        
        if (node == null) return min_val;
        if(node.val<min_val){
            min_val = node.val;   
        }
        min_val = min_helper(node.lnode,min_val);
        min_val = min_helper(node.rnode,min_val);
        return min_val;
    }
    
     public void min_helper2(Node node, float min_val[]){
        
        if (node == null) return ;
        if(node.val<min_val[0]){
            min_val[0] = node.val;   
        }
         min_helper2(node.lnode,min_val);
         min_helper2(node.rnode,min_val);
        return ;
    }
    
    public void print(){
        print_helper(root);
    }
    
    public void print_helper(Node node){
        if(node == null) return ;
        System.out.println(node.val);
        print_helper(node.lnode);
        print_helper(node.rnode);   
    }
    
    public static void main(String[] args){
        String filename = args[0];
        //read the file. 
        In in = new In(filename);
        myTree tree = new myTree(); 
        while (!in.isEmpty()) {
            float x = (float) in.readDouble();
            float y = (float) in.readDouble();
            tree.insert(x);
            tree.insert(y);
        };
        
        tree.print();
        System.out.println("Min:" + tree.min() );
        
    }
    
}