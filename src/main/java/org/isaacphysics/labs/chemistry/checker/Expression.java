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
 * Instance of an expression.
 * Expression is essentially a list of terms. This class makes use of various methods to measure the correctness of
 * expression (compared to a target expr).
 */
public final class Expression implements Countable {

    /**
     * Array of all terms involved in expression.
     */
    private ArrayList<AbstractTerm> terms;

    /**
     * Helper static variable for issuing unique IDs.
     */
    private static int dotIdTracker = 0;

    /**
     * Stores unique ID for every Expression.
     */
    private int dotId;

    /**
     * Construction method for Expression.
     * @param t Term involved in expression.
     */
    public Expression(final AbstractTerm t) {
        terms = new ArrayList<>();
        terms.add(t);
        dotId = dotIdTracker;
        dotIdTracker += 1;
    }

    /**
     * Adds a term to this expression.
     * @param t Term to be added
     */
    void add(final AbstractTerm t) {
        terms.add(t);
    }

    @Override
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

    @Override
    public boolean equals(final Object o) {

        if (o instanceof Expression) {

            Expression other = (Expression) o;

            if (terms.size() != other.terms.size()) {
                return false;
            }

            HashMap<AbstractTerm, Integer> htable1 = new HashMap<>();
            HashMap<AbstractTerm, Integer> htable2 = new HashMap<>();

            for (AbstractTerm t: terms) {

                if (htable1.containsKey(t)) {
                    htable1.put(t, htable1.get(t) + 1);
                } else {
                    htable1.put(t, 1);
                }
            }

            for (AbstractTerm t: other.terms) {
                if (htable2.containsKey(t)) {
                    htable2.put(t, htable2.get(t) + 1);
                } else {
                    htable2.put(t, 1);
                }
            }

            return htable1.equals(htable2);
        }

        return false;
    }

    @Override
    public HashMap<String, Integer> getAtomCount() {

        HashMap<String, Integer> h = new HashMap<>();

        for (AbstractTerm t : terms) {
            for (String e : t.getAtomCount().keySet()) {

                if (!h.containsKey(e)) {
                    h.put(e, t.getAtomCount().get(e));
                } else {
                    h.put(e, h.get(e) + t.getAtomCount().get(e));
                }

            }
        }

        return h;
    }

    @Override
    public Integer getCharge() {

        Integer c = 0;

        for (AbstractTerm t : terms) {
            c += t.getCharge();
        }

        return c;

    }

    /**
     * Checks if error term exists in this expression.
     *
     * @return True if error terms exist.
     */
    boolean containsError() {

        for (AbstractTerm t : terms) {
            if (t instanceof ErrorTerm) {
                return true;
            }
        }

        return false;

    }

    /**
     * Getter method. Returns all terms in this expression.
     *
     * @return All terms in this expression.
     */
    ArrayList<AbstractTerm> getTerms() {
        return this.terms;
    }

    /**
     * Checks if two expressions are weakly equivalent, i.e.
     * they have same molecules when ignoring both coefficients and state symbols.
     * <p>
     * For instance, NaOH (aq) + 3 H^{+} is weakly equivalent to NaOH + H^{+} (aq).
     *
     * @param expr Expression to be compared against
     * @return True if two expressions are weakly equivalent.
     */
    boolean weaklyEquivalent(final Expression expr) {

        if (terms.size() != expr.terms.size()) {
            return false;
        }

        HashMap<Formula, Integer> htable1 = new HashMap<>();
        HashMap<Formula, Integer> htable2 = new HashMap<>();

        for (AbstractTerm t: terms) {

            if (t instanceof ErrorTerm) {

                return false;

            } else {

                Term foo = (Term) t;
                Formula f = foo.getFormula();

                if (htable1.containsKey(f)) {

                    htable1.put(f, htable1.get(f) + 1);

                } else {

                    htable1.put(f, 1);

                }
            }
        }

        for (AbstractTerm t: expr.terms) {

            if (t instanceof ErrorTerm) {
                return false;
            } else {

                Term foo = (Term) t;
                Formula f = foo.getFormula();

                if (htable2.containsKey(f)) {
                    htable2.put(f, htable2.get(f) + 1);
                } else {
                    htable2.put(f, 1);
                }

            }
        }

        //System.out.println(htable1 + "\n" + htable2);
        return htable1.equals(htable2);
    }

    /**
     * Checks if both expressions are weakly equivalent, AND contains same coefficients in relevant terms.
     *
     * E.g. 2NaOH (aq), 2NaOH (g) should return true.
     *
     * Should assert that both expressions are weakly equivalent before executing this code,
     * as it is quite time consuming.
     *
     * @param expr Expression to be compared against.
     * @return True if both expressions are weakly equivalent, and contains same coefficients in relevant terms.
     */
    boolean sameCoefficients(final Expression expr) {

        if (terms.size() != expr.terms.size()) {
            return false;
        }

        // Key = (Formula, Coefficient).
        HashMap<Pair<Formula, Integer>, Integer> htable1 = new HashMap<>();
        HashMap<Pair<Formula, Integer>, Integer> htable2 = new HashMap<>();

        for (AbstractTerm t: terms) {

            if (t instanceof ErrorTerm) {

                return false;

            } else {

                Term foo = (Term) t;
                Formula f = foo.getFormula();
                Integer coeff = foo.getNumber();

                Pair<Formula, Integer> temp = new Pair<>(f, coeff);

                if (htable1.containsKey(temp)) {
                    htable1.put(temp, htable1.get(temp) + 1);
                } else {
                    htable1.put(temp, 1);
                }
            }
        }

        for (AbstractTerm t: expr.terms) {

            if (t instanceof ErrorTerm) {

                return false;

            } else {

                Term foo = (Term) t;
                Formula f = foo.getFormula();
                Integer coeff = foo.getNumber();

                Pair<Formula, Integer> temp = new Pair<>(f, coeff);

                if (htable2.containsKey(temp)) {

                    htable2.put(temp, htable2.get(temp) + 1);

                } else {

                    htable2.put(temp, 1);

                }

            }
        }

        return htable1.equals(htable2);
    }

    /**
     * Checks if both expressions are weakly equivalent, AND contains same state symbols in relevant terms.
     *
     * E.g. 2NaOH (aq), 20NaOH (aq) should return true.
     *
     * Should assert that both expressions are weakly equivalent before executing this code,
     * as it is quite time consuming.
     *
     * @param expr Expression to be compared against.
     * @return True if both expressions are weakly equivalent and contains same states symbols in all terms.
     */
    boolean sameStateSymbols(final Expression expr) {

        if (terms.size() != expr.terms.size()) {
            return false;
        }

        // Key = (Formula, State symbol).
        HashMap<Pair<Formula, String>, Integer> htable1 = new HashMap<>();
        HashMap<Pair<Formula, String>, Integer> htable2 = new HashMap<>();

        for (AbstractTerm t: terms) {

            if (t instanceof ErrorTerm) {

                return false;

            } else {
                Term foo = (Term) t;
                Formula f = foo.getFormula();
                String state = "null";

                if (foo.getState() != null) {
                    state = foo.getState().toString();
                }

                Pair<Formula, String> temp = new Pair<>(f, state);

                if (htable1.containsKey(temp)) {
                    htable1.put(temp, htable1.get(temp) + 1);
                } else {
                    htable1.put(temp, 1);
                }
            }
        }

        for (AbstractTerm t: expr.terms) {

            if (t instanceof ErrorTerm) {
                return false;
            } else {
                Term foo = (Term) t;
                Formula f = foo.getFormula();
                String state = "null";

                if (foo.getState() != null) {
                    state = foo.getState().toString();
                }

                Pair<Formula, String> temp = new Pair<>(f, state);

                if (htable2.containsKey(temp)) {
                    htable2.put(temp, htable2.get(temp) + 1);
                } else {
                    htable2.put(temp, 1);
                }
            }
        }

        return htable1.equals(htable2);
    }

    /**
     * Find terms in argument expression that do not exist in this one.
     *
     * @param expr The supposedly wrong chemical expression.
     * @return ArrayList of wrong terms in chemical expression.
     */
    ArrayList<Term> getWrongTerms(final Expression expr) {

        HashMap<Term, Integer> htable1 = new HashMap<>();

        for (AbstractTerm t: expr.terms) {

            if (t instanceof Term) {

                Term term = (Term) t;

                if (htable1.containsKey(term)) {

                    htable1.put(term, htable1.get(term) + 1);

                } else {

                    htable1.put(term, 1);

                }
            }
        }

        for (AbstractTerm t: terms) {

            if (t instanceof Term) {

                Term term = (Term) t;

                if (htable1.containsKey(term)) {
                    htable1.put(term, htable1.get(term) - 1);
                }
            }
        }

        ArrayList<Term> toReturn = new ArrayList<>();

        for (Term t: htable1.keySet()) {

            for (int i = 0; i < htable1.get(t); i++) {
                toReturn.add(t);
            }
        }

        return toReturn;
    }

    @Override
    public String getDotId() {
        return "expression_" + dotId;
    }

    @Override
    public String getDotCode() {

        StringBuilder result = new StringBuilder();
        result.append("\t");
        result.append(getDotId());
        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Expression&zwnj;|\\n");
        result.append(getDotString());
        result.append("\\n\\n|<terms>&zwj;&zwj;&zwj;terms&zwnj;}\",color=\"#fea100\"];\n");

        for (AbstractTerm t : terms) {
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
    public String getDotString() {

        StringBuilder b = new StringBuilder();

        for (int i = 0; i < terms.size(); i++) {

            if (i > 0) {
                b.append(" + ");
            }

            b.append(terms.get(i).getDotString());
        }

        return b.toString();
    }

    /**
     * Only defined for nuclear expressions.
     * Returns the total mass number of AbstractTerms.
     *
     * @return Total mass number of AbstractTerms.
     * @throws NuclearException Called this function on non-nuclear expressions.
     */
    Integer getMassCount() throws NuclearException {

        Integer mass = 0;

        for (AbstractTerm t: terms) {
            mass += t.getMassNumber();
        }

        return mass;
    }

    /**
     * Only defined for nuclear expressions.
     * Returns the total atomic number of AbstractTerms.
     *
     * @return Total atomic number of AbstractTerms.
     * @throws NuclearException Called this function on non-nuclear expressions.
     */
    Integer getAtomicCount() throws NuclearException {

        Integer atomic = 0;

        for (AbstractTerm t: terms) {
            atomic += t.getAtomicNumber();
        }

        return atomic;
    }

    /**
     * Method only applicable to nuclear formula.
     * Checks if all atomic numbers in nuclear equation is valid.
     *
     * @return True if the atomic numbers in nuclear equation is valid.
     */
    boolean isValidAtomicNumber() {

        for (AbstractTerm t: terms) {

            if (!t.isValidAtomicNumber()) {
                return false;
            }

        }

        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
