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

public class TestParser
{
    private String simpleMolecule = "H2SO4;";
    private String simpleMoleculeNumber = "2NO2;";
    private String simpleMoleculeState = "NH3(aq);";
    private String diffNotationAndMoleculeCharge = "C_{2}O_{4}H^{+};";
    private String expressionStatement = "C2O4H2 + H2O2";
    private String equationStatement1 = "8H^{+} + Cr2O7^{2-}(aq) + 3CH3CH2CH2OH(l) -> " +
                                            "2Cr^{3+}(aq) + 3CH3CH2CHO(l) + 7H2O(l);";
    private String equationStatement2 = "3CH3CH2CH2OH(l) + Cr2O7^{2-}(aq) + 8H^{+} -> " +
                                            "3CH3CH2CHO(l) + 2Cr^{3+}(aq) + 7H2O(l);";
    private String syntaxError = "2H2S(O4;";
    private String nestedGroupMolecule = "MgNaAl5((Si2O4)2O2)3(OH)6";

    @SuppressWarnings({"deprecation", "unchecked"})
    private ArrayList<Statement> stringParser(String s) throws Exception
    {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(s)),
                                            new DefaultSymbolFactory()).parse().value;

        return (ArrayList<Statement>) output;
    }

    @Test
    public void testParserSimpleMolecule() throws Exception
    {
        ArrayList<Statement> statements = stringParser(simpleMolecule);
        assertTrue(statements.size() == 1);

        Statement result = statements.get(0);
        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!",
                    result.toString().equals("H2SO4"));

        assertTrue("Result is not an ExpressionStatement!", result instanceof ExpressionStatement);
        ExpressionStatement exprStatement = (ExpressionStatement) result;

        Expression expr = exprStatement.getExpression();
        assertTrue("Expected only one term in the expression, got " + expr.getTerms().size() + "!",
                                                                        expr.getTerms().size() == 1);
        AbstractTerm t = expr.getTerms().get(0);

        if (t instanceof Term)
        {
            Term term = (Term) t;
            assertTrue("Implicit count of 1 for Terms failed, got " + term.getNumber() + "!", term.getNumber() == 1);

            HashMap<String, Integer> atomCount = term.getAtomCount();
            assertTrue("Parsed oxygen atom count incorrect! Expected 4, got " + atomCount.get("O") + "!",
                    atomCount.get("O") == 4);

            assertTrue("Charge of parsed molecule incorrect! Expected no charge (0), got " + term.getCharge() + "!",
                                                                                             term.getCharge() == 0);

            assertTrue("Physical State of parsed molecule incorrect! Expected null, got " + term.getState() + "!",
                                                                                            term.getState() == null);
        } else
        {
            assertTrue("Unexpected ErrorTerm present!", false);
        }

    }

    @Test
    public void testSimpleMoleculeNumber() throws Exception
    {
        ArrayList<Statement> statements = stringParser(simpleMoleculeNumber);
        Statement result = statements.get(0);

        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!",
                                         result.toString().equals("2NO2"));

        AbstractTerm t = ((ExpressionStatement) result).getExpression().getTerms().get(0);

        if (t instanceof Term)
        {
            Term term = (Term) t;
            assertTrue("Setting number of molecule in term failed! Expected 2, got " + term.getNumber() + "!",
                                                                                       term.getNumber() == 2);

            HashMap<String, Integer> atomCount = term.getAtomCount();
            assertTrue("Parsed oxygen atom count incorrect! Expected 4, got " + atomCount.get("O") + "!",
                                                                                atomCount.get("O") == 4);
        }
        else
        {
            assertTrue("Unexpected ErrorTerm present!", false);
        }
    }

    @Test
    public void testSimpleMoleculeState() throws Exception
    {
        ArrayList<Statement> statements = stringParser(simpleMoleculeState);
        Statement result = statements.get(0);

        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!",
                                         result.toString().equals("NH3(aq)"));

        AbstractTerm t = ((ExpressionStatement) result).getExpression().getTerms().get(0);

        if (t instanceof Term)
        {
            Term term = (Term) t;
            assertTrue("Physical State of parsed molecule incorrect! Expected (aq), got " + term.getState() + "!",
                    term.getState().equals(Term.PhysicalState.aq));
        } else
        {
            assertTrue("Unexpected ErrorTerm present!", false);
        }
    }

    @Test
    public void testDiffNotationAndMoleculeCharge() throws Exception
    {
        ArrayList<Statement> statements = stringParser(diffNotationAndMoleculeCharge);
        Statement result = statements.get(0);

        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!",
                                         result.toString().equals("C2O4H^{+}"));

        Term term = (Term) ((ExpressionStatement) result).getExpression().getTerms().get(0);

        assertTrue("Charge of parsed molecule incorrect! Expected 1, got " + term.getCharge() + "!", term.getCharge() == 1);
    }

    @Test
    public void testExpressionStatement() throws Exception
    {
        ArrayList<Statement> statements = stringParser(expressionStatement);
        Statement result = statements.get(0);

        assertTrue("Expected 1 statement out, got " + statements.size() + "!", statements.size() == 1);

        assertTrue("Result is not an ExpressionStatement!", result instanceof ExpressionStatement);
        ExpressionStatement exprStatement = (ExpressionStatement) result;
        Expression expr = exprStatement.getExpression();

        assertTrue("Expected 2 terms, got " + expr.getTerms().size() + "!", expr.getTerms().size() == 2);

        HashMap<String, Integer> atomCount = expr.getAtomCount();
        assertTrue("Parsed oxygen atom count incorrect! Expected 6, got " + atomCount.get("O") + "!" ,
                                                                            atomCount.get("O") == 6);

        AbstractTerm t = expr.getTerms().get(0);
        assertTrue("Printed output \"" + t.toString() + "\" not in mhchem format!", t.toString().equals("C2O4H2"));

    }

    @Test
    public void testEquationStatement() throws Exception
    {
        ArrayList<Statement> statements = stringParser(equationStatement1);
        Statement result = statements.get(0);

        assertTrue("Expected 1 statement out, got " + statements.size() + "!", statements.size() == 1);

        assertTrue("Result is not an EquationStatement!", result instanceof EquationStatement);
        EquationStatement eqnStatement = (EquationStatement) result;

        Expression left = eqnStatement.getLeftExpression();
        Expression right = eqnStatement.getRightExpression();
        assertTrue("The LHS is not an Expression!", null != left);
        assertTrue("The RHS is not an Expression!", null != right);

        assertTrue("Equation is not recognised as being balanced atomically!", eqnStatement.isBalancedAtoms());
        assertTrue("Equation is not recognised as being balanced electrically!", eqnStatement.isBalancedCharge());
        assertTrue("Equation is not recognised as being balanced overall!", eqnStatement.isBalanced());

        assertFalse("Equation unexpectedly recognised as containing an error!", eqnStatement.containsError());
    }

    @Test
    public void testEquationStatementEquality() throws Exception
    {
        ArrayList<Statement> statements = stringParser(equationStatement1 + equationStatement2);

        assertTrue("Expected 2 statements out, got " + statements.size() + "!", statements.size() == 2);

        Statement result1 = statements.get(0);
        Statement result2 = statements.get(1);
        assertTrue("Results are not EquationStatements!",
                    (result1 instanceof EquationStatement) && (result2 instanceof EquationStatement));
        EquationStatement eqnStatement1 = (EquationStatement) result1;
        EquationStatement eqnStatement2 = (EquationStatement) result2;

        assertTrue("Equations not recognised as being equal!", eqnStatement1.equals(eqnStatement2));
    }

    @Test
    public void testNestedGroupMolecule() throws Exception
    {
        ArrayList<Statement> statements = stringParser(nestedGroupMolecule);
        Statement result = statements.get(0);
        ExpressionStatement exprStatement = (ExpressionStatement) result;

        HashMap<String, Integer> atomCount = exprStatement.getAtomCount();
        assertTrue("Count of Oxygen atoms in nested group incorrect! Expected 36, got " + atomCount.get("O") + "!",
                                                                                          atomCount.get("O") == 36);
    }

    @Test
    public void testSyntaxErrors() throws Exception
    {
        ArrayList<Statement> statements = stringParser(syntaxError);
        ExpressionStatement result = (ExpressionStatement) statements.get(0);
        AbstractTerm t = result.getExpression().getTerms().get(0);

        assertTrue("ExpressionStatement not recognised as containing an error!", result.containsError());

        assertTrue("The result is not an ErrorTerm!", t instanceof ErrorTerm);

    }

    @Test
    public void testSyntaxErrorEquality() throws Exception
    {
        ArrayList<Statement> statements = stringParser(syntaxError + syntaxError);
        
        Statement result1 = statements.get(0);
        Statement result2 = statements.get(1);

        ExpressionStatement exprStatement1 = (ExpressionStatement) result1;
        ExpressionStatement exprStatement2 = (ExpressionStatement) result2;

        assertTrue("ExpressionStatements not recognised as containing errors!",
                    exprStatement1.containsError() && exprStatement2.containsError());
        assertFalse("Two expression statements containing errors recognised as equal!",
                    exprStatement1.equals(exprStatement2));
    }
}
