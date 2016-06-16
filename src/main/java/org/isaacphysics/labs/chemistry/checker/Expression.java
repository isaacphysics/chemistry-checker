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
import java.util.HashSet;

public class Expression implements Countable
{
    /**
     * Array of all terms involved in expression
     */
    private ArrayList<AbstractTerm> terms;

    /**
     * Helper static variable for issuing unique IDs
     */
    private static int dotIdTracker = 0;

    /**
     * Stores unique ID for every Expression
     */
    private int dotId;

    /**
     * Construction method for Expression
     * @param t Term involved in expression.
     */
    public Expression(AbstractTerm t)
    {
        terms = new ArrayList<>();
        terms.add(t);
        dotId = dotIdTracker;
        dotIdTracker += 1;
    }

    public Expression(Expression e, AbstractTerm t)
    {
        terms = new ArrayList<>(e.terms);
        terms.add(t);
        dotId = dotIdTracker;
        dotIdTracker += 1;
    }

    /**
     * Adds a term to this expression.
     * @param t Term to be added
     */
    public void add(AbstractTerm t)
    {
        terms.add(t);
    }

    @Override
    public String toString()
    {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < terms.size(); i++)
        {
            if (i > 0)
                b.append(" + ");
            b.append(terms.get(i).toString());
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Expression)
        {
            Expression other = (Expression) o;
            HashSet<AbstractTerm> termSet = new HashSet<>(terms);
            HashSet<AbstractTerm> otherTermSet = new HashSet<>(other.terms);
            return termSet.equals(otherTermSet);
        }
        return false;
    }

    @Override
    public HashMap<String, Integer> getAtomCount()
    {
        HashMap<String, Integer> h = new HashMap<>();
        for (AbstractTerm t : terms)
        {
            for (String e : t.getAtomCount().keySet())
            {
                if (!h.containsKey(e))
                    h.put(e,  t.getAtomCount().get(e));
                else
                    h.put(e, h.get(e) + t.getAtomCount().get(e));
            }
        }
        return h;
    }

    @Override
    public Integer getCharge()
    {
        Integer c = 0;
        for (AbstractTerm t : terms)
            c += t.getCharge();
        return c;
    }

    /**
     * Checks if error term exists in this expression.
     */
    boolean containsError()
    {
        for (AbstractTerm t : terms)
        {
            if (t instanceof ErrorTerm)
                return true;
        }
        return false;
    }

    /**
     * Getter method. Returns all terms in this expression.
     */
    ArrayList<AbstractTerm> getTerms() {
        return this.terms;
    }

    public boolean contains(Formula m)
    {
        for (AbstractTerm t : terms)
            if (t.contains(m))
                return true;

        return false;
    }

    /**
     * Checks if this class instance contains all terms in the expression provided in argument.
     * @param e Expression to be compared against
     */
    public boolean containsAll(Expression e)
    {
        for (AbstractTerm t : e.terms)
        {
            if (t instanceof ErrorTerm)
                return false;
            else
            {
                Term term = (Term) t;
                if (!this.contains(term.getFormula()))
                    return false;
            }
        }
        return true;
    }

    @Override
    public String getDotId() {
        return "expression_" + dotId;
    }

    @Override
    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("\t");
        result.append(getDotId());
        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Expression&zwnj;|\\n");
        result.append(getDotString());
        result.append("\\n\\n|<terms>&zwj;&zwj;&zwj;terms&zwnj;}\",color=\"#fea100\"];\n");
        for (AbstractTerm t : terms)
        {
            result.append("\t");
            result.append(getDotId());
            result.append(":terms -> ");
            result.append(t.getDotId());
            result.append(":n;\n");
            result.append(t.getDotCode());
        }
        return result.toString();
    }

    @Override
    public String getDotString()
    {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < terms.size(); i++)
        {
            if (i > 0)
                b.append(" + ");

            b.append(terms.get(i).getDotString());
        }
        return b.toString();
    }
}
