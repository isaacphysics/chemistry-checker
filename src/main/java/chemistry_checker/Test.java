package chemistry_checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java_cup.runtime.DefaultSymbolFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Test {

    public static void main(String args[]) throws Exception {
        //noinspection deprecation (We know DefaultSymbolFactory is depracated!)
        ArrayList<Statement> statements = (ArrayList<Statement>) new ChemistryParser(new ChemistryLexer(new InputStreamReader(new FileInputStream("src/test.txt"))), new DefaultSymbolFactory()).parse().value;
        System.err.flush();
        System.out.flush();
        for (Statement statement : statements) {
            System.out.println(statement);
        }
    }

    public String eqnToString(ArrayList<String> l, ArrayList<String> r) {
        String result = l.toString() + " -> " + r.toString();
        result = result.replace("[", "").replace("]", "").replace(", ", " + ");
        return result;
    }

    public String toJSON(String input, String result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node1 = mapper.createObjectNode();
        node1.put("result", result);
        node1.put("input", input);
        return mapper.writeValueAsString(node1);
    }
}
