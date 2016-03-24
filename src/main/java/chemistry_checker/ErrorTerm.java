package chemistry_checker;

import java.util.HashMap;

public class ErrorTerm extends Term {

    public ErrorTerm() {
        super(null);
    }
    public String toString() {
        return "ERROR";
    }

    public HashMap<String, Integer> getAtomCount() {
        return new HashMap<String, Integer>();
    }

    public Integer getCharge() {
        return 0;
    }
}
