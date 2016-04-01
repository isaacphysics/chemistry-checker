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
import java.util.HashMap;

public class Molecule implements Countable {
    private ArrayList<Group> groups;

    public Molecule(Group g) {
        groups = new ArrayList<Group>();
        groups.add(g);
    }

    public Molecule(Molecule m, Group g) {
        groups = new ArrayList<Group>(m.groups);
        groups.add(g);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Group g : groups) {
            b.append(g.toString());
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Molecule) {
            Molecule other = (Molecule) o;
            return this.toString().equals(other.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return groups.size();
    }

    public HashMap<String, Integer> getAtomCount() {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        for (Group g : groups) {
            for (String element : g.getAtomCount().keySet()) {
                if (!h.containsKey(element)) {
                    h.put(element,  g.getAtomCount().get(element));
                } else {
                    h.put(element, h.get(element) + g.getAtomCount().get(element));
                }
            }
        }
        return h;
    }

    public Integer getCharge() {
        Integer c = 0;
        for (Group g : groups) {
            c += g.getCharge();
        }
        return c;
    }
}
