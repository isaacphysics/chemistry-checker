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
import com.fasterxml.jackson.databind.node.ObjectNode;
import java_cup.runtime.DefaultSymbolFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class RunParser {

    @SuppressWarnings({"deprecation", "unchecked"})
    private static ArrayList<Statement> stringParser(String s) throws Exception
    {
        Object output = new ChemistryParser(new ChemistryLexer(new StringReader(s)),
                new DefaultSymbolFactory()).parse().value;

        return (ArrayList<Statement>) output;
    }

    public static void main(String args[]) throws Exception
    {
        /*//noinspection deprecation (We know DefaultSymbolFactory is depracated!)
        @SuppressWarnings("unchecked")
        ArrayList<Statement> statements = (ArrayList<Statement>) new ChemistryParser(new ChemistryLexer(new InputStreamReader(new FileInputStream("src/test.txt")))).parse().value;
        System.err.flush();
        System.out.flush();
        System.out.println();
        for (Statement statement : statements)
        {
            System.out.println(statement);

            if (statement instanceof NuclearExpressionStatement)
            {
                NuclearExpressionStatement s = (NuclearExpressionStatement) statement;

                System.out.println("Are atomic numbers valid? " + s.isValid());
                System.out.println("Total atomic#: " + s.getAtomicCount());
                System.out.println("Total mass#: " + s.getMassCount());
            }
            else if (statement instanceof ExpressionStatement)
            {
                ExpressionStatement s = (ExpressionStatement) statement;

                System.out.println("Total atoms: " + s.getAtomCount());
                System.out.println("Total charge: " + s.getCharge());
            }
            else if (statement instanceof EquationStatement)
            {
                EquationStatement s = (EquationStatement) statement;

                System.out.println("Is balanced? " + s.isBalanced());
                System.out.println("Total atoms LHS: " + s.getLeftExpression().getAtomCount());
                System.out.println("Total atoms RHS: " + s.getRightExpression().getAtomCount());
                System.out.println("Total charge LHS: " + s.getLeftExpression().getCharge());
                System.out.println("Total charge RHS: " + s.getRightExpression().getCharge());
            }
            else
            {
                NuclearEquationStatement s = (NuclearEquationStatement) statement;

                System.out.println("Is balanced? " + s.isBalanced());
                System.out.println("Are atomic numbers valid? " + s.isValid());
                System.out.println("Total atomic# LHS: " + s.getLeftExpression().getAtomicCount());
                System.out.println("Total atomic# RHS: " + s.getRightExpression().getAtomicCount());
                System.out.println("Total mass# LHS: " + s.getLeftExpression().getMassCount());
                System.out.println("Total mass# RHS: " + s.getRightExpression().getMassCount());
            }
            //System.out.printf("Dot code:\n%s\n", statement.getDotCode());
            System.out.println("\n");
        }*/

        /*String acid_base = "NaOH(aq) + HCl(aq) -> NaCl(aq) + H2O(l)";
        String wrong_eq  = "NaOH(l) + HCl(aq) -> NaCl + H2O(l)";
        ArrayList<Statement> stmt_list = stringParser(acid_base + ";" + wrong_eq);

        EquationStatement foo = (EquationStatement) stmt_list.get(0);
        EquationStatement bar = (EquationStatement) stmt_list.get(1);

        System.out.printf("Correct    : %s\nInput      : %s\n", foo.toString(), bar.toString());

        System.out.println("Wrong terms: " + foo.getWrongTerms(bar));*/

        checkTest();
    }

    public static String parseFromString(String statementString) {
        try {
            ArrayList<Statement> statements = (ArrayList<Statement>) new ChemistryParser(new ChemistryLexer(new StringReader(statementString))).parse().value;
            Statement statement = statements.get(0);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectNode node = mapper.createObjectNode();
            if (statement instanceof ExpressionStatement) {
                ExpressionStatement exprStatement = (ExpressionStatement) statement;
                node.put("type", "expression");
                node.put("input", statementString);
                node.put("result", exprStatement.toString());
                node.put("error", exprStatement.containsError());
                node.put("charge", exprStatement.getCharge());
                HashMap<String, Integer> atomCount = exprStatement.getAtomCount();
                ObjectNode atomCountNode = node.putObject("atom_count");
                for (String element : atomCount.keySet()) {
                    atomCountNode.put(element, atomCount.get(element));
                }
            } else if (statement instanceof EquationStatement) {
                EquationStatement eqnStatement = (EquationStatement) statement;
                node.put("type", "equation");
                node.put("input", statementString);
                node.put("result", eqnStatement.toString());
                node.put("error", eqnStatement.containsError());
                node.put("balanced", eqnStatement.isBalanced());
                node.put("balancedAtoms", eqnStatement.isBalancedAtoms());
                node.put("balancedCharge", eqnStatement.isBalancedCharge());

                ObjectNode leftHandSide = node.putObject("left");
                Expression left = eqnStatement.getLeftExpression();
                leftHandSide.put("error", left.containsError());
                leftHandSide.put("charge", left.getCharge());
                HashMap<String, Integer> atomCountLeft = left.getAtomCount();
                ObjectNode atomCountLeftNode = leftHandSide.putObject("atom_count");
                for (String element : atomCountLeft.keySet()) {
                    atomCountLeftNode.put(element, atomCountLeft.get(element));
                }

                ObjectNode rightHandSide = node.putObject("right");
                Expression right = eqnStatement.getRightExpression();
                rightHandSide.put("error", right.containsError());
                rightHandSide.put("charge", right.getCharge());
                HashMap<String, Integer> atomCountRight = right.getAtomCount();
                ObjectNode atomCountRightNode = rightHandSide.putObject("atom_count");
                for (String element : atomCountRight.keySet()) {
                    atomCountRightNode.put(element, atomCountRight.get(element));
                }
            }
            return mapper.writeValueAsString(node);
        } catch (Exception e) {
            return "";
        }
    }

    public static String check(String testString, String targetString) {
        try {
            ArrayList<Statement> testStatements = (ArrayList<Statement>) new ChemistryParser(new ChemistryLexer(new StringReader(testString))).parse().value;
            ArrayList<Statement> targetStatements = (ArrayList<Statement>) new ChemistryParser(new ChemistryLexer(new StringReader(targetString))).parse().value;
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
            node.put("error", testStatement.containsError());
            node.put("equal", targetStatement.equals(testStatement));
            node.put("typeMismatch", !targetStatement.getClass().equals(testStatement.getClass()));
            node.put("expectedType", targetStatement.getClass().getSimpleName().replace("Statement", "").toLowerCase());
            node.put("receivedType", testStatement.getClass().getSimpleName().replace("Statement", "").toLowerCase());
            node.put("sameMolecules", targetStatement.equals(testStatement));

            return mapper.writeValueAsString(node);
        } catch (Exception e) {
            return "";
        }
    }

    private static void checkTest() throws Exception
    {
        String solution = "NaOH (aq) + HCl (aq) -> NaCl (aq) + H2O (l);";

        String non_eq   = "NaOH(aq)+HCl(aq);"; // Non-equation

        String error    = "NaOH -> Na++Cl-;";  // Error term Na++Cl-

        String unbalanced_atom = "NaOH + HCl -> 2NaCl + H2O;";   // Atom count unbalanced

        String unbalanced_charge = "OH^{-} + H^{+} -> H2O^{2+};"; // Charge unbalanced

        String correct_ans = "NaOH (aq) + HCl (aq) -> H2O(l)+NaCl(aq);"; // Correct answer

        String wrong_term  = "NaOH (aq) + HCl(aq) -> HOH(l) + ClNa(aq);"; // Poorly-written terms

        String wrong_arrow = "NaOH (aq) + HCl (aq) <--> NaCl (aq) + H2O (l);"; // Wrong arrow

        String wrong_coeff = "10NaOH(aq) + 10HCl(aq) -> 10NaCl(aq) + 10H2O(l);"; // Wrong coefficients

        String wrong_state = "HCl(g) + NaOH(l) -> NaCl + H2O(l);"; // Wrong state symbols

        String sp_1 = "8H(g) + H(l) -> 9H(l);"; // Made-up test cases

        String sp_2 = "8H(l) + H(g) -> 9H(l)"; // Switched state symbols

        ArrayList<Statement> stmt_list = stringParser(solution + non_eq + error + unbalanced_atom + unbalanced_charge
                + correct_ans + wrong_term + wrong_arrow + wrong_coeff + wrong_state + sp_1 + sp_2);

        EquationStatement ans = (EquationStatement) stmt_list.get(0);

        System.out.println("Solution: " + solution);
        System.out.println("---------------");

        for (int i = 1; i < 10; i++)
        {
            System.out.println("Statement to be checked: " + stmt_list.get(i).toString());
            ans.check(stmt_list.get(i));
            System.out.println("----------");
        }

        System.out.println("Solution: " + sp_1);
        System.out.println("---------------");
        System.out.println("Statement to be checked: " + stmt_list.get(11).toString());
        ((EquationStatement) stmt_list.get(10)).check(stmt_list.get(11));
    }
}
