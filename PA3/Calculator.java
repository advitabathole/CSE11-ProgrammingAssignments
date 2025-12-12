/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * Sources Used: CSE 11 Notes
 *
 * PA3 - Calculator
 * Implements helper methods for arithmetic on strings.
 */
public class Calculator {

    // Constants
    private static final char DECIMAL_POINT = '.';
    private static final char ZERO_CHAR = '0';
    private static final int TEN = 10;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int FOUR = 4;

    private static final String ZERO_STR = "0";
    private static final String ZERO_RESULT = "0.0";
    private static final String DOT_STR = ".";
    private static final String DOT_ZERO = ".0";
    private static final String RE_LEAD = "^0+";
    private static final String RE_TRAIL = "0+$";

    // Test constants
    private static final String TEST_NUM1 = "00005.05";
    private static final String TEST_NUM2 = "10.050";
    private static final String TEST_NUM3 = "4.02";
    private static final String TEST_NUM4 = "0.0050";
    private static final String TEST_NUM5 = "100";
    private static final String TEST_NUM6 = "2.5";

    /** Public empty constructor. */
    public Calculator() {}

    /**
     * Return the whole number part of the number.
     * @param number input string
     * @return substring before decimal, keep leading zeros
     */
    public static String extractWholeNumber(String number) {
        if (number == null || number.isEmpty()) {
            return "";
        }
        int decimalIndex = number.indexOf(DECIMAL_POINT);
        if (decimalIndex == 0) {
            return "";
        } else if (decimalIndex > 0) {
            return number.substring(0, decimalIndex);
        }
        return number;
    }

    /**
     * Return the decimal part of the number.
     * @param number input string
     * @return substring after decimal, keep trailing zeros
     */
    public static String extractDecimal(String number) {
        if (number == null || number.isEmpty()) {
            return "";
        }
        int decimalIndex = number.indexOf(DECIMAL_POINT);
        if (decimalIndex == -1 || decimalIndex == number.length() - ONE) {
            return "";
        }
        return number.substring(decimalIndex + ONE);
    }

    /**
     * Prepend zeros to the left of number.
     * @param number input string
     * @param numZeros how many zeros to add
     * @return new string
     */
    public static String prependZeros(String number, int numZeros) {
        if (number == null) return null;
        if (numZeros <= 0) return number;

        StringBuilder zeroBuilder = new StringBuilder();
        for (int i = 0; i < numZeros; i++) {
            zeroBuilder.append(ZERO_CHAR);
        }
        zeroBuilder.append(number);
        return zeroBuilder.toString();
    }

    /**
     * Append zeros to the right of number.
     * @param number input string
     * @param numZeros how many zeros to add
     * @return new string
     */
    public static String appendZeros(String number, int numZeros) {
        if (number == null) return null;
        if (numZeros <= 0) return number;

        StringBuilder zeroBuilder = new StringBuilder(number);
        for (int i = 0; i < numZeros; i++) {
            zeroBuilder.append(ZERO_CHAR);
        }
        return zeroBuilder.toString();
    }

    /**
     * Format a numeric string: remove extra zeros and ensure decimal.
     * @param number input string
     * @return formatted string with at least one digit each side
     */
    public static String formatResult(String number) {
        if (number == null || number.isEmpty()) {
            return ZERO_RESULT;
        }

        if (!number.contains(DOT_STR)) {
            number = number + DOT_ZERO;
        }

        int decimalIndex = number.indexOf(DECIMAL_POINT);
        String wholePart = number.substring(0, decimalIndex);
        String decimalPart = number.substring(decimalIndex + ONE);

        wholePart = wholePart.replaceFirst(RE_LEAD, "");
        if (wholePart.isEmpty()) wholePart = ZERO_STR;

        decimalPart = decimalPart.replaceFirst(RE_TRAIL, "");
        if (decimalPart.isEmpty()) decimalPart = ZERO_STR;

        return wholePart + DOT_STR + decimalPart;
    }

    /**
     * Add two digit chars with carry and return the rightmost digit.
     * @param firstDigit first digit char
     * @param secondDigit second digit char
     * @param carryIn carry input
     * @return rightmost digit char of sum
     */
    public static char addDigits(char firstDigit, char secondDigit,
                                 boolean carryIn) {
        int firstValue = firstDigit - ZERO_CHAR;
        int secondValue = secondDigit - ZERO_CHAR;
        int carryValue = carryIn ? ONE : 0;
        int total = firstValue + secondValue + carryValue;
        int lastDigit = total % TEN;
        return (char) (ZERO_CHAR + lastDigit);
    }

    /**
     * Return true if adding digits produces a carry.
     * @param firstDigit first digit char
     * @param secondDigit second digit char
     * @param carryIn carry input
     * @return true if carry out
     */
    public static boolean carryOut(char firstDigit, char secondDigit,
                                   boolean carryIn) {
        int firstValue = firstDigit - ZERO_CHAR;
        int secondValue = secondDigit - ZERO_CHAR;
        int carryValue = carryIn ? ONE : 0;
        return (firstValue + secondValue + carryValue) >= TEN;
    }

    /**
     * Add two decimal strings and return formatted sum.
     * @param firstNumber first number string
     * @param secondNumber second number string
     * @return sum as formatted string
     */
    public static String add(String firstNumber, String secondNumber) {
        String firstWhole = extractWholeNumber(firstNumber);
        String secondWhole = extractWholeNumber(secondNumber);
        String firstDecimal = extractDecimal(firstNumber);
        String secondDecimal = extractDecimal(secondNumber);

        int maxDecimalLen = Math.max(firstDecimal.length(),
                                     secondDecimal.length());
        firstDecimal = appendZeros(firstDecimal,
                                   maxDecimalLen - firstDecimal.length());
        secondDecimal = appendZeros(secondDecimal,
                                    maxDecimalLen - secondDecimal.length());

        int maxWholeLen = Math.max(firstWhole.length(), secondWhole.length());
        firstWhole = prependZeros(firstWhole,
                                  maxWholeLen - firstWhole.length());
        secondWhole = prependZeros(secondWhole,
                                   maxWholeLen - secondWhole.length());

        StringBuilder decimalSum = new StringBuilder();
        boolean carry = false;

        for (int i = maxDecimalLen - ONE; i >= 0; i--) {
            char digitSum = addDigits(firstDecimal.charAt(i),
                                      secondDecimal.charAt(i), carry);
            carry = carryOut(firstDecimal.charAt(i),
                             secondDecimal.charAt(i), carry);
            decimalSum.insert(0, digitSum);
        }

        StringBuilder wholeSum = new StringBuilder();
        for (int i = maxWholeLen - ONE; i >= 0; i--) {
            char digitSum = addDigits(firstWhole.charAt(i),
                                      secondWhole.charAt(i), carry);
            carry = carryOut(firstWhole.charAt(i),
                             secondWhole.charAt(i), carry);
            wholeSum.insert(0, digitSum);
        }

        if (carry) {
            wholeSum.insert(0, (char) (ZERO_CHAR + ONE));
        }

        return formatResult(wholeSum + DOT_STR + decimalSum);
    }

    /**
     * Multiply number by numTimes using repeated addition.
     * @param number number as string
     * @param numTimes times to add
     * @return product formatted as string
     */
    public static String multiply(String number, int numTimes) {
        if (numTimes <= 0) return ZERO_RESULT;

        String totalResult = ZERO_RESULT;
        for (int i = 0; i < numTimes; i++) {
            totalResult = add(totalResult, number);
        }
        return formatResult(totalResult);
    }

    /**
     * Private quick test method, not part of public API.
     * @param args command line args
     */
    private static void main(String[] args) {
        System.out.println(add(TEST_NUM1, TEST_NUM2));
        System.out.println(add(TEST_NUM3, TEST_NUM4));
        System.out.println(multiply(TEST_NUM5, TWO));
        System.out.println(multiply(TEST_NUM6, FOUR));
    }
}


public class Calculator {

    public static String extractWholeNumber(String number) {
        int i = number.indexOf(".");

        String output = str.substring(0,i);

        return output;
    }

    public static String add(String firstNumber, String secondNumber) {
        double n = Double.parseDouble(firstNumber);
        double o = Double.parseDouble(secondNumber);

        double p = n + o;

        String done = String.valueOf(p);

        return done;
    }


    public static String multiply (String number, int numTimes) {
        if (numTimes <= 0) {
            return "0.0";
        }
        int o = Integer.parseInt(number);
        int total = 0;

        for (int i = 0; i < numTimes; i++) {
            total += o;
        }

        return String.valueOf(total);
    }
}