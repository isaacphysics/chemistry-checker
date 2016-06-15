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

public class Group implements Countable {

    private Molecule molecule;
    private String element;
    private Integer number;
    private Integer charge;
    private static int dotIdTracker = 0;
    private int dotId;

    public Group(String e, Integer n) {
        element = e;
        number = n;
        charge = 0;
        dotId = dotIdTracker;
        dotIdTracker += 1;
    }

    public Group(Molecule m, Integer n) {
        molecule = m;
        number = n;
        charge = molecule.getCharge() * n;
        dotId = dotIdTracker;
        dotIdTracker += 1;
    }

    public Group(Molecule m, Integer n, Integer c) {
        this(m, n);
        // TODO: Check that molecule not charged!
        charge = c;
    }

    public Group(String e, Integer n, Integer c, boolean multiplyCharge) {
        this(e, n);
        // TODO: Learn Chemistry - how do we deal with charge here?
        if (multiplyCharge) {
            charge = c * number;
        } else {
            charge = c;
        }
    }

    @Override
    public String toString() {
        String c;
        if (null == charge || 0 == charge) {
            c = "";
        } else if (1 == charge) {
            c = "^{+}";
        } else if (-1 == charge) {
            c = "^{-}";
        } else if (charge > 1) {
            c = "^{" + charge.toString() + "+}";
        } else {
            c = "^{" + Math.abs(charge) + "-}";
        }
        if (null != molecule) {
            return "(" + molecule.toString() + ")" + number.toString() + c;
        } else {
            return element + (number > 1 ? number.toString() : "") + c;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Group) {
            Group other = (Group) o;
            return this.toString().equals(other.toString());
        }
        return false;
    }

/*    private static Integer chargeToInt(String c) {
        if (c.equals("^{+}")) {
            return 1;
        } else if (c.equals("^{-}")) {
            return -1;
        } else if (c.contains("+")) {
            String stringVal = c.replace("^{", "").replace("}", "").replace("+", "");
            return Integer.parseInt(stringVal);
        } else if (c.contains("-")) {
            String stringVal = c.replace("^{", "").replace("}", "").replace("-", "");
            return Integer.parseInt("-" + stringVal);
        } else {
            return 0;
        }
    }*/

    public HashMap<String, Integer> getAtomCount() {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        if (null != element) {
            if (!element.equals("e")) {
                h.put(element, number);
            }
        } else {
            for (String element : molecule.getAtomCount().keySet()) {
                h.put(element, molecule.getAtomCount().get(element) * number);
            }
        }
        return h;
    }

    public Integer getCharge() {
        return charge;
    }

    public String getDotId() {
        return "group_" + dotId;
    }

    public String getDotCode() {
        StringBuilder result = new StringBuilder();
        result.append("\t");
        result.append(getDotId());
        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Group&zwnj;|\\n");
        result.append(getDotString());
        result.append("\\n\\n|&zwj;&zwj;&zwj;number&zwnj;: ");
        result.append(number);
        result.append("|&zwj;&zwj;&zwj;element&zwnj;");
        if (element != null) {
            result.append(": ");
            result.append(element);
        } else {
            result.append(": none");
        }
        result.append("|&zwj;&zwj;&zwj;charge&zwnj;: ");
        result.append(charge);
        result.append("|&zwj;&zwj;&zwj;molecule&zwnj;");
        if (molecule == null) {
            result.append(": none");
        }
        result.append("}\",color=\"#4c7fbe\"];\n");
        if (molecule != null) {
            result.append("\t");
            result.append(getDotId());
            result.append(":s -> ");
            result.append(molecule.getDotId());
            result.append(":n;\n");
            result.append(molecule.getDotCode());
        }
        return result.toString();
    }

    public String getDotString() {
        String c;
        if (null == charge || 0 == charge) {
            c = "";
        } else if (1 == charge) {
            c = "&zwj;&zwj;+&zwnj;";
        } else if (-1 == charge) {
            c = "&zwj;&zwj;-&zwnj;";
        } else if (charge > 1) {
            c = "&zwj;&zwj;" + charge.toString() + "+&zwnj;";
        } else {
            c = "&zwj;&zwj;" + Math.abs(charge) + "-&zwnj;";
        }
        if (null != molecule) {
            return "(" + molecule.getDotString() + ")&zwj;" + number.toString() + "&zwnj;" + c;
        } else {
            return element + (number > 1 ? "&zwj;"+number.toString()+"&zwnj;" : "") + c;
        }
    }
}
