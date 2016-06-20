package org.isaacphysics.labs.chemistry.checker;

/**
 * Exception class for nuclear terms.
 * Raised when methods like getAtomicNumber, getMassNumber are invoked on non-nuclear objects.
 *
 * Created by Ryan on 20/06/2016.
 */
public class NuclearException extends Exception
{
    public NuclearException(String e)
    {
        super(e);
    }
}
