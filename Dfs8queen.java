import java.util.*;

class Dfs8queen {
    
    static class State {
        int[] queens;
        int row;
        
        State(int[] queens, int row) {
            this.queens = queens.clone();
            this.row = row;
        }
        
        boolean isSafe(int row, int col) {
            for (int prevRow = 0; prevRow < row; prevRow++) {
                int prevCol = queens[prevRow];
                if (prevCol == col || Math.abs(prevRow - row) == Math.abs(prevCol - col)) {
                    return false;
                }
            }
            return true;
        }
        
        boolean isComplete() {
            return row == 8;
        }
    }
    
    static Stack<State> path = new Stack<>();
    
    public static boolean dfs(State current) {
        if (current.isComplete()) {
            return true;
        }
        
        for (int col = 0; col < 8; col++) {
            if (current.isSafe(current.row, col)) {
                int[] newQueens = current.queens.clone();
                newQueens[current.row] = col;
                State next = new State(newQueens, current.row + 1);
                path.push(next);
                
                if (dfs(next)) {
                    return true;
                }
                
                path.pop();
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        int[] emptyBoard = new int[8];
        Arrays.fill(emptyBoard, -1);
        State start = new State(emptyBoard, 0);
        path.push(start);
        
        System.out.println("=== 8-QUEENS DFS SOLUTION ===\n");
        
        if (dfs(start)) {
            State solution = path.peek();
            System.out.println("✓ SOLUTION FOUND!\n");
            
            System.out.println("Queen positions (row, column):");
            for (int i = 0; i < 8; i++) {
                System.out.println("Row " + i + " -> Column " + solution.queens[i]);
            }
            
            System.out.println("\nBoard visualization:");
            System.out.println("  | 0 1 2 3 4 5 6 7");
            System.out.println("--+----------------");
            for (int i = 0; i < 8; i++) {
                System.out.print(i + " | ");
                for (int j = 0; j < 8; j++) {
                    if (solution.queens[i] == j)
                        System.out.print("Q ");
                    else
                        System.out.print(". ");
                }
                System.out.println();
            }
        } else {
            System.out.println("No solution found!");
        }
    }
}