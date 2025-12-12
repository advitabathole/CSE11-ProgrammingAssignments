/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * This file contains the implementation of the Turtle critter.
 */

import java.awt.Color;

/**
 * Turtle - "Tu".
 * Always moves west. Attacks randomly with ROAR or FORFEIT.
 * Eats only when no other critters are nearby.
 */
public class Turtle extends Critter {

    /** Display name constant to avoid magic number. */
    private static final String SPECIES_NAME = "Tu";

    /** Neighbor constants to avoid magic numbers. */
    private static final String EMPTY = " ";
    private static final String DOT   = ".";

    /** Creates a new Turtle with display name "Tu". */
    public Turtle() {
        super(SPECIES_NAME);
    }

    /**
     * Returns the movement direction.
     *
     * @return the direction to move (always WEST)
     */
    @Override
    public Direction getMove() {
        return Direction.WEST;
    }

    /**
     * Determines if the Turtle will eat.
     *
     * @return true if all adjacent squares are empty or contain Turtle, false otherwise
     */
    @Override
    public boolean eat() {
        String north = info.getNeighbor(Direction.NORTH);
        String south = info.getNeighbor(Direction.SOUTH);
        String east  = info.getNeighbor(Direction.EAST);
        String west  = info.getNeighbor(Direction.WEST);

        boolean northClear = north.equals(EMPTY) || north.equals(DOT)
                             || north.equals(SPECIES_NAME);
        boolean southClear = south.equals(EMPTY) || south.equals(DOT)
                             || south.equals(SPECIES_NAME);
        boolean eastClear  = east.equals(EMPTY)  || east.equals(DOT)
                             || east.equals(SPECIES_NAME);
        boolean westClear  = west.equals(EMPTY)  || west.equals(DOT)
                             || west.equals(SPECIES_NAME);

        return northClear && southClear && eastClear && westClear;
    }

    /**
     * Generates the attack randomly.
     *
     * @param opponent the critter to attack
     * @return the type of attack (ROAR or FORFEIT)
     */
    @Override
    public Attack getAttack(String opponent) {
        return random.nextBoolean() ? Attack.ROAR : Attack.FORFEIT;
    }

    /**
     * Returns the display color of the Turtle.
     *
     * @return the color of the Turtle (green)
     */
    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}
