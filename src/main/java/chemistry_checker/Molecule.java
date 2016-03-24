package chemistry_checker;

import java.util.ArrayList;
import java.util.HashMap;

public class Molecule {
    ArrayList<Group> groups;

    public Molecule(Group g) {
        groups = new ArrayList<Group>();
        groups.add(g);
    }

    public Molecule(Molecule m, Group g) {
        groups = new ArrayList<Group>(m.groups);
        groups.add(g);
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Group g : groups) {
            b.append(g.toString());
        }
        return b.toString();
    }

    public HashMap<String, Integer> getAtomCount() {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        for (Group g : groups) {
            for (String e : g.getAtomCount().keySet()) {
                if (!h.containsKey(e)) {
                    h.put(e,  g.getAtomCount().get(e));
                } else {
                    h.put(e, h.get(e) + g.getAtomCount().get(e));
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
