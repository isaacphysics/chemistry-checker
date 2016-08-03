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
import java.util.HashMap;

/**
 *
 */
public final class ExpressionStatement extends Statement implements Countable {

    /**
     * Expression involved in statement.
     */
    private Expression expr;

    /**
     * Saved atom count.
     */
    private HashMap<String, Fraction> savedAtomCount = null;

    /**
     * Constructor method of ExpressionStatement.
     * @param e Expression involved in the statement.
     */
    public ExpressionStatement(final Expression e) {
        expr = e;
    }

    @Override
    public String toString() {
        return expr.toString();
    }

    @Override
    public String getDotId() {
        return null;
    }

    @Override
    public boolean equals(final Object o) {

        if (o instanceof ExpressionStatement) {
            ExpressionStatement other = (ExpressionStatement) o;
            return this.expr.equals(other.expr);
        }

        return false;

    }

    @Override
    public boolean containsError() {
        return expr.containsError();
    }

    @Override
    public HashMap<String, Fraction> getAtomCount() {
        if (savedAtomCount == null) {
            savedAtomCount = expr.getAtomCount();
        }
        return savedAtomCount;
    }

    @Override
    public Fraction getCharge() {
        return expr.getCharge();
    }

    /**
     * Getter method. Gets the expression involved in this statement.
     * @return The expression involved in this statement.
     */
    Expression getExpression() {
        return this.expr;
    }

    @Override
    public String getDotCode() {

        return "digraph chemical_syntax_tree {\n"
                + "\tnode [shape=record,penwidth=2,splines=ortho];\n\n"
                + expr.getDotCode()
                + "}\n";

    }

    @Override
    public boolean weaklyEquivalent(final Statement s) {

        if (!(s instanceof ExpressionStatement)) {
            return false;
        }

        ExpressionStatement other = (ExpressionStatement) s;

        return expr.weaklyEquivalent(other.expr);
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
     * @return True if both statemetns are weakly equivalent, and contains same state symbols in relevant terms.
     */
    boolean sameStateSymbols(final Statement s) {

        if (!(s instanceof ExpressionStatement)) {
            return false;
        }

        ExpressionStatement other = (ExpressionStatement) s;

        return expr.sameStateSymbols(other.expr);
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
     * @return True if both statement are weakly equivalent and contains same coefficients in relevant terms.
     */
    boolean sameCoefficients(final Statement s) {

        if (!(s instanceof ExpressionStatement)) {
            return false;
        }

        ExpressionStatement other = (ExpressionStatement) s;

        return expr.sameCoefficients(other.expr);
    }

    @Override
    public String getDotString() {
        return null;
    }

    @Override
    public ArrayList<Term> getWrongTerms(final Statement e) {

        if (e instanceof ExpressionStatement) {
            return expr.getWrongTerms(((ExpressionStatement) e).expr);
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public boolean check(final Statement input) {

        if (!(input instanceof ExpressionStatement)) {
            // Not even ExpressionStatement
            System.out.println("Not an ExpressionStatement!");
            return false;
        }

        if (input.containsError()) {
            // Error term exists in argument
            System.out.printf("Input: %s\nPlease correct the error.\n", input.toString());
            return false;
        }

        if (equals(input)) {
            return true;
        }

        ExpressionStatement exprInput = (ExpressionStatement) input;

        if (!weaklyEquivalent(input)) {
            // not even weakly equivalent: some molecules are unrelated to solution
            System.out.println("Unrelated terms exist in equation, and/or missing some terms.");
            System.out.println("Wrong terms: " + getWrongTerms(exprInput));

            return false;
        }

        if (!sameCoefficients(input)) {
            // wrong coefficients
            System.out.println("Some terms have incorrect coefficients.");
            System.out.println("Wrong terms: " + getWrongTerms(exprInput));

            return false;
        }

        if (!sameStateSymbols(input)) {
            // wrong state symbols
            System.out.println("Some terms have incorrect state symbols.");
        } else {
            // correct coefficients, state symbols, but misplaced.
            System.out.println("Coefficient/state symbols are misplaced.");
        }

        System.out.println("Wrong terms: " + getWrongTerms(exprInput));
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
