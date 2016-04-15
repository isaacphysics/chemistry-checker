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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
    }

    public String toJSON(String input, String result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node1 = mapper.createObjectNode();
        node1.put("result", result);
        node1.put("input", input);
        return mapper.writeValueAsString(node1);
    }
}
