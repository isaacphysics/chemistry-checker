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

public class ExpressionStatement extends Statement implements Countable
{
    /**
     * Expression involved in statement
     */
    private Expression expr;

    /**
     * Constructor method of ExpressionStatement.
     * @param e Expression involved in the statement.
     */
    public ExpressionStatement(Expression e) {
        expr = e;
    }

    @Override
    public String toString() {
        return expr.toString();
    }

    @Override
    public String getDotId() { return null; }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof ExpressionStatement)
        {
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
    public HashMap<String, Integer> getAtomCount() {
        return expr.getAtomCount();
    }

    @Override
    public Integer getCharge() {
        return expr.getCharge();
    }

    Expression getExpression() {
        return this.expr;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("digraph chemical_syntax_tree {\n");
        result.append("\tnode [shape=record,penwidth=2,splines=ortho];\n\n");
        result.append(expr.getDotCode());
        result.append("}\n");
        return result.toString();
    }

    @Override
    public boolean weaklyEquivalent(Statement s)
    {
        if (!(s instanceof ExpressionStatement))
            return false;

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
     */
    public boolean sameStateSymbols(Statement s)
    {
        if (!(s instanceof ExpressionStatement))
            return false;

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
     */
    public boolean sameCoefficients(Statement s)
    {
        if (!(s instanceof ExpressionStatement))
            return false;

        ExpressionStatement other = (ExpressionStatement) s;

        return expr.sameCoefficients(other.expr);
    }

    @Override
    public String getDotString()
    {
        return null;
    }

    public ArrayList<Term> getWrongTerms(ExpressionStatement e)
    {
        return expr.getWrongTerms(e.expr);
    }

    @Override
    public boolean check(Statement input)
    {
        if (!(input instanceof ExpressionStatement))
        {
            // Not even ExpressionStatement
            System.out.println("Not an ExpressionStatement!");
            return false;
        }

        if (input.containsError())
        {
            // Error term exists in argument
            System.out.printf("Input: %s\nPlease correct the error.\n", input.toString());
            return false;
        }

        if (equals(input))
            return true;

        ExpressionStatement e_input = (ExpressionStatement) input;

        if (!weaklyEquivalent(input))
        {
            // not even weakly equivalent: some molecules are unrelated to solution
            System.out.println("Unrelated terms exist in equation.");
            System.out.println("Wrong terms: " + getWrongTerms(e_input));

            return false;
        }

        if (!sameCoefficients(input))
        {
            // wrong coefficients
            System.out.println("Some terms have incorrect coefficients.");
            System.out.println("Wrong terms: " + getWrongTerms(e_input));

            return false;
        }

        if (sameStateSymbols(input))
        {
            // wrong state symbols
            System.out.println("Some terms have incorrect state symbols.");
        }
        else
        {
            // correct coefficients, state symbols, but misplaced.
            System.out.println("Coefficient/state symbols are misplaced.");
        }

        System.out.println("Wrong terms: " + getWrongTerms(e_input));
        return false;
    }
}
