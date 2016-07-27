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

/**
 * Instance of an error term - terms that contain syntax errors.
 */
public final class ErrorTerm extends AbstractTerm {

    /**
     * Constructor method for ErrorTerm.
     * Does nothing other than invoking its base class, AbstractTerm for generating dotId.
     */
    public ErrorTerm() {
        super();
    }

    @Override
    public String toString() {
        return "ERROR";
    }

    @Override
    public boolean equals(final Object o) {
        // Enforce assertion that error terms can never be equal.
        return false;
    }

    @Override
    public Fraction getMassNumber() throws NuclearException {
        throw new NuclearException("Mass number not defined for ErrorTerm.");
    }

    @Override
    public Fraction getAtomicNumber() throws NuclearException {
        throw new NuclearException("Atomic number not defined for ErrorTerm.");
    }

    @Override
    public HashMap<String, Fraction> getAtomCount() {
        return new HashMap<>();
    }

    @Override
    public Fraction getCharge() {
        return new Fraction(0, 1);
    }

    @Override
    public String getDotId() {
        return "error_term_" + getdotId();
    }

    @Override
    public String getDotCode() {

        return "\t"
                + getDotId()
                + " [label=\"{&zwj;&zwj;&zwj;&zwj;Term&zwnj;|\\n"
                + "Syntax Error"
                + "\\n\\n}\",color=\"#49902a\"];\n";

    }

    @Override
    public String getDotString() {
        return toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
