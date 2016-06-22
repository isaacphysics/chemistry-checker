package org.isaacphysics.labs.chemistry.checker;

/**
 * An instance of gamma ray.
 * Created by Ryan on 20/06/2016.
 */
public final class GammaRay extends SpecialNuclear
{
    public GammaRay()
    {
        super(0, 0, 0, "Gamma ray", "gamma_ray", "&#947;");
    }

    @Override
    public boolean equals(Object o) { return (o instanceof GammaRay); }
}
