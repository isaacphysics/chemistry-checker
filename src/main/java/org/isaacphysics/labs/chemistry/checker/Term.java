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

public class Term extends AbstractTerm {

    // Solid, Liquid, Gas, Aqueous in standard chemical notation
    public enum PhysicalState {
        s,
        l,
        g,
        aq
    }

    private Molecule molecule;
    private Integer number;
    private PhysicalState state;

    public Term(int n, Molecule m, String s) {
        this.number = n;
        this.molecule = m;
        if (null == s) {
            this.state = null;
        } else if (s.equals("s")) {
            this.state = PhysicalState.s;
        } else if (s.equals("l")) {
            this.state = PhysicalState.l;
        } else if (s.equals("g")) {
            this.state = PhysicalState.g;
        } else if (s.equals("aq")) {
            this.state = PhysicalState.aq;
        }
    }

    public Term(int n, Molecule m) {
        this(n, m, null);
    }

    public Term(Molecule m, String state) {
        this(1, m, state);
    }

    public Term(Molecule m) {
        this(1, m);
    }

    @Override
    public String toString() {
        String t = "";
        if (number > 1) {
            t += number.toString();
        }
        t += molecule.toString();
        if (state != null) {
            t += "(" + state.toString() + ")";
        }
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ErrorTerm) {
            return false;
        } else if (o instanceof Term) {
            Term other = (Term) o;
            return (this.molecule.equals(other.molecule)
                    && this.number.equals(other.number)
                    && (this.state == other.state));
        }
        return false;
    }

    @Override
    public int hashCode () {
        if (null != molecule) {
            return this.molecule.hashCode();
        } else {
            return super.hashCode();
        }
    }

    public HashMap<String, Integer> getAtomCount() {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        for (String element: molecule.getAtomCount().keySet()) {
            h.put(element, molecule.getAtomCount().get(element) * number);
        }
        return h;
    }

    public Integer getCharge() {
        return molecule.getCharge() * number;
    }

    public Molecule getMolecule() {
        return this.molecule;
    }

    public Integer getNumber() {
        return this.number;
    }

    public PhysicalState getState() {
        return this.state;
    }
}
