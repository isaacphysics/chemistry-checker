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

public class EquationStatement extends Statement
{
    /**
     * The left expression of a equation
     */
    private Expression left;

    /**
     * The right expression of a equation
     */
    private Expression right;

    /**
     * The arrow used in equation
     */
    private AbstractArrow arrow;

    /**
     * Constructor method of EquationStatement.
     * @param l Left expression of equation.
     * @param r Right expression of equation.
     */
    public EquationStatement(Expression l, AbstractArrow a, Expression r)
    {
        left = l;
        right = r;
        arrow = a;
    }

    @Override
    public String toString() {
        return left.toString() + arrow.toString() + right.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof EquationStatement)
        {
            EquationStatement other = (EquationStatement) o;
            //System.out.println("Checking equality: ");
            //System.out.println(this.left.equals(other.left));
            //System.out.println(this.arrow.equals(other.arrow));
            //System.out.println(this.right.equals(other.right));
            return this.left.equals(other.left) &&
                    this.arrow.equals(other.arrow) &&
                    this.right.equals(other.right);
        }
        return false;
    }

    @Override
    public boolean containsError() {
        return left.containsError() || right.containsError();
    }

    /**
     * Checks if numbers of atoms on both sides of equation are balanced.
     * Subparticles like electrons are not considered.
     */
    boolean isBalancedAtoms() {
        return !containsError() && left.getAtomCount().equals(right.getAtomCount());
    }

    /**
     * Checks if charges on both sides of equation are balanced.
     */
    boolean isBalancedCharge() {
        return !containsError() && (left.getCharge().equals(right.getCharge()));
    }

    /**
     * Checks if both numbers and charges on both sides of equation are balanced.
     */
    boolean isBalanced() {
        return isBalancedAtoms() && isBalancedCharge();
    }

    /**
     * Getter function. Returns left expression.
     */
    Expression getLeftExpression() {
        return this.left;
    }

    /**
     * Getter function. Returns right expression.
     */
    Expression getRightExpression() {
        return this.right;
    }

    /**
     * Given an EquationStatement, check if its left expression is equivalent to our left expression.
     * @param eqnStatement EquationStatement to be compared against.
     */
    private boolean sameMoleculesLeft(EquationStatement eqnStatement)
    {
        return left.containsAll(eqnStatement.left) && eqnStatement.left.containsAll(left);
    }

    /**
     * Given an EquationStatement, check if its right expression is equivalent to our right expression.
     * @param eqnStatement EquationStatement to be compared against.
     */
    private boolean sameMoleculesRight(EquationStatement eqnStatement)
    {
        return right.containsAll(eqnStatement.right) && eqnStatement.right.containsAll(right);
    }

    @Override
    public boolean sameMolecules(Statement statement)
    {
        if (statement instanceof EquationStatement)
        {
            EquationStatement eqnStatement = (EquationStatement) statement;
            return sameMoleculesLeft(eqnStatement) && sameMoleculesRight(eqnStatement);
        }
        else
            return false;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("digraph chemical_syntax_tree {\n");
        result.append("\tnode [shape=record,penwidth=2,splines=ortho];\n\n");

        result.append("\tequation [label=\"{&zwj;&zwj;&zwj;&zwj;Equation&zwnj;|\\n");
        result.append(left.getDotString());
        result.append(arrow.getDotString());
        result.append(right.getDotString());
        result.append("\\n\\n|<left>&zwj;&zwj;&zwj;left&zwnj;|<arrow>&zwj;&zwj;&zwj;arrow&zwnj;|" +
                "<right>&zwj;&zwj;&zwj;right&zwnj;}\",color=\"#bb2828\"];\n");

        result.append("\tequation:left:w -> ");
        result.append(left.getDotId());
        result.append(";\n");
        result.append("\tequation:arrow -> ");
        result.append("arrow:w");
        result.append(";\n");
        result.append("\tequation:right:e -> ");
        result.append(right.getDotId());
        result.append(";\n");

        result.append(left.getDotCode());
        result.append(arrow.getDotCode());
        result.append(right.getDotCode());
        result.append("}\n");
        return result.toString();
    }

}
