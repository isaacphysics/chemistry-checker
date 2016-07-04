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

/**
 * Created by Ryan on 17/06/2016.
 * A singleton class for returning an instance of single arrow for chemical equations.
 * Also provides method for pretty printing strings.
 */
public final class SingleArrow extends AbstractArrow
{
    /**
     * The single class instance for SingleArrow.
     */
    private static SingleArrow singleton = null;

    /**
     * Constructor function for SingleArrow.
     */
    private SingleArrow() {}

    /**
     * Getter method. Returns the only class instance of SingleArrow.
     * @return Class instance of SingleArrow
     */
    public static SingleArrow getSingleArrow()
    {
        if (singleton == null)
            singleton = new SingleArrow();

        return singleton;
    }

    @Override
    public String toString() { return " -> "; }

    @Override
    String getDotCode()
    {
        return "\tarrow [label=\"{&zwj;&zwj;&zwj;&zwj;SingleArrow&zwnj;|\\n" +
                "&#8594;\\n\\n" +
                "}\",color=\"#4c7fbe\"];\n";
    }

    @Override
    String getDotString() { return " &#8594; "; }
}
