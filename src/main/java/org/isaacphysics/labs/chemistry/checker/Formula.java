package org.isaacphysics.labs.chemistry.checker;

/**
 * Created by Ryan on 15/06/2016.
 * An abstract class that forms the basis of a molecule/ion/isotope.
 */
public abstract class Formula implements Countable
{
    /**
     * Helper static variable for issuing unique IDs
     */
    private static int dotIdTracker = 0;

    /**
     * Stores unique ID for every Formula
     */
    int dotId;

    /**
     * Constructor function.
     * Initializes dotId, so that every formula has an unique ID number.
     */
    public Formula()
    {
        dotId = dotIdTracker;
        dotIdTracker++;
    }

    /*@Override
    public abstract Integer getCharge();

    @Override
    public abstract HashMap<String, Integer> getAtomCount();
    */

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract String toString();

    /**
     * Method only applicable to nuclear formula.
     * @return Atomic number of formula.
     * @throws NuclearException
     */
    public abstract Integer getAtomicNumber() throws NuclearException;

    /**
     * Method only applicable to nuclear formula.
     * @return Mass number of formula.
     * @throws NuclearException
     */
    public abstract Integer getMassNumber() throws NuclearException;

    /**
     * Method only applicable to nuclear formula.
     * Checks if atomic number of isotope is valid.
     */
    public boolean isValidAtomicNumber()
    {
        return false;
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }
}
