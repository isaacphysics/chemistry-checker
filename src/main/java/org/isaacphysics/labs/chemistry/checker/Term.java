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

public class Term extends AbstractTerm
{
    // Solid, Liquid, Gas, Aqueous in standard chemical notation
    public enum PhysicalState
    {
        s,
        l,
        g,
        aq
    }

    private Formula formula;
    private Integer coefficient;
    private PhysicalState state;
    private static int dotIdTracker = 0;
    private int dotId;

    public Term(int n, Formula m, String s)
    {
        this.coefficient = n;
        this.formula = m;

        if (null == s)
            this.state = null;
        else
            this.state = PhysicalState.valueOf(s);

        dotId = dotIdTracker;
        dotIdTracker += 1;
    }

    public Term(int n, Formula m) {
        this(n, m, null);
    }

    public Term(Formula m, String state) {
        this(1, m, state);
    }

    public Term(Formula m) {
        this(1, m);
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
        if (o instanceof ErrorTerm)
            return false;
        else if (o instanceof Term)
        {
            Term other = (Term) o;
            return (this.formula.equals(other.formula)
                    && this.coefficient.equals(other.coefficient)
                    && (this.state == other.state));
        }
        return false;
    }

    @Override
    public int hashCode ()
    {
        if (null != formula)
            return this.formula.hashCode();
        else
            return super.hashCode();
    }

    public HashMap<String, Integer> getAtomCount()
    {
        HashMap<String, Integer> h = new HashMap<>();

        for (String element: formula.getAtomCount().keySet())
            h.put(element, formula.getAtomCount().get(element) * coefficient);

        return h;
    }

    public Integer getCharge() {
        return formula.getCharge() * coefficient;
    }

    public Formula getFormula() {
        return this.formula;
    }

    public Integer getNumber() {
        return this.coefficient;
    }

    public PhysicalState getState() {
        return this.state;
    }

    public boolean contains(Formula m) {
        return this.formula.equals(m);
    }

    public String getDotId() {
        return "term_" + dotId;
    }

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
}
