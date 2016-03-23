package chemistry_checker;

import java.util.ArrayList;

public class Expression extends ArrayList<Term> {

    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            if (i > 0) {
                b.append(" + ");
            }
            b.append(this.get(i).toString());
        }
        return b.toString();
    }
}
