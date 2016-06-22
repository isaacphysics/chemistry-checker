package org.isaacphysics.labs.chemistry.checker;

/**
 * Instance of a neutrino.
 * Created by Ryan on 20/06/2016.
 */
public final class Neutrino extends SpecialNuclear
{
    public Neutrino()
    {
        super(1, 0, 0, "Neutrino", "neutrino", "n");
    }

    @Override
    public boolean equals(Object o) { return (o instanceof Neutrino); }
}
