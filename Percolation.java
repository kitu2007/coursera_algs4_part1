/*----------------------------------------------------------------
 *  Author:        Kshitiz Garg
 *  Written:       9/29/2012
 *  Last updated:  9/30/2012
 *
 *  Compilation:   javac Precolation.java
 *  Execution:     java Percolation
 *  
 *  Prints "Test if a system is percolating, using union-find ".
 *
 *  % java Percolation
 *  NA
 *
 *----------------------------------------------------------------*/


public class Percolation {
    
    private WeightedQuickUnionUF wUF;
    // I need an active array, since I need to keep track of opened site
    // open site is different from connecting 2 sites.
    private int [] openArray;  // this helps to keep track of open sites 
    // top virtual site, any open site in top row is connected to this site
    private int virtualNodeIndexTop; 
     // bottom virtual site. any open site in botton row is connected to this site
    private int virtualNodeIndexBottom;
    // the dimension of the array (assumed square)
    private int N; 
    
    private int numSitesOpen; 
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int _N) {
        // set the dimension of the grid
        N = _N;
        // virtual nodes
        virtualNodeIndexTop = N*N; 
        virtualNodeIndexBottom = N*N+1;
        
        // the union find does the job of connecting nodes. 
        wUF = new WeightedQuickUnionUF(N*N+2);
        
        // create an N*N array of open sites 
        openArray = new int[N*N]; 
        for (int i = 0; i < N*N; ++i) {
            openArray[i] = 0; 
        }
 
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        
        if (i < 1 || j > N) throw 
            new IndexOutOfBoundsException("row index i out of bounds"); 
       // if already open do nothing
        if (isOpen(i, j)) {
            return; 
        }
        
        // else get the linear index of the array element and set it to 1
        int index = getIndex(i, j); 
        openArray[index] = 1; 
        
        // connect the node to virtual nodes if element is in top or bottom row
        if (i == 1)
            wUF.union(index, virtualNodeIndexTop); 
        
        if (i == N)
            wUF.union(index, virtualNodeIndexBottom);
        
        // connects the current node with bottom and top rows if open.
         if (i > 1 && isOpen(i-1, j))
                wUF.union(index, getIndex(i-1, j)); 
         if (i < N && isOpen(i+1, j))
                wUF.union(index, getIndex(i+1, j));
        
        // connects the current node with left and right column if open.
         if ( j > 1 && isOpen(i, j-1) )
                wUF.union(index, getIndex(i, j-1));
         if ( j < N && isOpen(i, j+1) ) 
                wUF.union(index, getIndex(i, j+1));
         
    }
    
    //opening a site is different from connecting a site
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)    
    {
        if (i<=0 || j>N) { 
             String outString = String.format("row index i:%d j:%d out of bounds",i,j);
            throw new IndexOutOfBoundsException( outString );
        }
        //return wUF.connected(i,j); 
        int index = getIndex(i, j);
        return (openArray[index]>0); 
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j)    
    {
        if(i<=0 || j>N)   throw new IndexOutOfBoundsException("row index i out of bounds");
        int index = getIndex(i,j);
        return wUF.connected(index,virtualNodeIndexTop);   
    }; 
    
    // does the system percolate?
    public boolean percolates()            
    {
        return wUF.connected(virtualNodeIndexTop,virtualNodeIndexBottom);   
    }
    
    private int getIndex(int i, int j){
        if(i<1 || j < 1 || i>N || j>N ) {
             String outString = String.format("row index i:%d j:%d out of bounds",i,j);
             throw new IndexOutOfBoundsException( outString );
        }
        
        int index = (i-1)*N+j-1; 
        if(index > (N*N-1) || index < 0) {
            String outString = String.format("Index Value:%d Out of range\n",index);
            throw new IndexOutOfBoundsException(outString) ; 
        }
        
        return index;
    }

    private int getNumSitesOpen(){
        int num = 0; 
        
        for(int i=0; i< N*N; ++i){
            if(openArray[i] == 1){
             num +=1; 
            }
        }
        return num; 
    }; 
        
    // the program to check what is going on
    public static void main(String [] args)
    {
        int N = StdIn.readInt();
        int iter = StdIn.readInt();
        
        Percolation per = new Percolation(N);
        StdOut.printf("Union Find Before \n");   

        if (false){
        for(int i=0; i<N*N+2; ++i)
            StdOut.printf( "i:%d parent:%d\n",i, per.wUF.find(i) );   

        StdOut.printf("Open Array Before \n");
        for(int i=0; i<N*N; ++i)
            StdOut.printf( "i:%d parent:%d\n",i, per.openArray[i] );
        } 
        
        for(int k=0; k<iter; ++k){
            int i = StdRandom.uniform(N)+1;
            int j = StdRandom.uniform(N)+1;
           // StdOut.printf("i:%d j:%d\n",i,j);
            per.open(i,j);
            
            if(per.percolates()){
                StdOut.printf("Percolates:%d\n",k);
                break;
            }
            //else
              //  StdOut.printf("Does not Percolates:%d\n",k);
        }

        if(!per.percolates())
             StdOut.printf("Does not Percolates\n");
            
         if (false){
        // is anything happening here.
        StdOut.printf("Union Find After \n");
        for(int i=0; i<N*N+2; ++i)
            StdOut.printf("i:%d parent:%d\n",i, per.wUF.find(i) );   

        StdOut.printf("Open Array After \n");
        for(int i=0; i<N*N; ++i)
            StdOut.printf("i:%d parent:%d\n",i , per.openArray[i] );
         }
        
    }
    
}