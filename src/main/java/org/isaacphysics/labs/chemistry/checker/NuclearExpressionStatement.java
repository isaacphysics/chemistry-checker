package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

/**
 * Statement containing nuclear expressions.
 * Created by Ryan on 21/06/2016.
 */
public class NuclearExpressionStatement extends Statement implements Countable
{
    /**
     * Expression involved in statement
     */
    private Expression expr;

    /**
     * Constructor method of ExpressionStatement.
     * @param e Expression involved in the statement.
     */
    public NuclearExpressionStatement(Expression e) {
        expr = e;
    }

    @Override
    public String toString() {
        return expr.toString();
    }

    @Override
    public String getDotId() { return null; }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof NuclearExpressionStatement)
        {
            NuclearExpressionStatement other = (NuclearExpressionStatement) o;
            return this.expr.equals(other.expr);
        }
        return false;
    }

    @Override
    public boolean containsError() {
        return expr.containsError();
    }

    @Override
    public HashMap<String, Integer> getAtomCount() {
        return expr.getAtomCount();
    }

    @Override
    public Integer getCharge() {
        return expr.getCharge();
    }

    Expression getExpression() {
        return this.expr;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("digraph chemical_syntax_tree {\n");
        result.append("\tnode [shape=record,penwidth=2,splines=ortho];\n\n");
        result.append(expr.getDotCode());
        result.append("}\n");
        return result.toString();
    }

    @Override
    public String getDotString()
    {
        return null;
    }

    public boolean isValid()
    {
        return expr.isValidAtomicNumber();
    }

    /**
     * @return Sum of atomic numbers in nuclear expression.
     * @throws NuclearException
     */
    public Integer getAtomicCount() throws NuclearException
    {
        return expr.getAtomicCount();
    }

    /**
     * @return Sum of mass numbers in nuclear expression.
     * @throws NuclearException
     */
    public Integer getMassCount() throws NuclearException
    {
        return expr.getMassCount();
    }

}
