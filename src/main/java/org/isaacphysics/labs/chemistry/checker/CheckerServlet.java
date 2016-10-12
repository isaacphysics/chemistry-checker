/**
 * Copyright 2016 Ryan Lau
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
                System.out.println("--------------------------------------------------");
            }

            if (req.containsKey("target") && req.containsKey("test")) {

                // Get target and test mhchem expressions from JSON object
                String targetMhchemExpression = req.get("target");
                String testMhchemExpresion = req.get("test");

                // Debug print
                System.out.println("Target string: '" + targetMhchemExpression + "'");
                System.out.println("Test string: '" + testMhchemExpresion + "'");

                // Return
                String result = RunParser.check(testMhchemExpresion, targetMhchemExpression);
                response.getWriter().println(result);

            } else {
                if (req.containsKey("target")) {
                    System.out.println("Target string: '" + req.get("target") + "'");
                } else {
                    System.out.println("Target string: ''");
                }
                if (req.containsKey("test")) {
                    System.out.println("Test string: '" + req.get("test") + "'");
                } else {
                    System.out.println("Test string: ''");
                }
                response.getWriter().println("{\"error\" : \"Bad input!\"}");
                response.setStatus(400);
                System.out.println("ERROR: Bad input!");
            }

            response.setContentType("application/json");

        } catch (Exception e) {

            // Got an exception when checking expressions.
            response.getWriter().println("{\"error\" : \"" + e.getClass().getSimpleName() + "\"}");
            System.out.println("ERROR: Parser cannot parse input!");

        }

        System.out.println("==================================================\n");
    }
}
