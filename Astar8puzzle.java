import java.util.*;

class Astar8puzzle {
    
    static class State implements Comparable<State> {
        int[][] board;
        int zeroRow, zeroCol;
        State parent;
        String move;
        int g; // cost from start (number of moves)
        int h; // heuristic (Manhattan distance)
        
        State(int[][] board, int zeroRow, int zeroCol, State parent, String move, int g) {
            this.board = new int[3][3];
            for (int i = 0; i < 3; i++)
                this.board[i] = board[i].clone();
            this.zeroRow = zeroRow;
            this.zeroCol = zeroCol;
            this.parent = parent;
            this.move = move;
            this.g = g;
            this.h = calculateManhattanDistance();
        }
        
        // Manhattan Distance heuristic
        int calculateManhattanDistance() {
            int distance = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int value = board[i][j];
                    if (value != 0) {
                        int goalRow = (value - 1) / 3;
                        int goalCol = (value - 1) % 3;
                        distance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                    }
                }
            }
            return distance;
        }
        
        int f() {
            return g + h; // A* evaluation function
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
        
        @Override
        public int compareTo(State other) {
            return Integer.compare(this.f(), other.f());
        }
        
        void print() {
            System.out.println(move + " (g=" + g + ", h=" + h + ", f=" + f() + ")");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    System.out.print(board[i][j] + " ");
                System.out.println();
            }
            System.out.println("--------");
        }
    }
    
    public static void solveAStar(int[][] startBoard) {
        // Find zero position
        int startRow = 0, startCol = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (startBoard[i][j] == 0) {
                    startRow = i;
                    startCol = j;
                }
        
        PriorityQueue<State> pq = new PriorityQueue<>();
        Map<String, Integer> visited = new HashMap<>(); // Store best g value for each state
        
        State start = new State(startBoard, startRow, startCol, null, "Start", 0);
        pq.add(start);
        visited.put(start.getKey(), 0);
        
        System.out.println("=== 8-PUZZLE SOLVER using A* SEARCH ===\n");
        System.out.println("Heuristic: Manhattan Distance\n");
        
        int nodesExplored = 0;
        
        while (!pq.isEmpty()) {
            State current = pq.poll();
            nodesExplored++;
            
            System.out.println("Exploring node " + nodesExplored + " (f=" + current.f() + ", g=" + current.g + ", h=" + current.h + ")");
            current.print();
            
            if (current.isGoal()) {
                System.out.println("\n✓ SOLUTION FOUND!\n");
                printSolution(current);
                System.out.println("\n--- STATISTICS ---");
                System.out.println("Nodes explored: " + nodesExplored);
                System.out.println("Optimal cost: " + current.g + " moves");
                return;
            }
            
            // Generate neighbors: Up, Down, Left, Right
            int[] dRow = {-1, 1, 0, 0};
            int[] dCol = {0, 0, -1, 1};
            String[] moveNames = {"Up", "Down", "Left", "Right"};
            
            for (int i = 0; i < 4; i++) {
                int newRow = current.zeroRow + dRow[i];
                int newCol = current.zeroCol + dCol[i];
                
                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                    int[][] newBoard = new int[3][3];
                    for (int r = 0; r < 3; r++)
                        newBoard[r] = current.board[r].clone();
                    
                    // Swap zero with adjacent tile
                    newBoard[current.zeroRow][current.zeroCol] = newBoard[newRow][newCol];
                    newBoard[newRow][newCol] = 0;
                    
                    State next = new State(newBoard, newRow, newCol, current, moveNames[i], current.g + 1);
                    String key = next.getKey();
                    
                    // If this state hasn't been visited or we found a better path to it
                    if (!visited.containsKey(key) || next.g < visited.get(key)) {
                        visited.put(key, next.g);
                        pq.add(next);
                    }
                }
            }
        }
        
        System.out.println("No solution found!");
    }
    
    static void printSolution(State goal) {
        Stack<State> path = new Stack<>();
        State current = goal;
        
        while (current != null) {
            path.push(current);
            current = current.parent;
        }
        
        int step = 0;
        System.out.println("=== SOLUTION PATH ===");
        while (!path.isEmpty()) {
            System.out.println("\nStep " + step + ":");
            path.pop().print();
            step++;
        }
        System.out.println("\nTotal moves: " + (step - 1));
    }
    
    public static void main(String[] args) {
        int[][] startBoard = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        
        solveAStar(startBoard);
    }
}