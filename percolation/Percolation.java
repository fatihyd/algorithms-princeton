import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * NOTE: All public methods expect grid parameters (row, col) to be 1-based
 * indices.
 */

public class Percolation {
    // n-by-n grid where true represents an open site, false represents a blocked
    // site
    private boolean[][] grid;
    private int gridSize;
    private WeightedQuickUnionUF unionFind;
    private int virtualTop;
    private int virtualBottom;
    private int numOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        gridSize = n;
        grid = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        virtualTop = n * n;
        virtualBottom = n * n + 1;
        numOfOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    /*
     * parameters "row" and "col" are 1-based-index
     * (first site in the grid is 1,1)
     */
    public void open(int row, int col) {
        // Convert row and col to zero-based index
        int rowIndex = row - 1;
        int colIndex = col - 1;

        if (rowIndex < 0 || rowIndex > gridSize - 1 || colIndex < 0 || colIndex > gridSize - 1) {
            throw new IllegalArgumentException();
        }

        // Open the site if it is not open already
        if (!grid[rowIndex][colIndex]) {
            grid[rowIndex][colIndex] = true;
            numOfOpenSites++;
            // Connect the site to the virtual top or bottom if applicable
            // ... (if the site is either on the top or the bottom)
            if (rowIndex == 0) {
                unionFind.union(getUFIndex(rowIndex, colIndex), virtualTop);
            } else if (rowIndex == gridSize - 1) {
                unionFind.union(getUFIndex(rowIndex, colIndex), virtualBottom);
            }
        }

        // Get the 1D Union-Find index for the 2D grid position (r, c)
        int currentSiteUFIndex = getUFIndex(rowIndex, colIndex);

        // Connect to top neighbor if it is open
        // Ensure there's a top neighbor and it's open
        if (rowIndex > 0 && grid[rowIndex - 1][colIndex]) {
            unionFind.union(currentSiteUFIndex, getUFIndex(rowIndex - 1, colIndex));
        }

        // Connect to bottom neighbor if it is open
        // Ensure there's a bottom neighbor and it's open
        if (rowIndex < gridSize - 1 && grid[rowIndex + 1][colIndex]) {
            unionFind.union(currentSiteUFIndex, getUFIndex(rowIndex + 1, colIndex));
        }

        // Connect to left neighbor if it is open
        // Ensure there's a left neighbor and it's open
        if (colIndex > 0 && grid[rowIndex][colIndex - 1]) {
            unionFind.union(currentSiteUFIndex, getUFIndex(rowIndex, colIndex - 1));
        }

        // Connect to right neighbor if it is open
        // Ensure there's a right neighbor and it's open
        if (colIndex < gridSize - 1 && grid[rowIndex][colIndex + 1]) {
            unionFind.union(currentSiteUFIndex, getUFIndex(rowIndex, colIndex + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // Convert row and col to zero-based index
        int rowIndex = row - 1;
        int colIndex = col - 1;

        if (rowIndex < 0 || rowIndex > gridSize - 1 || colIndex < 0 || colIndex > gridSize - 1) {
            throw new IllegalArgumentException();
        }

        return grid[rowIndex][colIndex];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // Convert row and col to zero-based index
        int rowIndex = row - 1;
        int colIndex = col - 1;

        if (rowIndex < 0 || rowIndex > gridSize - 1 || colIndex < 0 || colIndex > gridSize - 1) {
            throw new IllegalArgumentException();
        }

        // Return true if the site is connected to any open site in the top row (virtual
        // top)
        return unionFind.find(getUFIndex(rowIndex, colIndex)) == unionFind.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // Return true if virtual top and bottom are connected
        return unionFind.find(virtualTop) == unionFind.find(virtualBottom);
    }

    // Returns the 1D Union-Find index for the 2D grid position (r, c)
    private int getUFIndex(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex > gridSize - 1 || colIndex < 0 || colIndex > gridSize - 1) {
            throw new IllegalArgumentException();
        }

        return (rowIndex * gridSize) + colIndex;
    }

    private void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j]) {
                    System.out.print("O "); // O represents an open site
                } else {
                    System.out.print("X "); // X represents a blocked site
                }
            }
            System.out.println(); // Move to the next line after each row
        }
        System.out.println();
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4); // 4x4 grid
        System.out.println("Initial grid state:");
        p.printGrid();
        System.out.println("Percolates? " + p.percolates());
        System.out.println("----------------");

        // Test case 1: Open a single site in the top row
        p.open(1, 1);
        System.out.println("After opening (1, 1):");
        p.printGrid();
        System.out.println("Is (1, 1) full? " + p.isFull(1, 1));
        System.out.println("Percolates? " + p.percolates());
        System.out.println("----------------");

        // Test case 2: Open another site in the top row
        p.open(1, 2);
        System.out.println("After opening (1, 2):");
        p.printGrid();
        System.out.println("Is (1, 2) full? " + p.isFull(1, 2));
        System.out.println("Percolates? " + p.percolates());
        System.out.println("----------------");

        // Test case 3: Open a site not in the top row, no percolation
        p.open(2, 2);
        System.out.println("After opening (2, 2):");
        p.printGrid();
        System.out.println("Is (2, 2) full? " + p.isFull(2, 2));
        System.out.println("Percolates? " + p.percolates());
        System.out.println("----------------");

        // Test case 4: Open a site in the bottom row, still no percolation
        p.open(4, 2);
        System.out.println("After opening (4, 2):");
        p.printGrid();
        System.out.println("Is (4, 2) full? " + p.isFull(4, 2));
        System.out.println("Percolates? " + p.percolates());
        System.out.println("----------------");

        // Test case 5: Open a path from the top to the bottom row
        p.open(3, 2);
        System.out.println("After opening (3, 2):");
        p.printGrid();
        System.out.println("Is (3, 2) full? " + p.isFull(3, 2));
        System.out.println("Percolates? " + p.percolates());
        System.out.println("----------------");

        // Test case 6: Check if a non-connected site in the bottom row is full
        p.open(4, 4);
        System.out.println("After opening (4, 4):");
        p.printGrid();
        System.out.println("Is (4, 4) full? " + p.isFull(4, 4));
        System.out.println("Percolates? " + p.percolates());
        System.out.println("----------------");

        // Test case 7: Open all sites in the second column to ensure percolation
        p.open(2, 2); // Should already be open
        System.out.println("After ensuring full path in second column:");
        p.printGrid();
        System.out.println("Is (4, 2) full? " + p.isFull(4, 2));
        System.out.println("Percolates? " + p.percolates());
        System.out.println("----------------");

        // Test case 8: Check number of open sites
        System.out.println("Number of open sites: " + p.numberOfOpenSites());
    }

}
