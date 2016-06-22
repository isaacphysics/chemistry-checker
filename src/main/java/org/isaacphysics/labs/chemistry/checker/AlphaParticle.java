package org.isaacphysics.labs.chemistry.checker;

/**
 * Instance of an alpha particle.
 * Created by Ryan on 20/06/2016.
 */
public final class AlphaParticle extends SpecialNuclear
{
    public AlphaParticle()
    {
        super(4, 2, 2, "Alpha particle", "alpha_particle", "&#945;");
    }

    @Override
    public boolean equals(Object o) { return (o instanceof AlphaParticle); }
}
