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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test suite used for testing the chemical parser.
 * Each method defined in class should be annotated with @Test to allow automatic testing.
 * Methods like assertTrue and assertFalse can be used for debugging - test suite will signal failure
 * if assertion does not hold.
 * Every time a new feature is implemented, a new method should be implemented to detect potential errors.
 */
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
    private String nestedGroupMolecule = "MgNaAl5((Si2O4)2O2)3(OH)6;";
    private String nestedGroupMolecule2 = "((((((((((OH2)2)2)2)2)2)2)2)2)2)2";

    /**
     * Invokes ChemicalParser to parse the argument string.
     * @param s String to be parsed
     * @return ArrayList containing parsed chemical/physical statements.
     * @throws Exception
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    private ArrayList<Statement> stringParser(String s) throws Exception
    {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(s)),
                                            new DefaultSymbolFactory()).parse().value;

        return (ArrayList<Statement>) output;
    }

    /**
     * Extremely simple test.
     * Parses the formula for sulphuric acid (H2SO4), and check if the generated abstract syntax tree is correct.
     * @throws Exception
     */
    @Test
    public void testParserSimpleMolecule() throws Exception
    {
        ArrayList<Statement> statements = stringParser(simpleMolecule);
        assertTrue(statements.size() == 1);

        // Check if string outputted is in mhchem format.
        Statement result = statements.get(0);
        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!",
                    result.toString().equals("H2SO4"));

        // Checks the type of Statement.
        assertTrue("Result is not an ExpressionStatement!", result instanceof ExpressionStatement);
        ExpressionStatement exprStatement = (ExpressionStatement) result;

        // Checks the number of terms in expression.
        Expression expr = exprStatement.getExpression();
        assertTrue("Expected only one term in the expression, got " + expr.getTerms().size() + "!",
                                                                        expr.getTerms().size() == 1);
        AbstractTerm t = expr.getTerms().get(0);

        // Asserts that the term in Expression is not ErrorTerm.
        assertTrue("Unexpected ErrorTerm present!", t instanceof Term);
        Term term = (Term) t;

        // Asserts that coefficient of term is 1.
        assertTrue("Implicit count of 1 for Terms failed, got " + term.getNumber() + "!", term.getNumber().equals(new IntCoeff(1)));

        // Asserts number of oxygen atoms in term is 4.
        HashMap<String, Fraction> atomCount = term.getAtomCount();
        assertTrue("Parsed oxygen atom count incorrect! Expected 4, got " + atomCount.get("O") + "!",
                atomCount.get("O").equals(new Fraction(4, 1)));

        // Asserts that the charge of molecule is 0.
        assertTrue("Charge of parsed molecule incorrect! Expected no charge (0), got " + term.getCharge() + "!",
                term.getCharge().equals(new Fraction(0, 1)));

        // Asserts the state of molecule is not defined.
        assertTrue("Physical State of parsed molecule incorrect! Expected null, got " + term.getState() + "!",
                term.getState() == null);
    }

    /**
     * Simple test. Parses a simple molecule (nitrogen dioxide), and test the correctness of the generated abstract
     * syntax tree.
     * @throws Exception
     */
    @Test
    public void testSimpleMoleculeNumber() throws Exception
    {
        ArrayList<Statement> statements = stringParser(simpleMoleculeNumber);
        Statement result = statements.get(0);

        // Asserts that printed expression follows mhchem format.
        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!",
                                         result.toString().equals("2NO2"));

        AbstractTerm t = ((ExpressionStatement) result).getExpression().getTerms().get(0);

        // Asserts term in expression is not ErrorTerm.
        assertTrue("Unexpected ErrorTerm present!", t instanceof Term);
        Term term = (Term) t;

        // Asserts coefficient of term = 2.
        assertTrue("Setting number of molecule in term failed! Expected 2, got " + term.getNumber() + "!",
                term.getNumber().toString().equals("2"));

        // Asserts number of oxygen atoms in expression = 4.
        HashMap<String, Fraction> atomCount = term.getAtomCount();
        assertTrue("Parsed oxygen atom count incorrect! Expected 4, got " + atomCount.get("O") + "!",
                atomCount.get("O").toString().equals("4"));
    }

    /**
     * Simple test. Parses simple molecule (aqueous ammonia), and test the correctness of the generated
     * abstract syntax tree. Test mainly focuses on the physical state.
     * @throws Exception
     */
    @Test
    public void testSimpleMoleculeState() throws Exception
    {
        ArrayList<Statement> statements = stringParser(simpleMoleculeState);
        Statement result = statements.get(0);

        // Asserts the printed expression is in mhchem format.
        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!",
                                         result.toString().equals("NH3(aq)"));

        AbstractTerm t = ((ExpressionStatement) result).getExpression().getTerms().get(0);

        // Asserts the term in expression is not ErrorTerm.
        assertTrue("Unexpected ErrorTerm present!", t instanceof Term);
        Term term = (Term) t;

        // Asserts the physical state of term = aqueous.
        assertTrue("Physical State of parsed molecule incorrect! Expected (aq), got " + term.getState() + "!",
                term.getState().equals(Term.PhysicalState.aq));
    }

    /**
     * Simple test. Parses `C_{2}O_{4}H^{+}`, and checks the correctness of charge in the corresponding abstract
     * syntax tree.
     * @throws Exception
     */
    @Test
    public void testDiffNotationAndMoleculeCharge() throws Exception
    {
        ArrayList<Statement> statements = stringParser(diffNotationAndMoleculeCharge);
        Statement result = statements.get(0);

        // Asserts printed output is in correct mhchem format.
        assertTrue("Printed output \"" + result.toString() + "\" not in mhchem format!",
                                         result.toString().equals("C2O4H^{+}"));

        Term term = (Term) ((ExpressionStatement) result).getExpression().getTerms().get(0);

        // Asserts charge of molecule = 1.
        assertTrue("Charge of parsed molecule incorrect! Expected 1, got " + term.getCharge() + "!", term.getCharge().toString().equals("1"));
    }

    /**
     * Simple test. Parses a statement consisting of two terms, and test their properties.
     * @throws Exception
     */
    @Test
    public void testExpressionStatement() throws Exception
    {
        ArrayList<Statement> statements = stringParser(expressionStatement);
        Statement result = statements.get(0);

        // Asserts only one statement in expressionStatement.
        assertTrue("Expected 1 statement out, got " + statements.size() + "!", statements.size() == 1);

        // Asserts statement to be an instance of ExpressionStatement.
        assertTrue("Result is not an ExpressionStatement!", result instanceof ExpressionStatement);
        ExpressionStatement exprStatement = (ExpressionStatement) result;
        Expression expr = exprStatement.getExpression();

        // Asserts only two terms in ExpressionStatement.
        assertTrue("Expected 2 terms, got " + expr.getTerms().size() + "!", expr.getTerms().size() == 2);

        // Asserts only 6 oxygen atoms in the whole expression.
        HashMap<String, Fraction> atomCount = expr.getAtomCount();
        assertTrue("Parsed oxygen atom count incorrect! Expected 6, got " + atomCount.get("O") + "!" ,
                                                                            atomCount.get("O").toString().equals("6"));

        // Asserts the string form of first term to be in correct mhchem format.
        AbstractTerm t = expr.getTerms().get(0);
        assertTrue("Printed output \"" + t.toString() + "\" not in mhchem format!", t.toString().equals("C2O4H2"));

    }

    /**
     * Simple test. Parses a chemical equation, and test its correctness.
     * @throws Exception
     */
    @Test
    public void testEquationStatement() throws Exception
    {
        ArrayList<Statement> statements = stringParser(equationStatement1);
        Statement result = statements.get(0);

        // Asserts that the parsed syntax tree only contains one statement.
        assertTrue("Expected 1 statement out, got " + statements.size() + "!", statements.size() == 1);

        // Asserts that the statement is indeed a chemical equation.
        assertTrue("Result is not an EquationStatement!", result instanceof EquationStatement);
        EquationStatement eqnStatement = (EquationStatement) result;

        // Asserts both sides of equation is non-empty.
        Expression left = eqnStatement.getLeftExpression();
        Expression right = eqnStatement.getRightExpression();
        assertTrue("The LHS is not an Expression!", null != left);
        assertTrue("The RHS is not an Expression!", null != right);

        // Asserts equation to be balanced.
        assertTrue("Equation is not recognised as being balanced atomically!", eqnStatement.isBalancedAtoms());
        assertTrue("Equation is not recognised as being balanced electrically!", eqnStatement.isBalancedCharge());
        assertTrue("Equation is not recognised as being balanced overall!", eqnStatement.isBalanced());

        // Asserts equation to contain no error terms.
        assertFalse("Equation unexpectedly recognised as containing an error!", eqnStatement.containsError());
    }

    /**
     * Simple test. Parses two chemical equations that are semantically equivalent. Tests if equals method works.
     * @throws Exception
     */
    @Test
    public void testEquationStatementEquality() throws Exception
    {
        ArrayList<Statement> statements = stringParser(equationStatement1 + equationStatement2);

        // Asserts only two statements in ArrayList.
        assertTrue("Expected 2 statements out, got " + statements.size() + "!", statements.size() == 2);

        // Asserts both statements to be actual instances of EquationStatement.
        Statement result1 = statements.get(0);
        Statement result2 = statements.get(1);
        assertTrue("Results are not EquationStatements!",
                    (result1 instanceof EquationStatement) && (result2 instanceof EquationStatement));
        EquationStatement eqnStatement1 = (EquationStatement) result1;
        EquationStatement eqnStatement2 = (EquationStatement) result2;

        // Asserts that first statement is equivalent to second one.
        assertTrue("Equations not recognised as being equal!", eqnStatement1.equals(eqnStatement2));
    }

    /**
     * Hard test. Parses chemical expression with nested parenthesis, and test if count of oxygen atoms is correct.
     * @throws Exception
     */
    @Test
    public void testNestedGroupMolecule() throws Exception
    {
        ArrayList<Statement> statements = stringParser(nestedGroupMolecule + nestedGroupMolecule2);
        Statement result = statements.get(0);
        Statement result2 = statements.get(1);
        ExpressionStatement exprStatement = (ExpressionStatement) result;
        ExpressionStatement exprStatement2 = (ExpressionStatement) result2;

        // Asserts number of oxygen atoms in expression = 36.
        HashMap<String, Fraction> atomCount = exprStatement.getAtomCount();
        assertTrue("Count of Oxygen atoms in nested group incorrect! Expected 36, got " + atomCount.get("O") + "!",
                                                                                          atomCount.get("O").toString().equals("36"));

        // Asserts number of oxygen atoms in expression = 4098.
        HashMap<String, Fraction> atomCount2 = exprStatement2.getAtomCount();
        assertTrue("Count of Oxygen atoms in nested group incorrect! Expected 1024, got " + atomCount2.get("O") + "!",
            atomCount2.get("O").toString().equals("1024"));
        assertTrue("Count of Hydrogen atoms in nested group incorrect! Expected 2048, got " + atomCount2.get("H") + "!",
                atomCount2.get("H").toString().equals("2048"));
    }

    /**
     * Simple test. Parses an erroneous statement, and check if result contains error.
     * @throws Exception
     */
    @Test
    public void testSyntaxErrors() throws Exception
    {
        ArrayList<Statement> statements = stringParser(syntaxError);
        ExpressionStatement result = (ExpressionStatement) statements.get(0);
        AbstractTerm t = result.getExpression().getTerms().get(0);

        // Asserts that expression got contains error.
        assertTrue("ExpressionStatement not recognised as containing an error!", result.containsError());

        // Assert that the term in expression is ErrorTerm.
        assertTrue("The result is not an ErrorTerm!", t instanceof ErrorTerm);

    }

    /**
     * Simple test. Parses two erroneous statement, and checks if both statements are considered equivalent or not.
     * @throws Exception
     */
    @Test
    public void testSyntaxErrorEquality() throws Exception
    {
        ArrayList<Statement> statements = stringParser(syntaxError + syntaxError);

        Statement result1 = statements.get(0);
        Statement result2 = statements.get(1);

        ExpressionStatement exprStatement1 = (ExpressionStatement) result1;
        ExpressionStatement exprStatement2 = (ExpressionStatement) result2;

        // Asserts that both statements are erroneous.
        assertTrue("ExpressionStatements not recognised as containing errors!",
                    exprStatement1.containsError() && exprStatement2.containsError());

        // Asserts both statements not equivalent to each other.
        assertFalse("Two expression statements containing errors recognised as equal!",
                    exprStatement1.equals(exprStatement2));
    }

    /**
     * Simple test. Parses two expressions with differing arrow symbols. Checks if system manages to differentiate
     * between the two expressions.
     * @throws Exception
     */
    @Test
    public void testArrowDifference() throws Exception
    {
        String boudouard_reaction = "2CO <=> CO2 + C";
        String fake = "2CO -> CO2 + C";
        ArrayList<Statement> statements = stringParser(boudouard_reaction + ";" + fake);

        // Assumes only two statements are parsed.
        assertTrue("Expected two statements, got " + statements.size(), statements.size() == 2);

        Statement first = statements.get(0);
        Statement second = statements.get(1);

        // Assume both to be chemical equations.
        assertTrue("2CO <--> CO2 + C should be of type EquationStatement.", first instanceof EquationStatement);
        assertTrue("2CO -> CO2 + C should be of type EquationStatement.", second instanceof EquationStatement);

        // Assume both to be different in semantic meaning.
        assertFalse(boudouard_reaction + ";" + fake + " are considered the same.", first.equals(second));
    }

    /**
     * Simple test. Both statements mix up chemical terms with nuclear ones. The parser should be able to spot error
     * in parsing stage.
     * @throws Exception
     */
    @Test
    public void testInvalidExpressions() throws Exception
    {
        String invalidExpression = "Mg + ^{2}_{1}Al";
        String invalidEquation = "^{14}_{7}N + 3e^{-} -> ^{14}_{6}Na";

        ArrayList<Statement> statements = stringParser(invalidExpression + ";" + invalidEquation);

        // Assumes only two statements are parsed.
        assertTrue("Expected two statements, got " + statements.size(), statements.size() == 2);

        Statement first = statements.get(0);
        Statement second = statements.get(1);

        // Assumes both expressions contain error terms.
        assertTrue(invalidExpression + " not recognised as containing an error!", first.containsError());
        assertTrue(invalidEquation + " not recognised as containing an error!", second.containsError());
    }

    /**
     * Easy test. Invalid tokens such as state symbols, coefficients and double arrow are placed in nuclear expressions.
     * The parser should be able to detect such errors.
     * @throws Exception
     */
    @Test
    public void testInvalidNuclear() throws Exception
    {
        // State symbols should not be assigned to isotopes.
        String stateSymbol = "^{226}_{88}Ra (s) -> \\alphaparticle + ^{222}_{86}Rn (aq)";

        // Coefficients should not be assigned to isotopes.
        String coefficient = "^{14}_{6}C -> 20^{14}_{7}N + \\electron";

        // No equilibrium stuff for nuclear reactions.
        String equilibrium = "^{131}_{53}I <=> ^{131}_{54}Xe + \\electron";

        ArrayList<Statement> statements = stringParser(stateSymbol + ";" +
                coefficient + ";" + equilibrium);

        // Assumes only three statements are parsed.
        assertTrue("Expected three statements, got " + statements.size(), statements.size() == 3);

        Statement first = statements.get(0);
        Statement second = statements.get(1);
        Statement third = statements.get(2);

        // Assumes all three expressions have errors.
        assertTrue(stateSymbol + " not recognised as containing an error!", first.containsError());
        assertTrue(coefficient + " not recognised as containing an error!", second.containsError());
        assertTrue(equilibrium + " not recognised as containing an error!", third.containsError());
    }

    /**
     * Test on validity of atomic number. For atomic number to be valid, atomic number should match the element symbol
     * for all isotopes. The mass number should not be smaller than the atomic number as well.
     * @throws Exception
     */
    @Test
    public void checkAtomicNumber() throws Exception
    {
        String alphaDecay = "^{243}_{95}Am + ^{48}_{20}Ca -> ^{288}_{115}Uup + 3_{0}^{1}\\neutron";

        // Uut does not have an atomic number of 112.
        String invalidAtomicNumber = "^{283}_{112}Uut + 3_{2}^{4}\\alphaparticle ";

        ArrayList<Statement> statements = stringParser(alphaDecay + ";" + invalidAtomicNumber);

        // Assumes only two statements are parsed.
        assertTrue("Expected two statements, got " + statements.size(), statements.size() == 2);

        Statement first = statements.get(0);
        Statement second = statements.get(1);

        // Assumes both expressions to be nuclear equations.
        assertTrue(first + " should be of type NuclearEquationStatement.",
                first instanceof NuclearEquationStatement);
        assertTrue(second + " should be of type NuclearExpressionStatement.",
                second instanceof NuclearExpressionStatement);

        NuclearEquationStatement eq = (NuclearEquationStatement) first;
        NuclearExpressionStatement ex = (NuclearExpressionStatement) second;

        // Assumes first statement has valid atomic numbers.
        assertTrue(alphaDecay + " should have correct atomic numbers.", eq.isValid());

        // Assumes program is able to detect invalid atomic number in expression.
        assertFalse(invalidAtomicNumber + " should have incorrect atomic numbers.", ex.isValid());
    }

    /**
     * Checks if nuclear equations are balanced, and have valid atomic numbers. By balanced we mean that:
     * <p>
     *     <ul>
     *         <li>Mass numbers are the same on both sides.</li>
     *         <li>Atomic numbers are equal on both sides.</li>
     *     </ul>
     * </p>
     * @throws Exception
     */
    @Test
    public void checkNuclearBalance() throws Exception
    {
        String c14_decay = "^{14}_{6}C -> ^{14}_{7}N + ^{0}_{-1}\\electron";

        // Unbalanced mass number.
        String bad_decay = "^{12}_{6}C -> ^{14}_{7}N + ^{0}_{-1}\\electron";

        ArrayList<Statement> statements = stringParser(c14_decay + ";" + bad_decay);

        // Assumes only two statements are parsed.
        assertTrue("Expected two statements, got " + statements.size(), statements.size() == 2);

        Statement first = statements.get(0);
        Statement second = statements.get(1);

        // Assumes both statements to be nuclear equations.
        assertTrue(first + " should be of type NuclearEquationStatement.",
                first instanceof NuclearEquationStatement);
        assertTrue(second + " should be of type NuclearEquationStatement.",
                second instanceof NuclearEquationStatement);

        NuclearEquationStatement eq = (NuclearEquationStatement) first;
        NuclearEquationStatement eq2 = (NuclearEquationStatement) second;

        // Assumes first statement to be balanced and valid.
        //System.out.printf("Left expression atomic count:  %s\n", eq.getLeftExpression().getAtomicCount());
        //System.out.printf("Right expression atomic count: %s\n", eq.getRightExpression().getAtomicCount());
        assertTrue(c14_decay + " should have balanced atomic and mass numbers.", eq.isBalanced());
        assertTrue(c14_decay + " should have valid atomic numbers.", eq.isValid());

        // Assumes second statement to be unbalanced yet valid.
        assertFalse(bad_decay + "should not have balanced atomic and mass numbers.", eq2.isBalanced());
        assertTrue(bad_decay + " should have valid atomic numbers.", eq2.isValid());

        // Assumes both statements contain no error terms.
        assertFalse(c14_decay + " unexpectedly recognised as containing an error!", eq.containsError());
        assertFalse(bad_decay + " unexpectedly recognised as containing an error!", eq2.containsError());
    }

    /**
     * Tests equivalence of nuclear expressions.
     * @throws Exception
     */
    @Test
    public void testNuclearExpressionEquivalence() throws Exception
    {
        // Nuclear expressions: Set 1
        String eStream = "^{0}_{-1}\\electron + ^{0}_{-1}\\electron + ^{0}_{0}\\neutrino";
        String eStream2 = "2^{0}_{-1}\\electron + ^{0}_{0}\\neutrino";
        String eStream3 = "^{0}_{-1}\\electron + ^{0}_{0}\\neutrino + ^{0}_{0}\\neutrino";

        // Nuclear expression: Set 2
        String alphaDecay = "^{243}_{95}Am + ^{48}_{20}Ca -> ^{288}_{115}Uup + 3^{1}_{0}\\neutron";
        String alphaDecay2 = "^{48}_{20}Ca + ^{243}_{95}Am -> 3^{1}_{0}\\neutron + ^{288}_{115}Uup";
        String alphaDecay3 = "^{48}_{20}Ca + ^{243}_{95}Am -> ^{288}_{115}Uup";


        ArrayList<Statement> statements = stringParser(eStream + ";" +
                                                        eStream2 + ";" +
                                                        eStream3 + ";" +
                                                        alphaDecay + ";" +
                                                        alphaDecay2 + ";" +
                                                        alphaDecay3);

        // Assumes only six statements are parsed.
        assertTrue("Expected six statements, got " + statements.size(), statements.size() == 6);

        Statement first  = statements.get(0);
        Statement second = statements.get(1);
        Statement third  = statements.get(2);
        Statement fourth = statements.get(3);
        Statement fifth  = statements.get(4);
        Statement sixth  = statements.get(5);

        // Assumes that all statements in set 1 are pairwise non-equivalent.
        assertFalse("Expected eStream != eStream2.", first.equals(second));
        assertFalse("Expected eStream != eStream3.", first.equals(third));

        // Assumes first 2 statements in set 2 to be equivalent.
        // Also assumes second expression non-equivalent to third one.
        assertTrue("Expected alphaDecay == alphaDecay2.", fourth.equals(fifth));
        assertFalse("Expected alphaDecay2 != alphaDecay3.", fifth.equals(sixth));
    }

    /**
     * Testing out the weak equivalence method.
     * Two statements are weakly equivalent if they are of the same type, and contains same chemical formulae,
     * disregarding coefficients and state symbols.
     *
     * For instance NaOH(aq) is weakly equivalent to 100NaOH.
     * @throws Exception
     */
    @Test
    public void testWeakEquivalence() throws Exception
    {
        String acid_base     = "HCl(aq) + NaOH(aq) -> H2O(l) + NaCl(aq)";
        String missing_state = "HCl + NaOH -> NaCl + H2O";
        String much_wrong    = "20HCl(aq) + 300NaOH(l) <=> 40NaCl + 205H2O(g)";
        String error_term    = "HCl + NaOH -> 22 + NaCl(aq)";

        ArrayList<Statement> statements = stringParser(acid_base + ";" +
                                                        missing_state + ";" +
                                                        much_wrong + ";" +
                                                        error_term);

        // Assumes only four statements are parsed.
        assertTrue("Expected 4 statements, got " + statements.size(), statements.size() == 4);

        Statement first  = statements.get(0);
        Statement second = statements.get(1);
        Statement third  = statements.get(2);
        Statement fourth = statements.get(3);

        // Assumes that all four statements are chemical equations.
        assertTrue("All four statements should be EquationStatement.", first instanceof EquationStatement &&
                                                                        second instanceof EquationStatement &&
                                                                        third instanceof EquationStatement &&
                                                                        fourth instanceof EquationStatement);

        // Assumes that the first four terms are mutually equivalent.
        assertTrue("Expected acid_base ~= acid_base.", first.weaklyEquivalent(first));
        assertTrue("Expected acid_base ~= missing_state.", first.weaklyEquivalent(second));
        assertTrue("Expected acid_base ~= much_wrong.", first.weaklyEquivalent(third));
        assertTrue("Expected missing_state ~= much_wrong.", second.weaklyEquivalent(third));

        // Assumes last term is not equivalent to any other terms.
        assertFalse("Expected acid_base !~= error_term.", first.weaklyEquivalent(fourth));

        EquationStatement one = (EquationStatement) first;
        EquationStatement two = (EquationStatement) second;
        EquationStatement three = (EquationStatement) third;

        // Assumes first statement agrees with second statement on coefficient, but not on state.
        assertTrue("Expected acid_base == missing_state on coefficient.", one.sameCoefficients(two));
        assertFalse("Expected acid_base != missing_state on state.", one.sameStateSymbols(two));

        // Assumes first statement disagrees with fourth statement on both coefficient and state.
        assertFalse("Expected acid_base != much_wrong on coefficient.", one.sameCoefficients(three));
        assertFalse("Expected acid_base != much_wrong on state.", one.sameStateSymbols(three));
    }

    /**
     * Testing out new class Hydrate. It is an extension of uncharged compound, and has form X.n H2O.
     * Though it is also common for people to write X(H2O)n instead, this form clearly shows the water in crystallization.
     * The software should be able to count atoms in hydrate correctly.
     *
     * @throws Exception
     */
    @Test
    public void testHydrates() throws Exception
    {
        String hydrate = "CaCl2.2H2O -> CaCl2 + 2H2O";

        ArrayList<Statement> statements = stringParser(hydrate);

        // Assumes that only one statement is parsed.
        assertTrue("Expected 1 statement, got " + statements.size(), statements.size() == 1);

        Statement first = statements.get(0);

        // Assumes that statement is chemical equation.
        assertTrue("Expected hydrate to have type EquationStatement.", first instanceof EquationStatement);

        EquationStatement equation = (EquationStatement) first;

        // Expect equation to be balanced.
        assertTrue("Expected hydrate to be balanced.", equation.isBalanced());
    }

    /**
     * Test out the special syntax for isotopes.
     * Originally isotopes could only be written in form ^{A}_{B}X, but now _{B}^{A}X is also allowed.
     * <p>
     * This test is used to make sure that no error is introduced in modification.
     * For instance the mass numbers and atomic numbers may be messed up.
     * @throws Exception
     */
    @Test
    public void testIsotopes() throws Exception
    {
        String alphaDecay = "^{243}_{95}Am + ^{48}_{20}Ca -> ^{288}_{115}Uup + 3_{0}^{1}\\neutron";
        String sameDecay  = "_{20}^{48}Ca + _{95}^{243}Am -> 3_{0}^{1}\\neutron + ^{288}_{115}Uup";

        ArrayList<Statement> statements = stringParser(alphaDecay + ";" + sameDecay);
        assertTrue("Expected 2 statements, got " + statements.size(), statements.size() == 2);

        Statement first  = statements.get(0);
        Statement second = statements.get(1);

        // Assumes both statements to be nuclear equations.
        assertTrue("Expected alphaDecay to have type NuclearEquationStatement.",
                                                        first instanceof NuclearEquationStatement);
        assertTrue("Expected sameDecay to have type NuclearEquationStatement.",
                                                        second instanceof NuclearEquationStatement);

        // Assumes first statement to be balanced, and valid.
        assertTrue("Expected alphaDecay to be balanced.", ((NuclearEquationStatement) first).isBalanced());
        assertTrue("Expected alphaDecay to have valid atomic numbers.", ((NuclearEquationStatement) first).isValid());

        // Assumes second statement to be balanced and valid as well.
        assertTrue("Expected sameDecay to be balanced.", ((NuclearEquationStatement) second).isBalanced());
        assertTrue("Expected sameDecay to have valid atomic numbers.", ((NuclearEquationStatement) second).isValid());

        // Expect both statements to be equivalent.
        assertTrue("Expected alphaDecay == sameDecay.", first.equals(second));
    }

}
