package chemistry_checker;

import java.util.ArrayList;
import java.util.HashMap;

public class EquationStatement extends Statement {

    Expression left;
    Expression right;

    public EquationStatement(Expression l, Expression r) {
        left = l;
        right = r;
    }

    public String toString() {
        return left.toString() + " -> " + right.toString();
    }

    public boolean isBalancedAtoms() {
        return left.getAtomCount().equals(right.getAtomCount());
    }

    public boolean isBalancedCharge() {
        return left.getCharge() == right.getCharge();
    }

    public boolean isBalanced() {
        return isBalancedAtoms() && isBalancedCharge();
    }
}
