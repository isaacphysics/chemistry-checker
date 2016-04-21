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

public class ErrorTerm extends AbstractTerm {

    public ErrorTerm() {
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
        return new HashMap<String, Integer>();
    }

    public Integer getCharge() {
        return 0;
    }

    public boolean contains(Molecule m) {
        return false;
    }
}
