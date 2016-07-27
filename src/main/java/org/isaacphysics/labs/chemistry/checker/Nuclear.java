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
 * Abstract base class that forms basis of items in nuclear physics.
 * This includes isotopes, electrons and other nuclear particle stuff.
 *
 * Created by Ryan on 17/06/2016.
 */
public abstract class Nuclear extends Formula {

    /**
     * Helper static variable for issuing unique IDs.
     */
    private static int dotIdTracker = 0;

    /**
     * Stores unique ID for every Expression.
     */
    private int dotId;

    /**
     * Constructor method for Nuclear. Assigns an unique ID to the nuclear term.
     */
    public Nuclear() {
        dotId = dotIdTracker;
        dotIdTracker++;
    }

    /**
     * Getter method. Used in subclasses for getting unique IDs.
     *
     * @return Unique ID.
     */
    public int getdotId() {
        return dotId;
    }

    /**
     * Getter method. Returns mass number of given particle.
     *
     * @return Mass number of nuclear particle.
     */
    @Override
    public abstract Integer getMassNumber();

    /**
     * Getter method. Returns atomic number of given particle.
     *
     * @return Atomic number of nuclear particle.
     */
    @Override
    public abstract Integer getAtomicNumber();

    @Override
    public abstract boolean isValidAtomicNumber();
}
