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
import java.util.HashMap;

/**
 * Statement containing nuclear expressions.
 * Created by Ryan on 21/06/2016.
 */
public final class NuclearExpressionStatement extends Statement implements Countable {

    /**
     * Expression involved in statement.
     */
    private Expression expr;

    /**
     * Constructor method of ExpressionStatement.
     * @param e Expression involved in the statement.
     */
    public NuclearExpressionStatement(final Expression e) {
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

        if (o instanceof NuclearExpressionStatement) {

            NuclearExpressionStatement other = (NuclearExpressionStatement) o;
            return this.expr.equals(other.expr);

        }

        return false;
    }

    @Override
    public boolean containsError() {
        return expr.containsError();
    }

    @Override
    public HashMap<String, Integer> getAtomCount() {
        return expr.getAtomCount();
    }

    @Override
    public Integer getCharge() {
        return expr.getCharge();
    }

    /**
     * Returns the expression contained in the statement.
     * @return Expression of the statement.
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

        if (!(s instanceof NuclearExpressionStatement)) {
            return false;
        }

        NuclearExpressionStatement other = (NuclearExpressionStatement) s;

        return expr.weaklyEquivalent(other.expr);
    }

    @Override
    public String getDotString() {
        return null;
    }

    /**
     * Determines if all terms in expression are valid.
     *
     * @return True if all terms in expression are valid.
     */
    boolean isValid() {
        return expr.isValidAtomicNumber();
    }

    /**
     * Returns the sum of atomic numbers in nuclear expression.
     *
     * @return Sum of atomic numbers in nuclear expression.
     * @throws NuclearException Some terms are not nuclear.
     */
    public Integer getAtomicCount() throws NuclearException {
        return expr.getAtomicCount();
    }

    /**
     * Returns the sum of mass numbers in nuclear expression.
     *
     * @return Sum of mass numbers in nuclear expression.
     * @throws NuclearException Some terms are not nuclear.
     */
    public Integer getMassCount() throws NuclearException {
        return expr.getMassCount();
    }

    @Override
    public ArrayList<Term> getWrongTerms(final Statement e) {

        if (e instanceof NuclearExpressionStatement) {
            return expr.getWrongTerms(((NuclearExpressionStatement) e).expr);
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public boolean check(final Statement input) {
        if (!(input instanceof NuclearExpressionStatement)) {
            // not even NuclearExpressionStatement
            System.out.println("Not a NuclearExpressionStatement!");
            return false;
        }

        if (input.containsError()) {
            // error exists in argument
            System.out.printf("Input: %s\nPlease correct the error.\n", input.toString());
            return false;
        }

        if (equals(input)) {
            return true;
        }

        NuclearExpressionStatement exprInput = (NuclearExpressionStatement) input;

        if (!exprInput.isValid()) {
            // invalid atomic numbers
            System.out.println("There are elements with invalid atomic numbers.");
            return false;
        }

        if (!weaklyEquivalent(exprInput)) {
            // not weakly equivalent: exists irrelevant terms in equation
            System.out.println("Unrelated terms exist in equation, and/or some terms are missing.");
        } else {
            // wrong coefficients in some terms
            System.out.println("Some terms have incorrect coefficients.");
        }

        System.out.println("Wrong terms: " + getWrongTerms(exprInput));
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
