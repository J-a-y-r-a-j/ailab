import java.util.*;

public class Bfsmaze {
    static class State {
        int row, col;
        List<String> path;

        State(int row, int col, List<String> path) {
            this.row = row;
            this.col = col;
            this.path = new ArrayList<>(path);
        }
    }

    public static void main(String[] args) {
        // 0 = open space, 1 = wall, S = start, G = goal
        char[][] maze = {
            {'S', '0', '1', '0', '0'},
            {'1', '0', '1', '0', '1'},
            {'0', '0', '0', '0', '0'},
            {'0', '1', '1', '1', '0'},
            {'0', '0', '0', '0', 'G'}
        };

        System.out.println("Initial Maze:");
        displayMaze(maze);

        solveMaze(maze);
    }

    static void solveMaze(char[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;

        // Find start and goal positions
        int startRow = 0, startCol = 0;
        int goalRow = 0, goalCol = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                }
                if (maze[i][j] == 'G') {
                    goalRow = i;
                    goalCol = j;
                }
            }
        }

        Queue<State> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];

        List<String> initialPath = new ArrayList<>();
        initialPath.add("Start at (" + startRow + ", " + startCol + ")");

        State startState = new State(startRow, startCol, initialPath);
        queue.add(startState);
        visited[startRow][startCol] = true;

        int statesExplored = 0;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            statesExplored++;

            // Check if goal reached
            if (current.row == goalRow && current.col == goalCol) {
                System.out.println("\n✓ Solution found!");
                System.out.println("Total steps: " + (current.path.size() - 1));
                System.out.println("Cells explored: " + statesExplored);
                System.out.println("\nPath:\n");
                for (int i = 0; i < current.path.size(); i++) {
                    System.out.println((i) + ". " + current.path.get(i));
                }
                System.out.println("\nFinal Maze with path:");
                displayMazeWithPath(maze, current);
                return;
            }

            // Generate next states (4 directions: up, down, left, right)
            List<State> nextStates = generateNextStates(current, maze, visited);

            for (State next : nextStates) {
                if (!visited[next.row][next.col]) {
                    visited[next.row][next.col] = true;
                    queue.add(next);
                }
            }
        }

        System.out.println("No solution found!");
    }

    static List<State> generateNextStates(State current, char[][] maze, boolean[][] visited) {
        List<State> nextStates = new ArrayList<>();
        int rows = maze.length;
        int cols = maze[0].length;

        // Four directions: up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] directionNames = {"Up", "Down", "Left", "Right"};

        for (int d = 0; d < 4; d++) {
            int newRow = current.row + directions[d][0];
            int newCol = current.col + directions[d][1];

            // Check bounds
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                // Check if not wall and not visited
                if (maze[newRow][newCol] != '1' && !visited[newRow][newCol]) {
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add("Move " + directionNames[d] + " to (" + newRow + ", " + newCol + ")");
                    nextStates.add(new State(newRow, newCol, newPath));
                }
            }
        }

        return nextStates;
    }

    static void displayMaze(char[][] maze) {
        System.out.println();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == '1') {
                    System.out.print("█ ");  // Wall
                } else if (maze[i][j] == 'S') {
                    System.out.print("S ");  // Start
                } else if (maze[i][j] == 'G') {
                    System.out.print("G ");  // Goal
                } else {
                    System.out.print("  ");  // Open space
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    static void displayMazeWithPath(char[][] maze, State solution) {
        System.out.println();
        int rows = maze.length;
        int cols = maze[0].length;
        char[][] mazeWithPath = new char[rows][cols];

        // Copy original maze
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mazeWithPath[i][j] = maze[i][j];
            }
        }

        // Extract coordinates from path and mark them
        for (String step : solution.path) {
            // Parse coordinates from step (e.g., "Start at (0, 0)" or "Move Up to (1, 2)")
            int lastOpenParen = step.lastIndexOf('(');
            int comma = step.lastIndexOf(',');
            int closeParen = step.lastIndexOf(')');

            if (lastOpenParen != -1 && comma != -1 && closeParen != -1) {
                try {
                    int row = Integer.parseInt(step.substring(lastOpenParen + 1, comma).trim());
                    int col = Integer.parseInt(step.substring(comma + 1, closeParen).trim());
                    if (mazeWithPath[row][col] != 'S' && mazeWithPath[row][col] != 'G') {
                        mazeWithPath[row][col] = '*';  // Path marker
                    }
                } catch (Exception e) {
                    // Skip parsing errors
                }
            }
        }

        // Display maze with path
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mazeWithPath[i][j] == '1') {
                    System.out.print("█ ");
                } else if (mazeWithPath[i][j] == 'S') {
                    System.out.print("S ");
                } else if (mazeWithPath[i][j] == 'G') {
                    System.out.print("G ");
                } else if (mazeWithPath[i][j] == '*') {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
