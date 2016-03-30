package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

public class Group implements Countable {

    Molecule molecule;
    String element;
    Integer number;
    Integer charge;

    public Group(String e, Integer n) {
        element = e;
        number = n;
        charge = 0;
    }

    public Group(Molecule m, Integer n) {
        molecule = m;
        number = n;
        charge = molecule.getCharge() * n;
    }

    public Group(Molecule m, Integer n, String c) {
        this(m, n);
        // Assert that molecule not charged!
        charge = chargeToInt(c);
    }

    public Group(String e, Integer n, String c, boolean multiplyCharge) {
        this(e, n);
        // TODO: Learn Chemistry - how do we deal with charge here?
        if (multiplyCharge) {
            charge = chargeToInt(c) * number;
        } else {
            charge = chargeToInt(c);
        }
    }

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
        if (null != molecule) {
            return "(" + molecule.toString() + ")" + number.toString() + c;
        } else {
            return element.toString() + (number > 1 ? number.toString() : "") + c;
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Group) {
            Group other = (Group) o;
            return this.toString() == other.toString();
        }
        return false;
    }

    private static Integer chargeToInt(String c) {
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
    }

    public HashMap<String, Integer> getAtomCount() {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        if (null != element) {
            if (!element.equals("e")) {
                h.put(element, number);
            }
        } else {
            for (String e : molecule.getAtomCount().keySet()) {
                h.put(e, molecule.getAtomCount().get(e) * number);
            }
        }
        return h;
    }

    public Integer getCharge() {
        return charge;
    }
}
