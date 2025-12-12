/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources Used: CSE 11 Notes
 * 
 * PA 2 - Password Security
 * Evaluates password strength, suggests stronger one.
 */
import java.util.Scanner;

/**
 * PasswordSecurity checks password strength, applies rules.
 */
public class PasswordSecurity {
    private static final String PROMPT_MESSAGE = 
        "Please enter a password: ";
    private static final String TOO_SHORT_MESSAGE = 
        "Password is too short";
    private static final String VERY_WEAK_MESSAGE =
        "Password strength: very weak";
    private static final String WEAK_MESSAGE = 
        "Password strength: weak";
    private static final String MEDIUM_MESSAGE = 
        "Password strength: medium";
    private static final String STRONG_MESSAGE = 
        "Password strength: strong";
    private static final String SUGGESTION_MESSAGE = 
        "Here is a suggested stronger password: ";
    private static final String PREPEND_TEXT = "Ucsd";
    private static final String APPEND_SYMBOLS = "#$";
    private static final int MINIMUM_LENGTH = 8;
    private static final int NUM_CATEGORIES_VERY_WEAK = 1;
    private static final int NUM_CATEGORIES_WEAK = 2;
    private static final int NUM_CATEGORIES_MEDIUM = 3;
    private static final int NUM_CATEGORIES_STRONG = 4;
    private static final int MIN_LETTER_COUNT = 2;
    private static final int INSERT_INTERVAL = 3;
    private static final int MODULO_VALUE = 10;
    private static final char INITIAL_LOWERCASE = 'a';

    /**
     * Prompts password, evaluates strength, suggests stronger.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt and read password
        System.out.print(PROMPT_MESSAGE);
        String password = scanner.nextLine();
        String originalPassword = password; 
        // For Rule 4 digit calculation

        // Check if password is too short
        if (password.length() < MINIMUM_LENGTH) {
            System.out.println(TOO_SHORT_MESSAGE);
            scanner.close();
            return;
        }

        // Check for character categories
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                hasUpper = true;
            } else if (Character.isLowerCase(currentChar)) {
                hasLower = true;
            } else if (Character.isDigit(currentChar)) {
                hasDigit = true;
            } else {
                hasSymbol = true;
            }
        }

        // Count categories
        int categories = 0;
        if (hasUpper) categories++;
        if (hasLower) categories++;
        if (hasDigit) categories++;
        if (hasSymbol) categories++;

        // Set password strength
        String passwordSecurity;
        if (categories == NUM_CATEGORIES_VERY_WEAK) {
            passwordSecurity = VERY_WEAK_MESSAGE;
        } else if (categories == NUM_CATEGORIES_WEAK) {
            passwordSecurity = WEAK_MESSAGE;
        } else if (categories == NUM_CATEGORIES_MEDIUM) {
            passwordSecurity = MEDIUM_MESSAGE;
        } else {
            passwordSecurity = STRONG_MESSAGE;
        }

        System.out.println(passwordSecurity);

        // Exit if password is strong
        if (categories == NUM_CATEGORIES_STRONG) {
            scanner.close();
            return;
        }

        // Count letters for Rule 1
        int numLetters = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLetter(password.charAt(i))) {
                numLetters++;
            }
        }

        // Apply rules(only one of Rules 1-3)
        // Rule 1: Fewer than 2 letters, prepend Ucsd
        if (numLetters < MIN_LETTER_COUNT) {
            password = PREPEND_TEXT + password;
        } 
        // Rule 2: No lowercase, change last uppercase
        else if (!hasLower) {
            for (int i = password.length() - 1; i >= 0; i--) {
                if (Character.isUpperCase(password.charAt(i))) {
                    char lower = Character.toLowerCase(password.charAt(i));
                    StringBuilder sb = new StringBuilder();
                    sb.append(password.substring(0, i));
                    sb.append(lower);
                    sb.append(password.substring(i+1));
                    password = sb.toString();
                    break;
                }
            }
        }
        // Rule 3:No uppercase, change highest lowercase
        else if (!hasUpper) {
            char highestLowercase = INITIAL_LOWERCASE;
            int index = -1;
            for (int i = 0; i < password.length(); i++) {
                char currentChar = password.charAt(i);
                if (Character.isLowerCase(currentChar) && currentChar > highestLowercase) {
                    highestLowercase = currentChar;
                    index = i;
                }
            }
            if (index != -1) {
                char upper = Character.toUpperCase(password.charAt(index));
                StringBuilder sb = new StringBuilder();
                sb.append(password.substring(0, index));
                sb.append(upper);
                sb.append(password.substring(index+1));
                password = sb.toString();
            }
        }

        // Rule 4:No digits, insert digit every 3 chars
        if (!hasDigit) {
            int insertDigit = originalPassword.length() % MODULO_VALUE;
            StringBuilder newPassword = new StringBuilder();
            for (int i = 0; i < password.length(); i++) {
                newPassword.append(password.charAt(i));
                if ((i + 1) % INSERT_INTERVAL == 0 
                    && i < password.length() - 1) {
                    newPassword.append(insertDigit); 
                    // Insert digit, not at end
                }
            }
            if (password.length() % INSERT_INTERVAL == 0) {
                newPassword.append(insertDigit); 
                // Append if length divisible by 3
            }
            password = newPassword.toString();
        }

        // Rule 5: No symbols, append "#$"
        if (!hasSymbol) {
            password = password + APPEND_SYMBOLS;
        }

        System.out.println(
            SUGGESTION_MESSAGE + password
        );
        scanner.close();
    }
}