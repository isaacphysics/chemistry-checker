package org.isaacphysics.labs.chemistry.checker;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * This servlet receives JSON objects that contain two mhchem expressions, and give response.
 *
 * Created by hhrl2 on 20/07/2016.
 */
public class CheckerServlet extends HttpServlet {

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

            if (req.containsKey("target") && req.containsKey("test")) {

                // Get target and test mhchem expressions from JSON object
                String targetMhchemExpression = req.get("target");
                String testMhchemExpresion = req.get("test");

                // Debug print
                System.out.println("Parsed target: " + targetMhchemExpression);
                System.out.println("Parsed test: " + testMhchemExpresion);

                // Return
                response.getWriter().println(RunParser.check(testMhchemExpresion, targetMhchemExpression));

            } else {
                response.getWriter().println("{\"error\" : \"No input!\"}");
                System.out.println("ERROR: No input!");
            }


        } catch (Exception e) {

            // Got an exception when checking expressions.
            response.getWriter().println("{\"error\" : true}");
            System.out.println("ERROR: Parser cannot parse input!");

        }

        System.out.println("==================================================\n");
    }
}
