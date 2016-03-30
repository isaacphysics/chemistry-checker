package org.isaacphysics.labs.chemistry.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Expression implements Countable {
    ArrayList<Term> terms;

    public Expression(Term t) {
        terms = new ArrayList<Term>();
        terms.add(t);
    }

    public Expression(Expression e, Term t) {
        terms = new ArrayList<Term>(e.terms);
        terms.add(t);
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            if (i > 0) {
                b.append(" + ");
            }
            b.append(terms.get(i).toString());
        }
        return b.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof Expression) {
            Expression other = (Expression) o;
            HashSet<Term> termSet = new HashSet<Term>(terms);
            HashSet<Term> otherTermSet = new HashSet<Term>(other.terms);
            return termSet.equals(otherTermSet);
        }
        return false;
    }

    public HashMap<String, Integer> getAtomCount() {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        for (Term t : terms) {
            for (String e : t.getAtomCount().keySet()) {
                if (!h.containsKey(e)) {
                    h.put(e,  t.getAtomCount().get(e));
                } else {
                    h.put(e, h.get(e) + t.getAtomCount().get(e));
                }
            }
        }
        return h;
    }

    public Integer getCharge() {
        Integer c = 0;
        for (Term t : terms) {
            c += t.getCharge();
        }
        return c;
    }

    public boolean containsError() {
        for (Term t : terms) {
            if (t instanceof ErrorTerm) {
                return true;
            }
        }
        return false;
    }
}
