import java.util.*;

class Ldfsmaze {
    
    static class State {
        int row, col;
        State parent;
        String move;
        int depth;
        
        State(int row, int col, State parent, String move, int depth) {
            this.row = row;
            this.col = col;
            this.parent = parent;
            this.move = move;
            this.depth = depth;
        }
        
        String getKey() {
            return row + "," + col;
        }
    }
    
    static int[][] maze;
    static int rows, cols;
    static final int DEPTH_LIMIT = 3;
    static boolean solutionFound = false;
    static State goalState = null;
    
    public static void dls(State current, Set<String> visited, int goalRow, int goalCol) {
        if (solutionFound) return;
        
        // Check if reached goal
        if (current.row == goalRow && current.col == goalCol) {
            solutionFound = true;
            goalState = current;
            return;
        }
        
        // Stop if depth limit reached
        if (current.depth >= DEPTH_LIMIT) return;
        
        String key = current.getKey();
        if (visited.contains(key)) return;
        visited.add(key);
        
        // Moves: Up, Down, Left, Right
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};
        String[] moveNames = {"Up", "Down", "Left", "Right"};
        
        for (int i = 0; i < 4; i++) {
            if (solutionFound) return;
            
            int newRow = current.row + dRow[i];
            int newCol = current.col + dCol[i];
            
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && maze[newRow][newCol] == 1) {
                State next = new State(newRow, newCol, current, moveNames[i], current.depth + 1);
                if (!visited.contains(next.getKey())) {
                    dls(next, visited, goalRow, goalCol);
                }
            }
        }
        
        visited.remove(key);
    }
    
    static void printSolution() {
        if (!solutionFound) {
            System.out.println("\n✗ No path found within " + DEPTH_LIMIT + " steps!");
            return;
        }
        
        Stack<State> path = new Stack<>();
        State current = goalState;
        
        while (current != null) {
            path.push(current);
            current = current.parent;
        }
        
        System.out.println("\n✓ PATH FOUND within " + DEPTH_LIMIT + " steps!\n");
        
        int step = 0;
        while (!path.isEmpty()) {
            State s = path.pop();
            if (step == 0)
                System.out.println("Step " + step + ": Start at (" + s.row + "," + s.col + ")");
            else
                System.out.println("Step " + step + ": " + s.move + " → (" + s.row + "," + s.col + ")");
            step++;
        }
        System.out.println("\nTotal steps: " + (step - 1));
    }
    
    static void printMaze() {
        System.out.println("\nMaze Layout (1=path, 0=wall):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        // Maze: 1 = path, 0 = wall
        maze = new int[][] {
            {1, 0, 1, 1, 1},
            {1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1},
            {0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1}
        };
        
        rows = maze.length;
        cols = maze[0].length;
        
        int startRow = 0, startCol = 0;
        int goalRow = 4, goalCol = 4;
        
        System.out.println("=== MAZE SOLVER with DEPTH-LIMITED SEARCH (Depth=" + DEPTH_LIMIT + ") ===");
        printMaze();
        System.out.println("\nStart: (0,0) | Goal: (4,4)");
        System.out.println("\nSearching for path within " + DEPTH_LIMIT + " moves...");
        
        State start = new State(startRow, startCol, null, "Start", 0);
        Set<String> visited = new HashSet<>();
        
        dls(start, visited, goalRow, goalCol);
        
        printSolution();
        
        if (!solutionFound) {
            System.out.println("\nThe goal is too far! Need more than " + DEPTH_LIMIT + " steps.");
            System.out.println("Actual shortest path requires 8 steps.");
        }
    }
}