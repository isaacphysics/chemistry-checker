package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

/**
 * Instance of an alpha particle.
 * Created by Ryan on 20/06/2016.
 */
public final class AlphaParticle extends Nuclear
{
    @Override
    public Integer getMassNumber() { return 4; }

    @Override
    public Integer getAtomicNumber() { return 2; }

    @Override
    public Integer getCharge() { return 2; }

    @Override
    public HashMap<String, Integer> getAtomCount() { return new HashMap<>(); }

    @Override
    public boolean equals(Object o) { return (o instanceof AlphaParticle); }

    @Override
    public String toString() { return "/alpha_particle"; }

    @Override
    public String getDotId() { return "alpha_" + dotId; }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();

        result.append("\t");
        result.append(getDotId());

        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Alpha Particle&zwnj;|\\n");
        result.append(getDotString());

        result.append("\\n\\n|&zwj;&zwj;&zwj;atomic #&zwnj;: 2");

        result.append("\\n|&zwj;&zwj;&zwj;mass #&zwnj;: 4");

        result.append("}\",color=\"#4c7fbe\"];\n");
        return result.toString();
    }

    @Override
    public String getDotString() { return "&zwj;&zwj;4&zwnj;&zwj;2&zwnj;&#945;"; }
}
