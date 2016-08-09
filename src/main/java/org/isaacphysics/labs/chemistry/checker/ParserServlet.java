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
        String testMhchemExpression = null;

        try {
            @SuppressWarnings("unchecked")
            HashMap<String, String> req = mapper.readValue(requestString, HashMap.class);

            if (req.containsKey("description")) {
                System.out.println(req.get("description"));
                System.out.println("==================================================");
            }

            if (req.containsKey("test")) {
                // Get mhchem expressions from JSON object
                testMhchemExpression = req.get("test");

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
            response.getWriter().println("{\"error\" : \"Can't parse input!\", "
                    + "\"input\": \"" + testMhchemExpression + "\"}");
            
            System.out.println("ERROR: Parser cannot parse input!");

        }

        System.out.println("==================================================\n");
    }

}
