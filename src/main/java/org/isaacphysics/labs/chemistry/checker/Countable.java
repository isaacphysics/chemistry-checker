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

import java.util.HashMap;

interface Countable
{
    /**
     * Getter function. Returns total charge of a countable term.
     * @return Total charge of a term
     */
    Integer getCharge();

    /**
     * Gives a list of all atoms involved in a countable term, as well as their corresponding numbers.
     * Subparticles like electrons and gamma rays are not involved.
     * @return A list of (atom, number) pairs
     */
    HashMap<String, Integer> getAtomCount();

    @Override
    boolean equals(Object o);

    @Override
    String toString();

    /**
     * Getter function. Gives unique ID of the term.
     * @return Unique ID of the term
     */
    String getDotId();

    /**
     * Returns Graphviz code that generates syntax tree of chemical term.
     * <p>
     *     Special syntax for this modified Graphviz code:
     * <ul>
     *     <li> &zwj;&zwj;&zwj;&zwj;: Makes text to the right bold.
     *     <li> &zwj;&zwj;&zwj;: Makes text to the right italic.
     *     <li> &zwj;&zwj;: Superscripts text to the right.
     *     <li> &zwj;: Subscripts text to the right.
     *     <li> &zwnj;: Delimiter that removes effect of above.
     * </ul>
     *
     * @return Graphviz code that generates the abstract syntax tree
     */
    String getDotCode();

    /**
     * Returns pretty-printed chemical term/equation/expression
     * @return Pretty printed chemical term/equation/expression
     */
    String getDotString();
}
