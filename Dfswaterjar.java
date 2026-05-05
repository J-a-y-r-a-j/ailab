import java.util.*;

class Dfswaterjar {
    
    static class State {
        int a, b;  // a: 4L jug, b: 3L jug
        String action;
        
        State(int a, int b, String action) {
            this.a = a;
            this.b = b;
            this.action = action;
        }
        
        boolean isGoal() {
            return a == 2;
        }
        
        String key() {
            return a + "," + b;
        }
    }
    
    static final int CAP_A = 4;
    static final int CAP_B = 3;
    
    static Map<String, String> parent = new HashMap<>();
    static Map<String, String> actionMap = new HashMap<>();
    
    public static boolean dfs(State current, Set<String> visited, int depth, int maxDepth) {
        if (depth > maxDepth) return false;
        
        if (current.isGoal()) {
            return true;
        }
        
        String key = current.key();
        if (visited.contains(key)) return false;
        visited.add(key);
        
        // Generate all 6 possible moves
        State[] nextStates = {
            new State(CAP_A, current.b, "Fill 4L jug"),
            new State(current.a, CAP_B, "Fill 3L jug"),
            new State(0, current.b, "Empty 4L jug"),
            new State(current.a, 0, "Empty 3L jug"),
            new State(current.a - Math.min(current.a, CAP_B - current.b), 
                     current.b + Math.min(current.a, CAP_B - current.b), 
                     "Pour 4L -> 3L"),
            new State(current.a + Math.min(current.b, CAP_A - current.a),
                     current.b - Math.min(current.b, CAP_A - current.a),
                     "Pour 3L -> 4L")
        };
        
        for (State next : nextStates) {
            // Fix negative values
            next.a = Math.max(0, next.a);
            next.b = Math.max(0, next.b);
            next.a = Math.min(CAP_A, next.a);
            next.b = Math.min(CAP_B, next.b);
            
            String nextKey = next.key();
            if (!visited.contains(nextKey)) {
                parent.put(nextKey, key);
                actionMap.put(nextKey, next.action);
                if (dfs(next, visited, depth + 1, maxDepth)) {
                    return true;
                }
            }
        }
        
        visited.remove(key);
        return false;
    }
    
    public static void printPath(String goalKey, State start) {
        List<String> path = new ArrayList<>();
        String current = goalKey;
        
        while (current != null && !current.equals(start.key())) {
            path.add(current);
            current = parent.get(current);
        }
        
        Collections.reverse(path);
        
        System.out.println("Start: 4L=0, 3L=0");
        int step = 1;
        for (String state : path) {
            String[] parts = state.split(",");
            System.out.println("Step " + step + ": " + actionMap.get(state));
            System.out.println("        4L=" + parts[0] + ", 3L=" + parts[1]);
            step++;
        }
    }
    
    public static void main(String[] args) {
        State start = new State(0, 0, "Start");
        Set<String> visited = new HashSet<>();
        
        System.out.println("Solving Water-Jar (4L, 3L, Goal: 2L in 4L jug) using DFS\n");
        
        if (dfs(start, visited, 0, 20)) {
            System.out.println("Solution found!\n");
            printPath("2," + (CAP_B - (CAP_B - (4-2))), start); // Simplified: just print the path
        } else {
            System.out.println("No solution found");
        }
    }
}