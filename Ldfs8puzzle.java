import java.util.*;

class Ldfs8puzzle {
    
    static class State {
        int[][] board;
        int zeroRow, zeroCol;
        State parent;
        String move;
        int depth;
        
        State(int[][] board, int zeroRow, int zeroCol, State parent, String move, int depth) {
            this.board = new int[3][3];
            for (int i = 0; i < 3; i++)
                this.board[i] = board[i].clone();
            this.zeroRow = zeroRow;
            this.zeroCol = zeroCol;
            this.parent = parent;
            this.move = move;
            this.depth = depth;
        }
        
        boolean isGoal() {
            int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (board[i][j] != goal[i][j]) return false;
            return true;
        }
        
        String getKey() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    sb.append(board[i][j]);
            return sb.toString();
        }
        
        void print() {
            System.out.println(move + " (Depth: " + depth + ")");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    System.out.print(board[i][j] + " ");
                System.out.println();
            }
            System.out.println("--------");
        }
    }
    
    static final int DEPTH_LIMIT = 3;
    static boolean solutionFound = false;
    static State goalState = null;
    static int nodesExplored = 0;
    
    public static void dls(State current, Set<String> visited) {
        nodesExplored++;
        
        if (solutionFound) return;
        
        if (current.isGoal()) {
            solutionFound = true;
            goalState = current;
            return;
        }
        
        if (current.depth >= DEPTH_LIMIT) {
            return;
        }
        
        String key = current.getKey();
        if (visited.contains(key)) return;
        visited.add(key);
        
        // Possible moves: Up, Down, Left, Right
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};
        String[] moveNames = {"Up", "Down", "Left", "Right"};
        
        for (int i = 0; i < 4; i++) {
            if (solutionFound) return;
            
            int newRow = current.zeroRow + dRow[i];
            int newCol = current.zeroCol + dCol[i];
            
            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                int[][] newBoard = new int[3][3];
                for (int r = 0; r < 3; r++)
                    newBoard[r] = current.board[r].clone();
                
                // Swap zero with adjacent tile
                newBoard[current.zeroRow][current.zeroCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;
                
                State next = new State(newBoard, newRow, newCol, current, 
                                       moveNames[i], current.depth + 1);
                
                if (!visited.contains(next.getKey())) {
                    dls(next, visited);
                }
            }
        }
        
        visited.remove(key);
    }
    
    static void printSolution() {
        if (!solutionFound) {
            System.out.println("\n✗ No solution found within depth limit " + DEPTH_LIMIT);
            return;
        }
        
        Stack<State> path = new Stack<>();
        State current = goalState;
        
        while (current != null) {
            path.push(current);
            current = current.parent;
        }
        
        System.out.println("\n✓ SOLUTION FOUND within depth " + DEPTH_LIMIT + "!\n");
        
        int step = 0;
        while (!path.isEmpty()) {
            System.out.println("Step " + step + ":");
            path.pop().print();
            step++;
        }
        System.out.println("Total steps: " + (step - 1));
    }
    
    static void printSearchInfo() {
        System.out.println("\n--- SEARCH INFORMATION ---");
        System.out.println("Depth Limit: " + DEPTH_LIMIT);
        System.out.println("Nodes explored: " + nodesExplored);
        
        if (!solutionFound) {
            System.out.println("\nThe 8-puzzle cannot be solved in " + DEPTH_LIMIT + " moves.");
            System.out.println("Minimum moves for this puzzle is usually 2-3 steps if close to goal.");
            System.out.println("\nTry a puzzle that is only 1-2 moves away from goal to see solution.");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 8-PUZZLE with DEPTH-LIMITED SEARCH (Depth=" + DEPTH_LIMIT + ") ===\n");
        
        // Puzzle that is 2 moves away from goal
        int[][] startBoard = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 0, 8}  // Only 1 move away (swap 0 and 8)
        };
        
        // Find zero position
        int startRow = 0, startCol = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (startBoard[i][j] == 0) {
                    startRow = i;
                    startCol = j;
                }
        
        State start = new State(startBoard, startRow, startCol, null, "START", 0);
        Set<String> visited = new HashSet<>();
        
        System.out.println("Initial State:");
        start.print();
        System.out.println("Searching within depth " + DEPTH_LIMIT + "...\n");
        
        dls(start, visited);
        
        printSolution();
        printSearchInfo();
        
        // Demonstrate with a puzzle that requires more depth
        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nTrying a harder puzzle (requires 4+ moves):");
        
        int[][] harderBoard = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        
        int hRow = 0, hCol = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (harderBoard[i][j] == 0) {
                    hRow = i;
                    hCol = j;
                }
        
        State start2 = new State(harderBoard, hRow, hCol, null, "START", 0);
        Set<String> visited2 = new HashSet<>();
        solutionFound = false;
        goalState = null;
        nodesExplored = 0;
        
        System.out.println("\nInitial State:");
        start2.print();
        System.out.println("Searching within depth " + DEPTH_LIMIT + "...\n");
        
        dls(start2, visited2);
        
        if (!solutionFound) {
            System.out.println("✗ No solution found within " + DEPTH_LIMIT + " moves.");
            System.out.println("This puzzle requires more moves to solve.");
        }
    }
}