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
 * Class for storing water of crystallization.
 * Created by Ryan on 22/06/2016.
 */
public final class Hydrate extends Formula {

    /**
     * Formula of the dehydrated salt.
     */
    private Compound compound;

    /**
     * Number of water molecules per formula.
     */
    private Integer waterCount;

    /**
     * Saved atom count.
     */
    private HashMap<String, Fraction> savedAtomCount = null;

    /**
     * Constructor function for hydrate.
     * @param compound Formula of associated salt compound.
     * @param count Number of water molecules per formula.
     */
    public Hydrate(final Compound compound, final Integer count) {
        this.compound = compound;
        this.waterCount = count;
    }

    @Override
    public Fraction getCharge() {
        return new Fraction(0, 1);
    }

    @Override
    public HashMap<String, Fraction> getAtomCount() {

        if (savedAtomCount == null) {
            savedAtomCount = compound.getAtomCount();

            // Put water atoms...
            if (savedAtomCount.containsKey("H")) {
                savedAtomCount.put("H", savedAtomCount.get("H").plus(2 * waterCount));
            } else {
                savedAtomCount.put("H", new Fraction(2 * waterCount, 1));
            }

            if (savedAtomCount.containsKey("O")) {
                savedAtomCount.put("O", savedAtomCount.get("O").plus(waterCount));
            } else {
                savedAtomCount.put("O", new Fraction(waterCount, 1));
            }
        }

        return savedAtomCount;
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Hydrate)) {
            return false;
        }

        Hydrate other = (Hydrate) o;

        return compound.equals(other.compound) && waterCount.equals(other.waterCount);
    }

    @Override
    public String toString() {

        if (waterCount > 1) {
            return compound.toString() + "." + waterCount.toString() + "H2O";
        } else {
            return compound.toString() + ".H2O";
        }

    }

    @Override
    public String getDotId() {
        return "hydrate_" + getdotId();
    }

    @Override
    public String getDotCode() {

        return "\t" + getDotId()

                + " [label=\"{&zwj;&zwj;&zwj;&zwj;Hydrate&zwnj;|\\n"

                + getDotString()
                + "\\n\\n|&zwj;&zwj;&zwj;water per formula&zwnj;: " + waterCount + "}\",color=\"#614126\"];\n"

                + "\t" + getDotId() + ":s -> " + compound.getDotId() + ":n;\n"

                + compound.getDotCode();

    }

    @Override
    public String getDotString() {

        if (waterCount > 1) {
            return compound.toString() + "&#183;" + waterCount.toString() + "H2O";
        } else {
            return compound.toString() + "&#183;H2O";
        }

    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
