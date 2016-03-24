package chemistry_checker;

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
                System.out.println("Total atoms LHS: " + ((EquationStatement) statement).left.getAtomCount());
                System.out.println("Total atoms RHS: " + ((EquationStatement) statement).right.getAtomCount());
                System.out.println("Total charge LHS: " + ((EquationStatement) statement).left.getCharge());
                System.out.println("Total charge RHS: " + ((EquationStatement) statement).right.getCharge());
            }
            System.out.println("\n");
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
