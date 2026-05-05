import java.util.*;

class Dfs8puzzle {
    
    static class State {
        int[][] board;
        int zeroRow, zeroCol;
        String move;
        State parent;
        
        State(int[][] board, int zeroRow, int zeroCol, String move, State parent) {
            this.board = new int[3][3];
            for (int i = 0; i < 3; i++)
                this.board[i] = board[i].clone();
            this.zeroRow = zeroRow;
            this.zeroCol = zeroCol;
            this.move = move;
            this.parent = parent;
        }
        
        boolean isGoal() {
            int[][] goal = {{1,2,3}, {4,5,6}, {7,8,0}};
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
            System.out.println(move);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    System.out.print(board[i][j] + " ");
                System.out.println();
            }
            System.out.println("--------");
        }
    }
    
    public static void solveDFS(int[][] startBoard) {
        // Find zero position
        int startRow = 0, startCol = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (startBoard[i][j] == 0) {
                    startRow = i; startCol = j;
                }
        
        Stack<State> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        
        State start = new State(startBoard, startRow, startCol, "Start", null);
        stack.push(start);
        visited.add(start.getKey());
        
        int maxDepth = 30;
        Map<String, Integer> depthMap = new HashMap<>();
        depthMap.put(start.getKey(), 0);
        
        System.out.println("Solving 8-Puzzle using DFS (depth limit: " + maxDepth + ")\n");
        
        while (!stack.isEmpty()) {
            State current = stack.pop();
            int currentDepth = depthMap.get(current.getKey());
            
            if (current.isGoal()) {
                System.out.println("\n✓ SOLUTION FOUND!\n");
                printSolution(current);
                return;
            }
            
            if (currentDepth >= maxDepth) continue;
            
            // Possible moves: up, down, left, right
            int[] dRow = {-1, 1, 0, 0};
            int[] dCol = {0, 0, -1, 1};
            String[] moveNames = {"Up", "Down", "Left", "Right"};
            
            for (int i = 0; i < 4; i++) {
                int newRow = current.zeroRow + dRow[i];
                int newCol = current.zeroCol + dCol[i];
                
                if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                    // Create new board by swapping
                    int[][] newBoard = new int[3][3];
                    for (int r = 0; r < 3; r++)
                        newBoard[r] = current.board[r].clone();
                    
                    newBoard[current.zeroRow][current.zeroCol] = newBoard[newRow][newCol];
                    newBoard[newRow][newCol] = 0;
                    
                    State next = new State(newBoard, newRow, newCol, moveNames[i], current);
                    String key = next.getKey();
                    
                    if (!visited.contains(key)) {
                        visited.add(key);
                        depthMap.put(key, currentDepth + 1);
                        stack.push(next);
                    }
                }
            }
        }
        
        System.out.println("No solution found within depth " + maxDepth);
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
        // Example: Solvable puzzle
        int[][] startBoard = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        
        solveDFS(startBoard);
    }
}