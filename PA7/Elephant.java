/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * Elephant critter that moves toward a shared goal.
 * Shared static goalX/goalY is initialized to (0,0) in constructor.
 * Always eats. Mating increases level by LEVEL_UP.
 * Reset sets goal back to (0,0).
 */

import java.awt.Color;

/** Elephant - "El". */
public class Elephant extends Critter {

    /** Shared goal coordinates among all Elephants. */
    protected static int goalX;
    protected static int goalY;

    /** Display name constant. */
    private static final String SPECIES_NAME = "El";

    /** Level increase when mating. */
    private static final int LEVEL_UP = 2;

    /** Constructor initializes name and goal. */
    public Elephant() {
        super(SPECIES_NAME);
        goalX = 0;
        goalY = 0;
    }

    /**
     * Returns the display color of the Elephant.
     *
     * @return the color gray
     */
    @Override
    public Color getColor() {
        return Color.GRAY;
    }

    /**
     * Determines the next movement direction towards the current goal.
     * Picks a new random goal when the current goal is reached.
     *
     * @return direction to move
     */
    @Override
    public Direction getMove() {
        int currentX = info.getX();
        int currentY = info.getY();
        int dx = goalX - currentX;
        int dy = goalY - currentY;

        if (dx == 0 && dy == 0) {
            goalX = random.nextInt(info.getWidth());
            goalY = random.nextInt(info.getHeight());
            dx = goalX - currentX;
            dy = goalY - currentY;
        }

        if (Math.abs(dx) >= Math.abs(dy)) {
            return (dx > 0) ? Direction.EAST : Direction.WEST;
        } else {
            return (dy > 0) ? Direction.SOUTH : Direction.NORTH;
        }
    }

    /**
     * Elephants always eat.
     *
     * @return true
     */
    @Override
    public boolean eat() {
        return true;
    }

    /**
     * Mating increases the elephant's level by LEVEL_UP.
     */
    @Override
    public void mate() {
        incrementLevel(LEVEL_UP);
    }

    /**
     * Resets the shared goal coordinates to (0,0).
     */
    @Override
    public void reset() {
        goalX = 0;
        goalY = 0;
    }
}
