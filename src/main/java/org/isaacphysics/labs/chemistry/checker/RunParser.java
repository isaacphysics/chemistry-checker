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
import com.fasterxml.jackson.databind.node.ObjectNode;
import java_cup.runtime.DefaultSymbolFactory;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class RunParser {

    public static void main(String args[]) throws Exception {
        //noinspection deprecation (We know DefaultSymbolFactory is depracated!)
        ArrayList<Statement> statements = (ArrayList<Statement>) new ChemistryParser(new ChemistryLexer(new InputStreamReader(new FileInputStream("src/test.txt"))), new DefaultSymbolFactory()).parse().value;
        System.err.flush();
        System.out.flush();
        System.out.println();
        for (Statement statement : statements) {
            System.out.println(statement);
            if (statement instanceof ExpressionStatement) {
                System.out.println("Total atoms: " + ((ExpressionStatement) statement).getAtomCount());
                System.out.println("Total charge: " + ((ExpressionStatement) statement).getCharge());
            } else if (statement instanceof EquationStatement) {
                System.out.println("Is balanced? " + ((EquationStatement) statement).isBalanced());
                System.out.println("Total atoms LHS: " + ((EquationStatement) statement).getLeftExpression().getAtomCount());
                System.out.println("Total atoms RHS: " + ((EquationStatement) statement).getRightExpression().getAtomCount());
                System.out.println("Total charge LHS: " + ((EquationStatement) statement).getLeftExpression().getCharge());
                System.out.println("Total charge RHS: " + ((EquationStatement) statement).getRightExpression().getCharge());
            }
            System.out.println("\n");
        }

        System.out.println();
        EquationStatement a = (EquationStatement) statements.get(5);
        EquationStatement b = (EquationStatement) statements.get(6);
        if (a.equals(b)) {
            System.out.println("\"" + a.toString() + "\" == \"" + b.toString() + "\"");
            if (!b.equals(a)) {
                System.err.println("Equality not symmetric!");
            }
        } else {
            System.out.println("\"" + a.toString() + "\" != \"" + b.toString() + "\"");
            if (b.equals(a)) {
                System.err.println("Equality not symmetric!");
            }
        }

        System.out.println();
        System.out.println(parseFromString("CH3(CH2)5CH3"));
        System.out.println();
        System.out.println(parseFromString("C6H12O6 + 6O2 -> 6H(2O + 6CO2"));
    }

    public static String parseFromString(String statementString) {
        try {
            ArrayList<Statement> statements = (ArrayList<Statement>) new ChemistryParser(new ChemistryLexer(new StringReader(statementString)), new DefaultSymbolFactory()).parse().value;
            Statement statement = statements.get(0);
            ObjectMapper mapper = new ObjectMapper();
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
}
