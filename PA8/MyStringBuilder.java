/*
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 notes, Piazza
 *
 * A custom StringBuilder using a linked list of CharNodes.
 */
public class MyStringBuilder {

    // ===========================
    // Constants for error messages
    // ===========================
    private static final String NULL_STRING_ERROR =
        "Input string cannot be null";
    private static final String NULL_MSB_ERROR =
        "Other MyStringBuilder cannot be null";
    private static final String INVALID_INDEX_ERROR =
        "Invalid substring indices";

    // ===========================
    // Instance Variables
    // ===========================
    private CharNode start; // reference to first node
    private CharNode end;   // reference to last node
    private int length;     // number of characters

    // ===========================
    // Constructors
    // ===========================

    /**
     * Creates a MyStringBuilder with a single character.
     * @param ch character to initialize
     */
    public MyStringBuilder(char ch) {
        CharNode node = new CharNode(ch);
        this.start = node;
        this.end = node;
        this.length = 1;
    }

    /**
     * Creates a MyStringBuilder from a String.
     * @param str input string
     */
    public MyStringBuilder(String str) {
        if (str == null) {
            throw new NullPointerException(NULL_STRING_ERROR);
        }
        if (str.length() == 0) {
            this.start = null;
            this.end = null;
            this.length = 0;
            return;
        }

        this.start = new CharNode(str.charAt(0));
        CharNode currentNode = this.start;

        for (int i = 1; i < str.length(); i++) {
            CharNode nextNode = new CharNode(str.charAt(i));
            currentNode.setNext(nextNode);
            currentNode = nextNode;
        }

        this.end = currentNode;
        this.length = str.length();
    }

    /**
     * Creates a deep copy of another MyStringBuilder.
     * @param other the MyStringBuilder to copy
     */
    public MyStringBuilder(MyStringBuilder other) {
        if (other == null) {
            throw new NullPointerException(NULL_MSB_ERROR);
        }
        if (other.length == 0) {
            this.start = null;
            this.end = null;
            this.length = 0;
            return;
        }

        CharNode currentOther = other.start;
        this.start = new CharNode(currentOther.getData());
        CharNode currentThis = this.start;
        currentOther = currentOther.getNext();

        while (currentOther != null) {
            CharNode nextNode =
                new CharNode(currentOther.getData());
            currentThis.setNext(nextNode);
            currentThis = nextNode;
            currentOther = currentOther.getNext();
        }

        this.end = currentThis;
        this.length = other.length;
    }

    // ===========================
    // Methods
    // ===========================

    /**
     * Returns the number of characters.
     * @return length
     */
    public int length() {
        return this.length;
    }

    /**
     * Appends a single character.
     * @param ch character to append
     * @return updated MyStringBuilder
     */
    public MyStringBuilder append(char ch) {
        CharNode newNode = new CharNode(ch);

        if (this.length == 0) {
            this.start = newNode;
            this.end = newNode;
        } else {
            this.end.setNext(newNode);
            this.end = newNode;
        }

        this.length++;
        return this;
    }

    /**
     * Appends a String.
     * @param str String to append
     * @return updated MyStringBuilder
     */
    public MyStringBuilder append(String str) {
        if (str == null) {
            throw new NullPointerException(NULL_STRING_ERROR);
        }

        for (int i = 0; i < str.length(); i++) {
            this.append(str.charAt(i));
        }

        return this;
    }

    /**
     * Converts to a standard Java String.
     * @return string representation
     */
    public String toString() {
        if (this.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        CharNode currentNode = this.start;

        while (currentNode != null) {
            sb.append(currentNode.getData());
            currentNode = currentNode.getNext();
        }

        return sb.toString();
    }

    /**
     * Returns substring from startIdx to end.
     * @param startIdx starting index
     * @return substring
     */
    public String subString(int startIdx) {
        return subString(startIdx, this.length);
    }

    /**
     * Returns substring from startIdx to endIdx.
     * @param startIdx inclusive
     * @param endIdx exclusive
     * @return substring
     */
    public String subString(int startIdx, int endIdx) {
        if (startIdx < 0 ||
            startIdx > this.length ||
            endIdx < startIdx ||
            endIdx > this.length) {

            throw new IndexOutOfBoundsException(
                INVALID_INDEX_ERROR
            );
        }

        if (startIdx == endIdx) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        CharNode currentNode = this.start;

        for (int i = 0; i < startIdx; i++) {
            currentNode = currentNode.getNext();
        }

        for (int i = startIdx; i < endIdx; i++) {
            sb.append(currentNode.getData());
            currentNode = currentNode.getNext();
        }

        return sb.toString();
    }
}
