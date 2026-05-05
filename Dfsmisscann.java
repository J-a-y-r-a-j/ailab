import java.util.*;

class Dfsmisscann {
    
    static class State {
        int mLeft, cLeft, boat;
        State parent;
        String action;
        
        State(int mLeft, int cLeft, int boat, State parent, String action) {
            this.mLeft = mLeft;
            this.cLeft = cLeft;
            this.boat = boat;
            this.parent = parent;
            this.action = action;
        }
        
        boolean isValid() {
            int mRight = 3 - mLeft;
            int cRight = 3 - cLeft;
            
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
            System.out.println(action);
            System.out.print("Left: " + mLeft + "M " + cLeft + "C  |  ");
            System.out.print("Right: " + (3-mLeft) + "M " + (3-cLeft) + "C  |  ");
            System.out.println("Boat: " + (boat == 0 ? "LEFT" : "RIGHT"));
            System.out.println("-----------------------------------");
        }
    }
    
    static State goalState = null;
    static int maxDepth = 50;
    
    public static void dfs(State current, Set<String> visited, int depth) {
        if (depth > maxDepth) return;
        if (current.isGoal()) {
            goalState = current;
            return;
        }
        
        String key = current.getKey();
        if (visited.contains(key)) return;
        visited.add(key);
        
        int[][] moves = {{1,0}, {2,0}, {0,1}, {0,2}, {1,1}};
        
        for (int[] move : moves) {
            if (goalState != null) return;
            
            int mMove = move[0];
            int cMove = move[1];
            
            if (current.boat == 0) { // Boat on left
                if (mMove <= current.mLeft && cMove <= current.cLeft) {
                    State next = new State(
                        current.mLeft - mMove,
                        current.cLeft - cMove,
                        1,
                        current,
                        "► Move " + mMove + "M " + cMove + "C from LEFT to RIGHT"
                    );
                    if (next.isValid()) {
                        dfs(next, visited, depth + 1);
                    }
                }
            } else { // Boat on right
                if (mMove <= (3 - current.mLeft) && cMove <= (3 - current.cLeft)) {
                    State next = new State(
                        current.mLeft + mMove,
                        current.cLeft + cMove,
                        0,
                        current,
                        "◄ Move " + mMove + "M " + cMove + "C from RIGHT to LEFT"
                    );
                    if (next.isValid()) {
                        dfs(next, visited, depth + 1);
                    }
                }
            }
        }
        
        visited.remove(key);
    }
    
    public static void main(String[] args) {
        State start = new State(3, 3, 0, null, "START");
        Set<String> visited = new HashSet<>();
        
        System.out.println("=== MISSIONARIES AND CANNIBALS (DFS) ===\n");
        System.out.println("Initial State: 3 Missionaries, 3 Cannibals on LEFT bank");
        System.out.println("Goal: All 6 people on RIGHT bank\n");
        
        dfs(start, visited, 0);
        
        if (goalState != null) {
            System.out.println("\n✓ SOLUTION FOUND!\n");
            
            Stack<State> path = new Stack<>();
            State curr = goalState;
            while (curr != null) {
                path.push(curr);
                curr = curr.parent;
            }
            
            int step = 0;
            while (!path.isEmpty()) {
                System.out.println("Step " + step + ":");
                path.pop().print();
                step++;
            }
            System.out.println("Total moves: " + (step - 1));
        } else {
            System.out.println("✗ No solution found within depth " + maxDepth);
        }
    }
}