package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

/**
 * Special class of Formula that only cares about electrons in chemical equations.
 * Created by Ryan on 17/06/2016.
 */
public class ChemicalElectron extends Formula
{
    /**
     * Constructor function of ChemicalElectron.
     */
    public ChemicalElectron()
    {
        super();
    }

    @Override
    public String toString() {
        return "e^{-}";
    }

    @Override
    public Integer getCharge()
    {
        return -1;
    }

    @Override
    public HashMap<String, Integer> getAtomCount()
    {
        return new HashMap<>();
    }

    @Override
    public String getDotId()
    {
        return "electron_" + dotId;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();

        result.append("\t");
        result.append(getDotId());

        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Electron&zwnj;|\\n");
        result.append(getDotString());

        result.append("\n\n}\",color=\"#4c7fbe\"];\n");
        return result.toString();
    }

    @Override
    public String getDotString()
    {
        return "e&zwj;&zwj;-&zwnj;";
    }
}
