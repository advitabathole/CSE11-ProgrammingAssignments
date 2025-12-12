/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * Leopard critter with shared confidence across all Leopards and Ocelots.
 * Moves toward "." or "Patrick" neighbors. Attack and eating behavior
 * depend on confidence. Shared confidence is updated via win/lose and reset.
 */

import java.awt.Color;

/** Leopard - "Lpd". */
public class Leopard extends Feline {

    /** Shared confidence among all Leopards and Ocelots. */
    protected static int confidence = 0;

    /** Display name constant. */
    private static final String SPECIES_NAME = "Lpd";

    /** Opponent constants. */
    private static final String STARFISH = "Patrick";
    private static final String TURTLE = "Tu";
    private static final String EMPTY = ".";

    /** Maximum confidence value. */
    private static final int MAX_CONFIDENCE = 10;

    /** Random attack choice upper bound. */
    private static final int ATTACK_CHOICES = 3;

    /** Confidence multiplier for eating chance. */
    private static final int EAT_MULTIPLIER = 10;

    /** Confidence threshold for guaranteed POUNCE. */
    private static final int CONFIDENCE_THRESHOLD = 5;

    /** Constant for Random nextInt range when eating. */
    private static final int RANDOM_EAT_RANGE = 100;

    /** Constructor sets display name. */
    public Leopard() {
        super();
        this.displayName = SPECIES_NAME;
    }

    /**
     * Returns the display color of the Leopard.
     * @return the color of the Leopard (red)
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }

    /**
     * Moves toward first "." or "Patrick" neighbor, else behaves like Feline.
     * @return the direction to move
     */
    @Override
    public Direction getMove() {
        if (info.getNeighbor(Direction.NORTH).equals(EMPTY)
                || info.getNeighbor(Direction.NORTH).equals(STARFISH)) {
            return Direction.NORTH;
        }
        if (info.getNeighbor(Direction.SOUTH).equals(EMPTY)
                || info.getNeighbor(Direction.SOUTH).equals(STARFISH)) {
            return Direction.SOUTH;
        }
        if (info.getNeighbor(Direction.EAST).equals(EMPTY)
                || info.getNeighbor(Direction.EAST).equals(STARFISH)) {
            return Direction.EAST;
        }
        if (info.getNeighbor(Direction.WEST).equals(EMPTY)
                || info.getNeighbor(Direction.WEST).equals(STARFISH)) {
            return Direction.WEST;
        }

        return super.getMove();
    }

    /**
     * Determines whether to eat based on confidence.
     * @return true if the Leopard eats, false otherwise
     */
    @Override
    public boolean eat() {
        int chancePct = confidence * EAT_MULTIPLIER;
        return random.nextInt(RANDOM_EAT_RANGE) < chancePct;
    }

    /** Increments confidence on win (max MAX_CONFIDENCE). */
    @Override
    public void win() {
        if (confidence < MAX_CONFIDENCE) {
            confidence++;
        }
    }

    /** Decrements confidence on lose (min 0). */
    @Override
    public void lose() {
        if (confidence > 0) {
            confidence--;
        }
    }

    /**
     * Returns attack type based on opponent or confidence.
     * @param opponent the critter to attack
     * @return the type of attack (POUNCE or generated)
     */
    @Override
    public Attack getAttack(String opponent) {
        if (opponent.equals(TURTLE) || confidence > CONFIDENCE_THRESHOLD) {
            return Attack.POUNCE;
        }
        return generateAttack(opponent);
    }

    /**
     * Helper method to choose an attack.
     * @param opponent the critter to attack
     * @return the type of attack (FORFEIT, POUNCE, SCRATCH, or ROAR)
     */
    protected Attack generateAttack(String opponent) {
        if (opponent.equals(STARFISH)) {
            return Attack.FORFEIT;
        }
        int choice = random.nextInt(ATTACK_CHOICES);
        switch (choice) {
            case 0:
                return Attack.POUNCE;
            case 1:
                return Attack.SCRATCH;
            default:
                return Attack.ROAR;
        }
    }

    /** Resets shared confidence to 0. */
    @Override
    public void reset() {
        confidence = 0;
    }
}
