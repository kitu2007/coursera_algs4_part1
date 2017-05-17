/** 
 * Board class for playing 8-puzzle game
 * 
 * K G 
 */

import java.util.NoSuchElementException;

public  class Board {

    private final int [][] mBlocks;
    private final int dim;
    
    // construct a board from an N-by-N array of blocks    
    public Board(int[][] blocks)
    {
        dim = blocks.length;
        mBlocks = new int[dim][dim];
        for (int i = 0; i < dim; ++i) {
            for (int j = 0; j < dim; ++j) {
                this.mBlocks[i][j] = blocks[i][j];
            }
        };
    }

    
    // this return the index value where the item is found
    // index is then used to get the rol and the col
    private int findItem(int itemVal) {
        int index = -1;
        for (int i = 0; i < dim; ++i) {
            for (int j = 0; j < dim; ++j) {
                if (mBlocks[i][j] == itemVal) {
                    index = i*dim + j;
                    return index;
                }
            }
        } //
        return -1; 
    };
    
    
    // swap the value of row1,col1 with row2,col2
    // used to get the new board config..
    //------------------------------------------------------
    private boolean swapValues(int row1, int col1, int row2, int col2) {
        assert (row2 >= 0 && row2 < dim); 
        assert (row1 >= 0 && row1 < dim);
        assert (col2 >= 0 && col2 < dim); 
        assert (col1 >= 0 && col1 < dim);
        int tmpVal = this.mBlocks[row2][col2];
        this.mBlocks[row2][col2] = this.mBlocks[row1][col1];
        this.mBlocks[row1][col1] = tmpVal;
        return true;
    };
    
    
    
    
    // return the distances
    //-------------------------------------------

    // hamming distance: number of blocks out of place
    public int hamming()
    {
        int hammingDist = 0;
        int array_index = 1;
        for (int i = 0; i < dim; ++i) {
            for (int j = 0; j < dim; ++j, ++array_index) {
                // why i am checking for array_index < dim*dim (won't it always satisfy)
                if (array_index < dim*dim && mBlocks[i][j] != array_index)
                    ++hammingDist;
            }
        }
        return hammingDist;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        int manhattanDist = 0;
        for (int row = 0; row < dim; ++row)
        {
            for (int col = 0; col < dim; ++col)
            {
                int item_val = row*dim + col + 1;
                if (item_val  < dim*dim) {
                    int index = findItem(item_val);
                    if (index >= 0) {
                        //use index to get the row and col values
                        int index_row = getRow(index);
                        int index_col = getCol(index);
                        manhattanDist += Math.abs(col-index_col)+ Math.abs(row-index_row);
                    }
                    else {
                        throw new NoSuchElementException("item not found:" + item_val);
                    }
                }
            }
        }
        return manhattanDist;
    };
    
   
     
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin()
    {
        int row = StdRandom.uniform(0, dim);
        int col = StdRandom.uniform(0, dim-1); //ensures col is less than dim-1
        // find
        while (mBlocks[row][col] == 0 || mBlocks[row][col+1] == 0){
            row = StdRandom.uniform(0,dim);
            col = StdRandom.uniform(0,dim-1);
        };
        
        //copy the board
        Board newBoard = new Board(mBlocks);
        newBoard.swapValues(row, col, row, col+1);
        return newBoard;
    }
    
    
    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == this) return true; 
        if (y == null) return false; 
        if (y.getClass() != this.getClass()) return false; 
        
        Board yboard = (Board) y;
        if (yboard.dim != dim) return false;

         // checks if the board has identical values if not return      
        for ( int row = 0; row < dim; ++row) {
            for (int col = 0; col < dim; ++col) {
                if ( (yboard.mBlocks[row][col] - mBlocks[row][col]) != 0)
                    return false;
            }
        }
        
        return true;
    }
    
     // all neighboring boards
    public Iterable<Board> neighbors()    
    {
        //find the location of the zero index 
        int index = findItem(0);
        int row = getRow(index); 
        int col = getCol(index);
        Stack<Board> boards =  new Stack<Board>();
        
        //find it left, right, up and down
        if (col > 0) {
            // left neighbor exists
            Board newBoard = new Board(mBlocks);
            newBoard.swapValues(row,col,row,col-1);
            boards.push(newBoard);
        }
        if (col < dim-1) {
            //right neighbor exists
            Board newBoard = new Board(mBlocks);
            newBoard.swapValues(row,col,row,col+1);
            boards.push(newBoard);
        }
        if (row > 0 ) {
            //up neighbor exists
            Board newBoard = new Board(mBlocks);
            newBoard.swapValues(row,col,row-1,col);
            boards.push(newBoard);
        }
        if (row < dim-1) {
            //down neighbor exists
            Board newBoard = new Board(mBlocks);
            newBoard.swapValues(row,col,row+1,col);
            boards.push(newBoard);
        }
        return boards;
    };

    
    
    // helper functions
    //-------------------------------------
    
    // return rows and cols based on index val;
    private int getRow(int index) { return (int) index / dim;};
    private int getCol(int index) { return index % dim; };
    public int dimension() { return dim; } // board dimension N
    public boolean isGoal() {  return hamming() == 0; } //is this board the goal board?

    // string representation of the board (in the output format specified below)
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", mBlocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    
    }
}

