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

import java.util.ArrayList;

/**
 * Class for all physical equations.
 */
final class EquationStatement extends Statement {

    /**
     * The left expression of a equation.
     */
    private Expression left;

    /**
     * The right expression of a equation.
     */
    private Expression right;

    /**
     * The arrow used in equation.
     */
    private AbstractArrow arrow;

    /**
     * Constructor method of EquationStatement.
     *
     * @param l Left expression of equation.
     * @param a Arrow used in equation.
     * @param r Right expression of equation.
     */
    EquationStatement(final Expression l, final AbstractArrow a, final Expression r) {
        left = l;
        right = r;
        arrow = a;
    }

    @Override
    public String toString() {
        return left.toString() + arrow.toString() + right.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof EquationStatement) {
            EquationStatement other = (EquationStatement) o;
            //System.out.println("Checking equality: ");
            //System.out.println(this.left.equals(other.left));
            //System.out.println(this.arrow.equals(other.arrow));
            //System.out.println(this.right.equals(other.right));
            return this.left.equals(other.left)
                    && this.arrow.equals(other.arrow)
                    && this.right.equals(other.right);
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
     *
     * @return True if atom counts are balanced.
     */
    boolean isBalancedAtoms() {
        return !containsError() && left.getAtomCount().equals(right.getAtomCount());
    }

    /**
     * Checks if charges on both sides of equation are balanced.
     *
     * @return True if charges are balanced.
     */
    boolean isBalancedCharge() {
        return !containsError() && (left.getCharge().equals(right.getCharge()));
    }

    /**
     * Checks if both numbers and charges on both sides of equation are balanced.
     *
     * @return True if both atom counts and charges are balanced.
     */
    boolean isBalanced() {
        return isBalancedAtoms() && isBalancedCharge();
    }

    /**
     * Getter function. Returns left expression.
     *
     * @return The left expression.
     */
    Expression getLeftExpression() {
        return this.left;
    }

    /**
     * Getter function. Returns right expression.
     *
     * @return The right expression.
     */
    Expression getRightExpression() {
        return this.right;
    }

    /**
     * Getter function. Returns arrow.
     *
     * @return The arrow used in equation.
     */
    AbstractArrow getArrow() {
        return this.arrow;
    }

    @Override
    public String getDotCode() {
        return "digraph chemical_syntax_tree {\n"
                + "\tnode [shape=record,penwidth=2,splines=ortho];\n\n"
                + "\tequation [label=\"{&zwj;&zwj;&zwj;&zwj;Equation&zwnj;|\\n"

                + left.getDotString() + arrow.getDotString() + right.getDotString()

                + "\\n\\n|<left>&zwj;&zwj;&zwj;left&zwnj;|<arrow>&zwj;&zwj;&zwj;arrow&zwnj;|"
                + "<right>&zwj;&zwj;&zwj;right&zwnj;}\",color=\"#bb2828\"];\n"

                + "\tequation:left:w -> " + left.getDotId() + ";\n"
                + "\tequation:arrow -> " + "arrow:w" + ";\n"
                + "\tequation:right:e -> " + right.getDotId() + ";\n"

                + left.getDotCode() + arrow.getDotCode() + right.getDotCode()

                + "}\n";
    }

    @Override
    public boolean weaklyEquivalent(final Statement s) {

        if (!(s instanceof EquationStatement)) {
            return false;
        }

        EquationStatement other = (EquationStatement) s;

        return left.weaklyEquivalent(other.left) && right.weaklyEquivalent(other.right);
    }

    /**
     * Checks if both statements are weakly equivalent, AND contains same state symbols in relevant terms.
     *
     * E.g. 2NaOH (aq), 20NaOH (aq) should return true.
     *
     * Should assert that both expressions are weakly equivalent before executing this code,
     * as it is quite time consuming.
     *
     * @param s Statement to be compared against.
     * @return True if both statements are weakly equivalent, and contains same states symbols in all terms.
     */
    boolean sameStateSymbols(final Statement s) {

        if (!(s instanceof EquationStatement)) {
            return false;
        }

        EquationStatement other = (EquationStatement) s;

        return left.sameStateSymbols(other.left) && right.sameStateSymbols(other.right);
    }

    /**
     * Checks if both statements are weakly equivalent, AND contains same coefficients in relevant terms.
     *
     * E.g. 2NaOH (aq), 2NaOH (g) should return true.
     *
     * Should assert that both expressions are weakly equivalent before executing this code,
     * as it is quite time consuming.
     *
     * @param s Statement to be compared against.
     * @return True if both statements are weakly equivalent, and contains same coefficients in relevant terms.
     */
    boolean sameCoefficients(final Statement s) {

        if (!(s instanceof EquationStatement)) {
            return false;
        }

        EquationStatement other = (EquationStatement) s;

        return left.sameCoefficients(other.left) && right.sameCoefficients(other.right);
    }

    @Override
    public ArrayList<Term> getWrongTerms(final Statement e) {

        if (!(e instanceof EquationStatement)) {
            return new ArrayList<>();
        }

        EquationStatement expr = (EquationStatement) e;

        ArrayList<Term> toReturn = left.getWrongTerms(expr.left);

        toReturn.addAll(right.getWrongTerms(expr.right));

        return toReturn;
    }

    @Override
    public boolean check(final Statement input) {

        if (!(input instanceof EquationStatement)) {
            // Not even EquationStatement
            System.out.println("Not an EquationStatement!");
            return false;
        }

        if (input.containsError()) {
            // Error term exists in argument
            System.out.printf("Input: %s\nPlease correct the error.\n", input.toString());
            return false;
        }

        EquationStatement equationInput = (EquationStatement) input;

        if (!equationInput.isBalanced()) {

            if (!equationInput.isBalancedAtoms()) {

                // Atom count is unbalanced
                System.out.printf("Total atoms LHS: %s\nTotal atoms RHS: %s\n",
                        equationInput.left.getAtomCount(), equationInput.right.getAtomCount());

                System.out.println("Atom counts are unbalanced.");

            } else {
                // Charge is unbalanced
                System.out.printf("Total charge LHS: %s\nTotal charge LHS: %s\n",
                        equationInput.left.getCharge(), equationInput.right.getCharge());

                System.out.println("Charges are unbalanced.");
            }

            return false;
        }

        if (equals(input)) {
            return true;
        }

        if (!weaklyEquivalent(input)) {

            System.out.println("Unrelated terms exist in equation, and/or some terms are missing.");
            System.out.println("Wrong terms: " + getWrongTerms(equationInput));

            return false;
        }

        if (!this.arrow.equals(equationInput.arrow)) {

            System.out.println("Wrong arrow used.");

            return false;
        }

        if (!sameCoefficients(input)) {

            System.out.println("Some terms have incorrect coefficients.");
            System.out.println("Wrong terms: " + getWrongTerms(equationInput));

            return false;
        }

        if (!sameStateSymbols(input)) {

            System.out.println("Some terms have incorrect state symbols.");
            System.out.println("Wrong terms: " + getWrongTerms(equationInput));

            return false;
        }

        System.out.println("Coefficient/state symbols are misplaced.");
        System.out.println("Wrong terms: " + getWrongTerms(equationInput));
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
