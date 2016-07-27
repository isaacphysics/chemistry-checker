package org.isaacphysics.labs.chemistry.checker;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * This servlet receives JSON objects that contain one mhchem expression, parses it, and give extreme detailed info
 * about that expression.
 *
 * Created by hhrl2 on 26/07/2016.
 */
public class ParserServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        System.out.println("==================================================");

        BufferedReader requestStringReader = request.getReader();
        String requestString = "";
        String line;

        // Read the JSON object
        while ((line = requestStringReader.readLine()) != null) {

            requestString += line;

        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            @SuppressWarnings("unchecked")
            HashMap<String, String> req = mapper.readValue(requestString, HashMap.class);

            if (req.containsKey("description")) {
                System.out.println(req.get("description"));
                System.out.println("==================================================");
            }

            if (req.containsKey("test")) {
                // Get mhchem expressions from JSON object
                String testMhchemExpression = req.get("test");

                // Debug print
                System.out.println("Parsed: " + testMhchemExpression);

                String result = RunParser.parseFromString(testMhchemExpression);

                // Return
                response.getWriter().println(result);

                if (result.contains("ERROR")) {
                    System.out.println("Parse success, but input contained errors.");
                } else {
                    System.out.println("Parse success!");
                }

            } else {
                response.getWriter().println("{\"error\" : \"No input!\"}");
                System.out.println("ERROR: No input!");
            }

        } catch (Exception e) {

            // Got an exception when checking expressions.
            response.getWriter().println("{\"error\" : \"Can't parse input!\"}");
            System.out.println("ERROR: Parser cannot parse input!");

        }

        System.out.println("==================================================\n");
    }

}
