import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    private int nsize;
    private int sizecount;
    private WeightedQuickUnionUF wquf;
    private int virtualT;
    private int virtualB;
    private boolean[] site_open;
    private int opencount;

    public Percolation(int n){
        if (n <= 0){
            throw new IllegalArgumentException("Dimensions must be positive.");
        }
        nsize = n;
        sizecount = nsize * nsize;
        site_open = new boolean[sizecount];
        wquf = new WeightedQuickUnionUF(sizecount + 2);
        virtualT = sizecount;
        virtualB = sizecount + 1;
        opencount = 0;
    }

    public boolean isFull(int row, int col){
        validxy(row, col);
        int siteIndex = getIndex(row, col);
        return wquf.connected(virtualT, siteIndex);
    }

    private int getIndex(int row, int col){
        validxy(row, col);
        return (row - 1) * nsize + (col - 1);
    }

    public boolean isOpen(int row, int col){
        validxy(row, col);
        return site_open[getIndex(row, col)];
    }

    public boolean percolates(){
        return wquf.connected(virtualT, virtualB);
    }

    public void open(int i, int j){
        validxy(i, j);
        int openindex = getIndex(i, j);
        if (!site_open[openindex]){
            site_open[openindex] = true;
            opencount++;
            if (nsize != 1){
                if (i == 1){
                    // top
                    wquf.union(openindex, virtualT);
                }else if (i == nsize){
                    // bot
                    wquf.union(openindex, virtualB);
                }
                // union all direction
                if (j != 1 && isOpen(i, j - 1)){
                    wquf.union(openindex, getIndex(i, j - 1));
                }
                if (j != nsize && isOpen(i, j + 1)){
                    wquf.union(openindex, getIndex(i, j + 1));
                }
                if (i != 1 && isOpen(i - 1, j)){
                    wquf.union(openindex, getIndex(i - 1, j));
                }
                if (i != nsize && isOpen(i + 1, j)){
                    wquf.union(openindex, getIndex(i + 1, j));
                }
            }else{
                wquf.union(openindex, virtualT);
                wquf.union(openindex, virtualB);
            }
        }
    }

    private void validxy(int i, int j) {
        if ((i <= 0 || i > nsize) || (j <= 0 || j > nsize)){
            throw new IndexOutOfBoundsException("Illegal matrix value.");
        }
        
    }

    public int numberOfOpenSites(){
        return opencount;
    }

    public static void main(String[] args){
    }
}
