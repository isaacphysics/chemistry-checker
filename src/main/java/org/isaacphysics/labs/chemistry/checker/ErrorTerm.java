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

import java.util.HashMap;

public final class ErrorTerm extends AbstractTerm
{
    public ErrorTerm()
    {
        super();
    }

    @Override
    public String toString() {
        return "ERROR";
    }

    @Override
    public boolean equals(Object o) {
        // Enforce assertion that error terms can never be equal.
        return false;
    }

    public HashMap<String, Integer> getAtomCount() {
        return new HashMap<>();
    }

    public Integer getCharge() {
        return 0;
    }

    public boolean contains(Formula m) {
        return false;
    }

    public String getDotId() {
        return "error_term_" + dotId;
    }

    public String getDotCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("\t");
        result.append(getDotId());
        result.append(" [label=\"{&zwj;&zwj;&zwj;&zwj;Term&zwnj;|\\n");
        result.append("Syntax Error");
        result.append("\\n\\n}\",color=\"#49902a\"];\n");
        return result.toString();
    }

    public String getDotString() {
        return toString();
    }
}
