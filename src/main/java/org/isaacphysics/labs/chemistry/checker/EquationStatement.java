package org.isaacphysics.labs.chemistry.checker;

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

    public boolean equals(Object o) {
        if (o instanceof EquationStatement) {
            EquationStatement other = (EquationStatement) o;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }
        return false;
    }

    public boolean containsError() {
        return left.containsError() || right.containsError();
    }

    public boolean isBalancedAtoms() {
        return !containsError() && left.getAtomCount().equals(right.getAtomCount());
    }

    public boolean isBalancedCharge() {
        return !containsError() && (left.getCharge() == right.getCharge());
    }

    public boolean isBalanced() {
        return isBalancedAtoms() && isBalancedCharge();
    }

}
