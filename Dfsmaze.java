import java.util.*;

class Dfsmaze {
    
    static class State {
        int row, col;
        State parent;
        String move;
        
        State(int row, int col, State parent, String move) {
            this.row = row;
            this.col = col;
            this.parent = parent;
            this.move = move;
        }
        
        boolean isGoal(int goalRow, int goalCol) {
            return row == goalRow && col == goalCol;
        }
        
        String getKey() {
            return row + "," + col;
        }
    }
    
    static int rows, cols;
    static int[][] maze;
    
    public static void solveDFS(int[][] mazeMap, int startRow, int startCol, int goalRow, int goalCol) {
        maze = mazeMap;
        rows = maze.length;
        cols = maze[0].length;
        
        Stack<State> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        
        State start = new State(startRow, startCol, null, "Start");
        stack.push(start);
        visited.add(start.getKey());
        
        System.out.println("Solving Maze using DFS...");
        System.out.println("Start: (" + startRow + "," + startCol + ")");
        System.out.println("Goal: (" + goalRow + "," + goalCol + ")\n");
        
        while (!stack.isEmpty()) {
            State current = stack.pop();
            
            if (current.isGoal(goalRow, goalCol)) {
                System.out.println("\n✓ PATH FOUND!\n");
                printSolution(current);
                printMazeWithPath(current);
                return;
            }
            
            // Possible moves: Up, Down, Left, Right
            int[] dRow = {-1, 1, 0, 0};
            int[] dCol = {0, 0, -1, 1};
            String[] moveNames = {"Up", "Down", "Left", "Right"};
            
            for (int i = 0; i < 4; i++) {
                int newRow = current.row + dRow[i];
                int newCol = current.col + dCol[i];
                
                // Check bounds and if cell is walkable (1 = path, 0 = wall)
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && 
                    maze[newRow][newCol] == 1) {
                    
                    State next = new State(newRow, newCol, current, moveNames[i]);
                    String key = next.getKey();
                    
                    if (!visited.contains(key)) {
                        visited.add(key);
                        stack.push(next);
                    }
                }
            }
        }
        
        System.out.println("No path found from start to goal!");
    }
    
    static void printSolution(State goal) {
        Stack<State> path = new Stack<>();
        State current = goal;
        
        while (current != null) {
            path.push(current);
            current = current.parent;
        }
        
        int step = 0;
        System.out.println("=== PATH STEPS ===");
        while (!path.isEmpty()) {
            State s = path.pop();
            if (step == 0)
                System.out.println("Step " + step + ": Start at (" + s.row + "," + s.col + ")");
            else
                System.out.println("Step " + step + ": " + s.move + " -> (" + s.row + "," + s.col + ")");
            step++;
        }
        System.out.println("\nTotal steps: " + (step - 1));
        System.out.println("Path length (cells): " + (step));
    }
    
    static void printMazeWithPath(State goal) {
        // Create a copy of maze to mark the path
        char[][] display = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                display[i][j] = (maze[i][j] == 1) ? '.' : '#';
            }
        }
        
        // Mark the path
        State current = goal;
        while (current != null) {
            display[current.row][current.col] = 'P';
            current = current.parent;
        }
        
        // Mark start and goal
        display[goal.row][goal.col] = 'G';
        current = goal;
        while (current.parent != null) {
            current = current.parent;
        }
        display[current.row][current.col] = 'S';
        
        // Print the maze
        System.out.println("\n=== MAZE WITH PATH ===");
        System.out.println("S = Start, G = Goal, P = Path, # = Wall, . = Path (unused)");
        System.out.println();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(display[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        // Maze: 1 = path, 0 = wall
        int[][] maze = {
            {1, 0, 1, 1, 1},
            {1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1},
            {0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1}
        };
        
        // Start position (0,0) and Goal position (4,4)
        solveDFS(maze, 0, 0, 4, 4);
    }
}