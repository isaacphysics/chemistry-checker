package org.isaacphysics.labs.chemistry.checker;

/**
 * Instance of a neutron.
 *
 * Created by hhrl2 on 08/07/2016.
 */
public final class Neutron extends SpecialNuclear
{
    public Neutron(Integer mass, Integer atom)
    {
        super(1, 0, mass, atom, 0, "Neutron", "neutron", "n");
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Neutron;
    }
}
