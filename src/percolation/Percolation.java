package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int n;
    private int top;
    private int bot;
    private boolean[] isOpenBoo;
    private WeightedQuickUnionUF wQUpercolation;
    private WeightedQuickUnionUF wQUfullness;

   public Percolation(int n)                
   {
        if (n<=0) {throw new IllegalArgumentException();} 
        wQUpercolation = new WeightedQuickUnionUF(n*n+3);
        wQUfullness = new WeightedQuickUnionUF(n*n+3);
        isOpenBoo  = new boolean[n*n+3];
        this.n = n;
        this.top = n*n+1;
        this.bot = n*n+2;
        
        
   }
   public void open(int row, int col)
   {
        if (row <= 0 || row > n || col <= 0 || col > n)
        {
        	throw new IndexOutOfBoundsException();
        } else
        {
        	int pos = n*(row-1)+col;
        	this.isOpenBoo[pos] = true;
        	int cornerBoo[] = {1, 1, 1, 1};
        	if (row == 1) 
        	{
        		cornerBoo[0]=0; 
        		wQUpercolation.union(pos, top);
        		wQUfullness.union(pos, top);
        	}
        	if (row == n) 
        	{
        		cornerBoo[1]=0;
        		wQUpercolation.union(pos,bot);
        	}
        	if (col == 1) {cornerBoo[2]=0;}
        	if (col == n) {cornerBoo[3]=0;}
        	if (cornerBoo[0]==1 && isOpen(row-1, col))
        	{
        		wQUpercolation.union(pos,pos-cornerBoo[0]*n);
        		wQUfullness.union(pos,pos-cornerBoo[0]*n);
        	}
        	if (cornerBoo[1]==1 && isOpen(row+1, col)) 
        	{
        		wQUpercolation.union(pos,pos+cornerBoo[1]*n);
        		wQUfullness.union(pos,pos+cornerBoo[1]*n);
        	}
        	if (cornerBoo[2]==1 && isOpen(row, col-1)) 
        	{
        		wQUpercolation.union(pos,pos-cornerBoo[2]*1);
        		wQUfullness.union(pos,pos-cornerBoo[2]*1);
        	}
        	if (cornerBoo[3]==1 && isOpen(row, col+1)) 
        	{
        		wQUpercolation.union(pos,pos+cornerBoo[3]*1);
        		wQUfullness.union(pos,pos+cornerBoo[3]*1);
        	}
        } 
   }
   public boolean isOpen(int row, int col)  
   {
	   if (row <= 0 || row > n || col <= 0 || col > n)
       {
       	throw new IndexOutOfBoundsException();
       } else
        {
            return isOpenBoo[n*(row-1)+col];
        } 
   }
   public boolean isFull(int row, int col)  
   {
	   if (row <= 0 || row > n || col <= 0 || col > n)
       {
       	throw new IndexOutOfBoundsException();
       } else
        {
            return isOpen(row,col) && wQUfullness.connected(n*(row-1)+col,top);
        } 
   }
   public boolean percolates()             
    {
        return wQUpercolation.connected(bot,top);
    }
   public static void main(String[] args) 
   {
        Percolation percolation = new Percolation(4);
        for (int i=1; i<=4; i++) {
            percolation.open(i, 1);
        }
        
        percolation.open(4, 3);
        percolation.open(3, 3);
        System.out.println(percolation.isOpen(4, 1));
        System.out.println(percolation.isFull(4, 3));
        System.out.println(percolation.isFull(3, 3));
        System.out.println(percolation.isFull(3, 4));
        System.out.println(percolation.percolates());
   }  // test client (optional)
}

