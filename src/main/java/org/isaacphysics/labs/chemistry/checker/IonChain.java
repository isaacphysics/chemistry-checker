package org.isaacphysics.labs.chemistry.checker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * As its name suggests, it stores a chain of ions, e.g. Na+Cl-.
 * Chain must contain one or more ions, followed by an ion/uncharged molecule in the end.
 *
 * Created by hhrl2 on 12/07/2016.
 */
public final class IonChain extends Formula
{
    /**
     * The array of formulae stored in ion chain.
     */
    private ArrayList<Formula> formulas;

    /**
     * Constructor function of IonChain.
     * @param f First formula in IonChain.
     */
    public IonChain(Formula f)
    {
        super();

        formulas = new ArrayList<>();
        formulas.add(f);
    }

    /**
     * Appends one formula to the end of IonChain.
     * @param f Formula to be appended.
     */
    public void add(Formula f)
    {
        formulas.add(f);
    }

    /**
     * Reverses the list of formula stored in IonChain.
     */
    public void reverse()
    {
        Collections.reverse(formulas);
    }

    @Override
    public Integer getCharge()
    {
        Integer totalCharge = 0;

        for (Formula f: formulas)
        {
            totalCharge += f.getCharge();
        }

        return totalCharge;
    }

    @Override
    public HashMap<String, Integer> getAtomCount()
    {
        HashMap<String, Integer> h = new HashMap<>();
        for (Formula f : formulas)
        {
            for (String e : f.getAtomCount().keySet())
            {
                if (!h.containsKey(e))
                    h.put(e,  f.getAtomCount().get(e));
                else
                    h.put(e, h.get(e) + f.getAtomCount().get(e));
            }
        }
        return h;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof IonChain)
        {
            IonChain other = (IonChain) o;

            return formulas.equals(other.formulas);
        }

        return false;
    }

    @Override
    public String toString()
    {
        String toReturn = "";

        for (Formula f: formulas)
            toReturn += f.toString();

        return toReturn;
    }

    @Override
    public String getDotId()
    {
        return "ion_chain_" + dotId;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("\t");
        result.append(getDotId());
        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;IonChain&zwnj;|\\n");
        result.append(getDotString());
        result.append("\\n\\n|<terms>&zwj;&zwj;&zwj;formulas&zwnj;}\",color=\"#fea100\"];\n");

        for (Formula f : formulas)
        {
            result.append("\t");
            result.append(getDotId());
            result.append(":terms -> ");
            result.append(f.getDotId());
            result.append(":n;\n");
            result.append(f.getDotCode());
        }

        return result.toString();
    }

    @Override
    public String getDotString()
    {
        String toReturn = "";

        for (Formula f: formulas)
            toReturn += f.getDotString();

        return toReturn;
    }
}
