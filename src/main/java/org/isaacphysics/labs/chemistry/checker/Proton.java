package org.isaacphysics.labs.chemistry.checker;

/**
 * Instance of a proton.
 *
 * Created by hhrl2 on 08/07/2016.
 */
public final class Proton extends SpecialNuclear
{
    public Proton(Integer mass, Integer atom)
    {
        super(1, 1, mass, atom, 1, "Proton", "proton", "p");
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Proton;
    }
}
