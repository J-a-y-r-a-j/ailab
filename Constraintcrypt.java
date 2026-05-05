import java.util.*;

class Constraintcrypt {
    
    // Example: SEND + MORE = MONEY
    //   S E N D
    // + M O R E
    // = M O N E Y
    
    static String word1 = "SEND";
    static String word2 = "MORE";
    static String result = "MONEY";
    
    static Set<Character> uniqueLetters = new LinkedHashSet<>();
    static Map<Character, Integer> mapping = new HashMap<>();
    static boolean[] usedDigits = new boolean[10];
    static int solutionsCount = 0;
    
    public static void solve() {
        // Collect all unique letters from all words
        for (char c : (word1 + word2 + result).toCharArray()) {
            uniqueLetters.add(c);
        }
        
        System.out.println("=== CRYPTARITHMETIC CSP SOLVER ===\n");
        System.out.println("Problem: " + word1 + " + " + word2 + " = " + result);
        System.out.println("Unique letters: " + uniqueLetters);
        System.out.println("\nSolving...\n");
        
        List<Character> letters = new ArrayList<>(uniqueLetters);
        backtrack(letters, 0);
        
        if (solutionsCount == 0) {
            System.out.println("No solution found!");
        } else {
            System.out.println("\nTotal solutions found: " + solutionsCount);
        }
    }
    
    static void backtrack(List<Character> letters, int index) {
        if (index == letters.size()) {
            if (isValid()) {
                solutionsCount++;
                printSolution();
            }
            return;
        }
        
        char letter = letters.get(index);
        
        // Try digits 0-9 for this letter
        for (int digit = 0; digit <= 9; digit++) {
            // Leading digits cannot be zero
            if (digit == 0 && isLeadingLetter(letter)) {
                continue;
            }
            
            if (!usedDigits[digit]) {
                usedDigits[digit] = true;
                mapping.put(letter, digit);
                backtrack(letters, index + 1);
                mapping.remove(letter);
                usedDigits[digit] = false;
            }
        }
    }
    
    static boolean isLeadingLetter(char letter) {
        // Check if letter appears as first character of any word
        return word1.charAt(0) == letter || 
               word2.charAt(0) == letter || 
               result.charAt(0) == letter;
    }
    
    static boolean isValid() {
        // Convert words to numbers
        int num1 = getNumber(word1);
        int num2 = getNumber(word2);
        int numResult = getNumber(result);
        
        return num1 + num2 == numResult;
    }
    
    static int getNumber(String word) {
        int num = 0;
        for (char c : word.toCharArray()) {
            num = num * 10 + mapping.get(c);
        }
        return num;
    }
    
    static void printSolution() {
        System.out.println("Solution #" + solutionsCount + ":");
        System.out.println("  " + word1 + " = " + getNumber(word1));
        System.out.println("  " + word2 + " = " + getNumber(word2));
        System.out.println("  " + result + " = " + getNumber(result));
        System.out.println("  " + getNumber(word1) + " + " + getNumber(word2) + " = " + getNumber(result));
        System.out.println("\n  Mapping:");
        for (Map.Entry<Character, Integer> entry : mapping.entrySet()) {
            System.out.println("    " + entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("----------------------------------------");
    }
    
    public static void main(String[] args) {
        solve();
    }
}