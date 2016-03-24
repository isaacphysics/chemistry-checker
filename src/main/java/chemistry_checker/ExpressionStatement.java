package chemistry_checker;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpressionStatement extends Statement implements CountableCharge {

    private Expression terms;

    public ExpressionStatement(Expression e) {
        terms=e;
    }

    public String toString() {
        return terms.toString();
    }

    public HashMap<String, Integer> getAtomCount() {
        return terms.getAtomCount();
    }

    public Integer getCharge() {
        return terms.getCharge();
    }
}
