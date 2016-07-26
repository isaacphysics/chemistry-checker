package org.isaacphysics.labs.chemistry.checker;

/**
 * Class for fractional coefficients.
 *
 * Created by hhrl2 on 26/07/2016.
 */
class FracCoeff extends Coefficient {

    private Integer num;
    private Integer denom;
    private Fraction fractionForm;

    /**
     * Constructor method of FracCoeff.
     *
     * @param num Numerator of fraction.
     * @param denom Denominator of fraction.
     */
    FracCoeff(final Integer num, final Integer denom) {

        this.num = num;
        this.denom = denom;
        this.fractionForm = new Fraction(num, denom);

    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof FracCoeff)) {
            return false;
        }

        FracCoeff other = (FracCoeff) o;

        return other.num.equals(this.num) && other.denom.equals(this.denom);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return "\\frac{" + num + "}{" + denom + "}";
    }

    @Override
    public Fraction toFraction() {
        return fractionForm;
    }

    @Override
    public String getDotString() {
        return num + "/" + denom;
    }

}
