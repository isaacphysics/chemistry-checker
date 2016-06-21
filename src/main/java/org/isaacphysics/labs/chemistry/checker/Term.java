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

import java.util.HashMap;

public final class Term extends AbstractTerm
{
    /**
     *  States that can be used in a chemical term.
     *  If state is not provided, physical state is set to null.
     */
    enum PhysicalState
    {
        /**
         * Solid state
         */
        s,

        /**
         * Liquid state
         */
        l,

        /**
         * Gas state
         */
        g,

        /**
         * Aqueous state
         */
        aq
    }

    /**
     * Chemical formula associated to this term
     */
    private Formula formula;

    /**
     * Coefficient of this term
     */
    private Integer coefficient;

    /**
     * Physical state of this term
     */
    private PhysicalState state;

    /**
     * Constructor function of Term.
     * @param n Coefficient of term
     * @param m Chemical formula involved in the term
     * @param s State of the formula
     */
    public Term(int n, Formula m, String s)
    {
        super();

        this.coefficient = n;
        this.formula = m;

        if (null == s)
            this.state = null;
        else
            this.state = PhysicalState.valueOf(s);
    }

    @Override
    public String toString()
    {
        String t = "";
        if (coefficient > 1) {
            t += coefficient.toString();
        }
        t += formula.toString();
        if (state != null) {
            t += "(" + state.toString() + ")";
        }
        return t;
    }

    @Override
    public boolean equals(Object o)
    {
        //System.out.printf("Checking if %s and %s are equal...\n", toString(), o.toString());

        if (o instanceof ErrorTerm)
        {
            //System.out.println("One is ErrorTerm, no.");
            return false;
        }
        else if (o instanceof Term)
        {
            Term other = (Term) o;
            /*System.out.printf("Formula: %b, Coefficient: %b, State: %b\n",
                    this.formula.equals(other.formula),
                    this.coefficient.equals(other.coefficient),
                    this.state == other.state);*/
            return (this.formula.equals(other.formula)
                    && this.coefficient.equals(other.coefficient)
                    && (this.state == other.state));
        }
        //System.out.println("One is not even a term, no.");
        return false;
    }

    @Override
    public Integer getMassNumber() throws NuclearException
    {
        return formula.getMassNumber();
    }

    @Override
    public Integer getAtomicNumber() throws NuclearException
    {
        return formula.getAtomicNumber();
    }

    /*@Override
    public int hashCode ()
    {
        if (null != formula)
            return this.formula.hashCode();
        else
            return super.hashCode();
    }*/

    @Override
    public HashMap<String, Integer> getAtomCount()
    {
        HashMap<String, Integer> h = new HashMap<>();

        for (String element: formula.getAtomCount().keySet())
            h.put(element, formula.getAtomCount().get(element) * coefficient);

        return h;
    }

    @Override
    public Integer getCharge() {
        return formula.getCharge() * coefficient;
    }

    /**
     * Getter function. Returns chemical formula related to this term.
     * @return Formula of this term
     */
    public Formula getFormula() {
        return this.formula;
    }

    /**
     * Getter function. Returns coefficient.
     * @return Coefficient of this term
     */
    public Integer getNumber() {
        return this.coefficient;
    }

    /**
     * Getter function. Returns physical state of this term.
     * @return State of this term
     */
    public PhysicalState getState() {
        return this.state;
    }

    @Override
    public boolean contains(Formula m) {
        return this.formula.equals(m);
    }

    @Override
    public String getDotId() {
        return "term_" + dotId;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("\t");
        result.append(getDotId());
        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Term&zwnj;|\\n");
        result.append(getDotString());
        result.append("\\n\\n|&zwj;&zwj;&zwj;coefficient&zwnj;: ");
        result.append(coefficient);
        result.append("|&zwj;&zwj;&zwj;state&zwnj;: ");

        if (state != null)
            result.append(state.toString());
        else
            result.append("none");

        result.append("|&zwj;&zwj;&zwj;formula&zwnj;}\",color=\"#49902a\"];\n");

        result.append("\t");
        result.append(getDotId());
        result.append(":s -> ");
        result.append(formula.getDotId());
        result.append(":n;\n");

        result.append(formula.getDotCode());
        result.append("\n");
        return result.toString();
    }

    @Override
    public String getDotString()
    {
        String t = "";

        if (coefficient > 1)
            t += coefficient.toString();

        t += formula.getDotString();

        if (state != null)
            t += "(" + state.toString() + ")";
        
        return t;
    }

    @Override
    public boolean isValidAtomicNumber()
    {
        return formula.isValidAtomicNumber();
    }
}
