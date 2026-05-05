import java.util.*;

class Constraint8queens {
    
    // CSP: Variables: rows 0-7 (each must have a queen)
    // Domains: columns 0-7 for each queen
    // Constraints: No two queens share same column or diagonal
    
    static int[] queens; // queens[row] = column
    static int solutionsCount = 0;
    
    // Check if placing queen at (row, col) is safe given previous placements
    static boolean isSafe(int row, int col) {
        for (int prevRow = 0; prevRow < row; prevRow++) {
            int prevCol = queens[prevRow];
            
            // Same column
            if (prevCol == col) return false;
            
            // Same diagonal
            if (Math.abs(prevRow - row) == Math.abs(prevCol - col)) return false;
        }
        return true;
    }
    
    // Backtracking CSP solver
    static void solveCSP(int row) {
        if (row == 8) {
            // Found a solution
            solutionsCount++;
            System.out.println("\n=== SOLUTION #" + solutionsCount + " ===");
            printBoard();
            return;
        }
        
        // Try each column for current row
        for (int col = 0; col < 8; col++) {
            if (isSafe(row, col)) {
                queens[row] = col; // Assign value to variable
                solveCSP(row + 1); // Recurse to next variable
                // No need to unassign (queens[row] will be overwritten)
            }
        }
    }
    
    static void printBoard() {
        System.out.println("Queen positions (row, column):");
        for (int i = 0; i < 8; i++) {
            System.out.println("Row " + i + " → Column " + queens[i]);
        }
        
        System.out.println("\nBoard visualization:");
        System.out.println("  | 0 1 2 3 4 5 6 7");
        System.out.println("--+----------------");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < 8; j++) {
                if (queens[i] == j)
                    System.out.print("Q ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 8-QUEENS CONSTRAINT SATISFACTION PROBLEM ===\n");
        System.out.println("Variables: 8 rows (0-7)");
        System.out.println("Domains: Columns 0-7 for each queen");
        System.out.println("Constraints: No two queens share same row, column, or diagonal\n");
        
        queens = new int[8];
        solutionsCount = 0;
        
        solveCSP(0);
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Total solutions found: " + solutionsCount);
        System.out.println("(Note: Total distinct solutions for 8-Queens is 92)");
    }
}