package chemistry_checker;

import java.util.ArrayList;

public class EquationStatement extends Statement {

    private Expression left;
    private Expression right;

    public EquationStatement(Expression l, Expression r) {
        left = l;
        right = r;
    }

    public String toString() {
        return left.toString() + " -> " + right.toString();
    }
}
