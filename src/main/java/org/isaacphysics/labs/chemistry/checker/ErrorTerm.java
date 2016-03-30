package org.isaacphysics.labs.chemistry.checker;

import java.util.HashMap;

public class ErrorTerm extends Term {

    public ErrorTerm() {
        super(null);
    }

    public String toString() {
        return "ERROR";
    }

    public boolean equals(Object o) {
        // Enforce assertion that error terms can never be equal.
        return false;
    }

    public int hashCode() {
        return 1;
    }

    public HashMap<String, Integer> getAtomCount() {
        return new HashMap<String, Integer>();
    }

    public Integer getCharge() {
        return 0;
    }
}
