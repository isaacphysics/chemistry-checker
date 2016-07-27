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
 * A singleton class for returning an instance of double arrow for chemical equations.
 * Also provides method for pretty printing strings.
 *
 * Created by Ryan on 17/06/2016.
 */
final class DoubleArrow extends AbstractArrow {
    /**
     * The single class instance for DoubleArrow.
     */
    private static DoubleArrow singleton = null;

    /**
     * Constructor function for DoubleArrow.
     */
    private DoubleArrow() {
        // Nothing here, really. This is a singleton object.
    }

    /**
     * Getter method. Returns the only class instance of DoubleArrow.
     * @return Class instance of DoubleArrow
     */
    static DoubleArrow getDoubleArrow() {
        if (singleton == null) {
            singleton = new DoubleArrow();
        }

        return singleton;
    }

    @Override
    public String toString() {
        return " <=> ";
    }

    @Override
    String getDotCode() {
        return "\tarrow [label=\"{&zwj;&zwj;&zwj;&zwj;DoubleArrow&zwnj;|\\n"
                + "&#8652;\\n\\n"
                + "}\",color=\"#4c7fbe\"];\n";
    }

    @Override
    String getDotString() {
        return " &#8652; ";
    }
}
