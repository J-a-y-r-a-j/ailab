import java.util.Scanner;

public class Tictactoeai {
    static char[][] board = new char[3][3];
    static Scanner sc = new Scanner(System.in);
    static final int MAX = 10;
    static final int MIN = -10;

    public static void main(String[] args) {
        initializeBoard();
        
        while (true) {
            displayBoard();
            
            playerMove();
            if (checkWin('X')) {
                displayBoard();
                System.out.println("You win!");
                break;
            }
            if (isBoardFull()) {
                displayBoard();
                System.out.println("It's a draw!");
                break;
            }
            
            computerAIMove();
            if (checkWin('O')) {
                displayBoard();
                System.out.println("Computer wins!");
                break;
            }
            if (isBoardFull()) {
                displayBoard();
                System.out.println("It's a draw!");
                break;
            }
        }
    }

    static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    static void displayBoard() {
        System.out.println("\n " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("-----------");
        System.out.println(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("-----------");
        System.out.println(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + "\n");
    }

    static void playerMove() {
        while (true) {
            System.out.print("Enter position (1-9): ");
            int pos = sc.nextInt();
            if (pos < 1 || pos > 9) {
                System.out.println("Invalid input!");
                continue;
            }
            int row = (pos - 1) / 3;
            int col = (pos - 1) % 3;
            if (board[row][col] != ' ') {
                System.out.println("Position already taken!");
                continue;
            }
            board[row][col] = 'X';
            break;
        }
    }

    static void computerAIMove() {
        int bestScore = MIN;
        int bestRow = -1, bestCol = -1;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int score = minimax(0, false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }
        board[bestRow][bestCol] = 'O';
        System.out.println("Computer played at position " + (bestRow * 3 + bestCol + 1));
    }

    static int minimax(int depth, boolean isMaximizing) {
        if (checkWin('O')) return MAX - depth;
        if (checkWin('X')) return MIN + depth;
        if (isBoardFull()) return 0;

        if (isMaximizing) {
            int bestScore = MIN;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int score = minimax(depth + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = MAX;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int score = minimax(depth + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
            return bestScore;
        }
    }

    static boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player)
                return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }

    static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }
}
