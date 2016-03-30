package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

public class Term implements Countable {

    public enum PhysicalState {
        s,
        l,
        g,
        aq
    }

    Molecule molecule;
    Integer number;
    PhysicalState state;

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

    public boolean equals(Object o) {
        if (o instanceof ErrorTerm) {
            return false;
        } else if (o instanceof Term) {
            Term other = (Term) o;
            return (this.molecule.equals(other.molecule) && this.number.equals(other.number) && (this.state == other.state));
        }
        return false;
    }

    public int hashCode () {
        return this.molecule.groups.size();
    }

    public HashMap<String, Integer> getAtomCount() {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        for (String e: molecule.getAtomCount().keySet()) {
            h.put(e, molecule.getAtomCount().get(e) * number);
        }
        return h;
    }

    public Integer getCharge() {
        return molecule.getCharge() * number;
    }
}
