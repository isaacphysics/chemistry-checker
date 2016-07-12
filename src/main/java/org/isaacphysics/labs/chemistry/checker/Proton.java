package org.isaacphysics.labs.chemistry.checker;

/**
 * Instance of a proton.
 *
 * Created by hhrl2 on 08/07/2016.
 */
public final class Proton extends SpecialNuclear
{
    public Proton()
    {
        super(1, 1, 1, "Proton", "proton", "p");
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Proton;
    }
}
