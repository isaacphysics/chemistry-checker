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
 * Created by Ryan on 15/06/2016.
 * An abstract class that forms the basis of a molecule/ion/isotope.
 */
public abstract class Formula implements Countable
{
    /**
     * Helper static variable for issuing unique IDs
     */
    private static int dotIdTracker = 0;

    /**
     * Stores unique ID for every Formula
     */
    int dotId;

    /**
     * Constructor function.
     * Initializes dotId, so that every formula has an unique ID number.
     */
    public Formula()
    {
        dotId = dotIdTracker;
        dotIdTracker++;
    }

    /*@Override
    public abstract Integer getCharge();

    @Override
    public abstract HashMap<String, Integer> getAtomCount();
    */

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract String toString();

    /**
     * Method only applicable to nuclear formula.
     * @return Atomic number of formula.
     * @throws NuclearException
     */
    public Integer getAtomicNumber() throws NuclearException
    {
        throw new NuclearException("Formula does not have atomic number.");
    }

    /**
     * Method only applicable to nuclear formula.
     * @return Mass number of formula.
     * @throws NuclearException
     */
    public Integer getMassNumber() throws NuclearException
    {
        throw new NuclearException("Formula does not have mass number.");
    }

    /**
     * Method only applicable to nuclear formula.
     * Checks if atomic number of isotope is valid. That is:
     * 1. Mass number of isotope is not less than its atomic number.
     * 2. The given atomic number matches the element symbol.
     */
    public boolean isValidAtomicNumber()
    {
        return false;
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }
}
