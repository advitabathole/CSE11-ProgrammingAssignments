/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * This file contains the implementation of the Starfish critter.
 */

import java.awt.Color;

/**
 * Starfish - "Patrick".
 * Always stays in place and is pink.
 */
public class Starfish extends Critter {

    /** Display name constant to avoid magic number. */
    private static final String SPECIES_NAME = "Patrick";

    /** Creates a new Starfish with display name "Patrick". */
    public Starfish() {
        super(SPECIES_NAME);
    }

    /**
     * Returns the movement direction.
     * @return always CENTER
     */
    @Override
    public Direction getMove() {
        return Direction.CENTER;
    }

    /**
     * Returns the display color.
     * @return pink color
     */
    @Override
    public Color getColor() {
        return Color.PINK;
    }
}
