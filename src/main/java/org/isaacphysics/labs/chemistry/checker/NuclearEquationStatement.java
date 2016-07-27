/**
 * Copyright 2016 Ryan Lau
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
 * Special class for nuclear equations.
 * Very similar to EquationStatement, it holds two expressions, and checks if the mass and atomic numbers of both sides
 * are balanced.
 *
 * Created by Ryan on 20/06/2016.
 */
public final class NuclearEquationStatement extends Statement {

    /**
     * The left expression of a equation.
     */
    private Expression left;

    /**
     * The right expression of a equation.
     */
    private Expression right;

    /**
     * Constructor method of NuclearEquationStatement.
     * @param l Left expression of equation.
     * @param r Right expression of equation.
     */
    public NuclearEquationStatement(final Expression l, final Expression r) {
        left = l;
        right = r;
    }

    @Override
    public String toString() {
        return left.toString() + " -> " + right.toString();
    }

    @Override
    public boolean equals(final Object o) {

        if (o instanceof NuclearEquationStatement) {
            NuclearEquationStatement other = (NuclearEquationStatement) o;

            return this.left.equals(other.left)
                    && this.right.equals(other.right);
        }

        return false;
    }

    @Override
    public boolean containsError() {
        return left.containsError() || right.containsError();
    }

    /**
     * Getter function. Returns left expression.
     *
     * @return Left expression.
     */
    public Expression getLeftExpression() {
        return this.left;
    }

    /**
     * Getter function. Returns right expression.
     *
     * @return Right expression.
     */
    public Expression getRightExpression() {
        return this.right;
    }

    /**
     * Checks if mass numbers on both sides of equation are balanced.
     *
     * @return True if mass numbers are balanced.
     */
    boolean isBalancedMass() {

        try {

            return !containsError() && left.getMassCount().equals(right.getMassCount());

        } catch (NuclearException e) {

            return false;

        }

    }

    /**
     * Checks if atomic numbers on both sides of equation are balanced.
     *
     * @return True if atomic numbers are balanced.
     */
    boolean isBalancedAtom() {

        try {

            return !containsError() && left.getAtomicCount().equals(right.getAtomicCount());

        } catch (NuclearException e) {

            return false;

        }
    }

    /**
     * Checks if both mass numbers and atomic numbers on both sides of equation are balanced.
     *
     * @return True if both mass numbers and atomic numbers are balanced.
     */
    boolean isBalanced() {
        return isBalancedAtom() && isBalancedMass();
    }

    /**
     * Checks if the atomic number of all isotopes matches element name.
     *
     * @return True if all isotopes have valid atomic numbers.
     */
    boolean isValid() {
        return left.isValidAtomicNumber() && right.isValidAtomicNumber();
    }

    @Override
    String getDotCode() {

        return  "digraph chemical_syntax_tree {\n"
                + "\tnode [shape=record,penwidth=2,splines=ortho];\n\n"
                + "\tequation [label=\"{&zwj;&zwj;&zwj;&zwj;Nuclear Equation&zwnj;|\\n"

                + left.getDotString() + " &#8594; " + right.getDotString()

                + "\\n\\n|<left>&zwj;&zwj;&zwj;left&zwnj;|<right>&zwj;&zwj;&zwj;right&zwnj;}\",color=\"#bb2828\"];\n"

                + "\tequation:left:w -> " + left.getDotId() + ";\n"

                + "\tequation:right:e -> " + right.getDotId() + ";\n"

                + left.getDotCode() + right.getDotCode() + "}\n";

    }

    @Override
    public boolean weaklyEquivalent(final Statement s) {

        if (!(s instanceof NuclearEquationStatement)) {
            return false;
        }

        NuclearEquationStatement other = (NuclearEquationStatement) s;

        return left.weaklyEquivalent(other.left) && right.weaklyEquivalent(other.right);
    }

    @Override
    public ArrayList<Term> getWrongTerms(final Statement e) {

        if (!(e instanceof NuclearEquationStatement)) {
            return new ArrayList<>();
        }

        NuclearEquationStatement expr = (NuclearEquationStatement) e;

        ArrayList<Term> toReturn = left.getWrongTerms(expr.left);

        toReturn.addAll(right.getWrongTerms(expr.right));

        return toReturn;
    }

    @Override
    public boolean check(final Statement input) {

        if (!(input instanceof NuclearEquationStatement)) {
            // Not even nuclear equation statement
            System.out.println("Not a NuclearEquationStatement!");
            return false;
        }

        if (input.containsError()) {
            // Error term exists in argument
            System.out.printf("Input: %s\nPlease correct the error.\n", input.toString());
            return false;
        }

        NuclearEquationStatement equationInput = (NuclearEquationStatement) input;

        if (!equationInput.isBalanced()) {
            if (!equationInput.isBalancedMass()) {
                // Mass number not balanced.
                try {
                    System.out.printf("Total mass# LHS: %d\nTotal mass# RHS: %d\n",
                            equationInput.left.getMassCount(), equationInput.right.getMassCount());
                } catch (Exception e) {
                    System.out.printf("Equation is not nuclear!");
                }

                System.out.println("Mass numbers are unbalanced.");

            } else {

                // Atomic number not balanced.
                try {
                    System.out.printf("Total atomic# LHS: %d\nTotal atomic# RHS: %d\n",
                            equationInput.left.getAtomicCount(), equationInput.right.getAtomicCount());
                } catch (Exception e) {
                    System.out.printf("Equation is not nuclear!");
                }

                System.out.println("Atomic numbers are unbalanced.");
            }

            return false;
        }

        if (!equationInput.isValid()) {
            // invalid atomic numbers
            System.out.println("There are elements with invalid atomic numbers.");
            return false;
        }

        if (equals(equationInput)) {
            return true;
        }

        if (!weaklyEquivalent(equationInput)) {
            // not weakly equivalent: exists irrelevant terms in equation
            System.out.println("Unrelated terms exist in equation, or some terms are missing.");
        } else {
            // wrong coefficients in some terms
            System.out.println("Some terms have incorrect coefficients.");
        }

        System.out.println("Wrong terms: " + getWrongTerms(equationInput));
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
