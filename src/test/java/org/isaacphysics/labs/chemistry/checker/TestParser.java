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

import java_cup.runtime.DefaultSymbolFactory;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TestParser {
    String simpleMolecule = "H2SO4;";
    String simpleMoleculeNumber = "2NO2;";
    String simpleMoleculeState = "NH3(aq);";
    String diffNotationAndMoleculeCharge = "C_{2}O_{4}H^{+};";
    String expressionStatement = "C2O4H2 + H2O2";
    String equationStatement1 = "8H^{+} + Cr2O7^{2-}(aq) + 3CH3CH2CH2OH(l) -> 2Cr^{3+}(aq) + 3CH3CH2CHO(l) + 7H2O(l);";
    String equationStatement2 = "3CH3CH2CH2OH(l) + Cr2O7^{2-}(aq) + 8H^{+} -> 3CH3CH2CHO(l) + 2Cr^{3+}(aq) + 7H2O(l);";
    String syntaxError = "2H2S(O4;";
    String nestedGroupMolecule = "MgNaAl5((Si2O4)2O2)3(OH)6";

    @Test
    public void testParserSimpleMolecule() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(simpleMolecule)), new DefaultSymbolFactory()).parse().value;

        assertTrue("Output not an ArrayList!", output instanceof ArrayList<?>);

        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        assertTrue(statements.size() == 1);

        Statement result = statements.get(0);
        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!", result.toString().equals("H2SO4"));

        assertTrue("Result is not an ExpressionStatement!", result instanceof ExpressionStatement);
        ExpressionStatement exprStatement = (ExpressionStatement) result;

        Expression expr = exprStatement.terms;
        assertTrue("Expected only one term in the expression, got " + expr.terms.size() + "!", expr.terms.size() == 1);
        Term term = expr.terms.get(0);

        assertTrue("Implicit count of 1 for Terms failed, got " + term.number + "!", term.number == 1);

        HashMap<String, Integer> atomCount = term.getAtomCount();
        assertTrue("Parsed oxygen atom count incorrect! Expected 4, got " + atomCount.get("O") + "!", atomCount.get("O") == 4);

        assertTrue("Charge of parsed molecule incorrect! Expected no charge (0), got " + term.getCharge() + "!", term.getCharge() == 0);

        assertTrue("Physical State of parsed molecule incorrect! Expected null, got " + term.state + "!", term.state == null);

    }

    @Test
    public void testSimpleMoleculeNumber() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(simpleMoleculeNumber)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        Statement result = statements.get(0);

        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!", result.toString().equals("2NO2"));
        Term term = ((ExpressionStatement) result).terms.terms.get(0);

        assertTrue("Setting number of molecule in term failed! Expected 2, got " + term.number + "!", term.number == 2);

        HashMap<String, Integer> atomCount = term.getAtomCount();
        assertTrue("Parsed oxygen atom count incorrect! Expected 4, got " + atomCount.get("O") + "!" , atomCount.get("O") == 4);
    }

    @Test
    public void testSimpleMoleculeState() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(simpleMoleculeState)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        Statement result = statements.get(0);

        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!", result.toString().equals("NH3(aq)"));
        Term term = ((ExpressionStatement) result).terms.terms.get(0);

        assertTrue("Physical State of parsed molecule incorrect! Expected (aq), got " + term.state + "!", term.state.equals(Term.PhysicalState.aq));
    }

    @Test
    public void testDiffNotationAndMoleculeCharge() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(diffNotationAndMoleculeCharge)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        Statement result = statements.get(0);

        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!", result.toString().equals("C2O4H^{+}"));
        Term term = ((ExpressionStatement) result).terms.terms.get(0);

        assertTrue("Charge of parsed molecule incorrect! Expected 1, got " + term.getCharge() + "!", term.getCharge() == 1);
    }

    @Test
    public void testExpressionStatement() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(expressionStatement)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        Statement result = statements.get(0);

        assertTrue("Expected 1 statement out, got " + statements.size() + "!", statements.size() == 1);

        assertTrue("Result is not an ExpressionStatement!", result instanceof ExpressionStatement);
        ExpressionStatement exprStatement = (ExpressionStatement) result;
        Expression expr = exprStatement.terms;

        assertTrue("Expected 2 terms, got " + expr.terms.size() + "!", expr.terms.size() == 2);

        HashMap<String, Integer> atomCount = expr.getAtomCount();
        assertTrue("Parsed oxygen atom count incorrect! Expected 6, got " + atomCount.get("O") + "!" , atomCount.get("O") == 6);

        Term t = expr.terms.get(0);
        assertTrue("Printed output \"" + t.toString() + "\" not in mhchem format!", t.toString().equals("C2O4H2"));

    }

    @Test
    public void testEquationStatement() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(equationStatement1)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        Statement result = statements.get(0);

        assertTrue("Expected 1 statement out, got " + statements.size() + "!", statements.size() == 1);

        assertTrue("Result is not an EquationStatement!", result instanceof EquationStatement);
        EquationStatement eqnStatement = (EquationStatement) result;

        Expression left = eqnStatement.left;
        Expression right = eqnStatement.right;
        assertTrue("The LHS is not an Expression!", null != left);
        assertTrue("The RHS is not an Expression!", null != right);

        assertTrue("Equation is not recognised as being balanced atomically!", eqnStatement.isBalancedAtoms());
        assertTrue("Equation is not recognised as being balanced electrically!", eqnStatement.isBalancedCharge());
        assertTrue("Equation is not recognised as being balanced overall!", eqnStatement.isBalanced());

        assertFalse("Equation unexpectedly recognised as containing an error!", eqnStatement.containsError());
    }

    @Test
    public void testEquationStatementEquality() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(equationStatement1 + equationStatement2)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;

        assertTrue("Expected 2 statements out, got " + statements.size() + "!", statements.size() == 2);

        Statement result1 = statements.get(0);
        Statement result2 = statements.get(1);
        assertTrue("Results are not EquationStatements!", (result1 instanceof EquationStatement) && (result2 instanceof EquationStatement));
        EquationStatement eqnStatement1 = (EquationStatement) result1;
        EquationStatement eqnStatement2 = (EquationStatement) result2;

        assertTrue("Equations not recognised as being equal!", eqnStatement1.equals(eqnStatement2));
    }

    @Test
    public void testNestedGroupMolecule() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(nestedGroupMolecule)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        Statement result = statements.get(0);
        ExpressionStatement exprStatement = (ExpressionStatement) result;

        HashMap<String, Integer> atomCount = exprStatement.getAtomCount();
        assertTrue("Count of Oxygen atoms in nested group incorrect! Expected 36, got " + atomCount.get("O") + "!", atomCount.get("O") == 36);
    }

    @Test
    public void testSyntaxErrors() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(syntaxError)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        ExpressionStatement result = (ExpressionStatement) statements.get(0);
        Term t = result.terms.terms.get(0);

        assertTrue("ExpressionStatement not recognised as containing an error!", result.containsError());

        assertTrue("The result is not an ErrorTerm!", t instanceof ErrorTerm);

    }

    @Test
    public void testSyntaxErrorEquality() throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(syntaxError + syntaxError)), new DefaultSymbolFactory()).parse().value;
        ArrayList<Statement> statements = (ArrayList<Statement>) output;
        Statement result1 = statements.get(0);
        Statement result2 = statements.get(1);
        ExpressionStatement exprStatement1 = (ExpressionStatement) result1;
        ExpressionStatement exprStatement2 = (ExpressionStatement) result2;

        assertTrue("ExpressionStatements not recognised as containing errors!", exprStatement1.containsError() && exprStatement2.containsError());
        assertFalse("Two expression statements containing errors recognised as equal!", exprStatement1.equals(exprStatement2));
    }
}
