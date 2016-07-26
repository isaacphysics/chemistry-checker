package org.isaacphysics.labs.chemistry.checker;

/**
 * The coefficient of a term.
 *
 * Created by hhrl2 on 26/07/2016.
 */
public abstract class Coefficient {

    @Override
    public abstract boolean equals(final Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    /**
     * Gives the fraction form of the coefficent.
     * @return The fraction form of the coefficient.
     */
    public abstract Fraction toFraction();

    /**
     * Returns pretty-printed chemical term/equation/expression.
     * @return Pretty printed chemical term/equation/expression
     */
    public abstract String getDotString();

}
