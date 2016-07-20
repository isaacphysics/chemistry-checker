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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        BufferedReader requestStringReader = request.getReader();
        String requestString = "";
        String line;

        while ((line = requestStringReader.readLine()) != null) {

            requestString += line;

        }

        System.out.println(requestString);
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> req = mapper.readValue(requestString, HashMap.class);

        String targetMhchemExpression = req.get("target");
        String testMhchemExpresion = req.get("test");
        /*System.out.println("Target: " + targetMhchemExpression);
        System.out.println("Test: " + testMhchemExpresion);*/

        try {

            response.getWriter().println(RunParser.check(testMhchemExpresion, targetMhchemExpression));

        } catch (Exception e) {

            // Got an exception when checking expressions.
            response.getWriter().println("{\"error\" : true}");

        }
    }
}
