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
 * Created by Ryan on 15/06/2016.
 * Element: Special class of molecule that consists of only one type of atom.
 */
public final class Element extends Molecule {
    /**
     * The name of atom involved in this element.
     */
    private String element;

    /**
     * Subscript of element.
     * For example number of H2 is 2.
     */
    private Integer number;

    /**
     * Constructor function of Element.
     * @param e Element
     * @param n Subscript of element
     */
    public Element(final String e, final Integer n) {
        super();
        element = e;
        number = n;
    }

    @Override
    public String toString() {

        if (number > 1) {
            return element + number.toString();
        } else {
            return element;
        }

    }

    @Override
    public boolean equals(final Object o) {

        if (o instanceof Element) {
            Element other = (Element) o;
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

    @Override
    public HashMap<String, Integer> getAtomCount() {

        HashMap<String, Integer> h = new HashMap<>();

        if (element != null) {
            h.put(element, number);
        }

        return h;
    }

    //public Integer getCharge() {return charge;}

    @Override
    public String getDotId() {
        return "group_" + getdotId();
    }

    @Override
    public String getDotCode() {
        StringBuilder result = new StringBuilder();

        result.append("\t");
        result.append(getDotId());

        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Element&zwnj;|\\n");
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

        result.append("}\",color=\"#4c7fbe\"];\n");
        return result.toString();
    }

    @Override
    public String getDotString() {

        if (number > 1) {
            return element + "&zwj;" + number.toString();
        } else {
            return element;
        }

    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
