package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

/**
 * Created by Ryan on 15/06/2016.
 * Class for charged particles.
 * Stores the charge, and offload other jobs to Molecule.
 */
public class Ion extends Formula
{
    /**
     * Molecule involved for this particular ion.
     */
    private Molecule molecule;

    /**
     * Charge of the ion.
     */
    private Integer charge;

    /**
     * Constructor function of Ion
     * @param m Molecule involved for this ion.
     * @param c Charge of ion.
     */
    public Ion(Molecule m, Integer c)
    {
        super();

        molecule = m;
        charge = c;
    }

    @Override
    public Integer getCharge()
    {
        return charge;
    }

    @Override
    public HashMap<String, Integer> getAtomCount()
    {
        return molecule.getAtomCount();
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();

        result.append("\t");
        result.append(getDotId());

        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Ion&zwnj;|\\n");
        result.append(getDotString());

        result.append("\\n\\n|&zwj;&zwj;&zwj;charge&zwnj;: ");
        result.append(charge);

        result.append("}\",color=\"#614126\"];\n");

        result.append("\t");
        result.append(getDotId());
        result.append(":s -> ");
        result.append(molecule.getDotId());
        result.append(":n;\n");
        result.append(molecule.getDotCode());

        return result.toString();
    }

    @Override
    public String getDotId() {
        return "ion_" + dotId;
    }

    @Override
    public String getDotString()
    {
        String c;

        if (charge == null || charge == 0)
            c = "";
        else if (charge == 1)
            c = "&zwj;&zwj;+&zwnj;";
        else if (charge == -1)
            c = "&zwj;&zwj;-&zwnj;";
        else if (charge > 1)
            c = "&zwj;&zwj;" + charge.toString() + "+&zwnj;";
        else
            c = "&zwj;&zwj;" + Math.abs(charge) + "-&zwnj;";

        return molecule.getDotString() + "&zwnj;^\\{" + c + "\\}";
    }

    @Override
    public String toString()
    {
        String c;
        if (charge == null || charge == 0)
            c = "";
        else if (charge == 1)
            c = "^{+}";
        else if (charge == -1)
            c = "^{-}";
        else if (charge > 1)
            c = "^{" + charge.toString() + "+}";
        else
            c = "^{" + Math.abs(charge) + "-}";

        return molecule.toString() + c;
    }
}
