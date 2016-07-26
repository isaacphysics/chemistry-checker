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

        BufferedReader requestStringReader = request.getReader();
        String requestString = "";
        String line;

        // Read the JSON object
        while ((line = requestStringReader.readLine()) != null) {

            requestString += line;

        }

        ObjectMapper mapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
        HashMap<String, String> req = mapper.readValue(requestString, HashMap.class);

        // Get mhchem expressions from JSON object
        String targetMhchemExpression = req.get("target");

        // Debug print
        System.out.println("Parse target: " + targetMhchemExpression);

        try {

            // Return
            response.getWriter().println(RunParser.parseFromString(targetMhchemExpression));

        } catch (Exception e) {

            // Got an exception when checking expressions.
            response.getWriter().println("{\"error\" : true}");

        }
    }

}
