package org.isaacphysics.labs.chemistry.checker;

/**
 * Abstract base class that forms basis of items in nuclear physics.
 * This includes isotopes, electrons and other nuclear particle stuff.
 *
 * Created by Ryan on 17/06/2016.
 */
public abstract class Nuclear extends Formula
{
    /**
     * Helper static variable for issuing unique IDs
     */
    private static int dotIdTracker = 0;

    /**
     * Stores unique ID for every Expression
     */
    protected int dotId;

    public Nuclear()
    {
        dotId = dotIdTracker;
        dotIdTracker++;
    }

    /**
     * Getter method. Returns mass number of given particle.
     * @return Mass number of nuclear particle.
     */
    @Override
    public abstract Integer getMassNumber();

    /**
     * Getter method. Returns atomic number of given particle.
     * @return Atomic number of nuclear particle.
     */
    @Override
    public abstract Integer getAtomicNumber();

    /**
     * Checks if corresponding atomic number of atom is valid.
     */
    public boolean isValidAtomicNumber() throws NuclearException
    {
        return true;
    }
}
