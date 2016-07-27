/**
 * Copyright 2016 James Sharkey
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
 * The abstract base class for all possible terms in an equation/expression.
 *
 * Created by Ryan on 17/06/2016.
 */
abstract class AbstractTerm implements Countable {

    /**
     * Helper static variable for issuing unique IDs.
     */
    private static int dotIdTracker = 0;

    /**
     * Stores unique ID for every Term.
     */
    private int dotId;

    /**
     * Constructor function.
     * Initializes dotId, so that every term has an unique ID number.
     */
    AbstractTerm() {
        dotId = dotIdTracker;
        dotIdTracker += 1;
    }

    /**
     * Gives the private unique ID for an abstract term.
     *
     * @return Unique ID for an abstract term.
     */
    int getdotId() {
        return dotId;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        //System.out.printf("HashCode of %s: %d\n", toString(), toString().hashCode());
        return toString().hashCode();
    }

    /**
     * Defined for nuclear terms only.
     *
     * @return Mass number of term.
     * @throws NuclearException The particle is not nuclear.
     */
    public abstract Fraction getMassNumber() throws NuclearException;

    /**
     * Defined for nuclear terms only.
     *
     * @return Atomic number of term.
     * @throws NuclearException The particle is not nuclear.
     */
    public abstract Fraction getAtomicNumber() throws NuclearException;

    /**
     * Method only applicable to nuclear formula.
     * Checks if atomic number of isotope is valid.
     *
     * @return The validity of atomic number in nuclear formula.
     */
    public boolean isValidAtomicNumber() {
        return false;
    }
}
