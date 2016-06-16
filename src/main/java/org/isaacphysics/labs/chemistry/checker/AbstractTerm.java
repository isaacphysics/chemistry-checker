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

abstract class AbstractTerm implements Countable
{
    /**
     * Helper static variable for issuing unique IDs
     */
    private static int dotIdTracker = 0;

    /**
     * Stores unique ID for every Term
     */
    int dotId;

    /**
     * Constructor function.
     * Initializes dotId, so that every term has an unique ID number.
     */
    AbstractTerm()
    {
        dotId = dotIdTracker;
        dotIdTracker += 1;
    }

    /**
     * Checks if term contains all atoms in a given formula.
     * We do not take account of subparticles, such as electrons, during calculation.
     * @param m Chemical formula to be compared against
     */
    abstract boolean contains(Formula m);
}
