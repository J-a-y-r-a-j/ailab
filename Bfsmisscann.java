import java.util.*;

class Bfsmisscann {
    
    // State representation: (missionaries_left, cannibals_left, boat_side, missionaries_right, cannibals_right)
    // boat_side: 0 = left, 1 = right
    static class State {
        int mLeft, cLeft, boat, mRight, cRight;
        State parent;
        String move;
        
        State(int mLeft, int cLeft, int boat, int mRight, int cRight, State parent, String move) {
            this.mLeft = mLeft;
            this.cLeft = cLeft;
            this.boat = boat;
            this.mRight = mRight;
            this.cRight = cRight;
            this.parent = parent;
            this.move = move;
        }
        
        boolean isValid() {
            // Check if cannibals outnumber missionaries on left or right
            if ((mLeft > 0 && cLeft > mLeft) || (mRight > 0 && cRight > mRight))
                return false;
            // Check if values are within bounds
            return mLeft >= 0 && cLeft >= 0 && mRight >= 0 && cRight >= 0;
        }
        
        boolean isGoal() {
            return mLeft == 0 && cLeft == 0;
        }
        
        String getStateKey() {
            return mLeft + "," + cLeft + "," + boat + "," + mRight + "," + cRight;
        }
        
        void print() {
            System.out.println("Step: " + move);
            System.out.println("Left bank: M=" + mLeft + " C=" + cLeft);
            System.out.println("Right bank: M=" + mRight + " C=" + cRight);
            System.out.println("Boat is on " + (boat == 0 ? "LEFT" : "RIGHT") + " side");
            System.out.println("----------------------------------------");
        }
    }
    
    // All possible moves: (missionaries_to_move, cannibals_to_move)
    static int[][] moves = {
        {1, 0}, {2, 0}, {0, 1}, {0, 2}, {1, 1}
    };
    
    public static void solveBFS() {
        // Initial state: all on left bank
        State initialState = new State(3, 3, 0, 0, 0, null, "Start");
        
        // BFS queue
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.add(initialState);
        visited.add(initialState.getStateKey());
        
        System.out.println("Solving Missionaries and Cannibals problem using BFS...\n");
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            // Check if goal state reached
            if (current.isGoal()) {
                System.out.println("\n SOLUTION FOUND! \n");
                printSolution(current);
                return;
            }
            
            // Generate all possible next states
            if (current.boat == 0) { // Boat on left side, moving to right
                for (int[] move : moves) {
                    int mMove = move[0];
                    int cMove = move[1];
                    
                    if (mMove <= current.mLeft && cMove <= current.cLeft && (mMove + cMove) <= 2) {
                        State next = new State(
                            current.mLeft - mMove,
                            current.cLeft - cMove,
                            1, // boat now on right
                            current.mRight + mMove,
                            current.cRight + cMove,
                            current,
                            "Move " + mMove + " missionary(s) and " + cMove + " cannibal(s) from LEFT to RIGHT"
                        );
                        
                        if (next.isValid() && !visited.contains(next.getStateKey())) {
                            visited.add(next.getStateKey());
                            queue.add(next);
                        }
                    }
                }
            } else { // Boat on right side, moving to left
                for (int[] move : moves) {
                    int mMove = move[0];
                    int cMove = move[1];
                    
                    if (mMove <= current.mRight && cMove <= current.cRight && (mMove + cMove) <= 2) {
                        State next = new State(
                            current.mLeft + mMove,
                            current.cLeft + cMove,
                            0, // boat now on left
                            current.mRight - mMove,
                            current.cRight - cMove,
                            current,
                            "Move " + mMove + " missionary(s) and " + cMove + " cannibal(s) from RIGHT to LEFT"
                        );
                        
                        if (next.isValid() && !visited.contains(next.getStateKey())) {
                            visited.add(next.getStateKey());
                            queue.add(next);
                        }
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
        
        int stepNumber = 0;
        while (!path.isEmpty()) {
            System.out.println("Step " + stepNumber + ":");
            path.pop().print();
            stepNumber++;
        }
        
        System.out.println("Total steps: " + (stepNumber - 1));
    }
    
    public static void main(String[] args) {
        solveBFS();
    }
}