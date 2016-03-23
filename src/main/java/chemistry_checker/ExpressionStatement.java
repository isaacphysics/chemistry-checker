package chemistry_checker;

import java.util.ArrayList;

public class ExpressionStatement extends Statement {

    private Expression terms;

    public ExpressionStatement(Expression e) {
        terms=e;
    }

    public String toString() {
        return terms.toString();
    }
}
