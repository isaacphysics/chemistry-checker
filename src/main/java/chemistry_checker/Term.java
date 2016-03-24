package chemistry_checker;

import java.util.HashMap;

public class Term implements CountableCharge {

    public enum PhysicalState {
        SOLID,
        LIQUID,
        GAS,
        AQUEOUS
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
            this.state = PhysicalState.SOLID;
        } else if (s.equals("l")) {
            this.state = PhysicalState.LIQUID;
        } else if (s.equals("g")) {
            this.state = PhysicalState.GAS;
        } else if (s.equals("aq")) {
            this.state = PhysicalState.AQUEOUS;
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
