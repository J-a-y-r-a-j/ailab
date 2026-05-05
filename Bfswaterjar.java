import java.util.*;

public class Bfswaterjar {
    static class State {
        int jar4;  // Current water in 4-liter jar
        int jar3;  // Current water in 3-liter jar
        List<String> path;

        State(int jar4, int jar3, List<String> path) {
            this.jar4 = jar4;
            this.jar3 = jar3;
            this.path = new ArrayList<>(path);
        }

        @Override
        public boolean equals(Object obj) {
            State other = (State) obj;
            return this.jar4 == other.jar4 && this.jar3 == other.jar3;
        }

        @Override
        public int hashCode() {
            return Objects.hash(jar4, jar3);
        }
    }

    public static void main(String[] args) {
        int goal = 2;
        bfsWaterJar(goal);
    }

    static void bfsWaterJar(int goal) {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        // Start state: both jars empty
        List<String> initialPath = new ArrayList<>();
        State startState = new State(0, 0, initialPath);
        
        queue.add(startState);
        visited.add("0,0");

        while (!queue.isEmpty()) {
            State current = queue.poll();

            // Check if goal is reached
            if (current.jar4 == goal) {
                System.out.println("Solution found!");
                System.out.println("Steps to reach " + goal + " liters in 4-liter jar:\n");
                for (int i = 0; i < current.path.size(); i++) {
                    System.out.println((i + 1) + ". " + current.path.get(i));
                }
                System.out.println("\nFinal State: 4-liter jar = " + current.jar4 + " L, 3-liter jar = " + current.jar3 + " L");
                return;
            }

            // Generate all possible next states
            List<State> nextStates = generateNextStates(current);

            for (State next : nextStates) {
                String stateKey = next.jar4 + "," + next.jar3;
                if (!visited.contains(stateKey)) {
                    visited.add(stateKey);
                    queue.add(next);
                }
            }
        }

        System.out.println("No solution found!");
    }

    static List<State> generateNextStates(State current) {
        List<State> nextStates = new ArrayList<>();
        int jar4 = current.jar4;
        int jar3 = current.jar3;
        List<String> path = current.path;

        // 1. Fill 4-liter jar
        if (jar4 < 4) {
            List<String> newPath = new ArrayList<>(path);
            newPath.add("Fill 4-liter jar → (4, " + jar3 + ")");
            nextStates.add(new State(4, jar3, newPath));
        }

        // 2. Fill 3-liter jar
        if (jar3 < 3) {
            List<String> newPath = new ArrayList<>(path);
            newPath.add("Fill 3-liter jar → (" + jar4 + ", 3)");
            nextStates.add(new State(jar4, 3, newPath));
        }

        // 3. Empty 4-liter jar
        if (jar4 > 0) {
            List<String> newPath = new ArrayList<>(path);
            newPath.add("Empty 4-liter jar → (0, " + jar3 + ")");
            nextStates.add(new State(0, jar3, newPath));
        }

        // 4. Empty 3-liter jar
        if (jar3 > 0) {
            List<String> newPath = new ArrayList<>(path);
            newPath.add("Empty 3-liter jar → (" + jar4 + ", 0)");
            nextStates.add(new State(jar4, 0, newPath));
        }

        // 5. Pour 4-liter into 3-liter
        if (jar4 > 0 && jar3 < 3) {
            int pour = Math.min(jar4, 3 - jar3);
            int newJar4 = jar4 - pour;
            int newJar3 = jar3 + pour;
            List<String> newPath = new ArrayList<>(path);
            newPath.add("Pour 4-liter into 3-liter → (" + newJar4 + ", " + newJar3 + ")");
            nextStates.add(new State(newJar4, newJar3, newPath));
        }

        // 6. Pour 3-liter into 4-liter
        if (jar3 > 0 && jar4 < 4) {
            int pour = Math.min(jar3, 4 - jar4);
            int newJar4 = jar4 + pour;
            int newJar3 = jar3 - pour;
            List<String> newPath = new ArrayList<>(path);
            newPath.add("Pour 3-liter into 4-liter → (" + newJar4 + ", " + newJar3 + ")");
            nextStates.add(new State(newJar4, newJar3, newPath));
        }

        return nextStates;
    }
}
