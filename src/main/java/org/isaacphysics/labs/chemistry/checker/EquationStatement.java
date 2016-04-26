/**
 * Copyright 2016 James Sharkey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.isaacphysics.labs.chemistry.checker;

public class EquationStatement extends Statement {

    private Expression left;
    private Expression right;

    public EquationStatement(Expression l, Expression r) {
        left = l;
        right = r;
    }

    @Override
    public String toString() {
        return left.toString() + " -> " + right.toString();
    }

    @Override
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
        return !containsError() && (left.getCharge().equals(right.getCharge()));
    }

    public boolean isBalanced() {
        return isBalancedAtoms() && isBalancedCharge();
    }

    public Expression getLeftExpression() {
        return this.left;
    }

    public Expression getRightExpression() {
        return this.right;
    }

    public boolean sameMoleculesLeft(EquationStatement eqnStatement) {
        return left.containsAll(eqnStatement.left) && eqnStatement.left.containsAll(left);
    }

    public boolean sameMoleculesRight(EquationStatement eqnStatement) {
        return right.containsAll(eqnStatement.right) && eqnStatement.right.containsAll(right);
    }

    public boolean sameMolecules(Statement statement) {
        if (statement instanceof EquationStatement) {
            EquationStatement eqnStatement = (EquationStatement) statement;
            return sameMoleculesLeft(eqnStatement) && sameMoleculesRight(eqnStatement);
        } else {
            return false;
        }
    }

    public String getDotCode() {
        StringBuilder result = new StringBuilder();
        result.append("digraph chemical_syntax_tree {\n");
        result.append("\tnode [shape=record,penwidth=2,splines=ortho];\n\n");

        result.append("\tequation [label=\"{&zwj;&zwj;&zwj;&zwj;Equation&zwnj;|\\n");
        result.append(left.getDotString());
        result.append(" &#8594; ");
        result.append(right.getDotString());
        result.append("\\n\\n|<left>&zwj;&zwj;&zwj;left&zwnj;|<right>&zwj;&zwj;&zwj;right&zwnj;}\",color=\"#bb2828\"];\n");

        result.append("\tequation:left:w -> ");
        result.append(left.getDotId());
        result.append(";\n");
        result.append("\tequation:right:e -> ");
        result.append(right.getDotId());
        result.append(";\n");

        result.append(left.getDotCode());
        result.append(right.getDotCode());
        result.append("}\n");
        return result.toString();
    }

}
