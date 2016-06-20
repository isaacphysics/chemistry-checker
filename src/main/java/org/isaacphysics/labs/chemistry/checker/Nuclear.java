package org.isaacphysics.labs.chemistry.checker;

/**
 * Abstract base class that forms basis of items in nuclear physics.
 * This includes isotopes, electrons and other nuclear particle stuff.
 *
 * Created by Ryan on 17/06/2016.
 */
public abstract class Nuclear extends Formula
{
    public abstract Integer getMassNumber();

    public abstract Integer getAtomicNumber();
}
