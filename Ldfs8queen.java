import java.util.*;

class Ldfs8queen  {
    
    static class State {
        int[] queens; // queens[row] = column
        int row;
        State parent;
        int depth;
        
        State(int[] queens, int row, State parent, int depth) {
            this.queens = queens.clone();
            this.row = row;
            this.parent = parent;
            this.depth = depth;
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
        
        boolean isGoal() {
            return row == 8;
        }
        
        String getKey() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < row; i++) {
                sb.append(queens[i]).append(",");
            }
            return sb.toString();
        }
        
        void print() {
            System.out.println("Depth: " + depth + " - Placed " + row + " queen(s)");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (i < row && queens[i] == j)
                        System.out.print("Q ");
                    else if (i >= row)
                        System.out.print(". ");
                    else
                        System.out.print(". ");
                }
                System.out.println();
            }
            System.out.println("----------------------------------------");
        }
        
        void printFull() {
            System.out.println("Complete solution:");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (queens[i] == j)
                        System.out.print("Q ");
                    else
                        System.out.print(". ");
                }
                System.out.println();
            }
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
        
        if (current.depth >= DEPTH_LIMIT) return;
        
        String key = current.getKey();
        if (visited.contains(key)) return;
        visited.add(key);
        
        // Try placing queen in each column of current row
        for (int col = 0; col < 8; col++) {
            if (solutionFound) return;
            
            if (current.isSafe(current.row, col)) {
                int[] newQueens = current.queens.clone();
                newQueens[current.row] = col;
                State next = new State(newQueens, current.row + 1, current, current.depth + 1);
                
                if (!visited.contains(next.getKey())) {
                    dls(next, visited);
                }
            }
        }
        
        visited.remove(key);
    }
    
    static void printSolution() {
        if (!solutionFound) {
            System.out.println("\n✗ No complete solution found within depth limit " + DEPTH_LIMIT);
            return;
        }
        
        System.out.println("\n✓ FOUND A STATE with " + goalState.row + " queens placed!\n");
        
        Stack<State> path = new Stack<>();
        State current = goalState;
        
        while (current != null) {
            path.push(current);
            current = current.parent;
        }
        
        int step = 0;
        while (!path.isEmpty()) {
            System.out.println("Step " + step + ":");
            path.pop().print();
            step++;
        }
        
        System.out.println("\nNote: With depth limit " + DEPTH_LIMIT + ", we can only place " + DEPTH_LIMIT + " queens.");
        System.out.println("To get all 8 queens, we need depth limit of at least 8.");
    }
    
    public static void main(String[] args) {
        System.out.println("=== 8-QUEENS with DEPTH-LIMITED SEARCH (Depth=" + DEPTH_LIMIT + ") ===");
        System.out.println("\nWe can only place " + DEPTH_LIMIT + " queens within depth limit\n");
        
        int[] emptyBoard = new int[8];
        Arrays.fill(emptyBoard, -1);
        State start = new State(emptyBoard, 0, null, 0);
        Set<String> visited = new HashSet<>();
        
        System.out.println("Searching for states with " + DEPTH_LIMIT + " queens placed...\n");
        
        dls(start, visited);
        
        printSolution();
        
        System.out.println("\n--- SEARCH STATISTICS ---");
        System.out.println("Depth limit: " + DEPTH_LIMIT);
        System.out.println("Nodes explored: " + nodesExplored);
        System.out.println("Maximum queens placed: " + (goalState != null ? goalState.row : 0));
        
        System.out.println("\n--- EXPLANATION ---");
        System.out.println("With depth limit " + DEPTH_LIMIT + ", we can only place " + DEPTH_LIMIT + " queens.");
        System.out.println("The 8-Queens problem requires placing all 8 queens, so depth limit must be at least 8.");
        System.out.println("\nTo solve completely, run with DEPTH_LIMIT = 8");
        
        // Show what happens with depth limit 8
        System.out.println("\n" + "=".repeat(50));
        System.out.println("\nDemonstrating with DEPTH_LIMIT = 8 (uncomment to try):");
        System.out.println("// Change DEPTH_LIMIT to 8 to find complete solution");
        
        // Uncomment below to see full solution
        /*
        System.out.println("\n=== TRYING WITH DEPTH LIMIT 8 ===\n");
        int[] emptyBoard2 = new int[8];
        Arrays.fill(emptyBoard2, -1);
        State start2 = new State(emptyBoard2, 0, null, 0);
        Set<String> visited2 = new HashSet<>();
        solutionFound = false;
        nodesExplored = 0;
        
        // Change this to 8
        // static final int DEPTH_LIMIT = 8;
        
        dls(start2, visited2);
        
        if (solutionFound) {
            System.out.println("✓ Complete solution found!\n");
            goalState.printFull();
        }
        */
    }
}