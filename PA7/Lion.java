/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * Lion critter that moves in a clockwise square pattern:
 * EAST 5, SOUTH 5, WEST 5, NORTH 5, then repeat.
 * Hungry after winning; eats once when hungry.
 * sleep() resets hunger and reverses display name to "noiL";
 * wakeup() restores the name.
 */

import java.awt.Color;

/** Lion - "Lion". */
public class Lion extends Feline {

    /** Movement pattern constants. */
    private static final int PHASE_STEPS = 5;
    private static final int TOTAL_PHASES = 4;

    /** Display name constants. */
    private static final String NAME = "Lion";
    private static final String NAME_REVERSED = "noiL";

    private int phaseIndex;   // 0..3 for EAST, SOUTH, WEST, NORTH
    private int phaseCount;   // 0..4 count within current phase
    private boolean hungryFlag;

    /** 
     * Constructor initializes name, phase counters, and hunger flag.
     */
    public Lion() {
        super();
        this.displayName = NAME;
        this.phaseIndex = 0;
        this.phaseCount = 0;
        this.hungryFlag = false;
    }

    /**
     * Returns the display color of the Lion.
     *
     * @return the color yellow for the Lion
     */
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    /**
     * Returns the next move in the clockwise movement pattern:
     * EAST, SOUTH, WEST, NORTH, each for PHASE_STEPS steps, then repeat.
     *
     * @return the next direction in the Lion's movement pattern
     */
    @Override
    public Direction getMove() {
        Direction[] pattern = {
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
            Direction.NORTH
        };

        Direction move = pattern[phaseIndex];
        phaseCount++;

        if (phaseCount >= PHASE_STEPS) {
            phaseCount = 0;
            phaseIndex = (phaseIndex + 1) % TOTAL_PHASES;
        }

        return move;
    }

    /**
     * Determines if the Lion eats.
     * Lion eats only when hungry (after winning a fight).
     *
     * @return true if Lion is hungry, false otherwise
     */
    @Override
    public boolean eat() {
        if (hungryFlag) {
            hungryFlag = false;
            return true;
        }
        return false;
    }

    /**
     * Puts the Lion to sleep.
     * Resets hunger and reverses the display name.
     */
    @Override
    public void sleep() {
        hungryFlag = false;
        this.displayName = NAME_REVERSED;
    }

    /**
     * Wakes the Lion up.
     * Restores the display name to normal.
     */
    @Override
    public void wakeup() {
        this.displayName = NAME;
    }

    /**
     * Called after winning a fight.
     * Lion becomes hungry and will eat on the next opportunity.
     */
    @Override
    public void win() {
        hungryFlag = true;
    }
}
