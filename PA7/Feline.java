/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * This file contains the implementation of the Feline critter.
 */

import java.awt.Color;

/**
 * Feline - "Fe".
 * Moves in a chosen random direction for 5 moves, then picks a new direction.
 * Eats every 3rd encountered food (first encounter -> doesn't eat).
 * Always uses POUNCE for attack.
 */
public class Feline extends Critter {

    /** Display name constant to avoid magic number. */
    private static final String SPECIES_NAME = "Fe";

    /** Number of moves in a given direction before changing. */
    private static final int MOVE_LIMIT = 5;

    /** Eat frequency (every nth food encounter). */
    private static final int EAT_FREQUENCY = 3;

    /** Counts how many moves have been taken in the current direction. */
    private int moveCount;

    /** Counts food encounters. */
    private int eatCount;

    /** Current movement direction. */
    private Direction currDir;

    /** Creates a new Feline with display name "Fe". */
    public Feline() {
        super(SPECIES_NAME);
        moveCount = 0;
        eatCount = 0;
        currDir = null;
    }

    /**
     * Returns the next movement direction.
     * @return direction to move
     */
    @Override
    public Direction getMove() {
        if (currDir == null || moveCount >= MOVE_LIMIT) {
            Direction[] dirs = {
                Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST
            };
            currDir = dirs[random.nextInt(dirs.length)];
            moveCount = 0;
        }
        moveCount++;
        return currDir;
    }

    /**
     * Determines whether the Feline eats.
     * @return true on every 3rd encounter
     */
    @Override
    public boolean eat() {
        eatCount++;
        return eatCount % EAT_FREQUENCY == 0;
    }

    /**
     * Determines attack type.
     * @param opponent the critter to attack
     * @return always POUNCE
     */
    @Override
    public Attack getAttack(String opponent) {
        return Attack.POUNCE;
    }
}
