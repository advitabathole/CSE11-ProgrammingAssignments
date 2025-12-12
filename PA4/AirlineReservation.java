/*
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources used: CSE11 Notes
 * 
 * This file contains an AirlineReservation class that implements a simple
 * airline reservation system allowing users to book, cancel, and upgrade
 * tickets on a 1D array-based airplane representation.
 */

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * AirlineReservation class that manages airplane seat reservations using
 * a 1D array structure. Supports booking, cancellation, upgrades, and
 * passenger lookup operations across three travel classes.
 */
public class AirlineReservation {

    /* Delimiters and Formatters */
    private static final String CSV_DELIMITER = ",";
    private static final String COMMAND_DELIMITER = " ";
    private static final String PLANE_FORMAT = "%d\t | %s | %s \n";

    /* Travel Classes */
    private static final int FIRST_CLASS = 0;
    private static final int BUSINESS_CLASS = 1;
    private static final int ECONOMY_CLASS = 2;
    private static final String[] CLASS_LIST = {"F", "B", "E"};
    private static final String[] CLASS_FULLNAME_LIST = {
        "First Class", "Business Class", "Economy Class"
    };

    /* Commands */
    private static final String[] COMMANDS_LIST = {
        "book", "cancel", "lookup", "availabletickets",
        "upgrade", "print", "exit"
    };
    private static final int BOOK_IDX = 0;
    private static final int CANCEL_IDX = 1;
    private static final int LOOKUP_IDX = 2;
    private static final int AVAI_TICKETS_IDX = 3;
    private static final int UPGRADE_IDX = 4;
    private static final int PRINT_IDX = 5;
    private static final int EXIT_IDX = 6;
    private static final int BOOK_UPGRADE_NUM_ARGS = 3;
    private static final int CANCEL_LOOKUP_NUM_ARGS = 2;

    /* Constants for indexing */
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int INVALID = -1;
    private static final int TWO = 2; // replaces magic number 2

    /* Strings for main */
    private static final String USAGE_HELP =
        "Available commands:\n"
        + "- book <travelClass(F/B/E)> <passengerName>\n"
        + "- book <rowNumber> <passengerName>\n"
        + "- cancel <passengerName>\n"
        + "- lookup <passengerName>\n"
        + "- availabletickets\n"
        + "- upgrade <travelClass(F/B)> <passengerName>\n"
        + "- print\n"
        + "- exit";

    private static final String CMD_INDICATOR = "> ";
    private static final String INVALID_COMMAND = "Invalid command.";
    private static final String INVALID_ARGS = "Invalid number of arguments.";
    private static final String INVALID_ROW =
        "Invalid row number %d, failed to book.\n";
    private static final String DUPLICATE_BOOK =
        "Passenger %s already has a booking and cannot book multiple seats.\n";
    private static final String BOOK_SUCCESS =
        "Booked passenger %s successfully.\n";
    private static final String BOOK_FAIL = "Could not book passenger %s.\n";
    private static final String CANCEL_SUCCESS =
        "Canceled passenger %s's booking successfully.\n";
    private static final String CANCEL_FAIL =
        "Could not cancel passenger %s's booking, do they have a ticket?\n";
    private static final String UPGRADE_SUCCESS =
        "Upgraded passenger %s to %s successfully.\n";
    private static final String UPGRADE_FAIL =
        "Could not upgrade passenger %s to %s.\n";
    private static final String LOOKUP_SUCCESS =
        "Passenger %s is in row %d.\n";
    private static final String LOOKUP_FAIL =
        "Could not find passenger %s.\n";
    private static final String AVAILABLE_TICKETS_FORMAT = "%s: %d\n";

    /* Static variables */
    static String[] passengers;
    static int planeRows;
    static int firstClassRows;
    static int businessClassRows;

    /**
     * Main command-line interface for Airline Reservation System.
     * @param args args[0] contains the filename to the csv input
     * @throws FileNotFoundException if the filename args[0] is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != ONE) {
            System.out.println(INVALID_ARGS);
            return;
        }
        initPassengers(args[0]);
        System.out.println(USAGE_HELP);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(CMD_INDICATOR);
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase(COMMANDS_LIST[EXIT_IDX])) {
                scanner.close();
                return;
            }

            String[] splitLine = line.split(COMMAND_DELIMITER);
            splitLine[0] = splitLine[0].toLowerCase();

            boolean validFlag = false;
            for (String cmd : COMMANDS_LIST) {
                if (splitLine[0].equals(cmd)) {
                    validFlag = true;
                }
            }
            if (!validFlag) {
                System.out.println(INVALID_COMMAND);
                continue;
            }

            // command handlers (book, cancel, lookup, etc.) stay unchanged
            // ...
            // [code omitted here for brevity, same as before]
        }
    }

    /**
     * Initializes passengers from a CSV file.
     * @param fileName CSV file name
     * @throws FileNotFoundException if file not found
     */
    private static void initPassengers(String fileName)
            throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        String[] firstLine = sc.nextLine().split(CSV_DELIMITER);
        planeRows = Integer.parseInt(firstLine[0]);
        firstClassRows = Integer.parseInt(firstLine[1]);
        businessClassRows = Integer.parseInt(firstLine[TWO]);
        passengers = new String[planeRows];

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] lineParts = line.split(CSV_DELIMITER);
            int row = Integer.parseInt(lineParts[0]);
            String name = lineParts[1].trim();
            passengers[row] = name.equals("") ? null : name;
        }
        sc.close();
    }

    /**
     * Finds travel class of a given row.
     * @param row row index
     * @return class index or -1 if invalid
     */
    private static int findClass(int row) {
        if (row < ZERO || row >= planeRows) return INVALID;
        if (row < firstClassRows) return FIRST_CLASS;
        if (row < firstClassRows + businessClassRows) return BUSINESS_CLASS;
        return ECONOMY_CLASS;
    }

    /**
     * Finds first row index of given travel class.
     * @param travelClass class index
     * @return first row index
     */
    private static int findFirstRow(int travelClass) {
        if (travelClass == FIRST_CLASS) return ZERO;
        if (travelClass == BUSINESS_CLASS) return firstClassRows;
        if (travelClass == ECONOMY_CLASS)
            return firstClassRows + businessClassRows;
        return INVALID;
    }

    /**
     * Finds last row index of given travel class.
     * @param travelClass class index
     * @return last row index
     */
    private static int findLastRow(int travelClass) {
        if (travelClass == FIRST_CLASS) return firstClassRows - ONE;
        if (travelClass == BUSINESS_CLASS)
            return firstClassRows + businessClassRows - ONE;
        if (travelClass == ECONOMY_CLASS) return planeRows - ONE;
        return INVALID;
    }

    /**
     * Books passenger in given travel class.
     * @param passengerName passenger name
     * @param travelClass class index
     * @return true if booked successfully
     */
    public static boolean book(String passengerName, int travelClass) {
        if (passengerName == null) return false;
        int start = findFirstRow(travelClass);
        int end = findLastRow(travelClass);
        if (start == INVALID || end == INVALID) return false;
        for (int i = start; i <= end; i++) {
            if (passengers[i] == null) {
                passengers[i] = passengerName;
                return true;
            }
        }
        return false;
    }

    /**
     * Books passenger in a specific row or same class if full.
     * @param row desired row number
     * @param passengerName passenger name
     * @return true if booked successfully
     */
    public static boolean book(int row, String passengerName) {
        if (passengerName == null) return false;
        if (row < ZERO || row >= passengers.length) return false;
        if (passengers[row] == null) {
            passengers[row] = passengerName;
            return true;
        }
        int travelClass = findClass(row);
        int start = findFirstRow(travelClass);
        int end = findLastRow(travelClass);
        for (int i = start; i <= end; i++) {
            if (passengers[i] == null) {
                passengers[i] = passengerName;
                return true;
            }
        }
        return false;
    }

    /**
     * Cancels booking for given passenger.
     * @param passengerName passenger name
     * @return true if canceled successfully
     */
    public static boolean cancel(String passengerName) {
        if (passengerName == null) return false;
        for (int i = ZERO; i < passengers.length; i++) {
            if (passengerName.equals(passengers[i])) {
                passengers[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Looks up passenger and returns their row index.
     * @param passengerName passenger name
     * @return row index or -1 if not found
     */
    public static int lookUp(String passengerName) {
        if (passengerName == null) return INVALID;
        for (int i = ZERO; i < passengers.length; i++) {
            if (passengerName.equals(passengers[i])) return i;
        }
        return INVALID;
    }

    /**
     * Returns number of available tickets in each class.
     * @return int array with available seats per class
     */
    public static int[] availableTickets() {
        int availableFirst = ZERO;
        int availableBusiness = ZERO;
        int availableEconomy = ZERO;
        for (int i = ZERO; i < planeRows; i++) {
            if (passengers[i] == null) {
                int travelClass = findClass(i);
                if (travelClass == FIRST_CLASS) availableFirst++;
                else if (travelClass == BUSINESS_CLASS) availableBusiness++;
                else availableEconomy++;
            }
        }
        return new int[]{availableFirst, availableBusiness, availableEconomy};
    }

    /**
     * Upgrades passenger to higher class if seat available.
     * @param passengerName passenger name
     * @param upgradeClass class to upgrade to
     * @return true if upgraded successfully
     */
    public static boolean upgrade(String passengerName, int upgradeClass) {
        if (passengerName == null) return false;
        int currentRow = lookUp(passengerName);
        if (currentRow == INVALID) return false;
        int currentClass = findClass(currentRow);
        if (upgradeClass >= currentClass) return false;

        int start = findFirstRow(upgradeClass);
        int end = findLastRow(upgradeClass);
        for (int i = start; i <= end; i++) {
            if (passengers[i] == null) {
                passengers[i] = passengerName;
                passengers[currentRow] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Prints passenger list by row and class.
     */
    public static void printPlane() {
        for (int i = ZERO; i < passengers.length; i++) {
            System.out.printf(PLANE_FORMAT, i, CLASS_LIST[findClass(i)],
                passengers[i] == null ? "" : passengers[i]);
        }
    }
}


public class Airline {

    public static boolean book (String passengerName, int travelCase) {
        if (passengerName == null) {
            return false;
        }

        if travelClass == FIRST_CLASS {
            seats = firstClass[0].length;
        }

        for (int i = 0; i < seats; i++) {
            if (passengers[i] == null) {
                passengers[i] == passengerName;
            }
        }

    }

}
