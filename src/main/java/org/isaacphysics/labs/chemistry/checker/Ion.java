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

import java.util.HashMap;

/**
 * Created by Ryan on 15/06/2016.
 * Class for charged particles.
 * Stores the charge, and offload other jobs to Molecule.
 */
public final class Ion extends Formula {

    /**
     * Molecule involved for this particular ion.
     */
    private Molecule molecule;

    /**
     * Charge of the ion.
     */
    private Integer charge;

    /**
     * Constructor function of Ion.
     *
     * @param m Molecule involved for this ion.
     * @param c Charge of ion.
     */
    public Ion(final Molecule m, final Integer c) {
        super();

        molecule = m;
        charge = c;
    }

    @Override
    public Integer getCharge() {
        return charge;
    }

    @Override
    public HashMap<String, Integer> getAtomCount() {
        return molecule.getAtomCount();
    }

    @Override
    public String getDotCode() {

        return "\t" + getDotId() + " [label=\"{&zwj;&zwj;&zwj;&zwj;Ion&zwnj;|\\n"

                + getDotString()

                + "\\n\\n|&zwj;&zwj;&zwj;charge&zwnj;: " + charge

                + "}\",color=\"#614126\"];\n"

                + "\t" + getDotId() + ":s -> " + molecule.getDotId() + ":n;\n"

                + molecule.getDotCode();

    }

    @Override
    public String getDotId() {
        return "ion_" + getdotId();
    }

    @Override
    public String getDotString() {

        String c;

        if (charge == null || charge == 0) {
            c = "";
        } else if (charge == 1) {
            c = "&zwj;&zwj;+&zwnj;";
        } else if (charge == -1) {
            c = "&zwj;&zwj;-&zwnj;";
        } else if (charge > 1) {
            c = "&zwj;&zwj;" + charge.toString() + "+&zwnj;";
        } else {
            c = "&zwj;&zwj;" + Math.abs(charge) + "-&zwnj;";
        }

        return molecule.getDotString() + "&zwnj;" + c;

    }

    @Override
    public boolean equals(final Object o) {

        if (o instanceof Ion) {

            Ion other = (Ion) o;
            return this.charge.equals(other.charge) && this.molecule.equals(other.molecule);

        }

        return false;
    }

    @Override
    public String toString() {

        String c;

        if (charge == null || charge == 0) {
            c = "";
        } else if (charge == 1) {
            c = "^{+}";
        } else if (charge == -1) {
            c = "^{-}";
        } else if (charge > 1) {
            c = "^{" + charge.toString() + "+}";
        } else {
            c = "^{" + Math.abs(charge) + "-}";
        }

        return molecule.toString() + c;
    }

    @Override
    public Integer getAtomicNumber() throws NuclearException {
        throw new NuclearException("Atomic number not defined for Ion.");
    }

    @Override
    public Integer getMassNumber() throws NuclearException {
        throw new NuclearException("Mass number not defined for Ion.");
    }

    /**
     * @return Molecule associated with the ion.
     */
    public Molecule getMolecule() {
        return molecule;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
