package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

public class ExpressionStatement extends Statement implements Countable {

    Expression terms;

    public ExpressionStatement(Expression e) {
        terms=e;
    }

    public String toString() {
        return terms.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof ExpressionStatement) {
            ExpressionStatement other = (ExpressionStatement) o;
            return this.terms.equals(other.terms);
        }
        return false;
    }

    public HashMap<String, Integer> getAtomCount() {
        return terms.getAtomCount();
    }

    public Integer getCharge() {
        return terms.getCharge();
    }
}
