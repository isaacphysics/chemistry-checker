package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

/**
 * Created by Ryan on 15/06/2016.
 * An abstract class that forms the basis of a molecule/ion/isotope.
 */
public abstract class Formula implements Countable
{
    private static int dotIdTracker = 0;
    int dotId;

    public Formula()
    {
        dotId = dotIdTracker;
        dotIdTracker++;
    }

    @Override
    public abstract Integer getCharge();

    @Override
    public abstract HashMap<String, Integer> getAtomCount();

    public abstract String getDotCode();

    public abstract String getDotId();

    public abstract String getDotString();

    @Override
    public abstract String toString();
}
