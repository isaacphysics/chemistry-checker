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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * This servlet receives x-www-form-urlencoded data that contain one mhchem expression, parses it, and give extreme
 * detailed info about that expression. It is included for backwards compatibility with labs.isaacphysics.org
 *
 * Created by jps79 on 27/07/2016.
 */
public class FormParserServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        System.out.println("==================================================");
        @SuppressWarnings("unchecked")
        Map<String, String[]> input = request.getParameterMap();
        if (input.containsKey("description")) {
            System.out.println(input.get("description")[0]);
            System.out.println("==================================================");
        }
        if (input.containsKey("test")) {
            System.out.println("Parsing: " + input.get("test")[0]);
            String result = RunParser.parseFromString(input.get("test")[0]);
            response.getWriter().println(result);
            if (result.contains("ERROR")) {
                System.out.println("Parse success, but input contained errors.");
            } else {
                System.out.println("Parse success!");
            }
            if (input.get("test").length != 1) {
                System.out.println("WARN: Ignoring additional inputs!");
            }
        } else {
            response.getWriter().println("{\"error\" : \"No input!\"}");
            System.out.println("ERROR: No input!");
        }
        System.out.println("==================================================\n");
    }

}
