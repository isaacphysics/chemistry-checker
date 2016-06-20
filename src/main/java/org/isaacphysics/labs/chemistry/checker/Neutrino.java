package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

/**
 * Instance of a neutrino.
 * Created by Ryan on 20/06/2016.
 */
public final class Neutrino extends Nuclear
{
    @Override
    public Integer getMassNumber() { return 1; }

    @Override
    public Integer getAtomicNumber() { return 0; }

    @Override
    public Integer getCharge() { return 0; }

    @Override
    public HashMap<String, Integer> getAtomCount() { return new HashMap<>(); }

    @Override
    public boolean equals(Object o) { return (o instanceof Neutrino); }

    @Override
    public String toString() { return "/neutrino"; }

    @Override
    public String getDotId() { return "neutrino_" + dotId; }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();

        result.append("\t");
        result.append(getDotId());

        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Neutrino&zwnj;|\\n");
        result.append(getDotString());

        result.append("\\n\\n|&zwj;&zwj;&zwj;atomic #&zwnj;: 0");

        result.append("\\n|&zwj;&zwj;&zwj;mass #&zwnj;: 1");

        result.append("}\",color=\"#4c7fbe\"];\n");
        return result.toString();
    }

    @Override
    public String getDotString()
    {
        return "&zwj;&zwj;1&zwnj;&zwj;0&zwnj;n";
    }
}
