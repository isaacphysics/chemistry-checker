package org.isaacphysics.labs.chemistry.checker;

/**
 * Class to do fraction operations.
 *
 * Created by hhrl2 on 26/07/2016.
 */
class Fraction {

    private Integer num;
    private Integer denom;

    /**
     * Constructor method of Fraction.
     *
     * @param num Numerator of fraction.
     * @param denom Denominator of fraction.
     */
    Fraction(final Integer num, final Integer denom) {
        this.num = num;
        this.denom = denom;

        simplify();
    }

    /**
     * Finds the gcd of two arguments.
     *
     * @param x First argument.
     * @param y Second argument.
     * @return GCD of two arguments.
     */
    private Integer gcd(final Integer x, final Integer y) {
        if (!y.equals(0)) {
            return gcd(y, x % y);
        } else {
            return x;
        }
    }

    /**
     * Simplifies the fraction.
     */
    private void simplify() {

        if (num > 0) {
            Integer gcd = gcd(num, denom);
            num /= gcd;
            denom /= gcd;
        } else if (num < 0) {
            Integer gcd = gcd(-num, denom);
            num /= gcd;
            denom /= gcd;
        } else {
            denom = 1;
        }
    }

    @Override
    public boolean equals(final Object obj) {

        if (!(obj instanceof Fraction)) {
            return false;
        }

        Fraction other = (Fraction) obj;

        return other.num.equals(this.num) && other.denom.equals(this.denom);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {

        if (denom.equals(1)) {
            return num.toString();
        } else {
            return num + "/" + denom;
        }
    }

    /**
     * Multiplies current fraction with the argument.
     *
     * @param f Argument to be multiplied with.
     * @return The product of two fractions, simplified.
     */
    Fraction times(final Fraction f) {
        return new Fraction(this.num * f.num, this.denom * f.denom);
    }

    /**
     * Multiplies current fraction with the argument.
     *
     * @param n Argument to be multiplied with.
     * @return The product of the fraction and integer, simplified.
     */
    Fraction times(final Integer n) {
        return new Fraction(n * this.num, this.denom);
    }

    /**
     * Adds current fraction to the argument.
     *
     * @param f Argument to be added with.
     * @return The sum of two fractions, simplified.
     */
    Fraction plus(final Fraction f) {
        return new Fraction(this.num * f.denom + this.denom * f.num, this.denom * f.denom);
    }

    /**
     * Adds current fraction to the argument.
     *
     * @param n Argument to be added with.
     * @return The sum of the fraction and integer, simplified.
     */
    Fraction plus(final Integer n) {
        return new Fraction(this.num + this.denom * n, this.denom);
    }
}
