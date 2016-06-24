package org.isaacphysics.labs.chemistry.checker;

/**
 * Instance of a beta particle for nuclear physics.
 * Created by Ryan on 20/06/2016.
 */
public final class BetaParticle extends SpecialNuclear
{
    public BetaParticle()
    {
        super(0, -1, -1, "Beta particle", "beta_particle", "&#946;");
    }

    @Override
    public boolean equals(Object o) { return (o instanceof BetaParticle); }
}
