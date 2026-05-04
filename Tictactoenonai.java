import java.util.Scanner;
import java.util.Random;

public class Tictactoenonai{
    static char[][] board = new char[3][3];
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

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
            
            computerRandomMove();
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

    static void computerRandomMove() {
        int row, col;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (board[row][col] != ' ');
        board[row][col] = 'O';
        System.out.println("Computer played at position " + (row * 3 + col + 1));
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
