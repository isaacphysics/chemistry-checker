/**
 * Copyright 2016 Ryan Lau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
