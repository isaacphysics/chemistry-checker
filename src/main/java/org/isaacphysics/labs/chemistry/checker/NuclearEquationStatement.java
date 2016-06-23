package org.isaacphysics.labs.chemistry.checker;

/**
 * Special class for nuclear equations.
 * Very similar to EquationStatement, it holds two expressions, and checks if the mass and atomic numbers of both sides
 * are balanced.
 * Created by Ryan on 20/06/2016.
 */
public class NuclearEquationStatement extends Statement
{
    /**
     * The left expression of a equation
     */
    private Expression left;

    /**
     * The right expression of a equation
     */
    private Expression right;

    /**
     * Constructor method of NuclearEquationStatement.
     * @param l Left expression of equation.
     * @param r Right expression of equation.
     */
    public NuclearEquationStatement(Expression l, Expression r)
    {
        left = l;
        right = r;
    }

    @Override
    public String toString() {
        return left.toString() + " -> " + right.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof NuclearEquationStatement)
        {
            NuclearEquationStatement other = (NuclearEquationStatement) o;

            return this.left.equals(other.left) &&
                    this.right.equals(other.right);
        }
        return false;
    }

    @Override
    public boolean containsError() {
        return left.containsError() || right.containsError();
    }

    /**
     * Getter function. Returns left expression.
     */
    public Expression getLeftExpression() {
        return this.left;
    }

    /**
     * Getter function. Returns right expression.
     */
    public Expression getRightExpression() {
        return this.right;
    }

    /**
     * Checks if mass numbers on both sides of equation are balanced.
     */
    private boolean isBalancedMass()
    {
        try
        {
            return !containsError() && left.getMassCount().equals(right.getMassCount());
        }
        catch (NuclearException e)
        {
            return false;
        }
    }

    /**
     * Checks if atomic numbers on both sides of equation are balanced.
     */
    private boolean isBalancedAtom()
    {
        try
        {
            return !containsError() && left.getAtomicCount().equals(right.getAtomicCount());
        }
        catch (NuclearException e)
        {
            return false;
        }
    }

    /**
     * Checks if both mass numbers and atomic numbers on both sides of equation are balanced.
     */
    boolean isBalanced() { return isBalancedAtom() && isBalancedMass(); }

    /**
     * Checks if the atomic number of all isotopes matches element name.
     */
    boolean isValid() { return left.isValidAtomicNumber() && right.isValidAtomicNumber(); }

    @Override
    String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("digraph chemical_syntax_tree {\n");
        result.append("\tnode [shape=record,penwidth=2,splines=ortho];\n\n");

        result.append("\tequation [label=\"{&zwj;&zwj;&zwj;&zwj;Nuclear Equation&zwnj;|\\n");
        result.append(left.getDotString());
        result.append(" &#8594; ");
        result.append(right.getDotString());
        result.append("\\n\\n|<left>&zwj;&zwj;&zwj;left&zwnj;|" +
                "<right>&zwj;&zwj;&zwj;right&zwnj;}\",color=\"#bb2828\"];\n");

        result.append("\tequation:left:w -> ");
        result.append(left.getDotId());
        result.append(";\n");

        result.append("\tequation:right:e -> ");
        result.append(right.getDotId());
        result.append(";\n");

        result.append(left.getDotCode());
        result.append(right.getDotCode());
        result.append("}\n");
        return result.toString();
    }

    @Override
    public boolean weaklyEquivalent(Statement s)
    {
        if (!(s instanceof NuclearEquationStatement))
            return false;

        NuclearEquationStatement other = (NuclearEquationStatement) s;

        return left.weaklyEquivalent(other.left) && right.weaklyEquivalent(other.right);
    }

    /**
     * Checks if both statements are weakly equivalent, AND contains same coefficients in relevant terms.
     *
     * E.g. 2NaOH (aq), 2NaOH (g) should return true.
     *
     * Should assert that both expressions are weakly equivalent before executing this code,
     * as it is quite time consuming.
     *
     * @param s Statement to be compared against.
     */
    public boolean sameCoefficients(Statement s)
    {
        if (!(s instanceof NuclearEquationStatement))
            return false;

        NuclearEquationStatement other = (NuclearEquationStatement) s;

        return left.sameCoefficients(other.left) && right.sameCoefficients(other.right);
    }
}
