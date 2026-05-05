import java.util.*;

class Bfs8queen {
    
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
                if (prevCol == col || 
                    Math.abs(prevRow - row) == Math.abs(prevCol - col))
                    return false;
            }
            return true;
        }
        
        boolean isGoal() {
            return row == 8;
        }
        
        void print() {
            System.out.println("\nSolution found:");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(queens[i] == j ? "Q " : ". ");
                }
                System.out.println();
            }
        }
    }
    
    public static void solveBFS() {
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(new int[8], 0));
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            if (current.isGoal()) {
                current.print();
                System.out.println("\nQueen positions (row: column):");
                for (int i = 0; i < 8; i++) {
                    System.out.println("Row " + i + " -> Column " + current.queens[i]);
                }
                return;
            }
            
            for (int col = 0; col < 8; col++) {
                if (current.isSafe(current.row, col)) {
                    int[] newQueens = current.queens.clone();
                    newQueens[current.row] = col;
                    queue.add(new State(newQueens, current.row + 1));
                }
            }
        }
        
        System.out.println("No solution found!");
    }
    
    public static void main(String[] args) {
        System.out.println("8-Queens BFS Solution\n");
        solveBFS();
    }
}