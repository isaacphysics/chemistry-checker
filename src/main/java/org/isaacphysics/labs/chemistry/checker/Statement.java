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

import java.util.ArrayList;

abstract class Statement
{
    /**
     * Checks if statement contains any error terms.
     */
    abstract boolean containsError();

    @Override
    public abstract boolean equals(Object o);

    /**
     * Returns abstract syntax tree of statement.
     */
    abstract String getDotCode();

    /**
     * Checks if two statements are weakly equivalent, i.e.
     * equivalent when ignoring coefficients, arrows (if they have one) and state symbols.
     * <p>
     *     For instance, NaOH (aq) -> 3 H^{+} is weakly equivalent to NaOH <--> H^{+} (aq).
     * @param s Statement to be compared against
     */
    public abstract boolean weaklyEquivalent(Statement s);

    /**
     * Check if argument expression equals to this expression.
     * Prints helpful message if expression is wrong.
     */
    public abstract boolean check(Statement input);

    /**
     * Find terms in argument statement that do not exist in this one.
     *
     * @param e The supposedly wrong equation statement.
     * @return ArrayList of wrong terms in e.
     */
    public abstract ArrayList<Term> getWrongTerms(Statement e);
}