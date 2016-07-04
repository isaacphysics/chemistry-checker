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
 * Created by Ryan on 20/06/2016.
 */
public class NuclearEquationStatement extends Statement
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
     * Constructor method of NuclearEquationStatement.
     * @param l Left expression of equation.
     * @param r Right expression of equation.
     */
    public NuclearEquationStatement(Expression l, Expression r)
    {
        left = l;
        right = r;
    }

    @Override
    public String toString() {
        return left.toString() + " -> " + right.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof NuclearEquationStatement)
        {
            NuclearEquationStatement other = (NuclearEquationStatement) o;

            return this.left.equals(other.left) &&
                    this.right.equals(other.right);
        }
        return false;
    }

    @Override
    public boolean containsError() {
        return left.containsError() || right.containsError();
    }

    /**
     * Getter function. Returns left expression.
     */
    public Expression getLeftExpression() {
        return this.left;
    }

    /**
     * Getter function. Returns right expression.
     */
    public Expression getRightExpression() {
        return this.right;
    }

    /**
     * Checks if mass numbers on both sides of equation are balanced.
     */
    public boolean isBalancedMass()
    {
        try
        {
            return !containsError() && left.getMassCount().equals(right.getMassCount());
        }
        catch (NuclearException e)
        {
            return false;
        }
    }

    /**
     * Checks if atomic numbers on both sides of equation are balanced.
     */
    public boolean isBalancedAtom()
    {
        try
        {
            return !containsError() && left.getAtomicCount().equals(right.getAtomicCount());
        }
        catch (NuclearException e)
        {
            return false;
        }
    }

    /**
     * Checks if both mass numbers and atomic numbers on both sides of equation are balanced.
     */
    boolean isBalanced() { return isBalancedAtom() && isBalancedMass(); }

    /**
     * Checks if the atomic number of all isotopes matches element name.
     */
    boolean isValid() { return left.isValidAtomicNumber() && right.isValidAtomicNumber(); }

    @Override
    String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("digraph chemical_syntax_tree {\n");
        result.append("\tnode [shape=record,penwidth=2,splines=ortho];\n\n");

        result.append("\tequation [label=\"{&zwj;&zwj;&zwj;&zwj;Nuclear Equation&zwnj;|\\n");
        result.append(left.getDotString());
        result.append(" &#8594; ");
        result.append(right.getDotString());
        result.append("\\n\\n|<left>&zwj;&zwj;&zwj;left&zwnj;|" +
                "<right>&zwj;&zwj;&zwj;right&zwnj;}\",color=\"#bb2828\"];\n");

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

    @Override
    public boolean weaklyEquivalent(Statement s)
    {
        if (!(s instanceof NuclearEquationStatement))
            return false;

        NuclearEquationStatement other = (NuclearEquationStatement) s;

        return left.weaklyEquivalent(other.left) && right.weaklyEquivalent(other.right);
    }

    @Override
    public ArrayList<Term> getWrongTerms(Statement e)
    {
        if (!(e instanceof NuclearEquationStatement))
            return new ArrayList<>();

        NuclearEquationStatement expr = (NuclearEquationStatement) e;

        ArrayList<Term> toReturn = left.getWrongTerms(expr.left);

        toReturn.addAll(right.getWrongTerms(expr.right));

        return toReturn;
    }

    @Override
    public boolean check(Statement input)
    {
        if (!(input instanceof NuclearEquationStatement))
        {
            // Not even nuclear equation statement
            System.out.println("Not a NuclearEquationStatement!");
            return false;
        }

        if (input.containsError())
        {
            // Error term exists in argument
            System.out.printf("Input: %s\nPlease correct the error.\n", input.toString());
            return false;
        }

        NuclearEquationStatement e_input = (NuclearEquationStatement) input;

        if (!e_input.isBalanced())
        {
            if (!e_input.isBalancedMass())
            {
                // Mass number not balanced.
                try
                {
                    System.out.printf("Total mass# LHS: %d\nTotal mass# RHS: %d\n",
                            e_input.left.getMassCount(), e_input.right.getMassCount());
                }
                catch (Exception e) {}

                System.out.println("Mass numbers are unbalanced.");
            }
            else
            {
                // Atomic number not balanced.
                try
                {
                    System.out.printf("Total atomic# LHS: %d\nTotal atomic# RHS: %d\n",
                            e_input.left.getAtomicCount(), e_input.right.getAtomicCount());
                }
                catch (Exception e) {}

                System.out.println("Atomic numbers are unbalanced.");
            }

            return false;
        }

        if (!e_input.isValid())
        {
            // invalid atomic numbers
            System.out.println("There are elements with invalid atomic numbers.");
            return false;
        }

        if (equals(e_input))
            return true;

        if (!weaklyEquivalent(e_input))
        {
            // not weakly equivalent: exists irrelevant terms in equation
            System.out.println("Unrelated terms exist in equation, or some terms are missing.");
        }
        else
        {
            // wrong coefficients in some terms
            System.out.println("Some terms have incorrect coefficients.");
        }

        System.out.println("Wrong terms: " + getWrongTerms(e_input));
        return false;
    }
}
