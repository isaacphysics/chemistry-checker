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
