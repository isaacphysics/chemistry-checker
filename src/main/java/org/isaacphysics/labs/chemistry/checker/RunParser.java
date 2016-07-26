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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java_cup.runtime.DefaultSymbolFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The (original) main class. The class is mainly for testing purposes.
 */
public final class RunParser {

    /**
     * Constructor method of RunParser.
     * Set to private, because we don't want people to instantiate an utility class.
     */
    private RunParser() {
        // Nothing here.
    }

    /**
     * Parses the string provided, and return a list of statement from it.
     *
     * @param s The string to be parsed.
     * @return A list of statement parsed from argument.
     * @throws Exception Parser has trouble parsing a few terms.
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    private static ArrayList<Statement> stringParser(final String s) throws Exception {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(s)),
                new DefaultSymbolFactory()).parse().value;

        return (ArrayList<Statement>) output;
    }

    /**
     * Main method of RunParser. Used to test all sorts of methods defined below.
     *
     * @param args No use at all.
     * @throws Exception Parser has trouble parsing a few terms.
     */
    public static void main(final String[] args) throws Exception {
        //noinspection deprecation (We know DefaultSymbolFactory is depracated!)
        @SuppressWarnings("unchecked")
        ArrayList<Statement> statements = (ArrayList<Statement>) new ChemistryParser(new ChemistryLexer(
            new InputStreamReader(new FileInputStream("src/test.txt")))).parse().value;
        System.err.flush();
        System.out.flush();
        System.out.println();

        for (Statement statement : statements) {
            System.out.println(statement);

            if (statement.containsError()) {

                System.out.println("It contains error terms.");

            } else if (statement instanceof NuclearExpressionStatement) {

                NuclearExpressionStatement s = (NuclearExpressionStatement) statement;

                System.out.println("Are atomic numbers valid? " + s.isValid());
                System.out.println("Total atomic#: " + s.getAtomicCount());
                System.out.println("Total mass#: " + s.getMassCount());

            } else if (statement instanceof ExpressionStatement) {

                ExpressionStatement s = (ExpressionStatement) statement;

                System.out.println("Total atoms: " + s.getAtomCount());
                System.out.println("Total charge: " + s.getCharge());

            } else if (statement instanceof EquationStatement) {

                EquationStatement s = (EquationStatement) statement;

                System.out.println("Is balanced? " + s.isBalanced());
                System.out.println("Total atoms LHS: " + s.getLeftExpression().getAtomCount());
                System.out.println("Total atoms RHS: " + s.getRightExpression().getAtomCount());
                System.out.println("Total charge LHS: " + s.getLeftExpression().getCharge());
                System.out.println("Total charge RHS: " + s.getRightExpression().getCharge());

            } else {

                NuclearEquationStatement s = (NuclearEquationStatement) statement;

                System.out.println("Is balanced? " + s.isBalanced());
                System.out.println("Are atomic numbers valid? " + s.isValid());
                System.out.println("Total atomic# LHS: " + s.getLeftExpression().getAtomicCount());
                System.out.println("Total atomic# RHS: " + s.getRightExpression().getAtomicCount());
                System.out.println("Total mass# LHS: " + s.getLeftExpression().getMassCount());
                System.out.println("Total mass# RHS: " + s.getRightExpression().getMassCount());

            }
            System.out.printf("Dot code:\n%s\n", statement.getDotCode());
            System.out.println("\n");
        }


        /*String acid_base = "NaOH(aq) + HCl(aq) -> NaCl(aq) + H2O(l)";
        String wrong_eq  = "NaOH(l) + HCl(aq) -> NaCl + H2O(l)";
        ArrayList<Statement> stmt_list = stringParser(acid_base + ";" + wrong_eq);

        EquationStatement foo = (EquationStatement) stmt_list.get(0);
        EquationStatement bar = (EquationStatement) stmt_list.get(1);

        System.out.printf("Correct    : %s\nInput      : %s\n", foo.toString(), bar.toString());

        System.out.println("Wrong terms: " + foo.getWrongTerms(bar));

        //checkExpressionTest();
        */

        // System.out.println(check("C2H4(g) + O2(g) -> H2O(l) + CO2(g)", "C2H4(g) + 3O2(g) -> 2CO2(g) + 2H2O(l)"));
    }

    /**
     * Parses a mhchem expression, and outputs a JSON object describing that statement.
     *
     * @param statementString A single mhchem statement in string form.
     * @return JSON object, describing the mhchem statement parsed from the string.
     */
    public static String parseFromString(final String statementString) {
        try {
            @SuppressWarnings("unchecked, deprecation")
            ArrayList<Statement> statements = (ArrayList<Statement>) new ChemistryParser(
                    new ChemistryLexer(new StringReader(statementString))).parse().value;

            Statement statement = statements.get(0);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectNode node = mapper.createObjectNode();
            if (statement instanceof ExpressionStatement) {
                ExpressionStatement exprStatement = (ExpressionStatement) statement;
                node.put("type", "expression");
                node.put("input", statementString);
                node.put("result", exprStatement.toString());
                node.put("containsError", exprStatement.containsError());
                node.put("charge", exprStatement.getCharge().toString());
                HashMap<String, Fraction> atomCount = exprStatement.getAtomCount();
                ObjectNode atomCountNode = node.putObject("atom_count");
                for (String element : atomCount.keySet()) {
                    atomCountNode.put(element, atomCount.get(element).toString());
                }
            } else if (statement instanceof EquationStatement) {
                EquationStatement eqnStatement = (EquationStatement) statement;
                node.put("type", "equation");
                node.put("input", statementString);
                node.put("result", eqnStatement.toString());
                node.put("containsError", eqnStatement.containsError());
                node.put("balanced", eqnStatement.isBalanced());
                node.put("balancedAtoms", eqnStatement.isBalancedAtoms());
                node.put("balancedCharge", eqnStatement.isBalancedCharge());

                ObjectNode leftHandSide = node.putObject("left");
                Expression left = eqnStatement.getLeftExpression();
                leftHandSide.put("containsError", left.containsError());
                leftHandSide.put("charge", left.getCharge().toString());
                HashMap<String, Fraction> atomCountLeft = left.getAtomCount();
                ObjectNode atomCountLeftNode = leftHandSide.putObject("atom_count");
                for (String element : atomCountLeft.keySet()) {
                    atomCountLeftNode.put(element, atomCountLeft.get(element).toString());
                }

                ObjectNode rightHandSide = node.putObject("right");
                Expression right = eqnStatement.getRightExpression();
                rightHandSide.put("containsError", right.containsError());
                rightHandSide.put("charge", right.getCharge().toString());
                HashMap<String, Fraction> atomCountRight = right.getAtomCount();
                ObjectNode atomCountRightNode = rightHandSide.putObject("atom_count");
                for (String element : atomCountRight.keySet()) {
                    atomCountRightNode.put(element, atomCountRight.get(element).toString());
                }
            }
            return mapper.writeValueAsString(node);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * This method checks user input against target string, and returns a JSON object.
     * The JSON object contains following information:
     * <p>
     *     <ul>
     *         <li>testString: The user-provided argument.</li>
     *         <li>targetString: String to be matched against.</li>
     *         <li>test: Parsed testString, in mhchem format.</li>
     *         <li>target: Parsed targetString, in mhchem format.</li>
     *         <li>containsError: Boolean value stating whether or not user input contains error terms.</li>
     *         <li>equal: Determines if user input equivalent to target.</li>
     *         <li>typeMismatch: Determines if user input and target have different types.</li>
     *         <li>expectedType: Determines the type of target string.</li>
     *         <li>receivedType: Determines the type of user input.</li>
     *         <li>weaklyEquivalent: Determines if user input is equivalent to target,
     *         disregarding state symbols and coefficients. </li>
     *         <li>sameCoefficient: Checks if both target and user input have same coefficient on equal terms.</li>
     *         <li>sameArrow: Checks if arrows used in both statements are the same.
     *         A feature only in ExpressionStatement.</li>
     *         <li>sameState: Checks if the state symbols used in the two expressions are the same.
     *         Feature only available for chemical expressions.</li>
     *         <li>isBalanced: Checks if equation is balanced on both sides.
     *         Only featured in EquationStatement and NuclearEquationStatement.</li>
     *         <li>balancedAtoms: Checks if atom count is balanced on both sides of an equation.
     *         Only a feature in EquationStatement.</li>
     *         <li>balancedCharge: Checks if charge is balanced on both sides of an equation.
     *         Only a feature in EquationStatement.</li>
     *         <li>balancedAtomic: Checks if atomic number is balanced. Only useful for NuclearEquationStatement.</li>
     *         <li>balancedMass: Checks if mass number is balanced. Only useful for NuclearEquationStatment.</li>
     *         <li>validAtomicNumber: Checks if atomic number of isotopes in user input matches the element symbol.</li>
     *         <li>wrongTerms: A list of all wrong terms in user input, in mhchem format.</li>
     *     </ul>
     * </p>
     *
     * @param testString User-inputted string
     * @param targetString String to be matched with.
     * @return JSON object containing information about the matching.
     * @throws Exception Parser having trouble reading strings.
     */
    public static String check(final String testString, final String targetString) throws Exception {

        @SuppressWarnings("unchecked, deprecation")
            ArrayList<Statement> testStatements = (ArrayList<Statement>) new ChemistryParser(
                    new ChemistryLexer(new StringReader(testString))).parse().value;

        @SuppressWarnings("unchecked, deprecation")
            ArrayList<Statement> targetStatements = (ArrayList<Statement>) new ChemistryParser(
                    new ChemistryLexer(new StringReader(targetString))).parse().value;

        Statement testStatement = testStatements.get(0);
        Statement targetStatement = targetStatements.get(0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectNode node = mapper.createObjectNode();
        node.put("testString", testString);
        node.put("targetString", targetString);
        node.put("test", testStatement.toString());
        node.put("target", targetStatement.toString());

        if (targetStatement.containsError()) {
            System.err.println("Trusted string contains error!");
            System.err.println("\t\"" + targetString + "\"");
            System.err.println("\t\"" + targetStatement.toString() + "\"");
        }
        node.put("containsError", testStatement.containsError());
        node.put("equal", targetStatement.equals(testStatement));
        node.put("typeMismatch", !targetStatement.getClass().equals(testStatement.getClass()));
        node.put("expectedType", targetStatement.getClass().getSimpleName().replace("Statement", "").toLowerCase());
        node.put("receivedType", testStatement.getClass().getSimpleName().replace("Statement", "").toLowerCase());
        node.put("weaklyEquivalent", targetStatement.weaklyEquivalent(testStatement));

        if (targetStatement instanceof ExpressionStatement) {

            ExpressionStatement target = (ExpressionStatement) targetStatement;

            node.put("sameCoefficient", target.sameCoefficients(testStatement));
            node.put("sameState", target.sameStateSymbols(testStatement));

        } else if (targetStatement instanceof EquationStatement) {

            EquationStatement target = (EquationStatement) targetStatement;

            node.put("sameCoefficient", target.sameCoefficients(testStatement));
            node.put("sameState", target.sameStateSymbols(testStatement));

            if (testStatement instanceof EquationStatement) {

                EquationStatement test = (EquationStatement) testStatement;

                node.put("sameArrow", target.getArrow().equals(test.getArrow()));
                node.put("isBalanced", test.isBalanced());
                node.put("balancedAtoms", test.isBalancedAtoms());
                node.put("balancedCharge", test.isBalancedCharge());
            }

        } else if (targetStatement instanceof NuclearExpressionStatement) {

            if (testStatement instanceof NuclearExpressionStatement) {
                NuclearExpressionStatement test = (NuclearExpressionStatement) testStatement;

                node.put("validAtomicNumber", test.isValid());
            }
        } else {

            // instanceof NuclearEquationStatement
            if (testStatement instanceof NuclearEquationStatement) {

                NuclearEquationStatement test = (NuclearEquationStatement) testStatement;

                node.put("isBalanced", test.isBalanced());
                node.put("balancedAtomic", test.isBalancedAtom());
                node.put("balancedMass", test.isBalancedMass());
                node.put("validAtomicNumber", test.isValid());
            }
        }

        ArrayNode array = mapper.createArrayNode();

        for (Term t: targetStatement.getWrongTerms(testStatement)) {
            array.add(t.toString());
        }

        node.putArray("wrongTerms").addAll(array);

        return mapper.writeValueAsString(node);
    }

    /**
     * Simple test for chemical equations.
     *
     * @throws Exception Parser having trouble reading strings.
     */
    private static void checkEquationTest() throws Exception {
        String solution = "NaOH (aq) + HCl (aq) -> NaCl (aq) + H2O (l);";

        String nonEq   = "NaOH(aq)+HCl(aq);"; // Non-equation

        String error    = "NaOH -> Na++Cl-;";  // Error term Na++Cl-

        String unbalancedAtom = "NaOH + HCl -> 2NaCl + H2O;";   // Atom count unbalanced

        String unbalancedCharge = "OH^{-} + H^{+} -> H2O^{2+};"; // Charge unbalanced

        String correctAns = "NaOH (aq) + HCl (aq) -> H2O(l)+NaCl(aq);"; // Correct answer

        String wrongTerm  = "NaOH (aq) + HCl(aq) -> HOH(l) + ClNa(aq);"; // Poorly-written terms

        String wrongArrow = "NaOH (aq) + HCl (aq) <=> NaCl (aq) + H2O (l);"; // Wrong arrow

        String wrongCoeff = "10NaOH(aq) + 10HCl(aq) -> 10NaCl(aq) + 10H2O(l);"; // Wrong coefficients

        String wrongState = "HCl(g) + NaOH(l) -> NaCl + H2O(l);"; // Wrong state symbols

        String sp1 = "8H(g) + H(l) -> 9H(l);"; // Made-up test cases

        String sp2 = "8H(l) + H(g) -> 9H(l)"; // Switched state symbols

        ArrayList<Statement> stmtList = stringParser(solution + nonEq + error + unbalancedAtom + unbalancedCharge
                + correctAns + wrongTerm + wrongArrow + wrongCoeff + wrongState + sp1 + sp2);

        EquationStatement ans = (EquationStatement) stmtList.get(0);

        final int lastIndex = 11;

        System.out.println("Solution: " + solution);
        System.out.println("---------------");

        for (int i = 1; i < lastIndex - 1; i++) {
            System.out.println("Statement to be checked: " + stmtList.get(i).toString());
            ans.check(stmtList.get(i));
            System.out.println("----------");
        }

        System.out.println("Solution: " + stmtList);
        System.out.println("---------------");
        System.out.println("Statement to be checked: " + stmtList.get(lastIndex).toString());
        stmtList.get(lastIndex - 1).check(stmtList.get(lastIndex));
    }

    /**
     * Simple test for chemical expressions.
     *
     * @throws Exception Parser having trouble reading strings.
     */
    private static void checkExpressionTest() throws Exception {

        String test = "(CH3)3C^{+} + 3H2(g);" +                 // Correct answer 1
                "CH3^{+} + 3CH4 -> (CH3)3C^{+} + 3H2(g);" +     // Non-expression
                "Na++Cl-+3H2(g);" +                             // Error term included
                "3H2(g) + (CH3)3C^{+};" +                       // Correct user input 1
                "(CH3)3C^{+};" +                                // Missing terms
                "2(CH3)3C^{+} + 6H2(g);" +                      // Wrong coefficients
                "(CH3)3C^{+}(g) + 3H2(g);" +                    // Wrong state symbols
                "8H2(g) + H2(l);" +                             // Correct answer 2
                "H2(g) + 8H2(l)";                               // Permuted state symbols

        ArrayList<Statement> stmtList = stringParser(test);

        final int secondCorrectAnsIndex = 7;

        Statement answer1 = stmtList.get(0);
        Statement answer2 = stmtList.get(secondCorrectAnsIndex);

        System.out.println("Solution: " + answer1);
        System.out.println("---------------");

        for (int i = 1; i < secondCorrectAnsIndex; i++) {
            System.out.println("Statement to be checked: " + stmtList.get(i).toString());
            answer1.check(stmtList.get(i));
            System.out.println("----------");
        }

        System.out.println("Solution: " + answer2);
        System.out.println("---------------");
        System.out.println("Statement to be checked: " + stmtList.get(secondCorrectAnsIndex + 1).toString());
        answer2.check(stmtList.get(secondCorrectAnsIndex + 1));
        System.out.println("----------");
    }

    /**
     * Simple test for nuclear equations.
     *
     * @throws Exception Parser having trouble reading strings.
     */
    private static void checkNuclearEquation() throws Exception {

        String test = "^{219}_{86}Rn -> _{84}^{215}Po + \\alpha_particle;"
                + "Rn -> Po + Ca;"
                + "_{219}^{86} Rn -> _{215}^{84}Po ++;"
                + "^{220}_{86}Rn -> ^{215}_{84}Po + \\alpha_particle;"
                + "^{219}_{86}Rn -> ^{215}_{84}Po + ^{4}_{7}N;"
                + "^{14}_{6}U -> ^{14}_{7}N + \\beta_particle;"
                + "^{219}_{86}Rn -> \\alpha_particle + ^{215}_{84}Po;"
                + "^{219}_{86}Rn -> _{84}^{215}Po + \\alpha_particle + \\gamma_ray;"
                + "^{60}_{27}Co -> ^{60}_{27}Co + \\gamma_ray;"
                + "^{60}_{27}Co -> ^{60}_{27}Co + 100\\gamma_ray;";

        ArrayList<Statement> stmtList = stringParser(test);

        final int secondCorrectAnsIndex = 8;

        Statement answer1 = stmtList.get(0);
        Statement answer2 = stmtList.get(secondCorrectAnsIndex);

        System.out.println("Solution: " + answer1);
        System.out.println("---------------");

        for (int i = 1; i < secondCorrectAnsIndex; i++) {
            System.out.println("Statement to be checked: " + stmtList.get(i).toString());
            answer1.check(stmtList.get(i));
            System.out.println("----------");
        }

        System.out.println("Solution: " + answer2);
        System.out.println("---------------");
        System.out.println("Statement to be checked: " + stmtList.get(secondCorrectAnsIndex + 1).toString());
        answer2.check(stmtList.get(secondCorrectAnsIndex + 1));
        System.out.println("----------");
    }

    /**
     * A test for our parsing program.
     * Checks if parser can parse all the nuclear expressions below.
     *
     * @throws Exception Parser have trouble parsing a particular term.
     */
    private static void checkNuclearExpression() throws Exception {

        String test = "_{90}^{234}Th + \\alpha_particle;"
                + "Th + H;"
                + "^{234}_{90}Th ++ \\alpha_particle;"
                + "\\alpha_particle + ^{234}_{90}Th;"
                + "^{234}_{90}U + \\alpha_particle;"
                + "^{234}_{90}Th + \\alpha_particle + \\gamma_ray;"
                + "^{234}_{90}Th + 3\\alpha_particle";

        ArrayList<Statement> stmtList = stringParser(test);

        Statement answer1 = stmtList.get(0);
        System.out.println("Solution: " + answer1);
        System.out.println("---------------");

        for (int i = 1; i < stmtList.size(); i++) {
            System.out.println("Statement to be checked: " + stmtList.get(i).toString());
            answer1.check(stmtList.get(i));
            System.out.println("----------");
        }
    }
}
