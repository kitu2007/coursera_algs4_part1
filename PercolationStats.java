import java.lang.Math;

public class PercolationStats {
    // perform T independent computational experiments on an N-by-N grid
    private double [] vals;
    private int N; 
    private int T; 
    
    public PercolationStats(int _N, int _T){
        N = _N;
        T=_T;
        vals = new double[T];
        for(int tr=0; tr<T; ++tr){ 
            vals[tr] = 0.0; 
        }
        run();
    }
    
    private boolean run(){
        //run it T times
        for(int tr=0; tr<T; ++tr){
           // StdOut.printf( "run:%d\n",tr );   
            Percolation p = new Percolation(N);
            
            while( !p.percolates() ){
                int i = StdRandom.uniform(N)+1;
                int j = StdRandom.uniform(N)+1;
                p.open(i,j);
            }
            
            if(p.percolates()){
                double num = 0;
                for(int i=1; i<= N; ++i){
                    for(int j=1; j<=N; ++j){
                        if(p.isOpen(i,j)){
                            num +=1;
                        }
                    }
                }
                
                vals[tr] = num/(double)(N*N);
                //StdOut.printf( "percolate val:%f\n",vals[tr] );   
            }
        };
        return true;
    }
    
    
    public double mean()                     // sample mean of percolation threshold
    {
        double sum = 0; 
        for(int i=0; i<T; ++i){
            if(vals[i]==0)
                throw new IndexOutOfBoundsException() ; 
            
            sum = sum + vals[i];
        }
        sum = sum/(float) T; 
        return sum; 
    }
    
    public double stddev()                   // sample standard deviation of percolation threshold
    {
        double mean_val = mean();
        double std = 0; 
        for(int i=0; i<T; ++i){
            std = std + (vals[i]-mean_val)*(vals[i]-mean_val);
        }
        std = std/(double)(T-1);
        std = Math.sqrt(std);
        return std;
    }
    
    public double confidenceLo()             // returns lower bound of the 95% confidence interval
    {
        return ( mean()- 1.96* stddev()/ Math.sqrt( T ) );
    } 
    
    public double confidenceHi()             // returns upper bound of the 95% confidence interval
    {
        return ( mean() + 1.96* stddev()/Math.sqrt( T ) );
    }
    
    private void printVals(){
        for(int i=0; i<T; ++i){
            StdOut.printf("vals[%d] %f\n", i, vals[i] ); 
        } 
    };
    
    public static void main(String[] args)   // test client, described below
    {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats pstats = new PercolationStats(N,T); 
        //StdOut.printf("Values \n") ;  pstats.printVals(); 
        StdOut.printf("mean %f\n", pstats.mean() );   
        StdOut.printf("stddev %f\n", pstats.stddev() );
        StdOut.printf("95 confidence interval %f, %f \n", pstats.confidenceLo(), pstats.confidenceHi() );        
    }
}

