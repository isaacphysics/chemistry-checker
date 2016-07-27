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

/**
 * The base class of expressions and equations.
 */
abstract class Statement {
    /**
     * Checks if statement contains any error terms.
     *
     * @return True if statement contains error terms.
     */
    abstract boolean containsError();

    @Override
    public abstract boolean equals(Object o);

    /**
     * Returns abstract syntax tree of statement.
     *
     * @return Abstract syntax tree of statement, in string form.
     */
    abstract String getDotCode();

    /**
     * Checks if two statements are weakly equivalent, i.e.
     * equivalent when ignoring coefficients, arrows (if they have one) and state symbols.
     * <p>
     *     For instance, NaOH (aq) -> 3 H^{+} is weakly equivalent to NaOH <--> H^{+} (aq).
     *
     * @param s Statement to be compared against.
     * @return True if two statements are weakly equivalent.
     */
    public abstract boolean weaklyEquivalent(Statement s);

    /**
     * Check if argument statement equals to this statement.
     * Prints helpful message if statement is wrong.
     *
     * @param input Statement to be compared against.
     * @return True if argument statement is equivalent to this statement.
     */
    public abstract boolean check(Statement input);

    /**
     * Find terms in argument statement that do not exist in this one.
     *
     * @param e The supposedly wrong equation statement.
     * @return ArrayList of wrong terms in e.
     */
    public abstract ArrayList<Term> getWrongTerms(Statement e);

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}