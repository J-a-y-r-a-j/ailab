import java.util.*;

public class Bfs8puzzle {
    static class State {
        int[][] puzzle;
        int emptyRow, emptyCol;
        List<String> path;
        int moves;

        State(int[][] puzzle, int emptyRow, int emptyCol, List<String> path, int moves) {
            this.puzzle = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    this.puzzle[i][j] = puzzle[i][j];
                }
            }
            this.emptyRow = emptyRow;
            this.emptyCol = emptyCol;
            this.path = new ArrayList<>(path);
            this.moves = moves;
        }

        String getStateKey() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(puzzle[i][j]);
                }
            }
            return sb.toString();
        }

        void display() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (puzzle[i][j] == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(puzzle[i][j] + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Initial state
        int[][] initial = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };

        // Goal state
        int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        bfsEightPuzzle(initial, goal);
    }

    static void bfsEightPuzzle(int[][] initial, int[][] goal) {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        // Find empty position in initial state
        int emptyRow = 0, emptyCol = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (initial[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }

        State startState = new State(initial, emptyRow, emptyCol, new ArrayList<>(), 0);
        String startKey = startState.getStateKey();

        if (startKey.equals(getGoalKey(goal))) {
            System.out.println("Initial state is already the goal!");
            return;
        }

        queue.add(startState);
        visited.add(startKey);

        int statesExplored = 0;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            statesExplored++;

            // Generate next states by moving empty space
            List<State> nextStates = generateNextStates(current, goal);

            for (State next : nextStates) {
                String nextKey = next.getStateKey();

                if (nextKey.equals(getGoalKey(goal))) {
                    System.out.println("Solution found!");
                    System.out.println("Total moves: " + next.moves);
                    System.out.println("States explored: " + statesExplored);
                    System.out.println("\nSteps:\n");
                    for (int i = 0; i < next.path.size(); i++) {
                        System.out.println((i + 1) + ". " + next.path.get(i));
                    }
                    System.out.println("\nFinal state:");
                    next.display();
                    return;
                }

                if (!visited.contains(nextKey)) {
                    visited.add(nextKey);
                    queue.add(next);
                }
            }
        }

        System.out.println("No solution found!");
    }

    static List<State> generateNextStates(State current, int[][] goal) {
        List<State> nextStates = new ArrayList<>();
        int row = current.emptyRow;
        int col = current.emptyCol;

        // Four directions: up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] directionNames = {"Up", "Down", "Left", "Right"};

        for (int d = 0; d < 4; d++) {
            int newRow = row + directions[d][0];
            int newCol = col + directions[d][1];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
                // Create new puzzle by swapping
                int[][] newPuzzle = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        newPuzzle[i][j] = current.puzzle[i][j];
                    }
                }

                // Swap empty space with adjacent tile
                int tile = newPuzzle[newRow][newCol];
                newPuzzle[row][col] = tile;
                newPuzzle[newRow][newCol] = 0;

                List<String> newPath = new ArrayList<>(current.path);
                newPath.add("Move " + tile + " " + directionNames[d] + " → Empty at (" + newRow + ", " + newCol + ")");

                nextStates.add(new State(newPuzzle, newRow, newCol, newPath, current.moves + 1));
            }
        }

        return nextStates;
    }

    static String getGoalKey(int[][] goal) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(goal[i][j]);
            }
        }
        return sb.toString();
    }
}
