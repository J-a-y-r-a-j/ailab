import java.util.*;

class Ldfsmisscann {
    
    static class State {
        int mLeft, cLeft, boat; // boat: 0=left, 1=right
        State parent;
        String action;
        int depth;
        
        State(int mLeft, int cLeft, int boat, State parent, String action, int depth) {
            this.mLeft = mLeft;
            this.cLeft = cLeft;
            this.boat = boat;
            this.parent = parent;
            this.action = action;
            this.depth = depth;
        }
        
        boolean isValid() {
            int mRight = 3 - mLeft;
            int cRight = 3 - cLeft;
            
            // Cannibals cannot outnumber missionaries on either bank
            if (mLeft > 0 && cLeft > mLeft) return false;
            if (mRight > 0 && cRight > mRight) return false;
            
            return mLeft >= 0 && cLeft >= 0 && mLeft <= 3 && cLeft <= 3;
        }
        
        boolean isGoal() {
            return mLeft == 0 && cLeft == 0 && boat == 1;
        }
        
        String getKey() {
            return mLeft + "," + cLeft + "," + boat;
        }
        
        void print() {
            System.out.println(action + " (Depth: " + depth + ")");
            System.out.println("Left: " + mLeft + "M " + cLeft + "C | Right: " + (3-mLeft) + "M " + (3-cLeft) + "C | Boat: " + (boat == 0 ? "LEFT" : "RIGHT"));
            System.out.println("----------------------------------------");
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
        
        int[][] moves = {{1,0}, {2,0}, {0,1}, {0,2}, {1,1}};
        
        for (int[] move : moves) {
            if (solutionFound) return;
            
            int mMove = move[0];
            int cMove = move[1];
            
            if (current.boat == 0) { // Boat on left, move to right
                if (mMove <= current.mLeft && cMove <= current.cLeft) {
                    State next = new State(
                        current.mLeft - mMove,
                        current.cLeft - cMove,
                        1,
                        current,
                        "Move " + mMove + "M " + cMove + "C from LEFT to RIGHT",
                        current.depth + 1
                    );
                    if (next.isValid() && !visited.contains(next.getKey())) {
                        dls(next, visited);
                    }
                }
            } else { // Boat on right, move to left
                int mRight = 3 - current.mLeft;
                int cRight = 3 - current.cLeft;
                
                if (mMove <= mRight && cMove <= cRight) {
                    State next = new State(
                        current.mLeft + mMove,
                        current.cLeft + cMove,
                        0,
                        current,
                        "Move " + mMove + "M " + cMove + "C from RIGHT to LEFT",
                        current.depth + 1
                    );
                    if (next.isValid() && !visited.contains(next.getKey())) {
                        dls(next, visited);
                    }
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
        
        System.out.println("\n✓ SOLUTION FOUND!\n");
        
        int step = 0;
        while (!path.isEmpty()) {
            System.out.println("Step " + step + ":");
            path.pop().print();
            step++;
        }
        System.out.println("Total steps: " + (step - 1));
    }
    
    public static void main(String[] args) {
        System.out.println("=== MISSIONARIES AND CANNIBALS with DEPTH-LIMITED SEARCH ===");
        System.out.println("Depth Limit: " + DEPTH_LIMIT);
        System.out.println("Initial: 3M, 3C on LEFT bank");
        System.out.println("Goal: 0M, 0C on LEFT bank (all on right)\n");
        
        State start = new State(3, 3, 0, null, "START", 0);
        Set<String> visited = new HashSet<>();
        
        System.out.println("Searching within depth " + DEPTH_LIMIT + "...\n");
        
        dls(start, visited);
        
        printSolution();
        
        System.out.println("\n--- SEARCH STATISTICS ---");
        System.out.println("Nodes explored: " + nodesExplored);
        System.out.println("Depth limit: " + DEPTH_LIMIT);
        
        if (!solutionFound) {
            System.out.println("\nThe Missionaries and Cannibals problem requires 11 moves to solve.");
            System.out.println("With depth limit " + DEPTH_LIMIT + ", we can only explore states within 3 moves.");
            System.out.println("\nStates reachable within 3 moves from start:");
            System.out.println("Depth 0: (3,3,0)");
            System.out.println("Depth 1: (2,2,1) [move 1M1C], (1,3,1) [move 2M], (3,1,1) [move 2C]");
            System.out.println("Depth 2-3: More states, but goal (0,0,1) requires 11 moves");
        }
    }
}