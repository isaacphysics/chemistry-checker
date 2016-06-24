package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

/**
 * Class for regular isotopes, i.e. single atom with subscripted and superscripted numbers on the left.
 * Created by Ryan on 17/06/2016.
 */
public final class Isotope extends Nuclear
{
    /**
     * Maps elements to their corresponding atomic number.
     */
    private static HashMap<String, Integer> periodicTable;

    static
    {
        periodicTable = new HashMap<>();

        String[] elemList =
                {"H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl",
                "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As",
                "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In",
                "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb",
                "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl",
                "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk",
                "Cf", "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn", "Uut",
                "Fl", "Uup", "Lv", "Uus", "Uuo"};

        for (int i = 0; i < elemList.length; i++)
        {
            periodicTable.put(elemList[i], i+1);
        }
    }

    /**
     * Mass number of isotope.
     */
    private Integer mass,

    /**
     * Atomic number of isotope.
     */
            atom;

    /**
     * Atom/charged atom associated with isotope.
     */
    private Formula formula;

    /**
     * Constructor method of Isotope.
     * @param mass Mass number of isotope.
     * @param atom Atomic number of isotope.
     * @param f Atom/charged atom associated with the isotope.
     */
    public Isotope(Integer mass, Integer atom, Formula f)
    {
        super();

        this.mass = mass;
        this.atom = atom;
        this.formula = f;
    }

    @Override
    public Integer getMassNumber()
    {
        return mass;
    }

    @Override
    public Integer getAtomicNumber()
    {
        return atom;
    }

    @Override
    public Integer getCharge() { return formula.getCharge(); }

    @Override
    public HashMap<String, Integer> getAtomCount()
    {
        return formula.getAtomCount();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Isotope)
        {
            Isotope i = (Isotope) o;

            return (this.mass.equals(i.mass)) &&
                    (this.atom.equals(i.atom)) &&
                    (this.formula.equals(i.formula));
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "^{" + mass.toString() + "}_{" +
                atom.toString() + "}" +
                formula.toString();
    }

    @Override
    public String getDotId()
    {
        return "isotope_" + dotId;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();

        result.append("\t");
        result.append(getDotId());

        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Isotope&zwnj;|\\n");
        result.append(getDotString());

        result.append("\\n\\n|&zwj;&zwj;&zwj;atomic #&zwnj;: ");
        result.append(atom);

        result.append("\\n|&zwj;&zwj;&zwj;mass #&zwnj;: ");
        result.append(mass);

        result.append("\\n|&zwj;&zwj;&zwj;related atom&zwnj;}\",color=\"#944cbe\"];\n");

        result.append("\t");
        result.append(getDotId());
        result.append(":s -> ");
        result.append(formula.getDotId());
        result.append(":n;\n");
        result.append(formula.getDotCode());

        return result.toString();
    }

    @Override
    public String getDotString()
    {
        return "&zwj;&zwj;" + mass + "&zwnj;" +
                "&zwj;" + atom + "&zwnj;" +
                formula.getDotString();
    }

    @Override
    public boolean isValidAtomicNumber()
    {
        Molecule at;

        if (formula instanceof Ion)
        {
            at = ((Ion) formula).getMolecule();
        }
        else
        {
            at = (Element) formula;
        }

        if (periodicTable.containsKey(at.toString()))
            return periodicTable.get(at.toString()).equals(atom) && (mass >= atom);
        else
            return false;
    }
}
