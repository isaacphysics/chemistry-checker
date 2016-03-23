package chemistry_checker;

import java.util.ArrayList;

public class Molecule {
    ArrayList<String> groups;

    public Molecule(String g) {
        groups = new ArrayList<String>();
        groups.add(g);
    }

    public Molecule(Molecule m, String g) {
        groups = new ArrayList<String>(m.groups);
        groups.add(g);
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        for (String s : groups) {
            b.append(s.toString());
        }
        return b.toString();
    }
}
