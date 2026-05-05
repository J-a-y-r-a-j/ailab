import java.util.*;

class Bestfirst8puzzle {
    
    static class State implements Comparable<State> {
        int[][] board;
        int zeroRow, zeroCol;
        State parent;
        String move;
        int cost; // heuristic value
        
        State(int[][] board, int zeroRow, int zeroCol, State parent, String move) {
            this.board = new int[3][3];
            for (int i = 0; i < 3; i++)
                this.board[i] = board[i].clone();
            this.zeroRow = zeroRow;
            this.zeroCol = zeroCol;
            this.parent = parent;
            this.move = move;
            this.cost = calculateHeuristic();
        }
        
        // Heuristic: Number of misplaced tiles (Manhattan distance would be better)
        int calculateHeuristic() {
            int misplaced = 0;
            int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (board[i][j] != 0 && board[i][j] != goal[i][j])
                        misplaced++;
            return misplaced;
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
            return Integer.compare(this.cost, other.cost);
        }
        
        void print() {
            System.out.println(move + " (Heuristic: " + cost + ")");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    System.out.print(board[i][j] + " ");
                System.out.println();
            }
            System.out.println("--------");
        }
    }
    
    public static void solveBestFirst(int[][] startBoard) {
        // Find zero position
        int startRow = 0, startCol = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (startBoard[i][j] == 0) {
                    startRow = i;
                    startCol = j;
                }
        
        PriorityQueue<State> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        
        State start = new State(startBoard, startRow, startCol, null, "Start");
        pq.add(start);
        visited.add(start.getKey());
        
        System.out.println("=== 8-PUZZLE SOLVER using BEST FIRST SEARCH ===\n");
        System.out.println("Heuristic: Number of misplaced tiles\n");
        
        int nodesExplored = 0;
        
        while (!pq.isEmpty()) {
            State current = pq.poll();
            nodesExplored++;
            
            System.out.println("Exploring state " + nodesExplored + " (Heuristic: " + current.cost + ")");
            current.print();
            
            if (current.isGoal()) {
                System.out.println("\n✓ SOLUTION FOUND!\n");
                printSolution(current);
                System.out.println("Total nodes explored: " + nodesExplored);
                return;
            }
            
            // Generate neighbors
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
                    
                    newBoard[current.zeroRow][current.zeroCol] = newBoard[newRow][newCol];
                    newBoard[newRow][newCol] = 0;
                    
                    State next = new State(newBoard, newRow, newCol, current, moveNames[i]);
                    
                    if (!visited.contains(next.getKey())) {
                        visited.add(next.getKey());
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
        while (!path.isEmpty()) {
            System.out.println("Step " + step + ":");
            path.pop().print();
            step++;
        }
        System.out.println("Total steps: " + (step - 1));
    }
    
    public static void main(String[] args) {
        int[][] startBoard = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        
        solveBestFirst(startBoard);
    }
}