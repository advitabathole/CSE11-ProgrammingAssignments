/**
 * Name: Advita Bathole
 * Email: abathole@ucsd.edu
 * PID: A19237518
 * Sources: CSE 11 class notes, Piazza
 *
 * This file contains the implementation of the Ocelot critter.
 */

import java.awt.Color;

/**
 * Ocelot - "Oce"
 * Behaves like a Leopard but overrides its attack generator.
 * Prefers SCRATCH against Lions, Felines, and Leopards,
 * and uses POUNCE against all other opponents.
 */
public class Ocelot extends Leopard {

    /** Display name constant. */
    private static final String SPECIES_NAME = "Oce";

    /** Opponent constants to avoid magic numbers. */
    private static final String LION1 = "Lion";
    private static final String LION2 = "noiL";
    private static final String FELINE = "Fe";
    private static final String LEOPARD = "Lpd";

    /**
     * Constructs an Ocelot and sets its display name to "Oce".
     */
    public Ocelot() {
        super();
        this.displayName = SPECIES_NAME;
    }

    /**
     * Returns the Ocelot's color.
     *
     * @return the color of the Ocelot (light gray)
     */
    @Override
    public Color getColor() {
        return Color.LIGHT_GRAY;
    }

    /**
     * Generates the attack type for this Ocelot.
     * Uses SCRATCH against Lions, Felines, and Leopards.
     * Otherwise uses POUNCE.
     *
     * @param opponent the name of the encountered critter
     * @return SCRATCH or POUNCE depending on opponent
     */
    @Override
    protected Attack generateAttack(String opponent) {
        boolean isLion = opponent.equals(LION1) || opponent.equals(LION2);
        boolean isFeline = opponent.equals(FELINE);
        boolean isLeopard = opponent.equals(LEOPARD);

        if (isLion || isFeline || isLeopard) {
            return Attack.SCRATCH;
        }
        return Attack.POUNCE;
    }
}
