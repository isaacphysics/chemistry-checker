package org.isaacphysics.labs.chemistry.checker;

/**
 * Class for integral coefficients.
 *
 * Created by hhrl2 on 26/07/2016.
 */
class IntCoeff extends Coefficient {

    private Integer coefficient;
    private Fraction fractionForm;

    /**
     * Constructor method of IntCoeff.
     *
     * @param n Coefficient of term.
     */
    IntCoeff(final Integer n) {
        coefficient = n;
        fractionForm = new Fraction(n, 1);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof IntCoeff)) {
            return false;
        }

        IntCoeff other = (IntCoeff) o;

        return other.coefficient.equals(this.coefficient);

    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return coefficient.toString();
    }

    @Override
    public Fraction toFraction() {
        return fractionForm;
    }

    /**
     * Returns the coefficient.
     * @return Coefficient of term.
     */
    public Integer getCoefficient() {
        return coefficient;
    }

    @Override
    public String getDotString() {
        return coefficient.toString();
    }
}
