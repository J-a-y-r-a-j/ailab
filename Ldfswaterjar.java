import java.util.*;

class Ldfswaterjar {
    
    static class State {
        int jug1, jug2; // jug1=4L, jug2=3L
        State parent;
        String action;
        int depth;
        
        State(int jug1, int jug2, State parent, String action, int depth) {
            this.jug1 = jug1;
            this.jug2 = jug2;
            this.parent = parent;
            this.action = action;
            this.depth = depth;
        }
        
        boolean isGoal() {
            return jug1 == 2; // Goal: 2 liters in 4L jug
        }
        
        String getKey() {
            return jug1 + "," + jug2;
        }
        
        void print() {
            System.out.println(action);
            System.out.println("4L Jug: " + jug1 + " liters | 3L Jug: " + jug2 + " liters");
            System.out.println("Depth: " + depth);
            System.out.println("----------------------------------------");
        }
    }
    
    static final int CAP1 = 4;
    static final int CAP2 = 3;
    static final int DEPTH_LIMIT = 3;
    static boolean solutionFound = false;
    static State goalState = null;
    
    public static void dls(State current, Set<String> visited) {
        // Stop if solution already found
        if (solutionFound) return;
        
        // Check if goal reached
        if (current.isGoal()) {
            solutionFound = true;
            goalState = current;
            return;
        }
        
        // Stop if depth limit reached
        if (current.depth >= DEPTH_LIMIT) {
            return;
        }
        
        String key = current.getKey();
        if (visited.contains(key)) return;
        visited.add(key);
        
        // Generate all possible next states
        List<State> nextStates = generateNextStates(current);
        
        for (State next : nextStates) {
            if (!visited.contains(next.getKey())) {
                dls(next, visited);
                if (solutionFound) return;
            }
        }
        
        visited.remove(key);
    }
    
    static List<State> generateNextStates(State current) {
        List<State> nextStates = new ArrayList<>();
        int j1 = current.jug1;
        int j2 = current.jug2;
        int newDepth = current.depth + 1;
        
        // 1. Fill 4L jug
        if (j1 < CAP1) {
            nextStates.add(new State(CAP1, j2, current, 
                "Fill 4L jug: " + j1 + " → " + CAP1, newDepth));
        }
        
        // 2. Fill 3L jug
        if (j2 < CAP2) {
            nextStates.add(new State(j1, CAP2, current, 
                "Fill 3L jug: " + j2 + " → " + CAP2, newDepth));
        }
        
        // 3. Empty 4L jug
        if (j1 > 0) {
            nextStates.add(new State(0, j2, current, 
                "Empty 4L jug: " + j1 + " → 0", newDepth));
        }
        
        // 4. Empty 3L jug
        if (j2 > 0) {
            nextStates.add(new State(j1, 0, current, 
                "Empty 3L jug: " + j2 + " → 0", newDepth));
        }
        
        // 5. Pour from 4L to 3L
        if (j1 > 0 && j2 < CAP2) {
            int pour = Math.min(j1, CAP2 - j2);
            nextStates.add(new State(j1 - pour, j2 + pour, current, 
                "Pour " + pour + "L from 4L → 3L", newDepth));
        }
        
        // 6. Pour from 3L to 4L
        if (j2 > 0 && j1 < CAP1) {
            int pour = Math.min(j2, CAP1 - j1);
            nextStates.add(new State(j1 + pour, j2 - pour, current, 
                "Pour " + pour + "L from 3L → 4L", newDepth));
        }
        
        return nextStates;
    }
    
    static void printSolution() {
        if (!solutionFound) {
            System.out.println("No solution found within depth limit " + DEPTH_LIMIT);
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
        System.out.println("Maximum depth reached: " + (step - 1));
    }
    
    public static void main(String[] args) {
        System.out.println("=== WATER-JAR PROBLEM with DEPTH-LIMITED SEARCH ===");
        System.out.println("Jug capacities: 4L and 3L");
        System.out.println("Goal: Get 2 liters in the 4L jug");
        System.out.println("Depth Limit: " + DEPTH_LIMIT + "\n");
        
        State start = new State(0, 0, null, "START", 0);
        Set<String> visited = new HashSet<>();
        
        dls(start, visited);
        
        printSolution();
        
        // Show what depths were explored
        System.out.println("\n--- Search completed ---");
        System.out.println("Depth limit of " + DEPTH_LIMIT + " means only states within");
        System.out.println("3 moves from start were explored.");
        if (!solutionFound) {
            System.out.println("\nTry increasing depth limit to find solution.");
            System.out.println("(The actual solution requires 6-7 steps)");
        }
    }
}